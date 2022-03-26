package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDtoFull;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class for converting horse dto objects to entities and back
 */
@Component
public class HorseMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseMapper.class);

    /**
     * Returns a HorseDto with the parameters of the given entity.
     *
     * @param horse a horse entity object
     * @return the corresponding HorseDto
     */
    public HorseDto entityToDto(Horse horse) {
        LOGGER.trace("Converting entity to dto: {}", horse);

        if (horse == null) {
            return null;
        }

        return new HorseDto(horse.getId(), horse.getName(), horse.getDescription(),
                horse.getDateOfBirth(), horse.getSex(),
                horse.getOwner() == null ? null : horse.getOwner().getId(),
                horse.getFather() == null ? null : horse.getFather().getId(),
                horse.getMother() == null ? null : horse.getMother().getId());
    }

    /**
     * Returns a HorseDtoFull with the parameters of the given entity.
     *
     * @param horse  a horse entity object
     * @return the corresponding HorseDtoFull
     */
    public HorseDtoFull entityToDtoFull(Horse horse) {
        LOGGER.trace("Converting entity to dto: {}", horse);

        return new HorseDtoFull(horse.getId(), horse.getName(), horse.getDescription(),
                horse.getDateOfBirth(), horse.getSex(), horse.getOwner(),
                entityToDto(horse.getFather()), entityToDto(horse.getMother()));
    }


    /**
     * Returns a Horse with the parameters of the given entity.
     *
     * @param horseDto a horse dto
     * @return the corresponding Horse entity
     */
    /*
    public Horse dtoToEntity(HorseDto horseDto) {
        LOGGER.trace("Converting dto to entity: {}", horseDto);

        Horse horse = new Horse();
        horse.setId(horseDto.id());
        horse.setName(horseDto.name());
        horse.setDescription(horseDto.description());
        horse.setDateOfBirth(horseDto.dateOfBirth());
        horse.setSex(horseDto.sex());
        if(horseDto.o== null)
        horse.setOwnerId(horseDto.ownerId());
        horse.setFatherId(horseDto.fatherId());
        horse.setMotherId(horseDto.motherId());
        return horse;
    }

     */
}
