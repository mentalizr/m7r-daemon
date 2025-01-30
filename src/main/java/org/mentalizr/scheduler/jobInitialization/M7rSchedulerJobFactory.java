package org.mentalizr.scheduler.jobInitialization;

import org.mentalizr.scheduler.jobs.JobConfiguration;
import org.mentalizr.scheduler.jobFactories.SchedulerJobFactory;
import org.quartz.Job;
import org.quartz.Scheduler;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

public class M7rSchedulerJobFactory implements JobFactory {

    private final JobConfiguration jobConfiguration;

    public M7rSchedulerJobFactory(JobConfiguration jobConfiguration) {
        this.jobConfiguration = jobConfiguration;
    }

    @Override
    public Job newJob(TriggerFiredBundle bundle, Scheduler scheduler) {
//            Job job = bundle.getJobDetail().getJobClass().getDeclaredConstructor().newInstance();
        return SchedulerJobFactory.create(this.jobConfiguration);
    }

}
