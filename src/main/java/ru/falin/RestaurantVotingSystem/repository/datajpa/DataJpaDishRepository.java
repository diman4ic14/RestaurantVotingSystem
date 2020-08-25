package ru.falin.RestaurantVotingSystem.repository.datajpa;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.falin.RestaurantVotingSystem.model.Dish;
import ru.falin.RestaurantVotingSystem.repository.DishRepository;

import java.util.List;
import java.util.Objects;

@Repository
@Transactional
public class DataJpaDishRepository implements DishRepository {

    private final CrudDishRepository crudDishRepository;
    private final CrudRestaurantRepository crudRestaurantRepository;

    @Autowired
    public DataJpaDishRepository(CrudDishRepository crudDishRepository, CrudRestaurantRepository crudRestaurantRepository) {
        this.crudDishRepository = crudDishRepository;
        this.crudRestaurantRepository = crudRestaurantRepository;
    }

    @Override
    public Dish save(Dish dish, int restaurantId) {
        if (!dish.isNew() && get(dish.getId(), restaurantId) == null) {
            return null;
        }
        dish.setRestaurant(crudRestaurantRepository.getOne(restaurantId));
        Dish saved = crudDishRepository.save(dish);

        Hibernate.initialize(saved.getRestaurant());

        return saved;
    }

    @Override
    public boolean delete(int id, int restaurantId) {
        return crudDishRepository.delete(id, restaurantId) != 0;
    }

    @Override
    public Dish get(int id, int restaurantId) {
        Dish dish = crudDishRepository.findById(id)
                .filter(d -> d.getRestaurant().getId() == restaurantId)
                .orElse(null);

        if (Objects.nonNull(dish)) {
            Hibernate.initialize(dish.getRestaurant());
        }

        return dish;
    }

    @Override
    public List<Dish> getAll(int restaurantId) {
        return crudDishRepository.getAll(restaurantId);
    }
}
