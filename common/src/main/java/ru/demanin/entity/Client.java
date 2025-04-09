package ru.demanin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Client {
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
}
