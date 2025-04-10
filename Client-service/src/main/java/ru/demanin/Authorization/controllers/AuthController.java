package ru.demanin.Authorization.controllers;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.demanin.Authorization.security.JWTUtil;
import ru.demanin.Authorization.services.RegistrationService;
import ru.demanin.Authorization.util.PersonValidator;
import ru.demanin.dto.PersonAuthDTO;
import ru.demanin.dto.PersonDTO;
import ru.demanin.entity.Client;
import ru.demanin.mapper.CreateClientMapper;
import ru.demanin.repository.ClientRepository;
import ru.demanin.util.Role;

import java.util.Map;
/**
 * Контроллер для обработки аутентификации и регистрации пользователей.
 * <p>
 * Предоставляет REST-эндпоинты для:
 * <ul>
 *   <li>Регистрации новых пользователей</li>
 *   <li>Аутентификации существующих пользователей</li>
 *   <li>Генерации JWT-токенов</li>
 * </ul>
 * </p>
 *
 * <p>Все методы возвращают JWT-токен для последующей аутентификации.</p>
 *
 * @see RegistrationService
 * @see JWTUtil
 * @see AuthenticationManager
 */
@RestController
@RequestMapping("/auth")
@Api(value = "Person Controller", description = "Операции с клиентами")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CreateClientMapper createClientMapper;
    /**
     * Конструктор контроллера.
     *
     * @param registrationService сервис регистрации пользователей
     * @param personValidator валидатор данных пользователя
     * @param jwtUtil утилита для работы с JWT-токенами
     * @param authenticationManager менеджер аутентификации Spring Security
     * @param createClientMapper маппер для преобразования DTO в сущность Client
     */
    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator,
                          JWTUtil jwtUtil, AuthenticationManager authenticationManager, CreateClientMapper createClientMapper) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.createClientMapper = createClientMapper;
    }
    /**
     * Регистрирует нового пользователя в системе.
     * <p>
     * При успешной регистрации возвращает JWT-токен для аутентификации.
     * </p>
     *
     * @param name имя пользователя
     * @param fullName фамилия пользователя
     * @param surname отчество пользователя
     * @param login уникальный логин пользователя
     * @param password пароль пользователя
     * @param role роль пользователя (ADMINISTRATOR, CLIENT и т.д.)
     * @return ResponseEntity с JWT-токеном
     * @see Role
     */
    @PostMapping("/registration")
    @ApiOperation(value = "Регистрация клиента")
    public ResponseEntity<Map<String, String>> addClient(@ApiParam(value = "Введите имя") @RequestParam String name,
                                                         @ApiParam(value = "Введите фамилию") @RequestParam String fullName,
                                                         @ApiParam(value = "Введите отчество") @RequestParam String surname,
                                                         @ApiParam(value = "Введите логин") @RequestParam String login,
                                                         @ApiParam(value = "Введите пароль") @RequestParam String password,
                                                         @ApiParam(value = "Укажите роль") @RequestParam Role role) {
        Client client = createClientMapper.toEntity(new PersonDTO(name, fullName, surname, login, password,role));
        registrationService.register(client);
        String token = jwtUtil.generateToken(client.getLogin());
        return ResponseEntity.ok(Map.of("jwt-token", token));
    }
    /**
     * Аутентифицирует пользователя и возвращает JWT-токен.
     * <p>
     * Проверяет учетные данные пользователя и при успешной проверке
     * возвращает новый JWT-токен.
     * </p>
     *
     * @param login логин пользователя
     * @param password пароль пользователя
     * @return Map с JWT-токеном или сообщением об ошибке
     * @throws BadCredentialsException если предоставлены неверные учетные данные
     */
    @PostMapping("/login")
    @ApiOperation(
            value = "Обновление срока действия токена"
    )
    public Map<String, String> performLogin(@ApiParam(value = "Введите логин") @RequestParam String login,
                                            @ApiParam(value = "Введите пароль") @RequestParam String password
                                            ) {

        PersonAuthDTO personAuthDTO = new PersonAuthDTO(login, password);
        UsernamePasswordAuthenticationToken authInputToken =
                new UsernamePasswordAuthenticationToken(personAuthDTO.getLogin(),
                        personAuthDTO.getPassword());
        try {
            authenticationManager.authenticate(authInputToken);
        } catch (BadCredentialsException e) {
            return Map.of("message", "Incorrect credentials!");
        }
        String token = jwtUtil.generateToken(personAuthDTO.getLogin());
        return Map.of("jwt-token", token);
    }
}
