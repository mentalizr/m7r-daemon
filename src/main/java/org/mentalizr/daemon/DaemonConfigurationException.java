package org.mentalizr.daemon;

public class DaemonConfigurationException extends DaemonInitializationException {

    public DaemonConfigurationException() {
    }

    public DaemonConfigurationException(String message) {
        super(message);
    }

    public DaemonConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }

    public DaemonConfigurationException(Throwable cause) {
        super(cause);
    }

    public DaemonConfigurationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
