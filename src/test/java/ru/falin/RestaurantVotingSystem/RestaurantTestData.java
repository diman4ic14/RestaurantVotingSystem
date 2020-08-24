package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.Restaurant;

import java.util.List;

import static ru.falin.RestaurantVotingSystem.DishTestData.*;

public class RestaurantTestData {
    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingFieldsComparator("menu", "votes");

    public static final int NOT_FOUND = 1000;

    public static final int RESTAURANT1_ID = 9;
    public static final int RESTAURANT2_ID = RESTAURANT1_ID + 1;

    public static final Restaurant
            RESTAURANT1 = new Restaurant(RESTAURANT1_ID, "Subway"),
            RESTAURANT2 = new Restaurant(RESTAURANT1_ID + 1, "McDonalds"),
            RESTAURANT3 = new Restaurant(RESTAURANT1_ID + 2, "Burger King"),
            RESTAURANT4 = new Restaurant(RESTAURANT1_ID + 3, "KFC");

    public static Restaurant getNew() {
        return new Restaurant(null, "newRest");
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(RESTAURANT1);
        updated.setName("Updated");
        return updated;
    }
}
