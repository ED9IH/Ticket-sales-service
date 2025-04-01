package ru.demanin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.demanin.entity.Person;

@Repository
public class PersonRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Long savePerson(Person person) throws RuntimeException {
        String checkSql = "SELECT COUNT(*) FROM person WHERE login = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, person.getLogin());
        if (count > 0) {
            throw new RuntimeException("Пользователь с логином " + person.getLogin() + " уже существует");
        }
        String insertSql = "INSERT INTO person (name, full_name, surname, login, password) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";
        return jdbcTemplate.queryForObject(
                insertSql,
                Long.class,
                person.getName(),
                person.getFullName(),
                person.getSurname(),
                person.getLogin(),
                person.getPassword()
        );
    }

    public Person findByLogin(String login) {
        String checkLogin = "SELECT * FROM person where login = ?";
        Person person = jdbcTemplate.queryForObject(checkLogin, Person.class, login);

        return person;
    }


}
