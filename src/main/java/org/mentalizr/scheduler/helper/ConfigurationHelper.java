package org.mentalizr.scheduler.helper;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.scheduler.M7rSchedulerConfigurationException;

import java.nio.file.Path;
import java.util.Set;

public class ConfigurationHelper {

    public static void checkForParameterSyntaxErrors(
            Path configurationFile,
            Configuration configuration,
            Set<String> occurringParameters) {

        Set<String> keys = configuration.getKeys();
        for (String key : keys) {
            if (!occurringParameters.contains(key)) {
                String message = "Illegal parameter [" + key + "] in configuration file ["
                                 + configurationFile.toAbsolutePath() + "]";
                if (Strings.isSpecified(configuration.getSectionName())) {
                    message += " section name [" + configuration.getSectionName() + "].";
                } else {
                    message += ".";
                }
                throw new M7rSchedulerConfigurationException(message);
            }
        }
    }

    public static void checkForMandatoryParameters(
            Path configurationFile,
            Configuration configuration,
            Set<String> mandatoryParameters) {

        Set<String> keys = configuration.getKeys();
        for (String parameter : mandatoryParameters) {
            if (!keys.contains(parameter)) {
                String message = "Mandatory parameter [" + parameter + "] " +
                                 "not found in configuration file [" + configurationFile.toAbsolutePath() + "]";
                if (Strings.isSpecified(configuration.getSectionName())) {
                    message += " section name [" + configuration.getSectionName() + "].";
                } else {
                    message += ".";
                }
                throw new M7rSchedulerConfigurationException(message);
            }
        }
    }

}
