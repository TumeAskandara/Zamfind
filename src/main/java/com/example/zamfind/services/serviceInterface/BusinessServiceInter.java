package com.example.zamfind.services.serviceInterface;

import com.example.zamfind.model.Business;

import java.util.List;
import java.util.Optional;

public interface BusinessServiceInter {

    public Business createBusiness(Business business);

    public List<Business> getAllBusiness();

    List<Business> getAllBusinessByCategoriesAndLocation(String categories, String location);

    public Optional<Business> getBusiness(String id);

    public Business updateBusiness(String id, Business updateBusiness);

    void  deleteBusiness(String id);

    List <Business> searchBusinesses(String query);

    List<Business> getBusinessByUserId(String userId);
}
