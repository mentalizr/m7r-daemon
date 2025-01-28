package org.mentalizr.scheduler;

public class M7rSchedulerException extends RuntimeException {

    public M7rSchedulerException() {
    }

    public M7rSchedulerException(String message) {
        super(message);
    }

    public M7rSchedulerException(String message, Throwable cause) {
        super(message, cause);
    }

    public M7rSchedulerException(Throwable cause) {
        super(cause);
    }

    public M7rSchedulerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
