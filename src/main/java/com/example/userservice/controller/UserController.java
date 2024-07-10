package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.AuthenticationRequest;
import com.example.userservice.service.AuthenticationResponse;
import com.example.userservice.service.RegistrationRequest;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication")
public class UserController {

    private final UserService service;

    @PostMapping("/auth/register")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/auth/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/auth/activate-account")
    public void confirm(
            @RequestParam String token
    ) throws MessagingException {
        service.activateAccount(token);
    }

    @GetMapping("/email")
    public ResponseEntity<User> getUserByEmail(
            @RequestParam String email
    ) {
        User user = service.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @GetMapping("/update")
    public ResponseEntity<User> updateUser(
            @RequestBody User user
    ) {
        User updateUser = service.updateUser(user);
        return ResponseEntity.ok(updateUser);
    }
}
