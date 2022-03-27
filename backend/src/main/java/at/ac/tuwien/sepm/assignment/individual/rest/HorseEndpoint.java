package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDtoFull;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.dto.ParentSearchDto;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationConflictException;
import at.ac.tuwien.sepm.assignment.individual.exception.ValidationProcessException;
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
     * Returns all horses that match the search parameters.
     * If parameters are empty, all stored horses are returned.
     *
     * @param horseSearchDto parameters to search horse (name, description, date of birth, sex, name of owner)
     * @return all matching horses
     */
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public Stream<HorseDtoFull> searchHorse(HorseSearchDto horseSearchDto) {
        LOGGER.info("GET " + BASE_URL);

        return service.searchHorse(horseSearchDto).stream()
                .map(mapper::entityToDtoFull);
    }

    /**
     * Returns horse with the given id.
     *
     * @param id id of the horse to return
     * @return horse corresponding to the id
     */
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

    /**
     * Returns all children of a horse.
     * Currently not used with frontend.
     *
     * @param id id of horse of which to return the children
     * @return children of horse with id
     */
    @GetMapping("/{id}/children")
    public Stream<HorseDto> getAllChildren(@PathVariable("id") Long id) {
        LOGGER.info("GET" + BASE_URL + "/{}/children", id);

        try{
            return service.getAllChildren(id).stream()
                    .map(mapper::entityToDto);
        }catch (NotFoundException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse", e);
        }
    }

    /**
     * Store a horse in the database.
     *
     * @param horseDto data transfer object of horse
     * @return generated id
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Long post(@RequestBody HorseDto horseDto) {
        LOGGER.info("POST " + BASE_URL + " {" + horseDto.toString() + "}");

        try {
            return service.save(horseDto);
        } catch (ValidationConflictException e){
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (ValidationProcessException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        }
    }

    /**
     * Edit a horse by id in the database
     *
     * @param horseId  id of horse to be edited
     * @param horseDto new data for horse (horseDto.id is ignored)
     * @return horseDto resembling the edited horse
     */
    @PutMapping("/{horseId}")
    @ResponseStatus(HttpStatus.OK)
    public HorseDto put(@PathVariable Long horseId, @RequestBody HorseDto horseDto) {
        LOGGER.info("PUT " + BASE_URL + "/" + horseId + " {" + horseDto.toString() + "}");

        try {
            return mapper.entityToDto(service.update(horseId, horseDto));
        } catch (ValidationConflictException e){
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage(), e);
        } catch (ValidationProcessException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, e.getMessage(), e);
        } catch (NotFoundException e) {
            LOGGER.error(e.toString());
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Error during reading horse", e);
        }
    }

    /**
     * Delete horse with id.
     *
     * @param horseId id of horse to be deleted
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

    /**
     * Returns possible parents for horse.
     *
     * @param parentSearchDto parameters to search parents
     * @return possible parents
     */
    @GetMapping(params = {"dateOfBirth", "parentSex", "searchString"})
    public Stream<HorseDto> searchParent(ParentSearchDto parentSearchDto) {
        LOGGER.info("GET " + BASE_URL + "(searchParent)");

        return service.searchParent(parentSearchDto).stream()
                .map(mapper::entityToDto);
    }

}
