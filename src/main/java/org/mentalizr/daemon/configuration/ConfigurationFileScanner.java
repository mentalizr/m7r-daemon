package org.mentalizr.daemon.configuration;

import de.arthurpicht.utils.io.nio2.FileUtils;
import org.mentalizr.commons.paths.host.hostDir.M7rDaemonActiveFlagFile;
import org.mentalizr.commons.paths.host.hostDir.M7rDaemonConfigDir;
import org.mentalizr.daemon.DaemonException;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class ConfigurationFileScanner {

    private static final M7rDaemonConfigDir CONFIG_DIR = new M7rDaemonConfigDir();

    public static List<Path> scan() {
        try {
            List<Path> containingFiles = FileUtils.getContainingFiles(CONFIG_DIR.asPath());
            containingFiles.remove(new M7rDaemonActiveFlagFile().asPath());
            return containingFiles;

        } catch (IOException e) {
            throw new DaemonException("Could not scan directory: [" + CONFIG_DIR.asPath() + "].", e);
        }
    }

}
