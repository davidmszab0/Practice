package friendfinder.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception wrapper for 403, FORBIDDEN
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class HttpForbiddenException extends RuntimeException {
    public HttpForbiddenException(String message) {
        super(message);
    }
}
