package com.bunli.ticket.ticket.service;

import com.bunli.common.exception.ResponseErrorTemplate;
import com.bunli.ticket.dto.TicketLockRequest;
import com.bunli.ticket.dto.TicketRequest;

public interface TicketService {

    ResponseErrorTemplate createTicket(TicketRequest ticketRequest);

    ResponseErrorTemplate getTicketById(Long ticketId);

    ResponseErrorTemplate lockTicket(TicketLockRequest ticketLockRequest);

    void unlockTicket(Long eventId, Integer quantity);

}
