package com.zzz.ipfssdk.sdkReport.Dao;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "ReportItemJsonWrapper")
/* loaded from: classes4.dex */
public class ReportItemJsonWrapper {
    @DatabaseField
    public long curTimeStamp;
    @DatabaseField(generatedId = true)

    /* renamed from: id */
    public int f5977id;
    @DatabaseField
    public String playId;
    @DatabaseField
    public String tempEncodeData;
    @DatabaseField
    public int type;

    public ReportItemJsonWrapper() {
    }

    public ReportItemJsonWrapper(String str, int i, long j, String str2) {
        this.playId = str;
        this.type = i;
        this.curTimeStamp = j;
        this.tempEncodeData = str2;
    }

    public long getCurTimeStamp() {
        return this.curTimeStamp;
    }

    public int getId() {
        return this.f5977id;
    }

    public String getPlayId() {
        return this.playId;
    }

    public String getTempEncodeData() {
        return this.tempEncodeData;
    }

    public int getType() {
        return this.type;
    }

    public void setCurTimeStamp(long j) {
        this.curTimeStamp = j;
    }

    public void setId(int i) {
        this.f5977id = i;
    }

    public void setPlayId(String str) {
        this.playId = str;
    }

    public void setTempEncodeData(String str) {
        this.tempEncodeData = str;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String toString() {
        return "ReportItemJsonWrapper{id=" + this.f5977id + ", curTimeStamp=" + this.curTimeStamp + ", type=" + this.type + ", playId='" + this.playId + "'}";
    }
}
