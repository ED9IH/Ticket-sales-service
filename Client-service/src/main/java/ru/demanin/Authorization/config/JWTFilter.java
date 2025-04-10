package ru.demanin.Authorization.config;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.demanin.Authorization.security.JWTUtil;
import ru.demanin.Authorization.services.PersonDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
/**
 * JWT-фильтр для аутентификации на основе JWT-токенов.
 * <p>
 * Этот фильтр перехватывает входящие запросы и проверяет наличие валидного JWT-токена
 * в заголовке Authorization. При успешной проверке токена устанавливается аутентификация
 * в контексте безопасности Spring.
 * </p>
 *
 * <p>Работа фильтра:</p>
 * <ol>
 *   <li>Проверяет наличие заголовка Authorization с префиксом "Bearer "</li>
 *   <li>Извлекает JWT-токен из заголовка</li>
 *   <li>Валидирует токен с помощью {@link JWTUtil}</li>
 *   <li>Загружает данные пользователя через {@link PersonDetailsService}</li>
 *   <li>Устанавливает аутентификацию в {@link SecurityContextHolder}</li>
 * </ol>
 *
 * @see OncePerRequestFilter
 * @see JWTUtil
 * @see PersonDetailsService
 */
@Component
public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final PersonDetailsService personDetailsService;
    /**
     * Конструктор фильтра.
     *
     * @param jwtUtil утилита для работы с JWT-токенами
     * @param personDetailsService сервис для загрузки данных пользователя
     */
    public JWTFilter(JWTUtil jwtUtil, PersonDetailsService personDetailsService) {
        this.jwtUtil = jwtUtil;
        this.personDetailsService = personDetailsService;
    }
    /**
     * Основной метод фильтрации запросов.
     * <p>
     * Обрабатывает каждый входящий HTTP-запрос, проверяет JWT-токен
     * и устанавливает аутентификацию при успешной проверке.
     * </p>
     *
     * @param httpServletRequest входящий HTTP-запрос
     * @param httpServletResponse HTTP-ответ
     * @param filterChain цепочка фильтров
     * @throws ServletException если возникает ошибка сервлета
     * @throws IOException если возникает ошибка ввода/вывода
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = httpServletRequest.getHeader("Authorization");

        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);

            if (jwt.isBlank()) {
                httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                        "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    String username = jwtUtil.validateTokenAndRetrieveClaim(jwt);
                    UserDetails userDetails = personDetailsService.loadUserByUsername(username);

                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(userDetails,
                                    userDetails.getPassword(),
                                    userDetails.getAuthorities());

                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException exc) {
                    httpServletResponse.sendError(HttpServletResponse.SC_BAD_REQUEST,
                            "Invalid JWT Token");
                }
            }
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
