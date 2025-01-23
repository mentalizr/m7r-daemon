package org.mentalizr.daemon.jobs;

public abstract class JobConfiguration {

    protected final BaseConfiguration baseConfiguration;

    public JobConfiguration(BaseConfiguration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    public BaseConfiguration getBaseConfiguration() {
        return this.baseConfiguration;
    }

    public String getJobName() {
        return this.getBaseConfiguration().getName();
    }

    public String getTriggerName() {
        return this.getBaseConfiguration().getName() + "-trigger";
    }

    public String getCronSchedule() {
        return this.getBaseConfiguration().getCronSchedule();
    }

}
