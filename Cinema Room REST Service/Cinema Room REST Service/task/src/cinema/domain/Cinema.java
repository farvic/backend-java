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
    @JoinColumn(name = "cinema_id")
    private List<Seat> availableSeats;

    protected Cinema() {

    }
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

}
