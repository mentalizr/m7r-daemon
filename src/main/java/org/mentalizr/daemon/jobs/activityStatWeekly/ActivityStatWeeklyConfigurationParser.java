package org.mentalizr.daemon.jobs.activityStatWeekly;

import de.arthurpicht.configuration.Configuration;
import de.arthurpicht.utils.core.collection.Sets;
import org.mentalizr.daemon.DaemonConfigurationException;
import org.mentalizr.daemon.DaemonException;
import org.mentalizr.daemon.jobs.BaseConfiguration;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.mentalizr.daemon.jobs.activityStatWeekly.ActivityStatWeeklyConfiguration.*;

public class ActivityStatWeeklyConfigurationParser {

    public static ActivityStatWeeklyConfiguration parse(BaseConfiguration baseConfiguration, Configuration configuration, Path configurationPath) {

        checkForParameterSyntaxErrors(
                configuration,
                Sets.newHashSet(PROGRAMS, EXCLUDE_PROGRAMS, PROJECTS, EXCLUDE_PROJECTS, RECIPIENTS),
                configurationPath.toAbsolutePath().toString());
        checkForMandatoryParameters(
                configuration,
                Sets.newHashSet(RECIPIENTS),
                configurationPath.toAbsolutePath().toString());

        if (configuration.containsKey(PROGRAMS) && configuration.containsKey(EXCLUDE_PROGRAMS))
            throw new DaemonException("Job configuration [" + configurationPath.toAbsolutePath() + "] " +
                    "has contradictions: [" + PROGRAMS + "] and [" + EXCLUDE_PROGRAMS + "].");
        if (configuration.containsKey(PROJECTS) && configuration.containsKey(EXCLUDE_PROJECTS))
            throw new DaemonException("Job configuration [" + configurationPath.toAbsolutePath() + "] " +
                    "has contradictions: [" + PROJECTS + "] and [" + EXCLUDE_PROJECTS + "].");

        Set<String> programs = getValueSet(configuration, PROGRAMS);
        Set<String> excludePrograms = getValueSet(configuration, EXCLUDE_PROGRAMS);
        Set<String> projects = getValueSet(configuration, PROJECTS);
        Set<String> excludeProjects = getValueSet(configuration, EXCLUDE_PROJECTS);
        List<String> recipients = getValueList(configuration, RECIPIENTS);

        return new ActivityStatWeeklyConfiguration(
                baseConfiguration,
                programs,
                excludePrograms,
                projects,
                excludeProjects,
                recipients);
    }

    private static void checkForParameterSyntaxErrors(
            Configuration configuration,
            Set<String> validParameters,
            String absolutePath) {

        Set<String> keys = configuration.getKeys();
        for (String key : keys) {
            if (!validParameters.contains(key))
                throw new DaemonConfigurationException("Illegal parameter [" + key + "] " +
                        "in configuration file [" + absolutePath + "].");
        }
    }

    private static void checkForMandatoryParameters(
            Configuration configuration,
            Set<String> mandatoryParameters,
            String absolutePath) {

        Set<String> keys = configuration.getKeys();
        for (String parameter : mandatoryParameters) {
            if (!keys.contains(parameter))
                throw new DaemonConfigurationException("Mandatory parameter [" + parameter + "] " +
                        "not found in configuration file [" + absolutePath + "].");
        }
    }

    private static Set<String> getValueSet(Configuration configuration, String parameter) {
        if (configuration.containsKey(parameter)) {
            List<String> valueList = configuration.getStringList(parameter);
            return Set.copyOf(valueList);
        } else {
            return new HashSet<>();
        }
    }

    @SuppressWarnings("SameParameterValue")
    private static List<String> getValueList(Configuration configuration, String parameter) {
        if (configuration.containsKey(parameter)) {
            return configuration.getStringList(parameter);
        } else {
            return new ArrayList<>();
        }
    }

}
