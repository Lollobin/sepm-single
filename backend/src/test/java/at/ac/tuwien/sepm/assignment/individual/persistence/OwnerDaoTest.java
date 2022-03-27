package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Owner;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles({"test", "datagen"})
// enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class OwnerDaoTest {

    @Autowired
    OwnerDao ownerDao;

    @Test
    public void getAllReturnsAllStoredOwners() {
        List<Owner> owners = ownerDao.getAll();
        assertThat(owners.size()).isEqualTo(10);

        assertThat(owners.get(0).getId()).isEqualTo(-10);
        assertThat(owners.get(0).getFirstName()).isEqualTo("Ilona");
        assertThat(owners.get(0).getLastName()).isEqualTo("Klauke");

        assertThat(owners.get(9).getId()).isEqualTo(-1);
        assertThat(owners.get(9).getFirstName()).isEqualTo("Katja");
        assertThat(owners.get(9).getLastName()).isEqualTo("Storbeck");
        assertThat(owners.get(9).getEmail()).isEqualTo("katja.storbeck@gmail.com");
    }

    @Test
    public void searchOwnerReturnsCorrectOwners() {
        OwnerSearchDto ownerSearchDto = new OwnerSearchDto("Ma");

        List<Owner> owners = ownerDao.searchOwner(ownerSearchDto);

        assertThat(owners.size()).isEqualTo(2);

        assertThat(owners.get(0).getId()).isEqualTo(-9);
        assertThat(owners.get(1).getId()).isEqualTo(-5);
    }

}
