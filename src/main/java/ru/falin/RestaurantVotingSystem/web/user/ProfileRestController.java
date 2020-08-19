package ru.falin.RestaurantVotingSystem.web.user;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.falin.RestaurantVotingSystem.model.User;
import ru.falin.RestaurantVotingSystem.to.UserTo;

import javax.validation.Valid;

import java.net.URI;

import static ru.falin.RestaurantVotingSystem.web.SecurityUtil.authUserId;

@RestController
@RequestMapping(value = ProfileRestController.REST_URL)
public class ProfileRestController extends AbstractUserController {

    static final String REST_URL = "rest/profile";

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public User get() {
        return super.get(authUserId());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete() {
        super.delete(authUserId());
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody UserTo userTo) {
        User user = super.create(userTo);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL).build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo) {
        super.update(userTo, authUserId());
    }
}
