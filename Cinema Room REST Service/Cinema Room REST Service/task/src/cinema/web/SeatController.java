package cinema.web;

import cinema.domain.Seat;
import cinema.repo.CinemaRepository;
import cinema.repo.SeatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@CrossOrigin(origins = "http://localhost:28852")
@RestController
public class SeatController {


    private CinemaRepository cinemaRepository;
    private SeatRepository seatRepository;

    private static final Logger LOGGER =  LoggerFactory.getLogger(CinemaRepository.class);
    public SeatController(CinemaRepository cinemaRepository, SeatRepository seatRepository) {
        this.cinemaRepository = cinemaRepository;
        this.seatRepository = seatRepository;
    }

    @GetMapping("/allseats")
    public ResponseEntity<List<Seat>> getAvailableSeats() {

//        Cinema cinema = cinemaRepository.findById(1)
//                .orElseThrow(() -> new ResourceNotFoundException("The cinema is closed"));
        if (!cinemaRepository.existsById(1)) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Seat> seats = seatRepository.findByCinemaId(1);

        return new ResponseEntity<>(seats, HttpStatus.OK);

    }

    @GetMapping("/seat")
    public ResponseEntity<Object> getSeatById(@RequestBody Seat seat) {

        HashMap<String, Object> responseBody = new HashMap<>();

        Seat _seat = seatRepository.findByRowAndColumn(seat.getRow(), seat.getColumn());

        if (_seat == null) {
            responseBody.put("error", "The number of a row or a column is out of bounds!");
            return new ResponseEntity<>(responseBody,HttpStatus.BAD_REQUEST);

        }
        responseBody.putAll(Map.of("row",_seat.getRow(),"column",_seat.getColumn(),"price",_seat.getPrice()));
        return new ResponseEntity<>(responseBody, HttpStatus.OK);

    }


}