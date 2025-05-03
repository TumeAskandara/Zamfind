package com.example.zamfind.Dto.repository;

import com.example.zamfind.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review,String> {
}
