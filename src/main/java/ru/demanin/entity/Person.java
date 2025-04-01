package ru.demanin.entity;

import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Person {
    private Long id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String name;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String fullName;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String surname;
    @NotEmpty(message = "Поле не должно быть пустым")
    @Email
    private String login;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String password;
    private List<Ticket> tickets;
}
