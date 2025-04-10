package ru.demanin.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * DTO для возврата стандартизированных ответов API при операциях с билетами.
 * <p>
 * Используется для унификации формата ответов сервера при выполнении операций
 * с билетами ({@link ru.demanin.entity.Ticket}).
 * </p>
 *
 * <p>Основное назначение:</p>
 * <ul>
 *   <li>Возврат статуса операций с билетами (бронирование, создание, удаление)</li>
 *   <li>Передача сообщений об ошибках</li>
 *   <li>Подтверждение успешного выполнения операций</li>
 * </ul>
*/
@Getter
@Setter
@AllArgsConstructor
public class ResponseTicket {
    private String message;
}
