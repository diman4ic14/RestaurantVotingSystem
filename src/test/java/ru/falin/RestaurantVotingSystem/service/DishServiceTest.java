package ru.falin.RestaurantVotingSystem.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.falin.RestaurantVotingSystem.model.Dish;
import ru.falin.RestaurantVotingSystem.repository.VoteRepository;
import ru.falin.RestaurantVotingSystem.util.exception.NotFoundException;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;

import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.falin.RestaurantVotingSystem.DishTestData.*;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT1_ID;

public class DishServiceTest extends AbstractServiceTest {

    @Autowired
    private DishService service;

    @Autowired
    private VoteRepository repository;

    @Test
    void create() {
        Dish created = service.create(getNew(), RESTAURANT1_ID);
        int newId = created.getId();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_TEST_MATCHER.assertMatch(created, newDish);
        DISH_TEST_MATCHER.assertMatch(service.get(newId), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, RESTAURANT1_ID);
        DISH_TEST_MATCHER.assertMatch(service.get(updated.getId()), getUpdated());
    }

    @Test
    void delete() {
        service.delete(DISH1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID));
    }

    @Test
    void get() {
        DISH_TEST_MATCHER.assertMatch(service.get(DISH1_ID), DISH1);
    }

    @Test
    void getAll() {
        DISH_TEST_MATCHER.assertMatch(service.getAll(),
                DISHES);
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
    void vote() {
        Integer voteId = service.vote(LocalTime.of(10, 59), 9, 1);
        repository.get(voteId, 1);
    }

    @Test
    void voteAfter11am() {
        assertThrows(NotVotedException.class, () -> service.vote(LocalTime.of(11, 0), 9, 1));
    }
}