package org.mentalizr.scheduler.configuration;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class JobConfigurationsTest {

    @Test
    public void simpleTest() {
        JobConfigurations jobConfigurations = new JobConfigurations(Paths.get("src/test/resources/configTest1"));
        List<ActivityStatWeeklyConfiguration> activityStatWeeklyConfigurations = jobConfigurations.getActivityStatWeeklyConfigurations();

        assertEquals(1, activityStatWeeklyConfigurations.size());

        ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration = activityStatWeeklyConfigurations.getFirst();
        assertEquals("0 6 * * MON", activityStatWeeklyConfiguration.getBaseConfiguration().getCronSchedule());
        assertEquals(1, activityStatWeeklyConfiguration.getProjects().size());
        assertEquals("demo", Sets.getSomeElement(activityStatWeeklyConfiguration.getProjects()));
        assertTrue(activityStatWeeklyConfiguration.getExcludeProjects().isEmpty());
        assertTrue(activityStatWeeklyConfiguration.getPrograms().isEmpty());
        assertTrue(activityStatWeeklyConfiguration.getExcludePrograms().isEmpty());
        assertEquals(2, activityStatWeeklyConfiguration.getRecipients().size());
        assertTrue(activityStatWeeklyConfiguration.getRecipients().contains("dummy@example.com"));
        assertTrue(activityStatWeeklyConfiguration.getRecipients().contains("dummy2@example.com"));
    }

}