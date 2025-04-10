package ru.demanin.entity;
//Так как в тз небыло описсано для чего конкретно нужна дата и время.
//Я добавил еще одну дату и время в билете для отправления(departure).

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.demanin.util.StatusTicket;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ticket {
    private long id;
    private Route id_route;
    private Carrier carrier_id;
    private LocalDateTime data_of_creation;
    private int seatNumber;
    private BigDecimal price;
    private StatusTicket statusTicket;
    private LocalDateTime departure;

}
