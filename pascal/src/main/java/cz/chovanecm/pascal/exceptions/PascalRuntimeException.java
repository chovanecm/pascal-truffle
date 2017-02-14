package cz.chovanecm.pascal.exceptions;

/**
 * Created by martin on 1/24/17.
 */
public abstract class PascalRuntimeException extends RuntimeException {
    public PascalRuntimeException() {
    }

    public PascalRuntimeException(String message) {
        super(message);
    }

    public PascalRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PascalRuntimeException(Throwable cause) {
        super(cause);
    }

    public PascalRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
