package org.mentalizr.daemon.jobs.activityStatWeekly;

import de.arthurpicht.configuration.Configuration;
import org.mentalizr.daemon.DaemonException;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

public class ActivityStatWeeklyConfiguration {

    public static final String PROGRAMS = "programs";
    public static final String EXCLUDE_PROGRAMS = "exclude_programs";
    public static final String PROJECTS = "projects";
    public static final String EXCLUDE_PROJECTS = "exclude_projects";

    private Configuration configuration;

    public ActivityStatWeeklyConfiguration(Path configurationPath, Configuration configuration) {
        if (configuration.containsKey(PROGRAMS) || configuration.containsKey(EXCLUDE_PROGRAMS))
            throw new DaemonException("Job configuration [" + configurationPath.toAbsolutePath() + "] " +
                                      "has contradictions: [" + PROGRAMS + "] and [" + EXCLUDE_PROGRAMS + "].");
        if (configuration.containsKey(PROJECTS) || configuration.containsKey(EXCLUDE_PROJECTS))
            throw new DaemonException("Job configuration [" + configurationPath.toAbsolutePath() + "] " +
                                      "has contradictions: [" + PROJECTS + "] and [" + EXCLUDE_PROJECTS + "].");
        this.configuration = configuration;
    }

    public boolean hasPrograms() {
        return this.configuration.containsKey(PROGRAMS);
    }

    public Set<String> getPrograms() {
        if (!this.hasPrograms())
            throw new IllegalStateException("No programs configured");
        List<String> programs = this.configuration.getStringList(PROGRAMS);
        return Set.copyOf(programs);
    }

    public boolean hasExcludePrograms() {
        return this.configuration.containsKey(EXCLUDE_PROGRAMS);
    }

    public Set<String> getExcludePrograms() {
        throw new RuntimeException("Not implemented yet");
    }

}
