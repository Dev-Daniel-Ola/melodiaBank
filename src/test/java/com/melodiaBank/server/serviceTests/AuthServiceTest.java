package com.melodiaBank.server.serviceTests;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.melodiaBank.server.dtos.requests.RegisterRequestDTO;
import com.melodiaBank.server.dtos.responses.AuthResponseDTO;
import com.melodiaBank.server.models.User;
import com.melodiaBank.server.repositories.UserRepository;
import com.melodiaBank.server.services.AuthService;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AuthService authService;

    @Test 
    public void testThatUserRegistered_returnAuthResponse(){
        User user = new User();
        user.setId(1L);
        user.setFirstName("DANIEL");
        user.setLastName("OLA");
        user.setEmail("danola@gmail.com");
        user.setCreatedAt(LocalDateTime.now());

        when(userRepository.save(any(User.class))).thenReturn(user);

        RegisterRequestDTO requestDTO = new RegisterRequestDTO();
        requestDTO.setFirstName("DaNiel");
        requestDTO.setLastName("Ola");
        requestDTO.setEmail("DaNolA@gmail.com");
        requestDTO.setPassword("danola");

        var response = authService.registerUser(requestDTO);

        System.out.println(response.getCreatedAt() + "\n" + response.getEmail());

        assertNotNull(response);
        assertEquals("DANIEL", response.getFirstName());
        assertInstanceOf(AuthResponseDTO.class, response);

        verify(userRepository, times(1)).save(any(User.class));
    }
}
