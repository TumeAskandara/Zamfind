package com.example.zamfind.services.serviceInterface;

import com.example.zamfind.model.Review;

import java.util.List;

public interface ReviewServiceInter {
    public Review createReview(Review review);

    List<Review> getAllReviews();

    public Review getReview(String id);

    public Review updateReview(String id, Review updateReview);

    void  deleteReviews(String id);



}
