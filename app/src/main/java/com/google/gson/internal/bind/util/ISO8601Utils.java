package com.google.gson.internal.bind.util;

import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

/* loaded from: classes3.dex */
public class ISO8601Utils {
    private static final String UTC_ID = "UTC";
    private static final TimeZone TIMEZONE_UTC = TimeZone.getTimeZone(UTC_ID);

    public static String format(Date date) {
        return format(date, false, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z) {
        return format(date, z, TIMEZONE_UTC);
    }

    public static String format(Date date, boolean z, TimeZone timeZone) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar(timeZone, Locale.US);
        gregorianCalendar.setTime(date);
        StringBuilder sb = new StringBuilder(19 + (z ? 4 : 0) + (timeZone.getRawOffset() == 0 ? 1 : 6));
        padInt(sb, gregorianCalendar.get(1), 4);
        char c = '-';
        sb.append('-');
        padInt(sb, gregorianCalendar.get(2) + 1, 2);
        sb.append('-');
        padInt(sb, gregorianCalendar.get(5), 2);
        sb.append('T');
        padInt(sb, gregorianCalendar.get(11), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(12), 2);
        sb.append(':');
        padInt(sb, gregorianCalendar.get(13), 2);
        if (z) {
            sb.append('.');
            padInt(sb, gregorianCalendar.get(14), 3);
        }
        int offset = timeZone.getOffset(gregorianCalendar.getTimeInMillis());
        if (offset != 0) {
            int i = offset / 60000;
            int abs = Math.abs(i / 60);
            int abs2 = Math.abs(i % 60);
            if (offset >= 0) {
                c = '+';
            }
            sb.append(c);
            padInt(sb, abs, 2);
            sb.append(':');
            padInt(sb, abs2, 2);
        } else {
            sb.append('Z');
        }
        return sb.toString();
    }

    /* JADX WARN: Removed duplicated region for block: B:44:0x00ca A[Catch: IllegalArgumentException -> 0x01c2, NumberFormatException -> 0x01c4, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c6, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c6, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:18:0x0050, B:20:0x0060, B:21:0x0062, B:23:0x006e, B:24:0x0070, B:26:0x0076, B:30:0x0080, B:35:0x0090, B:37:0x0098, B:42:0x00c4, B:44:0x00ca, B:46:0x00d1, B:47:0x0188, B:52:0x00db, B:53:0x00f8, B:54:0x00f9, B:57:0x0116, B:59:0x0124, B:62:0x012e, B:64:0x014e, B:67:0x015f, B:68:0x0183, B:70:0x0186, B:71:0x0104, B:72:0x01b9, B:73:0x01c1, B:74:0x00b2, B:75:0x00b5), top: B:2:0x0004 }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01b9 A[Catch: IllegalArgumentException -> 0x01c2, NumberFormatException -> 0x01c4, NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c6, TryCatch #2 {NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException -> 0x01c6, blocks: (B:3:0x0004, B:5:0x0016, B:6:0x0018, B:8:0x0024, B:9:0x0026, B:11:0x0035, B:13:0x003b, B:18:0x0050, B:20:0x0060, B:21:0x0062, B:23:0x006e, B:24:0x0070, B:26:0x0076, B:30:0x0080, B:35:0x0090, B:37:0x0098, B:42:0x00c4, B:44:0x00ca, B:46:0x00d1, B:47:0x0188, B:52:0x00db, B:53:0x00f8, B:54:0x00f9, B:57:0x0116, B:59:0x0124, B:62:0x012e, B:64:0x014e, B:67:0x015f, B:68:0x0183, B:70:0x0186, B:71:0x0104, B:72:0x01b9, B:73:0x01c1, B:74:0x00b2, B:75:0x00b5), top: B:2:0x0004 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Date parse(String str, ParsePosition parsePosition) throws ParseException {
        String str2;
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        int length;
        TimeZone timeZone;
        char charAt;
        try {
            int index = parsePosition.getIndex();
            int i6 = index + 4;
            int parseInt = parseInt(str, index, i6);
            if (checkOffset(str, i6, '-')) {
                i6++;
            }
            int i7 = i6 + 2;
            int parseInt2 = parseInt(str, i6, i7);
            if (checkOffset(str, i7, '-')) {
                i7++;
            }
            int i8 = i7 + 2;
            int parseInt3 = parseInt(str, i7, i8);
            boolean checkOffset = checkOffset(str, i8, 'T');
            if (!checkOffset && str.length() <= i8) {
                GregorianCalendar gregorianCalendar = new GregorianCalendar(parseInt, parseInt2 - 1, parseInt3);
                parsePosition.setIndex(i8);
                return gregorianCalendar.getTime();
            }
            if (checkOffset) {
                int i9 = i8 + 1;
                int i10 = i9 + 2;
                i2 = parseInt(str, i9, i10);
                if (checkOffset(str, i10, ':')) {
                    i10++;
                }
                i = i10 + 2;
                i3 = parseInt(str, i10, i);
                if (checkOffset(str, i, ':')) {
                    i++;
                }
                if (str.length() > i && (charAt = str.charAt(i)) != 'Z' && charAt != '+' && charAt != '-') {
                    int i11 = i + 2;
                    int parseInt4 = parseInt(str, i, i11);
                    if (parseInt4 > 59 && parseInt4 < 63) {
                        parseInt4 = 59;
                    }
                    if (checkOffset(str, i11, '.')) {
                        int i12 = i11 + 1;
                        int indexOfNonDigit = indexOfNonDigit(str, i12 + 1);
                        int min = Math.min(indexOfNonDigit, i12 + 3);
                        int parseInt5 = parseInt(str, i12, min);
                        int i13 = min - i12;
                        if (i13 == 1) {
                            parseInt5 *= 100;
                        } else if (i13 == 2) {
                            parseInt5 *= 10;
                        }
                        i5 = parseInt5;
                        i4 = parseInt4;
                        i = indexOfNonDigit;
                        if (str.length() <= i) {
                            throw new IllegalArgumentException("No time zone indicator");
                        }
                        char charAt2 = str.charAt(i);
                        if (charAt2 == 'Z') {
                            timeZone = TIMEZONE_UTC;
                            length = i + 1;
                        } else {
                            if (charAt2 != '+' && charAt2 != '-') {
                                throw new IndexOutOfBoundsException("Invalid time zone indicator '" + charAt2 + "'");
                            }
                            String substring = str.substring(i);
                            if (substring.length() < 5) {
                                substring = substring + "00";
                            }
                            length = i + substring.length();
                            if (!"+0000".equals(substring) && !"+00:00".equals(substring)) {
                                String str3 = "GMT" + substring;
                                TimeZone timeZone2 = TimeZone.getTimeZone(str3);
                                String id = timeZone2.getID();
                                if (!id.equals(str3) && !id.replace(":", "").equals(str3)) {
                                    throw new IndexOutOfBoundsException("Mismatching time zone indicator: " + str3 + " given, resolves to " + timeZone2.getID());
                                }
                                timeZone = timeZone2;
                            }
                            timeZone = TIMEZONE_UTC;
                        }
                        GregorianCalendar gregorianCalendar2 = new GregorianCalendar(timeZone);
                        gregorianCalendar2.setLenient(false);
                        gregorianCalendar2.set(1, parseInt);
                        gregorianCalendar2.set(2, parseInt2 - 1);
                        gregorianCalendar2.set(5, parseInt3);
                        gregorianCalendar2.set(11, i2);
                        gregorianCalendar2.set(12, i3);
                        gregorianCalendar2.set(13, i4);
                        gregorianCalendar2.set(14, i5);
                        parsePosition.setIndex(length);
                        return gregorianCalendar2.getTime();
                    }
                    i4 = parseInt4;
                    i = i11;
                    i5 = 0;
                    if (str.length() <= i) {
                    }
                }
            } else {
                i = i8;
                i2 = 0;
                i3 = 0;
            }
            i4 = 0;
            i5 = 0;
            if (str.length() <= i) {
            }
        } catch (NumberFormatException | IllegalArgumentException | IndexOutOfBoundsException e) {
            if (str == null) {
                str2 = null;
            } else {
                str2 = '\"' + str + '\"';
            }
            String message = e.getMessage();
            if (message == null || message.isEmpty()) {
                message = "(" + e.getClass().getName() + ")";
            }
            ParseException parseException = new ParseException("Failed to parse date [" + str2 + "]: " + message, parsePosition.getIndex());
            parseException.initCause(e);
            throw parseException;
        }
    }

    private static boolean checkOffset(String str, int i, char c) {
        return i < str.length() && str.charAt(i) == c;
    }

    private static int parseInt(String str, int i, int i2) throws NumberFormatException {
        int i3;
        int i4;
        if (i < 0 || i2 > str.length() || i > i2) {
            throw new NumberFormatException(str);
        }
        if (i < i2) {
            i3 = i + 1;
            int digit = Character.digit(str.charAt(i), 10);
            if (digit < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i4 = -digit;
        } else {
            i3 = i;
            i4 = 0;
        }
        while (i3 < i2) {
            int i5 = i3 + 1;
            int digit2 = Character.digit(str.charAt(i3), 10);
            if (digit2 < 0) {
                throw new NumberFormatException("Invalid number: " + str.substring(i, i2));
            }
            i4 = (i4 * 10) - digit2;
            i3 = i5;
        }
        return -i4;
    }

    private static void padInt(StringBuilder sb, int i, int i2) {
        String num = Integer.toString(i);
        for (int length = i2 - num.length(); length > 0; length--) {
            sb.append('0');
        }
        sb.append(num);
    }

    private static int indexOfNonDigit(String str, int i) {
        while (i < str.length()) {
            char charAt = str.charAt(i);
            if (charAt < '0' || charAt > '9') {
                return i;
            }
            i++;
        }
        return str.length();
    }
}
