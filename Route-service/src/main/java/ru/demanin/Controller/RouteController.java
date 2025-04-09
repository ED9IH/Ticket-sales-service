package ru.demanin.Controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.demanin.response.ResponseRoute;
import ru.demanin.service.RouteService;

@RestController
@RequestMapping("/api/route")
@Api(value = "Route Controller", description = "Операции с иаршрутами")
public class RouteController {
    private final RouteService routeService;

    @Autowired
    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("/create")
    public ResponseEntity<ResponseRoute> createRout(@ApiParam(value = "Введите точку отправления") @RequestParam String departure_point,
                                                    @ApiParam(value = "Введите пункт назначения") @RequestParam String destination_point,
                                                    @ApiParam(value = "Введите время в пути в минутах") @RequestParam int duration_in_minutes) {
        routeService.createRoute(departure_point,destination_point,duration_in_minutes);
        return ResponseEntity.ok(new ResponseRoute("Путь создан"));

    }
}
