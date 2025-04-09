package ru.demanin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.demanin.dto.GetAllTicketDTO;
import ru.demanin.entity.Ticket;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface GetAllTicketMapper {

    @Mapping(source = "id_route.departurePoint", target = "departure_point")
    @Mapping(source = "id_route.destinationPoint", target = "destination_point")
    @Mapping(source = "id_route.durationInMinutes", target = "duration_in_minutes")
    @Mapping(source = "carrier_id.companyName", target = "carrier_name")
    @Mapping(source = "carrier_id.phoneNumber", target = "phone_Number")
    GetAllTicketDTO toDTO(Ticket ticket);

    Ticket toEntity(GetAllTicketDTO getAllTicketDTO);

    default List<GetAllTicketDTO> toDTOAllTicket(List<Ticket> tasks) {
        return tasks.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
