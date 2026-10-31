package com.bunli.ticket.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.bunli.common.entity.BaseEntity;
import com.bunli.ticket.enumz.TicketStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tt_ticket")
public class Ticket extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_status", nullable = false)
    private TicketStatus ticketStatus;

    @Column(name = "seat_number", nullable = false)
    private String seatNumber;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    @Column(name = "ticket_type", nullable = false)
    private TicketType ticketType;

    @Column(name = "locked_until")
    private LocalDateTime lockedUntil;

    @Column(name = "locked_by")
    private String lockedBy;
}
