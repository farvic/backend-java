package cinema.web;

import cinema.domain.Seat;
import cinema.repo.CinemaRepository;
import cinema.repo.SeatRepository;
import cinema.service.SeatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin(origins = "http://localhost:28852")
@RestController
public class SeatController {

    private final SeatService seatService;

    private static final Logger LOGGER =  LoggerFactory.getLogger(CinemaRepository.class);
    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @GetMapping("/seats-list")
    public List<Seat> getAvailableSeats() {
        return seatService.getAlSeats();
    }

    @GetMapping("/seat")
    public Seat getSeatById(@RequestBody Seat seat) {
        return seatService.getSeatById(seat);
    }

}