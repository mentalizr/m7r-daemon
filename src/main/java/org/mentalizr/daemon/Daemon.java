package org.mentalizr.daemon;

import de.arthurpicht.linuxWrapper.core.ps.Ps;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import org.mentalizr.commons.DaemonPidFile;
import org.mentalizr.daemon.appInit.ApplicationInitialization;
import org.mentalizr.daemon.appInit.ApplicationInitializationException;
import org.mentalizr.daemon.jobs.HeartbeatJob;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class Daemon {

    private static final Logger logger = LoggerFactory.getLogger(Daemon.class);
    private static final DaemonPidFile daemonPidFile = new DaemonPidFile();

    public static void main(String[] args) {

        try {
            ApplicationInitialization.execute();
        } catch (ApplicationInitializationException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        logger.info("Starting Daemon...");

        logger.debug("PID file is: " + daemonPidFile.asPath().toAbsolutePath());

        try {
            if (alreadyRunning()) {
                logger.error("Daemon is already running. Exiting.");
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
            scheduler.start();

            JobDetail job = JobBuilder.newJob(HeartbeatJob.class)
                    .withIdentity("heartbeat", "demo")
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("heartbeat-trigger", "demo")
                    .startNow()
                    .withSchedule(SimpleScheduleBuilder.simpleSchedule().withIntervalInSeconds(5).repeatForever())
                    .build();

            scheduler.scheduleJob(job, trigger);

        } catch (SchedulerException e) {
            logger.error("Starting daemon failed: " + e.getMessage(), e);
        }

    }

    private static boolean alreadyRunning() {
        if (daemonPidFile.exists()) {
            long pid = daemonPidFile.getPid();
            ProcessResultCollection processResultCollection = Ps.execute(Math.toIntExact(pid));
            return !Ps.noProcessForPidFound(processResultCollection);
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
            logger.info("Daemon is shut down.");
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

}
