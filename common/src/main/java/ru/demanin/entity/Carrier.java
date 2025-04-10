package ru.demanin.entity;

import lombok.*;

import javax.validation.constraints.NotEmpty;

/**
 * Entity class representing a transportation carrier.
 * <p>
 * This class models a carrier company that provides transportation services
 * in the system. It contains basic information about the carrier.
 * </p>
 *
 * <p>Main attributes:</p>
 * <ul>
 *   <li><b>id</b> - unique identifier for the carrier</li>
 *   <li><b>companyName</b> - legal name of the carrier company</li>
 *   <li><b>phoneNumber</b> - contact phone number for the carrier</li>
 * </ul>
 *
 * <p>Uses Lombok annotations for boilerplate code generation:</p>
 * <ul>
 *   <li>{@link Getter} - generates getter methods for all fields</li>
 *   <li>{@link Setter} - generates setter methods for all fields</li>
 *   <li>{@link NoArgsConstructor} - generates a no-argument constructor</li>
 *   <li>{@link AllArgsConstructor} - generates a constructor with all fields</li>
 * </ul>
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Carrier {
    private long id;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String companyName;
    @NotEmpty(message = "Поле не должно быть пустым")
    private String phoneNumber;
}
