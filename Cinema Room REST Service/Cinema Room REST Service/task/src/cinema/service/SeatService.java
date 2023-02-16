package cinema.service;

import cinema.domain.Order;
import cinema.domain.Seat;

import cinema.domain.Token;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;


@Service
public interface SeatService {

    public List<Seat> getAllSeats();

    public Order purchaseSeat(Seat seat);

    public Order refundTicket(Token token);

    public Seat getSeatByRowAndColumn(@RequestBody Seat seat);

}
