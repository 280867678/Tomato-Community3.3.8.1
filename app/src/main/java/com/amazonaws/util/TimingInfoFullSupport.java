package com.amazonaws.util;

import com.amazonaws.logging.Log;
import com.amazonaws.logging.LogFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
class TimingInfoFullSupport extends TimingInfo {
    private final Map<String, List<TimingInfo>> subMeasurementsByName = new HashMap();
    private final Map<String, Number> countersByName = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public TimingInfoFullSupport(Long l, long j, Long l2) {
        super(l, j, l2);
    }

    @Override // com.amazonaws.util.TimingInfo
    public void addSubMeasurement(String str, TimingInfo timingInfo) {
        List<TimingInfo> list = this.subMeasurementsByName.get(str);
        if (list == null) {
            list = new ArrayList<>();
            this.subMeasurementsByName.put(str, list);
        }
        if (timingInfo.isEndTimeKnown()) {
            list.add(timingInfo);
            return;
        }
        Log log = LogFactory.getLog(TimingInfoFullSupport.class);
        log.debug("Skip submeasurement timing info with no end time for " + str);
    }

    @Override // com.amazonaws.util.TimingInfo
    public Map<String, List<TimingInfo>> getSubMeasurementsByName() {
        return this.subMeasurementsByName;
    }

    public Number getCounter(String str) {
        return this.countersByName.get(str);
    }

    @Override // com.amazonaws.util.TimingInfo
    public Map<String, Number> getAllCounters() {
        return this.countersByName;
    }

    @Override // com.amazonaws.util.TimingInfo
    public void setCounter(String str, long j) {
        this.countersByName.put(str, Long.valueOf(j));
    }

    @Override // com.amazonaws.util.TimingInfo
    public void incrementCounter(String str) {
        Number counter = getCounter(str);
        setCounter(str, (counter != null ? counter.intValue() : 0) + 1);
    }
}
