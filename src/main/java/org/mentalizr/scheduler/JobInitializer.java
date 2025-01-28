package org.mentalizr.scheduler;

import org.mentalizr.scheduler.configuration.JobConfigurations;
import org.mentalizr.scheduler.helper.StringHelper;
import org.mentalizr.scheduler.jobs.JobConfiguration;
import org.mentalizr.scheduler.jobs.SchedulerJob;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeekly;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyJob;
import org.mentalizr.scheduler.jobs.heartbeat.Heartbeat;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatConfiguration;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatJob;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JobInitializer {

    private static final Logger logger = LoggerFactory.getLogger(JobInitializer.class);

    public static void initialize(Scheduler scheduler, JobConfigurations jobConfigurations) throws SchedulerException {
        for (ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration : jobConfigurations.getActivityStatWeeklyConfigurations()) {
            scheduleJob(scheduler, activityStatWeeklyConfiguration, ActivityStatWeekly.TYPE);
        }
        for (HeartbeatConfiguration heartbeatConfiguration : jobConfigurations.getHeartbeatConfigurations()) {
            scheduleJob(scheduler, heartbeatConfiguration, Heartbeat.TYPE);
        }
    }

    private static Class<? extends SchedulerJob> getSchedulerJobClass(String jobTypeName) {
        if (jobTypeName.equals(ActivityStatWeekly.TYPE)) {
            return ActivityStatWeeklyJob.class;
        } else if (jobTypeName.equals(Heartbeat.TYPE)) {
            return HeartbeatJob.class;
        }
        throw new IllegalStateException("Unknown jobTypeName for scheduler job: [" + jobTypeName + "].");
    }

    private static void scheduleJob(
            Scheduler scheduler,
            JobConfiguration jobConfiguration,
            String jobTypeName
            ) throws SchedulerException {

        String name = jobConfiguration.getJobName();
        String triggerName = jobConfiguration.getTriggerName();
        String cronSchedule = jobConfiguration.getCronSchedule();
        String jobTypeNameResolved = getJobTypeName(jobConfiguration.getClass());
        Class<? extends SchedulerJob> schedulerJobClass = getSchedulerJobClass(jobTypeName);

        logger.info("Scheduling job [{}] of type [{}].", name, jobTypeNameResolved);

        JobDetail job = JobBuilder.newJob(schedulerJobClass)
                .withIdentity(name, jobTypeName)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, jobTypeName)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .build();

        scheduler.scheduleJob(job, trigger);
    }

    private static String getJobTypeName(Class<? extends JobConfiguration> jobConfigurationClass) {
        String simpleName = jobConfigurationClass.getSimpleName();
        return StringHelper.cutOff(simpleName, "Configuration");
    }

}
