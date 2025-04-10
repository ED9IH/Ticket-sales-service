package ru.demanin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.demanin.entity.Client;
import ru.demanin.entity.Route;
import ru.demanin.entity.Ticket;

@Repository
public class RouteRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RouteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Route createRoute(Route route) throws RuntimeException {

        String insertSql = "INSERT INTO route (departure_point, destination_point, duration_in_minutes) " +
                "VALUES (?, ?, ?) RETURNING *";
        return jdbcTemplate.queryForObject(
                insertSql,(rs, rowNum) -> {
                    Route savedRote = new Route();
                    savedRote.setId(rs.getLong("id"));
                    savedRote.setDeparturePoint(rs.getString("departure_point"));
                    savedRote.setDeparturePoint(rs.getString("destination_point"));
                    savedRote.setDurationInMinutes(rs.getInt("duration_in_minutes"));
                    return savedRote;
                },
                route.getDeparturePoint(),
                route.getDestinationPoint(),
                route.getDurationInMinutes()
        );
    }
    public Route findById(long id) {
        String sql = "SELECT * FROM route WHERE id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    Route route = new Route();
                    route.setId(rs.getLong("id"));
                    route.setDeparturePoint(rs.getString("departure_point"));
                    route.setDestinationPoint(rs.getString("destination_point"));
                    route.setDurationInMinutes(rs.getInt("duration_in_minutes"));
                    // добавьте другие поля по необходимости
                    return route;
                },
                id
        );
    }

    public void deleteRoute(long routeId){
        String deleteSql="DELETE FROM public.route WHERE id = ?;";
        jdbcTemplate.update(deleteSql, Ticket.class,routeId);
    }
}
