package org.mentalizr.daemon.jobs;

import org.mentalizr.daemon.configuration.SchedulerActiveFlagFile;

public class JobHelper {

    public static boolean isInactive() {
        return !SchedulerActiveFlagFile.exists();
    }

}
