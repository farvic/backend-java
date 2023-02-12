package cinema.repo;


import cinema.domain.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Integer> {

    @Query("SELECT s FROM Seat s WHERE s.cinema.id = ?1")
    List<Seat> findByCinemaId(int cinemaId);

    List<Seat> findByCinemaIdAndIsAvailable(int cinemaId, boolean isAvailable);
    Seat findByRowAndColumn(int row, int column);



//    List<Seat> findAllByIsAvailable(boolean isAvailable);


}
