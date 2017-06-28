package friendfinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception wrapper for 422, UNPROCESSABLE_ENTITY
 */
@ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
public class HttpNotImplementedException extends RuntimeException {
    public HttpNotImplementedException(String message) {
        super(message);
    }
}
