package cinema.service;

import cinema.domain.Seat;
import cinema.errors.OrderException;
import cinema.repo.CinemaRepository;
import cinema.repo.SeatRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class SeatService {

    final private CinemaRepository cinemaRepository;
    final private SeatRepository seatRepository;
    public SeatService(CinemaRepository cinemaRepository, SeatRepository seatRepository) {
        this.cinemaRepository = cinemaRepository;
        this.seatRepository = seatRepository;
    }

    @GetMapping("/seats-list")
    public List<Seat> getAlSeats() {
        if (!cinemaRepository.existsById(1)) {
            throw new ResourceNotFoundException("The cinema is closed");
        }
        return seatRepository.findByCinemaId(1);
    }

    @GetMapping("/seat")
    public Seat getSeatByRowAndColumn(@RequestBody Seat seat) {
        return seatRepository.findByRowAndColumn(seat.getRow(), seat.getColumn())
                .orElseThrow(() -> new OrderException("The number of a row or a column is out of bounds!"));
    }

}
