package org.mentalizr.scheduler.jobInitialization;

import org.mentalizr.scheduler.configuration.JobConfigurations;
import org.mentalizr.scheduler.jobFactories.SchedulerJobFactory;
import org.mentalizr.scheduler.jobs.JobConfiguration;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobInitializer {

    private static final Logger logger = LoggerFactory.getLogger(JobInitializer.class);

    public static void initialize(Scheduler scheduler, JobConfigurations jobConfigurations) throws SchedulerException {
        for (JobConfiguration jobConfiguration : jobConfigurations.getJobConfigurations()) {
            scheduleJob(scheduler, jobConfiguration);
        }
    }

    private static void scheduleJob(
            Scheduler scheduler,
            JobConfiguration jobConfiguration
            ) throws SchedulerException {

        String name = jobConfiguration.getJobName();
        String triggerName = jobConfiguration.getTriggerName();
        String cronSchedule = jobConfiguration.getCronSchedule();
        String jobTypeName = jobConfiguration.getTypeString();
        M7rSchedulerJobFactory m7rSchedulerJobFactory = new M7rSchedulerJobFactory(jobConfiguration);

        logger.info("Scheduling job [{}] of type [{}].", name, jobTypeName);

        scheduler.setJobFactory(m7rSchedulerJobFactory);
        JobDetail job = JobBuilder.newJob(SchedulerJobFactory.getJobClass(jobConfiguration))
                .withIdentity(name, jobTypeName)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, jobTypeName)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .build();

        scheduler.scheduleJob(job, trigger);
    }

}
