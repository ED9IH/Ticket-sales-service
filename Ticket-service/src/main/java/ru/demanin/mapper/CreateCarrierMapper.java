package ru.demanin.mapper;

import org.mapstruct.Mapper;
import ru.demanin.dto.CarrierDTO;
import ru.demanin.entity.Carrier;

@Mapper(componentModel = "spring")
public interface CreateCarrierMapper {
    CarrierDTO toDTO(Carrier carrier);
    Carrier toEntity(CarrierDTO carrierDTO);
}
