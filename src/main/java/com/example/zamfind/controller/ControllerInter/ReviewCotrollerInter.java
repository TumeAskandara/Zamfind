package com.example.zamfind.controller.ControllerInter;

import com.example.zamfind.Dto.ResponseDTO;
import com.example.zamfind.model.Review;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReviewCotrollerInter {

    ResponseEntity<ResponseDTO> createdReview(Review review);

    ResponseEntity<List<ResponseDTO>> getAllReview();

    ResponseEntity<ResponseDTO> updateReview(String id, Review review);

    ResponseEntity<Void> deleteReview(String id);

    ResponseEntity<ResponseDTO> getReview(String id);
}
