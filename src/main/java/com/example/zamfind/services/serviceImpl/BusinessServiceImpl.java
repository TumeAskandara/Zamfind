package com.example.zamfind.services.serviceImpl;

import com.example.zamfind.Dto.repository.BusinessRepository;
import com.example.zamfind.services.serviceInterface.BusinessServiceInter;
import com.example.zamfind.model.Business;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor


public class BusinessServiceImpl implements BusinessServiceInter {

    @Autowired
    private final BusinessRepository businessRepository;

    @Override
    public Business createBusiness(Business business) {
        if (business.getLocation() != null) {
            business.setLocation(business.getLocation().toLowerCase());
        }

        if (business.getCategories() != null) {
            business.setCategories(business.getCategories().stream()
                    .map(String::toLowerCase)
                    .collect(Collectors.toList()));
        }

        business.setCreatedAt(LocalDateTime.now());
        business.setUpdatedAt(LocalDateTime.now());

        return businessRepository.save(business);
    }




    @Override
    public List<Business> getAllBusiness() {
        return businessRepository.findAll();
    }

    @Override
    public List<Business> getAllBusinessByCategoriesAndLocation(String categories,String location){

        return businessRepository.findBusinessByCategoriesAndLocation(categories, location);
    }

    @Override
    public Optional<Business> getBusiness(String id) {
        return businessRepository.findById(id);
    }

    @Override
    public Business updateBusiness(String id, Business updateBusiness) {
        Optional<Business> existingBusiness = businessRepository.findById(id);
        if(existingBusiness.isEmpty()){
            throw new RuntimeException("Business not found with id " + id);
        }

        Business business = existingBusiness.get();
        business.setName(updateBusiness.getName());
        business.setAddress(updateBusiness.getAddress());
        business.setCategories(updateBusiness.getCategories());
        business.setDescription(updateBusiness.getDescription());
        business.setLocation(updateBusiness.getLocation());
        business.setNumber(updateBusiness.getNumber());
        business.setCreatedAt(updateBusiness.getCreatedAt());
        business.setImages(updateBusiness.getImages());
        business.setUpdatedAt(java.time.LocalDateTime.now());
        return businessRepository.save(business);


    }


    @Override
    public void deleteBusiness(String id) {
        if(!businessRepository.existsById(id)){
            throw new RuntimeException("Business with the entered id does not exist" +id);
        }
         businessRepository.deleteById(id);

    }

    @Override
    public List<Business> searchBusinesses(String query) {
        return businessRepository.findByNameContainingIgnoreCaseOrCategoriesContainingIgnoreCase(query, query);
    }

    @Override
    public List<Business> getBusinessByUserId(String userId) {
        return businessRepository.findBusinessByUserId(userId);
    }
}
