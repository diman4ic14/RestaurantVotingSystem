package ru.falin.RestaurantVotingSystem.web.restaurant;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.falin.RestaurantVotingSystem.model.Restaurant;
import ru.falin.RestaurantVotingSystem.service.RestaurantService;
import ru.falin.RestaurantVotingSystem.to.RestaurantTo;
import ru.falin.RestaurantVotingSystem.web.SecurityUtil;

import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.assureIdConsistent;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNew;

public abstract class AbstractRestaurantController {

    protected static final Logger log = getLogger(AbstractRestaurantController.class);

    @Autowired
    private RestaurantService service;

    public List<RestaurantTo> getAllByDay() {
        log.info("getAllByDay");
        return service.getAllByDay(null);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public Restaurant get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        return service.create(restaurant);
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id {}", restaurant, id);
        assureIdConsistent(restaurant, id);
        service.update(restaurant);
    }

    public void vote(int id) {
        int userId = SecurityUtil.authUserId();
        LocalTime time = LocalTime.now();
        log.info("vote for {}", id);
        service.vote(time, id, userId);
    }
}
