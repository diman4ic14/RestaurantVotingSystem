package ru.falin.RestaurantVotingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.falin.RestaurantVotingSystem.model.Vote;
import ru.falin.RestaurantVotingSystem.repository.VoteRepository;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNotFoundWithId;

@Service
//@Transactional
public class VoteService {

    private final VoteRepository repository;

    @Autowired
    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote, LocalTime localTime, int restaurantId, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkTime(localTime);
        return repository.save(vote, restaurantId, userId);
    }

    public void update(Vote vote, LocalTime localTime, int restaurantId, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkTime(localTime);
        checkNotFoundWithId(repository.save(vote, restaurantId, userId), vote.getId());
    }

    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public List<Vote> getAll() {
        return repository.getAll();
    }

    public List<Vote> getFilteredByDay(LocalDate date) {
        return repository.getFilteredByDay(date.atStartOfDay());
    }

    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    private void checkTime(LocalTime localTime) {
        if (localTime.isAfter(LocalTime.of(10, 59))) {
            throw new NotVotedException("Sorry, but you cannot vote after 11:00");
        }
    }
}
