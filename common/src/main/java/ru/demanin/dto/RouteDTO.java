package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) для передачи данных о маршруте.
 * <p>
 * Используется для представления информации о маршруте между слоями приложения
 * без раскрытия внутренней структуры сущности.
 * </p>
 *
 * <p>Содержит следующие поля:</p>
 * <ul>
 *   <li><b>departurePoint</b> - пункт отправления маршрута</li>
 *   <li><b>destinationPoint</b> - пункт назначения маршрута</li>
 *   <li><b>durationInMinutes</b> - продолжительность маршрута в минутах</li>
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
public class RouteDTO {
    private String departurePoint;
    private String destinationPoint;
    private int durationInMinutes;
}
