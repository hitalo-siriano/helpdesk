package com.api.sistema.helpdesk.dtos;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateTicketAtenteDtos(@NotNull UUID ticketId,@Size( max = 255)String message) {
    
}
