package ru.demanin.mapper;

import org.mapstruct.Mapper;
import ru.demanin.entity.Carrier;
import ru.demanin.dto.CarrierDTO;

@Mapper(componentModel = "spring")
public interface CreateCarrierMapper {
    CarrierDTO toDTO(Carrier carrier);
    Carrier toEntity(CarrierDTO carrierDTO);
}
