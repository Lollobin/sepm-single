package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ParentSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.util.List;

/**
 * Service for working with horses.
 */
public interface HorseService {

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
     * @param horseId  ID of horse to be updated
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
     * Returns possible parents for horse.
     *
     * @param parentSearchDto parameters to search parents
     * @return possible parents
     */
    List<Horse> searchParent(ParentSearchDto parentSearchDto);

    /**
     * Returns all children of horse with id.
     *
     * @param id id of parent
     * @return children of horse
     */
    List<Horse> getAllChildren(Long id);

    /**
     * Returns all horses that match the search parameters.
     *
     * @param horseSearchDto parameters to search horse (name, description, date of birth, sex, name of owner)
     * @return all matching horses
     */
    List<Horse> searchHorse(HorseSearchDto horseSearchDto);
}
