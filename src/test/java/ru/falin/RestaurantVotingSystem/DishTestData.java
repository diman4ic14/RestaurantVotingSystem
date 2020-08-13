package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.TestMatcher;
import ru.falin.RestaurantVotingSystem.model.AbstractNamedEntity;
import ru.falin.RestaurantVotingSystem.model.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DishTestData {

    public static final TestMatcher<Dish> DISH_TEST_MATCHER = TestMatcher.usingFieldsComparator("restaurant");

    public static final int NOT_FOUND = 1000;

    public static final int DISH1_ID = 13;

    public static final Dish DISH1 = new Dish(DISH1_ID,"Sub 6 inch", 4);
    public static final Dish DISH2 = new Dish(DISH1_ID + 1,"Sub 12 inch", 6);
    public static final Dish DISH3 = new Dish(DISH1_ID + 2,"BigMac", 3);
    public static final Dish DISH4 = new Dish(DISH1_ID + 3,"French Fries", 2);
    public static final Dish DISH5 = new Dish(DISH1_ID + 4,"Orange juice", 2);
    public static final Dish DISH6 = new Dish(DISH1_ID + 5,"Whopper", 4);
    public static final Dish DISH7 = new Dish(DISH1_ID + 6,"Ice cream", 2);
    public static final Dish DISH8 = new Dish(DISH1_ID + 7,"Basket of chicken", 6);
    public static final Dish DISH9 = new Dish(DISH1_ID + 8,"Longer", 2);

    public static List<Dish> RESTAURANT1_GET_ALL = List.of(DISH1, DISH2).stream()
            .sorted(Comparator.comparing((AbstractNamedEntity::getName)))
            .collect(Collectors.toList());

    public static Dish getNew() {
        return new Dish(null, "newDish", 10);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH1);
        updated.setPrice(5);
        return updated;
    }
}
