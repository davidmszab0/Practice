package friendfinder.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception wrapper for 500, INTERNAL_SERVER_ERROR
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class HttpInternalServerErrorException extends RuntimeException {
    public HttpInternalServerErrorException(String message) {
        super(message);
    }
}
