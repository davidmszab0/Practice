package friendfinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception wrapper for 400, BAD_REQUEST
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class HttpBadRequestException extends RuntimeException {
    public HttpBadRequestException(String message) {
        super(message);
    }
}
