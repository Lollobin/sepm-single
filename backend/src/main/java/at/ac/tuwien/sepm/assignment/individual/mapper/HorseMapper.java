package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.springframework.stereotype.Component;

@Component
public class HorseMapper {

    public HorseDto entityToDto(Horse horse) {
        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(),
                horse.getDateOfBirth(), horse.getSex(), horse.getOwnerId());
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        Horse horse = new Horse();
        horse.setId(horseDto.id());
        horse.setName(horseDto.name());
        horse.setDescription(horseDto.description());
        horse.setDateOfBirth(horseDto.dateOfBirth());
        horse.setSex(horseDto.sex());
        horse.setOwnerId(horseDto.id());
        return horse;
    }
}
