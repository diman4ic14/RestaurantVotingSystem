package ru.falin.RestaurantVotingSystem.to;

import java.beans.ConstructorProperties;

public class RestaurantTo {
    private final int id;
    private final String name;
    private final int votes;

    @ConstructorProperties({"id", "name", "votes"})
    public RestaurantTo(int id, String name, int votes) {
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVotes() {
        return votes;
    }

    @Override
    public String toString() {
        return "RestaurantTo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", votes=" + votes +
                '}';
    }
}
