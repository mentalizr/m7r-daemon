package org.mentalizr.scheduler.configuration.obsolete;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.configuration.ConfigurationFactory;
import org.mentalizr.commons.paths.host.hostDir.SchedulerConfigFile;

import static org.mentalizr.scheduler.configuration.obsolete.ConfigurationHelper.*;
import static org.mentalizr.scheduler.configuration.obsolete.DaemonConf.*;

public class DaemonConfLoader {

    private static final SchedulerConfigFile SCHEDULER_CONFIG_FILE = new SchedulerConfigFile();

    public static DaemonConf load() {

        ConfigurationFactory configurationFactory = bindConfigFile(SCHEDULER_CONFIG_FILE);
        Configuration configuration = configurationFactory.getConfiguration();

        boolean autostart = getMandatoryBoolean(configuration, AUTOSTART, SCHEDULER_CONFIG_FILE);
        boolean watchdog = getMandatoryBoolean(configuration, WATCHDOG, SCHEDULER_CONFIG_FILE);
        int watchdogIntervalSec = getMandatoryInt(configuration, WATCHDOG_INTERVAL_SEC, SCHEDULER_CONFIG_FILE);

        return new DaemonConf(
                autostart,
                watchdog,
                watchdogIntervalSec
        );
    }

}
