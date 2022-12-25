package sj.keyboard.data;

/* loaded from: classes4.dex */
public class EmoticonEntity {
    private String mContent;
    private long mEventType;
    private String mIconUri;

    public long getEventType() {
        return this.mEventType;
    }

    public void setEventType(long j) {
        this.mEventType = j;
    }

    public String getIconUri() {
        return this.mIconUri;
    }

    public void setIconUri(String str) {
        this.mIconUri = str;
    }

    public void setIconUri(int i) {
        this.mIconUri = "" + i;
    }

    public String getContent() {
        return this.mContent;
    }

    public void setContent(String str) {
        this.mContent = str;
    }

    public EmoticonEntity(long j, String str, String str2) {
        this.mEventType = j;
        this.mIconUri = str;
        this.mContent = str2;
    }

    public EmoticonEntity(String str, String str2) {
        this.mIconUri = str;
        this.mContent = str2;
    }

    public EmoticonEntity(String str) {
        this.mContent = str;
    }

    public EmoticonEntity() {
    }
}
