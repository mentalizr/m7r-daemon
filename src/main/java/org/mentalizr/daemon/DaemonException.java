package org.mentalizr.daemon;

public class DaemonException extends RuntimeException {

    public DaemonException() {
    }

    public DaemonException(String message) {
        super(message);
    }

    public DaemonException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaemonException(Throwable cause) {
        super(cause);
    }

    public DaemonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
