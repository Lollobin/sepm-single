package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.HorseDtoFull;
import at.ac.tuwien.sepm.assignment.individual.enums.Sex;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "datagen"})
// enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class HorseEndpointTest {

    @Autowired
    private WebApplicationContext webAppContext;
    private MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webAppContext).build();
    }

    @Test
    public void gettingAllHorses() throws Exception {
        byte[] body = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/horses")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        List<HorseDtoFull> horseResult = objectMapper.readerFor(HorseDtoFull.class).<HorseDtoFull>readValues(body).readAll();

        assertThat(horseResult).isNotNull();
        assertThat(horseResult.size()).isEqualTo(10);
        assertThat(horseResult.get(1).id()).isEqualTo(-9);
        assertThat(horseResult.get(1).name()).isEqualTo("Poppy");
        assertThat(horseResult.get(1).description()).isEqualTo("Gone missing");
        assertThat(horseResult.get(1).dateOfBirth()).isEqualTo("2019-12-24");
        assertThat(horseResult.get(1).sex()).isEqualTo(Sex.female);
        assertThat(horseResult.get(1).owner().id()).isEqualTo(-5);
        assertThat(horseResult.get(1).father().id()).isEqualTo(-4);
        assertThat(horseResult.get(1).mother().id()).isEqualTo(-6);

        assertThat(horseResult.get(9).id()).isEqualTo(-1);
        assertThat(horseResult.get(9).name()).isEqualTo("Wendy");
        assertThat(horseResult.get(9).description()).isEqualTo("Wendy is a great horse");
        assertThat(horseResult.get(9).dateOfBirth()).isEqualTo("1980-04-03");
        assertThat(horseResult.get(9).sex()).isEqualTo(Sex.female);
        assertThat(horseResult.get(9).owner().id()).isEqualTo(-1);
    }

    @Test
    public void gettingHorseById() throws Exception {
        byte[] body = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/horses/-5")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        List<HorseDtoFull> horseResult = objectMapper.readerFor(HorseDtoFull.class).<HorseDtoFull>readValues(body).readAll();

        HorseDtoFull horseDtoFull = horseResult.get(0);

        assertThat(horseDtoFull.id()).isEqualTo(-5);
        assertThat(horseDtoFull.name()).isEqualTo("Billy");
        assertThat(horseDtoFull.description()).isEqualTo("");
        assertThat(horseDtoFull.dateOfBirth()).isEqualTo("2001-03-01");
        assertThat(horseDtoFull.sex()).isEqualTo(Sex.male);
        assertThat(horseDtoFull.owner().id()).isEqualTo(-5);
        assertThat(horseDtoFull.father().id()).isEqualTo(-3);
        assertThat(horseDtoFull.mother().id()).isEqualTo(-2);
    }

    @Test
    public void gettingNonexistentUrlReturns404() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/asdf123")
                ).andExpect(status().isNotFound());
    }

    @Test
    public void gettingNonexistentIdReturns404() throws Exception {
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/horses/0")
                ).andExpect(status().isNotFound());
    }

    @Test
    public void emptyPostReturns422() throws Exception {
        Object obj = new Object() {
            public final String name = null;
            public final String description = null;
            public final String dateOfBirth = null;
            public final String sex = null;
            public final String ownerId = null;
            public final String fatherId = null;
            public final String motherId = null;
        };

        String json = objectMapper.writeValueAsString(obj);
        mockMvc
                .perform(post("/horses")
                        .characterEncoding("utf-8")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                ).andExpect(status().isUnprocessableEntity());
    }


}
