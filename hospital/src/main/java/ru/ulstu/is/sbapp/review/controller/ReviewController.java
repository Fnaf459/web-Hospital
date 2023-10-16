package ru.ulstu.is.sbapp.review.controller;

import org.springframework.web.bind.annotation.*;
import ru.ulstu.is.sbapp.configuration.WebConfiguration;
import ru.ulstu.is.sbapp.review.controller.ReviewDTO;
import ru.ulstu.is.sbapp.review.service.ReviewService;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(WebConfiguration.REST_API + "/review")
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/{id}")
    public ReviewDTO getReview(@PathVariable Long id) {
        return new ReviewDTO(reviewService.findReview(id));
    }

    @GetMapping("/")
    public List<ReviewDTO> getReviews() {
        return reviewService.findAllReviews().stream()
                .map(ReviewDTO::new)
                .toList();
    }


    @PostMapping("/edit")
    public ReviewDTO createReview(@RequestParam int gradeDoctor,
                                  @RequestParam String textField,
                                  @RequestParam Long doctorId,
                                  @RequestParam Long patientId) {
        return new ReviewDTO(reviewService.addReview(gradeDoctor, textField, doctorId, patientId));
    }

    @PutMapping("/{id}")
    public ReviewDTO updateReview(@PathVariable Long id,
                                  @RequestParam int gradeDoctor,
                                  @RequestParam String textField,
                                  @RequestParam Long doctorId,
                                  @RequestParam Long patientId) {
        return new ReviewDTO(reviewService.updateReview(id, gradeDoctor, textField, doctorId, patientId));
    }

    @DeleteMapping("/{id}")
    public ReviewDTO deleteReview(@PathVariable Long id) {
        return new ReviewDTO(reviewService.deleteReview(id));
    }
}
