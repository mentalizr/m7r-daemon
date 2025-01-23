package org.mentalizr.daemon.configuration;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import de.arthurpicht.configuration.ConfigurationFileNotFoundException;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.commons.paths.host.hostDir.M7rDaemonActiveFlagFile;
import org.mentalizr.daemon.DaemonConfigurationException;
import org.mentalizr.daemon.DaemonException;
import org.mentalizr.daemon.DaemonInitializationException;
import org.mentalizr.daemon.jobs.BaseConfiguration;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfigurationParser;
import org.mentalizr.daemon.jobs.heartbeat.HeartbeatConfiguration;
import org.mentalizr.daemon.jobs.heartbeat.HeartbeatConfigurationParser;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class JobConfigurations {

    public static final String ACTIVITY_STAT_WEEKLY = "activity-stat-weekly";
    public static final String HEARTBEAT = "heartbeat";

    private final Path configDir;

    private final List<ActivityStatWeeklyConfiguration> activityStatWeeklyConfigurations;
    private final List<HeartbeatConfiguration> heartbeatConfigurations;

    public JobConfigurations(Path configDir) {
        this.configDir = configDir;
        if (!FileUtils.isExistingDirectory(this.configDir))
            throw new DaemonInitializationException("The config directory does not exist: [" + this.configDir.toAbsolutePath() + "].");

        this.activityStatWeeklyConfigurations = new ArrayList<>();
        this.heartbeatConfigurations = new ArrayList<>();

        List<Path> configurationFiles = scanSchedulerConfigDir();
        for (Path configurationFile : configurationFiles) {
            ConfigurationFactory configurationFactory = obtainConfigurationFactory(configurationFile);
            BaseConfiguration baseConfiguration = getBaseConfiguration(configurationFile, configurationFactory);

            if (configurationFactory.hasSection(ACTIVITY_STAT_WEEKLY)) {
                Configuration activityStatWeeklySection = configurationFactory.getConfiguration(ACTIVITY_STAT_WEEKLY);
                ActivityStatWeeklyConfigurationParser activityStatWeeklyConfigurationParser
                        = new ActivityStatWeeklyConfigurationParser(
                        baseConfiguration,
                        activityStatWeeklySection,
                        configurationFile);
                ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration
                        = activityStatWeeklyConfigurationParser.parse();
                this.activityStatWeeklyConfigurations.add(activityStatWeeklyConfiguration);
            } else if (configurationFactory.hasSection(HEARTBEAT)) {
                Configuration heartbeatSection = configurationFactory.getConfiguration(HEARTBEAT);
                HeartbeatConfigurationParser heartbeatConfigurationParser
                        = new HeartbeatConfigurationParser(baseConfiguration, heartbeatSection, configurationFile);
                HeartbeatConfiguration heartbeatConfiguration = heartbeatConfigurationParser.parse();
                this.heartbeatConfigurations.add(heartbeatConfiguration);
            } else {
                throw new DaemonConfigurationException("No valid scheduler configuration: " +
                        "[" + configurationFile.toAbsolutePath() + "]. Section name not recognized.");
            }
        }
    }

    public List<ActivityStatWeeklyConfiguration> getActivityStatWeeklyConfigurations() {
        return this.activityStatWeeklyConfigurations;
    }

    public List<HeartbeatConfiguration> getHeartbeatConfigurations() {
        return this.heartbeatConfigurations;
    }

    private List<Path> scanSchedulerConfigDir() {
        try {
            List<Path> containingFiles = FileUtils.getRegularNonHiddenFilesInDirectory(this.configDir);
            containingFiles.remove(new M7rDaemonActiveFlagFile().asPath());
            return containingFiles;

        } catch (IOException e) {
            throw new DaemonException("Could not scan directory: [" + this.configDir.toAbsolutePath() + "].", e);
        }
    }

    private ConfigurationFactory obtainConfigurationFactory(Path configurationFile) {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addConfigurationFileFromFilesystem(configurationFile.toFile());
        } catch (ConfigurationFileNotFoundException | IOException e) {
            throw new DaemonException("Error binding scheduler configuration file ["
                    + configurationFile.toAbsolutePath() + "]: " + e.getMessage(), e);
        }
        return configurationFactory;
    }

    private BaseConfiguration getBaseConfiguration(Path configurationFile, ConfigurationFactory configurationFactory) {
        Configuration configuration = configurationFactory.getConfiguration();
        if (!configuration.containsKey(BaseConfiguration.CRON_SCHEDULE))
            throw new DaemonException("Configuration file [" + configurationFile.toAbsolutePath() + "] " +
                    "does not contain [" + BaseConfiguration.CRON_SCHEDULE + "] parameter.");
        String name = configurationFile.getFileName().toString();
        String cronSchedule = configuration.getString(BaseConfiguration.CRON_SCHEDULE);
        return new BaseConfiguration(name, cronSchedule);
    }

}
