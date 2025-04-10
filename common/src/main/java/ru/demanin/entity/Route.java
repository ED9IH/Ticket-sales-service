package ru.demanin.entity;

import lombok.*;

import javax.validation.constraints.NotEmpty;
/**
 * Класс-сущность, представляющий маршрут перевозки.
 * <p>
 * Содержит информацию о пунктах отправления и назначения,
 * а также продолжительности маршрута.
 * </p>
 *
 * <p>Основные атрибуты:</p>
 * <ul>
 *   <li><b>id</b> - уникальный идентификатор маршрута</li>
 *   <li><b>departurePoint</b> - пункт отправления</li>
 *   <li><b>destinationPoint</b> - пункт назначения</li>
 *   <li><b>durationInMinutes</b> - продолжительность маршрута в минутах</li>
 * </ul>
 *
 * <p>Использует аннотации Lombok для генерации кода:</p>
 * <ul>
 *   <li>{@link Getter} - генерирует геттеры для всех полей</li>
 *   <li>{@link Setter} - генерирует сеттеры для всех полей</li>
 *   <li>{@link NoArgsConstructor} - генерирует конструктор без аргументов</li>
 *   <li>{@link AllArgsConstructor} - генерирует конструктор со всеми полями</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private long id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String departurePoint;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String destinationPoint;
    @NotEmpty(message = "Поле не должно быть пустым")
    private int durationInMinutes;
}

