package account.services;

import account.domain.User;
import account.repositories.PaymentRepository;
import account.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {


    final private PaymentRepository paymentRepository;
    final private UserRepository userRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    @Override
    public User getEmployee() {
        return null;
    }
}
