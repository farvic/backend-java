package cinema.web;

import cinema.Main;
import cinema.domain.Order;
import cinema.domain.Seat;
import cinema.domain.Stats;
import cinema.domain.Token;
import cinema.errors.CustomControllerAdvice;
import cinema.errors.OrderException;
import cinema.repo.OrderRepository;
import cinema.repo.SeatRepository;
import cinema.service.OrderService;
import cinema.service.OrderServiceImpl;
import cinema.service.SeatServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.jdbc.JdbcTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@ExtendWith(SpringExtension.class)
@WebMvcTest(OrderController.class)
class OrderControllerTest {
    @MockBean
    private OrderServiceImpl mockOrderService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testGetOrders() throws Exception {

        // Setup
        given(mockOrderService.getOrders()).willReturn(Collections.emptyList());

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(get("/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        verify(mockOrderService).getOrders();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentLength()).isEqualTo(0);
    }

    @Test
    void testStatsEndpoint() throws Exception {

        // Setup
        Stats stats = new Stats(0, 81, 0);
        when(mockOrderService.getStats("super_secret")).thenReturn(stats);

        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/stats").queryParam("password","super_secret")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        String responseBody = response.getContentAsString();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(responseBody).isEqualTo(stats.toJsonString());

    }

    @Test
    void testStatsEndpoint_ThrowsOrderException() throws Exception {

        // Setup
        when(mockOrderService.getStats("password")).thenThrow(new OrderException("The password is wrong!", HttpStatus.UNAUTHORIZED));
//
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/stats")
                        .queryParam("password","password")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        verify(mockOrderService).getStats("password");
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"error\":\"The password is wrong!\"}");
    }
}