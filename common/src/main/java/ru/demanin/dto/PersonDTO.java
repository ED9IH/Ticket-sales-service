package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.demanin.util.Role;
/**
 * Data Transfer Object (DTO) для передачи данных о пользователе.
 * <p>
 * Используется для регистрации новых пользователей и обновления их данных.
 * Содержит полную информацию о пользователе, включая учетные данные и роль.
 * </p>
 *
 * <p>Содержит следующие поля:</p>
 * <ul>
 *   <li><b>name</b> - имя пользователя</li>
 *   <li><b>fullName</b> - фамилия пользователя</li>
 *   <li><b>surname</b> - отчество пользователя</li>
 *   <li><b>login</b> - уникальный идентификатор для входа в систему</li>
 *   <li><b>password</b> - пароль пользователя</li>
 *   <li><b>role</b> - роль пользователя в системе</li>
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
public class PersonDTO {
    private String name;
    private String fullName;
    private String surname;
    private String login;
    private String password;
    private Role role;
}
