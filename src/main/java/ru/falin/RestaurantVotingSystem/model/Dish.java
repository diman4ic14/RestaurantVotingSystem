package ru.falin.RestaurantVotingSystem.model;

import org.hibernate.validator.constraints.Range;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "dishes")
public class Dish extends AbstractNamedEntity {

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @Column(name = "price", nullable = false)
    @Range(min = 1, max = 1000)
    private int price;

    @JoinColumn(name = "restaurant_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    private Restaurant restaurant;

    public Dish() {
    }

    public Dish(Integer id, String name, LocalDateTime date,int price, Restaurant restaurant) {
        this(id, name, date,price);
        this.restaurant = restaurant;
    }

    public Dish(Integer id, String name, LocalDateTime date,  int price) {
        super(id, name);
        this.price = price;
        this.date = date;
    }

    public Dish(Dish dish) {
        this(dish.getId(), dish.getName(), dish.getDate(), dish.getPrice(), dish.restaurant);
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Restaurant getRestaurant() {
        return restaurant;
    }

    public void setRestaurant(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", date=" + date +
                ", price=" + price +
                ", restaurant=" + restaurant +
                '}';
    }
}
