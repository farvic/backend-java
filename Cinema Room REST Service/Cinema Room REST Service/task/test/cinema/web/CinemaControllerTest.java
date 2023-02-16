package cinema.web;

import cinema.domain.Cinema;
import cinema.domain.Seat;
import cinema.service.CinemaService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


@WebMvcTest(CinemaController.class)
class CinemaControllerTest {

    @MockBean
    private CinemaService mockCinemaService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetCinemaSeats() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        JacksonTester.initFields(this, mapper);

        // Setup
        List<Seat> seats = List.of(new Seat(1, 1, 10, true), new Seat(1, 2, 10, true));
        Cinema cinema = new Cinema(seats);

        when(mockCinemaService.getCinemaSeats()).thenReturn(cinema);
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/seats")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(cinema.toJsonString());
    }
}