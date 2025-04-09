package ru.demanin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.demanin.entity.Client;

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


}
