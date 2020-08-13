package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.Vote;

import java.time.LocalDateTime;

import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT1;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT2;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT3;
import static ru.falin.RestaurantVotingSystem.RestaurantTestData.RESTAURANT4;
import static ru.falin.RestaurantVotingSystem.UserTestData.ADMIN;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER1;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER2;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER3;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER4;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER5;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER6;
import static ru.falin.RestaurantVotingSystem.UserTestData.USER7;

public class VoteTestData {

    public static final TestMatcher<Vote> VOTE_TEST_MATCHER = TestMatcher.usingFieldsComparator("user", "restaurant");

    public static final int NOT_FOUND = 1000;

    public static final int VOTE1_ID = 22;

    public static final Vote VOTE1 = new Vote(VOTE1_ID, ADMIN, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT1);
    public static final Vote VOTE2 = new Vote(VOTE1_ID + 1, USER1, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT4);
    public static final Vote VOTE3 = new Vote(VOTE1_ID + 2, USER2, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT2);
    public static final Vote VOTE4 = new Vote(VOTE1_ID + 3, USER3, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT3);
    public static final Vote VOTE5 = new Vote(VOTE1_ID + 4, USER4, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT4);
    public static final Vote VOTE6 = new Vote(VOTE1_ID + 5, USER5, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT1);
    public static final Vote VOTE7 = new Vote(VOTE1_ID + 6, USER6, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT2);
    public static final Vote VOTE8 = new Vote(VOTE1_ID + 7, USER1, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT1);
    public static final Vote VOTE9 = new Vote(VOTE1_ID + 8, USER2, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT2);
    public static final Vote VOTE10 = new Vote(VOTE1_ID + 9, USER3, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT2);

    public static Vote getNew() {
        return new Vote(null, USER7, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT1);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE1);
        updated.setRestaurant(RESTAURANT2);
        return updated;
    }
}
