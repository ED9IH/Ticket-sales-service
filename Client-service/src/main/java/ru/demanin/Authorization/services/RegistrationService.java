package ru.demanin.Authorization.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.demanin.entity.Client;
import ru.demanin.repository.ClientRepository;
/**
 * Сервис для регистрации новых пользователей в системе.
 * <p>
 * Обеспечивает безопасное сохранение данных пользователя,
 * включая шифрование паролей перед сохранением в базу данных.
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Шифрование паролей пользователей</li>
 *   <li>Сохранение данных пользователя в базу данных</li>
 *   <li>Интеграция с системой безопасности Spring</li>
 * </ul>
 *
 * @see PasswordEncoder
 * @see ClientRepository
 */

@Service
public class RegistrationService {

    private final ClientRepository clientRepository;
    private final PasswordEncoder passwordEncoder;
    /**
     * Конструктор сервиса регистрации.
     *
     * @param clientRepository репозиторий для работы с данными клиентов
     * @param passwordEncoder кодировщик паролей для безопасного хранения
     */
    @Autowired
    public RegistrationService(ClientRepository clientRepository, PasswordEncoder passwordEncoder) {
        this.clientRepository = clientRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Регистрирует нового пользователя в системе.
     * <p>
     * Выполняет следующие действия:
     * <ol>
     *   <li>Шифрует пароль пользователя</li>
     *   <li>Сохраняет данные пользователя в базу данных</li>
     * </ol>
     * </p>
     *
     * @param client объект клиента для регистрации
     * @throws IllegalArgumentException если client равен null
     * @throws org.springframework.dao.DataAccessException при ошибках доступа к данным
     */
    public void register(Client client) {
        client.setPassword(passwordEncoder.encode(client.getPassword()));
        clientRepository.savePerson(client);
    }
}
