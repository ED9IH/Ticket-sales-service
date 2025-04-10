package ru.demanin.response;

import lombok.*;
/**
 * Класс-обертка для возврата ответов API, связанных с операциями над перевозчиками.
 * <p>
 * Используется для стандартизации формата ответов от сервера
 * при выполнении операций с сущностью {@link ru.demanin.entity.Carrier}.
 * </p>
 *
 * <p>Основное поле:</p>
 * <ul>
 *   <li><b>message</b> - текстовое сообщение о результате операции</li>
 * </ul>
 *
 * <p>Оснащен аннотациями Lombok для автоматической генерации:</p>
 * <ul>
 *   <li>{@link Getter} - геттеры для всех полей</li>
 *   <li>{@link Setter} - сеттеры для всех полей</li>
 *   <li>{@link AllArgsConstructor} - конструктор со всеми аргументами</li>
 * </ul>
 *
 * <p>Пример использования:</p>
 * <pre>
 * {@code
 * return new ResponseCarrier("Перевозчик успешно создан");
 * }
 * </pre>
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseCarrier {
    private String message;
}
