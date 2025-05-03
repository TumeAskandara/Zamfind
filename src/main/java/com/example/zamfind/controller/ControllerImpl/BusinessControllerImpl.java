package com.example.zamfind.controller.ControllerImpl;

import com.example.zamfind.Dto.MetaDTO;
import com.example.zamfind.Dto.ResponseDTO;
import com.example.zamfind.controller.ControllerInter.BusinessControllerInter;
import com.example.zamfind.model.Business;
import com.example.zamfind.services.serviceImpl.BusinessServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/business")
@CrossOrigin(origins = "*", maxAge = 3600)
public class BusinessControllerImpl implements BusinessControllerInter {

    private final BusinessServiceImpl businessService;

    @Override
    @PostMapping("/api/createBusiness")
    public ResponseEntity<ResponseDTO> createBusiness(@RequestBody Business business) {
        try {
            Business createdBusiness = businessService.createBusiness(business);
            ResponseDTO response = ResponseDTO.builder()
                    .meta(new MetaDTO(
                            HttpStatus.CREATED.value(),
                            HttpStatus.CREATED.getReasonPhrase(),
                            "Business created successfully"
                    ))
                    .data(createdBusiness)
                    .correlationId(UUID.randomUUID().toString())
                    .transactionId(UUID.randomUUID().toString())
                    .build();

            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    @GetMapping("getBusinessByUserId/{userId}")
    public ResponseEntity<?> getBusinessByUserId(@PathVariable String userId) {
        try {
            List<Business> businesses = businessService.getBusinessByUserId(userId);
            if (businesses.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("No businesses found for the user.");
            }
            return ResponseEntity.ok(businesses);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred: " + e.getMessage());
        }
    }


    @Override
    @GetMapping("/api/getAllBusinesses")
    public ResponseEntity<ResponseDTO> getAllBusiness() {
        try {
            List<Business> businesses = businessService.getAllBusiness();

            MetaDTO meta = MetaDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .statusDescription("All Businesses Successfully Retrieved")
                    .message("")
                    .build();

            ResponseDTO responseDto = ResponseDTO.builder()
                    .meta(meta)
                    .data(businesses)
                    .build();

            return new ResponseEntity(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    @GetMapping("/getBusinessByCategoryAndLocation")
    public ResponseEntity<ResponseDTO> getAllBusinessByCategoriesAndLocation(
            @RequestParam(required = false) String categories,
            @RequestParam(required = false) String location) {
        try {
            // Convert to lowercase BEFORE passing to service
            if (categories != null) {
                categories = categories.toLowerCase();
            }
            if (location != null) {
                location = location.toLowerCase();
            }

            List<Business> businessCategories = businessService.getAllBusinessByCategoriesAndLocation(categories, location);

            if (businessCategories.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            MetaDTO meta = MetaDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .statusDescription("Business successfully retrieved")
                    .message("")
                    .build();

            ResponseDTO responseDto = ResponseDTO.builder()
                    .meta(meta)
                    .data(businessCategories)
                    .build();

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }


    @Override
    @GetMapping("/api/getBusiness/{id}")
    public ResponseEntity<ResponseDTO> getBusiness(@PathVariable String id) {
        try {
            Business business = businessService.getBusiness(id)
                    .orElseThrow(() -> new RuntimeException("Business not found with ID: " + id));

            MetaDTO meta = MetaDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .statusDescription("Business successfully retrieved")
                    .message("")
                    .build();

            ResponseDTO responseDto = ResponseDTO.builder()
                    .meta(meta)
                    .data(business)
                    .build();

            return new ResponseEntity<>(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    @PutMapping("/api/updateBusinesses/{id}")
    public ResponseEntity<ResponseDTO> updateBusiness(@PathVariable String id, @RequestBody Business business) {
        try {
            Business updatedBusiness = businessService.updateBusiness(id, business);

            ResponseDTO response = ResponseDTO.builder()
                    .meta(new MetaDTO(
                            HttpStatus.OK.value(),
                            HttpStatus.OK.getReasonPhrase(),
                            "Business updated successfully"
                    ))
                    .data(updatedBusiness)
                    .correlationId(UUID.randomUUID().toString())
                    .transactionId(UUID.randomUUID().toString())
                    .build();

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    @Override
    @DeleteMapping("/api/deleteBusiness/{id}")
    public ResponseEntity<ResponseDTO> deleteBusiness(@PathVariable String id) {
        try {
            businessService.deleteBusiness(id);

            MetaDTO meta = MetaDTO.builder()
                    .statusCode(HttpStatus.OK.value())
                    .statusDescription("Business successfully deleted")
                    .message("")
                    .build();

            ResponseDTO responseDto = ResponseDTO.builder()
                    .meta(meta)
                    .build();

            return new ResponseEntity(responseDto, HttpStatus.OK);
        } catch (Exception e) {
            return handleException(e);
        }
    }

    // Centralized Exception Handling
    private ResponseEntity<ResponseDTO> handleException(Exception e) {
        MetaDTO meta = MetaDTO.builder()
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusDescription("An error occurred")
                .message(e.getMessage())
                .build();

        ResponseDTO errorResponse = ResponseDTO.builder()
                .meta(meta)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
