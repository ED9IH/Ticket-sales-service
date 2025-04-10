package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.demanin.util.StatusTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) для получения полной информации о билете.
 * <p>
 * Используется для передачи комплексных данных о билете между слоями приложения,
 * включая информацию о маршруте и перевозчике.
 * </p>
 *
 * <p>Содержит следующие поля:</p>
 * <ul>
 *   <li><b>id</b> - уникальный идентификатор билета</li>
 *   <li><b>departure_point</b> - пункт отправления</li>
 *   <li><b>destination_point</b> - пункт назначения</li>
 *   <li><b>duration_in_minutes</b> - продолжительность поездки в минутах</li>
 *   <li><b>carrier_name</b> - название компании-перевозчика</li>
 *   <li><b>phone_Number</b> - контактный телефон перевозчика</li>
 *   <li><b>data_of_creation</b> - дата и время создания билета</li>
 *   <li><b>seatNumber</b> - номер места</li>
 *   <li><b>price</b> - стоимость билета</li>
 *   <li><b>statusTicket</b> - текущий статус билета</li>
 *   <li><b>departure</b> - дата и время отправления</li>
 * </ul>
 *
 * <p>Оснащен аннотациями Lombok для автоматической генерации:</p>
 * <ul>
 *   <li>{@link Getter} - геттеры для всех полей</li>
 *   <li>{@link Setter} - сеттеры для всех полей</li>
 *   <li>{@link AllArgsConstructor} - конструктор со всеми аргументами</li>
 * </ul>
 */
@Getter
@Setter
@AllArgsConstructor
public class GetAllTicketDTO {
    private Long id;
    private String departure_point;
    private String destination_point;
    private int duration_in_minutes;
    private String carrier_name;
    private String phone_Number;
    private LocalDateTime data_of_creation;
    private int seatNumber;
    private BigDecimal price;
    private StatusTicket statusTicket;
    private LocalDateTime departure;
}
