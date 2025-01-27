package org.mentalizr.daemon;

import org.mentalizr.daemon.configuration.JobConfigurations;
import org.mentalizr.daemon.helper.StringHelper;
import org.mentalizr.daemon.jobs.JobConfiguration;
import org.mentalizr.daemon.jobs.SchedulerJob;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeekly;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyJob;
import org.mentalizr.daemon.jobs.heartbeat.Heartbeat;
import org.mentalizr.daemon.jobs.heartbeat.HeartbeatConfiguration;
import org.mentalizr.daemon.jobs.heartbeat.HeartbeatJob;
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
