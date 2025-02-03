package org.mentalizr.scheduler.jobs;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.utils.core.collection.Sets;
import org.mentalizr.scheduler.helper.ConfigurationHelper;

import java.nio.file.Path;

public class BaseConfigurationParser {

    public static BaseConfiguration parse(Path configurationFile, Configuration configuration) {

        ConfigurationHelper.checkForParameterSyntaxErrors(
                configurationFile,
                configuration,
                Sets.newHashSet(BaseConfiguration.CRON_SCHEDULE, BaseConfiguration.ENABLED));
        ConfigurationHelper.checkForMandatoryParameters(
                configurationFile,
                configuration,
                Sets.newHashSet(BaseConfiguration.CRON_SCHEDULE)
        );

        String name = configurationFile.getFileName().toString();
        boolean enabled = configuration.getBoolean(BaseConfiguration.ENABLED, true);
        String cronSchedule = configuration.getString(BaseConfiguration.CRON_SCHEDULE);

        return new BaseConfiguration(name, enabled, cronSchedule);
    }

}
