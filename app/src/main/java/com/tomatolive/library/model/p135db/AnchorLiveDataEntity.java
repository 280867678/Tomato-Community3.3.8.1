package com.tomatolive.library.model.p135db;

/* renamed from: com.tomatolive.library.model.db.AnchorLiveDataEntity */
/* loaded from: classes3.dex */
public class AnchorLiveDataEntity extends BaseDBEntity {
    public String anchorId;
    public String appId;
    public String barrageNum;
    public String coinNum;
    public long endTime;
    public String expGrade;
    public String liveId;
    public String nickname;
    public long startTime;
    public String tag;
    public String viewerCount;

    public String toString() {
        return "AnchorLiveDataEntity{anchorId='" + this.anchorId + "', tag='" + this.tag + "', expGrade='" + this.expGrade + "', nickname='" + this.nickname + "', liveId='" + this.liveId + "', endTime=" + this.endTime + ", startTime=" + this.startTime + ", coinNum='" + this.coinNum + "', barrageNum='" + this.barrageNum + "'}";
    }
}
