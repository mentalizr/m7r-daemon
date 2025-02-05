package org.mentalizr.scheduler.jobs;

@SuppressWarnings("ClassCanBeRecord")
public class BaseConfiguration {

    public static final String CRON_SCHEDULE = "cron-schedule";
    public static final String ENABLED = "enabled";

    private final String name;
    private final boolean enabled;
    private final String cronSchedule;

    public BaseConfiguration(String name, boolean enabled, String cronSchedule) {
        this.name = name;
        this.enabled = enabled;
        this.cronSchedule = cronSchedule;
    }

    public String getName() {
        return name;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public String getCronSchedule() {
        return cronSchedule;
    }

}
