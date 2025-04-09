package ru.demanin.mapper;

import org.mapstruct.Mapper;
import ru.demanin.dto.RouteDTO;
import ru.demanin.entity.Route;

@Mapper(componentModel = "spring")
public interface RouteMapper {
    RouteDTO toDTO(Route route);
    Route toEntity(RouteDTO routeDTO);
}
