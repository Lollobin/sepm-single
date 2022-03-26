package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

/**
 * Class for Horse DTOs including the parents as DTOs
 * Contains all common properties
 */
public record HorseDtoFull(Long id, String name, String description, java.sql.Date dateOfBirth, Sex sex,
                           Owner owner, HorseDto father, HorseDto mother) {
}
