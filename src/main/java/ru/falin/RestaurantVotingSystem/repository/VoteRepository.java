package ru.falin.RestaurantVotingSystem.repository;

import ru.falin.RestaurantVotingSystem.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

public interface VoteRepository {

    Vote save(Vote vote, int restaurantId, int userId);

    boolean delete(int id, int userId);

    Vote get(int id, int userId);

    List<Vote> getAll();

    List<Vote> getFilteredByDay(LocalDateTime date);
}
