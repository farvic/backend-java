package account.controllers;

import account.dto.PaymentDto;
import account.dto.PaymentRequest;
import account.services.PaymentServiceImpl;
import account.dto.ResponseBody;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    public PaymentController(PaymentServiceImpl paymentService) {
        this.paymentService = paymentService;
    }


    @GetMapping("/empl/payment")
    public ResponseEntity<?> getPayments(
            @RequestParam(value = "period", required = false) String period,
            @AuthenticationPrincipal UserDetails userDetails)
    {

        List<PaymentDto> payments = paymentService.getPayments(period, userDetails);
        if (period != null)
            return ResponseEntity.ok(payments.get(0));
        else
            return ResponseEntity.ok(payments);
    }

    @PostMapping("/acct/payments")
    public ResponseEntity<ResponseBody> postPayment(@Valid @RequestBody List<PaymentRequest> paymentRequest) {
        ResponseBody responseBody = paymentService.postPayment(paymentRequest);
        return ResponseEntity.ok(responseBody);
    }

    @PutMapping("/acct/payments")
    public ResponseEntity<ResponseBody> putPayment(@Valid @RequestBody  PaymentRequest paymentRequest) {
        ResponseBody responseBody = paymentService.putPayment(paymentRequest);
        return ResponseEntity.ok(responseBody);
    }

}
