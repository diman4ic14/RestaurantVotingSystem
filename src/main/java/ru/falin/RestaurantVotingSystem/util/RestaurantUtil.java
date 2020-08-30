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
