package master.flame.danmaku.danmaku.model;

import master.flame.danmaku.danmaku.util.DanmakuUtils;

/* loaded from: classes4.dex */
public class Danmaku extends BaseDanmaku {
    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return 0.0f;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return 0.0f;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j) {
        return null;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return 0.0f;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return 0.0f;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 0;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public boolean isShown() {
        return false;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f, float f2) {
    }

    public Danmaku(CharSequence charSequence) {
        DanmakuUtils.fillText(this, charSequence);
    }
}
