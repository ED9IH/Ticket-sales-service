package ru.demanin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.demanin.util.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
/**
 * Класс-сущность, представляющий клиента/пользователя системы.
 * <p>
 * Содержит персональные данные, учетные данные и роль пользователя.
 * Включает аннотации валидации для обязательных полей.
 * </p>
 *
 * <p>Основные атрибуты:</p>
 * <ul>
 *   <li><b>id</b> - уникальный идентификатор клиента</li>
 *   <li><b>name</b> - имя клиента</li>
 *   <li><b>fullName</b> - фамилия клиента</li>
 *   <li><b>surname</b> - отчество клиента</li>
 *   <li><b>login</b> - логин для входа в систему</li>
 *   <li><b>password</b> - пароль пользователя</li>
 *   <li><b>role</b> - роль пользователя в системе</li>
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
public class Client {
    private Long id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String name;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String fullName;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String surname;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String login;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String password;
    @NotEmpty(message = "Поле не должно быть пустым")
    private Role role;
}
