package ru.falin.RestaurantVotingSystem.web.dish;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.falin.RestaurantVotingSystem.model.Dish;
import ru.falin.RestaurantVotingSystem.service.DishService;
import ru.falin.RestaurantVotingSystem.util.exception.ErrorType;
import ru.falin.RestaurantVotingSystem.util.exception.NotFoundException;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;
import ru.falin.RestaurantVotingSystem.web.AbstractControllerTest;
import ru.falin.RestaurantVotingSystem.web.json.JsonUtil;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.falin.RestaurantVotingSystem.DishTestData.*;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT1_ID;
import static ru.falin.RestaurantVotingSystem.TestUtil.readFromJson;
import static ru.falin.RestaurantVotingSystem.TestUtil.userHttpBasic;
import static ru.falin.RestaurantVotingSystem.UserTestData.ADMIN;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER1;
import static ru.falin.RestaurantVotingSystem.util.exception.ErrorType.NOT_ACCEPTABLE_VOTE;

class DishRestControllerTest extends AbstractControllerTest {

    private final String REST_URL = DishRestController.REST_URL + "/";

    @Autowired
    private DishService service;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TEST_MATCHER.contentJson(DISH1));
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DISH_TEST_MATCHER.contentJson(DISHES));
    }

    @Test
    void getUnAuth() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID));
    }

    @Test
    void deleteNotAdmin() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + DISH1_ID)
                .with(userHttpBasic(USER1)))
                .andExpect(status().isForbidden());
    }

    @Test
    void voteAfter11am() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isAfter(LocalTime.of(10, 59)));
        perform(MockMvcRequestBuilders.get(REST_URL + "vote/" + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andDo(print())
                .andExpect(status().isNotAcceptable())
                .andExpect(errorType(NOT_ACCEPTABLE_VOTE))
                .andExpect(detailMessage("Sorry, but you cannot vote after 11:00"));
    }

    @Test
    void voteBefore11am() throws Exception {
        Assumptions.assumeTrue(LocalTime.now().isBefore(LocalTime.of(11, 0)), "Voting is allowed before 11 am");
        perform(MockMvcRequestBuilders.get(REST_URL + "vote/" + RESTAURANT1_ID)
                .with(userHttpBasic(ADMIN)))
                .andExpect(status().isOk());
    }

    @Test
    void update() throws Exception {
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());
        DISH_TEST_MATCHER.assertMatch(service.get(updated.getId()), updated);
    }

    @Test
    void updateNotAdmin() throws Exception {
        Dish expected = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + DISH1_ID)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createToLocation() throws Exception {
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newDish)))
                .andExpect(status().isCreated());
        Dish created = readFromJson(action, Dish.class);
        Integer id = created.getId();
        newDish.setId(id);
        DISH_TEST_MATCHER.assertMatch(service.get(id), newDish);
        DISH_TEST_MATCHER.assertMatch(created, newDish);
    }

    @Test
    void createNotAdmin() throws Exception {
        Dish expected = getNew();
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(USER1))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isForbidden());
    }

    @Test
    void createInvalid() throws Exception {
        Dish expected = getNew();
        expected.setName("1");
        perform(MockMvcRequestBuilders.post(REST_URL)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage("Field 'name' size must be between 2 and 100"));
    }

    @Test
    void updateInvalid() throws Exception {
        Dish expected = getUpdated();
        expected.setName("1");
        perform(MockMvcRequestBuilders.put(REST_URL + DISH1_ID)
                .with(userHttpBasic(ADMIN))
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected)))
                .andExpect(status().isUnprocessableEntity())
                .andExpect(errorType(ErrorType.VALIDATION_ERROR))
                .andExpect(detailMessage("Field 'name' size must be between 2 and 100"));
    }
}