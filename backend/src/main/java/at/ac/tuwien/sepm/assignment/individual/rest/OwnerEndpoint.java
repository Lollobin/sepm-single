package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.dto.OwnerSearchDto;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.mapper.OwnerMapper;
import at.ac.tuwien.sepm.assignment.individual.service.OwnerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

/**
 * Endpoint for owners.
 * Implements access and communication to rest layer regarding owners.
 */
@RestController
@RequestMapping(OwnerEndpoint.BASE_URL)
public class OwnerEndpoint {

    static final String BASE_URL = "/owners";
    private static final Logger LOGGER = LoggerFactory.getLogger(OwnerEndpoint.class);

    private final OwnerService ownerService;
    private final OwnerMapper ownerMapper;

    public OwnerEndpoint(OwnerService ownerService, OwnerMapper ownerMapper) {
        this.ownerService = ownerService;
        this.ownerMapper = ownerMapper;
    }

    /**
     * Returns all owners.
     *
     * @return all owners that are currently in the database
     */
    @GetMapping
    public Stream<OwnerDto> allOwners() {
        LOGGER.info("GET " + BASE_URL);
        return ownerService.allOwners().stream()
                .map(ownerMapper::entityToDto);
    }

    /**
     * Store an owner in the database.
     *
     * @param ownerDto owner data to be stored
     * @return horseDto with generated id
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public OwnerDto post(@RequestBody OwnerDto ownerDto) {
        LOGGER.info("POST " + BASE_URL + " {" + ownerDto.toString() + "}");

        try {
            return ownerMapper.entityToDto(ownerService.save(ownerDto));
        } catch (ValidationException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    /**
     * Returns owner with matching name.
     *
     * @param ownerSearchDto search criteria
     * @return owner with matching name
     */
    @GetMapping(params = {"name"})
    @ResponseStatus(HttpStatus.OK)
    public Stream<OwnerDto>searchOwner(OwnerSearchDto ownerSearchDto){
        return ownerService.searchOwner(ownerSearchDto).stream()
                .map(ownerMapper::entityToDto);
    }
}
