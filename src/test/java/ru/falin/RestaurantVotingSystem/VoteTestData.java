package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.Vote;

import java.time.LocalDateTime;

import static ru.falin.RestaurantVotingSystem.RestaurantTestData.*;
import static ru.falin.RestaurantVotingSystem.UserTestData.*;

public class VoteTestData {

    public static final TestMatcher<Vote> VOTE_TEST_MATCHER = TestMatcher.usingFieldsComparator("user", "restaurant");

    public static final int NOT_FOUND = 1000;

    public static final int VOTE1_ID = 22;

    public static final Vote
            VOTE1 = new Vote(VOTE1_ID, ADMIN, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT1),
            VOTE2 = new Vote(VOTE1_ID + 1, USER1, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT4),
            VOTE3 = new Vote(VOTE1_ID + 2, USER2, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT2),
            VOTE4 = new Vote(VOTE1_ID + 3, USER3, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT3),
            VOTE5 = new Vote(VOTE1_ID + 4, USER4, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT4),
            VOTE7 = new Vote(VOTE1_ID + 6, USER6, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT2),
            VOTE6 = new Vote(VOTE1_ID + 5, USER5, LocalDateTime.of(2020, 8, 13, 0, 0), RESTAURANT1),
            VOTE8 = new Vote(VOTE1_ID + 7, USER1, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT1),
            VOTE9 = new Vote(VOTE1_ID + 8, USER2, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT2),
            VOTE10 = new Vote(VOTE1_ID + 9, USER3, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT2);

    public static Vote getNew() {
        return new Vote(null, USER7, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT1);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(VOTE1);
        updated.setRestaurant(RESTAURANT2);
        return updated;
    }
}
