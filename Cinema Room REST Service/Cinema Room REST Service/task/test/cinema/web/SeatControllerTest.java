package cinema.web;

import cinema.domain.Seat;
import cinema.service.SeatService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ExtendWith(SpringExtension.class)
@WebMvcTest(SeatController.class)
class SeatControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SeatService mockSeatService;

    ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void testGetAvailableSeats() throws Exception {
        // Setup
        List<Seat> seats = List.of(new Seat(0, 1, 1, 10, false));
        when(mockSeatService.getAllSeats()).thenReturn(seats);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/seats-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        assertThat(response.getContentAsString()).isEqualTo(objectMapper.writeValueAsString(seats));
    }

    @Test
    void testGetAvailableSeatsReturnsNoItems() throws Exception {
        // Setup
        when(mockSeatService.getAllSeats()).thenReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/seats-list")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("[]");
    }

    @Test
    void testGetSeatByRowAndColumn() throws Exception {
        // Setup
        Seat seat = new Seat(0, 1, 1, 10, true);
        when(mockSeatService.getSeatByRowAndColumn(any(Seat.class))).thenReturn(seat);

        String seatJson = objectMapper.writeValueAsString(seat);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/seat")
                        .content(seatJson).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(seatJson);
    }
}
