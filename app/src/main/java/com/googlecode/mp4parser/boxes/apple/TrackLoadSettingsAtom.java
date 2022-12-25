package com.googlecode.mp4parser.boxes.apple;

import com.googlecode.mp4parser.AbstractBox;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class TrackLoadSettingsAtom extends AbstractBox {
    public static final String TYPE = "load";
    int defaultHints;
    int preloadDuration;
    int preloadFlags;
    int preloadStartTime;

    @Override // com.googlecode.mp4parser.AbstractBox
    protected long getContentSize() {
        return 16L;
    }

    public TrackLoadSettingsAtom() {
        super(TYPE);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void getContent(ByteBuffer byteBuffer) {
        byteBuffer.putInt(this.preloadStartTime);
        byteBuffer.putInt(this.preloadDuration);
        byteBuffer.putInt(this.preloadFlags);
        byteBuffer.putInt(this.defaultHints);
    }

    @Override // com.googlecode.mp4parser.AbstractBox
    protected void _parseDetails(ByteBuffer byteBuffer) {
        this.preloadStartTime = byteBuffer.getInt();
        this.preloadDuration = byteBuffer.getInt();
        this.preloadFlags = byteBuffer.getInt();
        this.defaultHints = byteBuffer.getInt();
    }

    public int getPreloadStartTime() {
        return this.preloadStartTime;
    }

    public void setPreloadStartTime(int i) {
        this.preloadStartTime = i;
    }

    public int getPreloadDuration() {
        return this.preloadDuration;
    }

    public void setPreloadDuration(int i) {
        this.preloadDuration = i;
    }

    public int getPreloadFlags() {
        return this.preloadFlags;
    }

    public void setPreloadFlags(int i) {
        this.preloadFlags = i;
    }

    public int getDefaultHints() {
        return this.defaultHints;
    }

    public void setDefaultHints(int i) {
        this.defaultHints = i;
    }
}
