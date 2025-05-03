package com.example.zamfind.services.serviceImpl;
import com.example.zamfind.Dto.UserInfoResponseDTO;
import com.example.zamfind.Dto.repository.UserRepository;
import com.example.zamfind.Dto.userdto.AuthenticationRequest;
import com.example.zamfind.Dto.userdto.AuthenticationResponse;
import com.example.zamfind.Dto.userdto.RegisterRequest;
import com.example.zamfind.config.JWTService;
import com.example.zamfind.model.Role.Role;
import com.example.zamfind.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final EmailService emailService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        Optional<User> existingUser = userRepository.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + request.getEmail());
        }

        // Generate a verification token
        String verificationToken = UUID.randomUUID().toString();

        // Create a new user with verification token
        User user = User.builder()
                .firstname(request.getFirstname())
                .lastname(request.getLastname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole() != null ? Role.valueOf(request.getRole()) : Role.CUSTOMER)

                .verificationToken(verificationToken)
                .isVerified(false) // Default to false
                .build();

        // Save the user
        userRepository.save(user);

        // Send verification email
        emailService.sendVerificationEmail(user.getEmail(), verificationToken);

        return AuthenticationResponse.builder()
                .message("User registered. Check your email for verification.")
                .build();
    }

    public String verifyEmail(String token) {
        User user = userRepository.findByVerificationToken(token)
                .orElseThrow(() -> new IllegalArgumentException("Invalid verification token"));

        user.setVerified(true);
        user.setVerificationToken(null); // Remove the token after verification
        userRepository.save(user);

        return "Email verified successfully!";
    }


    public UserInfoResponseDTO authenticate(AuthenticationRequest request) {
        // Step 1: Validate credentials (throws BadCredentialsException if invalid)
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Step 2: If authentication succeeds, generate JWT
        var user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String token = jwtService.generateToken(user);

        return UserInfoResponseDTO.builder()
                .token(token)
                .userId(user.getId())
                .firstName(user.getFirstname())
                .lastName(user.getLastname())
                .email(user.getEmail())
                .expiration(jwtService.extractExpiration(token).toString())
                .roles(Set.of(user.getRole().name()))
                .build();
    }
}
