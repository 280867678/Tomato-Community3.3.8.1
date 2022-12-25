package com.blankj.utilcode.util;

import android.content.ClipData;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.p002v4.util.SimpleArrayMap;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Formatter;
import java.util.Iterator;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes2.dex */
public final class LogUtils {
    private static SimpleDateFormat simpleDateFormat;

    /* renamed from: T */
    private static final char[] f1194T = {'V', 'D', 'I', 'W', 'E', 'A'};
    private static final String FILE_SEP = System.getProperty("file.separator");
    private static final String LINE_SEP = System.getProperty("line.separator");
    private static final Config CONFIG = new Config();
    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private static final SimpleArrayMap<Class, IFormatter> I_FORMATTER_MAP = new SimpleArrayMap<>();

    /* loaded from: classes2.dex */
    public interface IFileWriter {
        void write(String str, String str2);
    }

    /* loaded from: classes2.dex */
    public static abstract class IFormatter<T> {
        public abstract String format(T t);
    }

    public static void iTag(String str, Object... objArr) {
        log(4, str, objArr);
    }

    public static void json(Object obj) {
        log(35, CONFIG.getGlobalTag(), obj);
    }

    public static void json(String str, Object obj) {
        log(35, str, obj);
    }

    public static void log(int i, String str, Object... objArr) {
        if (!CONFIG.isLogSwitch()) {
            return;
        }
        final int i2 = i & 15;
        int i3 = i & 240;
        if (!CONFIG.isLog2ConsoleSwitch() && !CONFIG.isLog2FileSwitch() && i3 != 16) {
            return;
        }
        if (i2 < CONFIG.mConsoleFilter && i2 < CONFIG.mFileFilter) {
            return;
        }
        final TagHead processTagAndHead = processTagAndHead(str);
        final String processBody = processBody(i3, objArr);
        if (CONFIG.isLog2ConsoleSwitch() && i3 != 16 && i2 >= CONFIG.mConsoleFilter) {
            print2Console(i2, processTagAndHead.tag, processTagAndHead.consoleHead, processBody);
        }
        if ((!CONFIG.isLog2FileSwitch() && i3 != 16) || i2 < CONFIG.mFileFilter) {
            return;
        }
        EXECUTOR.execute(new Runnable() { // from class: com.blankj.utilcode.util.LogUtils.1
            @Override // java.lang.Runnable
            public void run() {
                int i4 = i2;
                String str2 = processTagAndHead.tag;
                LogUtils.print2File(i4, str2, processTagAndHead.fileHead + processBody);
            }
        });
    }

    private static TagHead processTagAndHead(String str) {
        String str2;
        String str3;
        String name;
        if (!CONFIG.mTagIsSpace && !CONFIG.isLogHeadSwitch()) {
            str3 = CONFIG.getGlobalTag();
        } else {
            StackTraceElement[] stackTrace = new Throwable().getStackTrace();
            int stackOffset = CONFIG.getStackOffset() + 3;
            if (stackOffset >= stackTrace.length) {
                String fileName = getFileName(stackTrace[3]);
                if (!CONFIG.mTagIsSpace || !isSpace(str)) {
                    fileName = str;
                } else {
                    int indexOf = fileName.indexOf(46);
                    if (indexOf != -1) {
                        fileName = fileName.substring(0, indexOf);
                    }
                }
                return new TagHead(fileName, null, ": ");
            }
            StackTraceElement stackTraceElement = stackTrace[stackOffset];
            String fileName2 = getFileName(stackTraceElement);
            if (!CONFIG.mTagIsSpace || !isSpace(str)) {
                str2 = str;
            } else {
                int indexOf2 = fileName2.indexOf(46);
                str2 = indexOf2 == -1 ? fileName2 : fileName2.substring(0, indexOf2);
            }
            if (CONFIG.isLogHeadSwitch()) {
                String formatter = new Formatter().format("%s, %s.%s(%s:%d)", Thread.currentThread().getName(), stackTraceElement.getClassName(), stackTraceElement.getMethodName(), fileName2, Integer.valueOf(stackTraceElement.getLineNumber())).toString();
                String str4 = " [" + formatter + "]: ";
                if (CONFIG.getStackDeep() <= 1) {
                    return new TagHead(str2, new String[]{formatter}, str4);
                }
                String[] strArr = new String[Math.min(CONFIG.getStackDeep(), stackTrace.length - stackOffset)];
                strArr[0] = formatter;
                String formatter2 = new Formatter().format("%" + (name.length() + 2) + "s", "").toString();
                int length = strArr.length;
                for (int i = 1; i < length; i++) {
                    StackTraceElement stackTraceElement2 = stackTrace[i + stackOffset];
                    strArr[i] = new Formatter().format("%s%s.%s(%s:%d)", formatter2, stackTraceElement2.getClassName(), stackTraceElement2.getMethodName(), getFileName(stackTraceElement2), Integer.valueOf(stackTraceElement2.getLineNumber())).toString();
                }
                return new TagHead(str2, strArr, str4);
            }
            str3 = str2;
        }
        return new TagHead(str3, null, ": ");
    }

    private static String getFileName(StackTraceElement stackTraceElement) {
        String fileName = stackTraceElement.getFileName();
        if (fileName != null) {
            return fileName;
        }
        String className = stackTraceElement.getClassName();
        String[] split = className.split("\\.");
        if (split.length > 0) {
            className = split[split.length - 1];
        }
        int indexOf = className.indexOf(36);
        if (indexOf != -1) {
            className = className.substring(0, indexOf);
        }
        return className + ".java";
    }

    private static String processBody(int i, Object... objArr) {
        String str;
        if (objArr != null) {
            if (objArr.length == 1) {
                str = formatObject(i, objArr[0]);
            } else {
                StringBuilder sb = new StringBuilder();
                int length = objArr.length;
                for (int i2 = 0; i2 < length; i2++) {
                    Object obj = objArr[i2];
                    sb.append("args");
                    sb.append("[");
                    sb.append(i2);
                    sb.append("]");
                    sb.append(" = ");
                    sb.append(formatObject(obj));
                    sb.append(LINE_SEP);
                }
                str = sb.toString();
            }
        } else {
            str = "null";
        }
        return str.length() == 0 ? "log nothing" : str;
    }

    private static String formatObject(int i, Object obj) {
        if (obj == null) {
            return "null";
        }
        if (i == 32) {
            return LogFormatter.object2String(obj, 32);
        }
        if (i == 48) {
            return LogFormatter.object2String(obj, 48);
        }
        return formatObject(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String formatObject(Object obj) {
        IFormatter iFormatter;
        if (obj == null) {
            return "null";
        }
        if (!I_FORMATTER_MAP.isEmpty() && (iFormatter = I_FORMATTER_MAP.get(getClassFromObject(obj))) != null) {
            return iFormatter.format(obj);
        }
        return LogFormatter.object2String(obj);
    }

    private static void print2Console(int i, String str, String[] strArr, String str2) {
        if (CONFIG.isSingleTagSwitch()) {
            printSingleTagMsg(i, str, processSingleTagMsg(i, str, strArr, str2));
            return;
        }
        printBorder(i, str, true);
        printHead(i, str, strArr);
        printMsg(i, str, str2);
        printBorder(i, str, false);
    }

    private static void printBorder(int i, String str, boolean z) {
        if (CONFIG.isLogBorderSwitch()) {
            Log.println(i, str, z ? "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────" : "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        }
    }

    private static void printHead(int i, String str, String[] strArr) {
        if (strArr != null) {
            for (String str2 : strArr) {
                if (CONFIG.isLogBorderSwitch()) {
                    str2 = "│ " + str2;
                }
                Log.println(i, str, str2);
            }
            if (!CONFIG.isLogBorderSwitch()) {
                return;
            }
            Log.println(i, str, "├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
        }
    }

    private static void printMsg(int i, String str, String str2) {
        int length = str2.length();
        int i2 = length / 1100;
        if (i2 <= 0) {
            printSubMsg(i, str, str2);
            return;
        }
        int i3 = 0;
        int i4 = 0;
        while (i3 < i2) {
            int i5 = i4 + 1100;
            printSubMsg(i, str, str2.substring(i4, i5));
            i3++;
            i4 = i5;
        }
        if (i4 == length) {
            return;
        }
        printSubMsg(i, str, str2.substring(i4, length));
    }

    private static void printSubMsg(int i, String str, String str2) {
        String[] split;
        if (!CONFIG.isLogBorderSwitch()) {
            Log.println(i, str, str2);
            return;
        }
        for (String str3 : str2.split(LINE_SEP)) {
            Log.println(i, str, "│ " + str3);
        }
    }

    private static String processSingleTagMsg(int i, String str, String[] strArr, String str2) {
        StringBuilder sb = new StringBuilder();
        int i2 = 0;
        if (CONFIG.isLogBorderSwitch()) {
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(LINE_SEP);
            sb.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
            sb.append(LINE_SEP);
            if (strArr != null) {
                for (String str3 : strArr) {
                    sb.append("│ ");
                    sb.append(str3);
                    sb.append(LINE_SEP);
                }
                sb.append("├┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄┄");
                sb.append(LINE_SEP);
            }
            String[] split = str2.split(LINE_SEP);
            int length = split.length;
            while (i2 < length) {
                String str4 = split[i2];
                sb.append("│ ");
                sb.append(str4);
                sb.append(LINE_SEP);
                i2++;
            }
            sb.append("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
        } else {
            if (strArr != null) {
                sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                sb.append(LINE_SEP);
                int length2 = strArr.length;
                while (i2 < length2) {
                    sb.append(strArr[i2]);
                    sb.append(LINE_SEP);
                    i2++;
                }
            }
            sb.append(str2);
        }
        return sb.toString();
    }

    private static void printSingleTagMsg(int i, String str, String str2) {
        int length = str2.length();
        int i2 = 1100;
        int i3 = CONFIG.isLogBorderSwitch() ? (length - 113) / 1100 : length / 1100;
        if (i3 > 0) {
            int i4 = 1;
            if (CONFIG.isLogBorderSwitch()) {
                Log.println(i, str, str2.substring(0, 1100) + LINE_SEP + "└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                while (i4 < i3) {
                    StringBuilder sb = new StringBuilder();
                    sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                    sb.append(LINE_SEP);
                    sb.append("┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                    sb.append(LINE_SEP);
                    sb.append("│ ");
                    int i5 = i2 + 1100;
                    sb.append(str2.substring(i2, i5));
                    sb.append(LINE_SEP);
                    sb.append("└────────────────────────────────────────────────────────────────────────────────────────────────────────────────");
                    Log.println(i, str, sb.toString());
                    i4++;
                    i2 = i5;
                }
                if (i2 == length - 113) {
                    return;
                }
                Log.println(i, str, ConstantUtils.PLACEHOLDER_STR_ONE + LINE_SEP + "┌────────────────────────────────────────────────────────────────────────────────────────────────────────────────" + LINE_SEP + "│ " + str2.substring(i2, length));
                return;
            }
            Log.println(i, str, str2.substring(0, 1100));
            while (i4 < i3) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                sb2.append(LINE_SEP);
                int i6 = i2 + 1100;
                sb2.append(str2.substring(i2, i6));
                Log.println(i, str, sb2.toString());
                i4++;
                i2 = i6;
            }
            if (i2 == length) {
                return;
            }
            Log.println(i, str, ConstantUtils.PLACEHOLDER_STR_ONE + LINE_SEP + str2.substring(i2, length));
            return;
        }
        Log.println(i, str, str2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static void print2File(int i, String str, String str2) {
        String format = getSdf().format(new Date());
        String substring = format.substring(0, 10);
        String substring2 = format.substring(11);
        String str3 = CONFIG.getDir() + CONFIG.getFilePrefix() + "_" + substring + "_" + CONFIG.getProcessName() + ".txt";
        if (!createOrExistsFile(str3, substring)) {
            Log.e("LogUtils", "create " + str3 + " failed!");
            return;
        }
        input2File(substring2 + f1194T[i - 2] + "/" + str + str2 + LINE_SEP, str3);
    }

    private static SimpleDateFormat getSdf() {
        if (simpleDateFormat == null) {
            simpleDateFormat = new SimpleDateFormat("yyyy_MM_dd HH:mm:ss.SSS ", Locale.getDefault());
        }
        return simpleDateFormat;
    }

    private static boolean createOrExistsFile(String str, String str2) {
        File file = new File(str);
        if (file.exists()) {
            return file.isFile();
        }
        if (!createOrExistsDir(file.getParentFile())) {
            return false;
        }
        try {
            deleteDueLogs(str, str2);
            boolean createNewFile = file.createNewFile();
            if (createNewFile) {
                printDeviceInfo(str, str2);
            }
            return createNewFile;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void deleteDueLogs(String str, String str2) {
        File[] listFiles;
        if (CONFIG.getSaveDays() > 0 && (listFiles = new File(str).getParentFile().listFiles(new FilenameFilter() { // from class: com.blankj.utilcode.util.LogUtils.2
            @Override // java.io.FilenameFilter
            public boolean accept(File file, String str3) {
                return str3.matches("^" + LogUtils.CONFIG.getFilePrefix() + "_[0-9]{4}_[0-9]{2}_[0-9]{2}_.*$");
            }
        })) != null && listFiles.length > 0) {
            SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
            try {
                long time = simpleDateFormat2.parse(str2).getTime() - (CONFIG.getSaveDays() * DateUtils.ONE_DAY_MILLIONS);
                for (final File file : listFiles) {
                    String name = file.getName();
                    name.length();
                    if (simpleDateFormat2.parse(findDate(name)).getTime() <= time) {
                        EXECUTOR.execute(new Runnable() { // from class: com.blankj.utilcode.util.LogUtils.3
                            @Override // java.lang.Runnable
                            public void run() {
                                if (!file.delete()) {
                                    Log.e("LogUtils", "delete " + file + " failed!");
                                }
                            }
                        });
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private static String findDate(String str) {
        Matcher matcher = Pattern.compile("[0-9]{4}_[0-9]{2}_[0-9]{2}").matcher(str);
        return matcher.find() ? matcher.group() : "";
    }

    private static void printDeviceInfo(String str, String str2) {
        String str3 = "";
        int i = 0;
        try {
            PackageInfo packageInfo = Utils.getApp().getPackageManager().getPackageInfo(Utils.getApp().getPackageName(), 0);
            if (packageInfo != null) {
                str3 = packageInfo.versionName;
                i = packageInfo.versionCode;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        input2File("************* Log Head ****************\nDate of Log        : " + str2 + "\nDevice Manufacturer: " + Build.MANUFACTURER + "\nDevice Model       : " + Build.MODEL + "\nAndroid Version    : " + Build.VERSION.RELEASE + "\nAndroid SDK        : " + Build.VERSION.SDK_INT + "\nApp VersionName    : " + str3 + "\nApp VersionCode    : " + i + "\n************* Log Head ****************\n\n", str);
    }

    private static boolean createOrExistsDir(File file) {
        return file != null && (!file.exists() ? file.mkdirs() : file.isDirectory());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean isSpace(String str) {
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

    private static void input2File(String str, String str2) {
        BufferedWriter bufferedWriter;
        if (CONFIG.mFileWriter == null) {
            BufferedWriter bufferedWriter2 = null;
            try {
                try {
                    try {
                        bufferedWriter = new BufferedWriter(new FileWriter(str2, true));
                    } catch (IOException e) {
                        e = e;
                    }
                } catch (Throwable th) {
                    th = th;
                }
                try {
                    bufferedWriter.write(str);
                    bufferedWriter.close();
                } catch (IOException e2) {
                    e = e2;
                    bufferedWriter2 = bufferedWriter;
                    e.printStackTrace();
                    Log.e("LogUtils", "log to " + str2 + " failed!");
                    if (bufferedWriter2 == null) {
                        return;
                    }
                    bufferedWriter2.close();
                    return;
                } catch (Throwable th2) {
                    th = th2;
                    bufferedWriter2 = bufferedWriter;
                    if (bufferedWriter2 != null) {
                        try {
                            bufferedWriter2.close();
                        } catch (IOException e3) {
                            e3.printStackTrace();
                        }
                    }
                    throw th;
                }
                return;
            } catch (IOException e4) {
                e4.printStackTrace();
                return;
            }
        }
        CONFIG.mFileWriter.write(str2, str);
    }

    /* loaded from: classes2.dex */
    public static final class Config {
        private int mConsoleFilter;
        private String mDefaultDir;
        private String mDir;
        private int mFileFilter;
        private String mFilePrefix;
        private IFileWriter mFileWriter;
        private String mGlobalTag;
        private boolean mLog2ConsoleSwitch;
        private boolean mLog2FileSwitch;
        private boolean mLogBorderSwitch;
        private boolean mLogHeadSwitch;
        private boolean mLogSwitch;
        private String mProcessName;
        private int mSaveDays;
        private boolean mSingleTagSwitch;
        private int mStackDeep;
        private int mStackOffset;
        private boolean mTagIsSpace;

        private Config() {
            this.mFilePrefix = "util";
            this.mLogSwitch = true;
            this.mLog2ConsoleSwitch = true;
            this.mGlobalTag = "";
            this.mTagIsSpace = true;
            this.mLogHeadSwitch = true;
            this.mLog2FileSwitch = false;
            this.mLogBorderSwitch = true;
            this.mSingleTagSwitch = true;
            this.mConsoleFilter = 2;
            this.mFileFilter = 2;
            this.mStackDeep = 1;
            this.mStackOffset = 0;
            this.mSaveDays = -1;
            this.mProcessName = Utils.getCurrentProcessName();
            if (this.mDefaultDir != null) {
                return;
            }
            if ("mounted".equals(Environment.getExternalStorageState()) && Utils.getApp().getExternalCacheDir() != null) {
                this.mDefaultDir = Utils.getApp().getExternalCacheDir() + LogUtils.FILE_SEP + "log" + LogUtils.FILE_SEP;
                return;
            }
            this.mDefaultDir = Utils.getApp().getCacheDir() + LogUtils.FILE_SEP + "log" + LogUtils.FILE_SEP;
        }

        public final String getProcessName() {
            String str = this.mProcessName;
            return str == null ? "" : str.replace(":", "_");
        }

        public final String getDir() {
            String str = this.mDir;
            return str == null ? this.mDefaultDir : str;
        }

        public final String getFilePrefix() {
            return this.mFilePrefix;
        }

        public final boolean isLogSwitch() {
            return this.mLogSwitch;
        }

        public final boolean isLog2ConsoleSwitch() {
            return this.mLog2ConsoleSwitch;
        }

        public final String getGlobalTag() {
            return LogUtils.isSpace(this.mGlobalTag) ? "" : this.mGlobalTag;
        }

        public final boolean isLogHeadSwitch() {
            return this.mLogHeadSwitch;
        }

        public final boolean isLog2FileSwitch() {
            return this.mLog2FileSwitch;
        }

        public final boolean isLogBorderSwitch() {
            return this.mLogBorderSwitch;
        }

        public final boolean isSingleTagSwitch() {
            return this.mSingleTagSwitch;
        }

        public final char getConsoleFilter() {
            return LogUtils.f1194T[this.mConsoleFilter - 2];
        }

        public final char getFileFilter() {
            return LogUtils.f1194T[this.mFileFilter - 2];
        }

        public final int getStackDeep() {
            return this.mStackDeep;
        }

        public final int getStackOffset() {
            return this.mStackOffset;
        }

        public final int getSaveDays() {
            return this.mSaveDays;
        }

        public String toString() {
            return "process: " + getProcessName() + LogUtils.LINE_SEP + "switch: " + isLogSwitch() + LogUtils.LINE_SEP + "console: " + isLog2ConsoleSwitch() + LogUtils.LINE_SEP + "tag: " + getGlobalTag() + LogUtils.LINE_SEP + "head: " + isLogHeadSwitch() + LogUtils.LINE_SEP + "file: " + isLog2FileSwitch() + LogUtils.LINE_SEP + "dir: " + getDir() + LogUtils.LINE_SEP + "filePrefix: " + getFilePrefix() + LogUtils.LINE_SEP + "border: " + isLogBorderSwitch() + LogUtils.LINE_SEP + "singleTag: " + isSingleTagSwitch() + LogUtils.LINE_SEP + "consoleFilter: " + getConsoleFilter() + LogUtils.LINE_SEP + "fileFilter: " + getFileFilter() + LogUtils.LINE_SEP + "stackDeep: " + getStackDeep() + LogUtils.LINE_SEP + "stackOffset: " + getStackOffset() + LogUtils.LINE_SEP + "saveDays: " + getSaveDays() + LogUtils.LINE_SEP + "formatter: " + LogUtils.I_FORMATTER_MAP;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class TagHead {
        String[] consoleHead;
        String fileHead;
        String tag;

        TagHead(String str, String[] strArr, String str2) {
            this.tag = str;
            this.consoleHead = strArr;
            this.fileHead = str2;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes2.dex */
    public static final class LogFormatter {
        private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

        static String object2String(Object obj) {
            return object2String(obj, -1);
        }

        static String object2String(Object obj, int i) {
            if (obj.getClass().isArray()) {
                return array2String(obj);
            }
            if (obj instanceof Throwable) {
                return throwable2String((Throwable) obj);
            }
            if (obj instanceof Bundle) {
                return bundle2String((Bundle) obj);
            }
            if (obj instanceof Intent) {
                return intent2String((Intent) obj);
            }
            if (i == 32) {
                return object2Json(obj);
            }
            if (i == 48) {
                return formatXml(obj.toString());
            }
            return obj.toString();
        }

        private static String throwable2String(Throwable th) {
            return ThrowableUtils.getFullStackTrace(th);
        }

        private static String bundle2String(Bundle bundle) {
            Iterator<String> it2 = bundle.keySet().iterator();
            if (!it2.hasNext()) {
                return "Bundle {}";
            }
            StringBuilder sb = new StringBuilder(128);
            sb.append("Bundle { ");
            while (true) {
                String next = it2.next();
                Object obj = bundle.get(next);
                sb.append(next);
                sb.append('=');
                if (!(obj instanceof Bundle)) {
                    sb.append(LogUtils.formatObject(obj));
                } else {
                    sb.append(obj == bundle ? "(this Bundle)" : bundle2String((Bundle) obj));
                }
                if (!it2.hasNext()) {
                    sb.append(" }");
                    return sb.toString();
                }
                sb.append(',');
                sb.append(' ');
            }
        }

        private static String intent2String(Intent intent) {
            boolean z;
            Intent selector;
            ClipData clipData;
            StringBuilder sb = new StringBuilder(128);
            sb.append("Intent { ");
            String action = intent.getAction();
            boolean z2 = true;
            if (action != null) {
                sb.append("act=");
                sb.append(action);
                z = false;
            } else {
                z = true;
            }
            Set<String> categories = intent.getCategories();
            if (categories != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("cat=[");
                for (String str : categories) {
                    if (!z2) {
                        sb.append(',');
                    }
                    sb.append(str);
                    z2 = false;
                }
                sb.append("]");
                z = false;
            }
            Uri data = intent.getData();
            if (data != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("dat=");
                sb.append(data);
                z = false;
            }
            String type = intent.getType();
            if (type != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("typ=");
                sb.append(type);
                z = false;
            }
            int flags = intent.getFlags();
            if (flags != 0) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("flg=0x");
                sb.append(Integer.toHexString(flags));
                z = false;
            }
            String str2 = intent.getPackage();
            if (str2 != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("pkg=");
                sb.append(str2);
                z = false;
            }
            ComponentName component = intent.getComponent();
            if (component != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("cmp=");
                sb.append(component.flattenToShortString());
                z = false;
            }
            Rect sourceBounds = intent.getSourceBounds();
            if (sourceBounds != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("bnds=");
                sb.append(sourceBounds.toShortString());
                z = false;
            }
            if (Build.VERSION.SDK_INT >= 16 && (clipData = intent.getClipData()) != null) {
                if (!z) {
                    sb.append(' ');
                }
                clipData2String(clipData, sb);
                z = false;
            }
            Bundle extras = intent.getExtras();
            if (extras != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("extras={");
                sb.append(bundle2String(extras));
                sb.append('}');
                z = false;
            }
            if (Build.VERSION.SDK_INT >= 15 && (selector = intent.getSelector()) != null) {
                if (!z) {
                    sb.append(' ');
                }
                sb.append("sel={");
                sb.append(selector == intent ? "(this Intent)" : intent2String(selector));
                sb.append("}");
            }
            sb.append(" }");
            return sb.toString();
        }

        @RequiresApi(api = 16)
        private static void clipData2String(ClipData clipData, StringBuilder sb) {
            ClipData.Item itemAt = clipData.getItemAt(0);
            if (itemAt == null) {
                sb.append("ClipData.Item {}");
                return;
            }
            sb.append("ClipData.Item { ");
            String htmlText = itemAt.getHtmlText();
            if (htmlText != null) {
                sb.append("H:");
                sb.append(htmlText);
                sb.append("}");
                return;
            }
            CharSequence text = itemAt.getText();
            if (text != null) {
                sb.append("T:");
                sb.append(text);
                sb.append("}");
                return;
            }
            Uri uri = itemAt.getUri();
            if (uri != null) {
                sb.append("U:");
                sb.append(uri);
                sb.append("}");
                return;
            }
            Intent intent = itemAt.getIntent();
            if (intent != null) {
                sb.append("I:");
                sb.append(intent2String(intent));
                sb.append("}");
                return;
            }
            sb.append("NULL");
            sb.append("}");
        }

        private static String object2Json(Object obj) {
            if (obj instanceof CharSequence) {
                return formatJson(obj.toString());
            }
            try {
                return GSON.toJson(obj);
            } catch (Throwable unused) {
                return obj.toString();
            }
        }

        private static String formatJson(String str) {
            try {
                int length = str.length();
                for (int i = 0; i < length; i++) {
                    char charAt = str.charAt(i);
                    if (charAt == '{') {
                        return new JSONObject(str).toString(2);
                    }
                    if (charAt == '[') {
                        return new JSONArray(str).toString(2);
                    }
                    if (!Character.isWhitespace(charAt)) {
                        return str;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return str;
        }

        private static String formatXml(String str) {
            try {
                StreamSource streamSource = new StreamSource(new StringReader(str));
                StreamResult streamResult = new StreamResult(new StringWriter());
                Transformer newTransformer = TransformerFactory.newInstance().newTransformer();
                newTransformer.setOutputProperty("indent", "yes");
                newTransformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
                newTransformer.transform(streamSource, streamResult);
                String obj = streamResult.getWriter().toString();
                return obj.replaceFirst(SimpleComparison.GREATER_THAN_OPERATION, SimpleComparison.GREATER_THAN_OPERATION + LogUtils.LINE_SEP);
            } catch (Exception e) {
                e.printStackTrace();
                return str;
            }
        }

        private static String array2String(Object obj) {
            if (obj instanceof Object[]) {
                return Arrays.deepToString((Object[]) obj);
            }
            if (obj instanceof boolean[]) {
                return Arrays.toString((boolean[]) obj);
            }
            if (obj instanceof byte[]) {
                return Arrays.toString((byte[]) obj);
            }
            if (obj instanceof char[]) {
                return Arrays.toString((char[]) obj);
            }
            if (obj instanceof double[]) {
                return Arrays.toString((double[]) obj);
            }
            if (obj instanceof float[]) {
                return Arrays.toString((float[]) obj);
            }
            if (obj instanceof int[]) {
                return Arrays.toString((int[]) obj);
            }
            if (obj instanceof long[]) {
                return Arrays.toString((long[]) obj);
            }
            if (obj instanceof short[]) {
                return Arrays.toString((short[]) obj);
            }
            throw new IllegalArgumentException("Array has incompatible type: " + obj.getClass());
        }
    }

    private static Class getClassFromObject(Object obj) {
        String obj2;
        Class<?> cls = obj.getClass();
        if (cls.isAnonymousClass() || cls.isSynthetic()) {
            Type[] genericInterfaces = cls.getGenericInterfaces();
            if (genericInterfaces.length == 1) {
                Type type = genericInterfaces[0];
                while (type instanceof ParameterizedType) {
                    type = ((ParameterizedType) type).getRawType();
                }
                obj2 = type.toString();
            } else {
                Type genericSuperclass = cls.getGenericSuperclass();
                while (genericSuperclass instanceof ParameterizedType) {
                    genericSuperclass = ((ParameterizedType) genericSuperclass).getRawType();
                }
                obj2 = genericSuperclass.toString();
            }
            if (obj2.startsWith("class ")) {
                obj2 = obj2.substring(6);
            } else if (obj2.startsWith("interface ")) {
                obj2 = obj2.substring(10);
            }
            try {
                return Class.forName(obj2);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return cls;
    }
}
