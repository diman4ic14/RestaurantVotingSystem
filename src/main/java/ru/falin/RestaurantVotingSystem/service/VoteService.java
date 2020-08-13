package ru.falin.RestaurantVotingSystem.service;

import ru.falin.RestaurantVotingSystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface VoteService {

    Vote create(Vote vote, LocalTime localTime, int restaurantId, int userId);

    void update(Vote vote, LocalTime localTime, int restaurantId, int userId);

    void delete(int id, int userId);

    List<Vote> getAll();

    List<Vote> getFilteredByDay(LocalDate date);

    Vote get(int id, int userId);
}
