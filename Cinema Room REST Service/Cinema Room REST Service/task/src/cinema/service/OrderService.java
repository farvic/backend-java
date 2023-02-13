package cinema.service;

import cinema.domain.Order;
import cinema.domain.Seat;
import cinema.domain.Stats;
import cinema.domain.Token;
import cinema.repo.OrderRepository;
import cinema.repo.SeatRepository;
import cinema.errors.OrderException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final SeatRepository seatRepository;

    private static final Logger LOGGER =  LoggerFactory.getLogger(OrderRepository.class);

    public OrderService(OrderRepository orderRepository, SeatRepository seatRepository) {
        this.orderRepository = orderRepository;
        this.seatRepository = seatRepository;
    }

    public Order purchaseSeat(Seat seat) {

        Seat _seat = seatRepository.findByRowAndColumn(seat.getRow(), seat.getColumn())
                .orElseThrow(() -> new OrderException("The number of a row or a column is out of bounds!"));

        LOGGER.info("Seat " + _seat);

        if (_seat.isAvailable()) {

            _seat.setIsAvailable(false);

            seatRepository.save(_seat);

            Order order = new Order(_seat);

            LOGGER.info("Order " + order);

            orderRepository.save(order);

            return order;

        } else {
            throw new OrderException("The ticket has been already purchased!");
        }
    }

    public Order refundTicket(Token token) {

        Order order = orderRepository.findByToken(token.getToken());

        if (order == null) {
            throw new OrderException("Wrong token!");
        }

        Seat seat = order.getTicket();

        seat.setIsAvailable(true);

        LOGGER.info("Refund ticket: " + seat.isAvailable());

        seatRepository.save(seat);

        orderRepository.delete(order);

        order.setReturn(true);

        return order;

    }

    public Stats getStats(String password) {

        int numberOfPurchasedTickets;
        int currentIncome = 0;
        int numberOfAvailableSeats;

        if (password == null || !password.equals("super_secret")) {
            throw new OrderException("The password is wrong!", HttpStatus.UNAUTHORIZED);
        }

        List<Order> order = orderRepository.findAll();

        numberOfPurchasedTickets = order.size();

        numberOfAvailableSeats = 81 - numberOfPurchasedTickets;

        for (Order o : order) {
            currentIncome += o.getTicket().getPrice();
        }
        return new Stats(currentIncome, numberOfAvailableSeats, numberOfPurchasedTickets);
    }
}