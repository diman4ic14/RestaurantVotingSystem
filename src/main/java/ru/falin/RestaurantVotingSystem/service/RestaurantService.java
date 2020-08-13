package ru.falin.RestaurantVotingSystem.service;

import ru.falin.RestaurantVotingSystem.model.Restaurant;

import java.util.List;

public interface RestaurantService {

    Restaurant create(Restaurant restaurant);

    void update(Restaurant restaurant);

    Restaurant get(int id);

    void delete(int id);

    List<Restaurant> getAll();
}
