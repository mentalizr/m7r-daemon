package org.mentalizr.scheduler.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static org.mentalizr.scheduler.Const.CONFIGURATION_KEY;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public abstract class SchedulerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(SchedulerJob.class);

    public abstract void schedulerExecute(JobExecutionContext context, String jobConfiguration) throws JobExecutionException;

    public abstract JobConfiguration getJobConfiguration(String jobConfigurationJson);

    @Override
    public final void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (JobHelper.isInactive()) {
            logger.info("Scheduler is configured as deactivated. Skipping execution.");
            return;
        }
        String jobConfigurationJson = getJobConfigurationAsJson(jobExecutionContext);
        JobConfiguration jobConfiguration = getJobConfiguration(jobConfigurationJson);
        if (!jobConfiguration.baseConfiguration.isEnabled()) {
            logger.info("Job [" + jobConfiguration.getJobName() + "] is configured as disabled. Skipping execution.");
            return;
        }
        schedulerExecute(jobExecutionContext, jobConfigurationJson);
    }

    private String getJobConfigurationAsJson(JobExecutionContext jobExecutionContext) {
        JobDataMap jobDataMap = jobExecutionContext.getJobDetail().getJobDataMap();
        String[] contextKeys = jobDataMap.getKeys();
        if (!Arrays.asList(contextKeys).contains(CONFIGURATION_KEY))
            throw new IllegalStateException("Job context does not contain configuration.");
        return jobDataMap.getString(CONFIGURATION_KEY);
    }

}
