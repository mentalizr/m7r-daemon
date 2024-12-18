package org.mentalizr.daemon;

import org.mentalizr.daemon.appInit.ApplicationInitialization;
import org.mentalizr.daemon.appInit.ApplicationInitializationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Daemon {

    private static final Logger logger = LoggerFactory.getLogger(Daemon.class);

    public static void main(String[] args) {

        try {
            ApplicationInitialization.execute();
        } catch (ApplicationInitializationException e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }

        while (true) {
            logger.info("Daemon is running");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // din
            }
        }

    }

}
