package org.mentalizr.scheduler.jobInitialization;

import de.arthurpicht.utils.core.dates.ISODates;
import org.mentalizr.scheduler.M7rSchedulerException;
import org.mentalizr.scheduler.configuration.JobConfigurations;
import org.mentalizr.scheduler.jobs.JobConfiguration;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class JobOverview {

    public record JobOverviewRecord(String name, String type, String nextExecutionTime) {}

    public static List<JobOverviewRecord> getJobOverviewRecords(JobConfigurations jobConfigurations) {
        List<JobOverviewRecord> jobOverviewRecords = new ArrayList<>();
        for (JobConfiguration jobConfiguration : jobConfigurations.getJobConfigurations()) {
            JobOverviewRecord jobOverviewRecord = getOverview(jobConfiguration);
            jobOverviewRecords.add(jobOverviewRecord);
        }
        return jobOverviewRecords;
    }

    private static JobOverviewRecord getOverview(JobConfiguration jobConfiguration) {
        String name = jobConfiguration.getJobName();
        String type = jobConfiguration.getTypeString();
        String nextExecutionTime = getNextExecutionTime(jobConfiguration.getCronSchedule());
        return new JobOverviewRecord(name, type, nextExecutionTime);
    }

    private static String getNextExecutionTime(String cronExpressionString) {
        try {
            CronExpression cronExpression = new CronExpression(cronExpressionString);
            Date nextExecutionDate = cronExpression.getNextValidTimeAfter(Date.from(Instant.now()));
            return ISODates.toIsoString(nextExecutionDate);
        } catch (ParseException e) {
            throw new M7rSchedulerException(e.getMessage(), e);
        }
    }

}
