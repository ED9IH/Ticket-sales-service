package ru.demanin.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.demanin.entity.Carrier;
import ru.demanin.entity.Route;
import ru.demanin.entity.Ticket;
/**
 * Репозиторий для работы с перевозчиками в базе данных.
 * <p>
 * Обеспечивает основные CRUD-операции для сущности {@link Carrier}.
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Создание нового перевозчика</li>
 *   <li>Поиск перевозчика по идентификатору</li>
 *   <li>Удаление перевозчика (с каскадным удалением связанных билетов)</li>
 * </ul>
 */
@Repository
public class CarrierRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CarrierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    public Carrier createCarrier(Carrier carrier) throws RuntimeException {
        String checkSql = "SELECT COUNT(*) FROM carrier WHERE company_name = ?";
        int count = jdbcTemplate.queryForObject(checkSql, Integer.class, carrier.getCompanyName());
        if (count > 0) {
            throw new RuntimeException("Компания с таким именем " + carrier.getCompanyName() + " уже зарегистрирована");
        }
        String insertSql = "INSERT INTO carrier (company_name, phone_number) " +
                "VALUES (?, ?) returning *";
        return jdbcTemplate.queryForObject(
                insertSql,
                (rs, rowNum) -> {
                    Carrier savedCarrier = new Carrier();
                    savedCarrier.setCompanyName(rs.getString("company_name"));
                    savedCarrier.setPhoneNumber(rs.getString("phone_number"));
                    return savedCarrier;
                },
                carrier.getCompanyName(),
                carrier.getPhoneNumber()
        );
    }
    public Carrier findById(long id) {
        String sql = "SELECT * FROM carrier WHERE id = ?";
        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {
                    Carrier carrier = new Carrier();
                    carrier.setId(rs.getLong("id"));
                    carrier.setCompanyName(rs.getString("company_name"));
                    carrier.setPhoneNumber(rs.getString("phone_number"));
                    // Установите другие поля вашего Carrier по необходимости
                    return carrier;
                },
                id
        );
    }

    public void deleteCarrier(long carrierId){
        String deleteTicketSql="DELETE FROM public.ticket WHERE carrier_id = ?";
        jdbcTemplate.update(deleteTicketSql, Ticket.class,carrierId);
        String deleteCarrier="DELETE FROM public.carrier WHERE id = ?";
        jdbcTemplate.update(deleteCarrier, Carrier.class,carrierId);
    }
}
