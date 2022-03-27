package at.ac.tuwien.sepm.assignment.individual.persistence;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseSearchDto;
import at.ac.tuwien.sepm.assignment.individual.entity.Horse;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import at.ac.tuwien.sepm.assignment.individual.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ActiveProfiles({"test", "datagen"})
// enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
public class HorseDaoTest {

    @Autowired
    HorseDao horseDao;

    @Test
    public void getAllReturnsAllStoredHorses() {
        List<Horse> horses = horseDao.searchHorse(new HorseSearchDto(null, null, null, null, null));
        assertThat(horses.size()).isEqualTo(10);

        assertThat(horses.get(1).getId()).isEqualTo(-9);
        assertThat(horses.get(1).getName()).isEqualTo("Poppy");
        assertThat(horses.get(1).getDescription()).isEqualTo("Gone missing");
        assertThat(horses.get(1).getDateOfBirth()).isEqualTo("2019-12-24");
        assertThat(horses.get(1).getSex()).isEqualTo(Sex.female);
        assertThat(horses.get(1).getOwner().getId()).isEqualTo(-5);
        assertThat(horses.get(1).getFather().getId()).isEqualTo(-4);
        assertThat(horses.get(1).getMother().getId()).isEqualTo(-6);

        assertThat(horses.get(9).getId()).isEqualTo(-1);
        assertThat(horses.get(9).getName()).isEqualTo("Wendy");
        assertThat(horses.get(9).getDescription()).isEqualTo("Wendy is a great horse");
        assertThat(horses.get(9).getDateOfBirth()).isEqualTo("1980-04-03");
        assertThat(horses.get(9).getSex()).isEqualTo(Sex.female);
        assertThat(horses.get(9).getOwner().getId()).isEqualTo(-1);
    }

    @Test
    public void getOneByIdReturnsCorrectHorse() {
        Horse horse = horseDao.getOneById((long) -5);

        assertThat(horse.getId()).isEqualTo(-5);
        assertThat(horse.getName()).isEqualTo("Billy");
        assertThat(horse.getDescription()).isEqualTo("");
        assertThat(horse.getDateOfBirth()).isEqualTo("2001-03-01");
        assertThat(horse.getSex()).isEqualTo(Sex.male);
        assertThat(horse.getOwner().getId()).isEqualTo(-5);
        assertThat(horse.getFather().getId()).isEqualTo(-3);
        assertThat(horse.getMother().getId()).isEqualTo(-2);
    }

    @Test
    public void searchHorseReturnsCorrectHorses() {
        HorseSearchDto horseSearchDto = new HorseSearchDto(
                "",
                "",
                null,
                Sex.male,
                "Maurice"
        );

        List<Horse> horses = horseDao.searchHorse(horseSearchDto);

        assertThat(horses.size()).isEqualTo(2);

        assertThat(horses.get(0).getId()).isEqualTo(-5);
        assertThat(horses.get(1).getId()).isEqualTo(-3);
    }

    @Test
    public void searchHorseWithNonExistentIdThrowsNotFoundException(){
        assertThrows(NotFoundException.class, ()->horseDao.getOneById((long)0));
    }


}
