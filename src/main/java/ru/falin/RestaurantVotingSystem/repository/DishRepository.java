package ru.falin.RestaurantVotingSystem.repository;

import ru.falin.RestaurantVotingSystem.model.Dish;

import java.time.LocalDateTime;
import java.util.List;

public interface DishRepository {

    Dish save(Dish dish, int restaurantId);

    boolean delete(int id);

    Dish get(int id);

    List<Dish> getAll(LocalDateTime dateTime);
}
