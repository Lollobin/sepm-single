package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.rest.HorseEndpoint;
import org.apache.tomcat.jni.Time;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class to validate HorseDto.
 * Checks parameters for errors caused by wrong user input.
 * <p>
 * Throws ValidationException if error is detected.
 */
@Component
public class HorseValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseValidator.class);

    /**
     * Validates all relevant parameters of a HorseDto.
     * Throws ValidationException if error is detected.
     * @param horseDto  object to be validated
     * @return  true if all tests succeed
     */
    public boolean validateHorse(HorseDto horseDto) {
        LOGGER.trace("Validating horse {}", horseDto);
        return validateName(horseDto.name())
                && validateDateOfBirth(horseDto.dateOfBirth())
                && validateOwnerId(horseDto.ownerId());
    }

    private boolean validateName(String name) {
        LOGGER.trace("Validating name '{}'", name);

        //todo check for empty strings like "    "

        if (name == null) {
            throw new ValidationException("Name cannot be null");
        }

        if (name.equals("")) {
            throw new ValidationException("Name cannot be empty");
        }
        return true;
    }

    private boolean validateDateOfBirth(java.sql.Date dateOfBirth) {
        LOGGER.trace("Validating dateOfBirth '{}'", dateOfBirth);

        if(dateOfBirth == null){
            throw new ValidationException("Date of birth cannot be null");
        }

        if (dateOfBirth.after(new java.sql.Date(System.currentTimeMillis()))) {
            throw new ValidationException("Date of birth cannot be in the future");
        }

        return true;
    }

    private boolean validateOwnerId(Long ownerId) {

        //LOGGER.trace("Validating ownerId '{}'", ownerId);
        //todo add check if owner exists

        return true;
    }
}
