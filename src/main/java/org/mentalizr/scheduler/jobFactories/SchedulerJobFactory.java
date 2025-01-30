package org.mentalizr.scheduler.jobFactories;

import org.mentalizr.scheduler.jobs.JobConfiguration;
import org.mentalizr.scheduler.jobs.SchedulerJob;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyJob;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatConfiguration;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatJob;
import org.quartz.Job;

public class SchedulerJobFactory {

    public static Class<? extends Job> getJobClass(JobConfiguration jobConfiguration) {
        if (jobConfiguration instanceof HeartbeatConfiguration) {
            return HeartbeatJob.class;
        } else if (jobConfiguration instanceof ActivityStatWeeklyConfiguration) {
            return ActivityStatWeeklyJob.class;
        }
        throw new IllegalStateException("Unsupported job configuration type: " + jobConfiguration.getClass().getName());
    }

    public static SchedulerJob create(JobConfiguration jobConfiguration) {
        if (jobConfiguration instanceof HeartbeatConfiguration heartbeatConfiguration) {
            return new HeartbeatJob(heartbeatConfiguration);
        } else if (jobConfiguration instanceof ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration) {
            return new ActivityStatWeeklyJob(activityStatWeeklyConfiguration);
        }
        throw new IllegalStateException("Unsupported job configuration type: " + jobConfiguration.getClass().getName());
    }

}
