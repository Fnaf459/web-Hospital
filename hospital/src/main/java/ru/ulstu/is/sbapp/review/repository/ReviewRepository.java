package ru.ulstu.is.sbapp.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ulstu.is.sbapp.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
