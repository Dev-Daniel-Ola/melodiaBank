package com.melodiaBank.server.controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melodiaBank.server.dtos.requests.LoginRequestDTO;
import com.melodiaBank.server.dtos.requests.RegisterRequestDTO;
import com.melodiaBank.server.dtos.responses.AuthResponseDTO;
import com.melodiaBank.server.services.AuthService;

@RestController 
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    AuthController(AuthService authService){
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponseDTO register(@RequestBody RegisterRequestDTO request) {
        return authService.registerUser(request);
    }

    @PostMapping("/login")
    public AuthResponseDTO login(@RequestBody LoginRequestDTO request){
        return authService.login(request);
    }
    
}
