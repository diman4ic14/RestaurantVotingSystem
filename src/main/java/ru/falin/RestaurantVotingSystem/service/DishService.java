package ru.falin.RestaurantVotingSystem.service;

import ru.falin.RestaurantVotingSystem.model.Dish;

import java.util.List;

public interface DishService {
    Dish create(Dish dish, int restaurantId);

    void update(Dish dish, int restaurantId);

    void delete(int id, int restaurantId);

    Dish get(int id, int restaurantId);

    List<Dish> getAll(int restaurantId);
}
