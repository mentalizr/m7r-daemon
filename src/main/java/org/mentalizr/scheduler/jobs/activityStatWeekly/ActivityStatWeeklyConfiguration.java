package org.mentalizr.scheduler.jobs.activityStatWeekly;

import org.mentalizr.scheduler.jobs.BaseConfiguration;
import org.mentalizr.scheduler.jobs.JobConfiguration;

import java.util.List;
import java.util.Set;

public final class ActivityStatWeeklyConfiguration extends JobConfiguration {

    public static final String SECTION_NAME = "activity-stat-weekly";

    public static final String PROGRAMS = "programs";
    public static final String EXCLUDE_PROGRAMS = "exclude_programs";
    public static final String PROJECTS = "projects";
    public static final String EXCLUDE_PROJECTS = "exclude_projects";
    public static final String RECIPIENTS = "recipients";

    private final Set<String> programs;
    private final Set<String> excludePrograms;
    private final Set<String> projects;
    private final Set<String> excludeProjects;
    private final List<String> recipients;

    public ActivityStatWeeklyConfiguration(
            BaseConfiguration baseConfiguration,
            Set<String> programs,
            Set<String> excludePrograms,
            Set<String> projects,
            Set<String> excludeProjects,
            List<String> recipients
    ) {
        super(baseConfiguration);
        this.programs = programs;
        this.excludePrograms = excludePrograms;
        this.projects = projects;
        this.excludeProjects = excludeProjects;
        this.recipients = recipients;
    }

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

    public Set<String> getPrograms() {
        return programs;
    }

    public Set<String> getExcludePrograms() {
        return excludePrograms;
    }

    public Set<String> getProjects() {
        return projects;
    }

    public Set<String> getExcludeProjects() {
        return excludeProjects;
    }

    public List<String> getRecipients() {
        return recipients;
    }

}
