package org.mentalizr.scheduler.configuration;

import org.mentalizr.scheduler.jobs.JobConfiguration;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatConfiguration;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ClassCanBeRecord")
public class JobConfigurations {

    private final List<JobConfiguration> jobConfigurations;

    public JobConfigurations(List<JobConfiguration> jobConfigurations) {
        this.jobConfigurations = Collections.unmodifiableList(jobConfigurations);
    }

    public List<JobConfiguration> getJobConfigurations() {
        return this.jobConfigurations;
    }

    public List<ActivityStatWeeklyConfiguration> getActivityStatWeeklyConfigurations() {
        return this.jobConfigurations.stream()
                .filter(jobConfiguration -> jobConfiguration instanceof ActivityStatWeeklyConfiguration)
                .map(jobConfiguration -> (ActivityStatWeeklyConfiguration) jobConfiguration)
                .collect(Collectors.toList());
    }

    public List<HeartbeatConfiguration> getHeartbeatConfigurations() {
        return this.jobConfigurations.stream()
                .filter(jobConfiguration -> jobConfiguration instanceof HeartbeatConfiguration)
                .map(jobConfiguration -> (HeartbeatConfiguration) jobConfiguration)
                .collect(Collectors.toList());
    }

}
