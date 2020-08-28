package ru.falin.RestaurantVotingSystem.web.dish;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.falin.RestaurantVotingSystem.model.Dish;
import ru.falin.RestaurantVotingSystem.service.DishService;
import ru.falin.RestaurantVotingSystem.web.SecurityUtil;

import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.assureIdConsistent;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNew;

public abstract class AbstractDishController {

    protected static final Logger log = getLogger(AbstractDishController.class);

    @Autowired
    private DishService service;

    public Dish get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public List<Dish> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public Dish create(Dish dish, int restaurantId) {
        log.info("create {} for restaurant {}", dish, restaurantId);
        checkNew(dish);
        return service.create(dish, restaurantId);
    }

    public void update(Dish dish, int id, int restaurantId) {
        log.info("update {} with {}", dish, id);
        assureIdConsistent(dish, id);
        service.update(dish, restaurantId);
    }

    public void vote(int restaurantId) {
        int userId = SecurityUtil.authUserId();
        LocalTime time = LocalTime.now();
        log.info("vote for {}", restaurantId);
        service.vote(time, restaurantId, userId);
    }
}
