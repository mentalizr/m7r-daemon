package org.mentalizr.scheduler.jobs.heartbeat;

import org.mentalizr.scheduler.jobs.SchedulerJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatJob extends SchedulerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatJob.class);

    public HeartbeatJob(HeartbeatConfiguration heartbeatConfiguration) {
        this.jobConfiguration = heartbeatConfiguration;
    }

    @Override
    public void schedulerExecute(JobExecutionContext jobExecutionContext) {
        HeartbeatConfiguration heartbeatConfiguration = (HeartbeatConfiguration) this.jobConfiguration;
        String logMessage = heartbeatConfiguration.logMessage();
        logger.info(logMessage);
    }

}
