package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.AbstractNamedEntity;
import ru.falin.RestaurantVotingSystem.model.Dish;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.falin.RestaurantVotingSystem.RestaurantTestData.*;

public class DishTestData {

    public static final TestMatcher<Dish> DISH_TEST_MATCHER = TestMatcher.usingFieldsComparator();

    public static final int NOT_FOUND = 1000;

    public static final int DISH1_ID = 13;

    public static final Dish
                        DISH1 = new Dish(DISH1_ID,"Sub 6 inch", 4, RESTAURANT1),
                        DISH2 = new Dish(DISH1_ID + 1,"Sub 12 inch", 6, RESTAURANT1),
                        DISH3 = new Dish(DISH1_ID + 2,"BigMac", 3, RESTAURANT2),
                        DISH4 = new Dish(DISH1_ID + 3,"French Fries", 2, RESTAURANT2),
                        DISH5 = new Dish(DISH1_ID + 4,"Orange juice", 2,RESTAURANT2),
                        DISH6 = new Dish(DISH1_ID + 5,"Whopper", 4, RESTAURANT3),
                        DISH7 = new Dish(DISH1_ID + 6,"Ice cream", 2, RESTAURANT3),
                        DISH8 = new Dish(DISH1_ID + 7,"Basket of chicken", 6, RESTAURANT4),
                        DISH9 = new Dish(DISH1_ID + 8,"Longer", 2, RESTAURANT4);

    public static List<Dish> RESTAURANTS = List.of(DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9).stream()
            .sorted(Comparator.comparing((AbstractNamedEntity::getName)))
            .collect(Collectors.toList());

    public static Dish getNew() {
        return new Dish(null, "newDish", 10, RESTAURANT1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH1);
        updated.setPrice(5);
        return updated;
    }
}
