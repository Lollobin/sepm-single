package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDtoParents;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.validator.HorseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseServiceImpl.class);
    private final HorseDao dao;
    private final HorseValidator validator;
    private final HorseMapper mapper;

    public HorseServiceImpl(HorseDao dao, HorseMapper mapper) {
        this.dao = dao;
        validator = new HorseValidator(dao);
        this.mapper = mapper;
    }

    @Override
    public List<Horse> allHorses() {
        LOGGER.info("Getting all horses");
        return dao.getAll();
    }

    @Override
    public Horse save(HorseDto horseDto) {
        LOGGER.info("Saving {}", horseDto.toString());
        validator.validateHorse(horseDto);
        return dao.save(horseDto);
    }

    @Override
    public Horse update(Long horseId, HorseDto horseDto) {
        LOGGER.info("Updating horse with ID {} to match {}", horseId, horseDto);
        validator.validateHorse(horseDto);
        //todo check if horse has children, then age and sex cannot always be changed
        return dao.update(horseId, horseDto);
    }

    @Override
    public HorseDtoParents getOneById(Long id) {
        LOGGER.info("Get horse with id {}", id);
        Horse horse = dao.getOneById(id);

        Horse father = null;
        Horse mother = null;

        if (horse.getFatherId() != null) {
            father = dao.getOneById(horse.getFatherId());
        }

        if (horse.getMotherId() != null) {
            mother = dao.getOneById(horse.getMotherId());
        }

        return mapper.entityToDtoParents(horse, father, mother);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Delete horse with id {}", id);
        dao.delete(id);
    }

    @Override
    public List<Horse> searchParent(Date dateOfBirth, Sex parentSex, String searchString) {
        return dao.searchParent(dateOfBirth, parentSex, searchString);
    }
}
