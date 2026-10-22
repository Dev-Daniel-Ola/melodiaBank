package com.melodiaBank.server.controllerTests;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melodiaBank.server.controllers.AuthController;
import com.melodiaBank.server.dtos.requests.RegisterRequestDTO;
import com.melodiaBank.server.dtos.responses.AuthResponseDTO;
import com.melodiaBank.server.services.AuthService;
import static org.mockito.ArgumentMatchers.any;


@WebMvcTest(AuthController.class)
public class AuthControllerTest {

    @Autowired 
    private MockMvc mockMvc;

    @MockitoBean 
    private AuthService authService;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Test
    public void testThatUserRegister_return_200_ok() throws Exception{

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setFirstName("Daniel");
        request.setLastName("Daniel");
        request.setEmail("ola@gmail.com");
        request.setPassword("ola@gmail.com");


        AuthResponseDTO response = new AuthResponseDTO();
        response.setId(1L);
        response.setFirstName("DANIEL");
        response.setLastName("DANIEL");
        response.setEmail("ola@gmail.com");
        response.setCreatedAt(LocalDateTime.now().toString());

        when(authService.registerUser(any(RegisterRequestDTO.class))).thenReturn(response);

        mockMvc.perform(post("/api/auth/register")
        .contentType(MediaType.APPLICATION_JSON)
        .accept(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request)))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.id").exists())
        .andExpect(jsonPath("$.firstName").value("DANIEL"));
    }
}
