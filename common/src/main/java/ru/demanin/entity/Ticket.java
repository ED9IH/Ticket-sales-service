package ru.demanin.entity;
//Так как в тз небыло описсано для чего конкретно нужна дата и время.
//Я добавил еще одну дату и время в билете для отправления(departure).

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.demanin.util.StatusTicket;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
/**
 * Класс-сущность, представляющий билет в системе.
 * <p>
 * Содержит информацию о бронировании билета, включая маршрут,
 * перевозчика, места и статус бронирования.
 * </p>
 *
 * <p>Основные атрибуты:</p>
 * <ul>
 *   <li><b>id</b> - уникальный идентификатор билета</li>
 *   <li><b>id_route</b> - маршрут следования</li>
 *   <li><b>carrier_id</b> - перевозчик</li>
 *   <li><b>data_of_creation</b> - дата и время создания билета</li>
 *   <li><b>seatNumber</b> - номер места</li>
 *   <li><b>price</b> - стоимость билета</li>
 *   <li><b>statusTicket</b> - текущий статус билета</li>
 *   <li><b>departure</b> - дата и время отправления</li>
 * </ul>
 *
 * <p>Особенности:</p>
 * <ul>
 *   <li>Включает два временных параметра: создание и отправление</li>
 *   <li>Использует {@link JsonInclude} для исключения null-полей при сериализации</li>
 *   <li>Все поля обязательны для заполнения ({@link NotEmpty})</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket {
    private long id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private Route id_route;
    @NotEmpty(message = "Поле не должно быть пустым")
    private Carrier carrier_id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private LocalDateTime data_of_creation;
    @NotEmpty(message = "Поле не должно быть пустым")
    private int seatNumber;
    @NotEmpty(message = "Поле не должно быть пустым")
    private BigDecimal price;
    @NotEmpty(message = "Поле не должно быть пустым")
    private StatusTicket statusTicket;
    @NotEmpty(message = "Поле не должно быть пустым")
    private LocalDateTime departure;

}
