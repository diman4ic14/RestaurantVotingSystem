package ru.falin.RestaurantVotingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.falin.RestaurantVotingSystem.model.Restaurant;
import ru.falin.RestaurantVotingSystem.model.Vote;
import ru.falin.RestaurantVotingSystem.repository.RestaurantRepository;
import ru.falin.RestaurantVotingSystem.repository.VoteRepository;
import ru.falin.RestaurantVotingSystem.to.RestaurantTo;
import ru.falin.RestaurantVotingSystem.util.RestaurantUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNotFoundWithId;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    @Autowired
    public RestaurantService(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    public Restaurant create(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        return restaurantRepository.save(restaurant);
    }

    public void update(Restaurant restaurant) {
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNotFoundWithId(restaurantRepository.save(restaurant), restaurant.getId());
    }

    public Restaurant get(int id) {
        return checkNotFoundWithId(restaurantRepository.get(id), id);
    }

    public void delete(int id) {
        checkNotFoundWithId(restaurantRepository.delete(id), id);
    }

    public List<RestaurantTo> getAllByDay(LocalDate date) {
        LocalDateTime dateTime = date == null ? LocalDate.now().atStartOfDay() : date.atStartOfDay();

        List<Vote> votes = voteRepository.getFilteredByDay(dateTime);
        return RestaurantUtil.getFilteredTo(votes);
    }

    public List<Restaurant> getAll() {
        return restaurantRepository.getAll();
    }
}
