package com.example.zamfind.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDTO {

    private String token;
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String expiration;
    private UUID refreshToken;
    private Set<String> roles;
}
