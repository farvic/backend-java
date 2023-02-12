package cinema.repo;

import cinema.domain.Order;
import cinema.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {
        Order findByToken(String token);


}
