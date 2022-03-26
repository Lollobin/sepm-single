package at.ac.tuwien.sepm.assignment.individual.service.impl;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import at.ac.tuwien.sepm.assignment.individual.persistence.OwnerDao;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import at.ac.tuwien.sepm.assignment.individual.validator.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OwnerServiceImpl implements OwnerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerServiceImpl.class);

    private final OwnerDao ownerDao;
    private final Validator validator;

    public OwnerServiceImpl(OwnerDao ownerDao, Validator validator) {
        this.ownerDao = ownerDao;
        this.validator = validator;
    }

    @Override
    public List<Owner> allOwners() {
        LOGGER.info("Getting all owners");
        return ownerDao.getAll();
    }

    @Override
    public Owner save(OwnerDto ownerDto) {
        LOGGER.info("Saving {}", ownerDto.toString());
        validator.validateOwner(ownerDto);
        return ownerDao.save(ownerDto);
    }

    @Override
    public List<Owner> searchOwner(OwnerSearchDto ownerSearchDto) {
        return ownerDao.searchOwner(ownerSearchDto);
    }
}
