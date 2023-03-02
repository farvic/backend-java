package account.domain;

import account.utils.PaymentId;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.YearMonth;


@Entity
@Table(name = "payments")
@IdClass(PaymentId.class)
public class Payment {
    @Column
    @NotNull(message = "Employee email is required")
    @Pattern(regexp = ".*@acme\\.com", message = "Email must end with @acme.com", flags = Pattern.Flag.CASE_INSENSITIVE)
    @Id
    private String employee;

    @Id
    @Column
    @NotNull(message = "Period is required")
//    @Pattern(regexp = "^(0[1-9]|1[012])-(19|20)\\d\\d$", message = "Period must be in the format MM-yyyy")
    private YearMonth period;

    @Column
    @NotNull(message = "User salary must not be empty")
    @Positive(message = "Salary must be positive")
    private Long salary;



    public Payment() {
    }

    public Payment(String employee, YearMonth period, Long salary) {
        this.employee = employee;
        this.period = period;
        this.salary = salary;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
