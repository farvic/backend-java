package cinema.service;

import cinema.domain.Cinema;
import cinema.domain.Seat;
import cinema.repo.CinemaRepository;
import cinema.repo.SeatRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CinemaService {

    private final CinemaRepository cinemaRepository;
    private final SeatRepository seatRepository;

    public CinemaService(CinemaRepository cinemaRepository, SeatRepository seatRepository) {
        this.cinemaRepository = cinemaRepository;
        this.seatRepository = seatRepository;
    }

    public Cinema getCinemaSeats() {

        Cinema cinema = cinemaRepository.findById(1)
                .orElseThrow(() -> new ResourceNotFoundException("The cinema is closed"));

        List<Seat> seats = seatRepository.findByCinemaIdAndIsAvailable(1, true);

        cinema.setAvailableSeats(seats);

        return cinema;
    }
}
