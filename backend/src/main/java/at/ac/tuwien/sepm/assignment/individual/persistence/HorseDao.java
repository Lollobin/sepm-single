package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ParentSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.util.List;

/**
 * Data Access Object for horses.
 * Implements access functionality to the application's persistent data store regarding horses.
 */
public interface HorseDao {

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
     * @param horseId  ID of horse to be updated
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
     * Returns possible parents for horse.
     *
     * @param parentSearchDto parameters to search parents
     * @return possible parents
     */
    List<Horse> searchParent(ParentSearchDto parentSearchDto);

    /**
     * Returns all children of a horse.
     *
     * @param id id of horse parent horse
     * @return children of the horse
     */
    List<Horse> getAllChildren(Long id);

    /**
     * Returns all horses matching the search parameters
     *
     * @param horseSearchDto search parameters for horses
     * @return matching horses (all horses if horseSearchDto is null)
     */
    List<Horse> searchHorse(HorseSearchDto horseSearchDto);
}
