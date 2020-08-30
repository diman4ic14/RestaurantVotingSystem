package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.Restaurant;
import ru.falin.RestaurantVotingSystem.to.RestaurantTo;

import java.util.List;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(
            Restaurant.class, "menu", "votes");

    public static final TestMatcher<RestaurantTo> RESTAURANT_TO_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(RestaurantTo.class);

    public static final int NOT_FOUND = 1000;

    public static final int RESTAURANT1_ID = 9;

    public static final Restaurant
            RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Subway"),
            RESTAURANT2 = new Restaurant(RESTAURANT1_ID + 1, "McDonalds"),
            RESTAURANT3 = new Restaurant(RESTAURANT1_ID + 2, "Burger King"),
            RESTAURANT4 = new Restaurant(RESTAURANT1_ID + 3, "KFC");

    public static final List<Restaurant> RESTAURANTS = List.of(RESTAURANT3, RESTAURANT4, RESTAURANT2, RESTAURANT1);

    public static Restaurant getNew() {
        return new Restaurant(null, "newRest");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("Updated");
        return updated;
    }
}
