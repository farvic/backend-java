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
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@AutoConfigureMockMvc

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
//        , properties = {"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"}, classes = Main.class, value = )
@ContextConfiguration(classes = {Main.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
        , properties = {"spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"}, classes = Main.class)
//@SqlConfig(dataSource = "dataSource")
//@Transactional
//@Sql(scripts={"file:D:/java/hyperskill/Cinema Room REST Service/Cinema Room REST Service/task/test/schema.sql", "file:D:/java/hyperskill/Cinema Room REST Service/Cinema Room REST Service/task/test/data.sql"},config = @SqlConfig(encoding = "utf-8"))
//@Sql(scripts={"/data.sql"},config = @SqlConfig(encoding = "utf-8", transactionMode = SqlConfig.TransactionMode.ISOLATED))
class OrderControllerTest {

    @MockBean
    private OrderService orderService;

    @MockBean
    private SeatRepository mockSeatRepository;

    @MockBean
    private OrderRepository mockOrderRepository;

    @InjectMocks
    private OrderController orderController;

    private JacksonTester<Order> jsonOrder;
    private JacksonTester<Stats> jsonStats;

    @Autowired
    private MockMvc mockMvc;



//    @Autowired
//    private WebApplicationContext webApplicationContext;
    final ObjectMapper objectMapper;
    final JdbcTemplate jdbcTemplate;

    @Autowired
    public OrderControllerTest(DataSource dataSource, ObjectMapper objectMapper) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.objectMapper = objectMapper;
    }


    @BeforeEach
    void setUp() {

        JacksonTester.initFields(this, new ObjectMapper());
        this.mockMvc = MockMvcBuilders.standaloneSetup(new OrderController(orderService)).setControllerAdvice(new CustomControllerAdvice()).alwaysDo(print()).build();


    }
    @Test
    void usersTest() {
        // verify state in test database:

        assertNumUsers(81);
        // run code that uses the test data...
    }

    int countRowsInTable(String tableName) {
        return JdbcTestUtils.countRowsInTable(this.jdbcTemplate, tableName);
    }

    void assertNumUsers(int expected) {
        assertEquals(expected, countRowsInTable("seats"),
                "Number of rows in the [user] table.");
    }


//    @Test
//    @Sql("/data.sql")
//    void databaseTest() {
//
//        // run code that uses the test schema and data
//    }

    @Test
//    @Sql("/data.sql")
    void testGetOrders() throws Exception {
        // Setup
        given(mockOrderRepository.findAll()).willReturn(Collections.emptyList());
        // Run the test


        final MockHttpServletResponse response = mockMvc.perform(get("/orders")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentLength()).isEqualTo(0);
    }

    @Test
    void testPurchaseSeat() throws Exception {
        // Setup
        final Seat seat = new Seat(1, 1, 10, true);
        Map<String, Integer> body = Map.of("row", 1, "column", 1);
        final Order order = new Order(seat);


//        when(mockSeatRepository.findByRowAndColumn(1, 1)).thenReturn(Optional.of(new Seat(1, 1, 10, true)));
//        when(mockSeatRepository.save(any(Seat.class))).thenReturn(new Seat(1, 1, 10, true));
//        when(mockOrderRepository.save(any(Order.class))).thenReturn(new Order(seat));
        when(orderService.purchaseSeat(seat)).thenReturn(order);
//        given(orderService.purchaseSeat(seat)).willReturn(order);
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/purchase")
                        .content(objectMapper.writeValueAsString(seat)).contentType(MediaType.APPLICATION_JSON_VALUE).characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        String responseBody = response.getContentAsString();


//        Order order = objectMapper.readValue(responseBody, Order.class);
//        assertThat(order.getToken()).isNotEmpty();
        assertThat(order.getTicket().getRow()).isEqualTo(1);
        assertThat(order.getTicket().getColumn()).isEqualTo(1);
        assertThat(responseBody).isEqualTo("{\"token\":\"" + order.getToken() + "\",\"ticket\":{\"row\":1,\"column\":1}}");
    }

    @Test
    void testRefundTicket() throws Exception {
        // Setup
        final Seat seat = new Seat(1, 1, 10, true);
        final Order order = new Order("token",seat, true);
        final Token token = new Token("token");

        when(orderService.refundTicket(token)).thenReturn(order);


        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/return")
                        .content(objectMapper.writeValueAsString(token)).contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8").accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"returned_ticket\":{\"row\":1,\"column\":1},\"refund\":10}");
    }

    @Test
    void testStatsEndpoint() throws Exception {
        // Setup
        when(orderService.getStats("super_secret")).thenReturn(new Stats(0, 81, 0));
        // Run the test
        final MockHttpServletResponse response = mockMvc.perform(post("/stats").queryParam("password","super_secret")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonStats.write(new Stats(0, 81, 0)).getJson());

    }

    @Test
    void testStatsEndpoint_ThrowsOrderException() throws Exception {
        // Setup
        when(orderService.getStats("password")).thenThrow(new OrderException("The password is wrong!", HttpStatus.UNAUTHORIZED));
//        given(orderService.getStats("password")).willThrow(new OrderException("The password is wrong!", HttpStatus.UNAUTHORIZED));
        // Run the test
//        assertThrows(OrderException.class, () -> orderService.getStats("password"));
        final MockHttpServletResponse response = mockMvc.perform(post("/stats").queryParam("password","password")
                        .characterEncoding("utf-8")
                        .accept(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // Verify the results
        assertThat(response.getStatus()).isEqualTo(HttpStatus.UNAUTHORIZED.value());
        assertThat(response.getContentAsString()).isEqualTo("{\"error\":\"The password is wrong!\"}");
    }
}