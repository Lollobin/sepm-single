package at.ac.tuwien.sepm.assignment.individual.service;

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
public class HorseServiceTest {

    @Autowired
    HorseService horseService;

    @Test
    public void getAllReturnsAllStoredHorses() {
        List<Horse> horses = horseService.allHorses();
        assertThat(horses.size()).isEqualTo(1);
        assertThat(horses.get(0).getId()).isEqualTo(-1);
        assertThat(horses.get(0).getName()).isEqualTo("Wendy");
        assertThat(horses.get(0).getDescription()).isEqualTo("Wendy is a great horse");
        assertThat(horses.get(0).getDateOfBirth()).isEqualTo("2005-04-03");
        assertThat(horses.get(0).getSex()).isEqualTo(Sex.female);
        assertThat(horses.get(0).getId()).isEqualTo(1);
    }
}
