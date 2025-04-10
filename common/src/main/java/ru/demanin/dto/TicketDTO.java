package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import ru.demanin.util.StatusTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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
