package org.mentalizr.scheduler.configuration;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import de.arthurpicht.configuration.ConfigurationFileNotFoundException;
import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.commons.paths.host.hostDir.M7rSchedulerActiveFlagFile;
import org.mentalizr.commons.paths.host.hostDir.M7rSchedulerConfigDir;
import org.mentalizr.commons.paths.host.hostDir.M7rSchedulerConfigHashFile;
import org.mentalizr.scheduler.M7rSchedulerConfigurationException;
import org.mentalizr.scheduler.M7rSchedulerException;
import org.mentalizr.scheduler.M7rSchedulerInitializationException;
import org.mentalizr.scheduler.helper.Checksums;
import org.mentalizr.scheduler.helper.FileHelper;
import org.mentalizr.scheduler.jobs.BaseConfiguration;
import org.mentalizr.scheduler.jobs.JobConfiguration;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration;
import org.mentalizr.scheduler.jobs.activityStatWeekly.ActivityStatWeeklyConfigurationParser;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatConfiguration;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatConfigurationParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Path;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class JobConfigurations {

    private static final Logger logger = LoggerFactory.getLogger(JobConfigurations.class);

    private final List<JobConfiguration> jobConfigurations;

    public JobConfigurations(List<JobConfiguration> jobConfigurations) {
        this.jobConfigurations = Collections.unmodifiableList(jobConfigurations);
    }

//    public static JobConfigurations create() {
//        return new JobConfigurations(new M7rSchedulerConfigDir().asPath());
//    }

//    public JobConfigurations(Path configDir) {
//        List<Path> configurationFiles = scanSchedulerConfigDir(configDir);
//
//        logger.info(configurationFiles.size() + " configuration files found.");
//        for (Path path : configurationFiles) {
//            logger.info("configuration file: " + path.toString());
//        }
//
//        List<JobConfiguration> jobConfigurations = new ArrayList<>();
//
//        for (Path configurationFile : configurationFiles) {
//            ConfigurationFactory configurationFactory = obtainConfigurationFactory(configurationFile);
//            BaseConfiguration baseConfiguration = getBaseConfiguration(configurationFile, configurationFactory);
//
//            if (configurationFactory.hasSection(ActivityStatWeeklyConfiguration.SECTION_NAME)) {
//                Configuration activityStatWeeklySection
//                        = configurationFactory.getConfiguration(ActivityStatWeeklyConfiguration.SECTION_NAME);
//                ActivityStatWeeklyConfigurationParser activityStatWeeklyConfigurationParser
//                        = new ActivityStatWeeklyConfigurationParser(
//                        baseConfiguration,
//                        activityStatWeeklySection,
//                        configurationFile);
//                ActivityStatWeeklyConfiguration activityStatWeeklyConfiguration
//                        = activityStatWeeklyConfigurationParser.parse();
//                jobConfigurations.add(activityStatWeeklyConfiguration);
//            } else if (configurationFactory.hasSection(HeartbeatConfiguration.SECTION_NAME)) {
//                Configuration heartbeatSection = configurationFactory.getConfiguration(HeartbeatConfiguration.SECTION_NAME);
//                HeartbeatConfigurationParser heartbeatConfigurationParser
//                        = new HeartbeatConfigurationParser(baseConfiguration, heartbeatSection, configurationFile);
//                HeartbeatConfiguration heartbeatConfiguration = heartbeatConfigurationParser.parse();
//                jobConfigurations.add(heartbeatConfiguration);
//            } else {
//                throw new M7rSchedulerConfigurationException("No valid scheduler configuration: " +
//                        "[" + configurationFile.toAbsolutePath() + "]. Section name not recognized.");
//            }
//        }
//
//        this.jobConfigurations = Collections.unmodifiableList(jobConfigurations);
//
////        this.activityStatWeeklyConfigurations = Collections.unmodifiableList(activityStatWeeklyConfigurations);
////        this.heartbeatConfigurations = Collections.unmodifiableList(heartbeatConfigurations);
//
//        saveHash(configurationFiles);
//    }

    public List<JobConfiguration> getJobConfigurations() {
        return this.jobConfigurations;
    }

    public List<ActivityStatWeeklyConfiguration> getActivityStatWeeklyConfigurations() {
        return this.jobConfigurations.stream()
                .filter(jobConfiguration -> jobConfiguration instanceof ActivityStatWeeklyConfiguration)
                .map(jobConfiguration -> (ActivityStatWeeklyConfiguration) jobConfiguration)
                .collect(Collectors.toList());
    }

    public List<HeartbeatConfiguration> getHeartbeatConfigurations() {
        return this.jobConfigurations.stream()
                .filter(jobConfiguration -> jobConfiguration instanceof HeartbeatConfiguration)
                .map(jobConfiguration -> (HeartbeatConfiguration) jobConfiguration)
                .collect(Collectors.toList());
    }

//    public static boolean hasConsistentConfiguration() {
//        Path schedulerConfigDir = new M7rSchedulerConfigDir().asPath();
//        return hasConsistentConfiguration(schedulerConfigDir);
//    }
//
//    public static boolean hasConsistentConfiguration(Path schedulerConfigDir) {
//        if (!SchedulerConfigHashFile.exists())
//            return false;
//        List<Path> configurationFiles = scanSchedulerConfigDir(schedulerConfigDir);
//        String currentHash;
//        try {
//            currentHash = Checksums.computeSha256Checksum(configurationFiles);
//        } catch (NoSuchAlgorithmException | IOException e) {
//            throw new M7rSchedulerException("Error computing config hash: " + e.getMessage(), e);
//        }
//        String savedHash;
//        try {
//            savedHash = SchedulerConfigHashFile.read();
//        } catch (IOException e) {
//            throw new M7rSchedulerException("Error reading scheduler config hash file: " + e.getMessage(), e);
//        }
//        return currentHash.equals(savedHash);
//    }

//    private static List<Path> scanSchedulerConfigDir(Path configDir) {
//        if (!FileUtils.isExistingDirectory(configDir))
//            throw new M7rSchedulerInitializationException("The config directory does not exist: " +
//                    "[" + configDir.toAbsolutePath() + "].");
//
//        try {
//            List<Path> containingFiles = FileHelper.getRegularFilesNotEndingWithTildeInDirectory(configDir);
//            containingFiles.remove(new M7rSchedulerActiveFlagFile().asPath());
//            containingFiles.remove(new M7rSchedulerConfigHashFile().asPath());
//            return containingFiles;
//
//        } catch (IOException e) {
//            throw new M7rSchedulerException("Could not scan directory: [" + configDir.toAbsolutePath() + "].", e);
//        }
//    }
//
//    private ConfigurationFactory obtainConfigurationFactory(Path configurationFile) {
//        ConfigurationFactory configurationFactory = new ConfigurationFactory();
//        try {
//            configurationFactory.addConfigurationFileFromFilesystem(configurationFile.toFile());
//        } catch (ConfigurationFileNotFoundException | IOException e) {
//            throw new M7rSchedulerException("Error binding scheduler configuration file ["
//                                            + configurationFile.toAbsolutePath() + "]: " + e.getMessage(), e);
//        }
//        return configurationFactory;
//    }
//
//    private BaseConfiguration getBaseConfiguration(Path configurationFile, ConfigurationFactory configurationFactory) {
//        Configuration configuration = configurationFactory.getConfiguration();
//        if (!configuration.containsKey(BaseConfiguration.CRON_SCHEDULE))
//            throw new M7rSchedulerException("Configuration file [" + configurationFile.toAbsolutePath() + "] " +
//                                            "does not contain [" + BaseConfiguration.CRON_SCHEDULE + "] parameter.");
//        String name = configurationFile.getFileName().toString();
//        String cronSchedule = configuration.getString(BaseConfiguration.CRON_SCHEDULE);
//        return new BaseConfiguration(name, cronSchedule);
//    }
//
//    private void saveHash(List<Path> configurationFiles) {
//        try {
//            String hash = Checksums.computeSha256Checksum(configurationFiles);
//            SchedulerConfigHashFile.write(hash);
//        } catch (NoSuchAlgorithmException | IOException e) {
//            throw new M7rSchedulerException("Error on saving scheduler configuration hash file: " + e.getMessage(), e);
//        }
//    }

}
