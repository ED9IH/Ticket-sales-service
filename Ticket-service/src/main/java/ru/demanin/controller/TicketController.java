package ru.demanin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.demanin.dto.GetAllTicketDTO;
import ru.demanin.entity.Ticket;
import ru.demanin.exception.ErrorResponse;
import ru.demanin.exception.InvalidRequestException;
import ru.demanin.exception.TicketNotFoundException;
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
    @ApiOperation("Создание билета")
    public ResponseEntity<ResponseTicket> addTicket(@ApiParam(value = "Введите id маршрута") @RequestParam long id_route,
                                                    @ApiParam(value = "Введите номер места") @RequestParam int seat_number,
                                                    @ApiParam(value = "Введите цену") @RequestParam BigDecimal price,
                                                    @RequestParam @DateTimeFormat(pattern = "MM-dd-yyyy HH:mm")
                                                    @ApiParam(value = "Введите дату и время отправления", example = "00-00-0000 00:00") LocalDateTime departure,
                                                    @ApiParam(value = "Введите id компании") @RequestParam long carrier_id) {
        ticketService.createTicket(id_route, carrier_id, seat_number, price, departure);
        return ResponseEntity.ok(new ResponseTicket("Билет создан"));
    }

    @GetMapping("/freeTicket")
    @ApiOperation("Свободные билеты")
    public List<GetAllTicketDTO> getAllFreeTicket() {
        return ticketService.getAllTicketFree();
    }

    @GetMapping("/getTicketsByDateAndTime")
    @ApiOperation("Все билеты c фильтрации по дате отправления")
    public ResponseEntity<List<Ticket>> getTicketsByDateAndTime(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        if (ticketService.getTicketsByDateAndTime(page, size) == null) {
            throw new InvalidRequestException("Билеты не найдены");
        }
        return ResponseEntity.ok(ticketService.getTicketsByDateAndTime(page, size));
    }

    @GetMapping("/getTicketsByDeparture")
    @ApiOperation("Все билеты фильтрации по пункту назначения и отправления")
    public ResponseEntity<List<Ticket>> getTicketsByDeparture(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @ApiParam("Точка отправления") @RequestParam String departurePoint,
            @ApiParam("Пункт назначения") @RequestParam String destinationPoint) {

        if (departurePoint == null || departurePoint.isEmpty()) {
            throw new InvalidRequestException("Поле 'departurePoint' не может быть пустым");
        }

        if (destinationPoint == null || destinationPoint.isEmpty()) {
            throw new InvalidRequestException("Поле 'destinationPoint' не может быть пустым");
        }
        List<Ticket> tickets = ticketService.getTicketsByDeparture(
                page, size,
                departurePoint.trim(),
                destinationPoint.trim());

        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("Билеты по заданным критериям не найдены");
        }

        return ResponseEntity.ok(tickets);
    }

    @GetMapping("/getTicketsByCarrier")
    @ApiOperation("Все билеты фильтрации по имени перевозчика")
    public ResponseEntity<List<Ticket>> getTicketsByCarrier(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @ApiParam("Имя перевозчика") @RequestParam String carrier) {

        if (carrier == null || carrier.isEmpty()) {
            throw new InvalidRequestException("Поле 'Имя перевозчика' не может быть пустым");
        }
        List<Ticket> tickets = ticketService.getTicketsByCarrier(
                page, size,
                carrier.trim());
        if (tickets.isEmpty()) {
            throw new TicketNotFoundException("Билеты по заданным критериям не найдены");
        }

        return ResponseEntity.ok(tickets);
    }

    @PostMapping("/reservedTicket")
    @ApiOperation("бронирование билетов")
    public ResponseEntity<ResponseTicket> reservedTicket(@ApiParam("Укажите id билета") @RequestParam long id) {
        ticketService.payTicket(id);
        return ResponseEntity.ok(new ResponseTicket("Билет зарезирвирован"));
    }


    @ExceptionHandler(InvalidRequestException.class)
    public ResponseEntity<ErrorResponse> handleInvalidRequest(InvalidRequestException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TicketNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTicketNotFound(TicketNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                ex.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                "Внутренняя ошибка сервера",
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
