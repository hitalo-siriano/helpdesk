package com.api.sistema.helpdesk.dtos;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record TicketRecordDtos(@NotNull UUID ticketId,@NotBlank @Size(min = 5, max = 255)String title, @NotNull  @Size(min = 15, max = 255)String description,@NotBlank @Size(min = 5, max = 255) String local , @NotNull @Digits(integer = 10, fraction = 1) Long contact ) {

}
