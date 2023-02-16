package cinema.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CinemaTest {

    private Cinema cinemaUnderTest;

    @BeforeEach
    void setUp() {
        List<Seat> seats = List.of(
                new Seat(1, 1, 10, true),
                new Seat(1, 2, 10, true)
        );
        cinemaUnderTest = new Cinema(seats);
    }

    @Test
    void testConstructorAndGetters() throws Exception {
        assertEquals(9, cinemaUnderTest.getTotalRows());
        assertEquals(9, cinemaUnderTest.getTotalColumns());
        assertEquals(2, cinemaUnderTest.getAvailableSeats().size());
    }

    @Test
    public void testSetAvailableSeats() {
        List<Seat> newSeats = List.of(
                new Seat(1, 1, 10, true),
                new Seat(1, 2, 10, true),
                new Seat(1, 3, 10, true)
        );
        cinemaUnderTest.setAvailableSeats(newSeats);
        assertEquals(cinemaUnderTest.getAvailableSeats(), newSeats);
    }
}
