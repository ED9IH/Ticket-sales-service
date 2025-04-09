package ru.demanin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demanin.dto.RouteDTO;
import ru.demanin.mapper.RouteMapper;
import ru.demanin.entity.Route;
import ru.demanin.repository.RouteRepository;

@Service
public class RouteService {

    private final RouteRepository routeRepository;
    private final RouteMapper routeMapper;

    @Autowired
    public RouteService(RouteRepository routeRepository, RouteMapper routeMapper) {
        this.routeRepository = routeRepository;
        this.routeMapper = routeMapper;
    }


    public Route createRoute(String departure_point, String destination_point, int duration_in_minutes){
        Route route = routeMapper.toEntity(new RouteDTO(departure_point,destination_point,duration_in_minutes));
       return routeRepository.createRoute(route);
    }
}
