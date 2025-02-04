package org.mentalizr.scheduler.jobs.activityStatWeekly;

import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.ActivityStatPeriod;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.PeriodWeek;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationBinder;
import org.mentalizr.cli.config.MailConfiguration;
import org.mentalizr.clientSdk.ClientSdkException;
import org.mentalizr.clientSdk.SessionAgent;
import org.mentalizr.clientSdk.activityStat.ActivityStat;
import org.mentalizr.clientSdk.activityStat.ActivityStatRequest;
import org.mentalizr.scheduler.jobs.SchedulerJob;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ActivityStatWeeklyJob extends SchedulerJob implements Job {

    public static final String NAME = "activity-stat-weekly";

    private static final Logger logger = LoggerFactory.getLogger(ActivityStatWeeklyJob.class);

    public ActivityStatWeeklyJob(ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration) {
        this.jobConfiguration = activityStatWeeklyConfiguration;
    }

    public ActivityStatWeeklyConfiguration getActivityStatWeeklyConfiguration() {
        return (ActivityStatWeeklyConfiguration) this.jobConfiguration;
    }

    @Override
    public void schedulerExecute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        logger.info("Message from activity-stat-weekly.");

//        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
//        ActivityStatRequest activityStatRequest = createActivityStatRequest(activityStatPeriod);
//
//        try {
//            SessionAgent sessionAgent = SessionAgent.createFromLocalConfigWithTransientCookieStorage();
//            MailConfiguration mailConfiguration = obtainMailConfiguration();
//            ActivityStat.exec(sessionAgent.getRESTCallContext(), activityStatRequest, mailConfiguration, false);
//        } catch (ClientSdkException e) {
//            throw new JobExecutionException(e);
//        }
    }

    private ActivityStatRequest createActivityStatRequest(ActivityStatPeriod activityStatPeriod) {
        ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration = getActivityStatWeeklyConfiguration();

        ActivityStatRequest.Builder activityStatRequestBuilder =
                new ActivityStatRequest.Builder()
                        .withFromTimestamp(activityStatPeriod.getFromTimestamp())
                        .withUntilTimestamp(activityStatPeriod.getUntilTimestamp());

        if (activityStatWeeklyConfiguration.hasPrograms()) {
            activityStatRequestBuilder
                    .withProgramsIncludeMode(true)
                    .withPrograms(activityStatWeeklyConfiguration.getPrograms());
        }

        if (activityStatWeeklyConfiguration.hasExcludePrograms()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(false)
                    .withPrograms(activityStatWeeklyConfiguration.getExcludePrograms());
        }

        if (activityStatWeeklyConfiguration.hasProjects()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(true)
                    .withProjects(activityStatWeeklyConfiguration.getProjects());
        }

        if (activityStatWeeklyConfiguration.hasExcludeProjects()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(false)
                    .withProjects(activityStatWeeklyConfiguration.getExcludeProjects());
        }

        List<String> mailRecipients = activityStatWeeklyConfiguration.getRecipients();
        activityStatRequestBuilder
                .withSendAsMail(true)
                .withMailRecipients(mailRecipients)
                .withMailSubject("Wöchentliche Aktivitätsstatistik");

        return activityStatRequestBuilder.build();
    }

    private MailConfiguration obtainMailConfiguration() throws JobExecutionException {
        CliConfiguration cliConfiguration = CliConfigurationBinder.bindConfiguration();
        if (!cliConfiguration.hasMailConfiguration())
            throw new JobExecutionException("No mail configuration found in local m7r-cli configuration file.");
        return cliConfiguration.getMailConfiguration();
    }

}
