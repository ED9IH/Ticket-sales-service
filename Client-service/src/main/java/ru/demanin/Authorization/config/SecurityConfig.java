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


@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final PersonDetailsService personDetailsService;
    private final JWTFilter jwtFilter;
    private final JWTUtil jwtUtil;

    @Autowired
    public SecurityConfig(PersonDetailsService personDetailsService, JWTFilter jwtFilter, JWTUtil jwtUtil) {
        this.personDetailsService = personDetailsService;
        this.jwtFilter = jwtFilter;
        this.jwtUtil = jwtUtil;
    }

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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(personDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
