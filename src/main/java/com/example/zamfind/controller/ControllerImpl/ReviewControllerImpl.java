package com.example.zamfind.controller.ControllerImpl;

import com.example.zamfind.model.Review;
import com.example.zamfind.services.serviceInterface.ReviewServiceInter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReviewControllerImpl {

    private final ReviewServiceInter reviewService;

    // Create a new review
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review createdReview = reviewService.createReview(review);
        return new ResponseEntity<>(createdReview, HttpStatus.CREATED);
    }

    // Get all reviews
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    // Get a review by ID
    @GetMapping("/{id}")
    public ResponseEntity<Review> getReview(@PathVariable String id) {
        Review review = reviewService.getReview(id);
        return new ResponseEntity<>(review, HttpStatus.OK);
    }

    // Update a review by ID
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable String id, @RequestBody Review review) {
        Review updatedReview = reviewService.updateReview(id, review);
        return new ResponseEntity<>(updatedReview, HttpStatus.OK);
    }

    // Delete a review by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable String id) {
        reviewService.deleteReviews(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
