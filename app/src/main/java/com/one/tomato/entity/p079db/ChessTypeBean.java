package com.one.tomato.entity.p079db;

import com.google.gson.annotations.SerializedName;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.util.ArrayList;

/* renamed from: com.one.tomato.entity.db.ChessTypeBean */
/* loaded from: classes3.dex */
public class ChessTypeBean {
    @SerializedName(DatabaseFieldConfigLoader.FIELD_NAME_ID)
    private int chessId;
    private int countOfUpHost;
    private String createTime;
    private ArrayList<ChessTypeSubBean> data;
    private String icon;
    private boolean isSelect;
    private int sort;
    private int status;
    private String title;
    private String titleEn;
    private String titleTw;
    private int type;
    private String updateTime;

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String str) {
        this.createTime = str;
    }

    public String getIcon() {
        return this.icon;
    }

    public void setIcon(String str) {
        this.icon = str;
    }

    public int getChessId() {
        return this.chessId;
    }

    public void setChessId(int i) {
        this.chessId = i;
    }

    public int getSort() {
        return this.sort;
    }

    public void setSort(int i) {
        this.sort = i;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getTitleEn() {
        return this.titleEn;
    }

    public void setTitleEn(String str) {
        this.titleEn = str;
    }

    public String getTitleTw() {
        return this.titleTw;
    }

    public void setTitleTw(String str) {
        this.titleTw = str;
    }

    public int getType() {
        return this.type;
    }

    public void setType(int i) {
        this.type = i;
    }

    public String getUpdateTime() {
        return this.updateTime;
    }

    public void setUpdateTime(String str) {
        this.updateTime = str;
    }

    public boolean isSelect() {
        return this.isSelect;
    }

    public void setSelect(boolean z) {
        this.isSelect = z;
    }

    public ArrayList<ChessTypeSubBean> getData() {
        return this.data;
    }

    public void setData(ArrayList<ChessTypeSubBean> arrayList) {
        this.data = arrayList;
    }

    public int getCountOfUpHost() {
        return this.countOfUpHost;
    }

    public void setCountOfUpHost(int i) {
        this.countOfUpHost = i;
    }
}
