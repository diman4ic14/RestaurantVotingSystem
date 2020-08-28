package ru.falin.RestaurantVotingSystem.web.user;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import ru.falin.RestaurantVotingSystem.model.User;
import ru.falin.RestaurantVotingSystem.service.UserService;
import ru.falin.RestaurantVotingSystem.to.UserTo;
import ru.falin.RestaurantVotingSystem.util.UserUtil;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.assureIdConsistent;
import static ru.falin.RestaurantVotingSystem.util.ValidationUtil.checkNew;

public abstract class AbstractUserController {

    protected static final Logger log = getLogger(AbstractUserController.class);

    @Autowired
    private UserService service;

    public List<User> getAll(){
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id {}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        log.info("update from To {} with id {}", userTo, id);
        assureIdConsistent(userTo, id);
        service.update(userTo);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public User create(UserTo userTo) {
        log.info("create from To {}", userTo);
        return UserUtil.createNewFromTo(userTo);
    }

    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        return service.getByEmail(email);
    }

    public void enable(int id, boolean enable) {
        log.info(enable ? "enable {}" : "disable {}", id);
        service.enable(id, enable);
    }
}
