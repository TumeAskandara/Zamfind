package com.example.zamfind.controller.ControllerInter;

import com.example.zamfind.Dto.ResponseDTO;
import com.example.zamfind.model.Business;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface BusinessControllerInter {
    ResponseEntity<ResponseDTO> createBusiness(Business business);

    @GetMapping("getBusinessByUserId/{userId}")
    ResponseEntity<?> getBusinessByUserId(@PathVariable String userId);

    ResponseEntity<ResponseDTO> getAllBusiness();

    @GetMapping("/getBusinessByCategory")
    ResponseEntity<ResponseDTO> getAllBusinessByCategoriesAndLocation(@RequestParam String categories,
                                                           @RequestParam String location);

    ResponseEntity<ResponseDTO> getBusiness(String id);

    ResponseEntity<ResponseDTO> updateBusiness(String id, Business business);

    ResponseEntity<ResponseDTO> deleteBusiness(String id);




}
