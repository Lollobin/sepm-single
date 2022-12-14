package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;

import java.util.List;

/**
 * Data Access Object for owners.
 * Implements access functionality to the application's persistent data store regarding owners.
 */
public interface OwnerDao {
    /**
     * Get all owners stored in the persistent data store.
     *
     * @return a list of all stored owners
     */
    List<Owner> getAll();

    /**
     * Save a single owner in the persistent data store.
     *
     * @param ownerDto data transfer object of owner to be saved
     * @return corresponding entity with generated ID
     */
    Owner save(OwnerDto ownerDto);

    /**
     * Returns all possible owners.
     * Matches the string to owner name.
     *
     * @param ownerSearchDto string to match to owner name
     * @return possible owner matches
     */
    List<Owner> searchOwner(OwnerSearchDto ownerSearchDto);

    /**
     * Get owner with the given id from the persistent data store.
     *
     * @param id id of the owner to return
     * @return owner with the given id
     */
    Owner getOneById(Long id);

}
