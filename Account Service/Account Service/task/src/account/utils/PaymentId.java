package account.utils;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.Objects;

public class PaymentId implements Serializable {

    private String employee;

    private YearMonth period;

    public PaymentId() {
    }

    public PaymentId(String employee, YearMonth period) {
        this.employee = employee;
        this.period = period;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentId paymentId = (PaymentId) o;
        return Objects.equals(employee, paymentId.employee) && Objects.equals(period, paymentId.period);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, period);
    }
}
