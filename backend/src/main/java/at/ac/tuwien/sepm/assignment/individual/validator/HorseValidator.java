package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;

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
    private final HorseDao dao;

    public HorseValidator(HorseDao dao) {
        this.dao = dao;
    }

    /**
     * Validates all relevant parameters of a HorseDto.
     * Throws ValidationException if error is detected.
     *
     * @param horseDto object to be validated
     * @return true if all tests succeed
     */
    public void validateHorse(HorseDto horseDto) {
        LOGGER.trace("Validating horse {}", horseDto);

        validateName(horseDto.name());
        validateDateOfBirth(horseDto.dateOfBirth());
        validateOwnerId(horseDto.ownerId());
        validateParents(horseDto.fatherId(), horseDto.motherId(), horseDto.dateOfBirth());
    }

    private void validateName(String name) {
        LOGGER.trace("Validating name '{}'", name);

        if (name.trim().length() == 0)
            throw new ValidationException("Name cannot be empty or only spaces");
    }

    private void validateDateOfBirth(java.sql.Date dateOfBirth) {
        LOGGER.trace("Validating dateOfBirth '{}'", dateOfBirth);

        if (dateOfBirth == null)
            throw new ValidationException("Date of birth cannot be null");


        if (dateOfBirth.after(new java.sql.Date(System.currentTimeMillis())))
            throw new ValidationException("Date of birth cannot be in the future");
    }

    private void validateOwnerId(Long ownerId) {

        //LOGGER.trace("Validating ownerId '{}'", ownerId);
        //todo add check if owner exists

    }

    private void validateParents(Long fatherId, Long motherId, java.sql.Date dateOfBirth) {
        if (fatherId != null) {
            Horse father = dao.getOneById(fatherId);
            if (father.getSex() == Sex.female)
                throw new ValidationException("Father has to be male");
            if (father.getDateOfBirth().before(dateOfBirth))
                throw new ValidationException("Father cannot be younger than horse");
        }

        if (motherId != null) {
            Horse mother = dao.getOneById(motherId);
            if (mother.getSex() == Sex.male)
                throw new ValidationException("Mother has to be female");
            if (mother.getDateOfBirth().before(dateOfBirth))
                throw new ValidationException("Mother cannot be younger than horse");
        }
    }
}
