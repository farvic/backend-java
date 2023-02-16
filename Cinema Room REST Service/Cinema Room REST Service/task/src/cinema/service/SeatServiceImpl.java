package cinema.service;

import cinema.domain.Order;
import cinema.domain.Seat;
import cinema.domain.Token;
import cinema.errors.OrderException;
import cinema.repo.CinemaRepository;
import cinema.repo.OrderRepository;
import cinema.repo.SeatRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class SeatServiceImpl implements SeatService{

    final private CinemaRepository cinemaRepository;
    final private SeatRepository seatRepository;

    final private OrderRepository orderRepository;
    public SeatServiceImpl(CinemaRepository cinemaRepository, SeatRepository seatRepository, OrderRepository orderRepository) {
        this.cinemaRepository = cinemaRepository;
        this.seatRepository = seatRepository;
        this.orderRepository = orderRepository;

    }

    @Override
    public List<Seat> getAllSeats() {
        if (!cinemaRepository.existsById(1)) {
            throw new ResourceNotFoundException("The cinema is closed");
        }
        return seatRepository.findByCinemaId(1);
    }

    @Override
    public Order purchaseSeat(Seat seat) {

        Seat _seat = seatRepository.findByRowAndColumn(seat.getRow(), seat.getColumn())
                .orElseThrow(() -> new OrderException("The number of a row or a column is out of bounds!"));


        if (_seat.isAvailable()) {

            _seat.setIsAvailable(false);

            seatRepository.save(_seat);

            Order order = new Order(_seat);

            orderRepository.save(order);

            return order;

        } else {
            throw new OrderException("The ticket has been already purchased!");
        }
    }

    @Override
    public Order refundTicket(Token token) {

        Order order = orderRepository.findByToken(token.getToken());

        if (order == null) {
            throw new OrderException("Wrong token!");
        }

        Seat seat = order.getTicket();

        seat.setIsAvailable(true);

        seatRepository.save(seat);

        Order _order = new Order(order.getToken(),order.getTicket(), true);

        order.setTicket(null);

        orderRepository.delete(order);

        return _order;

    }



    @Override
    public Seat getSeatByRowAndColumn(@RequestBody Seat seat) {
        return seatRepository.findByRowAndColumn(seat.getRow(), seat.getColumn())
                .orElseThrow(() -> new OrderException("The number of a row or a column is out of bounds!"));
    }

}
