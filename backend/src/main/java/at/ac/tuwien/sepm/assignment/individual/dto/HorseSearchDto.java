package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;


/**
 * Class for horse search parameters.
 */
public record HorseSearchDto(String name,
                             String description,
                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
                             Sex sex,
                             String owner) {
}
