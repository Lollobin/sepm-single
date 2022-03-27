package at.ac.tuwien.sepm.assignment.individual.rest;

import at.ac.tuwien.sepm.assignment.individual.dto.OwnerDto;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles({"test", "datagen"})
// enable "test" spring profile during test execution in order to pick up configuration from application-test.yml
@SpringBootTest
@EnableWebMvc
@WebAppConfiguration
public class OwnerEndpointTest {

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
                        .get("/owners")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        List<OwnerDto> ownerResult = objectMapper.readerFor(OwnerDto.class).<OwnerDto>readValues(body).readAll();

        assertThat(ownerResult).isNotNull();
        assertThat(ownerResult.get(0).id()).isEqualTo(-10);
        assertThat(ownerResult.get(0).firstName()).isEqualTo("Ilona");
        assertThat(ownerResult.get(0).lastName()).isEqualTo("Klauke");

        assertThat(ownerResult.get(9).id()).isEqualTo(-1);
        assertThat(ownerResult.get(9).firstName()).isEqualTo("Katja");
        assertThat(ownerResult.get(9).lastName()).isEqualTo("Storbeck");
        assertThat(ownerResult.get(9).email()).isEqualTo("katja.storbeck@gmail.com");
    }
}
