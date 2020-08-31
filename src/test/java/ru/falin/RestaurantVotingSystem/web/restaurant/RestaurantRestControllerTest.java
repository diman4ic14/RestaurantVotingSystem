package ru.falin.RestaurantVotingSystem.web.restaurant;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.falin.RestaurantVotingSystem.model.Restaurant;
import ru.falin.RestaurantVotingSystem.service.RestaurantService;
import ru.falin.RestaurantVotingSystem.util.RestaurantUtil;
import ru.falin.RestaurantVotingSystem.util.exception.NotFoundException;
import ru.falin.RestaurantVotingSystem.web.AbstractControllerTest;
import ru.falin.RestaurantVotingSystem.web.json.JsonUtil;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.*;
import static ru.falin.RestaurantVotingSystem.TestUtil.readFromJson;
import static ru.falin.RestaurantVotingSystem.TestUtil.userHttpBasic;
import static ru.falin.RestaurantVotingSystem.UserTestData.ADMIN;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER1;
import static ru.falin.RestaurantVotingSystem.VoteTestData.VOTES_BY_DAY;
import static ru.falin.RestaurantVotingSystem.util.exception.ErrorType.NOT_ACCEPTABLE_VOTE;
import static ru.falin.RestaurantVotingSystem.util.exception.ErrorType.VALIDATION_ERROR;
import static ru.falin.RestaurantVotingSystem.web.ExceptionInfoHandler.EXCEPTION_DUPLICATE_NAME;

class RestaurantRestControllerTest extends AbstractControllerTest {

    private final String REST_URL = RestaurantRestController.REST_URL + "/";

    @Autowired
    private RestaurantService service;

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANTS));
    }

    @Test
    void getAllNowAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andDo(print())
                .andExpect(status().isForbidden());
    }

    @Test
    void getAllByDay() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "today")
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_TO_MATCHER.contentJson(RestaurantUtil.getFilteredTo(VOTES_BY_DAY)));
    }

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(RESTAURANT_MATCHER.contentJson(RESTAURANT1));
    }

    @Test
    void getNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void deleteNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createToLocation() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        Integer id = created.getId();
        newRestaurant.setId(id);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(id), newRestaurant);
    }

    @Test
    void voteAfter11am() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isAfter(LocalTime.of(10, 59)));
        perform(MockMvcRequestBuilders.post(REST_URL + "vote")
                .with(userHttpBasic(ADMIN))
                .param("id", String.valueOf(RESTAURANT1_ID)))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(errorType(NOT_ACCEPTABLE_VOTE))
                .andExpect(detailMessage("Sorry, but you cannot vote after 11:00"));
    }

    @Test
    void voteBefore11am() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isBefore(LocalTime.of(11, 0)), "Voting is allowed before 11 am");
        perform(MockMvcRequestBuilders.post(REST_URL + "vote")
                .with(userHttpBasic(ADMIN))
                .param("id", String.valueOf(RESTAURANT1_ID)))
                .andExpect(status().isOk());
    }

    @Test
    void createNotAdmin() throws Exception {
        Restaurant expected = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    void updateNotAdmin() throws Exception {
        Restaurant expected = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createInvalid() throws Exception {
        Restaurant expected = getNew();
        expected.setName("1");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage("Field 'name' size must be between 2 and 100"));
    }

    @Test
    void updateInvalid() throws Exception {
        Restaurant expected = getUpdated();
        expected.setName("1");
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage("Field 'name' size must be between 2 and 100"));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void createDuplicate() throws Exception {
        Restaurant expected = getNew();
        expected.setName("Subway");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME));
    }

    @Test
    @Transactional(propagation = Propagation.NEVER)
    void updateDuplicate() throws Exception {
        Restaurant expected = getUpdated();
        expected.setName("KFC");
        perform(MockMvcRequestBuilders.put(REST_URL + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(VALIDATION_ERROR))
                .andExpect(detailMessage(EXCEPTION_DUPLICATE_NAME));
    }
}