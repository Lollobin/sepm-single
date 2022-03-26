package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ParentSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.persistence.HorseDao;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import at.ac.tuwien.sepm.assignment.individual.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HorseServiceImpl implements HorseService {

    private static final Logger LOGGER = LoggerFactory.getLogger(HorseServiceImpl.class);
    private final HorseDao horseDao;
    private final Validator validator;

    public HorseServiceImpl(HorseDao horseDao, Validator validator) {
        this.horseDao = horseDao;
        this.validator = validator;
    }

    @Override
    public List<Horse> allHorses() {
        LOGGER.info("Getting all horses");
        return horseDao.getAll();
    }

    @Override
    public Long save(HorseDto horseDto) {
        LOGGER.info("Saving {}", horseDto.toString());
        validator.validateHorse(horseDto);
        return horseDao.save(horseDto);
    }

    @Override
    public Horse update(Long horseId, HorseDto horseDto) {
        LOGGER.info("Updating horse with ID {} to match {}", horseId, horseDto);
        validator.validateHorseUpdate(horseId, horseDto);
        return horseDao.update(horseId, horseDto);
    }

    @Override
    public Horse getOneById(Long id) {
        LOGGER.info("Get horse with id {}", id);
        return horseDao.getOneById(id);
    }

    @Override
    public void delete(Long id) {
        LOGGER.info("Delete horse with id {}", id);
        horseDao.delete(id);
    }

    @Override
    public List<Horse> searchParent(ParentSearchDto parentSearchDto) {
        return horseDao.searchParent(parentSearchDto);
    }

    @Override
    public List<Horse> getAllChildren(Long id) {
        return horseDao.getAllChildren(id);
    }

    @Override
    public List<Horse> searchHorse(HorseSearchDto horseSearchDto) {
        return horseDao.searchHorse(horseSearchDto);
    }
}
