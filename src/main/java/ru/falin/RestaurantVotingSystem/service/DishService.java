package ru.falin.RestaurantVotingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.falin.RestaurantVotingSystem.model.Dish;
import ru.falin.RestaurantVotingSystem.model.Vote;
import ru.falin.RestaurantVotingSystem.repository.DishRepository;
import ru.falin.RestaurantVotingSystem.repository.VoteRepository;
import ru.falin.RestaurantVotingSystem.util.exception.NotVotedException;

import java.time.LocalTime;
import java.util.List;

import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class DishService {

    private final DishRepository dishRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public DishService(DishRepository dishRepository, VoteRepository voteRepository) {
        this.dishRepository = dishRepository;
        this.voteRepository = voteRepository;
    }

    public Dish create(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        return dishRepository.save(dish, restaurantId);
    }

    public void update(Dish dish, int restaurantId) {
        Assert.notNull(dish, "dish must not be null");
        checkNotFoundWithId(dishRepository.save(dish, restaurantId), dish.getId());
    }

    public void delete(int id) {
        checkNotFoundWithId(dishRepository.delete(id), id);
    }

    public Dish get(int id) {
        return checkNotFoundWithId(dishRepository.get(id), id);
    }

    public List<Dish> getAll() {
        return dishRepository.getAll();
    }

    public void vote(LocalTime time, int restaurantId, int userId) {
        checkTime(time);
        voteRepository.save(new Vote(), restaurantId, userId);
    }

    private void checkTime(LocalTime localTime) {
        if (localTime.isAfter(LocalTime.of(10, 59))) {
            throw new NotVotedException("Sorry, but you cannot vote after 11:00");
        }
    }
}
