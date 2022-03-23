package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

/**
 * Class for Horse DTOs including the parents as DTOs
 * Contains all common properties
 */
public record HorseDtoParents(Long id, String name, String description, java.sql.Date dateOfBirth, Sex sex,
                              Long ownerId, HorseDto father, HorseDto mother) {
}
