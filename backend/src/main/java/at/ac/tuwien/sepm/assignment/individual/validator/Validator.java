package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;

/**
 * Class to validate HorseDto.
 * Checks parameters for errors caused by wrong user input.
 * <p>
 * Throws ValidationException if error is detected.
 */
@Component
public class Validator {

    private static final Logger LOGGER = LoggerFactory.getLogger(Validator.class);
    private final HorseDao dao;

    public Validator(HorseDao dao) {
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

    public void validateOwner(OwnerDto ownerDto) {
        LOGGER.trace("Validating owner {}", ownerDto);
        validateName(ownerDto.firstName());
        validateName(ownerDto.lastName());
        validateEmail(ownerDto.email());

    }

    public void validateParentUpdate(Long id, HorseDto horseDto) {
        Horse oldHorse = dao.getOneById(id);
        List<Horse> children = dao.getAllChildren(id);

        if (children.isEmpty())
            return;

        if (oldHorse.getSex() != horseDto.sex())
            throw new ValidationException("Cannot change sex of horse with children");

        if (oldHorse.getDateOfBirth() != horseDto.dateOfBirth())
            for (Horse child : children)
                if (oldHorse.getDateOfBirth().after(child.getDateOfBirth()))
                    throw new ValidationException("Cannot change horse to be younger than its child");
    }

    private void validateName(String name) {
        LOGGER.trace("Validating name '{}'", name);

        if(name==null)
            throw new ValidationException("Name cannot be null");

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

    private void validateEmail(String email) {
        String regexPattern = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
        if (!Pattern.compile(regexPattern).matcher(email).matches())
            throw new ValidationException("Email is not a valid adress");
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
            if (father.getDateOfBirth().after(dateOfBirth))
                throw new ValidationException("Father cannot be younger than horse");
        }

        if (motherId != null) {
            Horse mother = dao.getOneById(motherId);
            if (mother.getSex() == Sex.male)
                throw new ValidationException("Mother has to be female");
            if (mother.getDateOfBirth().after(dateOfBirth))
                throw new ValidationException("Mother cannot be younger than horse");
        }
    }
}
