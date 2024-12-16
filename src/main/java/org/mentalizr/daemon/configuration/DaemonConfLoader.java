package org.mentalizr.daemon.configuration;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import org.mentalizr.commons.paths.host.hostDir.DaemonConfigFile;

import static org.mentalizr.daemon.configuration.ConfigurationHelper.*;
import static org.mentalizr.daemon.configuration.DaemonConf.*;

public class DaemonConfLoader {

    private static final DaemonConfigFile daemonConfigFile = new DaemonConfigFile();

    public static DaemonConf load() {

        ConfigurationFactory configurationFactory = bindConfigFile(daemonConfigFile);
        Configuration configuration = configurationFactory.getConfiguration();

        boolean autostart = getMandatoryBoolean(configuration, AUTOSTART, daemonConfigFile);
        boolean watchdog = getMandatoryBoolean(configuration, WATCHDOG, daemonConfigFile);
        int watchdogIntervalSec = getMandatoryInt(configuration, WATCHDOG_INTERVAL_SEC, daemonConfigFile);

        return new DaemonConf(
                autostart,
                watchdog,
                watchdogIntervalSec
        );
    }

}
