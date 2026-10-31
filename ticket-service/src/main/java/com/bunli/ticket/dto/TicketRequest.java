package com.bunli.ticket.dto;

import java.math.BigDecimal;

import com.bunli.ticket.entity.TicketType;
import com.bunli.ticket.enumz.TicketStatus;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketRequest {

    @NotNull(message = "Event id cannot be null")
    private Long eventId;

    @NotNull(message = "Seat number cannot be null")
    private String seatNumber;

    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than zero")
    private BigDecimal price;

    private TicketType ticketType;

    private TicketStatus ticketStatus;
}
