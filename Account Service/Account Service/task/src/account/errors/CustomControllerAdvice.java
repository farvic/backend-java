package account.errors;

import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import java.time.DateTimeException;
import java.time.LocalDateTime;

@RestControllerAdvice
public class CustomControllerAdvice {

    int status;
    String timestamp;
    String path;
    String message;


//    @ResponseStatus(HttpStatus.UNAUTHORIZED)
//    @ExceptionHandler(RuntimeException.class)
//    public ResponseEntity<ErrorResponse> handleBadCredentialsException(
//            UserExistsException e, HttpServletRequest request) {
//
//        status = e.getStatus().value();
//        timestamp = LocalDateTime.now().toString();
//        message = e.getMessage();
//        path = request.getServletPath();
//
//        return new ResponseEntity<>(
//                new ErrorResponse(
//                        timestamp,
//                        status,
//                        e.getError(),
//                        "",
//                        path)
//                , e.getStatus()
//        );
//    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DateTimeException.class)
    public ResponseEntity<ErrorResponse> dateInputException(
            DateTimeException e, HttpServletRequest request) {

        status = HttpStatus.BAD_REQUEST.value();
        timestamp = LocalDateTime.now().toString();
        message = e.getCause().getMessage();
        path = request.getServletPath();

        return new ResponseEntity<>(
                new ErrorResponse(
                        timestamp,
                        status,
                        "Bad Request",
                        message,
                        path)
                , HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MismatchedInputException.class)
    public ResponseEntity<ErrorResponse> mismatchedInputException(
            MismatchedInputException e, HttpServletRequest request) {

        status = HttpStatus.BAD_REQUEST.value();
        timestamp = LocalDateTime.now().toString();
        message = e.getCause().getMessage();
        path = request.getServletPath();

        return new ResponseEntity<>(
                new ErrorResponse(
                        timestamp,
                        status,
                        message,
                        path)
                , HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> methodNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {

        status = HttpStatus.BAD_REQUEST.value();
        timestamp = LocalDateTime.now().toString();
        message = e.getFieldError().getDefaultMessage();
        path = request.getServletPath();

        return new ResponseEntity<>(
                new ErrorResponse(
                        timestamp,
                        status,
                        "Bad Request",
                        message,
                        path)
                , HttpStatus.BAD_REQUEST
        );
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserExistsException.class)
    public ResponseEntity<ErrorResponse> handleUserExistsException(
            UserExistsException e, HttpServletRequest request) {

        status = e.getStatus().value();
        timestamp = LocalDateTime.now().toString();
        message = e.getMessage();
        path = request.getServletPath();

        return new ResponseEntity<>(
                new ErrorResponse(
                        timestamp,
                        status,
                        e.getError(),
                        message,
                        path)
                , e.getStatus()
        );
    }


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
