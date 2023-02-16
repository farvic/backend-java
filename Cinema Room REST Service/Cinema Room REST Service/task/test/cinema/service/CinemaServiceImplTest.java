package cinema.service;

import cinema.domain.Cinema;
import cinema.domain.Seat;
import cinema.repo.CinemaRepository;
import cinema.repo.SeatRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;


import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CinemaServiceImplTest {

    @Mock
    private CinemaRepository mockCinemaRepository;
    @Mock
    private SeatRepository mockSeatRepository;

    @InjectMocks
    private CinemaServiceImpl cinemaServiceImplUnderTest;


    @Test
    void testGetCinemaSeats() {
        // Setup
        Seat seat1 = new Seat(1, 1, 10, true);
        Seat seat2 = new Seat(1, 2, 10, true);
        List<Seat> seats = List.of(seat1, seat2);
        when(mockCinemaRepository.findById(1)).thenReturn(Optional.of(new Cinema(seats)));
        when(mockSeatRepository.findByCinemaIdAndIsAvailable(1, true)).thenReturn(seats);


        // Run the test
        final Cinema result = cinemaServiceImplUnderTest.getCinemaSeats();

        // Verify the results
        Assertions.assertEquals(result.getAvailableSeats(), seats);

    }

    @Test
    void testGetCinemaSeats_CinemaRepositoryReturnsAbsent() {
        // Setup
        when(mockCinemaRepository.findById(0)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> cinemaServiceImplUnderTest.getCinemaSeats())
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void testGetCinemaSeats_SeatRepositoryReturnsNoItems() {
        // Setup
        List<Seat> seats = Collections.emptyList();
        when(mockCinemaRepository.findById(1)).thenReturn(Optional.of(new Cinema(seats)));
        when(mockSeatRepository.findByCinemaIdAndIsAvailable(1, true)).thenReturn(seats);


        // Run the test
        final Cinema result = cinemaServiceImplUnderTest.getCinemaSeats();

        // Verify the results
        Assertions.assertEquals(result.getAvailableSeats(), seats);
    }
}
