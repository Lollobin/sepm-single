package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationConflictException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationProcessException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class to validate user input.
 * Checks parameters for errors.
 * <p>
 * Throws ValidationProcessException or ValidationConflictException if error is detected.
 */
@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);
    private final HorseDao horseDao;
    private final OwnerDao ownerDao;

    public Validator(HorseDao horseDao, OwnerDao ownerDao) {
        this.horseDao = horseDao;
        this.ownerDao = ownerDao;
    }

    /**
     * Validates all relevant parameters of a new HorseDto.
     * Throws ValidationProcessException if error is detected.
     *
     * @param horseDto object to be validated
     */
    public void validateHorse(HorseDto horseDto) {
        LOGGER.trace("Validating horse {}", horseDto);

        validateName(horseDto.name());
        if (horseDto.description() != null)
            validateDescription(horseDto.description());
        validateDateOfBirth(horseDto.dateOfBirth());
        validateSex(horseDto.sex());

        validateOwnerId(horseDto.ownerId());
        validateParents(horseDto.fatherId(), horseDto.motherId(), horseDto.dateOfBirth());
    }

    /**
     * Validates all relevant parameters of a new OwnerDto.
     * Throws ValidationProcessException if error is detected.
     *
     * @param ownerDto object to be validated
     */
    public void validateOwner(OwnerDto ownerDto) {
        LOGGER.trace("Validating owner {}", ownerDto);

        validateName(ownerDto.firstName());
        validateName(ownerDto.lastName());
        validateEmail(ownerDto.email());
    }

    /**
     * Validates HorseDto to be updated.
     *
     * @param id       id of horse to be updated
     * @param horseDto new data for horse
     */
    public void validateHorseUpdate(Long id, HorseDto horseDto) {
        LOGGER.trace("Validating horse update id: {}, data: {}", id, horseDto);
        validateHorse(horseDto);
        Horse oldHorse = horseDao.getOneById(id);

        List<Horse> children = horseDao.getAllChildren(id);

        if (children.isEmpty())
            return;

        if (oldHorse.getSex() != horseDto.sex())
            throw new ValidationConflictException("Cannot change sex of horse with children");

        if (oldHorse.getDateOfBirth() != horseDto.dateOfBirth())
            for (Horse child : children)
                if (oldHorse.getDateOfBirth().isAfter(child.getDateOfBirth()))
                    throw new ValidationConflictException("Cannot change horse to be younger than its child");
    }

    private void validateName(String name) {
        LOGGER.trace("Validating name: {}", name);

        if (name == null)
            throw new ValidationProcessException("Name cannot be empty");

        if (name.trim().length() == 0)
            throw new ValidationProcessException("Name cannot be empty or only spaces");

        if (name.length() > 255)
            throw new ValidationProcessException("Name is too long");
    }

    private void validateDescription(String description) {
        LOGGER.trace("Validating description: {}", description);

        if (description.length() > 255)
            throw new ValidationProcessException("Description is too long");
    }

    private void validateDateOfBirth(LocalDate dateOfBirth) {
        LOGGER.trace("Validating dateOfBirth: {}", dateOfBirth);

        if (dateOfBirth == null)
            throw new ValidationProcessException("Date of birth cannot be empty");

        if (dateOfBirth.isAfter(LocalDate.now()))
            throw new ValidationProcessException("Date of birth cannot be in the future");
    }

    private void validateSex(Sex sex) {
        LOGGER.trace("Validating dateOfBirth: {}", sex);

        if (sex == null)
            throw new ValidationProcessException("Sex cannot be undefined");
    }

    private void validateEmail(String email) {
        LOGGER.trace("Validating e-mail: {}", email);

        if (email == null || email.equals(""))
            return;

        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (!Pattern.compile(regexPattern).matcher(email).matches())
            throw new ValidationProcessException("Email is not a valid e-mail address");
    }

    private void validateOwnerId(Long ownerId) {
        LOGGER.trace("Validating ownerId: {}", ownerId);
        if (ownerId == null)
            return;
        try {
            ownerDao.getOneById(ownerId);
        } catch (NotFoundException e) {
            throw new ValidationConflictException("Owner doesn't exist");
        }
    }

    private void validateParents(Long fatherId, Long motherId, LocalDate dateOfBirth) {
        LOGGER.trace("Validating parents: fatherId: {}, motherId: {}, dateOfBirth: {}", fatherId, motherId, dateOfBirth);

        if (fatherId != null) {
            Horse father;
            try {
                father = horseDao.getOneById(fatherId);
            } catch (NotFoundException e) {
                throw new ValidationConflictException("Father doesn't exist");
            }

            if (father.getSex() != Sex.male)
                throw new ValidationConflictException("Father has to be male");
            if (father.getDateOfBirth().isAfter(dateOfBirth))
                throw new ValidationConflictException("Father cannot be younger than horse");
        }

        if (motherId != null) {
            Horse mother;
            try {
                mother = horseDao.getOneById(motherId);
            } catch (NotFoundException e) {
                throw new ValidationConflictException("Mother doesn't exist");
            }
            if (mother.getSex() != Sex.female)
                throw new ValidationConflictException("Mother has to be female");
            if (mother.getDateOfBirth().isAfter(dateOfBirth))
                throw new ValidationConflictException("Mother cannot be younger than horse");
        }
    }
}
