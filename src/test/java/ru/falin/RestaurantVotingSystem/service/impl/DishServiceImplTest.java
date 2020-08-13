package ru.falin.RestaurantVotingSystem.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.falin.RestaurantVotingSystem.model.Dish;
import ru.falin.RestaurantVotingSystem.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.falin.RestaurantVotingSystem.DishTestData.*;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT1_ID;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT2_ID;

public class DishServiceImplTest extends AbstractServiceTest {

    @Autowired
    private DishServiceImpl service;

    @Test
    void create() {
        Dish created = service.create(getNew(), RESTAURANT1_ID);
        int newId = created.getId();
        Dish newDish = getNew();
        newDish.setId(newId);
        DISH_TEST_MATCHER.assertMatch(created, newDish);
        DISH_TEST_MATCHER.assertMatch(service.get(newId, RESTAURANT1_ID), newDish);
    }

    @Test
    void update() {
        Dish updated = getUpdated();
        service.update(updated, RESTAURANT1_ID);
        DISH_TEST_MATCHER.assertMatch(service.get(updated.getId(), RESTAURANT1_ID), getUpdated());
    }

    @Test
    void delete() {
        service.delete(DISH1_ID, RESTAURANT1_ID);
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID, RESTAURANT1_ID));
    }

    @Test
    void get() {
        DISH_TEST_MATCHER.assertMatch(service.get(DISH1_ID, RESTAURANT1_ID), DISH1);
    }

    @Test
    void getAll() {
        DISH_TEST_MATCHER.assertMatch(service.getAll(RESTAURANT1_ID),
                RESTAURANT1_GET_ALL);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, RESTAURANT1_ID));
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(DISH1_ID, RESTAURANT2_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, RESTAURANT1_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(DISH1_ID, RESTAURANT2_ID));
    }
}