package ru.ulstu.is.sbapp.review.service;

import ru.ulstu.is.sbapp.review.model.Review;

public class ReviewNotFoundException extends RuntimeException{
    public ReviewNotFoundException(Long id) {
        super(String.format("Review with id [%s] is not found", id));
    }
}
