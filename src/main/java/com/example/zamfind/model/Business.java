package com.example.zamfind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Data
@Document(collection = "businesses")
public class Business {
    @Id

    private String id = UUID.randomUUID().toString();
    private String userId;
    private String name;
    private String address;
    private String profilePhoto;
    private String description;
    private List<String> categories;
    private String location;
    private String number;
//    private double latitude;
//    private double longitude;
    private List<String> images;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
