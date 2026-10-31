package com.bunli.ticket.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bunli.ticket.entity.TicketType;
import com.bunli.ticket.enumz.TicketStatus;

import lombok.Data;

@Data
public class TicketResponse {

    private Long id;

    private Long eventId;

    private String seatNumber;

    private BigDecimal price;

    private TicketType ticketType;

    private TicketStatus ticketStatus;

    private LocalDateTime lockedUntil;

    private String lockedBy;

    private LocalDateTime createdAt;

    private String createdBy;

    private LocalDateTime updatedAt;

    private String updatedBy;
}
