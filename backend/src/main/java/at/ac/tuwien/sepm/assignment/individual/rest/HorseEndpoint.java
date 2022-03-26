package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDtoFull;
import at.ac.tuwien.sepm.assignment.individual.dto.SearchDto;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationException;
import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

/**
 * Endpoint for horses.
 * Implements access and communication to rest layer regarding horses.
 */
@RestController
@RequestMapping(HorseEndpoint.BASE_URL)
public class HorseEndpoint {

    static final String BASE_URL = "/horses";
    private static final Logger LOGGER = LoggerFactory.getLogger(HorseEndpoint.class);
    private final HorseService service;
    private final HorseMapper mapper;

    public HorseEndpoint(HorseService service, HorseMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    /**
     * Returns all horses.
     *
     * @return all horses that are currently in the database.
     */
    @GetMapping
    public Stream<HorseDto> allHorses() {
        LOGGER.info("GET " + BASE_URL);
        return service.allHorses().stream()
                .map(mapper::entityToDto);
    }


    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public Stream<HorseDto> searchHorse(SearchDto searchDto) {
        LOGGER.info("GET " + BASE_URL + "/search");
        LOGGER.info(searchDto.getName());

        return service.searchHorse(searchDto).stream()
                .map(mapper::entityToDto);
    }


    @GetMapping(value = "/{id}")
    public HorseDtoFull getOneById(@PathVariable("id") Long id) {
        LOGGER.info("GET " + BASE_URL + "/{}", id);
        try {
            return mapper.entityToDtoFull(service.getOneById(id));
        } catch (NotFoundException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse", e);
        }
    }

    @GetMapping("/{id}/children")
    public Stream<HorseDto> getAllChildren(@PathVariable("id") Long id) {
        LOGGER.info("GET" + BASE_URL + "/{}/children", id);
        return service.getAllChildren(id).stream()
                .map(mapper::entityToDto);
    }

    /**
     * Store a horse in the database.
     *
     * @param horseDto data transfer object of horse
     * @return generated ID
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long post(@RequestBody HorseDto horseDto) {
        LOGGER.info("POST " + BASE_URL + " {" + horseDto.toString() + "}");

        try {
            return service.save(horseDto);
        } catch (ValidationException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Parameters are not valid", e);
        }
    }

    /**
     * Edit a horse by ID in the database
     *
     * @param horseId  ID of horse to be edited
     * @param horseDto new data for horse (ID is ignored)
     * @return horseDto resembling the edited horse
     */
    @PutMapping("/{horseId}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto put(@PathVariable Long horseId, @RequestBody HorseDto horseDto) {
        LOGGER.info("PUT " + BASE_URL + "/" + horseId + " {" + horseDto.toString() + "}");

        try {
            return mapper.entityToDto(service.update(horseId, horseDto));
        } catch (ValidationException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Parameters are not valid", e);
        } catch (NotFoundException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse", e);
        }
    }

    /**
     * Delete horse with id.
     *
     * @param horseId id of horse to be deleted.
     */
    @DeleteMapping("/{horseId}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable Long horseId) {
        LOGGER.info("DELETE " + BASE_URL + "/" + horseId);

        try {
            service.delete(horseId);
        } catch (NotFoundException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Could not find horse to delete", e);
        }
    }

    @GetMapping(params = {"dateOfBirth", "parentSex", "searchString"})
    public Stream<HorseDto> searchParent(@RequestParam java.sql.Date dateOfBirth, @RequestParam Sex parentSex, @RequestParam String searchString) {
        LOGGER.info("GET " + BASE_URL);
        return service.searchParent(dateOfBirth, parentSex, searchString).stream()
                .map(mapper::entityToDto);
    }

}
