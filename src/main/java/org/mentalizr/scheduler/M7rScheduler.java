package org.mentalizr.scheduler;

import org.mentalizr.scheduler.appInit.ApplicationInitialization;
import org.mentalizr.scheduler.appInit.ApplicationInitializationException;
import org.mentalizr.scheduler.configuration.JobConfigurations;
import org.mentalizr.scheduler.configuration.JobConfigurationsManager;
import org.mentalizr.scheduler.helper.LinuxHelper;
import org.mentalizr.scheduler.jobInitialization.JobInitializer;
import org.mentalizr.scheduler.processManagement.DaemonPidFile;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class M7rScheduler {

    private static final Logger logger = LoggerFactory.getLogger(M7rScheduler.class);
    private static final DaemonPidFile daemonPidFile = new DaemonPidFile();

    public static void main(String[] args) {

        try {
            ApplicationInitialization.execute();
        } catch (ApplicationInitializationException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        logger.info("Starting Scheduler...");

        logger.debug("PID file is: " + daemonPidFile.asPath().toAbsolutePath());

        try {
            if (alreadyRunning()) {
                logger.error("Scheduler is already running. Exiting.");
                System.exit(2);
            }
            daemonPidFile.create();
        } catch (RuntimeException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }


        try {
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();
            addShutdownHook(scheduler);

            logger.info("load configuration ...");
            JobConfigurations jobConfigurations = JobConfigurationsManager.fromConfigFiles();
            logger.info("initialize scheduler ...");
            JobInitializer.initialize(scheduler, jobConfigurations);
            JobConfigurationsManager.saveHash();

            scheduler.start();

        } catch (SchedulerException | RuntimeException e) {
            logger.error("Starting daemon failed: " + e.getMessage(), e);
            System.exit(1);
        }

    }

    private static boolean alreadyRunning() {
        if (daemonPidFile.exists()) {
            long pid = daemonPidFile.getPid();
            return LinuxHelper.hasProcess(pid);
        }
        return false;
    }

    private static void addShutdownHook(Scheduler scheduler) {
        Thread shutdownHook = new Thread(() -> {
            try {
                 daemonPidFile.removeIfExists();
            } catch (RuntimeException e) {
                logger.error("Cannot delete PID file [" + daemonPidFile.asPath() + "]: " + e.getMessage(), e);
            }
            try {
                scheduler.shutdown();
            } catch (SchedulerException e) {
                logger.error("Shutdown of scheduler failed: " + e.getMessage(), e);
            }
            logger.info("Scheduler is shut down.");
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

}
