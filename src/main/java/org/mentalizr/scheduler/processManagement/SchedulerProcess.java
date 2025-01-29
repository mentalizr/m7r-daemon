package org.mentalizr.scheduler.processManagement;

import de.arthurpicht.processExecutor.ProcessExecutionException;
import de.arthurpicht.processExecutor.ProcessExecutor;
import de.arthurpicht.processExecutor.ProcessExecutorBuilder;
import de.arthurpicht.processExecutor.ProcessResultCollection;
import de.arthurpicht.utils.core.strings.Strings;
import org.mentalizr.commons.paths.host.GitReposDir;
import org.mentalizr.scheduler.helper.LinuxHelper;

public class SchedulerProcess {

    private static final String SCHEDULER_START_COMMAND
            = GitReposDir.createInstance().asPath()
            .resolve("core/m7r-scheduler/bin/m7r-scheduler-start.sh").toString();

    public static void start() {
        ProcessExecutor processExecutor = new ProcessExecutorBuilder()
                .withCommands(SCHEDULER_START_COMMAND)
                .build();
        try {
            int exitCode = processExecutor.execute();
            if (exitCode != 0)
                throw new RuntimeException("Scheduler start command [" + SCHEDULER_START_COMMAND + " " +
                        "finished with non-zero exit code: [" + exitCode + "].");
        } catch (ProcessExecutionException e) {
            throw new RuntimeException("Scheduler start command execution failed: " + e.getMessage(), e);
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // din
        }

        if (!isRunning())
            throw new RuntimeException("Starting Scheduler failed. See log for details.");
    }

    public static void stop() {
        DaemonPidFile pidFile = new DaemonPidFile();
        if (!pidFile.exists())
            throw new IllegalStateException("PID file does not exist: [" + pidFile + "].");
        int pid = pidFile.getPid();
        ProcessResultCollection processResultCollection = LinuxHelper.kill(pid);
        if (processResultCollection.isFail())
            throw new RuntimeException(
                    "Stopping daemon process failed: " + Strings.listing(processResultCollection.getErrorOut(),
                            ", "));
    }

    public static boolean isRunning() {
        DaemonPidFile pidFile = new DaemonPidFile();
        if (pidFile.exists()) {
            long pid = pidFile.getPid();
            return LinuxHelper.hasProcess(pid);
        } else {
            return false;
        }
    }

    public static boolean isStopped() {
        return !isRunning();
    }

}
