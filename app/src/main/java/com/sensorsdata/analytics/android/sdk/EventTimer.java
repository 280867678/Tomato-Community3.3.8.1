package com.sensorsdata.analytics.android.sdk;

import android.os.SystemClock;
import com.tomatolive.library.utils.DateUtils;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

/* loaded from: classes3.dex */
public class EventTimer {
    private long startTime;
    private final TimeUnit timeUnit;
    private boolean isPaused = false;
    private long eventAccumulatedDuration = 0;
    private long endTime = -1;

    /* JADX INFO: Access modifiers changed from: package-private */
    public EventTimer(TimeUnit timeUnit, long j) {
        this.startTime = j;
        this.timeUnit = timeUnit;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005a A[Catch: Exception -> 0x0077, TryCatch #0 {Exception -> 0x0077, blocks: (B:9:0x002c, B:11:0x0032, B:14:0x005a, B:17:0x005f, B:19:0x0034, B:21:0x003c, B:22:0x003f, B:24:0x0047, B:25:0x0049, B:26:0x004b, B:28:0x0051, B:29:0x0072), top: B:5:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:17:0x005f A[Catch: Exception -> 0x0077, TryCatch #0 {Exception -> 0x0077, blocks: (B:9:0x002c, B:11:0x0032, B:14:0x005a, B:17:0x005f, B:19:0x0034, B:21:0x003c, B:22:0x003f, B:24:0x0047, B:25:0x0049, B:26:0x004b, B:28:0x0051, B:29:0x0072), top: B:5:0x0022 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String duration() {
        float f;
        float f2;
        if (this.isPaused) {
            this.endTime = this.startTime;
        } else {
            long j = this.endTime;
            if (j < 0) {
                j = SystemClock.elapsedRealtime();
            }
            this.endTime = j;
        }
        long j2 = (this.endTime - this.startTime) + this.eventAccumulatedDuration;
        try {
            if (j2 < 0 || j2 > DateUtils.ONE_DAY_MILLIONS) {
                return String.valueOf(0);
            }
            if (this.timeUnit != TimeUnit.MILLISECONDS) {
                if (this.timeUnit == TimeUnit.SECONDS) {
                    f2 = ((float) j2) / 1000.0f;
                } else {
                    if (this.timeUnit == TimeUnit.MINUTES) {
                        f = ((float) j2) / 1000.0f;
                    } else if (this.timeUnit == TimeUnit.HOURS) {
                        f = (((float) j2) / 1000.0f) / 60.0f;
                    }
                    f2 = f / 60.0f;
                }
                return f2 >= 0.0f ? String.valueOf(0) : String.format(Locale.CHINA, "%.3f", Float.valueOf(f2));
            }
            f2 = (float) j2;
            if (f2 >= 0.0f) {
            }
        } catch (Exception e) {
            SALog.printStackTrace(e);
            return String.valueOf(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getStartTime() {
        return this.startTime;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setStartTime(long j) {
        this.startTime = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEndTime(long j) {
        this.endTime = j;
    }

    long getEndTime() {
        return this.endTime;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public long getEventAccumulatedDuration() {
        return this.eventAccumulatedDuration;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setEventAccumulatedDuration(long j) {
        this.eventAccumulatedDuration = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setTimerState(boolean z, long j) {
        this.isPaused = z;
        if (z) {
            this.eventAccumulatedDuration = (this.eventAccumulatedDuration + j) - this.startTime;
        }
        this.startTime = j;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isPaused() {
        return this.isPaused;
    }
}
