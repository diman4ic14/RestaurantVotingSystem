package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.AbstractNamedEntity;
import ru.falin.RestaurantVotingSystem.model.Dish;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static ru.falin.RestaurantVotingSystem.RestaurantTestData.*;

public class DishTestData {

    public static final TestMatcher<Dish> DISH_TEST_MATCHER = TestMatcher.usingEqualsAssertions(Dish.class);

    public static final int NOT_FOUND = 1000;

    public static final int DISH1_ID = 13;

    public static final Dish
                        DISH1 = new Dish(DISH1_ID,"Sub 6 inch", LocalDate.now().atStartOfDay(), 4, RESTAURANT1),
                        DISH2 = new Dish(DISH1_ID + 1,"Sub 12 inch", LocalDate.now().atStartOfDay(), 6, RESTAURANT1),
                        DISH3 = new Dish(DISH1_ID + 2,"BigMac", LocalDate.now().atStartOfDay(), 3, RESTAURANT2),
                        DISH4 = new Dish(DISH1_ID + 3,"French Fries", LocalDate.now().atStartOfDay(), 2, RESTAURANT2),
                        DISH5 = new Dish(DISH1_ID + 4,"Orange juice", LocalDate.now().atStartOfDay(), 2,RESTAURANT2),
                        DISH6 = new Dish(DISH1_ID + 5,"Whopper", LocalDate.now().atStartOfDay(), 4, RESTAURANT3),
                        DISH7 = new Dish(DISH1_ID + 6,"Ice cream", LocalDate.now().atStartOfDay(), 2, RESTAURANT3),
                        DISH8 = new Dish(DISH1_ID + 7,"Basket of chicken", LocalDate.now().atStartOfDay(), 6, RESTAURANT4),
                        DISH9 = new Dish(DISH1_ID + 8,"Longer", LocalDate.now().atStartOfDay(), 2, RESTAURANT4);

    public static List<Dish> DISHES = List.of(DISH1, DISH2, DISH3, DISH4, DISH5, DISH6, DISH7, DISH8, DISH9).stream()
            .sorted(Comparator.comparing((AbstractNamedEntity::getName)))
            .collect(Collectors.toList());

    public static Dish getNew() {
        return new Dish(null, "newDish", LocalDate.now().atStartOfDay(), 10, RESTAURANT1);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(DISH1);
        updated.setPrice(5);
        return updated;
    }
}
