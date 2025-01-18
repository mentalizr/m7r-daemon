//package org.mentalizr.daemon.jobs.activityStatWeekly;
//
//import de.arthurpicht.configuration.Configuration;
//import org.mentalizr.cli.commands.user.activity.stat.ActivityStatExecParameters;
//import org.mentalizr.cli.commands.user.activity.stat.FromUntilValues;
//
//import java.time.LocalDate;
//import java.util.Set;
//
//public class ActivityStatWeeklyCliOptions implements ActivityStatExecParameters {
//
//    public ActivityStatWeeklyCliOptions(Configuration configuration) {
//
//
//
//    }
//
//    @Override
//    public boolean hasOptionWeeksBack() {
//        return true;
//    }
//
//    @Override
//    public int getValueWeeksBack() {
//        return 1;
//    }
//
//    @Override
//    public boolean hasOptionCurrentWeek() {
//        return false;
//    }
//
//    @Override
//    public boolean hasOptionLastWeek() {
//        return false;
//    }
//
//    @Override
//    public boolean hasOptionDaysBack() {
//        return false;
//    }
//
//    @Override
//    public int getValueDaysBack() {
//        return 0;
//    }
//
//    @Override
//    public boolean hasOptionDate() {
//        return false;
//    }
//
//    @Override
//    public LocalDate getValueDate() {
//        return null;
//    }
//
//    @Override
//    public boolean hasOptionCurrently() {
//        return false;
//    }
//
//    @Override
//    public boolean hasOptionToday() {
//        return false;
//    }
//
//    @Override
//    public boolean hasOptionFromOrUntil() {
//        return false;
//    }
//
//    @Override
//    public FromUntilValues getFromUntilValues() {
//        return null;
//    }
//
//    @Override
//    public boolean isMail() {
//        return false;
//    }
//
//    @Override
//    public String getMailListFile() {
//        return "";
//    }
//
//    @Override
//    public boolean isMailerDryRun() {
//        return false;
//    }
//
//    @Override
//    public boolean hasProjects() {
//        return false;
//    }
//
//    @Override
//    public Set<String> getProjects() {
//        return Set.of();
//    }
//
//    @Override
//    public boolean hasExcludedProjects() {
//        return false;
//    }
//
//    @Override
//    public Set<String> getExcludedProjects() {
//        return Set.of();
//    }
//
//    @Override
//    public boolean hasPrograms() {
//        return false;
//    }
//
//    @Override
//    public Set<String> getPrograms() {
//        return Set.of();
//    }
//
//    @Override
//    public boolean hasExcludedPrograms() {
//        return false;
//    }
//
//    @Override
//    public Set<String> getExcludedProgram() {
//        return Set.of();
//    }
//}
