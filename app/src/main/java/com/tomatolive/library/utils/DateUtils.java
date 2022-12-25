package com.tomatolive.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.format.DateFormat;
import com.blankj.utilcode.util.TimeUtils;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.dialog.LotteryDialog;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Formatter;
import java.util.Locale;

/* loaded from: classes4.dex */
public class DateUtils {
    public static final String C_DATE_PATTON_DATE_CHINA_1 = "yyyy年MM月";
    public static final String C_DATE_PATTON_DATE_CHINA_2 = "yyyy年MM月dd日";
    public static final String C_DATE_PATTON_DATE_CHINA_3 = "yyyy年MM月dd日 HH:mm";
    public static final String C_DATE_PATTON_DATE_CHINA_4 = "dd";
    public static final String C_DATE_PATTON_DATE_CHINA_5 = "mm:ss";
    public static final String C_DATE_PATTON_DATE_CHINA_6 = "MM月dd日";
    public static final String C_DATE_PATTON_DATE_CHINA_7 = "MM月dd日 HH:mm";
    public static final String C_DATE_PATTON_DATE_CHINA_8 = "MM-dd HH:mm:ss";
    public static final String C_DATE_PATTON_DEFAULT = "yyyy-MM-dd";
    public static final String C_DATE_PATTON_DEFAULT_1 = "yyyy.MM.dd";
    public static final String C_TIME_PATTON_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String C_TIME_PATTON_DEFAULT_2 = "yyyy-MM-dd HH:mm";
    private static String DATE_FORMAT = "yyyy-MM-dd";
    private static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final long ONE_DAY_MILLIONS = 86400000;
    public static final long ONE_HOUR_MILLIONS = 3600000;
    public static final long ONE_MINUTE_MILLIONS = 60000;
    private static StringBuilder mFormatBuilder = new StringBuilder();
    private static Formatter mFormatter = new Formatter(mFormatBuilder, Locale.getDefault());

    public static String stringForTime(long j) {
        long j2 = j / 1000;
        long j3 = j2 % 60;
        long j4 = (j2 / 60) % 60;
        long j5 = j2 / 3600;
        mFormatBuilder.setLength(0);
        return j5 > 0 ? mFormatter.format("%d:%02d:%02d", Long.valueOf(j5), Long.valueOf(j4), Long.valueOf(j3)).toString() : mFormatter.format("%02d:%02d", Long.valueOf(j4), Long.valueOf(j3)).toString();
    }

    public static String millisecond2TimeMinute(long j) {
        long j2 = j / 1000;
        long j3 = j2 % 60;
        long j4 = (j2 / 60) % 60;
        long j5 = j2 / 3600;
        mFormatBuilder.setLength(0);
        return j5 > 0 ? mFormatter.format("%d'%02d'%02d'", Long.valueOf(j5), Long.valueOf(j4), Long.valueOf(j3)).toString() : mFormatter.format("%02d'%02d'", Long.valueOf(j4), Long.valueOf(j3)).toString();
    }

    public static String secondToMinutesString(long j) {
        mFormatBuilder.setLength(0);
        return mFormatter.format("%02d:%02d", Long.valueOf(j / 60), Long.valueOf(j % 60)).toString();
    }

    public static String secondToString(long j) {
        long j2 = j % 60;
        long j3 = (j / 60) % 60;
        long j4 = j / 3600;
        mFormatBuilder.setLength(0);
        return j4 > 0 ? mFormatter.format("%d:%02d:%02d", Long.valueOf(j4), Long.valueOf(j3), Long.valueOf(j2)).toString() : mFormatter.format("%02d:%02d", Long.valueOf(j3), Long.valueOf(j2)).toString();
    }

    public static String getStartLiveTime(String str) {
        long string2long = NumberUtils.string2long(str) * 1000;
        if (string2long == 0) {
            return "";
        }
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date(string2long));
    }

    public static String getCurrentDate() {
        return new SimpleDateFormat(DATE_FORMAT).format(new Date());
    }

    public static String getCurrentDateTime() {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(new Date());
    }

    public static String getCurrentDateTime(String str) {
        return new SimpleDateFormat(str).format(new Date());
    }

    public static String dateToDateTime(Date date) {
        return new SimpleDateFormat(DATE_TIME_FORMAT).format(date);
    }

    public static String dateToString(Date date, String str) {
        return new SimpleDateFormat(str).format(date);
    }

    public static String dateToString(String str, String str2) {
        return new SimpleDateFormat(str2).format(stringToDate(str));
    }

    public static int getWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(7) - 1;
    }

    public static Date getLastDayOfMonth(Date date) {
        return addDay(getFirstDayOfMonth(addMonth(date, 1)), -1);
    }

    public static Date addDay(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(5, i);
        return calendar.getTime();
    }

    public static Date getFirstDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(5, 1);
        return calendar.getTime();
    }

    public static Date addMonth(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, i);
        return calendar.getTime();
    }

    public static boolean isLeapYEAR(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(1);
        return (i % 4 == 0 && i % 100 != 0) || i % LotteryDialog.MAX_VALUE == 0;
    }

    public static Date getDateByYMD(int i, int i2, int i3) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(i, i2 - 1, i3);
        return calendar.getTime();
    }

    public static Date getYearCycleOfDate(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, i);
        return calendar.getTime();
    }

    public static Date getMonthCycleOfDate(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(2, i);
        return calendar.getTime();
    }

    public static int getYearByMinusDate(Date date, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return calendar2.get(1) - calendar.get(1);
    }

    public static int getMonthByMinusDate(Date date, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return ((calendar2.get(1) * 12) + calendar2.get(2)) - ((calendar.get(1) * 12) + calendar.get(2));
    }

    public static long getDayByMinusDate(Object obj, Object obj2) {
        Date chgObject = chgObject(obj);
        Date chgObject2 = chgObject(obj2);
        return (chgObject2.getTime() - chgObject.getTime()) / ONE_DAY_MILLIONS;
    }

    public static Date chgObject(Object obj) {
        if (obj instanceof Date) {
            return (Date) obj;
        }
        if (obj instanceof String) {
            return stringToDate((String) obj);
        }
        return new Date();
    }

    public static Date stringToDate(String str) {
        if (str == null || str.equals("")) {
            return null;
        }
        try {
            return new SimpleDateFormat(DATE_FORMAT).parse(str);
        } catch (ParseException unused) {
            return stringToDate(str, "yyyyMMdd");
        }
    }

    public static Date stringToDate(String str, String str2) {
        Date date = new Date();
        try {
            return new SimpleDateFormat(str2).parse(str);
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

    public static int calcAge(Date date, Date date2) {
        int yearOfDate = getYearOfDate(date2);
        int monthOfDate = getMonthOfDate(date2);
        int dayOfDate = getDayOfDate(date2);
        int yearOfDate2 = getYearOfDate(date);
        int monthOfDate2 = getMonthOfDate(date);
        return (monthOfDate > monthOfDate2 || (monthOfDate == monthOfDate2 && dayOfDate > getDayOfDate(date))) ? yearOfDate - yearOfDate2 : (yearOfDate - 1) - yearOfDate2;
    }

    public static int getYearOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(1);
    }

    public static int getMonthOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(2) + 1;
    }

    public static int getDayOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(5);
    }

    public static String getBirthDayFromIDCard(String str) {
        Calendar calendar = Calendar.getInstance();
        if (str.length() == 15) {
            calendar.set(1, Integer.valueOf("19" + str.substring(6, 8)).intValue());
            calendar.set(2, Integer.valueOf(str.substring(8, 10)).intValue() - 1);
            calendar.set(5, Integer.valueOf(str.substring(10, 12)).intValue());
        } else if (str.length() == 18) {
            calendar.set(1, Integer.valueOf(str.substring(6, 10)).intValue());
            calendar.set(2, Integer.valueOf(str.substring(10, 12)).intValue() - 1);
            calendar.set(5, Integer.valueOf(str.substring(12, 14)).intValue());
        }
        return dateToString(calendar.getTime());
    }

    public static String dateToString(Date date) {
        return new SimpleDateFormat(DATE_FORMAT).format(date);
    }

    public static Date addYear(Date date, int i) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(1, i);
        return calendar.getTime();
    }

    public static long getAgeByBirthday(String str) {
        return ((new Date().getTime() - stringToDate(str, "yyyy-MM-dd").getTime()) / ONE_DAY_MILLIONS) / 365;
    }

    public static String getClearTime(String str) {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_TIME_FORMAT);
        calendar.add(13, NumberUtils.string2int(str));
        return simpleDateFormat.format(calendar.getTime());
    }

    public static String getShortTime(long j) {
        if (j == 0) {
            return "";
        }
        Date date = new Date(j);
        Date date2 = new Date();
        long time = date2.getTime() - date.getTime();
        int calculateDayStatus = calculateDayStatus(date, new Date());
        if (time <= 600000) {
            return SystemUtils.getResString(R$string.fq_just_now);
        }
        if (time < ONE_HOUR_MILLIONS) {
            return (time / ONE_MINUTE_MILLIONS) + SystemUtils.getResString(R$string.fq_minute_ago);
        } else if (calculateDayStatus == 0) {
            return (time / ONE_HOUR_MILLIONS) + SystemUtils.getResString(R$string.fq_hour_ago);
        } else if (calculateDayStatus == -1) {
            return (time / ONE_HOUR_MILLIONS) + SystemUtils.getResString(R$string.fq_hour_ago);
        } else if (isSameYear(date, date2) && calculateDayStatus < -1) {
            return (time / ONE_DAY_MILLIONS) + SystemUtils.getResString(R$string.fq_day_ago);
        } else {
            return DateFormat.format("yyyy-MM-dd", date).toString();
        }
    }

    public static String getLaveMinuteTimeSpan(long j) {
        String str;
        long j2 = j / 86400;
        long j3 = (j % 86400) / 3600;
        long j4 = (j % 3600) / 60;
        long j5 = j % 60;
        if (j2 > 0) {
            str = j2 + "天";
        } else if (j3 > 0) {
            str = j3 + "小时";
        } else if (j4 > 0) {
            str = j4 + "分钟";
        } else {
            str = j5 + "秒";
        }
        return "剩余" + str;
    }

    public static boolean isSameYear(Date date, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(1);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return i == calendar2.get(1);
    }

    public static int calculateDayStatus(Date date, Date date2) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int i = calendar.get(6);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        return i - calendar2.get(6);
    }

    public static Date formatStr2Date(String str) {
        return formatStr2Date(str, null);
    }

    public static Date formatStr2Date(String str, String str2) {
        if (str == null) {
            return null;
        }
        if (str2 == null || str2.equals("")) {
            str2 = C_TIME_PATTON_DEFAULT;
        }
        return getSimpleDateFormat(str2).parse(str, new ParsePosition(0));
    }

    public static SimpleDateFormat getSimpleDateFormat() {
        return getSimpleDateFormat(null);
    }

    @SuppressLint({"SimpleDateFormat"})
    public static SimpleDateFormat getSimpleDateFormat(String str) {
        if (str == null) {
            return new SimpleDateFormat(C_TIME_PATTON_DEFAULT);
        }
        return new SimpleDateFormat(str);
    }

    public static boolean isEffectiveDate(Date date, Date date2, Date date3) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(date2);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.setTime(date3);
        return calendar.after(calendar2) && calendar.before(calendar3);
    }

    public static Calendar getCalendarFormat(String str) {
        Date formatStr2Date = formatStr2Date(str, "yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(formatStr2Date);
        return calendar;
    }

    public static boolean isYesterday(long j) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            Date parse = simpleDateFormat.parse(simpleDateFormat.format(new Date()));
            if (j >= parse.getTime()) {
                return false;
            }
            return j > parse.getTime() - ONE_DAY_MILLIONS;
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static String getFriendlyTimeSpanByNow(Context context, long j) {
        if (context == null) {
            return "";
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(C_DATE_PATTON_DATE_CHINA_7);
        Calendar calendar = Calendar.getInstance();
        calendar.set(11, 0);
        calendar.set(13, 0);
        calendar.set(12, 0);
        calendar.set(14, 0);
        long timeInMillis = calendar.getTimeInMillis();
        String[] stringArray = context.getResources().getStringArray(R$array.fq_date_time_format_tips);
        if (j >= timeInMillis) {
            return String.format(stringArray[0], Long.valueOf(j));
        }
        if (j >= timeInMillis - ONE_DAY_MILLIONS) {
            return String.format(stringArray[1], Long.valueOf(j));
        }
        int i = (j > (timeInMillis - 172800000) ? 1 : (j == (timeInMillis - 172800000) ? 0 : -1));
        return i >= 0 ? String.format(stringArray[2], Long.valueOf(j)) : (i >= 0 || j < timeInMillis - 1471228928) ? String.format(stringArray[3], Long.valueOf(j)) : simpleDateFormat.format(new Date(j));
    }

    public static String formatSecondToDateFormat(long j, String str) {
        return formatMillisecondToDateFormat(j * 1000, str);
    }

    public static String formatSecondToDateFormat(String str, String str2) {
        return formatSecondToDateFormat(NumberUtils.string2long(str), str2);
    }

    public static String formatMillisecondToDateFormat(long j, String str) {
        return TimeUtils.millis2String(j, new SimpleDateFormat(str));
    }

    public static String formatMillisecondToDateFormat(String str, String str2) {
        return formatMillisecondToDateFormat(NumberUtils.string2long(str), str2);
    }

    public static String getTimeStrFromLongSecond(String str, String str2) {
        return formatSecondToDateFormat(str, str2);
    }
}
