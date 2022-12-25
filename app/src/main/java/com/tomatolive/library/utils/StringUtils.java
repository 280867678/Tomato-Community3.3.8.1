package com.tomatolive.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.p002v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.format.Formatter;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.slf4j.Marker;

/* loaded from: classes4.dex */
public class StringUtils {
    private static final String COMMA_STR = ",";
    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};

    private StringUtils() {
    }

    public static String getRandomString(int i) {
        Random random = new Random(System.currentTimeMillis());
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < i; i2++) {
            sb.append("abcdefghigklmnopkrstuvwxyzABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789".charAt(random.nextInt(62)));
        }
        return sb.toString();
    }

    public static String makeGUID() {
        return UUID.randomUUID().toString();
    }

    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    public static String[] split(String str, String str2) {
        int i = 0;
        if (str == null || str2 == null || str.trim().equals("") || str2.trim().equals("")) {
            return new String[]{str};
        }
        ArrayList arrayList = new ArrayList();
        int length = str2.length();
        while (true) {
            if (i < str.length()) {
                int indexOf = str.indexOf(str2, i);
                if (indexOf < 0) {
                    arrayList.add(str.substring(i));
                    break;
                }
                arrayList.add(str.substring(i, indexOf));
                i = indexOf + length;
            } else if (i == str.length()) {
                arrayList.add("");
            }
        }
        String[] strArr = new String[arrayList.size()];
        arrayList.toArray(strArr);
        return strArr;
    }

    public static String modifyUrl(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (charAt == '\\') {
                charAt = '/';
            }
            if (charAt > 256 || charAt == ' ' || charAt == '[' || charAt == ']' || charAt == '.' || charAt == '(' || charAt == ')') {
                sb.append(UrlEncoderUtils.encode("" + charAt, "UTF-8"));
            } else {
                sb.append(charAt);
            }
        }
        return sb.toString();
    }

    public static String errEncode(String str) {
        if (!TextUtils.isEmpty(str) && !Pattern.compile("[\\u4e00-\\u9fa5\\u0800-\\u4e00]+").matcher(str).find()) {
            try {
                return new String(str.getBytes("iso-8859-1"), "GBK");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return str;
            }
        }
        return str;
    }

    public static boolean isErrCode(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        return !Pattern.compile("[\\u4e00-\\u9fa5]+").matcher(str).find();
    }

    public static String add0IfLgTen(int i) {
        if (i > 0 && i < 10) {
            return "0" + i + ".";
        }
        return i + ".";
    }

    @SuppressLint({"DefaultLocale"})
    public static String getSizeText(long j) {
        if (j <= 0) {
            return "0.0M";
        }
        if (j < 102400) {
            return "0.1M";
        }
        return String.format("%.1f", Float.valueOf((((float) j) / 1024.0f) / 1024.0f)) + "M";
    }

    public static String getSizeText(Context context, long j) {
        return j < 0 ? "" : Formatter.formatFileSize(context, j);
    }

    public static String spiltImageName(String str) {
        int i;
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        int lastIndexOf = lowerCase.lastIndexOf("filename");
        if (lastIndexOf == -1) {
            int lastIndexOf2 = lowerCase.lastIndexOf("/");
            if (lastIndexOf2 == -1) {
                return null;
            }
            i = lastIndexOf2 + 1;
        } else {
            i = lastIndexOf + 9;
        }
        int indexOf = lowerCase.indexOf(".jpg", i);
        if (indexOf != -1 || (indexOf = lowerCase.indexOf(".png", i)) != -1) {
            return lowerCase.substring(i, indexOf + 4);
        }
        return null;
    }

    public static String hashImageName(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        String lowerCase2 = str2.toLowerCase();
        int indexOf = lowerCase.indexOf(".jpg");
        if (indexOf == -1) {
            indexOf = lowerCase.indexOf(".png");
        }
        return lowerCase2 + lowerCase.substring(indexOf);
    }

    public static String getExceptionString(Exception exc) {
        StringWriter stringWriter = new StringWriter();
        exc.printStackTrace(new PrintWriter(stringWriter));
        return stringWriter.toString().replace("\n", "<br />");
    }

    public static String imeiToBigInteger(String str) {
        BigInteger bigInteger = new BigInteger("0");
        try {
            BigInteger bigInteger2 = new BigInteger("16");
            String md5 = MD5Utils.getMd5(str);
            int length = md5.length();
            for (int i = 0; i < length; i++) {
                bigInteger = bigInteger.add(new BigInteger("" + md5.charAt(i), 16).multiply(bigInteger2.pow((length - 1) - i)));
            }
            return bigInteger.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return bigInteger.toString();
        }
    }

    public static BigInteger imeiTolong(String str) {
        BigInteger bigInteger = new BigInteger("0");
        try {
            BigInteger bigInteger2 = new BigInteger("16");
            String md5 = MD5Utils.getMd5(str);
            int length = md5.length();
            for (int i = 0; i < length; i++) {
                bigInteger = bigInteger.add(new BigInteger("" + md5.charAt(i), 16).multiply(bigInteger2.pow((length - 1) - i)));
            }
            return bigInteger;
        } catch (Exception e) {
            e.printStackTrace();
            return bigInteger;
        }
    }

    public static int countWords(String str) {
        try {
            if (TextUtils.isEmpty(str)) {
                return 0;
            }
            return str.getBytes("GBK").length;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static boolean versionOver(String str, String str2) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2)) {
            return false;
        }
        if (str.startsWith("v")) {
            str = str.substring(1);
        }
        if (str2.startsWith("v")) {
            str2 = str2.substring(1);
        }
        String[] split = str.split("\\.");
        String[] split2 = str2.split("\\.");
        int length = split.length;
        if (length >= split2.length) {
            length = split2.length;
        }
        for (int i = 0; i < length; i++) {
            try {
                int parseInt = Integer.parseInt(split[i]);
                int parseInt2 = Integer.parseInt(split2[i]);
                if (parseInt < parseInt2) {
                    return false;
                }
                if (parseInt > parseInt2) {
                    return true;
                }
            } catch (NumberFormatException unused) {
                return false;
            }
        }
        return split.length >= split2.length;
    }

    public static String formatTime(DateFormat dateFormat, long j) {
        if (String.valueOf(j).length() < 13) {
            j *= 1000;
        }
        return dateFormat.format(new Date(j));
    }

    public static ArrayList<String> splitString(String str, String str2) {
        String[] split;
        if (TextUtils.isEmpty(str) || (split = split(str, str2)) == null || split.length == 0) {
            return null;
        }
        ArrayList<String> arrayList = new ArrayList<>();
        Collections.addAll(arrayList, split);
        return arrayList;
    }

    public static String mergeString(List<String> list, String str) {
        StringBuilder sb = new StringBuilder();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i));
            if (i < size - 1) {
                sb.append(str);
            }
        }
        return sb.toString();
    }

    public static String bytesToHex(byte[] bArr) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString();
    }

    public static boolean isEmpty(CharSequence charSequence) {
        return charSequence == null || charSequence.length() == 0;
    }

    public static String formatFilePath(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return str.replace("\\", "").replace("/", "").replace(Marker.ANY_MARKER, "").replace("?", "").replace(":", "").replace("\"", "").replace(SimpleComparison.LESS_THAN_OPERATION, "").replace(SimpleComparison.GREATER_THAN_OPERATION, "").replace("|", "");
    }

    public static String hashKeyForDisk(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        String md5 = MD5Utils.getMd5(str);
        return md5 == null ? String.valueOf(str.hashCode()) : md5;
    }

    public static String getFoldStringByLength(String str, int i) {
        if (TextUtils.isEmpty(str) || i == 0) {
            return "";
        }
        if (i >= str.length()) {
            return str;
        }
        return str.substring(0, i) + "…";
    }

    public static double getLength(String str) {
        double d = 0.0d;
        if (TextUtils.isEmpty(str)) {
            return 0.0d;
        }
        int i = 0;
        while (i < str.length()) {
            int i2 = i + 1;
            d += str.substring(i, i2).matches("[一-龥]") ? 2.0d : 1.0d;
            i = i2;
        }
        return Math.ceil(d);
    }

    public static String substring(String str, int i, int i2) {
        int i3 = i;
        int i4 = 0;
        while (i3 < str.length()) {
            int i5 = i3 + 1;
            i4 = str.substring(i3, i5).matches("[一-龥]") ? i4 + 2 : i4 + 1;
            if (i4 == i2) {
                return str.substring(i, i5);
            }
            if (i4 > i2) {
                return str.substring(i, i3);
            }
            i3 = i5;
        }
        return str;
    }

    public static String substring(String str, int i, boolean z) {
        String substring = substring(str, 0, i);
        if (!substring.equals(str)) {
            return substring + "...";
        }
        return str;
    }

    public static String formatStrLen(String str, int i) {
        if (TextUtils.isEmpty(str) || str.length() <= i) {
            return str;
        }
        String substring = str.substring(0, i);
        return substring + "...";
    }

    public static void modifyTextViewDrawable(TextView textView, Drawable drawable, int i) {
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        if (i == 0) {
            textView.setCompoundDrawables(drawable, null, null, null);
        } else if (i == 1) {
            textView.setCompoundDrawables(null, drawable, null, null);
        } else if (i == 2) {
            textView.setCompoundDrawables(null, null, drawable, null);
        } else {
            textView.setCompoundDrawables(null, null, null, drawable);
        }
    }

    public static String formatPhoneRemoveSpaces(String str) {
        return str.replaceAll(ConstantUtils.PLACEHOLDER_STR_ONE, "").replaceAll("-", "");
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x00be  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x00c3 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static String getFileMD5(File file) {
        StringBuffer stringBuffer;
        char[] cArr;
        int i;
        FileInputStream fileInputStream;
        byte[] digest;
        FileInputStream fileInputStream2 = null;
        try {
            try {
                try {
                    cArr = new char[]{'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
                    fileInputStream = new FileInputStream(file);
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    try {
                        MappedByteBuffer map = fileInputStream.getChannel().map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
                        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
                        messageDigest.update(map);
                        digest = messageDigest.digest();
                        stringBuffer = new StringBuffer(digest.length * 2);
                    } catch (Exception e) {
                        e = e;
                        stringBuffer = null;
                    }
                    try {
                        int length = digest.length;
                        for (byte b : digest) {
                            char c = cArr[(b & 240) >> 4];
                            char c2 = cArr[b & 15];
                            stringBuffer.append(c);
                            stringBuffer.append(c2);
                        }
                        fileInputStream.close();
                        fileInputStream2 = length;
                    } catch (Exception e2) {
                        e = e2;
                        fileInputStream2 = fileInputStream;
                        e.printStackTrace();
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                            fileInputStream2 = fileInputStream2;
                        }
                        if (stringBuffer == null) {
                        }
                    }
                } catch (Throwable th2) {
                    th = th2;
                    fileInputStream2 = fileInputStream;
                    if (fileInputStream2 != null) {
                        try {
                            fileInputStream2.close();
                        } catch (Exception e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
            } catch (Exception e4) {
                e = e4;
                stringBuffer = null;
            }
        } catch (Exception e5) {
            e5.printStackTrace();
        }
        return stringBuffer == null ? stringBuffer.toString() : "";
    }

    public static byte[] toByteArray(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        String lowerCase = str.toLowerCase();
        byte[] bArr = new byte[lowerCase.length() / 2];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            bArr[i2] = (byte) ((((byte) (Character.digit(lowerCase.charAt(i), 16) & 255)) << 4) | ((byte) (Character.digit(lowerCase.charAt(i + 1), 16) & 255)));
            i += 2;
        }
        return bArr;
    }

    public static String toHexString(byte[] bArr) {
        if (bArr == null || bArr.length < 1) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bArr.length; i++) {
            if ((bArr[i] & 255) < 16) {
                sb.append("0");
            }
            sb.append(Integer.toHexString(bArr[i] & 255));
        }
        return sb.toString().toLowerCase();
    }

    public static String compress(String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        GZIPOutputStream gZIPOutputStream;
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
            } catch (Exception unused) {
                gZIPOutputStream = null;
            } catch (Throwable th) {
                th = th;
                gZIPOutputStream = null;
            }
        } catch (Exception unused2) {
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
        } catch (Throwable th2) {
            th = th2;
            byteArrayOutputStream = null;
            gZIPOutputStream = null;
        }
        try {
            gZIPOutputStream.write(str.getBytes());
            gZIPOutputStream.close();
            String byteArrayOutputStream2 = byteArrayOutputStream.toString("ISO-8859-1");
            gZIPOutputStream.close();
            byteArrayOutputStream.close();
            return byteArrayOutputStream2;
        } catch (Exception unused3) {
            if (gZIPOutputStream != null) {
                gZIPOutputStream.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            return str;
        } catch (Throwable th3) {
            th = th3;
            if (gZIPOutputStream != null) {
                gZIPOutputStream.close();
            }
            if (byteArrayOutputStream != null) {
                byteArrayOutputStream.close();
            }
            throw th;
        }
    }

    public static String uncompress(String str) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream;
        ByteArrayInputStream byteArrayInputStream;
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        GZIPInputStream gZIPInputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            try {
                byteArrayInputStream = new ByteArrayInputStream(str.getBytes("ISO-8859-1"));
                try {
                    GZIPInputStream gZIPInputStream2 = new GZIPInputStream(byteArrayInputStream);
                    try {
                        byte[] bArr = new byte[256];
                        while (true) {
                            int read = gZIPInputStream2.read(bArr);
                            if (read >= 0) {
                                byteArrayOutputStream.write(bArr, 0, read);
                            } else {
                                String byteArrayOutputStream2 = byteArrayOutputStream.toString();
                                byteArrayOutputStream.close();
                                gZIPInputStream2.close();
                                byteArrayInputStream.close();
                                return byteArrayOutputStream2;
                            }
                        }
                    } catch (Exception unused) {
                        gZIPInputStream = gZIPInputStream2;
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        if (gZIPInputStream != null) {
                            gZIPInputStream.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        return str;
                    } catch (Throwable th) {
                        th = th;
                        gZIPInputStream = gZIPInputStream2;
                        if (byteArrayOutputStream != null) {
                            byteArrayOutputStream.close();
                        }
                        if (gZIPInputStream != null) {
                            gZIPInputStream.close();
                        }
                        if (byteArrayInputStream != null) {
                            byteArrayInputStream.close();
                        }
                        throw th;
                    }
                } catch (Exception unused2) {
                } catch (Throwable th2) {
                    th = th2;
                }
            } catch (Exception unused3) {
                byteArrayInputStream = null;
            } catch (Throwable th3) {
                th = th3;
                byteArrayInputStream = null;
            }
        } catch (Exception unused4) {
            byteArrayOutputStream = null;
            byteArrayInputStream = null;
        } catch (Throwable th4) {
            th = th4;
            byteArrayOutputStream = null;
            byteArrayInputStream = null;
        }
    }

    public static String bytes2HexString(byte[] bArr) {
        int length;
        if (bArr != null && (length = bArr.length) > 0) {
            char[] cArr = new char[length << 1];
            int i = 0;
            for (int i2 = 0; i2 < length; i2++) {
                int i3 = i + 1;
                char[] cArr2 = HEX_DIGITS;
                cArr[i] = cArr2[(bArr[i2] >> 4) & 15];
                i = i3 + 1;
                cArr[i3] = cArr2[bArr[i2] & 15];
            }
            return new String(cArr);
        }
        return "";
    }

    public static byte[] hexString2Bytes(String str) {
        if (isSpace(str)) {
            return null;
        }
        int length = str.length();
        if (length % 2 != 0) {
            str = "0" + str;
            length++;
        }
        char[] charArray = str.toUpperCase().toCharArray();
        byte[] bArr = new byte[length >> 1];
        for (int i = 0; i < length; i += 2) {
            bArr[i >> 1] = (byte) ((hex2Dec(charArray[i]) << 4) | hex2Dec(charArray[i + 1]));
        }
        return bArr;
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    private static int hex2Dec(char c) {
        if (c < '0' || c > '9') {
            if (c >= 'A' && c <= 'F') {
                return (c - 'A') + 10;
            }
            throw new IllegalArgumentException();
        }
        return c - '0';
    }

    public static SpannableString getHighLightText(Context context, String str, String str2, @ColorRes int i) {
        SpannableString spannableString = new SpannableString(str);
        Matcher matcher = Pattern.compile(str2).matcher(str);
        while (matcher.find()) {
            spannableString.setSpan(new ForegroundColorSpan(ContextCompat.getColor(context, i)), matcher.start(), matcher.end(), 33);
        }
        return spannableString;
    }

    public static String getCommaSpliceStrByList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : list) {
            sb.append(str);
            sb.append(COMMA_STR);
        }
        return sb.length() == 0 ? "" : sb.substring(0, sb.length() - 1);
    }

    public static List<String> getListByCommaSplit(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        ArrayList arrayList = new ArrayList();
        if (str.contains(COMMA_STR)) {
            for (String str2 : TextUtils.split(str, COMMA_STR)) {
                arrayList.add(str2);
            }
        } else {
            arrayList.add(str);
        }
        return arrayList;
    }
}
