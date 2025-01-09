package org.mentalizr.daemon;

import de.arthurpicht.linuxWrapper.core.ps.Ps;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import org.mentalizr.commons.paths.host.hostDir.M7rDaemonPidFile;
import org.mentalizr.daemon.appInit.ApplicationInitialization;
import org.mentalizr.daemon.appInit.ApplicationInitializationException;
import org.mentalizr.commons.helper.PidFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

@SuppressWarnings("StringConcatenationArgumentToLogCall")
public class Daemon {

    private static final Logger logger = LoggerFactory.getLogger(Daemon.class);
    private static final PidFile pidFile = new PidFile(new M7rDaemonPidFile().asPath());

    public static void main(String[] args) {

        try {
            ApplicationInitialization.execute();
        } catch (ApplicationInitializationException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        logger.info("Starting Daemon...");

        logger.debug("PID file is: " + pidFile.asPath().toAbsolutePath());

        try {
            if (alreadyRunning()) {
                logger.error("Daemon is already running. Exiting.");
                System.exit(2);
            }
            pidFile.create();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        addShutdownHook();

        while (true) {
            logger.info("Daemon is running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // din
            }
        }

    }



    private static boolean alreadyRunning() throws IOException {
        if (pidFile.exists()) {
            long pid = pidFile.getPid();
            ProcessResultCollection processResultCollection = Ps.execute(Math.toIntExact(pid));
            return !Ps.noProcessForPidFound(processResultCollection);
        }
        return false;
    }

    private static void addShutdownHook() {
        Thread shutdownHook = new Thread(() -> {
            try {
                 pidFile.removeIfExists();
            } catch (IOException e) {
                logger.error("Cannot delete PID file [" + pidFile.asPath() + "]: " + e.getMessage(), e);
            }
            logger.info("Daemon is shut down.");
        });
        Runtime.getRuntime().addShutdownHook(shutdownHook);
    }

}
