package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.time.LocalDate;
import java.util.List;

/**
 * Service for working with horses.
 */
public interface HorseService {
    /**
     * Lists all horses stored in the system.
     *
     * @return list of all stored horses
     */
    List<Horse> allHorses();

    /**
     * Validates horse and forwards it for saving in database.
     *
     * @param horseDto the horse to be validated and saved
     * @return generated ID
     */
    Long save(HorseDto horseDto);

    /**
     * Validates parameters of horseDto and checks if horse with "horseId" exists.
     * Forwards it to persistence layer for updating
     *
     * @param horseId ID of horse to be updated
     * @param horseDto new data for horse
     * @return updated horse
     */
    Horse update(Long horseId, HorseDto horseDto);

    /**
     * Get horse with the given id.
     *
     * @param id id of horse to be fetched.
     * @return horse with id.
     */
    Horse getOneById(Long id);


    /**
     * Delete horse with id in database
     *
     * @param id id of horse to be deleted.
     */
    void delete(Long id);

    /**
     * todo add doc
     * @param dateOfBirth
     * @param parentSex
     * @param searchString
     * @return
     */
    List<Horse> searchParent(LocalDate dateOfBirth, Sex parentSex, String searchString);

    /**
     * todo add doc
     * @param id
     * @return
     */
    List<Horse> getAllChildren(Long id);

    /**
     * todo add doc
     * @param horseSearchDto
     * @return
     */
    List<Horse> searchHorse(HorseSearchDto horseSearchDto);
}
