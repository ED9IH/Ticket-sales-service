package ru.demanin.mapper;

import org.mapstruct.Mapper;
import ru.demanin.dto.PersonDTO;
import ru.demanin.entity.Client;

@Mapper(componentModel = "spring")
public interface CreateClientMapper {
    PersonDTO toDTO(Client client);
    Client toEntity(PersonDTO personDTO);
}
