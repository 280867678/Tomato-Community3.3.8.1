package com.one.tomato.entity.p079db;

import com.one.tomato.utils.FileUtil;
import java.io.Serializable;
import java.util.Objects;
import org.litepal.crud.LitePalSupport;

/* renamed from: com.one.tomato.entity.db.VideoDownload */
/* loaded from: classes3.dex */
public class VideoDownload extends LitePalSupport implements Serializable {
    private static final long serialVersionUID = 1069535693075176088L;
    private String cover;
    private boolean isChecked;
    private int memberId;
    private String postId;
    private float progress;
    private String size;
    private long speed;
    private int state = 0;
    private String title;
    private String url;
    private int videoView;

    public int getVideoView() {
        return this.videoView;
    }

    public void setVideoView(int i) {
        this.videoView = i;
    }

    public VideoDownload(String str) {
        this.url = str;
    }

    public VideoDownload(String str, String str2, String str3) {
        this.url = str;
        this.cover = str2;
        this.title = str3;
    }

    public String getFormatSpeed() {
        if (this.speed == 0) {
            return "10KB/s";
        }
        return FileUtil.formatFileSize(this.speed) + "/s";
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getCover() {
        return this.cover;
    }

    public void setCover(String str) {
        this.cover = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getPostId() {
        return this.postId;
    }

    public void setPostId(String str) {
        this.postId = str;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        this.state = i;
    }

    public long getSpeed() {
        return this.speed;
    }

    public void setSpeed(long j) {
        this.speed = j;
    }

    public float getProgress() {
        return this.progress;
    }

    public void setProgress(float f) {
        this.progress = f;
    }

    public String getSize() {
        return this.size;
    }

    public void setSize(String str) {
        this.size = str;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public int getMemberId() {
        return this.memberId;
    }

    public void setMemberId(int i) {
        this.memberId = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && VideoDownload.class == obj.getClass()) {
            return Objects.equals(this.url, ((VideoDownload) obj).url);
        }
        return false;
    }

    public int hashCode() {
        return Objects.hash(this.url);
    }
}
