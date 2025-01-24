package org.mentalizr.daemon;

import de.arthurpicht.utils.core.dates.ISODates;
import org.quartz.CronExpression;

import java.text.ParseException;
import java.time.Instant;
import java.util.Date;

public class Dummy {

    public int dudu() {
        return 0;
    }

    public static void main(String[] args) throws ParseException {
        System.out.println("Hello world!");
        CronExpression cronExpression = new CronExpression("0 0 0 * * ?");
        System.out.println("Expression Summary: " + cronExpression.getExpressionSummary());
        Date date = cronExpression.getNextValidTimeAfter(Date.from(Instant.now()));
        System.out.println(date);
    }

}
