package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.demanin.util.StatusTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Data Transfer Object (DTO) representing ticket information.
 * <p>
 * This class is used to transfer ticket data between different layers of the application
 * while abstracting the underlying entity structure.
 * </p>
 *
 * <p>Contains the following fields:</p>
 * <ul>
 *   <li><b>id_route</b> - identifier of the associated route</li>
 *   <li><b>carrier_id</b> - identifier of the carrier</li>
 *   <li><b>data_of_creation</b> - ticket creation timestamp</li>
 *   <li><b>seatNumber</b> - assigned seat number</li>
 *   <li><b>price</b> - ticket price</li>
 *   <li><b>statusTicket</b> - current status of the ticket</li>
 *   <li><b>departure</b> - scheduled departure time</li>
 * </ul>
 *
 * <p>Uses Lombok annotations for automatic generation of:</p>
 * <ul>
 *   <li>{@link Getter} - getter methods for all fields</li>
 *   <li>{@link Setter} - setter methods for all fields</li>
 *   <li>{@link AllArgsConstructor} - constructor with all arguments</li>
 * </ul>
 */
@Getter
@Setter
@AllArgsConstructor
public class TicketDTO {
    private Long id_route;
    private Long carrier_id;
    private LocalDateTime data_of_creation;
    private int seatNumber;
    private BigDecimal price;
    private StatusTicket statusTicket;
    private LocalDateTime departure;

}
