package ru.falin.RestaurantVotingSystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.falin.RestaurantVotingSystem.AuthorizedUser;
import ru.falin.RestaurantVotingSystem.model.User;
import ru.falin.RestaurantVotingSystem.repository.UserRepository;
import ru.falin.RestaurantVotingSystem.to.UserTo;
import ru.falin.RestaurantVotingSystem.util.UserUtil;

import java.util.List;

import static ru.falin.RestaurantVotingSystem.util.UserUtil.prepareToSave;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNotFound;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNotFoundWithId;

@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        Assert.notNull(user, "user must not be null");
        return prepareAndSave(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public void update(User user) {
        Assert.notNull(user, "user must not be null");
//        checkNotFoundWithId(repository.save(user), user.getId());
        prepareAndSave(user);
    }


    public void update(UserTo userTo) {
        User user = get(userTo.getId());
        prepareAndSave(UserUtil.updateFromTo(user, userTo));
    }

    public User get(int id) {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public List<User> getAll() {
        return repository.getAll();
    }

    public User getByEmail(String email) {
        Assert.notNull(email, "email must not be null");
        return checkNotFound(repository.getByEmail(email), "email=" + email);
    }

    @Override
    public AuthorizedUser loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = repository.getByEmail(email.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException("User " + email + " is not found");
        }
        return new AuthorizedUser(user);
    }

    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user, passwordEncoder));
    }

    public void enable(int id, boolean enable) {
        User user = repository.get(id);
        user.setEnabled(enable);
        repository.save(user);
    }
}
