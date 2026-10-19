package com.melodiaBank.server.services;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Service;

import com.melodiaBank.server.dtos.requests.RegisterRequestDTO;
import com.melodiaBank.server.dtos.responses.RegisterResponseDTO;
import com.melodiaBank.server.models.User;
import com.melodiaBank.server.repositories.UserRepository;


@Service
public class AuthService {

    private final UserRepository userRepository;

    AuthService(UserRepository userRepo){
        this.userRepository = userRepo;
    }

    public RegisterResponseDTO registerUser(RegisterRequestDTO request){
        
        User user = new User();
        user.setFirstName(request.getFirstName().toUpperCase());
        user.setLastName(request.getLastName().toUpperCase());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);

        RegisterResponseDTO response = new RegisterResponseDTO();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss a");

        response.setCreateAt(savedUser.getCreated().format(formatter));
        
        return response;
    }
}
