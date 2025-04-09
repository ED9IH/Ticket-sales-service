package ru.demanin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.demanin.dto.GetAllTicketDTO;
import ru.demanin.response.ResponseTicket;
import ru.demanin.service.TicketService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/ticket")
@Api(value = "Ticket Controller", description = "Операции с билетами")
public class TicketController {
    private final TicketService ticketService;

    @Autowired
    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;

    }

    @PostMapping("/create")
    @Operation(description = "Создание билета")
    public ResponseEntity<ResponseTicket> addTicket(@ApiParam(value = "Введите id маршрута") @RequestParam long id_route,
                                                    @ApiParam(value = "Введите номер места") @RequestParam int seat_number,
                                                    @ApiParam(value = "Введите цену") @RequestParam BigDecimal price,
                                                    @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm")
                                                    @ApiParam(value = "Введите дату и время отправления", example = "00-00-0000 00:00") LocalDateTime departure,
                                                    @ApiParam(value = "Введите id компании")@RequestParam long carrier_id) {
        ticketService.createTicket(id_route,carrier_id, seat_number, price, departure);
        return ResponseEntity.ok(new ResponseTicket("Билет создан"));
    }

    @GetMapping("/FreeTicket")
    @Operation(description = "Свободные билеты")
    public List<GetAllTicketDTO> getAllFreeTicket() {
        return ticketService.getAllTicketFree();
    }
}
