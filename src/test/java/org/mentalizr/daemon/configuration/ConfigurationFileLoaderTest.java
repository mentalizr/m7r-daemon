package org.mentalizr.daemon.configuration;

import de.arthurpicht.utils.core.collection.Sets;
import org.junit.jupiter.api.Test;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;

import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ConfigurationFileLoaderTest {

    @Test
    public void simpleTest() {
        ConfigurationFileLoader configurationFileLoader = new ConfigurationFileLoader(Paths.get("src/test/resources/configTest1"));
        List<ActivityStatWeeklyConfiguration> activityStatWeeklyConfigurations = configurationFileLoader.getActivityStatWeeklyConfigurations();

        assertEquals(1, activityStatWeeklyConfigurations.size());

        ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration = activityStatWeeklyConfigurations.getFirst();
        assertEquals("0 6 * * MON", activityStatWeeklyConfiguration.baseConfiguration().getCronSchedule());
        assertEquals(1, activityStatWeeklyConfiguration.projects().size());
        assertEquals("demo", Sets.getSomeElement(activityStatWeeklyConfiguration.projects()));
        assertTrue(activityStatWeeklyConfiguration.excludeProjects().isEmpty());
        assertTrue(activityStatWeeklyConfiguration.programs().isEmpty());
        assertTrue(activityStatWeeklyConfiguration.excludePrograms().isEmpty());
        assertEquals(2, activityStatWeeklyConfiguration.recipients().size());
        assertTrue(activityStatWeeklyConfiguration.recipients().contains("dummy@example.com"));
        assertTrue(activityStatWeeklyConfiguration.recipients().contains("dummy2@example.com"));
    }

}