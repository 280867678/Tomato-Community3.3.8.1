package master.flame.danmaku.controller;

import master.flame.danmaku.danmaku.model.AbsDisplayer;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.parser.BaseDanmakuParser;
import master.flame.danmaku.danmaku.renderer.IRenderer;

/* loaded from: classes4.dex */
public interface IDrawTask {

    /* loaded from: classes4.dex */
    public interface TaskListener {
        void onDanmakuAdd(BaseDanmaku baseDanmaku);

        void onDanmakuConfigChanged();

        void onDanmakuShown(BaseDanmaku baseDanmaku);

        void onDanmakusDrawingFinished();

        void ready();
    }

    void addDanmaku(BaseDanmaku baseDanmaku);

    void clearDanmakusOnScreen(long j);

    IRenderer.RenderingState draw(AbsDisplayer absDisplayer);

    IDanmakus getVisibleDanmakusOnTime(long j);

    void onPlayStateChanged(int i);

    void prepare();

    void quit();

    void requestClear();

    void requestClearRetainer();

    void requestHide();

    void requestRender();

    void requestSync(long j, long j2, long j3);

    void seek(long j);

    void setParser(BaseDanmakuParser baseDanmakuParser);

    void start();
}
