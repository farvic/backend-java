package cinema.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

class SeatTest {

    private Seat seatUnderTest;

    @BeforeEach
    void setUp() {
        seatUnderTest = new Seat(1, 1, 10, false);
    }

    @Test
    public void testConstructorAndGetters() throws Exception {
        assertThat(seatUnderTest.getId(),is(0));
        assertThat(seatUnderTest.getRow(),is(1));
        assertThat(seatUnderTest.getColumn(),is(1));
        assertThat(seatUnderTest.getPrice(),is(10));
        assertThat(seatUnderTest.isAvailable(),is(false));
    }

    @Test
    void testSetId() {
        // Setup
        final int id = 2;

        // Run the test
        seatUnderTest.setId(id);

        // Verify the results
        assertThat(seatUnderTest.getId(),is(2));
    }
    @Test
    void testSetPrice() {
        // Setup
        final int price = 2;

        // Run the test
        seatUnderTest.setPrice(price);

        // Verify the results
        assertThat(seatUnderTest.getPrice(),is(2));
    }
    @Test
    void testSetRow() {
        // Setup
        final int row = 2;

        // Run the test
        seatUnderTest.setRow(row);

        // Verify the results
        assertThat(seatUnderTest.getRow(),is(2));
    }
    @Test
    void testSetColumn() {
        // Setup
        final int column = 2;

        // Run the test
        seatUnderTest.setColumn(column);

        // Verify the results
        assertThat(seatUnderTest.getColumn(),is(2));
    }

    @Test
    void testSetAvailable() {
        // Setup
        final boolean available = false;

        // Run the test
        seatUnderTest.setIsAvailable(available);

        // Verify the results
        assertThat(seatUnderTest.isAvailable(),is(false));
    }


}