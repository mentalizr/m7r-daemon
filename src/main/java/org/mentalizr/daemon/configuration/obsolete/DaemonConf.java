package org.mentalizr.daemon.configuration.obsolete;

public class DaemonConf {

    public static final String AUTOSTART = "autostart";
    public static final String WATCHDOG = "watchdog";
    public static final String WATCHDOG_INTERVAL_SEC = "watchdog_interval_sec";

    private final boolean autostart;
    private final boolean watchdog;
    private final int watchdogIntervalSec;

    public DaemonConf(boolean autostart, boolean watchdog, int watchdogIntervalSec) {
        this.autostart = autostart;
        this.watchdog = watchdog;
        this.watchdogIntervalSec = watchdogIntervalSec;
    }

    public boolean isAutostart() {
        return autostart;
    }

    public boolean isWatchdog() {
        return watchdog;
    }

    public int getWatchdogIntervalSec() {
        return watchdogIntervalSec;
    }

}
