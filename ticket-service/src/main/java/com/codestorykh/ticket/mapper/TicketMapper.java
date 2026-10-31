package com.bunli.ticket.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.bunli.ticket.dto.TicketRequest;
import com.bunli.ticket.dto.TicketResponse;
import com.bunli.ticket.entity.Ticket;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    Ticket toEntity(TicketRequest request);

    TicketResponse toResponse(Ticket ticket);
}
