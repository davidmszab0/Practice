package friendfinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception wrapper for 409, CONFLICT
 */
@ResponseStatus(value = HttpStatus.CONFLICT)
public class HttpConflictException extends RuntimeException {
    public HttpConflictException(String message) {
        super(message);
    }
}
