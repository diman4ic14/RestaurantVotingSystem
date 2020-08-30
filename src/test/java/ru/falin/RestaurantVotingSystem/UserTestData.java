package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.Role;
import ru.falin.RestaurantVotingSystem.model.User;
import ru.falin.RestaurantVotingSystem.web.json.JsonUtil;

import java.util.Collections;
import java.util.Date;
import java.util.List;

public class UserTestData {
    public static TestMatcher<User> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringAssertions(
            User.class, "registered", "meals", "password", "votes");

    public static final int NOT_FOUND = 1000;

    public static final int ADMIN_ID = 1;
    public static final int USER1_ID = ADMIN_ID + 1;

    public static final User
            ADMIN = new User(ADMIN_ID, "admin", "admin@yandex.ru", "admin", Role.ADMIN),
            USER1 = new User(USER1_ID, "user1", "user1@yandex.ru", "user1", Role.USER),
            USER2 = new User(USER1_ID + 1, "user2", "user2@yandex.ru", "user2", Role.USER),
            USER3 = new User(USER1_ID + 2, "user3", "user3@yandex.ru", "user3", Role.USER),
            USER4 = new User(USER1_ID + 3, "user4", "user4@yandex.ru", "user4", Role.USER),
            USER5 = new User(USER1_ID + 4, "user5", "user5@yandex.ru", "user5", Role.USER),
            USER6 = new User(USER1_ID + 5, "user6", "user6@yandex.ru", "user6", Role.USER),
            USER7 = new User(USER1_ID + 6, "user7", "user7@yandex.ru", "user7", Role.USER);

    public static List<User> USERS = List.of(ADMIN, USER1, USER2, USER3, USER4, USER5, USER6, USER7);

    public static User getNew() {
        return new User(null, "new", "new@gmail.com", "newPass", false, new Date(), Collections.singleton(Role.USER));
    }

    public static User getUpdated() {
        User updated = new User(USER1);
        updated.setName("UpdatedName");
        updated.setEmail("updatedemail@yandex.ru");
        return updated;
    }

    public static String jsonWithPassword(User user, String passw) {
        return JsonUtil.writeAdditionProps(user, "password", passw);
    }
}
