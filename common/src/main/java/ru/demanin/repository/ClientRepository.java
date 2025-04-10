package ru.demanin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.demanin.entity.Client;
import ru.demanin.entity.Ticket;
import ru.demanin.status.StatusTicket;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class ClientRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ClientRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long savePerson(Client client) throws RuntimeException {
        String checkSql = "SELECT COUNT(*) FROM person WHERE login = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, client.getLogin());
        if (count > 0) {
            throw new RuntimeException("Пользователь с логином " + client.getLogin() + " уже существует");
        }
        String insertSql = "INSERT INTO person (name, full_name, surname, login, password) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(
                insertSql,
                Long.class,
                client.getName(),
                client.getFullName(),
                client.getSurname(),
                client.getLogin(),
                client.getPassword()
        );
    }

    public Client findByLogin(String login) {
        String checkLogin = "SELECT * FROM person where login = ?";
        Client client = jdbcTemplate.queryForObject(checkLogin, Client.class, login);
        return client;
    }

    public List<Ticket> getTicketsByDateAndTime(long id) {
        // 1. Сначала получаем ID билетов, связанных с клиентом
        String ticketIdsSql = "SELECT ticket_id FROM person WHERE id = ?";
        List<Long> ticketIds = jdbcTemplate.queryForList(ticketIdsSql, Long.class, id);
        // 2. Затем получаем полную информацию о билетах
        String ticketsSql = "SELECT * FROM ticket WHERE id IN (" +
                String.join(",", Collections.nCopies(ticketIds.size(), "?")) + ")";

        List<Ticket> tickets = jdbcTemplate.query(
                ticketsSql,
                (rs, rowNum) -> {
                    Ticket ticket = new Ticket();
                    ticket.setId(rs.getLong("id"));
                    ticket.setSeatNumber(rs.getInt("seat_number"));
                    ticket.setPrice(rs.getBigDecimal("price"));
                    ticket.setDeparture(rs.getTimestamp("departure_time").toLocalDateTime());
                    ticket.setStatusTicket(StatusTicket.valueOf(rs.getString("status")));
                    return ticket;
                },
                ticketIds.toArray());
        return tickets.stream()
                .sorted(Comparator.comparing(Ticket::getDeparture))
                .collect(Collectors.toList());
    }


}
