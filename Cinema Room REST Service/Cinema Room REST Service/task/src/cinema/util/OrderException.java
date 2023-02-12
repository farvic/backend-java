package cinema.util;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class OrderException extends RuntimeException {
    private String error;
    private HttpStatus status;

    public OrderException(String error) {
        this.error = error;
        this.status = HttpStatus.BAD_REQUEST;
    }

    public OrderException(String error, HttpStatus status) {
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
