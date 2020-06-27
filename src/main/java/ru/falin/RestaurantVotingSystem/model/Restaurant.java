package ru.falin.RestaurantVotingSystem.model;

import java.util.List;

public class Restaurant extends AbstractNamedEntity {
    private List<Dish> menu;

    public Restaurant() {
    }

    public Restaurant(int id, String name, List<Dish> menu) {
        super(id, name);
        this.menu = menu;
    }

    public List<Dish> getMenu() {
        return menu;
    }

    public void setMenu(List<Dish> menu) {
        this.menu = menu;
    }

    @Override
    public String toString() {
        return "Restaurant{" +
                "id=" + id +
                ", name='" + name +
                ", menu=" + menu +
                '}';
    }
}
