package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(),
                horse.getDateOfBirth(), horse.getGender(), horse.getOwnerId());
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        Horse horse = new Horse();
        horse.setId(horseDto.id());
        horse.setName(horseDto.name());
        horse.setDescription(horseDto.description());
        horse.setDateOfBirth(horseDto.dateOfBirth());
        horse.setGender(horseDto.gender());
        horse.setOwnerId(horseDto.id());
        return horse;
    }
}
