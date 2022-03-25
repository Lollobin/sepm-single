package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;

import java.util.List;

public interface OwnerService {

    /**
     * Lists all owners in the system.
     *
     * @return list of all stored owners
     */
    List<Owner> allOwners();


    /**
     * Validates ownerDto and saves it.
     *
     * @param ownerDto owner data to be validated and stored
     * @return owner entity with generated id
     */
    Owner save(OwnerDto ownerDto);
}
