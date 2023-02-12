package cinema.domain;

import javax.persistence.*;
import java.util.UUID;


@Entity
@Table(name = "orders")
public class Order {
    @Id
    @Column(name = "token")
    private String token;

    @JoinColumn(name = "seat_id", referencedColumnName = "seat_id")
    @OneToOne(cascade = CascadeType.ALL)

    private Seat ticket;

    public Order() {
    }

    public Order(Seat ticket) {
        this.ticket = ticket;
        this.token = UUID.randomUUID().toString();
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Seat getTicket() {
        return ticket;
    }

    public void setTicket(Seat ticket) {
        this.ticket = null;
    }

    @Override
    public String toString() {
        return "Order{" +
                "token=" + token +
                ", ticket=" + ticket +
                '}';
    }
}
