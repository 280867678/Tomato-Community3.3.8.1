package com.one.tomato.utils.p088tt;

import java.io.Serializable;
import java.util.List;

/* renamed from: com.one.tomato.utils.tt.UploadVideoInfo */
/* loaded from: classes3.dex */
public class UploadVideoInfo implements Serializable {
    private String coverHorizontal;
    private String coverVertical;
    private String filePath;
    private String hash;
    private MimeType mimeType;
    private List<PartTagBean> partETags;
    private String pathKey;
    private SplitFileInfo splitFileInfo;
    private String suffix;
    private String uploadId;
    private String title = "";
    private String info = "";
    private String code = "";
    private String actor = "";
    private int status = -1;
    private int time = -1;

    public List<PartTagBean> getPartETags() {
        return this.partETags;
    }

    public void setPartETags(List<PartTagBean> list) {
        this.partETags = list;
    }

    public String getUploadId() {
        return this.uploadId;
    }

    public void setUploadId(String str) {
        this.uploadId = str;
    }

    public String getSuffix() {
        return this.suffix;
    }

    public void setSuffix(String str) {
        this.suffix = str;
    }

    public String getPathKey() {
        return this.pathKey;
    }

    public void setPathKey(String str) {
        this.pathKey = str;
    }

    public int getTime() {
        return this.time;
    }

    public void setTime(int i) {
        this.time = i;
    }

    public String getHash() {
        return this.hash;
    }

    public void setHash(String str) {
        this.hash = str;
    }

    public String getCoverHorizontal() {
        return this.coverHorizontal;
    }

    public void setCoverHorizontal(String str) {
        this.coverHorizontal = str;
    }

    public String getCoverVertical() {
        return this.coverVertical;
    }

    public void setCoverVertical(String str) {
        this.coverVertical = str;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int i) {
        this.status = i;
    }

    public SplitFileInfo getSplitFileInfo() {
        return this.splitFileInfo;
    }

    public void setSplitFileInfo(SplitFileInfo splitFileInfo) {
        this.splitFileInfo = splitFileInfo;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setFilePath(String str) {
        this.filePath = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getInfo() {
        return this.info;
    }

    public void setInfo(String str) {
        this.info = str;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getActor() {
        return this.actor;
    }

    public void setActor(String str) {
        this.actor = str;
    }

    public MimeType getMimeType() {
        return this.mimeType;
    }

    public void setMimeType(MimeType mimeType) {
        this.mimeType = mimeType;
    }

    public String toString() {
        return "UploadVideoInfo{filePath='" + this.filePath + "', title='" + this.title + "', info='" + this.info + "', code='" + this.code + "', actor='" + this.actor + "', coverHorizontal='" + this.coverHorizontal + "', coverVertical='" + this.coverVertical + "', status=" + this.status + ", hash='" + this.hash + "', time=" + this.time + ", suffix='" + this.suffix + "', pathKey='" + this.pathKey + "', splitFileInfo=" + this.splitFileInfo + ", uploadId='" + this.uploadId + "', partETags=" + this.partETags + '}';
    }

    public String print() {
        return "\n源文件路径：" + this.filePath + "\n标题：" + this.title + "\n简介：" + this.info + "\n番号：" + this.code + "\n演员：" + this.actor + "\n后台横屏封面路径：" + this.coverHorizontal + "\n后台竖屏封面路径：" + this.coverVertical + "\n状态：" + this.status + "\nhash：" + this.hash + "\n时间：" + this.time + "\n后缀：" + this.suffix + "\nuploadId：" + this.uploadId + "\n";
    }
}
