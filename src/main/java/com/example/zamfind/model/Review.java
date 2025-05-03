package com.example.zamfind.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews")
public class Review {
    @Id
    @JsonIgnore
    private String id = UUID.randomUUID().toString();
    private String businessId;
    private String userId;
    private String content;
    private double rating;
    private LocalDateTime createdAt;
}
