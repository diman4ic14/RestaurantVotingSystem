package ru.falin.RestaurantVotingSystem.model;

public class Dish extends AbstractNamedEntity {
    private int price;

    public Dish() {
    }

    public Dish(int id, String name, int price) {
        super(id, name);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name=" + name +
                ", price=" + price +
                '}';
    }
}
