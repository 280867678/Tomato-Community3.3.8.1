package com.tomatolive.library.model.p135db;

/* renamed from: com.tomatolive.library.model.db.LiveDataEntity */
/* loaded from: classes3.dex */
public class LiveDataEntity extends BaseDBEntity {
    public String anchorId;
    public String appId;
    public long endTime;
    public String expGrade;
    public String liveId;
    public String nickname;
    public long startTime;
    public String tag;
    public String viewerLevel;

    public String toString() {
        return "LiveDataEntity{anchorId='" + this.anchorId + "', tag='" + this.tag + "', expGrade='" + this.expGrade + "', nickname='" + this.nickname + "', liveId='" + this.liveId + "', viewerLevel='" + this.viewerLevel + "', endTime=" + this.endTime + ", startTime=" + this.startTime + '}';
    }
}
