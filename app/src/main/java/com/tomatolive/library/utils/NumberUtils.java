package com.tomatolive.library.utils;

import android.text.TextUtils;
import com.tomatolive.library.TomatoLiveSDK;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;

/* loaded from: classes4.dex */
public class NumberUtils {
    public static int formatMillisecond(int i) {
        return i * 1000;
    }

    private NumberUtils() {
    }

    public static int string2int(String str) {
        return string2int(str, 0);
    }

    public static int dp2px(float f) {
        return (int) ((f * TomatoLiveSDK.getSingleton().getApplication().getResources().getDisplayMetrics().density) + 0.5f);
    }

    public static int string2int(String str, int i) {
        if (TextUtils.isEmpty(str)) {
            return i;
        }
        try {
            return Integer.parseInt(str);
        } catch (Exception unused) {
            return i;
        }
    }

    public static long string2long(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0L;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception unused) {
            return 0L;
        }
    }

    public static long string2long(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            return j;
        }
        try {
            return Long.parseLong(str);
        } catch (Exception unused) {
            return j;
        }
    }

    public static double string2Double(String str) {
        if (TextUtils.isEmpty(str)) {
            return 0.0d;
        }
        try {
            return Double.parseDouble(str);
        } catch (Exception unused) {
            return 0.0d;
        }
    }

    public static String formatDoubleStr(double d) {
        return new DecimalFormat("#.00").format(d);
    }

    public static int getRandom(int i, int i2) {
        int abs = Math.abs(i2 - i);
        if (i > i2) {
            i = i2;
        }
        return i + new Random(System.currentTimeMillis()).nextInt(abs);
    }

    public static int getIntRandom(int i) {
        return new Random().nextInt(i);
    }

    public static String doubleFormat(double d) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        return numberFormat.format(d);
    }

    public static String formatDoubleNumberToString(double d, String str, RoundingMode roundingMode) {
        if (TextUtils.isEmpty(str)) {
            str = "###0.00";
        }
        if (roundingMode == null) {
            roundingMode = RoundingMode.HALF_UP;
        }
        DecimalFormat decimalFormat = new DecimalFormat(str);
        decimalFormat.setGroupingUsed(false);
        decimalFormat.setRoundingMode(roundingMode);
        return decimalFormat.format(d);
    }

    public static String formatStarNum(long j, boolean z) {
        int i = (j > 10000L ? 1 : (j == 10000L ? 0 : -1));
        if (i < 0) {
            return String.valueOf(j);
        }
        if (j > 10000000) {
            double d = j / 1.0E7d;
            DecimalFormat decimalFormat = new DecimalFormat("###0.0");
            if (z) {
                decimalFormat = new DecimalFormat("###0.00");
            }
            decimalFormat.setGroupingUsed(false);
            decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
            return decimalFormat.format(d) + "千万";
        } else if (i < 0) {
            return "0";
        } else {
            double d2 = j / 10000.0d;
            DecimalFormat decimalFormat2 = new DecimalFormat("###0.0");
            if (z) {
                decimalFormat2 = new DecimalFormat("###0.00");
            }
            decimalFormat2.setGroupingUsed(false);
            decimalFormat2.setRoundingMode(RoundingMode.HALF_UP);
            return CovertFloat(decimalFormat2.format(d2)) + "万";
        }
    }

    public static String formatStarNum(long j, boolean z, boolean z2) {
        if (j < 10000) {
            NumberFormat numberFormat = NumberFormat.getInstance();
            numberFormat.setGroupingUsed(z2);
            if (j > 0 && z) {
                numberFormat.setMinimumFractionDigits(2);
            }
            return numberFormat.format(j);
        }
        return formatStarNum(j, z);
    }

    public static String CovertFloat(String str) {
        if (str.contains(".")) {
            String substring = str.substring(str.length() - 1);
            if (substring.equals("0")) {
                return CovertFloat(str.substring(0, str.length() - 1));
            }
            return substring.equals(".") ? str.substring(0, str.length() - 1) : str;
        }
        return str;
    }

    public static String getLimitLengthString(String str, int i, String str2) throws UnsupportedEncodingException {
        byte[] bytes = str.getBytes("GBK");
        if (bytes.length <= i) {
            return str;
        }
        int i2 = 0;
        for (int i3 = 0; i3 < i; i3++) {
            if (bytes[i3] < 0) {
                i2++;
            }
        }
        if (i2 % 2 == 0) {
            return new String(bytes, 0, i, "GBK") + str2;
        }
        return new String(bytes, 0, i - 1, "GBK") + str2;
    }

    public static String secondsFormat(long j) {
        if (j <= 0) {
            return "";
        }
        int ceil = (int) Math.ceil(((float) j) / 60.0f);
        if (ceil < 60) {
            return ceil + "分钟";
        }
        StringBuilder sb = new StringBuilder();
        int i = ceil / 60;
        int i2 = ceil % 60;
        if (i < 24) {
            sb.append(i);
            sb.append("小时");
            if (i2 > 0) {
                sb.append(i2);
                sb.append("分钟");
            }
            return sb.toString();
        }
        int i3 = i / 24;
        int i4 = i % 24;
        sb.append(i3);
        sb.append("天");
        if (i4 > 0) {
            sb.append(i4);
            sb.append("小时");
        }
        if (i2 > 0) {
            sb.append(i2);
            sb.append("分钟");
        }
        return sb.toString();
    }

    public static double mul(double d, double d2) {
        return new BigDecimal(Double.toString(d)).multiply(new BigDecimal(Double.toString(d2))).doubleValue();
    }

    public static double add(double d, double d2) {
        return new BigDecimal(Double.toString(d)).add(new BigDecimal(Double.toString(d2))).doubleValue();
    }

    public static double sub(double d, double d2) {
        return new BigDecimal(Double.toString(d)).subtract(new BigDecimal(Double.toString(d2))).doubleValue();
    }

    public static String formatThreeNumStr(String str) {
        return formatThreeNumStr(string2long(str));
    }

    public static String formatThreeNumStr(long j) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);
        return numberFormat.format(j);
    }

    public static String formatThreeNumStr(double d) {
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(true);
        return numberFormat.format(d);
    }

    public static long calculateLength(CharSequence charSequence) {
        double d = 0.0d;
        for (int i = 0; i < charSequence.length(); i++) {
            char charAt = charSequence.charAt(i);
            d += (charAt <= 0 || charAt >= 127) ? 1.0d : 0.5d;
        }
        return Math.round(d);
    }
}
