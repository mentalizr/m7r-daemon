package org.mentalizr.daemon.jobs.activityStatWeekly;

import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.ActivityStatPeriod;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.PeriodWeek;
import org.mentalizr.cli.config.CliConfiguration;
import org.mentalizr.cli.config.CliConfigurationBinder;
import org.mentalizr.cli.config.MailConfiguration;
import org.mentalizr.clientSdk.ClientSdkException;
import org.mentalizr.clientSdk.SessionAgent;
import org.mentalizr.clientSdk.activityStat.ActivityStat;
import org.mentalizr.clientSdk.activityStat.ActivityStatRequest;
import org.mentalizr.daemon.jobs.JobHelper;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class ActivityStatWeeklyJob implements Job {

    private static final Logger logger = LoggerFactory.getLogger(ActivityStatWeeklyJob.class);

    private final ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration;

    public ActivityStatWeeklyJob(ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration) {
        this.activityStatWeeklyConfiguration = activityStatWeeklyConfiguration;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        if (JobHelper.isInactive()) {
            logger.info("Execution prevented. Scheduler is configured to be inactive.");
        }

        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
        ActivityStatRequest activityStatRequest = createActivityStatRequest(activityStatPeriod);

        try {
            SessionAgent sessionAgent = SessionAgent.createFromLocalConfigWithTransientCookieStorage();
            MailConfiguration mailConfiguration = obtainMailConfiguration();
            ActivityStat.exec(sessionAgent.getRESTCallContext(), activityStatRequest, mailConfiguration, false);
        } catch (ClientSdkException e) {
            throw new JobExecutionException(e);
        }
    }

    private ActivityStatRequest createActivityStatRequest(ActivityStatPeriod activityStatPeriod) {
        ActivityStatRequest.Builder activityStatRequestBuilder =
                new ActivityStatRequest.Builder()
                        .withFromTimestamp(activityStatPeriod.getFromTimestamp())
                        .withUntilTimestamp(activityStatPeriod.getUntilTimestamp());

        if (this.activityStatWeeklyConfiguration.hasPrograms()) {
            activityStatRequestBuilder
                    .withProgramsIncludeMode(true)
                    .withPrograms(this.activityStatWeeklyConfiguration.getPrograms());
        }

        if (this.activityStatWeeklyConfiguration.hasExcludePrograms()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(false)
                    .withPrograms(this.activityStatWeeklyConfiguration.getExcludePrograms());
        }

        if (this.activityStatWeeklyConfiguration.hasProjects()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(true)
                    .withProjects(this.activityStatWeeklyConfiguration.getProjects());
        }

        if (this.activityStatWeeklyConfiguration.hasExcludeProjects()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(false)
                    .withProjects(this.activityStatWeeklyConfiguration.getExcludeProjects());
        }

        List<String> mailRecipients = this.activityStatWeeklyConfiguration.getRecipients();
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
