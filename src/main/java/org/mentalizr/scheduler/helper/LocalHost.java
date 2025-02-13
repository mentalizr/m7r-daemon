package org.mentalizr.scheduler.helper;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

    public static String getHostname() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            return "UNKNOWN";
        }
    }

}
