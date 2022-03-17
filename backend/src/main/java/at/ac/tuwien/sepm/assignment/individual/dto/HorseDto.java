package at.ac.tuwien.sepm.assignment.individual.dto;

import at.ac.tuwien.sepm.assignment.individual.enums.Gender;

/**
 * Class for Horse DTOs
 * Contains all common properties
 */
public record HorseDto(Long id, String name, String description, java.sql.Date dateOfBirth, Gender gender, Long ownerId) {

    public HorseDto withID(Long id){
        return new HorseDto(id, name(), description(), dateOfBirth(), gender(), ownerId());
    }
}
