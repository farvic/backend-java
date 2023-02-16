package cinema.web;

import cinema.domain.Order;
import cinema.domain.Seat;
import cinema.domain.Token;
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
        return seatService.getAllSeats();
    }

    @PostMapping(value="/purchase", consumes = "application/json", produces = "application/json")
    public Order purchaseSeat(@RequestBody Seat seat)  {
        return seatService.purchaseSeat(seat);
    }

    @PostMapping("/return")
    public Order refundTicket(@RequestBody Token token)  {
        return seatService.refundTicket(token);
    }

    @GetMapping("/seat")
    public Seat getSeatByRowAndColumn(@RequestBody Seat seat) {
        return seatService.getSeatByRowAndColumn(seat);
    }

}