package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Data Transfer Object (DTO) для представления информации о перевозчике.
 * <p>
 * Используется для передачи данных о перевозчике между слоями приложения
 * без раскрытия внутренней структуры сущности.
 * </p>
 *
 * <p>Содержит следующие поля:</p>
 * <ul>
 *   <li><b>companyName</b> - название компании перевозчика</li>
 *   <li><b>phoneNumber</b> - контактный телефон перевозчика</li>
 * </ul>
 *
 * <p>Оснащен аннотациями Lombok для автоматической генерации:</p>
 * <ul>
 *   <li>Геттеров для всех полей</li>
 *   <li>Сеттеров для всех полей</li>
 *   <li>Конструктора со всеми аргументами</li>
 * </ul>
 *
 * @see Getter
 * @see Setter
 * @see AllArgsConstructor
 */
@Getter
@Setter
@AllArgsConstructor
public class CarrierDTO {
    /**
     * Название компании перевозчика.
     * <p>
     * Не может быть null или пустым.
     * </p>
     */
    private String companyName;
    /**
     * Контактный телефон перевозчика.
     * <p>
     * Должен соответствовать формату телефонных номеров.
     * </p>
     */
    private String phoneNumber;
}
