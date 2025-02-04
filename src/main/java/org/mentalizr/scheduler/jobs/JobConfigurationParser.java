package org.mentalizr.scheduler.jobs;

import de.arthurpicht.configuration.Configuration;
import org.mentalizr.scheduler.helper.ConfigurationHelper;

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

    protected void checkForParameterSyntaxErrors(Set<String> occurringParameters) {
        ConfigurationHelper.checkForParameterSyntaxErrors(this.configurationFile, this.configuration, occurringParameters);
    }

    protected void checkForMandatoryParameters(Set<String> mandatoryParameters) {
        ConfigurationHelper.checkForMandatoryParameters(this.configurationFile, this.configuration, mandatoryParameters);
    }

}
