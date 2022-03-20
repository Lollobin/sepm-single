package at.ac.tuwien.sepm.assignment.individual.service;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

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
     * @return horse with generated ID
     */
    Horse save(HorseDto horseDto);
}
