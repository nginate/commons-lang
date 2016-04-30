package com.github.nginate.commons.lang.function.unchecked;

/**
 * Sometimes it's still necessary to indicate specific problems as if you've previously done with checked exceptions,
 *  but now you do not want. Maybe because of functional flow, or etc. This exception mimics IO exception in runtime
 *  world.
 *
 *  @see java.io.IOException
 *
 *  @since 1.0
 */
public class RuntimeIOException extends RuntimeException {
    public RuntimeIOException() {
    }

    public RuntimeIOException(String message) {
        super(message);
    }

    public RuntimeIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public RuntimeIOException(Throwable cause) {
        super(cause);
    }

    public RuntimeIOException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
