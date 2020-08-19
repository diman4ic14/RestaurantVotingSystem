package ru.falin.RestaurantVotingSystem;

import ru.falin.RestaurantVotingSystem.model.User;
import ru.falin.RestaurantVotingSystem.to.UserTo;
import ru.falin.RestaurantVotingSystem.util.UserUtil;

public class AuthorizedUser extends org.springframework.security.core.userdetails.User {

    private static final long serialVersionUID = 1L;

    private UserTo userTo;

    public AuthorizedUser(User user) {
        super(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, user.getRoles());
        this.userTo = UserUtil.asTo(user);
    }

    public Integer getId() {
        return userTo.getId();
    }

    public void update(UserTo userTo) {
        this.userTo = userTo;
    }

    public UserTo getUserTo() {
        return this.userTo;
    }

    public String toString() {
        return userTo.toString();
    }
}
