package ru.falin.RestaurantVotingSystem.to;

import ru.falin.RestaurantVotingSystem.model.Dish;

import java.util.List;

public class RestaurantTo {
    private final int id;
    private final String name;
    private final List<Dish> menu;
    private final int votes;

    public RestaurantTo(int id, String name, List<Dish> menu, int votes) {
        this.id = id;
        this.name = name;
        this.menu = menu;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", menu=" + menu +
                ", votes=" + votes +
                '}';
    }
}
