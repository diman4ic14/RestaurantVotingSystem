package ru.falin.RestaurantVotingSystem.web.dish;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.falin.RestaurantVotingSystem.model.Dish;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = DishRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class DishRestController extends AbstractDishController {

    public static final String REST_URL = "rest/dishes";

    @Override
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping
    public List<Dish> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @Secured("ADMIN")
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    @GetMapping("/vote/{restaurantId}")
    public void vote(@PathVariable int restaurantId) {
        super.vote(restaurantId);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Secured("ADMIN")
    public void update(@Valid @RequestBody Dish dish, int id) {
        super.update(dish, id, dish.getRestaurant().getId());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @Secured("ADMIN")
    public ResponseEntity<Dish> createToLocation(@Valid @RequestBody Dish dish) {
        Dish created = super.create(dish, dish.getRestaurant().getId());
        URI urlOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(urlOfNewResource).body(created);
    }
}
