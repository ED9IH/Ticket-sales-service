package ru.demanin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.demanin.dto.GetAllTicketDTO;
import ru.demanin.dto.TicketDTO;
import ru.demanin.entity.Ticket;
import ru.demanin.mapper.CreateTicketMapper;
import ru.demanin.mapper.GetAllTicketMapper;
import ru.demanin.repository.RouteRepository;
import ru.demanin.repository.TicketRepository;
import ru.demanin.status.StatusTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final CreateTicketMapper createTicketMapper;
    private final GetAllTicketMapper getAllTicketMapper;
    private final RouteRepository routeRepository;

    @Autowired
    public TicketService(TicketRepository ticketRepository, CreateTicketMapper createTicketMapper, GetAllTicketMapper getAllTicketMapper, RouteRepository routeRepository) {
        this.ticketRepository = ticketRepository;
        this.createTicketMapper = createTicketMapper;
        this.getAllTicketMapper = getAllTicketMapper;
        this.routeRepository = routeRepository;
    }

    public Ticket createTicket(long id_route, long carrier_id, int seat_number, BigDecimal price, LocalDateTime departure) {
        Ticket ticket = createTicketMapper.toEntity(new TicketDTO(id_route, carrier_id, LocalDateTime.now(), seat_number, price, StatusTicket.FREE, departure));
        return ticketRepository.createTicket(ticket);
    }

    public List<GetAllTicketDTO> getAllTicketFree() {
        List<Ticket> ticket = ticketRepository.getAllFreeTicket();
        return getAllTicketMapper.toDTOAllTicket(ticket);
    }
}
