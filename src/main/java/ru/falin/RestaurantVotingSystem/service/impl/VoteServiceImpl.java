package ru.falin.RestaurantVotingSystem.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.falin.RestaurantVotingSystem.model.Vote;
import ru.falin.RestaurantVotingSystem.repository.VoteRepository;
import ru.falin.RestaurantVotingSystem.service.VoteService;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNotFoundWithId;

@Service
@Transactional
public class VoteServiceImpl implements VoteService {

    private final VoteRepository repository;

    @Autowired
    public VoteServiceImpl(VoteRepository repository) {
        this.repository = repository;
    }

    @Override
    public Vote create(Vote vote, LocalTime localTime, int restaurantId, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkTime(localTime);
        return repository.save(vote, restaurantId, userId);
    }

    @Override
    public void update(Vote vote, LocalTime localTime, int restaurantId, int userId) {
        Assert.notNull(vote, "vote must not be null");
        checkTime(localTime);
        checkNotFoundWithId(repository.save(vote, restaurantId, userId), vote.getId());
    }

    @Override
    public void delete(int id, int userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public List<Vote> getAll() {
        return repository.getAll();
    }

    @Override
    public List<Vote> getFilteredByDay(LocalDate date) {
        return repository.getFilteredByDay(date.atStartOfDay());
    }

    @Override
    public Vote get(int id, int userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    private void checkTime(LocalTime localTime) {
        if (localTime.isAfter(LocalTime.of(10, 59))) {
            throw new NotVotedException("Sorry, but you cannot vote after 11:00");
        }
    }
}
