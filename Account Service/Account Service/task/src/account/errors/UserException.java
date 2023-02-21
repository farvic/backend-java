package account.errors;

import org.springframework.http.HttpStatus;

public class UserException extends RuntimeException {
    private String error;
    private HttpStatus status;

    public UserException(String error) {
        this.error = error;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public UserException(String error, HttpStatus status) {
        this.error = error;
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
