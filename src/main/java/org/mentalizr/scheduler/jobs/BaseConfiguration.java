package org.mentalizr.scheduler.jobs;

@SuppressWarnings("ClassCanBeRecord")
public class BaseConfiguration {

    public static final String CRON_SCHEDULE = "cron-schedule";
    public static final String ENABLED = "enabled";
    public static final String NOTIFY_ON_SUCCESS = "notify-on-success";
    public static final String NOTIFY_ON_FAILURE = "notify-on-failure";

    private final String name;
    private final boolean enabled;
    private final String cronSchedule;
    private final boolean notifyOnSuccess;
    private final boolean notifyOnFailure;

    public BaseConfiguration(String name, boolean enabled, String cronSchedule, boolean notifyOnSuccess, boolean notifyOnFailure) {
        this.name = name;
        this.enabled = enabled;
        this.cronSchedule = cronSchedule;
        this.notifyOnSuccess = notifyOnSuccess;
        this.notifyOnFailure = notifyOnFailure;
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

    public boolean isNotifyOnSuccess() {
        return notifyOnSuccess;
    }

    public boolean isNotifyOnFailure() {
        return notifyOnFailure;
    }


}
