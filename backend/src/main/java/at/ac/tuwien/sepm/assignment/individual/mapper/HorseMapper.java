package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDtoFull;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Class for converting horse entity objects to DTOs.
 */
@Component
public class HorseMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseMapper.class);
    private final OwnerMapper ownerMapper;

    public HorseMapper(OwnerMapper ownerMapper) {
        this.ownerMapper = ownerMapper;
    }

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
     * @param horse a horse entity object
     * @return the corresponding HorseDtoFull
     */
    public HorseDtoFull entityToDtoFull(Horse horse) {
        LOGGER.trace("Converting entity to dto: {}", horse);

        return new HorseDtoFull(horse.getId(), horse.getName(), horse.getDescription(),
                horse.getDateOfBirth(), horse.getSex(), ownerMapper.entityToDto(horse.getOwner()),
                entityToDto(horse.getFather()), entityToDto(horse.getMother()));
    }
}
