package univ.paris13.lee.app.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(value = NoTokenException.class)
    public ResponseEntity<String> noToken(Exception e) {
        return createResponse(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(value = NotFoundException.class)
    public ResponseEntity<String> notFound(Exception e) {
        return createResponse(e, HttpStatus.NOT_FOUND);
    }

    private static ResponseEntity<String> createResponse(Exception e, HttpStatus status) {
        return new ResponseEntity<>(e.getMessage(), status);
    }
}