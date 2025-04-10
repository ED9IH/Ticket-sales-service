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
//               .antMatchers("/task/all").hasAnyAuthority()
//                .antMatchers("/task/{taskId}").hasAnyAuthority(Role.ADMIN.name(), Role.EXECUTOR.name())
//                .antMatchers("/task/newTask").hasAuthority(Role.ADMIN.name())
//                .antMatchers("/task/editStatus").hasAnyAuthority(Role.ADMIN.name(), Role.EXECUTOR.name())
//                .antMatchers("/task/delete").hasAuthority(Role.ADMIN.name())
//                .antMatchers("/task/editPriority").hasAuthority(Role.ADMIN.name())
//                .antMatchers("/users/allUsers").hasAuthority(Role.ADMIN.name())
//                .antMatchers("/task/addComment").hasAnyAuthority(Role.ADMIN.name(), Role.EXECUTOR.name())
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
