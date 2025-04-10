package ru.demanin.Authorization.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.demanin.Authorization.services.PersonDetailsService;
import ru.demanin.entity.Client;
/**
 * Валидатор для проверки данных пользователя при регистрации.
 * <p>
 * Реализует интерфейс {@link Validator} Spring Framework для проверки
 * уникальности логина пользователя перед регистрацией.
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Проверка поддержки класса для валидации</li>
 *   <li>Валидация уникальности логина пользователя</li>
 *   <li>Интеграция с сервисом {@link PersonDetailsService}</li>
 * </ul>
 *
 * @see Validator
 * @see PersonDetailsService
 */

@Component
public class PersonValidator implements Validator {

    private final PersonDetailsService personDetailsService;

    /**
     * Конструктор валидатора.
     *
     * @param personDetailsService сервис для проверки существующих пользователей
     */
    @Autowired
    public PersonValidator(PersonDetailsService personDetailsService) {
        this.personDetailsService = personDetailsService;
    }

    /**
     * Определяет, поддерживает ли валидатор указанный класс.
     *
     * @param aClass класс для проверки
     * @return true если валидатор поддерживает указанный класс, false в противном случае
     */
    @Override
    public boolean supports(Class<?> aClass) {
        return Client.class.equals(aClass);
    }

    /**
     * Выполняет валидацию объекта клиента.
     * <p>
     * Проверяет, существует ли уже пользователь с указанным логином.
     * Если пользователь найден, добавляет ошибку в объект {@link Errors}.
     * </p>
     *
     * @param o объект для валидации (ожидается {@link Client})
     * @param errors объект для накопления ошибок валидации
     * @throws IllegalArgumentException если переданный объект не является экземпляром {@link Client}
     */
    @Override
    public void validate(Object o, Errors errors) {
        Client client = (Client) o;

        try {
            personDetailsService.loadUserByUsername(client.getLogin());
        } catch (UsernameNotFoundException ignored) {
            return; // все ок, пользователь не найден
        }

        errors.rejectValue("email", "", "Человек с таким именем пользователя уже существует");
    }
}
