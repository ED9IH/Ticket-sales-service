package ru.demanin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.demanin.dto.TicketDTO;
import ru.demanin.entity.Ticket;

@Mapper(componentModel = "spring",uses = {RouteMapper.class, CreateTicketMapper.class})
public interface CreateTicketMapper {
    @Mapping(source = "id_route.id",target = "id_route")
    @Mapping(source = "carrier_id.id",target = "carrier_id")
    TicketDTO toDTO(Ticket ticket);
    @Mapping(source = "id_route",target = "id_route.id")
    @Mapping(source = "carrier_id",target = "carrier_id.id")
    Ticket toEntity(TicketDTO ticketDTO);
}
