package org.mentalizr.daemon;

public class DaemonInitializationException extends DaemonException {

    public DaemonInitializationException() {
    }

    public DaemonInitializationException(String message) {
        super(message);
    }

    public DaemonInitializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaemonInitializationException(Throwable cause) {
        super(cause);
    }

    public DaemonInitializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
