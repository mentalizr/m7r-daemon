package org.mentalizr.scheduler.jobs;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.utils.core.collection.Sets;
import org.mentalizr.scheduler.helper.ConfigurationHelper;

import java.nio.file.Path;

import static org.mentalizr.scheduler.jobs.BaseConfiguration.*;
import static org.mentalizr.scheduler.jobs.BaseConfiguration.CRON_SCHEDULE;

public class BaseConfigurationParser {

    public static BaseConfiguration parse(Path configurationFile, Configuration configuration) {

        ConfigurationHelper.checkForParameterSyntaxErrors(
                configurationFile,
                configuration,
                Sets.newHashSet(CRON_SCHEDULE, ENABLED, NOTIFY_ON_SUCCESS, NOTIFY_ON_FAILURE));
        ConfigurationHelper.checkForMandatoryParameters(
                configurationFile,
                configuration,
                Sets.newHashSet(CRON_SCHEDULE)
        );

        String name = configurationFile.getFileName().toString();
        boolean enabled = configuration.getBoolean(ENABLED, true);
        String cronSchedule = configuration.getString(CRON_SCHEDULE);
        boolean notifyOnSuccess = configuration.getBoolean(NOTIFY_ON_SUCCESS, false);
        boolean notifyOnFailure = configuration.getBoolean(NOTIFY_ON_FAILURE, false);

        return new BaseConfiguration(name, enabled, cronSchedule, notifyOnSuccess, notifyOnFailure);
    }

}
