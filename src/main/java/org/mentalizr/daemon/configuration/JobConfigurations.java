package org.mentalizr.daemon.configuration;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import de.arthurpicht.configuration.ConfigurationFileNotFoundException;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.commons.paths.host.hostDir.M7rSchedulerActiveFlagFile;
import org.mentalizr.commons.paths.host.hostDir.M7rSchedulerConfigDir;
import org.mentalizr.commons.paths.host.hostDir.M7rSchedulerConfigHashFile;
import org.mentalizr.daemon.M7rSchedulerConfigurationException;
import org.mentalizr.daemon.M7rSchedulerException;
import org.mentalizr.daemon.M7rSchedulerInitializationException;
import org.mentalizr.daemon.helper.Checksums;
import org.mentalizr.daemon.helper.FileHelper;
import org.mentalizr.daemon.jobs.BaseConfiguration;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeekly;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfigurationParser;
import org.mentalizr.daemon.jobs.heartbeat.Heartbeat;
import org.mentalizr.daemon.jobs.heartbeat.HeartbeatConfiguration;
import org.mentalizr.daemon.jobs.heartbeat.HeartbeatConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JobConfigurations {

    private static final Logger logger = LoggerFactory.getLogger(JobConfigurations.class);

    private final List<ActivityStatWeeklyConfiguration> activityStatWeeklyConfigurations;
    private final List<HeartbeatConfiguration> heartbeatConfigurations;

    public static JobConfigurations create() {
        return new JobConfigurations(new M7rSchedulerConfigDir().asPath());
    }

    public JobConfigurations(Path configDir) {
        List<Path> configurationFiles = scanSchedulerConfigDir(configDir);

        logger.info(configurationFiles.size() + " configuration files found.");
        for (Path path : configurationFiles) {
            logger.info("configuration file: " + path.toString());
        }

        List<ActivityStatWeeklyConfiguration> activityStatWeeklyConfigurations = new ArrayList<>();
        List<HeartbeatConfiguration> heartbeatConfigurations = new ArrayList<>();

        for (Path configurationFile : configurationFiles) {
            ConfigurationFactory configurationFactory = obtainConfigurationFactory(configurationFile);
            BaseConfiguration baseConfiguration = getBaseConfiguration(configurationFile, configurationFactory);

            if (configurationFactory.hasSection(ActivityStatWeekly.TYPE)) {
                Configuration activityStatWeeklySection = configurationFactory.getConfiguration(ActivityStatWeekly.TYPE);
                ActivityStatWeeklyConfigurationParser activityStatWeeklyConfigurationParser
                        = new ActivityStatWeeklyConfigurationParser(
                        baseConfiguration,
                        activityStatWeeklySection,
                        configurationFile);
                ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration
                        = activityStatWeeklyConfigurationParser.parse();
                activityStatWeeklyConfigurations.add(activityStatWeeklyConfiguration);
            } else if (configurationFactory.hasSection(Heartbeat.TYPE)) {
                Configuration heartbeatSection = configurationFactory.getConfiguration(Heartbeat.TYPE);
                HeartbeatConfigurationParser heartbeatConfigurationParser
                        = new HeartbeatConfigurationParser(baseConfiguration, heartbeatSection, configurationFile);
                HeartbeatConfiguration heartbeatConfiguration = heartbeatConfigurationParser.parse();
                heartbeatConfigurations.add(heartbeatConfiguration);
            } else {
                throw new M7rSchedulerConfigurationException("No valid scheduler configuration: " +
                                                             "[" + configurationFile.toAbsolutePath() + "]. Section name not recognized.");
            }
        }

        this.activityStatWeeklyConfigurations = Collections.unmodifiableList(activityStatWeeklyConfigurations);
        this.heartbeatConfigurations = Collections.unmodifiableList(heartbeatConfigurations);

        saveHash(configurationFiles);
    }

    public List<ActivityStatWeeklyConfiguration> getActivityStatWeeklyConfigurations() {
        return this.activityStatWeeklyConfigurations;
    }

    public List<HeartbeatConfiguration> getHeartbeatConfigurations() {
        return this.heartbeatConfigurations;
    }

    public static boolean hasConsistentConfiguration() {
        Path schedulerConfigDir = new M7rSchedulerConfigDir().asPath();
        return hasConsistentConfiguration(schedulerConfigDir);
    }

    public static boolean hasConsistentConfiguration(Path schedulerConfigDir) {
        if (!SchedulerConfigHashFile.exists())
            return false;
        List<Path> configurationFiles = scanSchedulerConfigDir(schedulerConfigDir);
        String currentHash;
        try {
            currentHash = Checksums.computeSha256Checksum(configurationFiles);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new M7rSchedulerException("Error computing config hash: " + e.getMessage(), e);
        }
        String savedHash;
        try {
            savedHash = SchedulerConfigHashFile.read();
        } catch (IOException e) {
            throw new M7rSchedulerException("Error reading scheduler config hash file: " + e.getMessage(), e);
        }
        return currentHash.equals(savedHash);
    }

    private static List<Path> scanSchedulerConfigDir(Path configDir) {
        if (!FileUtils.isExistingDirectory(configDir))
            throw new M7rSchedulerInitializationException("The config directory does not exist: [" + configDir.toAbsolutePath() + "].");

        try {
            List<Path> containingFiles = FileHelper.getRegularFilesNotEndingWithTildeInDirectory(configDir);
            containingFiles.remove(new M7rSchedulerActiveFlagFile().asPath());
            containingFiles.remove(new M7rSchedulerConfigHashFile().asPath());
            return containingFiles;

        } catch (IOException e) {
            throw new M7rSchedulerException("Could not scan directory: [" + configDir.toAbsolutePath() + "].", e);
        }
    }

    private ConfigurationFactory obtainConfigurationFactory(Path configurationFile) {
        ConfigurationFactory configurationFactory = new ConfigurationFactory();
        try {
            configurationFactory.addConfigurationFileFromFilesystem(configurationFile.toFile());
        } catch (ConfigurationFileNotFoundException | IOException e) {
            throw new M7rSchedulerException("Error binding scheduler configuration file ["
                                            + configurationFile.toAbsolutePath() + "]: " + e.getMessage(), e);
        }
        return configurationFactory;
    }

    private BaseConfiguration getBaseConfiguration(Path configurationFile, ConfigurationFactory configurationFactory) {
        Configuration configuration = configurationFactory.getConfiguration();
        if (!configuration.containsKey(BaseConfiguration.CRON_SCHEDULE))
            throw new M7rSchedulerException("Configuration file [" + configurationFile.toAbsolutePath() + "] " +
                                            "does not contain [" + BaseConfiguration.CRON_SCHEDULE + "] parameter.");
        String name = configurationFile.getFileName().toString();
        String cronSchedule = configuration.getString(BaseConfiguration.CRON_SCHEDULE);
        return new BaseConfiguration(name, cronSchedule);
    }

    private void saveHash(List<Path> configurationFiles) {
        try {
            String hash = Checksums.computeSha256Checksum(configurationFiles);
            SchedulerConfigHashFile.write(hash);
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new M7rSchedulerException("Error on saving scheduler configuration hash file: " + e.getMessage(), e);
        }
    }

}
