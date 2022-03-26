package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;

import java.time.LocalDate;
import java.util.List;

/**
 * Data Access Object for horses.
 * Implements access functionality to the application's persistent data store regarding horses.
 */
public interface HorseDao {
    /**
     * Get all horses stored in the persistent data store.
     *
     * @return a list of all stored horses
     */
    List<Horse> getAll();

    /**
     * Save a single horse in the persistent data store.
     *
     * @param horseDto data transfer object of horse to be saved
     * @return generated ID
     */
    Long save(HorseDto horseDto);

    /**
     * Update horse with ID 'horseId' in persistent data store to match 'horseDto'.
     *
     * @param horseId ID of horse to be updated
     * @param horseDto data to update horse with
     * @return horse with updated parameters
     */
  Horse update(Long horseId, HorseDto horseDto);

    /**
     * Get horse with the given id from the persistent data store.
     *
     * @param id id of the horse to return
     * @return horse with the given id
     */
  Horse getOneById(Long id);

    /**
     * Delete horse with id from the persistent data store.
     *
     * @param id id of horse to be deleted
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
