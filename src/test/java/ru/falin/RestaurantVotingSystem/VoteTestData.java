package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.Vote;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.falin.RestaurantVotingSystem.RestaurantTestData.*;
import static ru.falin.RestaurantVotingSystem.UserTestData.*;

public class VoteTestData {

    public static final TestMatcher<Vote> VOTE_TEST_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(
            Vote.class, "user", "restaurant");

    public static final int VOTE1_ID = 22;

    public static final Vote
            VOTE1 = new Vote(VOTE1_ID, USER1, LocalDate.now().atStartOfDay(), RESTAURANT2),
            VOTE2 = new Vote(VOTE1_ID + 1, USER2, LocalDate.now().atStartOfDay(), RESTAURANT2),
            VOTE3 = new Vote(VOTE1_ID + 2, USER3, LocalDate.now().atStartOfDay(), RESTAURANT3),
            VOTE4 = new Vote(VOTE1_ID + 3, USER4, LocalDate.now().atStartOfDay(), RESTAURANT4),
            VOTE5 = new Vote(VOTE1_ID + 4, USER5, LocalDate.now().atStartOfDay(), RESTAURANT2),
            VOTE6 = new Vote(VOTE1_ID + 5, USER6, LocalDate.now().atStartOfDay(), RESTAURANT1),
            VOTE7 = new Vote(VOTE1_ID + 6, USER7, LocalDate.now().atStartOfDay(), RESTAURANT1);

    public static final List<Vote> VOTES_BY_DAY = List.of(VOTE1, VOTE2, VOTE3, VOTE4, VOTE5, VOTE6, VOTE7);

    public static Vote getNew() {
        return new Vote(null, USER7, LocalDateTime.of(2020, 8, 14, 0, 0), RESTAURANT1);
    }
}
