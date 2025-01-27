package org.mentalizr.daemon.jobs.heartbeat;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.utils.core.collection.Sets;
import org.mentalizr.daemon.jobs.BaseConfiguration;
import org.mentalizr.daemon.jobs.JobConfigurationParser;

import java.nio.file.Path;

import static org.mentalizr.daemon.jobs.heartbeat.HeartbeatConfiguration.LOG_MESSAGE;

public class HeartbeatConfigurationParser extends JobConfigurationParser {

    public HeartbeatConfigurationParser(BaseConfiguration baseConfiguration, Configuration configuration, Path configurationPath) {
        super(baseConfiguration, configuration, configurationPath);
    }

    @Override
    public HeartbeatConfiguration parse() {
        checkForParameterSyntaxErrors(Sets.newHashSet(LOG_MESSAGE));
        checkForMandatoryParameters(Sets.newHashSet(LOG_MESSAGE));

        String logMessage = configuration.getString(LOG_MESSAGE);

        return new HeartbeatConfiguration(baseConfiguration, logMessage);
    }

}
