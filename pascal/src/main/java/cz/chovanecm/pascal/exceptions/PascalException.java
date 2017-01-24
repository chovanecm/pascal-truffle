package cz.chovanecm.pascal.exceptions;

/**
 * Created by martin on 1/24/17.
 */
public abstract class PascalException extends RuntimeException {
    public PascalException() {
    }

    public PascalException(String message) {
        super(message);
    }

    public PascalException(String message, Throwable cause) {
        super(message, cause);
    }

    public PascalException(Throwable cause) {
        super(cause);
    }

    public PascalException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
