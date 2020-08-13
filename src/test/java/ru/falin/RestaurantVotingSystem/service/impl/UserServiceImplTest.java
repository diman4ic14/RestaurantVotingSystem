package ru.falin.RestaurantVotingSystem.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import ru.falin.RestaurantVotingSystem.model.Role;
import ru.falin.RestaurantVotingSystem.model.User;
import ru.falin.RestaurantVotingSystem.service.UserService;
import ru.falin.RestaurantVotingSystem.util.exception.NotFoundException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.falin.RestaurantVotingSystem.UserTestData.*;

public class UserServiceImplTest extends AbstractServiceTest {

    @Autowired
    private UserService service;

    @Test
    void create() {
        User created = service.create(getNew());
        int newId = created.getId();
        User newUser = getNew();
        newUser.setId(newId);
        USER_MATCHER.assertMatch(created, newUser);
        USER_MATCHER.assertMatch(service.get(newId), newUser);
    }

    @Test
    void delete() {
        service.delete(USER1_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER1_ID));
    }

    @Test
    void update() {
        User updated = getUpdated();
        service.update(updated);
        USER_MATCHER.assertMatch(service.get(USER1_ID), getUpdated());
    }

    @Test
    void get() {
        USER_MATCHER.assertMatch(service.get(USER1_ID), USER1);
    }

    @Test
    void getAll() {
        USER_MATCHER.assertMatch(service.getAll(), ADMIN, USER1, USER2, USER3, USER4, USER5, USER6, USER7);
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND));
    }

    @Test
    public void duplicateMealCreate() {
        assertThrows(DataAccessException.class, () -> service.create(
                new User(null, "Duplicate", "user1@yandex.ru", "newPass", Role.USER)));
    }

    @Test
    public void getByEmail() {
        USER_MATCHER.assertMatch(service.getByEmail("user1@yandex.ru"), USER1);
    }

    @Test
    public void getByEmailNotFound() {
        assertThrows(NotFoundException.class, () -> service.getByEmail("non-existent@gmail.com"));
    }
}