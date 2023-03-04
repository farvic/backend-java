package account.dto;

import account.model.AccessOperation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class UserAccessRequest {

    @NotEmpty(message = "User is required")
    private String user;
    @NotNull(message = "AccessOperation is required")
    private AccessOperation operation;

    public UserAccessRequest() {
    }

    public UserAccessRequest(String user, AccessOperation operation) {
        this.user = user;
        this.operation = operation;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public AccessOperation getOperation() {
        return operation;
    }

    public void setOperation(AccessOperation operation) {
        this.operation = operation;
    }
}
