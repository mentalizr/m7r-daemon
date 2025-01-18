package org.mentalizr.daemon.jobs.activityStatWeekly;

import de.arthurpicht.configuration.Configuration;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.ActivityStatPeriod;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.PeriodWeek;
import org.mentalizr.serviceObjects.requestObjects.ActivityStatRequestSO;

public class ActivityStatWeeklyParameters {

    public static ActivityStatRequestSO getActivityStatRequest(Configuration configuration) {
        ActivityStatRequestSO activityStatRequestSO = new ActivityStatRequestSO();

        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
        activityStatRequestSO.setFromTimestamp(activityStatPeriod.getFromTimestamp());
        activityStatRequestSO.setUntilTimestamp(activityStatPeriod.getUntilTimestamp());

//        if (configuration.containsKey())
        throw new RuntimeException("Not implemented yet");


    }





}
