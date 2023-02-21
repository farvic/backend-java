package account.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    @NotNull(message = "employee email must not be empty")
    private User employee;


    public Payment(Long id, User employee) {
        this.id = id;
        this.employee = employee;
    }

    protected Payment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee) {
        this.employee = employee;
    }
}
