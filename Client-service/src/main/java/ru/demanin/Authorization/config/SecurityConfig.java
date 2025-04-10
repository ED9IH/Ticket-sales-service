package ru.demanin.Authorization.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.demanin.Authorization.security.JWTUtil;
import ru.demanin.Authorization.services.PersonDetailsService;
import ru.demanin.util.Role;

/**
 * Основной класс конфигурации безопасности приложения.
 * <p>
 * Настраивает аутентификацию и авторизацию для REST API, включая:
 * <ul>
 *   <li>JWT-аутентификацию</li>
 *   <li>Ролевую модель доступа</li>
 *   <li>Настройки CSRF и сессий</li>
 *   <li>Шифрование паролей</li>
 * </ul>
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Активация веб-безопасности через {@code @EnableWebSecurity}</li>
 *   <li>Включение аннотаций безопасности методов через {@code @EnableGlobalMethodSecurity}</li>
 *   <li>Настройка HTTP-безопасности и правил доступа</li>
 *   <li>Конфигурация менеджера аутентификации</li>
 *   <li>Регистрация JWT-фильтра</li>
 * </ul>
 *
 * @see WebSecurityConfigurerAdapter
 * @see JWTFilter
 * @see PersonDetailsService
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;
    private final JWTUtil jwtUtil;
    /**
     * Конструктор конфигурации безопасности.
     *
     * @param personDetailsService сервис для загрузки данных пользователя
     * @param jwtFilter фильтр для обработки JWT-токенов
     * @param jwtUtil утилита для работы с JWT
     */
    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter, JWTUtil jwtUtil) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
        this.jwtUtil = jwtUtil;
    }
    /**
     * Конфигурирует правила HTTP-безопасности.
     * <p>
     * Устанавливает:
     * <ul>
     *   <li>Отключение CSRF защиты (для REST API)</li>
     *   <li>Правила авторизации для различных эндпоинтов</li>
     *   <li>Политику безсессионной работы (STATELESS)</li>
     *   <li>Регистрацию JWT-фильтра</li>
     * </ul>
     * </p>
     *
     * @param http объект конфигурации HTTP-безопасности
     * @throws Exception если возникает ошибка конфигурации
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/login", "/auth/registration", "/error").permitAll()
                .antMatchers("/swagger-ui.html",
                        "/v2/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**"
                ).permitAll()
                .antMatchers("/api/carrier").hasAnyAuthority()
                .antMatchers("/api/carrier/createCarrier").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name())
                .antMatchers("/api/carrier/deleteCarrier").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name())
                .antMatchers("/api/route").hasAnyAuthority()
                .antMatchers("/api/route/create").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name())
                .antMatchers("/api/route/deleteRoute").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name())
                .antMatchers("/api/ticket").hasAnyAuthority()
                .antMatchers("/api/ticket/create").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name())
                .antMatchers("/api/ticket/freeTicket").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_CLIENT.name())
                .antMatchers("/api/ticket/getTicketsByDateAndTime").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_CLIENT.name())
                .antMatchers("/api/ticket/getTicketsByDeparture").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_CLIENT.name())
                .antMatchers("/api/ticket/getTicketsByCarrier").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_CLIENT.name())
                .antMatchers("/api/ticket/reservedTicket/{ticketId}").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_CLIENT.name())
                .antMatchers("/api/ticket/getAllReservationMyTicket").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_CLIENT.name())
                .antMatchers("/api/ticket/deleteTicket").hasAnyAuthority(Role.ROLE_ADMINISTRATOR.name(),Role.ROLE_CLIENT.name())
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTFilter(jwtUtil, personDetailsService), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
    }
    /**
     * Настраивает аутентификацию пользователей.
     * <p>
     * Устанавливает:
     * <ul>
     *   <li>Сервис для загрузки пользователей</li>
     *   <li>Кодировщик паролей</li>
     * </ul>
     * </p>
     *
     * @param auth построитель конфигурации аутентификации
     * @throws Exception если возникает ошибка конфигурации
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }
    /**
     * Создает и возвращает кодировщик паролей.
     * <p>
     * Используется алгоритм BCrypt для хеширования паролей.
     * </p>
     *
     * @return экземпляр {@link BCryptPasswordEncoder}
     */
    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /**
     * Создает и возвращает менеджер аутентификации.
     * <p>
     * Необходим для процесса аутентификации пользователей.
     * </p>
     *
     * @return менеджер аутентификации
     * @throws Exception если возникает ошибка при создании бина
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
