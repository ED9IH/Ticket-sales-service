package ru.demanin.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;
/**
 * Конфигурация Swagger для документации REST API.
 * <p>
 * Настраивает интеграцию Swagger UI с Spring Boot приложением,
 * включая поддержку JWT-аутентификации в документации.
 * </p>
 *
 * <p>Основные функции конфигурации:</p>
 * <ul>
 *   <li>Активация Swagger 2</li>
 *   <li>Настройка базового Docket для сканирования API</li>
 *   <li>Добавление поддержки JWT-авторизации</li>
 *   <li>Конфигурация SecurityContext для Swagger</li>
 * </ul>
 *
 * @see Docket
 * @see ApiKey
 * @see SecurityContext
 */
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.any()) // Укажите пакет с контроллерами
                .paths(PathSelectors.ant("/**"))
                .build()
                .securitySchemes(Collections.singletonList(apiKey())) // Добавляем поддержку JWT
                .securityContexts(Collections.singletonList(securityContext())); // Настраиваем SecurityContext
    }
    /**
     * Создает и настраивает основной Docket bean для Swagger.
     * <p>
     * Конфигурация включает:
     * <ul>
     *   <li>Сканирование всех контроллеров</li>
     *   <li>Фильтрацию по всем путям</li>
     *   <li>Добавление поддержки JWT-авторизации</li>
     * </ul>
     * </p>
     *
     * @return настроенный экземпляр Docket
     */
    private ApiKey apiKey() {
        return new ApiKey("JWT", "Authorization", "header"); // Название, имя заголовка, место (header)
    }
    /**
     * Создает конфигурацию API Key для JWT-авторизации.
     * <p>
     * Настраивает передачу JWT-токена в заголовке Authorization.
     * </p>
     *
     * @return конфигурация ApiKey для Swagger
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .forPaths(PathSelectors.any()) // Применять ко всем путям
                .build();
    }
    /**
     * Настраивает SecurityContext для Swagger.
     * <p>
     * Определяет области применения авторизации для всех путей API.
     * </p>
     *
     * @return настроенный SecurityContext
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Collections.singletonList(new SecurityReference("JWT", authorizationScopes));
    }
}
