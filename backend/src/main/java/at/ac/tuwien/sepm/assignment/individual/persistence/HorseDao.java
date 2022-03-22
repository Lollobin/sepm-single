package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

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
     * @return corresponding entity with generated ID
     */
    Horse save(HorseDto horseDto);

    /**
     * Update horse with ID 'horseId' in persistent data store to match 'horseDto'.
     *
     * @param horseId ID of horse to be updated
     * @param horseDto data to update horse with
     * @return horse with updated parameters
     */
  Horse update(Long horseId, HorseDto horseDto);

    /**
     * Get horse with the given id in the persistent data store.
     *
     * @param id ID oh horse to return
     * @return horse with the given id
     */
  Horse getOneById(Long id);
}
