package org.mentalizr.daemon.jobs;

public class BaseConfiguration {

    public static final String CRON_SCHEDULE = "cron-schedule";

    private final String cronSchedule;

    public BaseConfiguration(String cronSchedule) {
        this.cronSchedule = cronSchedule;
    }

    public String getCronSchedule() {
        return cronSchedule;
    }

}
