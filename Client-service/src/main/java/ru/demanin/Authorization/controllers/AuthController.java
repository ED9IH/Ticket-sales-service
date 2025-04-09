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

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Api(value = "Person Controller", description = "Операции с клиентами")
public class AuthController {
    private final RegistrationService registrationService;
    private final PersonValidator personValidator;
    private final JWTUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CreateClientMapper createClientMapper;

    @Autowired
    public AuthController(RegistrationService registrationService, PersonValidator personValidator,
                          JWTUtil jwtUtil, AuthenticationManager authenticationManager, CreateClientMapper createClientMapper) {
        this.registrationService = registrationService;
        this.personValidator = personValidator;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.createClientMapper = createClientMapper;
    }
    @PostMapping("/registration")
    @ApiOperation(value = "Регистрация клиента")
    public ResponseEntity<Map<String, String>> addClient(@ApiParam(value = "Введите имя")@RequestParam String name,
                                            @ApiParam(value = "Введите фамилию")@RequestParam String fullName,
                                            @ApiParam(value = "Введите отчество")@RequestParam String surname,
                                            @ApiParam(value = "Введите логин")@RequestParam String login,
                                            @ApiParam(value = "Введите пароль")@RequestParam String password){
         Client client =createClientMapper.toEntity(new PersonDTO(name,fullName,surname,login,password));
                 registrationService.register(client);
        String token = jwtUtil.generateToken(client.getLogin());
        return ResponseEntity.ok(Map.of("jwt-token", token));
    }

//    @PostMapping("/registration")
//    public Map<String, String> performRegistration(@RequestBody @Valid PersonAuthDTO personAuthDTO,
//                                                   BindingResult bindingResult) {
//        Person person = convertToPerson(personAuthDTO);
//
//        personValidator.validate(person, bindingResult);
//
//        if (bindingResult.hasErrors()) {
//            return Map.of("message", "Ошибка!");
//        }
//
//        registrationService.register(person);
//
//        String token = jwtUtil.generateToken(person.getLogin());
//        return Map.of("jwt-token", token);
//    }

    @PostMapping("/login")
    @ApiOperation(
            value = "Обновление срока действия токена"
    )
    public Map<String, String> performLogin(@ApiParam(value = "Введите логин")@RequestParam String login,
                                            @ApiParam(value = "Введите пароль")@RequestParam String password) {
        PersonAuthDTO personAuthDTO = new PersonAuthDTO(login,password);
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
