package com.melodiaBank.server.services;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.melodiaBank.server.dtos.requests.LoginRequestDTO;
import com.melodiaBank.server.dtos.requests.RegisterRequestDTO;
import com.melodiaBank.server.dtos.responses.AuthResponseDTO;
import com.melodiaBank.server.models.User;
import com.melodiaBank.server.repositories.UserRepository;
import com.melodiaBank.server.exceptions.EmailAlreadyExistException;


@Service
public class AuthService {

    private final Logger LOGGER = LoggerFactory.getLogger(AuthService.class);
    private final UserRepository userRepository;

    AuthService(UserRepository userRepo){
        this.userRepository = userRepo;
    }

    public AuthResponseDTO registerUser(RegisterRequestDTO request){

        boolean emailExist = userRepository.existByEmail(request.getEmail().toLowerCase());
        if (emailExist){
            LOGGER.error("Email already exist");
            throw new EmailAlreadyExistException("Email already exist");
        }
        
        User user = new User();
        user.setFirstName(request.getFirstName().toUpperCase());
        user.setLastName(request.getLastName().toUpperCase());
        user.setEmail(request.getEmail().toLowerCase());
        user.setPassword(request.getPassword());

        User savedUser = userRepository.save(user);

        AuthResponseDTO response = new AuthResponseDTO();
        response.setId(savedUser.getId());
        response.setFirstName(savedUser.getFirstName());
        response.setLastName(savedUser.getLastName());
        response.setEmail(savedUser.getEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss a");

        response.setCreatedAt(savedUser.getCreatedAt().format(formatter));
        
        return response;
    }

    public AuthResponseDTO login(LoginRequestDTO request){

        User existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser == null){
            throw new IllegalArgumentException("Invalid email or password");
        }

        if (existingUser.getPassword() == request.getPassword()){
            throw new IllegalArgumentException("Invalid email or password");
        }

        AuthResponseDTO response = new AuthResponseDTO();
        response.setId(existingUser.getId());
        response.setFirstName(existingUser.getFirstName());
        response.setLastName(existingUser.getLastName());
        response.setEmail(existingUser.getEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss a");

        response.setCreatedAt(existingUser.getCreatedAt().format(formatter));

        return response;
    }
}
