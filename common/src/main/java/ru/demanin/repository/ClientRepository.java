package ru.demanin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.demanin.entity.Client;
/**
 * Репозиторий для работы с клиентами (пользователями) в базе данных.
 * <p>
 * Обеспечивает основные операции для работы с сущностью {@link Client}.
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Сохранение нового клиента в системе</li>
 *   <li>Поиск клиента по логину</li>
 * </ul>
 */
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
        String insertSql = "INSERT INTO person (name, full_name, surname, login, password, role) " +
                "VALUES (?, ?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(
                insertSql,
                Long.class,
                client.getName(),
                client.getFullName(),
                client.getSurname(),
                client.getLogin(),
                client.getPassword(),
                String.valueOf(client.getRole())

        );
    }

    public Client findByLogin(String login) {
        String sql = "SELECT * FROM person WHERE login = ?";

            return jdbcTemplate.queryForObject(sql, new Object[]{login}, (rs, rowNum) -> {
                Client client = new Client();
                client.setId(rs.getLong("id"));
                client.setLogin(rs.getString("login"));
                client.setPassword(rs.getString("password"));
                client.setName(rs.getString("name"));
                client.setFullName(rs.getString("full_name"));
                return client;
            });
    }

}
