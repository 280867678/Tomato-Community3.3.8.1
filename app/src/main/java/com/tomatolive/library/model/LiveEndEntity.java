package com.tomatolive.library.model;

import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class LiveEndEntity extends AnchorEntity {
    public String endTime;
    public String herald;
    public String maxPopularity = "0";
    public String publishTime;
    public String startTime;

    public long getLiveDurationTimeMillis() {
        return (NumberUtils.string2long(this.endTime) * 1000) - (NumberUtils.string2long(this.startTime) * 1000);
    }
}
