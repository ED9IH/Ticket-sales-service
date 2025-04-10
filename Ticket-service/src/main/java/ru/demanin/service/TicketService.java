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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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


    public List<Ticket> getTicketsByDateAndTime(int page, int size) {
        List<Ticket> allTickets = ticketRepository.getAllFreeTicket().stream()
                .sorted(Comparator.comparing(Ticket::getDeparture))
                .collect(Collectors.toList());
        return paginateList(allTickets, page, size);
    }

    public List<Ticket> getTicketsByDeparture(int page, int size, String departurePoint, String destinationPoint) {
        List<Ticket> filteredTickets = ticketRepository.getAllFreeTicket().stream()
                .filter(ticket -> containsIgnoreCase(ticket.getId_route().getDeparturePoint(), departurePoint))
                .filter(ticket -> containsIgnoreCase(ticket.getId_route().getDestinationPoint(), destinationPoint))
                .sorted(Comparator.comparing(Ticket::getDeparture))
                .collect(Collectors.toList());
        return paginateList(filteredTickets, page, size);
    }

    public List<Ticket> getTicketsByCarrier(int page, int size, String carrier) {

        List<Ticket> filteredTickets = ticketRepository.getAllFreeTicket().stream()
                .filter(ticket -> containsIgnoreCase(ticket.getCarrier_id().getCompanyName(), carrier))
                .collect(Collectors.toList());
        return paginateList(filteredTickets, page, size);
    }

    public Long payTicket(long id) {
      return ticketRepository.reservationTicket(id);
    }

    private boolean containsIgnoreCase(String source, String search) {
        if (search == null || search.isEmpty()) {
            return true;
        }
        return source != null && source.toLowerCase().contains(search.toLowerCase());
    }

    private List<Ticket> paginateList(List<Ticket> items, int page, int size) {
        if (size <= 0 || page < 0) {
            throw new IllegalArgumentException("Invalid page or size");
        }
        int fromIndex = page * size;
        if (fromIndex >= items.size()) {
            return Collections.emptyList();
        }
        int toIndex = Math.min(fromIndex + size, items.size());
        return items.subList(fromIndex, toIndex);
    }
}
