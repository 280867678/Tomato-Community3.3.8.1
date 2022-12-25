package master.flame.danmaku.controller;

import master.flame.danmaku.danmaku.model.IDanmakus;

/* loaded from: classes4.dex */
public interface IDanmakuView {

    /* loaded from: classes4.dex */
    public interface OnDanmakuClickListener {
        boolean onDanmakuClick(IDanmakus iDanmakus);

        boolean onDanmakuLongClick(IDanmakus iDanmakus);

        boolean onViewClick(IDanmakuView iDanmakuView);
    }

    IDanmakus getCurrentVisibleDanmakus();

    OnDanmakuClickListener getOnDanmakuClickListener();

    float getXOff();

    float getYOff();

    void setOnDanmakuClickListener(OnDanmakuClickListener onDanmakuClickListener);
}
