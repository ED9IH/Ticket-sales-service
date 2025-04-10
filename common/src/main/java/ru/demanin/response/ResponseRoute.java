package ru.demanin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * Класс для стандартизированного ответа API при операциях с маршрутами.
 * <p>
 * Используется для возврата унифицированных ответов от сервера при выполнении операций
 * с сущностями маршрутов ({@link ru.demanin.entity.Route}).
 * </p>
 *
 * <p>Содержит:</p>
 * <ul>
 *   <li><b>message</b> - текстовое сообщение о результате выполнения операции</li>
 * </ul>
 *
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseRoute {
    private String message;
}
