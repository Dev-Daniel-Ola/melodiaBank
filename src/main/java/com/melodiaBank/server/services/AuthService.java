package com.melodiaBank.server.services;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.melodiaBank.server.dtos.requests.LoginRequestDTO;
import com.melodiaBank.server.dtos.requests.RegisterRequestDTO;
import com.melodiaBank.server.dtos.responses.AuthResponseDTO;
import com.melodiaBank.server.exceptions.EmailAlreadyExistException;
import com.melodiaBank.server.exceptions.UserNotFoundException;
import com.melodiaBank.server.models.User;
import com.melodiaBank.server.repositories.UserRepository;
import static com.melodiaBank.server.utils.Mapper.mapToResponseDTO;


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

        return mapToResponseDTO(savedUser);
    }

    public AuthResponseDTO login(LoginRequestDTO request){

        User existingUser = userRepository.findByEmail(request.getEmail());

        if (existingUser == null){
            throw new UserNotFoundException("Invalid email or password");
        }

        if (!existingUser.getPassword().equals(request.getPassword())){
            LOGGER.error("Login request failed for: " + request.getEmail());
            throw new IllegalArgumentException("Invalid email or password");
        }

        return mapToResponseDTO(existingUser);
    }
}
