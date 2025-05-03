package com.example.zamfind.services.serviceImpl;

import com.example.zamfind.Dto.repository.ReviewRepository;
import com.example.zamfind.model.Review;
import com.example.zamfind.services.serviceInterface.ReviewServiceInter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewServiceInter {

    private final ReviewRepository reviewRepository;

    // Constructor Injection
    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public Review createReview(Review review) {
        // Set creation timestamp
        review.setCreatedAt(java.time.LocalDateTime.now());
        return reviewRepository.save(review);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public Review getReview(String id) {
        return reviewRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No review found with this id: " + id));
    }

    @Override
    public Review updateReview(String id, Review updateReview) {
        Optional<Review> existingReview = reviewRepository.findById(id);

        if (existingReview.isEmpty()) {
            throw new RuntimeException("No review found with this id: " + id);
        }

        Review review = existingReview.get();
        review.setContent(updateReview.getContent());
        review.setRating(updateReview.getRating());
        review.setCreatedAt(java.time.LocalDateTime.now()); // Update timestamp
        return reviewRepository.save(review);
    }

    @Override
    public void deleteReviews(String id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("No review found with this id: " + id);
        }
        reviewRepository.deleteById(id);
    }
}
