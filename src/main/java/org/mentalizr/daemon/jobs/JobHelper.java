package org.mentalizr.daemon.jobs;

import org.mentalizr.commons.DaemonActiveFlagFile;

public class JobHelper {

    public static boolean isInactive() {
        return !DaemonActiveFlagFile.exists();
    }

}
