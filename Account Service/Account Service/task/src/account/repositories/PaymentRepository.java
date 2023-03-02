package account.repositories;

import account.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findAllByEmployee(String employee);
    Optional<Payment> findByEmployeeAndPeriod(String employee, YearMonth period);
}
