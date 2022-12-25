package com.j256.ormlite.android;

import com.j256.ormlite.logger.Log;
import com.j256.ormlite.logger.LoggerFactory;

/* loaded from: classes3.dex */
public class AndroidLog implements Log {
    public static final String ALL_LOGS_NAME = "ORMLite";
    public static final int MAX_TAG_LENGTH = 23;
    public static final int REFRESH_LEVEL_CACHE_EVERY = 200;
    public String className;
    public final boolean[] levelCache;
    public volatile int levelCacheC = 0;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.j256.ormlite.android.AndroidLog$1 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C21761 {
        public static final /* synthetic */ int[] $SwitchMap$com$j256$ormlite$logger$Log$Level = new int[Log.Level.values().length];

        static {
            try {
                $SwitchMap$com$j256$ormlite$logger$Log$Level[Log.Level.TRACE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$j256$ormlite$logger$Log$Level[Log.Level.DEBUG.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$j256$ormlite$logger$Log$Level[Log.Level.INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$j256$ormlite$logger$Log$Level[Log.Level.WARNING.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$j256$ormlite$logger$Log$Level[Log.Level.ERROR.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$j256$ormlite$logger$Log$Level[Log.Level.FATAL.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public AndroidLog(String str) {
        this.className = LoggerFactory.getSimpleClassName(str);
        int length = this.className.length();
        if (length > 23) {
            this.className = this.className.substring(length - 23, length);
        }
        int i = 0;
        for (Log.Level level : Log.Level.values()) {
            int levelToAndroidLevel = levelToAndroidLevel(level);
            if (levelToAndroidLevel > i) {
                i = levelToAndroidLevel;
            }
        }
        this.levelCache = new boolean[i + 1];
        refreshLevelCache();
    }

    private boolean isLevelEnabledInternal(int i) {
        return android.util.Log.isLoggable(this.className, i) || android.util.Log.isLoggable(ALL_LOGS_NAME, i);
    }

    private int levelToAndroidLevel(Log.Level level) {
        switch (C21761.$SwitchMap$com$j256$ormlite$logger$Log$Level[level.ordinal()]) {
            case 1:
                return 2;
            case 2:
                return 3;
            case 3:
                return 4;
            case 4:
                return 5;
            case 5:
            case 6:
                return 6;
            default:
                return 4;
        }
    }

    private void refreshLevelCache() {
        for (Log.Level level : Log.Level.values()) {
            int levelToAndroidLevel = levelToAndroidLevel(level);
            boolean[] zArr = this.levelCache;
            if (levelToAndroidLevel < zArr.length) {
                zArr[levelToAndroidLevel] = isLevelEnabledInternal(levelToAndroidLevel);
            }
        }
    }

    @Override // com.j256.ormlite.logger.Log
    public boolean isLevelEnabled(Log.Level level) {
        int i = this.levelCacheC + 1;
        this.levelCacheC = i;
        if (i >= 200) {
            refreshLevelCache();
            this.levelCacheC = 0;
        }
        int levelToAndroidLevel = levelToAndroidLevel(level);
        boolean[] zArr = this.levelCache;
        return levelToAndroidLevel < zArr.length ? zArr[levelToAndroidLevel] : isLevelEnabledInternal(levelToAndroidLevel);
    }

    @Override // com.j256.ormlite.logger.Log
    public void log(Log.Level level, String str) {
        int i = C21761.$SwitchMap$com$j256$ormlite$logger$Log$Level[level.ordinal()];
        if (i == 1) {
            android.util.Log.v(this.className, str);
        } else if (i == 2) {
            android.util.Log.d(this.className, str);
        } else if (i == 4) {
            android.util.Log.w(this.className, str);
        } else if (i == 5 || i == 6) {
            android.util.Log.e(this.className, str);
        } else {
            android.util.Log.i(this.className, str);
        }
    }

    @Override // com.j256.ormlite.logger.Log
    public void log(Log.Level level, String str, Throwable th) {
        int i = C21761.$SwitchMap$com$j256$ormlite$logger$Log$Level[level.ordinal()];
        if (i == 1) {
            android.util.Log.v(this.className, str, th);
        } else if (i == 2) {
            android.util.Log.d(this.className, str, th);
        } else if (i == 4) {
            android.util.Log.w(this.className, str, th);
        } else if (i == 5 || i == 6) {
            android.util.Log.e(this.className, str, th);
        } else {
            android.util.Log.i(this.className, str, th);
        }
    }
}
