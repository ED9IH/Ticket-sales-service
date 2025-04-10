package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.demanin.util.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
/**
 * Data Transfer Object (DTO) для аутентификации пользователя.
 * <p>
 * Используется для передачи учетных данных пользователя при входе в систему.
 * Содержит валидацию полей для обеспечения корректности данных.
 * </p>
 *
 * <p>Содержит следующие поля:</p>
 * <ul>
 *   <li><b>login</b> - email пользователя (используется как идентификатор)</li>
 *   <li><b>password</b> - пароль пользователя</li>
 * </ul>
 *
 * <p>Оснащен аннотациями:</p>
 * <ul>
 *   <li>{@link Getter} - геттеры для всех полей</li>
 *   <li>{@link Setter} - сеттеры для всех полей</li>
 *   <li>{@link AllArgsConstructor} - конструктор со всеми аргументами</li>
 *   <li>Валидационные аннотации для проверки входных данных</li>
 * </ul>
 */
@Getter
@Setter
@AllArgsConstructor
public class PersonAuthDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Email
    private String login;
    private String password;
}
