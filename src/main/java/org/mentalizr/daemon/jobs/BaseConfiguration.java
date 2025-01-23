package org.mentalizr.daemon.jobs;

public class BaseConfiguration {

    public static final String CRON_SCHEDULE = "cron-schedule";

    private final String name;
    private final String cronSchedule;

    public BaseConfiguration(String name, String cronSchedule) {
        this.name = name;
        this.cronSchedule = cronSchedule;
    }

    public String getName() {
        return name;
    }

    public String getCronSchedule() {
        return cronSchedule;
    }

}
