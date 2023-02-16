package cinema.service;

import cinema.domain.Order;

import cinema.domain.Stats;


import java.util.List;

public interface OrderService {

    public List<Order> getOrders();

    public Stats getStats(String password);
}
