package account.services;

import account.domain.Payment;
import account.domain.User;
import account.dto.PaymentDto;
import account.dto.PaymentRequest;
import account.dto.ResponseBody;
import account.errors.UserException;
import account.repositories.PaymentRepository;
import account.repositories.UserRepository;
import account.utils.PaymentUtils;
import account.utils.YearMonthConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.time.YearMonth;
import java.util.*;

@Service
public class PaymentServiceImpl implements PaymentService {


    final private PaymentRepository paymentRepository;
    final private UserRepository userRepository;

    final static Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private final PaymentConversionService paymentConversionService;


    public PaymentServiceImpl(PaymentRepository paymentRepository, UserRepository userRepository, PaymentConversionService paymentConversionService) {
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
        this.paymentConversionService = paymentConversionService;
    }

    @Override
    public List<PaymentDto> getPayments(String period, UserDetails userDetails) {
        User user = userRepository.findByEmail(userDetails.getUsername()).orElseThrow(() -> new UserException("User not found"));

        if (period != null) {
            Payment payment = paymentRepository.findByEmployeeAndPeriod(userDetails.getUsername(), YearMonthConverter.convert(period)).orElseThrow(() -> new UserException("Payment not found"));
            return PaymentUtils.mapPaymentsToPaymentDtos(Collections.singletonList(payment), user);
        }

        List<Payment> payments = paymentRepository.findAllByEmployee(userDetails.getUsername());
        return PaymentUtils.mapPaymentsToPaymentDtos(payments, user);
    }

    @Override
    @Transactional
    public ResponseBody postPayment(List<PaymentRequest> paymentRequests) {
        ResponseBody responseBody = new ResponseBody();
        Map<String, List<Payment>> paymentMap = new HashMap<>();

        // Check for duplicate payments for each employee
        paymentRequests.forEach(paymentRequest -> {
            Payment payment = paymentConversionService.convert(paymentRequest, Payment.class);
            if (paymentMap.containsKey(paymentRequest.getEmployee())) {
                List<Payment> employeePayments = paymentMap.get(paymentRequest.getEmployee());
                employeePayments.stream()
                        .filter(p -> p.getPeriod().equals(payment.getPeriod()))
                        .findFirst()
                        .ifPresent(p -> {
                            throw new UserException("Bad Request");
                        });
                employeePayments.add(payment);
            } else {
                List<Payment> employeePayments = new ArrayList<>();
                employeePayments.add(payment);
                paymentMap.put(paymentRequest.getEmployee(), employeePayments);
            }
        });

        LOGGER.info("Payments: " + paymentMap);

        // Save payments for each employee
        paymentMap.forEach((employee, employeePayments) -> {
            User user = userRepository.findByEmail(employee).orElseThrow(() -> new UserException("Bad Request"));
            LOGGER.info("Employee: " + employee + " payments: " + employeePayments);
            employeePayments.forEach(payment -> payment.setEmployee(user.getEmail()));
            paymentRepository.saveAll(employeePayments);
        });

        responseBody.setStatus("Added successfully!");
        return responseBody;
    }

    @Override
    public ResponseBody putPayment(PaymentRequest payment) {
        ResponseBody responseBody = new ResponseBody();


        User user = userRepository.findByEmail(payment.getEmployee()).orElseThrow(() -> new UserException("Employee not found"));

        YearMonth period = YearMonthConverter.convert(payment.getPeriod());

        Payment paymentFromDb = paymentRepository.
                findByEmployeeAndPeriod(payment.getEmployee(), period)
                .orElseThrow(() -> new UserException("Payment not found"));

        paymentFromDb.setSalary(payment.getSalary());

        paymentRepository.save(paymentFromDb);

        responseBody.setStatus("Updated successfully!");
        return responseBody;
    }




}
