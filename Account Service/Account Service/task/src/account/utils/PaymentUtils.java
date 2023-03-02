package account.utils;
import account.domain.Payment;
import account.domain.User;
import account.dto.PaymentDto;
import java.util.ArrayList;
import java.util.List;

public class PaymentUtils {

    public static List<PaymentDto> mapPaymentsToPaymentDtos(List<Payment> payments, User employee) {
        List<PaymentDto> paymentDtos = new ArrayList<>();
        for (Payment payment : payments) {
            PaymentDto paymentDto = new PaymentDto();
            paymentDto.setName(employee.getName());
            paymentDto.setLastname(employee.getLastname());
            paymentDto.setPeriod(payment.getPeriod());
            paymentDto.setSalary(formatSalary(payment.getSalary()));
            paymentDtos.add(paymentDto);
        }
        paymentDtos.sort((o1, o2) -> o2.getPeriod().compareTo(o1.getPeriod()));
        return paymentDtos;
    }

    private static String formatSalary(long salary) {
        long dollars = salary / 100;
        long cents = salary % 100;
        return String.format("%d dollar(s) %02d cent(s)", dollars, cents);
    }
}
