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
public class GetAllTicketDTO {
    private Long id;
    private String departure_point;
    private String destination_point;
    private int duration_in_minutes;
    private String carrier_name;
    private String phone_Number;
    private LocalDateTime data_of_creation;
    private int seatNumber;
    private BigDecimal price;
    private StatusTicket statusTicket;
    private LocalDateTime departure;
}
