package ru.falin.RestaurantVotingSystem.web.dish;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.falin.RestaurantVotingSystem.View;
import ru.falin.RestaurantVotingSystem.model.Dish;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/dishes")
public class DishUIController extends AbstractDishController {

    @Override
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Dish get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Dish> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createOrUpdate(@Validated(View.Web.class) Dish dish) {
        if (dish.isNew()) {
            super.create(dish, dish.getRestaurant().getId());
        } else {
            super.update(dish, dish.getId(), dish.getRestaurant().getId());
        }
    }

    @GetMapping("/today/vote/{id}")
    @Override
    public void vote(@PathVariable("id") int restaurantId) {
        super.vote(restaurantId);
    }
}
