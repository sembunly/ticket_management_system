package com.bunli.ticket.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bunli.ticket.entity.Ticket;
import com.bunli.ticket.enumz.TicketStatus;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findAllByEventId(Long eventId);

    List<Ticket> findAllByEventIdAndTicketStatus(Long eventId, TicketStatus status);

    Optional<Ticket> findFirstBySeatNumberAndEventId(String seatNumber, Long eventId);
}
