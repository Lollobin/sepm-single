package at.ac.tuwien.sepm.assignment.individual.mapper;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.springframework.stereotype.Component;

@Component
public class OwnerMapper {

    public OwnerDto entityToDto(Owner entity) {
        if (entity == null) return null;
        return new OwnerDto(entity.getId(), entity.getFirstName(), entity.getLastName(), entity.getEmail());
    }

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
