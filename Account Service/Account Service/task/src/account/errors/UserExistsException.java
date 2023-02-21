package account.errors;

import org.springframework.http.HttpStatus;

public class UserExistsException extends UserException{

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String message;
    public UserExistsException(String error, HttpStatus status, String message) {
        super(error, status);
        this.message = message;
    }

}
