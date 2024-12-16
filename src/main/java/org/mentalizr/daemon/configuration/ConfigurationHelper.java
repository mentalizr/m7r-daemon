package org.mentalizr.daemon.configuration;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import de.arthurpicht.configuration.ConfigurationFileNotFoundException;
import org.mentalizr.commons.paths.M7rFile;
import org.mentalizr.daemon.DaemonConfigurationException;

import java.io.File;
import java.io.IOException;

public class ConfigurationHelper {

    public static ConfigurationFactory bindConfigFile(M7rFile m7rConfigFile) {
        File daemonConfigFileAsFile = m7rConfigFile.asPath().toFile();
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addConfigurationFileFromFilesystem(daemonConfigFileAsFile);
        } catch (ConfigurationFileNotFoundException | IOException e) {
            throw new DaemonConfigurationException("File not found: [" + daemonConfigFileAsFile.getAbsolutePath() + "].");
        }
        return configurationFactory;
    }

    public static boolean getMandatoryBoolean(Configuration configuration, String name, M7rFile m7rFile) {
        if (!configuration.containsKey(name))
            throw new DaemonConfigurationException(
                    "Configuration parameter [" + name + "] not found in ["
                            + m7rFile.asPath().toAbsolutePath() + "].");
        return configuration.getBoolean(name);
    }

    public static int getMandatoryInt(Configuration configuration, String name, M7rFile m7rFile) {
        if (!configuration.containsKey(name))
            throw new DaemonConfigurationException(
                    "Configuration parameter [" + name + "] not found in ["
                            + m7rFile.asPath().toAbsolutePath() + "].");
        return configuration.getInt(name);
    }


}
