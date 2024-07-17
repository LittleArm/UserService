package com.example.userservice.controllers;

import com.example.userservice.dtos.UserDTO;
import com.example.userservice.dtos.AuthenticationRequest;
import com.example.userservice.dtos.AuthenticationResponse;
import com.example.userservice.dtos.RegistrationRequest;
import com.example.userservice.repositories.UserRepository;
import com.example.userservice.services.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestHeader;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
@Tag(name = "UserService")
public class UserController {

    private final UserService service;
    private final UserRepository userRepository;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<Void> register(
            @RequestBody @Valid RegistrationRequest request
    ) throws MessagingException {
        service.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody @Valid AuthenticationRequest request
    ) {
        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/activate")
    public ResponseEntity<Void> confirm(
            @RequestParam String token
    ) throws MessagingException {
        service.activateAccount(token);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserDTO> getUserByEmail(
            @PathVariable String email
    ) {
        UserDTO userDTO = service.getUserByEmail(email);
        return ResponseEntity.ok(userDTO);
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUser(
            @RequestBody UserDTO userDTO
    ) {
        UserDTO updateUser = service.updateUser(userDTO);
        return ResponseEntity.ok(updateUser);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable String email,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorizationHeader
    ) {
        service.deleteUserByEmail(email);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
