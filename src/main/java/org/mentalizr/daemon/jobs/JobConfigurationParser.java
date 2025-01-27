package org.mentalizr.daemon.jobs;

import de.arthurpicht.configuration.Configuration;
import org.mentalizr.daemon.M7rSchedulerConfigurationException;

import java.nio.file.Path;
import java.util.Set;

public abstract class JobConfigurationParser {

    protected final BaseConfiguration baseConfiguration;
    protected final Configuration configuration;
    protected final Path configurationFile;

    public JobConfigurationParser(BaseConfiguration baseConfiguration, Configuration configuration, Path ConfigurationFile) {
        this.baseConfiguration = baseConfiguration;
        this.configuration = configuration;
        this.configurationFile = ConfigurationFile;
    }

    public abstract JobConfiguration parse();

    protected void checkForParameterSyntaxErrors(Set<String> validParameters) {
        Set<String> keys = this.configuration.getKeys();
        for (String key : keys) {
            if (!validParameters.contains(key))
                throw new M7rSchedulerConfigurationException("Illegal parameter [" + key + "] " +
                                                             "in configuration file [" + this.configurationFile.toAbsolutePath() + "].");
        }
    }

    protected void checkForMandatoryParameters(Set<String> mandatoryParameters) {
        Set<String> keys = configuration.getKeys();
        for (String parameter : mandatoryParameters) {
            if (!keys.contains(parameter))
                throw new M7rSchedulerConfigurationException("Mandatory parameter [" + parameter + "] " +
                                                             "not found in configuration file [" + this.configurationFile.toAbsolutePath() + "].");
        }
    }

}
