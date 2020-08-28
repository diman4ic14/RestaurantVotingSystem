package ru.falin.RestaurantVotingSystem.util;

import ru.falin.RestaurantVotingSystem.model.*;
import ru.falin.RestaurantVotingSystem.to.RestaurantTo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RestaurantUtil {
    private RestaurantUtil() {
    }

    public static final List<Restaurant> RESTAURANTS = List.of(
            new Restaurant(9, "Subway"),
            new Restaurant(10, "McDonalds"),
            new Restaurant(11, "Burger King"),
            new Restaurant(12, "KFC")
    );

    private static final User ADMIN = new User(1, "admin", "admin@yandex.ru", "admin", Role.ADMIN);
    private static final User USER1 = new User(2, "user1", "user1@yandex.ru", "user1", Role.USER);
    private static final User USER2 = new User(3, "user2", "user2@yandex.ru", "user2", Role.USER);
    private static final User USER3 = new User(4, "user3", "user3@yandex.ru", "user3", Role.USER);
    private static final User USER4 = new User(5, "user4", "user4@yandex.ru", "user4", Role.USER);
    private static final User USER5 = new User(6, "user5", "user5@yandex.ru", "user5", Role.USER);
    private static final User USER6 = new User(7, "user6", "user6@yandex.ru", "user6", Role.USER);
    private static final User USER7 = new User(8, "user7", "user7@yandex.ru", "user7", Role.USER);

    public static final List<Vote> VOTE_LIST = List.of(
            new Vote(22, ADMIN, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANTS.get(0)),
            new Vote(23, USER1, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANTS.get(3)),
            new Vote(24, USER2, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANTS.get(1)),
            new Vote(25, USER3, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANTS.get(2)),
            new Vote(26, USER4, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANTS.get(1)),
            new Vote(27, USER5, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANTS.get(0)),
            new Vote(28, USER6, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANTS.get(1))
    );


    public static List<RestaurantTo> getFilteredTo(Collection<Vote> votedList) {
        return votedList.stream()
                .collect(Collectors.groupingBy(Vote::getRestaurant, Collectors.counting()))
                .entrySet()
                .stream()
                .map(rest -> createTo(rest.getKey(), Math.toIntExact(rest.getValue())))
                .sorted(Comparator.comparingInt(RestaurantTo::getVotes).reversed())
                .collect(Collectors.toList());
    }

    private static RestaurantTo createTo(Restaurant restaurant, int votes) {
        return new RestaurantTo(restaurant.getId(), restaurant.getName(), votes);
    }
}
