package org.mentalizr.scheduler;

import com.google.gson.Gson;
import org.mentalizr.scheduler.jobs.BaseConfiguration;
import org.mentalizr.scheduler.jobs.heartbeat.HeartbeatConfiguration;

public class Dummy {

    public static void main(String[] args) {

        BaseConfiguration baseConfiguration = new BaseConfiguration("myName", true, "myCronSchedule");
        HeartbeatConfiguration heartbeatConfiguration = new HeartbeatConfiguration(baseConfiguration, "myLogMessage");

        String jobJson = new Gson().toJson(heartbeatConfiguration);

        System.out.println(jobJson);

    }

}
