package account.services;


import account.dto.PaymentDto;
import account.dto.PaymentRequest;
import account.dto.ResponseBody;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PaymentService {
    List<PaymentDto> getPayments(String period, UserDetails userDetails);

    ResponseBody postPayment(List<PaymentRequest> paymentRequest);

    ResponseBody putPayment(PaymentRequest paymentRequest);
}
