package ru.demanin.Authorization.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;
import java.util.Date;
/**
 * Утилита для работы с JWT (JSON Web Tokens).
 * <p>
 * Обеспечивает функциональность для генерации и валидации JWT-токенов,
 * используемых для аутентификации пользователей в системе.
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Генерация JWT-токенов с указанным сроком действия</li>
 *   <li>Валидация токенов и извлечение данных пользователя</li>
 *   <li>Подпись токенов с использованием секретного ключа</li>
 * </ul>
 *
 * @see <a href="https://jwt.io/introduction/">Официальная документация JWT</a>
 */
@Component
public class JWTUtil {

    @Value("${jwt_secret}")
    private String secret;
    /**
     * Генерирует JWT-токен для указанного email пользователя.
     * <p>
     * Токен содержит следующие claims:
     * <ul>
     *   <li>subject: "User details" (фиксированное значение)</li>
     *   <li>login: email пользователя</li>
     *   <li>issuer: "Client-service" (фиксированное значение)</li>
     *   <li>дату выдачи (issuedAt)</li>
     *   <li>срок действия - 60 минут от момента генерации</li>
     * </ul>
     * </p>
     *
     * @param email email пользователя, который будет включен в токен
     * @return сгенерированный JWT-токен в виде строки
     * @throws IllegalArgumentException если email равен null или пустой строке
     */

    public String generateToken(String email) {
        Date expirationDate = Date.from(ZonedDateTime.now().plusMinutes(60).toInstant());

        return JWT.create()
                .withSubject("User details")
                .withClaim("login", email)
                .withIssuedAt(new Date())
                .withIssuer("Client-service")
                .withExpiresAt(expirationDate)
                .sign(Algorithm.HMAC256(secret));
    }
    /**
     * Валидирует JWT-токен и извлекает из него login пользователя.
     * <p>
     * Проверяет:
     * <ul>
     *   <li>Подпись токена с использованием секретного ключа</li>
     *   <li>Соответствие subject и issuer ожидаемым значениям</li>
     *   <li>Срок действия токена</li>
     * </ul>
     * </p>
     *
     * @param token JWT-токен для валидации
     * @return login пользователя, извлеченный из токена
     * @throws JWTVerificationException если токен невалиден (просрочен, некорректная подпись и т.д.)
     * @throws IllegalArgumentException если токен равен null или пустой строке
     */
    public String validateTokenAndRetrieveClaim(String token) throws JWTVerificationException {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                .withSubject("User details")
                .withIssuer("Client-service")
                .build();

        DecodedJWT jwt = verifier.verify(token);
        return jwt.getClaim("login").asString();
    }
}
