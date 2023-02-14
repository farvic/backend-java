package cinema.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

class StatsTest {

    private Stats statsUnderTest;

    @BeforeEach
    void setUp() {
        statsUnderTest = new Stats(0, 81, 0);
    }

    @Test
    void testConstructorAndGetters() throws Exception {
        assertThat(statsUnderTest.getCurrentIncome(),is(0));
        assertThat(statsUnderTest.getNumberOfAvailableSeats(),is(81));
        assertThat(statsUnderTest.getNumberOfPurchasedTickets(),is(0));

    }


    @Test
    public void testSetCurrentIncome() {
        int newIncome = 200;
        statsUnderTest.setCurrentIncome(newIncome);
        assertEquals(newIncome, statsUnderTest.getCurrentIncome());
    }
    @Test
    public void testSetNumberOfAvailableSeats() {
        int newNumberOfSeats = 40;
        statsUnderTest.setNumberOfAvailableSeats(newNumberOfSeats);
        assertEquals(newNumberOfSeats, statsUnderTest.getNumberOfAvailableSeats());
    }

    @Test
    public void testSetNumberOfPurchasedTickets() {
        int newNumberOfTickets = 30;
        statsUnderTest.setNumberOfPurchasedTickets(newNumberOfTickets);
        assertEquals(newNumberOfTickets, statsUnderTest.getNumberOfPurchasedTickets());
    }

}
