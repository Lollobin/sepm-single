package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;

import java.util.List;

import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles({"test", "datagen"})
// enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseDaoTest {

    @Autowired
    HorseDao horseDao;

    @Test
    public void getAllReturnsAllStoredHorses() {
        List<Horse> horses = horseDao.searchHorse(new HorseSearchDto(null, null, null, null, null));
        assertThat(horses.size()).isEqualTo(1);
        assertThat(horses.get(0).getId()).isEqualTo(-1);
        assertThat(horses.get(0).getName()).isEqualTo("Wendy");
        assertThat(horses.get(0).getDescription()).isEqualTo("Wendy is a great horse");
        assertThat(horses.get(0).getDateOfBirth()).isEqualTo("2005-04-03");
        assertThat(horses.get(0).getSex()).isEqualTo(Sex.female);
        assertThat(horses.get(0).getId()).isEqualTo(1);
    }
}
