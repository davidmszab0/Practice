package friendfinder.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception wrapper for 422, UNPROCESSABLE_ENTITY
 */
@ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)
public class HttpUnprocessableEntityException extends RuntimeException {
    public HttpUnprocessableEntityException(String message) {
        super(message);
    }
}
