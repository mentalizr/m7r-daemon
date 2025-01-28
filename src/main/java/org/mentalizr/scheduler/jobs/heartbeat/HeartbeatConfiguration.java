package org.mentalizr.scheduler.jobs.heartbeat;

import org.mentalizr.scheduler.jobs.BaseConfiguration;
import org.mentalizr.scheduler.jobs.JobConfiguration;

public final class HeartbeatConfiguration extends JobConfiguration {

    public static final String LOG_MESSAGE = "log-message";

    private final String logMessage;

    public HeartbeatConfiguration(
            BaseConfiguration getBaseConfiguration,
            String logMessage
    ) {
        super(getBaseConfiguration);
        this.logMessage = logMessage;
    }

    public boolean hasLogMessage() {
        return logMessage != null;
    }

    public String logMessage() {
        return logMessage;
    }

}
