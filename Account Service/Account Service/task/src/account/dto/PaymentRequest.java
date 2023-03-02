package account.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.YearMonthDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.YearMonthSerializer;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.io.Serializable;


public class PaymentRequest implements Serializable {

    @NotNull(message = "Employee email is required")
    @Pattern(regexp = ".*@acme\\.com", message = "Email must end with @acme.com", flags = Pattern.Flag.CASE_INSENSITIVE)
    private String employee;

    @NotNull(message = "Period is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM-yyyy|yyyy-MM", locale = "en")
    private String period;
    @Positive(message = "Salary must be positive")
    @NotNull(message = "User salary must not be empty")
    private Long salary;

    public PaymentRequest() {
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Long getSalary() {
        return salary;
    }

    public void setSalary(Long salary) {
        this.salary = salary;
    }
}
