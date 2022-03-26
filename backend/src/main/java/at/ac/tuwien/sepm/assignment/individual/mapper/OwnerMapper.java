package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.springframework.stereotype.Component;

/**
 * Class for converting owner DTOs to entities and back.
 */
@Component
public class OwnerMapper {

    /**
     * Returns an OwnerDto with the properties of the given entity.
     *
     * @param entity an owner entity object
     * @return the corresponding OwnerDto
     */
    public OwnerDto entityToDto(Owner entity) {
        if (entity == null) return null;
        return new OwnerDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail());
    }

    /**
     * Returns an Owner entity with the properties of the given DTO
     *
     * @param dto an OwnerDto
     * @return the corresponding owner entity
     */
    public Owner dtoToEntity(OwnerDto dto) {
        if (dto == null) return null;
        Owner owner = new Owner();
        owner.setId(dto.id());
        owner.setFirstName(dto.firstName());
        owner.setLastName(dto.lastName());
        owner.setEmail(dto.email());
        return owner;
    }
}
