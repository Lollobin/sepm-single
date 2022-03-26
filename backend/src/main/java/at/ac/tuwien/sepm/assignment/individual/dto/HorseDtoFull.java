package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Class for Horse DTOs.
 * Contains all common properties.
 * Owner and Parents are contained as DTOs
 */
public record HorseDtoFull(Long id,
                           String name,
                           String description,
                           @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                           Sex sex,
                           Owner owner,
                           HorseDto father,
                           HorseDto mother) {
}
