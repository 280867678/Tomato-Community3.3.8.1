package com.one.tomato.utils;

import android.text.TextUtils;
import com.broccoli.p150bh.R;
import com.tomatolive.library.utils.DateUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes3.dex */
public class FormatUtil {
    private static SimpleDateFormat dateFormat = new SimpleDateFormat();

    public static String formatTwo(Double d) {
        return new DecimalFormat("##0.00").format(Double.valueOf(new BigDecimal(d + "").setScale(2, 4).doubleValue()));
    }

    public static String formatTwo(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0";
        }
        return new DecimalFormat("##0.00").format(Double.valueOf(new BigDecimal(str).setScale(2, 4).doubleValue()));
    }

    public static String formatOne(Double d) {
        return new DecimalFormat("##0.0").format(Double.valueOf(new BigDecimal(d + "").setScale(1, 4).doubleValue()));
    }

    public static String formatZero(Double d) {
        return new DecimalFormat("##0").format(Double.valueOf(new BigDecimal(d + "").setScale(1, 4).doubleValue()));
    }

    public static String formatZero(String str) {
        if (TextUtils.isEmpty(str)) {
            return "0";
        }
        return new DecimalFormat("##0").format(Double.valueOf(new BigDecimal(str).setScale(1, 4).doubleValue()));
    }

    public static String formatTomato2RMB(String str) {
        try {
            return formatTomato2RMB(Double.parseDouble(str));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatTomato2RMB(double d) {
        String formatTwo;
        double d2 = d / 10.0d;
        try {
            if (d2 > 10000.0d) {
                double d3 = d2 / 10000.0d;
                formatTwo = formatTwo(Double.valueOf(d3)) + "w";
            } else {
                formatTwo = formatTwo(Double.valueOf(d2));
            }
            return formatTwo;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String formatNumOverTenThousand(String str) {
        double parseDouble;
        if (TextUtils.isEmpty(str)) {
            return "0";
        }
        try {
            if (Double.parseDouble(str) > 10000.0d) {
                str = formatOne(Double.valueOf(parseDouble / 10000.0d)) + "w";
            }
            return str;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return "0";
        }
    }

    public static String formatNumOverTenThousand(long j) {
        if (j > 10000) {
            return (j / 10000) + "w";
        }
        return String.valueOf(j);
    }

    public static String formatTime(String str, Date date) {
        dateFormat.applyPattern(str);
        return dateFormat.format(date);
    }

    public static String formatTime(String str, String str2) {
        try {
            dateFormat.applyPattern(str);
            return formatTime(str, formatTimeToDate(str, str2));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static Date formatTimeToDate(String str, String str2) {
        try {
            dateFormat.applyPattern(str);
            return dateFormat.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static String formatTime(Date date, Date date2) {
        long time = date2.getTime() - date.getTime();
        long j = time / DateUtils.ONE_DAY_MILLIONS;
        if (j > 120) {
            return formatTime("yyyy-MM-dd", date);
        }
        if (j > 90) {
            return AppUtil.getString(R.string.format_three_month_ago);
        }
        if (j > 60) {
            return AppUtil.getString(R.string.format_two_month_ago);
        }
        if (j > 30) {
            return AppUtil.getString(R.string.format_one_month_ago);
        }
        if (j > 0) {
            return j + AppUtil.getString(R.string.format_some_days_ago);
        }
        long j2 = time % DateUtils.ONE_DAY_MILLIONS;
        long j3 = j2 / DateUtils.ONE_HOUR_MILLIONS;
        if (j3 > 0) {
            return j3 + AppUtil.getString(R.string.format_some_hours_ago);
        }
        long j4 = (j2 % DateUtils.ONE_HOUR_MILLIONS) / DateUtils.ONE_MINUTE_MILLIONS;
        if (j4 < 5) {
            return AppUtil.getString(R.string.format_recent);
        }
        if (j4 > 0) {
            return j4 + AppUtil.getString(R.string.format_some_minutes_ago);
        }
        return j + AppUtil.getString(R.string.format_day) + j3 + AppUtil.getString(R.string.format_hour) + j4 + AppUtil.getString(R.string.format_minute);
    }
}
