package org.mentalizr.daemon.jobs.heartbeat;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatJob.class);

    private final HeartbeatConfiguration heartbeatConfiguration;

    public HeartbeatJob(HeartbeatConfiguration heartbeatConfiguration) {
        this.heartbeatConfiguration = heartbeatConfiguration;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String logMessage = this.heartbeatConfiguration.logMessage();
        logger.info(logMessage);
    }

}
