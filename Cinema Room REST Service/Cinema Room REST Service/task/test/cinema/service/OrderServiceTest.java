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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;


import static org.assertj.core.api.Assertions.*;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {


    @Mock
    private OrderRepository mockOrderRepository;

    @InjectMocks
    private OrderServiceImpl orderServiceUnderTest;

    @Test
    void testGetOrders() {

        // Setup
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
        final List<Order> orders = List.of(new Order("token", new Seat(0, 0, 10, false), false));
        when(mockOrderRepository.findAll()).thenReturn(orders);

        // Run the test
        final Stats result = orderServiceUnderTest.getStats("super_secret");

        // Verify the results
        verify(mockOrderRepository).findAll();
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
        verify(mockOrderRepository).findAll();
        assertThat(result.getCurrentIncome()).isEqualTo(0);
        assertThat(result.getNumberOfAvailableSeats()).isEqualTo(81);
        assertThat(result.getNumberOfPurchasedTickets()).isEqualTo(0);
    }
}
