package ru.demanin.Authorization.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.demanin.Authorization.security.PersonDetails;
import ru.demanin.entity.Client;
import ru.demanin.repository.ClientRepository;


import java.util.Optional;
/**
 * Сервис для загрузки данных пользователя в Spring Security.
 * <p>
 * Реализует интерфейс {@link UserDetailsService} для интеграции
 * пользовательских данных из системы с Spring Security.
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Поиск пользователей по логину</li>
 *   <li>Преобразование сущности {@link Client} в {@link UserDetails}</li>
 *   <li>Обработка случаев, когда пользователь не найден</li>
 * </ul>
 *
 * @see UserDetailsService
 * @see PersonDetails
 */
@Service
public class PersonDetailsService implements UserDetailsService {

    private final ClientRepository clientRepository;
    /**
     * Конструктор сервиса.
     *
     * @param clientRepository репозиторий для работы с клиентами
     */
    @Autowired
    public PersonDetailsService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }
    /**
     * Загружает данные пользователя по его логину.
     * <p>
     * Преобразует сущность {@link Client} в {@link PersonDetails},
     * которую может использовать Spring Security для аутентификации.
     * </p>
     *
     * @param username логин пользователя для поиска
     * @return объект {@link UserDetails} с данными пользователя
     * @throws UsernameNotFoundException если пользователь с указанным логином не найден
     * @throws IllegalArgumentException если username равен null или пустой строке
     */
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Client> person = Optional.ofNullable(clientRepository.findByLogin(s));
        if (person.isEmpty())
            throw new UsernameNotFoundException("User not found");
        return new PersonDetails(person.get());
    }
}
