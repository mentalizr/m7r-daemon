package org.mentalizr.scheduler.processManagement;

import org.mentalizr.commons.helper.PidFile;
import org.mentalizr.commons.paths.host.hostDir.M7rSchedulerPidFile;

import java.io.IOException;
import java.nio.file.Path;

public class DaemonPidFile {

    public static class DaemonPidFileException extends RuntimeException {
        public DaemonPidFileException(Throwable cause) {
            super(cause);
        }
    }

    private final PidFile pidFile;

    public DaemonPidFile() {
        this.pidFile =  new PidFile(new M7rSchedulerPidFile().asPath());
    }

    public Path asPath() {
        return this.pidFile.asPath();
    }

    public void create() throws DaemonPidFileException {
        try {
            this.pidFile.create();
        } catch (IOException e) {
            throw new DaemonPidFileException(e);
        }
    }

    public void removeIfExists() throws DaemonPidFileException {
        try {
            this.pidFile.removeIfExists();
        } catch (IOException e) {
            throw new DaemonPidFileException(e);
        }
    }

    public boolean exists() {
        return this.pidFile.exists();
    }

    public int getPid() throws DaemonPidFileException {
        try {
            return this.pidFile.getPid();
        } catch (IOException e) {
            throw new DaemonPidFileException(e);
        }
    }

}
