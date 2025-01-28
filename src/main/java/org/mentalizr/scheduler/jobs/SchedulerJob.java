package org.mentalizr.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SchedulerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerJob.class);

    public abstract void schedulerExecute(JobExecutionContext context) throws JobExecutionException;

    @Override
    public final void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (JobHelper.isInactive()) {
            logger.info("Execution prevented. Scheduler is configured to be inactive.");
        }
        schedulerExecute(jobExecutionContext);
    }

}
