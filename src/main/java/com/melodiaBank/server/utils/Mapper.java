package com.melodiaBank.server.utils;

import java.time.format.DateTimeFormatter;

import com.melodiaBank.server.dtos.responses.AuthResponseDTO;
import com.melodiaBank.server.models.User;

public class Mapper {

    public static AuthResponseDTO mapToResponseDTO(User user){
        AuthResponseDTO response = new AuthResponseDTO();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/mm/yyyy hh:mm:ss a");

        response.setCreatedAt(user.getCreatedAt().format(formatter));

        return response;
    }

}
