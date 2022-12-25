package com.amazonaws.util;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class TimingInfo {
    private Long endTimeNano;
    private final long startTimeNano;

    public void addSubMeasurement(String str, TimingInfo timingInfo) {
    }

    public void incrementCounter(String str) {
    }

    public void setCounter(String str, long j) {
    }

    public static TimingInfo startTiming() {
        return new TimingInfo(Long.valueOf(System.currentTimeMillis()), System.nanoTime(), null);
    }

    public static TimingInfo startTimingFullSupport() {
        return new TimingInfoFullSupport(Long.valueOf(System.currentTimeMillis()), System.nanoTime(), null);
    }

    public static TimingInfo startTimingFullSupport(long j) {
        return new TimingInfoFullSupport(null, j, null);
    }

    public static TimingInfo unmodifiableTimingInfo(long j, Long l) {
        return new TimingInfoUnmodifiable(null, j, l);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public TimingInfo(Long l, long j, Long l2) {
        this.startTimeNano = j;
        this.endTimeNano = l2;
    }

    public final long getStartTimeNano() {
        return this.startTimeNano;
    }

    public final long getEndTimeNano() {
        Long l = this.endTimeNano;
        if (l == null) {
            return -1L;
        }
        return l.longValue();
    }

    @Deprecated
    public final double getTimeTakenMillis() {
        Double timeTakenMillisIfKnown = getTimeTakenMillisIfKnown();
        if (timeTakenMillisIfKnown == null) {
            return -1.0d;
        }
        return timeTakenMillisIfKnown.doubleValue();
    }

    public final Double getTimeTakenMillisIfKnown() {
        if (isEndTimeKnown()) {
            return Double.valueOf(durationMilliOf(this.startTimeNano, this.endTimeNano.longValue()));
        }
        return null;
    }

    public static double durationMilliOf(long j, long j2) {
        return TimeUnit.NANOSECONDS.toMicros(j2 - j) / 1000.0d;
    }

    public final boolean isEndTimeKnown() {
        return this.endTimeNano != null;
    }

    public final String toString() {
        return String.valueOf(getTimeTakenMillis());
    }

    public TimingInfo endTiming() {
        this.endTimeNano = Long.valueOf(System.nanoTime());
        return this;
    }

    public Map<String, List<TimingInfo>> getSubMeasurementsByName() {
        return Collections.emptyMap();
    }

    public Map<String, Number> getAllCounters() {
        return Collections.emptyMap();
    }
}
