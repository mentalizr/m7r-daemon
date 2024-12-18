package org.mentalizr.daemon.appInit;

import ch.qos.logback.classic.Level;
import de.arthurpicht.utils.logging.LoggerInit;
import org.mentalizr.commons.paths.host.hostDir.M7rHostLogDir;

import java.io.IOException;
import java.nio.file.Path;

public class ApplicationInitialization {

    public static void execute() {
        createLogDir();
        initLogging();
    }

    private static void createLogDir() throws ApplicationInitializationException {
        M7rHostLogDir m7rHostLogDir = new M7rHostLogDir();
        if (!m7rHostLogDir.exists()) {
            try {
                m7rHostLogDir.create();
            } catch (IOException e) {
                throw new ApplicationInitializationException(
                        "Application initialization failed. Could not create directory ["
                                + m7rHostLogDir.toAbsolutePathString() + "]");
            }
        }
    }

    private static void initLogging() {
        M7rHostLogDir m7rHostLogDir = new M7rHostLogDir();
        Path logFile = m7rHostLogDir.asPath().resolve("m7r-daemon.log");
        LoggerInit.consoleAndFile(logFile, Level.DEBUG, Level.OFF);
    }

}
