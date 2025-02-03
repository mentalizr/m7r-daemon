package org.mentalizr.scheduler.jobs;

import org.mentalizr.scheduler.helper.StringHelper;

public abstract class JobConfiguration {

    protected final BaseConfiguration baseConfiguration;

    public JobConfiguration(BaseConfiguration baseConfiguration) {
        this.baseConfiguration = baseConfiguration;
    }

    public BaseConfiguration getBaseConfiguration() {
        return this.baseConfiguration;
    }

    /**
     * The returned job name is equals to the respective configuration file name.
     *
     * @return job name
     */
    public String getJobName() {
        return this.getBaseConfiguration().getName();
    }

    public String getTriggerName() {
        return this.getBaseConfiguration().getName() + "-trigger";
    }

    public String getCronSchedule() {
        return this.getBaseConfiguration().getCronSchedule();
    }

    public String getTypeString() {
        String simpleName = this.getClass().getSimpleName();
        String typeString = StringHelper.cutOff(simpleName, "Configuration");
        return typeString.substring(0, 1).toLowerCase() + typeString.substring(1);
    }

}
