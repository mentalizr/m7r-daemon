package org.mentalizr.daemon.jobs.activityStatWeekly;

import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.ActivityStatPeriod;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.PeriodWeek;
import org.mentalizr.clientSdk.ClientSdkException;
import org.mentalizr.clientSdk.SessionAgent;
import org.mentalizr.clientSdk.activityStat.ActivityStatRequest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.List;

public class ActivityStatWeeklyJob implements Job {

    private final ActivityStatWeeklyConfiguration configuration;

    public ActivityStatWeeklyJob(ActivityStatWeeklyConfiguration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
        ActivityStatRequest activityStatRequest = createActivityStatRequest(activityStatPeriod);

        try {
            SessionAgent sessionAgent = SessionAgent.createFromLocalConfigWithTransientCookieStorage();


        } catch (ClientSdkException e) {
            throw new JobExecutionException(e);
        }

    }


    private ActivityStatRequest createActivityStatRequest(ActivityStatPeriod activityStatPeriod) {
        ActivityStatRequest.Builder activityStatRequestBuilder =
                new ActivityStatRequest.Builder()
                        .withFromTimestamp(activityStatPeriod.getFromTimestamp())
                        .withUntilTimestamp(activityStatPeriod.getUntilTimestamp());

        if (this.configuration.hasPrograms()) {
            activityStatRequestBuilder
                    .withProgramsIncludeMode(true)
                    .withPrograms(this.configuration.programs());
        }

        if (this.configuration.hasExcludePrograms()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(false)
                    .withPrograms(this.configuration.excludePrograms());
        }

        if (this.configuration.hasProjects()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(true)
                    .withProjects(this.configuration.projects());
        }

        if (this.configuration.hasExcludeProjects()) {
            activityStatRequestBuilder
                    .withProjectIncludeMode(false)
                    .withProjects(this.configuration.excludeProjects());
        }

        List<String> mailRecipients = this.configuration.recipients();
        activityStatRequestBuilder
                .withSendAsMail(true)
                .withMailRecipients(mailRecipients)
                .withMailSubject("Wöchentliche Aktivitätsstatistik");

        return activityStatRequestBuilder.build();
    }


}
