package org.mentalizr.daemon.jobs.activityStatWeekly;

import org.mentalizr.daemon.jobs.BaseConfiguration;

import java.util.List;
import java.util.Set;

public record ActivityStatWeeklyConfiguration(
        BaseConfiguration baseConfiguration,
        Set<String> programs,
        Set<String> excludePrograms,
        Set<String> projects,
        Set<String> excludeProjects,
        List<String> recipients
) {

    public static final String PROGRAMS = "programs";
    public static final String EXCLUDE_PROGRAMS = "exclude_programs";
    public static final String PROJECTS = "projects";
    public static final String EXCLUDE_PROJECTS = "exclude_projects";
    public static final String RECIPIENTS = "recipients";

    public boolean hasPrograms() {
        return programs != null && !programs.isEmpty();
    }

    public boolean hasExcludePrograms() {
        return excludePrograms != null && !excludePrograms.isEmpty();
    }

    public boolean hasProjects() {
        return projects != null && !projects.isEmpty();
    }

    public boolean hasExcludeProjects() {
        return excludeProjects != null && !excludeProjects.isEmpty();
    }

}
