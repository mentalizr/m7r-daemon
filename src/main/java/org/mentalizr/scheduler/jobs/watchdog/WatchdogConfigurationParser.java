package org.mentalizr.scheduler.jobs.watchdog;

import de.arthurpicht.configuration.Configuration;
import org.mentalizr.scheduler.jobs.BaseConfiguration;
import org.mentalizr.scheduler.jobs.JobConfigurationParser;

import java.nio.file.Path;

public class WatchdogConfigurationParser extends JobConfigurationParser {

    public WatchdogConfigurationParser(BaseConfiguration baseConfiguration, Configuration configuration, Path configurationPath) {
        super(baseConfiguration, configuration, configurationPath);
    }

    @Override
    public WatchdogConfiguration parse() {

        return new WatchdogConfiguration(
                baseConfiguration
        );
    }

}
