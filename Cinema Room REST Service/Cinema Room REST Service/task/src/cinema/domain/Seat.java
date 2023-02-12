package cinema.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@Table(name = "seats")
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    @JsonIgnore
    private long id;
    @Column(name = "seat_row")
    private int row;
    @Column(name = "seat_column")
    private int column;

    @Column(name = "price")
    private int price;


    @Column(name = "available")

    private boolean isAvailable;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "cinema_id",nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Cinema cinema;

    protected Seat() {
    }

    public Seat(int row, int column) {
        this.row = row;
        this.column = column;
        this.price = price;
    }
    public Seat(int row, int column, int price, boolean isAvailable) {
        this.row = row;
        this.column = column;
        this.price = price;
        this.isAvailable = isAvailable;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }


    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }


    @JsonIgnore
    public boolean isAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

    @Override
    public String toString() {
        return "Seat [id=" + id + ", row=" + row + ", column=" + column + "]";
    }



}