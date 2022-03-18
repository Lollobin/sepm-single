package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.mapper.HorseMapper;
import at.ac.tuwien.sepm.assignment.individual.dto.HorseDto;
import at.ac.tuwien.sepm.assignment.individual.service.HorseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.stream.Stream;

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

    @GetMapping
    public Stream<HorseDto> allHorses() {
        LOGGER.info("GET " + BASE_URL);
        return service.allHorses().stream()
                .map(mapper::entityToDto);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public HorseDto post(@RequestBody HorseDto horseDto) {
        LOGGER.info("POST " + BASE_URL + " {" + horseDto.toString() + "}");

        return mapper.entityToDto(service.save(horseDto));

/*
                try{
            return mapper.entityToDto(service.save(horseDto));
        } catch (ValidationException e){
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "Error during saving horse",e);
        }
         */

    }
}
