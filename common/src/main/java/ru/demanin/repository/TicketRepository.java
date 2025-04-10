package ru.demanin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.demanin.entity.Carrier;
import ru.demanin.entity.Route;
import ru.demanin.entity.Ticket;
import ru.demanin.status.StatusTicket;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TicketRepository {
    private final JdbcTemplate jdbcTemplate;
    private final RouteRepository routeRepository;
    private final CarrierRepository carrierRepository;

    @Autowired
    public TicketRepository(JdbcTemplate jdbcTemplate, RouteRepository routeRepository, CarrierRepository carrierRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.routeRepository = routeRepository;
        this.carrierRepository = carrierRepository;
    }

    public Ticket createTicket(Ticket ticket) throws RuntimeException {
        Route route = routeRepository.findById(ticket.getId_route().getId());
        Carrier carrier = carrierRepository.findById(ticket.getCarrier_id().getId());
        ticket.setData_of_creation(LocalDateTime.now());
        ticket.setStatusTicket(StatusTicket.FREE);
        String insertSql = "INSERT INTO ticket (route_id, carrier_id, data_time, seat_number, price, status, departure_time) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING *";

        return jdbcTemplate.queryForObject(
                insertSql,
                (rs, rowNum) -> {
                    Ticket savedTicket = new Ticket();
                    savedTicket.setId(rs.getLong("id"));
                    savedTicket.setId_route(route);
                    savedTicket.setCarrier_id(carrier);
                    savedTicket.setData_of_creation(rs.getTimestamp("data_time").toLocalDateTime());
                    savedTicket.setSeatNumber(rs.getInt("seat_number"));
                    savedTicket.setPrice(rs.getBigDecimal("price"));
                    String statusStr = rs.getString("status");
                    StatusTicket status = statusStr != null ? StatusTicket.valueOf(statusStr) : StatusTicket.FREE;
                    savedTicket.setStatusTicket(status);
                    savedTicket.setDeparture(rs.getTimestamp("departure_time").toLocalDateTime());
                    return savedTicket;
                },
                route.getId(),
                carrier.getId(),
                LocalDateTime.now(),
                ticket.getSeatNumber(),
                ticket.getPrice(),
                StatusTicket.FREE.name(),
                ticket.getDeparture()
        );
    }

    public List<Ticket> getAllFreeTicket() {
        String sql = "SELECT * FROM ticket WHERE status = ?";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Ticket ticket = new Ticket();
                    ticket.setId(rs.getLong("id"));
                    Long routeId = rs.getLong("route_id");
                    Route route = routeRepository.findById(routeId);
                    ticket.setId_route(route);
                    Long carrierId = rs.getLong("carrier_id");
                    Carrier carrier = carrierRepository.findById(carrierId);
                    ticket.setCarrier_id(carrier);

                    ticket.setData_of_creation(rs.getTimestamp("data_time").toLocalDateTime());
                    ticket.setSeatNumber(rs.getInt("seat_number"));
                    ticket.setPrice(rs.getBigDecimal("price"));
                    ticket.setStatusTicket(StatusTicket.valueOf(rs.getString("status")));

                    Timestamp departureTs = rs.getTimestamp("departure_time");
                    if (departureTs != null) {
                        ticket.setDeparture(departureTs.toLocalDateTime());
                    }

                    return ticket;
                },
                StatusTicket.FREE.name()
        );
    }


    public Long reservationTicket(long ticketId, String personLogin) {
        String statusSql = "SELECT status FROM ticket WHERE id = ?";
        String currentStatus;
        try {
            currentStatus = jdbcTemplate.queryForObject(
                    statusSql,
                    String.class,
                    ticketId);
        } catch (EmptyResultDataAccessException e) {
            throw new IllegalArgumentException("Билет с id " + ticketId + " не найден");
        }

        if ("RESERVATION".equals(currentStatus)) {
            throw new RuntimeException("Билет уже забронирован");
        }

        String updateSql = "UPDATE ticket SET status = 'RESERVATION' WHERE id = ?";
         jdbcTemplate.update(updateSql, ticketId);

        String updatePersonTicketId = "UPDATE person SET ticket_id = ? WHERE login = ?";
         jdbcTemplate.update(updatePersonTicketId, ticketId, personLogin);


        return ticketId;
    }
}
