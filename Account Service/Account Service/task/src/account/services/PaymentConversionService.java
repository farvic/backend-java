package account.services;

import account.domain.Payment;
import account.dto.PaymentRequest;
import account.utils.YearMonthConverter;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;

@Service
public class PaymentConversionService implements ConversionService {

    @Override
    public boolean canConvert(Class<?> sourceType, Class<?> targetType) {
        assert sourceType != null;
        return sourceType.equals(PaymentRequest.class) && targetType.equals(Payment.class);
    }

    @Override
    public boolean canConvert(TypeDescriptor sourceType, TypeDescriptor targetType) {
        return false;
    }


    @Override
    public <T> T convert(Object source, Class<T> targetType) {

        PaymentRequest paymentRequest = (PaymentRequest) source;

        String employee = paymentRequest.getEmployee();

        YearMonth period = YearMonthConverter.convert(paymentRequest.getPeriod());

        Long salary = paymentRequest.getSalary();

        return (T) new Payment(employee, period, salary);
    }

    @Override
    public Object convert(Object source, TypeDescriptor sourceType, TypeDescriptor targetType) {
        return null;
    }

}
