package org.mentalizr.scheduler.jobs;

import org.mentalizr.scheduler.configuration.SchedulerActiveFlagFile;

public class JobHelper {

    public static boolean isInactive() {
        return !SchedulerActiveFlagFile.exists();
    }

}
