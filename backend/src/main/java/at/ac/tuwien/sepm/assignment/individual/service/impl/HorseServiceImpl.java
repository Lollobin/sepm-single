package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.exception.ServiceException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.rest.HorseEndpoint;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.validator.HorseValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseServiceImpl.class);
    private final HorseDao dao;
    private static final HorseValidator VALIDATOR = new HorseValidator();

    public HorseServiceImpl(HorseDao dao) {
        this.dao = dao;
    }

    @Override
    public List<Horse> allHorses() {
        LOGGER.info("Getting all horses");
        return dao.getAll();
    }

    @Override
    public Horse save(HorseDto horseDto) {
        LOGGER.info("Saving {}", horseDto.toString());
        VALIDATOR.validateHorse(horseDto);
        return dao.save(horseDto);
    }

    @Override
    public Horse update(Long horseId, HorseDto horseDto) {
        LOGGER.info("Updating horse with ID {} to match {}", horseId, horseDto);
        VALIDATOR.validateHorse(horseDto);
        //todo add check if horse with ID exists
        return dao.update(horseId, horseDto);
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.info("Get horse with id {}", id);
        return dao.getOneById(id);
    }
}
