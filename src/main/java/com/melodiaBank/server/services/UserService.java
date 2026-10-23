package com.melodiaBank.server.services;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.melodiaBank.server.dtos.responses.UserResponseDTO;
import com.melodiaBank.server.exceptions.UserNotFoundException;
import com.melodiaBank.server.models.User;
import com.melodiaBank.server.repositories.UserRepository;


@Service 
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepo){
        this.userRepository = userRepo;
    }

    public List<UserResponseDTO> getUsers() {
        
        List<User> users = userRepository.findAll();

        List<UserResponseDTO> response = users.stream()
        .map((user) -> {
            UserResponseDTO responseDTO = new UserResponseDTO();
            responseDTO.setId(user.getId());
            responseDTO.setFirstName(user.getFirstName());
            responseDTO.setLastName(user.getLastName());
            responseDTO.setEmail(user.getEmail());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss a");

            responseDTO.setCreatedAt(user.getCreatedAt().format(formatter));

            return responseDTO;

        }).toList();

        return response;
    }

    public String deleteUser(Long userId){

        if(!userRepository.existsById(userId)){
            throw new UserNotFoundException("User does not exist");
        }
        
        userRepository.deleteById(userId);
    
        return "User deleted successfully...!";
    }

}
