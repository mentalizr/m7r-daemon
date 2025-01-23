package org.mentalizr.daemon;

import org.mentalizr.daemon.configuration.JobConfigurations;
import org.mentalizr.daemon.jobs.JobConfiguration;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyJob;
import org.mentalizr.daemon.jobs.heartbeat.HeartbeatConfiguration;
import org.quartz.*;

import static org.mentalizr.daemon.configuration.JobConfigurations.ACTIVITY_STAT_WEEKLY;
import static org.mentalizr.daemon.configuration.JobConfigurations.HEARTBEAT;

public class JobInitializer {

    public static void initialize(Scheduler scheduler, JobConfigurations jobConfigurations) throws SchedulerException {
        for (ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration : jobConfigurations.getActivityStatWeeklyConfigurations()) {
            scheduleJob(scheduler, activityStatWeeklyConfiguration, ACTIVITY_STAT_WEEKLY);
        }
        for (HeartbeatConfiguration heartbeatConfiguration : jobConfigurations.getHeartbeatConfigurations()) {
            scheduleJob(scheduler, heartbeatConfiguration, HEARTBEAT);
        }
    }

    private static void scheduleJob(Scheduler scheduler, JobConfiguration jobConfiguration, String group) throws SchedulerException {
        String name = jobConfiguration.getJobName();
        String triggerName = jobConfiguration.getTriggerName();
        String cronSchedule = jobConfiguration.getCronSchedule();

        JobDetail job = JobBuilder.newJob(ActivityStatWeeklyJob.class)
                .withIdentity(name, group)
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .withIdentity(triggerName, group)
                .startNow()
                .withSchedule(CronScheduleBuilder.cronSchedule(cronSchedule))
                .build();

        scheduler.scheduleJob(job, trigger);
    }

}
