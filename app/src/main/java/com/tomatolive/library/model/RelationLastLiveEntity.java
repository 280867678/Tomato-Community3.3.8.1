package com.tomatolive.library.model;

import com.blankj.utilcode.util.TimeUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;

/* loaded from: classes3.dex */
public class RelationLastLiveEntity {
    public String createTime;
    public String relationStartLiveId;
    public String relationStartLiveTag;
    public String relationStartLiveTopic;

    public String getCreateTime() {
        return TimeUtils.millis2String(NumberUtils.string2long(this.createTime) * 1000, DateUtils.C_TIME_PATTON_DEFAULT_2);
    }
}
