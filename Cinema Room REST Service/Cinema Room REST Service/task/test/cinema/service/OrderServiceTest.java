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


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
        final Order order = new Order("token", new Seat(1, 1, 10, false), false);
        when(mockOrderRepository.save(any(Order.class))).thenReturn(order);

        // Run the test
        final Order result = orderServiceUnderTest.purchaseSeat(seat);

        // Verify the results
        assertFalse(result.getTicket().isAvailable());
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
    void testPurchaseSeatRowOrColumnOutOfBounds() {
        // Setup
        final Seat seat = new Seat(0, 0, 0, false);
        when(mockSeatRepository.findByRowAndColumn(0, 0)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> orderServiceUnderTest.purchaseSeat(seat))
                .isInstanceOf(OrderException.class).
                hasFieldOrPropertyWithValue("error", "The number of a row or a column is out of bounds!");

    }
    @Test
    void testRefundTicket() {
        // Setup
        final Token token = new Token("token");

        // Configure OrderRepository.findByToken(...).
        final Order order = new Order("token", new Seat(0, 0, 0, false), true);
        when(mockOrderRepository.findByToken("token")).thenReturn(order);

        // Run the test
        final Order result = orderServiceUnderTest.refundTicket(token);

        // Verify the results
        assertTrue(result.getTicket().isAvailable());
        verify(mockOrderRepository).delete(any(Order.class));
    }

    @Test
    void testRefundInvalidToken() {
        // Setup
        final Token token = new Token("token");
        when(mockOrderRepository.findByToken("token")).thenReturn(null);

        // Run the test and verify the results
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
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void testGetOrdersReturnEmptyList() {
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
        final List<Order> orders = List.of(new Order("token", new Seat(0, 0, 10, false), false));
        when(mockOrderRepository.findAll()).thenReturn(orders);

        // Run the test
        final Stats result = orderServiceUnderTest.getStats("super_secret");

        // Verify the results
        assertThat(result.getCurrentIncome()).isEqualTo(10);
        assertThat(result.getNumberOfAvailableSeats()).isEqualTo(80);
        assertThat(result.getNumberOfPurchasedTickets()).isEqualTo(1);
    }

    @Test
    void testGetStats_OrderRepositoryReturnsNoItems() {
        // Setup
        when(mockOrderRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final Stats result = orderServiceUnderTest.getStats("super_secret");

        // Verify the results
        assertThat(result.getCurrentIncome()).isEqualTo(0);
        assertThat(result.getNumberOfAvailableSeats()).isEqualTo(81);
        assertThat(result.getNumberOfPurchasedTickets()).isEqualTo(0);
    }

    @Test
    void testGetStatsAfterRefund() {
        // Setup
        // Configure OrderRepository.findAll(...).
        final Token token = new Token("token");

        // Configure OrderRepository.findByToken(...).
        final Order order = new Order("token", new Seat(0, 0, 10, false), false);
        when(mockOrderRepository.findByToken("token")).thenReturn(order);

        // Run the test
        orderServiceUnderTest.refundTicket(token);

        final Stats result = orderServiceUnderTest.getStats("super_secret");

        // Verify the results
        assertThat(result.getCurrentIncome()).isEqualTo(0);
        assertThat(result.getNumberOfAvailableSeats()).isEqualTo(81);
        assertThat(result.getNumberOfPurchasedTickets()).isEqualTo(0);
    }


}
