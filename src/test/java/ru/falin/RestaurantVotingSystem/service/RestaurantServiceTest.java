package ru.falin.RestaurantVotingSystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.falin.RestaurantVotingSystem.model.Restaurant;
import ru.falin.RestaurantVotingSystem.repository.VoteRepository;
import ru.falin.RestaurantVotingSystem.util.RestaurantUtil;
import ru.falin.RestaurantVotingSystem.util.exception.NotFoundException;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.*;
import static ru.falin.RestaurantVotingSystem.VoteTestData.VOTES_BY_DAY;


public class RestaurantServiceTest extends AbstractServiceTest {

    @Autowired
    private RestaurantService service;
    @Autowired
    private VoteRepository repository;

    @Test
    void create() {
        Restaurant created = service.create(getNew());
        int newId = created.getId();
        Restaurant newRestaurant = getNew();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(service.get(newId), newRestaurant);
    }

    @Test
    void update() {
        Restaurant updated = getUpdated();
        service.update(updated);
        RESTAURANT_MATCHER.assertMatch(service.get(updated.getId()), getUpdated());
    }

    @Test
    void get() {
        RESTAURANT_MATCHER.assertMatch(service.get(RESTAURANT1_ID), RESTAURANT1);
    }

    @Test
    void delete() {
        service.delete(RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(RESTAURANT1_ID));
    }

    @Test
    void getAllByDay() {
        RESTAURANT_TO_MATCHER.assertMatch(service.getAllByDay(LocalDate.now()),
                RestaurantUtil.getFilteredTo(VOTES_BY_DAY));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND));
    }

    @Test
    void createDuplicateName() {
        assertThrows(DataAccessException.class, () ->
                service.create(new Restaurant(null, "Subway")));
    }

    @Test
    void getAll() {
        RESTAURANT_MATCHER.assertMatch(service.getAll(), RESTAURANTS);
    }

    @Test
    void vote() {
        Integer voteId = service.vote(LocalTime.of(10, 59), 9, 1);
        repository.get(voteId, 1);
    }

    @Test
    void voteAfter11am() {
        assertThrows(NotVotedException.class, () -> service.vote(LocalTime.of(11, 0), 9, 1));
    }
}