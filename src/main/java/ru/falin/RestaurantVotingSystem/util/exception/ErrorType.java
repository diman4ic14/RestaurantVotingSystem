package ru.falin.RestaurantVotingSystem.util.exception;

import org.springframework.http.HttpStatus;

public enum ErrorType {
    NOT_ACCEPTABLE_VOTE("Not acceptable vote", HttpStatus.NOT_ACCEPTABLE),
    DATA_ERROR("Data error", HttpStatus.CONFLICT),
    VALIDATION_ERROR("Validation error", HttpStatus.UNPROCESSABLE_ENTITY);

    private final String errorCode;
    private final HttpStatus status;

    ErrorType(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
