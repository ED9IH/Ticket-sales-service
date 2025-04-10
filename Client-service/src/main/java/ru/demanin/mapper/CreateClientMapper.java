package ru.demanin.mapper;

import org.mapstruct.Mapper;
import ru.demanin.dto.PersonDTO;
import ru.demanin.entity.Client;

/**
 * Маппер для преобразования между сущностью {@link Client} и DTO {@link PersonDTO}.
 * <p>
 * Использует MapStruct для автоматической генерации реализации на этапе компиляции.
 * Поддерживает Spring DI через указание componentModel = "spring".
 * </p>
 *
 * <p>Основные функции:</p>
 * <ul>
 *   <li>Преобразование сущности Client в PersonDTO (для передачи данных)</li>
 *   <li>Преобразование PersonDTO в сущность Client (для сохранения данных)</li>
 *   <li>Автоматическое сопоставление полей с одинаковыми именами</li>
 * </ul>
 *
 * @see Mapper
 * @see Client
 * @see PersonDTO
 */
@Mapper(componentModel = "spring")
public interface CreateClientMapper {
    /**
     * Преобразует сущность Client в объект передачи данных PersonDTO.
     *
     * @param client сущность клиента для преобразования
     * @return объект передачи данных PersonDTO
     * @throws IllegalArgumentException если client равен null
     */
    PersonDTO toDTO(Client client);
    /**
     * Преобразует объект передачи данных PersonDTO в сущность Client.
     *
     * @param personDTO объект передачи данных для преобразования
     * @return сущность Client
     * @throws IllegalArgumentException если personDTO равен null
     */
    Client toEntity(PersonDTO personDTO);
}
