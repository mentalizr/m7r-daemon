package org.mentalizr.scheduler.helper;

import static de.arthurpicht.utils.core.assertion.MethodPreconditions.assertArgumentNotNull;

public class StringHelper {

    public static String cutOff(String string, String cutoff) {
        assertArgumentNotNull("string", string);
        assertArgumentNotNull("cutoff", cutoff);
        if (string.endsWith(cutoff)) {
            return string.substring(0, string.length() - cutoff.length());
        } else {
            throw new IllegalArgumentException("Specified string [" + string + "] " +
                    "does not end with specified cutoff [" + cutoff + "].");
        }
    }

}
