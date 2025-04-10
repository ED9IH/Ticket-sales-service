package ru.demanin.Authorization.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.demanin.entity.Client;

import java.util.Collection;
import java.util.Collections;
/**
 * Реализация интерфейса UserDetails для работы с аутентификацией Spring Security.
 * <p>
 * Обертка для сущности {@link Client}, предоставляющая необходимые методы
 * для интеграции с Spring Security.
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Предоставление данных пользователя для аутентификации</li>
 *   <li>Управление ролями/правами пользователя</li>
 *   <li>Контроль состояния учетной записи</li>
 * </ul>
 */
public class PersonDetails implements UserDetails {
    private final Client client;
    /**
     * Создает новый экземпляр PersonDetails для указанного клиента.
     *
     * @param client объект клиента, для которого создаются детали аутентификации
     * @throws IllegalArgumentException если client равен null
     */
    public PersonDetails(Client client) {
        this.client = client;
    }
    /**
     * Возвращает права/роли пользователя.
     * <p>
     * В текущей реализации возвращает список с одной ролью "ROLE_".
     * Для рабочей системы следует использовать реальные роли из объекта Client.
     * </p>
     *
     * @return коллекция объектов GrantedAuthority
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_"));
    }
    /**
     * Возвращает пароль пользователя.
     *
     * @return пароль пользователя в зашифрованном виде
     */
    @Override
    public String getPassword() {
        return this.client.getPassword();
    }
    /**
     * Возвращает логин пользователя.
     *
     * @return логин (имя пользователя) для аутентификации
     */
    @Override
    public String getUsername() {
        return this.client.getLogin();
    }
    /**
     * Указывает, не истек ли срок действия учетной записи.
     * <p>
     * В текущей реализации всегда возвращает true (срок не истек).
     * </p>
     *
     * @return true если учетная запись действительна, false если срок истек
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    /**
     * Указывает, не заблокирована ли учетная запись.
     * <p>
     * В текущей реализации всегда возвращает true (учетная запись не заблокирована).
     * </p>
     *
     * @return true если учетная запись не заблокирована, false если заблокирована
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * Указывает, не истекли ли учетные данные (пароль).
     * <p>
     * В текущей реализации всегда возвращает true (учетные данные действительны).
     * </p>
     *
     * @return true если учетные данные действительны, false если истекли
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * Указывает, включена ли (активна) учетная запись.
     * <p>
     * В текущей реализации всегда возвращает true (учетная запись активна).
     * </p>
     *
     * @return true если учетная запись включена, false если отключена
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
    /**
     * Возвращает объект клиента, связанный с этими учетными данными.
     *
     * @return объект {@link Client}, связанный с этими учетными данными
     */
    public Client getUser() {
        return this.client;
    }
}
