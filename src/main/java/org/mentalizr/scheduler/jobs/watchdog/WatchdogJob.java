package org.mentalizr.scheduler.jobs.watchdog;

import com.google.gson.Gson;
import org.mentalizer.mailer.MailConfiguration;
import org.mentalizer.mailer.MailConfigurationException;
import org.mentalizer.mailer.MailConfigurationLoader;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.ActivityStatPeriod;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.PeriodWeek;
import org.mentalizr.clientSdk.ClientSdkException;
import org.mentalizr.clientSdk.SessionAgent;
import org.mentalizr.clientSdk.activityStat.ActivityStat;
import org.mentalizr.clientSdk.activityStat.ActivityStatRequest;
import org.mentalizr.scheduler.jobs.SchedulerJob;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyHelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class WatchdogJob extends SchedulerJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(WatchdogJob.class);

    @Override
    public void schedulerExecute(JobExecutionContext jobExecutionContext, String jobConfigurationJson)
            throws JobExecutionException {

        WatchdogConfiguration watchdogConfiguration
                = getJobConfiguration(jobConfigurationJson);

        logger.info("Starting job [" + watchdogConfiguration.getJobName() + "] ...");

        // ...

        logger.info("Job [" + watchdogConfiguration.getJobName() + "] executed successfully.");
    }

    @Override
    public WatchdogConfiguration getJobConfiguration(String jobConfigurationJson) {
        return new Gson().fromJson(jobConfigurationJson, WatchdogConfiguration.class);
    }

}
