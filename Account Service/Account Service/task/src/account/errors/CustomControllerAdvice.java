package account.errors;

import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomControllerAdvice {

    int status;
    String timestamp;
    String path;



    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleUserExceptions(
            UserException e, HttpServletRequest request) {

        status = e.getStatus().value();
        timestamp = LocalDateTime.now().toString();
        path = request.getServletPath();

        return new ResponseEntity<>(
                new ErrorResponse(
                        timestamp,
                        status,
                        e.getError(),
                        path)
                , e.getStatus()
                );
    }

    @ExceptionHandler(Exception.class) // exception handled
    public ResponseEntity<ErrorResponse> handleExceptions(
            UserException e, HttpServletRequest request) {
        status = e.getStatus().value();
        timestamp = LocalDateTime.now().toString();
        path = request.getContextPath();
        return new ResponseEntity<>(
                new ErrorResponse(
                        timestamp,
                        status,
                        e.getError(),
                        path)
                , e.getStatus()
        );
    }
}
