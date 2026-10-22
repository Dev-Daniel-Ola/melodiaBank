package com.melodiaBank.server.services;

import com.melodiaBank.server.dtos.requests.RegisterRequestDTO;
import com.melodiaBank.server.dtos.responses.AuthResponseDTO;
import com.melodiaBank.server.models.User;
import com.melodiaBank.server.repositories.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepo;

    @InjectMocks
    private AuthService authService;

    @Test
    public void testThatUserRegister_success() {
        
        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setEmail("vivian@gmail.com");
        requestDTO.setFirstName("vivian");
        requestDTO.setLastName("dan");
        requestDTO.setPassword("vivian");

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setEmail("vivian@gmail.com");
        savedUser.setFirstName("VIVIAN");
        savedUser.setLastName("DAN");
        savedUser.setCreatedAt(LocalDateTime.now());

        when(userRepo.save(any(User.class))).thenReturn(savedUser);

        AuthResponseDTO response = authService.registerUser(requestDTO);

        System.out.println(response.getCreatedAt());

        assertNotNull(response);
        assertEquals("VIVIAN", response.getFirstName());
        assertEquals("vivian@gmail.com", response.getEmail());

        verify(userRepo, times(1)).save(any(User.class));
    }
}