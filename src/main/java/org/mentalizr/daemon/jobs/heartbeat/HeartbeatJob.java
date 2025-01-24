package org.mentalizr.daemon.jobs.heartbeat;

import org.mentalizr.daemon.jobs.SchedulerJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatJob extends SchedulerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatJob.class);

    private final HeartbeatConfiguration heartbeatConfiguration;

    public HeartbeatJob(HeartbeatConfiguration heartbeatConfiguration) {
        this.heartbeatConfiguration = heartbeatConfiguration;
    }

    @Override
    public void schedulerExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String logMessage = this.heartbeatConfiguration.logMessage();
        logger.info(logMessage);
    }

}
