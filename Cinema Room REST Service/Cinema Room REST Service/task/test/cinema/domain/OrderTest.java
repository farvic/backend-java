package cinema.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class OrderTest {

    @Mock
    private Seat seatMock;
    private Order orderUnderTest;

    @BeforeEach
    void setUp() {
        seatMock = new Seat(1, 1, 10, true);
        orderUnderTest = new Order(seatMock);
    }

    @Test
    public void testConstructorAndGetters() throws Exception {
        assertNotNull(orderUnderTest.getToken());
        assertFalse(orderUnderTest.isReturn());
        assertEquals(seatMock, orderUnderTest.getTicket());
    }
    @Test
    public void testSetTicket() {
        Seat seat2 = new Seat(2, 2, 2);
        orderUnderTest.setTicket(seat2);
        Seat orderSeat = orderUnderTest.getTicket();
        assertEquals(seat2.getId(), orderSeat.getId());
        assertEquals(seat2.getColumn(), orderSeat.getColumn());
        assertEquals(seat2.getRow(), orderSeat.getRow());
        assertEquals(seat2.getPrice(), orderSeat.getPrice());
        assertEquals(seat2.isAvailable(), orderSeat.isAvailable());
    }
   
    @Test
    public void testSetReturn() {
        orderUnderTest.setReturn(true);
        assertTrue(orderUnderTest.isReturn());
    }

    @Test
    public void testToString() {
        String expectedString = "Order{token=" + orderUnderTest.getToken() + ", ticket=" + seatMock + "}";
        assertEquals(expectedString, orderUnderTest.toString());
    }






}
