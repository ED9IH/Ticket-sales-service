package ru.demanin.mapper;

import org.mapstruct.Mapper;
import ru.demanin.dto.PersonDTO;
import ru.demanin.entity.Person;

@Mapper(componentModel = "spring")
public interface CreateClientMapper {
    PersonDTO toDTO(Person person);
    Person toEntity(PersonDTO personDTO);
}
