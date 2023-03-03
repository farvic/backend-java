package account.dto;

import account.domain.Role;
import account.model.Operation;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserRoleRequest {

    @NotEmpty(message = "User is required")
    String user;

    @NotNull(message = "Role is required")
    String role;

    @NotNull(message = "Operation is required")
    Operation operation;

    protected UserRoleRequest() {

    }

    public UserRoleRequest(String user, String role, Operation operation) {
        this.user = user;
        this.role = role;
        this.operation = operation;
    }


    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }
}
