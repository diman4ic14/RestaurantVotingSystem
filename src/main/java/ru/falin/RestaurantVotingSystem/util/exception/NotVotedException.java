package ru.falin.RestaurantVotingSystem.util.exception;

public class NotVotedException extends RuntimeException{

    public NotVotedException(String message) {
        super(message);
    }
}
