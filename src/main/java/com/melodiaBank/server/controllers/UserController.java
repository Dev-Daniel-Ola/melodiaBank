package com.melodiaBank.server.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.melodiaBank.server.dtos.responses.UserResponseDTO;
import com.melodiaBank.server.services.UserService;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public List<UserResponseDTO> getUsers() {
        return userService.getUsers();
    }
    
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable String id){

        Long userId = Long.valueOf(id);

        return userService.deleteUser(userId);
    }
}
