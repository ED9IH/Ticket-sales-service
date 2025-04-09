package ru.demanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RouteDTO {
    private String departurePoint;
    private String destinationPoint;
    private int durationInMinutes;
}
