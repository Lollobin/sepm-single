package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * Class for parent horse search parameters.
 */
public record ParentSearchDto(
        @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateOfBirth,
        Sex parentSex,
        String searchString) {
}
