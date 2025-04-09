package ru.demanin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demanin.dto.CarrierDTO;
import ru.demanin.entity.Carrier;
import ru.demanin.mapper.CreateCarrierMapper;
import ru.demanin.repository.CarrierRepository;

@Service
public class CarrierService {
    private final CarrierRepository carrierRepository;
    private final CreateCarrierMapper createCarrierMapper;

    @Autowired
    public CarrierService(CarrierRepository carrierRepository, CreateCarrierMapper createCarrierMapper) {
        this.carrierRepository = carrierRepository;
        this.createCarrierMapper = createCarrierMapper;
    }

    public Carrier createCarrier(String name, String phoneNumber){
        Carrier carrier=createCarrierMapper.toEntity(new CarrierDTO(name,phoneNumber));
        return carrierRepository.createCarrier(carrier);
    }
}
