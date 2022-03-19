package at.ac.tuwien.sepm.assignment.individual.validator;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import org.springframework.stereotype.Component;

@Component
public class HorseValidator {

    public boolean validateHorse(HorseDto horseDto) {

        return validateName(horseDto.name());

    }

    private boolean validateName(String name) {
        if (name == null) {
            throw new ValidationException("Name cannot be null");
        }

        if (name.equals("")) {
            throw new ValidationException("Name cannot be empty");
        }
        return true;
    }
}
