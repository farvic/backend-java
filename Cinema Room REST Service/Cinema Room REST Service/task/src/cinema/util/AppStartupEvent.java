//package cinema.util;
//
//import com.example.gradleexample.domain.Seat;
//import com.example.gradleexample.repo.SeatRepository;
//import org.springframework.boot.context.event.ApplicationReadyEvent;
//import org.springframework.context.ApplicationListener;
//import org.springframework.stereotype.Component;
//
//@Component
//public class AppStartupEvent implements ApplicationListener<ApplicationReadyEvent> {
//    private final SeatRepository seatRepository;
//
//
//    public AppStartupEvent(SeatRepository seatRepository) {
//        this.seatRepository = seatRepository;
//    }
//
//    @Override
//    public void onApplicationEvent(ApplicationReadyEvent event) {
//
//        Iterable<Seat> seats = this.seatRepository.findAll();
//        seats.forEach(System.out::println);
//    }
//}
