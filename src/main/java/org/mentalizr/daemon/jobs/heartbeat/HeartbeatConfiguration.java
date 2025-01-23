package org.mentalizr.daemon.jobs.heartbeat;

import org.mentalizr.daemon.jobs.BaseConfiguration;
import org.mentalizr.daemon.jobs.JobConfiguration;

import java.util.List;
import java.util.Objects;
import java.util.Set;

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
