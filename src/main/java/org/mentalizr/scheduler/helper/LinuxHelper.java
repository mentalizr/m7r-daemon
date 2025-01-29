package org.mentalizr.scheduler.helper;

import de.arthurpicht.linuxWrapper.core.ps.Ps;
import de.arthurpicht.processExecutor.ProcessExecution;
import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessResultCollection;

public class LinuxHelper {

    public static ProcessResultCollection kill(int processId) {
        String[] command = {"kill", Integer.toString(processId)};
        try {
            return ProcessExecution.execute(command);
        } catch (ProcessExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public static boolean hasProcess(long processId) {
        ProcessResultCollection processResultCollection = Ps.execute(Math.toIntExact(processId));
        return !Ps.noProcessForPidFound(processResultCollection);
    }

}
