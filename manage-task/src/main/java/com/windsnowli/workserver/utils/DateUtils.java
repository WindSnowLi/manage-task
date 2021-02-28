package com.windsnowli.workserver.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.SimpleTimeZone;

/**
 * @author windSnowLi
 */
public class DateUtils {
    /**
     * 时间增减小时
     * @param nowDate 基准时间
     * @param hour 改变量
     * @return String yyyy-MM-dd-HH时间
     */
    public static String calculationDate(Date nowDate, int hour) {
        String oneHoursAgoTime;
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(new SimpleTimeZone(0, "GMT"));
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(nowDate);
        rightNow.add(Calendar.HOUR, hour);
        Date dt1 = rightNow.getTime();
        oneHoursAgoTime = sdf.format(dt1);
        return oneHoursAgoTime;
    }
}
