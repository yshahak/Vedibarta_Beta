package org.vedibarta.app.calendar;

import net.sourceforge.zmanim.hebrewcalendar.JewishCalendar;

import java.util.Date;

/**
 * Created by Yaakov Shahak on 28/05/2017.
 */

public class CalendarHelper {

    public static int getWeekParashaIndex(){
        JewishCalendar jewishCalendar = new JewishCalendar();
        int dayOfWeek = jewishCalendar.getDayOfWeek();
        Date now = jewishCalendar.getTime();
        now.setTime(now.getTime() + 1000*60*60*24 * (7 - dayOfWeek));
        jewishCalendar.setDate(now);
        return jewishCalendar.getParshaIndex();
    }
}
