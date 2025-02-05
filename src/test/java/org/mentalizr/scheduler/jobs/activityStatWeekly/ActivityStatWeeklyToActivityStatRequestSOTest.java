package org.mentalizr.scheduler.jobs.activityStatWeekly;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.ActivityStatPeriod;
import org.mentalizr.cli.commands.user.activity.stat.activityStatPeriod.PeriodWeek;
import org.mentalizr.clientSdk.activityStat.ActivityStatRequest;
import org.mentalizr.clientSdk.activityStat.ActivityStatRequests;
import org.mentalizr.scheduler.configuration.JobConfigurations;
import org.mentalizr.scheduler.configuration.JobConfigurationsManager;
import org.mentalizr.serviceObjects.requestObjects.ActivityStatRequestSO;

import java.nio.file.Paths;
import java.util.HashSet;

/**
 * Testing the generation of ActivityStatRequestSO-instances from configuration files for ActivityStatWeekly jobs.
 */
public class ActivityStatWeeklyToActivityStatRequestSOTest {

    @Test
    public void test1() {
        JobConfigurations jobConfigurations
                = JobConfigurationsManager.fromConfigFiles(Paths.get("src/test/resources/activityStatWeeklyTest1"));
        ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration
                = jobConfigurations.getActivityStatWeeklyConfigurations().getFirst();

        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
        ActivityStatRequest activityStatRequest =
                ActivityStatWeeklyHelper.createActivityStatRequest(activityStatPeriod, activityStatWeeklyConfiguration);
        ActivityStatRequestSO activityStatRequestSOActual
                = ActivityStatRequests.toActivityStatRequestSO(activityStatRequest);

        ActivityStatRequestSO activityStatRequestSOExpected = new ActivityStatRequestSO();
        activityStatRequestSOExpected.setFromTimestamp(activityStatPeriod.getFromTimestamp());
        activityStatRequestSOExpected.setUntilTimestamp(activityStatPeriod.getUntilTimestamp());
        activityStatRequestSOExpected.setProjects(Sets.newHashSet("demo"));
        activityStatRequestSOExpected.setProjectsIncludeMode(true);
        activityStatRequestSOExpected.setPrograms(new HashSet<>());
        activityStatRequestSOExpected.setProgramsIncludeMode(false);

        Assertions.assertEquals(activityStatRequestSOExpected, activityStatRequestSOActual);
    }

    @Test
    public void test2() {
        JobConfigurations jobConfigurations
                = JobConfigurationsManager.fromConfigFiles(Paths.get("src/test/resources/activityStatWeeklyTest2"));
        ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration
                = jobConfigurations.getActivityStatWeeklyConfigurations().getFirst();

        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
        ActivityStatRequest activityStatRequest =
                ActivityStatWeeklyHelper.createActivityStatRequest(activityStatPeriod, activityStatWeeklyConfiguration);
        ActivityStatRequestSO activityStatRequestSOActual
                = ActivityStatRequests.toActivityStatRequestSO(activityStatRequest);

        ActivityStatRequestSO activityStatRequestSOExpected = new ActivityStatRequestSO();
        activityStatRequestSOExpected.setFromTimestamp(activityStatPeriod.getFromTimestamp());
        activityStatRequestSOExpected.setUntilTimestamp(activityStatPeriod.getUntilTimestamp());
        activityStatRequestSOExpected.setProjects(Sets.newHashSet("demo-1", "demo-2"));
        activityStatRequestSOExpected.setProjectsIncludeMode(true);
        activityStatRequestSOExpected.setPrograms(Sets.newHashSet("prog-1", "prog-2"));
        activityStatRequestSOExpected.setProgramsIncludeMode(true);

        Assertions.assertEquals(activityStatRequestSOExpected, activityStatRequestSOActual);
    }

    @Test
    public void test3() {
        JobConfigurations jobConfigurations
                = JobConfigurationsManager.fromConfigFiles(Paths.get("src/test/resources/activityStatWeeklyTest3"));
        ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration
                = jobConfigurations.getActivityStatWeeklyConfigurations().getFirst();

        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
        ActivityStatRequest activityStatRequest =
                ActivityStatWeeklyHelper.createActivityStatRequest(activityStatPeriod, activityStatWeeklyConfiguration);
        ActivityStatRequestSO activityStatRequestSOActual
                = ActivityStatRequests.toActivityStatRequestSO(activityStatRequest);

        ActivityStatRequestSO activityStatRequestSOExpected = new ActivityStatRequestSO();
        activityStatRequestSOExpected.setFromTimestamp(activityStatPeriod.getFromTimestamp());
        activityStatRequestSOExpected.setUntilTimestamp(activityStatPeriod.getUntilTimestamp());
        activityStatRequestSOExpected.setProjects(Sets.newHashSet("demo-1", "demo-2"));
        activityStatRequestSOExpected.setProjectsIncludeMode(false);
        activityStatRequestSOExpected.setPrograms(Sets.newHashSet("prog-1", "prog-2"));
        activityStatRequestSOExpected.setProgramsIncludeMode(true);

        Assertions.assertEquals(activityStatRequestSOExpected, activityStatRequestSOActual);
    }

    @Test
    public void test4() {
        JobConfigurations jobConfigurations
                = JobConfigurationsManager.fromConfigFiles(Paths.get("src/test/resources/activityStatWeeklyTest4"));
        ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration
                = jobConfigurations.getActivityStatWeeklyConfigurations().getFirst();

        ActivityStatPeriod activityStatPeriod = new ActivityStatPeriod(new PeriodWeek(-1));
        ActivityStatRequest activityStatRequest =
                ActivityStatWeeklyHelper.createActivityStatRequest(activityStatPeriod, activityStatWeeklyConfiguration);
        ActivityStatRequestSO activityStatRequestSOActual
                = ActivityStatRequests.toActivityStatRequestSO(activityStatRequest);

        ActivityStatRequestSO activityStatRequestSOExpected = new ActivityStatRequestSO();
        activityStatRequestSOExpected.setFromTimestamp(activityStatPeriod.getFromTimestamp());
        activityStatRequestSOExpected.setUntilTimestamp(activityStatPeriod.getUntilTimestamp());
        activityStatRequestSOExpected.setProjects(Sets.newHashSet("demo-1", "demo-2"));
        activityStatRequestSOExpected.setProjectsIncludeMode(false);
        activityStatRequestSOExpected.setPrograms(Sets.newHashSet("prog-1", "prog-2"));
        activityStatRequestSOExpected.setProgramsIncludeMode(false);

        Assertions.assertEquals(activityStatRequestSOExpected, activityStatRequestSOActual);
    }

}
