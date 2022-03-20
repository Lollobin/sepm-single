package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.validator.HorseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HorseMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseMapper.class);

    public HorseDto entityToDto(Horse horse) {
        LOGGER.trace("Converting entity to dto: {}", horse);

        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(),
                horse.getDateOfBirth(), horse.getSex(), horse.getOwnerId());
    }

    public Horse dtoToEntity(HorseDto horseDto) {
        LOGGER.trace("Converting dto to entity: {}", horseDto);

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
