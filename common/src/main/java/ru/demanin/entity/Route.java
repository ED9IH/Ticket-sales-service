package ru.demanin.entity;

import lombok.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Route {
    private long id;
    private String departurePoint;
    private String destinationPoint;
    private int durationInMinutes;
}

