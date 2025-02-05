package org.mentalizr.scheduler.jobs.heartbeat;

import com.google.gson.Gson;
import org.mentalizr.scheduler.jobs.SchedulerJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatJob extends SchedulerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatJob.class);

    @Override
    public void schedulerExecute(JobExecutionContext jobExecutionContext, String jobConfiguration) {
        HeartbeatConfiguration heartbeatConfiguration = new Gson().fromJson(jobConfiguration, HeartbeatConfiguration.class);
        String logMessage = heartbeatConfiguration.logMessage();
        logger.info(logMessage);
    }

}
