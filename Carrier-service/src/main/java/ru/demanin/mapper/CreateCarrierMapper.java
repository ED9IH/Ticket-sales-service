package ru.demanin.mapper;

import org.mapstruct.Mapper;
import ru.demanin.entity.Carrier;
import ru.demanin.dto.CarrierDTO;

/**
 * MapStrackt Автоматически создает DTO для сущности.
 */
@Mapper(componentModel = "spring")
public interface CreateCarrierMapper {
    CarrierDTO toDTO(Carrier carrier);
    Carrier toEntity(CarrierDTO carrierDTO);
}
