package org.mentalizr.daemon.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HeartbeatJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(HeartbeatJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        logger.info("daemons heartbeat ...");
    }

}
