package ru.falin.RestaurantVotingSystem.service;

import ru.falin.RestaurantVotingSystem.model.User;

import java.util.List;

public interface UserService {

    User create(User user);

    void delete(int id);

    void update(User user);

    User get(int id);

    List<User> getAll();

    User getByEmail(String email);
}
