package ru.demanin.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrier {
    private long id;
    private String companyName;
    private String phoneNumber;
}
