package ru.falin.RestaurantVotingSystem.service.impl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import ru.falin.RestaurantVotingSystem.model.Vote;
import ru.falin.RestaurantVotingSystem.service.VoteService;
import ru.falin.RestaurantVotingSystem.util.exception.NotFoundException;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.falin.RestaurantVotingSystem.UserTestData.ADMIN_ID;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER1_ID;
import static ru.falin.RestaurantVotingSystem.VoteTestData.*;

public class VoteServiceTest extends AbstractServiceTest {

    @Autowired
    private VoteService service;

    @Test
    void create() {
        Vote newVote = getNew();
        Vote created = service.create(newVote, LocalTime.of(9, 0),
                newVote.getRestaurant().getId(), newVote.getUser().getId());
        int newId = created.getId();
        newVote.setId(newId);
        VOTE_TEST_MATCHER.assertMatch(created, newVote);
        VOTE_TEST_MATCHER.assertMatch(service.get(newId, newVote.getUser().getId()), newVote);
    }

    @Test
    void createAfter11am() {
        Vote newVote = getNew();
        assertThrows(NotVotedException.class, () ->
                service.create(newVote, LocalTime.of(11, 0),
                        newVote.getRestaurant().getId(), newVote.getUser().getId()));
    }

    @Test
    void update() {
        Vote updated = getUpdated();
        service.update(updated, LocalTime.of(10, 0),
                updated.getRestaurant().getId(), updated.getUser().getId());
        VOTE_TEST_MATCHER.assertMatch(service.get(updated.getId(), updated.getUser().getId()), updated);
    }

    @Test
    void updateNotOwn() {
        Vote updated = getUpdated();
        assertThrows(NotFoundException.class, () -> service.update(updated, LocalTime.of(10, 0),
                updated.getRestaurant().getId(), USER1_ID));
    }

    @Test
    void delete() {
        service.delete(VOTE1_ID, ADMIN_ID);
        assertThrows(NotFoundException.class, () -> service.get(VOTE1_ID, ADMIN_ID));
    }

    @Test
    void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(VOTE1_ID, USER1_ID));
    }

    @Test
    void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, ADMIN_ID));
    }

    @Test
    void getAll() {
        VOTE_TEST_MATCHER.assertMatch(service.getAll(),
                VOTE10, VOTE9, VOTE8, VOTE7, VOTE6, VOTE5, VOTE4, VOTE3, VOTE2, VOTE1);
    }

    @Test
    void getFilteredByDay() {
        List<Vote> filteredByDay = service.getFilteredByDay(LocalDate.of(2020, 8, 14));
        VOTE_TEST_MATCHER.assertMatch(filteredByDay, VOTE10, VOTE9, VOTE8);
    }

    @Test
    void get() {
        VOTE_TEST_MATCHER.assertMatch(service.get(VOTE1_ID, ADMIN_ID), VOTE1);
    }

    @Test
    void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(VOTE1_ID, USER1_ID));
    }

    @Test
    void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, ADMIN_ID));
    }
}