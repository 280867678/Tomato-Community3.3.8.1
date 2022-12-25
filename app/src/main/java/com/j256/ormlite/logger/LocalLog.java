package com.j256.ormlite.logger;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public class LocalLog implements Log {
    public static final String LOCAL_LOG_FILE_PROPERTY = "com.j256.ormlite.logger.file";
    public static final String LOCAL_LOG_LEVEL_PROPERTY = "com.j256.ormlite.logger.level";
    public static PrintStream printStream;
    public final String className;
    public final Log.Level level;
    public static final Log.Level DEFAULT_LEVEL = Log.Level.DEBUG;
    public static final ThreadLocal<DateFormat> dateFormatThreadLocal = new ThreadLocal<DateFormat>() { // from class: com.j256.ormlite.logger.LocalLog.1
        @Override // java.lang.ThreadLocal
        public DateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");
        }
    };
    public static final String LOCAL_LOG_PROPERTIES_FILE = "/ormliteLocalLog.properties";
    public static final List<PatternLevel> classLevels = readLevelResourceFile(LocalLog.class.getResourceAsStream(LOCAL_LOG_PROPERTIES_FILE));

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class PatternLevel {
        public Log.Level level;
        public Pattern pattern;

        public PatternLevel(Pattern pattern, Log.Level level) {
            this.pattern = pattern;
            this.level = level;
        }
    }

    static {
        openLogFile(System.getProperty(LOCAL_LOG_FILE_PROPERTY));
    }

    public LocalLog(String str) {
        this.className = LoggerFactory.getSimpleClassName(str);
        List<PatternLevel> list = classLevels;
        Log.Level level = null;
        if (list != null) {
            for (PatternLevel patternLevel : list) {
                if (patternLevel.pattern.matcher(str).matches() && (level == null || patternLevel.level.ordinal() < level.ordinal())) {
                    level = patternLevel.level;
                }
            }
        }
        if (level == null) {
            String property = System.getProperty(LOCAL_LOG_LEVEL_PROPERTY);
            if (property == null) {
                level = DEFAULT_LEVEL;
            } else {
                try {
                    level = Log.Level.valueOf(property.toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException("Level '" + property + "' was not found", e);
                }
            }
        }
        this.level = level;
    }

    public static List<PatternLevel> configureClassLevels(InputStream inputStream) {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        ArrayList arrayList = new ArrayList();
        while (true) {
            String readLine = bufferedReader.readLine();
            if (readLine == null) {
                return arrayList;
            }
            if (readLine.length() != 0 && readLine.charAt(0) != '#') {
                String[] split = readLine.split(SimpleComparison.EQUAL_TO_OPERATION);
                if (split.length != 2) {
                    PrintStream printStream2 = System.err;
                    printStream2.println("Line is not in the format of 'pattern = level': " + readLine);
                } else {
                    try {
                        arrayList.add(new PatternLevel(Pattern.compile(split[0].trim()), Log.Level.valueOf(split[1].trim())));
                    } catch (IllegalArgumentException unused) {
                        PrintStream printStream3 = System.err;
                        printStream3.println("Level '" + split[1] + "' was not found");
                    }
                }
            }
        }
    }

    public static void openLogFile(String str) {
        if (str == null) {
            printStream = System.out;
            return;
        }
        try {
            printStream = new PrintStream(new File(str));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Log file " + str + " was not found", e);
        }
    }

    private void printMessage(Log.Level level, String str, Throwable th) {
        if (!isLevelEnabled(level)) {
            return;
        }
        StringBuilder sb = new StringBuilder(128);
        sb.append(dateFormatThreadLocal.get().format(new Date()));
        sb.append(" [");
        sb.append(level.name());
        sb.append("] ");
        sb.append(this.className);
        sb.append(' ');
        sb.append(str);
        printStream.println(sb.toString());
        if (th == null) {
            return;
        }
        th.printStackTrace(printStream);
    }

    public static List<PatternLevel> readLevelResourceFile(InputStream inputStream) {
        try {
            if (inputStream != null) {
                try {
                    List<PatternLevel> configureClassLevels = configureClassLevels(inputStream);
                    try {
                        inputStream.close();
                        return configureClassLevels;
                    } catch (IOException unused) {
                        return configureClassLevels;
                    }
                } catch (IOException e) {
                    PrintStream printStream2 = System.err;
                    StringBuilder sb = new StringBuilder();
                    sb.append("IO exception reading the log properties file '/ormliteLocalLog.properties': ");
                    sb.append(e);
                    printStream2.println(sb.toString());
                    try {
                        inputStream.close();
                    } catch (IOException unused2) {
                    }
                }
            }
            return null;
        } catch (Throwable th) {
            try {
                inputStream.close();
            } catch (IOException unused3) {
            }
            throw th;
        }
    }

    public void flush() {
        printStream.flush();
    }

    @Override // com.j256.ormlite.logger.Log
    public boolean isLevelEnabled(Log.Level level) {
        return this.level.isEnabled(level);
    }

    @Override // com.j256.ormlite.logger.Log
    public void log(Log.Level level, String str) {
        printMessage(level, str, null);
    }

    @Override // com.j256.ormlite.logger.Log
    public void log(Log.Level level, String str, Throwable th) {
        printMessage(level, str, th);
    }
}
