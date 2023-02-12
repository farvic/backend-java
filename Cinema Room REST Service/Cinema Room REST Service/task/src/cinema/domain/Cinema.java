package cinema.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "cinema")
public class Cinema {

    @Id
    @JsonIgnore
    private int id;

    @Column(name = "total_rows")
    private final int totalRows = 9;

    @Column(name = "total_columns")
    private final int totalColumns = 9;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    @JoinColumn(name = "tutorial_id")
    private List<Seat> availableSeats;

//    @OneToMany(mappedBy = "token", fetch = FetchType.LAZY)
//    private List<Order> orders;

//    public Cinema() {
//        this.totalRows = 9;
//        this.totalColumns = 9;
//    }
//
//    public Cinema(List<Seat> availableSeats) {
//        this.totalRows = 9;
//        this.totalColumns = 9;
//        this.availableSeats = availableSeats;
//    }

    public Cinema(int id, List<Seat> availableSeats) {
        this.id = id;
        this.availableSeats = availableSeats;
    }

    protected Cinema() {

    }


//    public Cinema(List<Seat> availableSeats, List<Order> orders) {
//        this.totalRows = 9;
//        this.totalColumns = 9;
//        this.availableSeats = availableSeats;
//        this.orders = orders;
//    }

//    protected Cinema() {
////        this(null);
//        totalColumns = 0;
//    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



//    @JsonIgnore
//    public List<Order> getOrders() {
//        return orders;
//    }
//
//    public void setOrders(List<Order> orders) {
//        this.orders = orders;
//    }

    public int getTotalRows() {
        return totalRows;
    }

    public int getTotalColumns() {
        return totalColumns;
    }


    public List<Seat> getAvailableSeats() {
        return availableSeats;
    }

    public void setAvailableSeats(List<Seat> availableSeats) {
        this.availableSeats = availableSeats;
    }


    @Override
    public String toString() {
        return "Cinema{" +
                "id=" + id +
                ", totalRows=" + totalRows +
                ", totalColumns=" + totalColumns +
                ", availableSeats=" + availableSeats +
                '}';
    }
}
