package org.mentalizr.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class SchedulerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerJob.class);

    protected JobConfiguration jobConfiguration;

    public abstract void schedulerExecute(JobExecutionContext context) throws JobExecutionException;

    @Override
    public final void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (JobHelper.isInactive()) {
            logger.info("Scheduler is configured as deactivated. Skipping execution.");
            return;
        }
        if (this.jobConfiguration == null)
            throw new IllegalStateException("JobConfiguration not set");
        if (!this.jobConfiguration.getBaseConfiguration().isEnabled()) {
            logger.info("Job is configured as disabled. Skipping execution.");
            return;
        }
        schedulerExecute(jobExecutionContext);
    }

}
