package ru.demanin.Authorization.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
public class PersonAuthDTO {
    @NotEmpty(message = "Имя не должно быть пустым")
    @Email
    private String login;
    private String password;
}
