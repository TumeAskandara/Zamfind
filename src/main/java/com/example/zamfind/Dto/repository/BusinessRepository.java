package com.example.zamfind.Dto.repository;

import com.example.zamfind.model.Business;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface BusinessRepository extends MongoRepository<Business, String> {
    List<Business> findByNameContainingIgnoreCaseOrCategoriesContainingIgnoreCase(String name, String category);

    List<Business> findBusinessByCategoriesAndLocation(String Categories, String location);

    List<Business> findBusinessByUserId(String userId);

}
