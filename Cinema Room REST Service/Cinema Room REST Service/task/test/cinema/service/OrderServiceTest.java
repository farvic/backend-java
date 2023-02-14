package cinema.service;

import cinema.domain.Order;
import cinema.domain.Seat;
import cinema.domain.Stats;
import cinema.domain.Token;
import cinema.errors.OrderException;
import cinema.repo.OrderRepository;
import cinema.repo.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @Mock
    private OrderRepository mockOrderRepository;
    @Mock
    private SeatRepository mockSeatRepository;

    private OrderService orderServiceUnderTest;

    @BeforeEach
    void setUp() {
        orderServiceUnderTest = new OrderService(mockOrderRepository, mockSeatRepository);
    }

    @Test
    void testPurchaseSeat() {
        // Setup
        final Seat seat = new Seat(1, 1, 10, true);
        when(mockSeatRepository.findByRowAndColumn(1, 1)).thenReturn(Optional.of(new Seat(1, 1, 10, true)));
        when(mockSeatRepository.save(any(Seat.class))).thenReturn(new Seat(1, 1, 10, true));

        // Configure OrderRepository.save(...).
        final Order order = new Order("token", new Seat(1, 1, 10, false), true);
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order);

        // Run the test
        final Order result = orderServiceUnderTest.purchaseSeat(seat);

        // Verify the results
        verify(mockSeatRepository).save(any(Seat.class));
        verify(mockOrderRepository).save(any(Order.class));
    }

    @Test
    void testPurchaseSeatNotAvailable() throws OrderException{
        // Setup
        final Seat seat = new Seat(1, 1, 10, false);
        when(mockSeatRepository.findByRowAndColumn(1, 1)).thenReturn(Optional.of(new Seat(1, 1, 10, false)));

        // Run the test
        OrderException thrown = assertThrows(OrderException.class, () -> orderServiceUnderTest.purchaseSeat(seat));

        // Verify the results
        String result = "The ticket has been already purchased!";
        assertEquals(result, thrown.getError());
    }

    @Test
    void testPurchaseSeatRowOrColumnOutOfBounds() throws Exception { //
        // Setup
        final Seat seat = new Seat(1, 1, 10, true);
        Throwable thrown = catchThrowable(() -> mockSeatRepository.findByRowAndColumn(11, 11));
//        when(mockSeatRepository.findByRowAndColumn(11, 11)).thenReturn(null);

        assertThat(thrown).isInstanceOf(OrderException.class);

//        // Run the test
//        OrderException thrown = assertThrows(OrderException.class, () -> mockSeatRepository.findByRowAndColumn(11, 11));
//
//        // Verify the results
//        String result = "The ticket has been already purchased!";
//        assertEquals(result, thrown.getError());
    }

    @Test
    void testPurchaseSeat_SeatRepositoryFindByRowAndColumnReturnsAbsent() {
        // Setup
        final Seat seat = new Seat(0, 0, 0, false);
        when(mockSeatRepository.findByRowAndColumn(0, 0)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> orderServiceUnderTest.purchaseSeat(seat)).isInstanceOf(OrderException.class);
    }

    @Test
    void testRefundTicket() {
        // Setup
        final Token token = new Token("token");

        // Configure OrderRepository.findByToken(...).
        final Order order = new Order("token", new Seat(0, 0, 0, false), false);
        when(mockOrderRepository.findByToken("token")).thenReturn(order);

        when(mockSeatRepository.save(any(Seat.class))).thenReturn(new Seat(0, 0, 0, false));

        // Run the test
        final Order result = orderServiceUnderTest.refundTicket(token);

        // Verify the results
        verify(mockSeatRepository).save(any(Seat.class));
        verify(mockOrderRepository).delete(any(Order.class));
    }

    @Test
    void testRefundTicket_OrderRepositoryFindByTokenReturnsNull() {
        // Setup
        final Token token = new Token("token");
        when(mockOrderRepository.findByToken("token")).thenReturn(null);

        // Run the test
        assertThatThrownBy(() -> orderServiceUnderTest.refundTicket(token)).isInstanceOf(OrderException.class);
    }

    @Test
    void testGetOrders() {
        // Setup
        // Configure OrderRepository.findAll(...).
        final List<Order> orders = List.of(new Order("token", new Seat(0, 0, 0, false), false));
        when(mockOrderRepository.findAll()).thenReturn(orders);

        // Run the test
        final List<Order> result = orderServiceUnderTest.getOrders();

        // Verify the results
    }

    @Test
    void testGetOrders_OrderRepositoryReturnsNoItems() {
        // Setup
        when(mockOrderRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<Order> result = orderServiceUnderTest.getOrders();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetStats() {
        // Setup
        // Configure OrderRepository.findAll(...).
        final List<Order> orders = List.of(new Order("token", new Seat(0, 0, 0, false), false));
        when(mockOrderRepository.findAll()).thenReturn(orders);

        // Run the test
        final Stats result = orderServiceUnderTest.getStats("super_secret");

        // Verify the results
    }

    @Test
    void testGetStats_OrderRepositoryReturnsNoItems() {
        // Setup
        when(mockOrderRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final Stats result = orderServiceUnderTest.getStats("super_secret");

        // Verify the results
    }
}
