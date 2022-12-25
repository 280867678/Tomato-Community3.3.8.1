package master.flame.danmaku.p144ui.widget;

import android.graphics.RectF;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import master.flame.danmaku.controller.IDanmakuView;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.IDanmakus;
import master.flame.danmaku.danmaku.model.android.Danmakus;

/* renamed from: master.flame.danmaku.ui.widget.DanmakuTouchHelper */
/* loaded from: classes4.dex */
public class DanmakuTouchHelper {
    private IDanmakuView danmakuView;
    private final GestureDetector mTouchDelegate;
    private float mXOff;
    private float mYOff;
    private final GestureDetector.OnGestureListener mOnGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: master.flame.danmaku.ui.widget.DanmakuTouchHelper.1
        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public boolean onDown(MotionEvent motionEvent) {
            if (DanmakuTouchHelper.this.danmakuView == null || DanmakuTouchHelper.this.danmakuView.getOnDanmakuClickListener() == null) {
                return false;
            }
            DanmakuTouchHelper danmakuTouchHelper = DanmakuTouchHelper.this;
            danmakuTouchHelper.mXOff = danmakuTouchHelper.danmakuView.getXOff();
            DanmakuTouchHelper danmakuTouchHelper2 = DanmakuTouchHelper.this;
            danmakuTouchHelper2.mYOff = danmakuTouchHelper2.danmakuView.getYOff();
            return true;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnDoubleTapListener
        public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
            IDanmakus iDanmakus = DanmakuTouchHelper.this.touchHitDanmaku(motionEvent.getX(), motionEvent.getY());
            boolean z = false;
            if (iDanmakus != null && !iDanmakus.isEmpty()) {
                z = DanmakuTouchHelper.this.performDanmakuClick(iDanmakus, false);
            }
            return !z ? DanmakuTouchHelper.this.performViewClick() : z;
        }

        @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
        public void onLongPress(MotionEvent motionEvent) {
            if (DanmakuTouchHelper.this.danmakuView.getOnDanmakuClickListener() == null) {
                return;
            }
            DanmakuTouchHelper danmakuTouchHelper = DanmakuTouchHelper.this;
            danmakuTouchHelper.mXOff = danmakuTouchHelper.danmakuView.getXOff();
            DanmakuTouchHelper danmakuTouchHelper2 = DanmakuTouchHelper.this;
            danmakuTouchHelper2.mYOff = danmakuTouchHelper2.danmakuView.getYOff();
            IDanmakus iDanmakus = DanmakuTouchHelper.this.touchHitDanmaku(motionEvent.getX(), motionEvent.getY());
            if (iDanmakus == null || iDanmakus.isEmpty()) {
                return;
            }
            DanmakuTouchHelper.this.performDanmakuClick(iDanmakus, true);
        }
    };
    private RectF mDanmakuBounds = new RectF();

    private DanmakuTouchHelper(IDanmakuView iDanmakuView) {
        this.danmakuView = iDanmakuView;
        this.mTouchDelegate = new GestureDetector(((View) iDanmakuView).getContext(), this.mOnGestureListener);
    }

    public static synchronized DanmakuTouchHelper instance(IDanmakuView iDanmakuView) {
        DanmakuTouchHelper danmakuTouchHelper;
        synchronized (DanmakuTouchHelper.class) {
            danmakuTouchHelper = new DanmakuTouchHelper(iDanmakuView);
        }
        return danmakuTouchHelper;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        return this.mTouchDelegate.onTouchEvent(motionEvent);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performDanmakuClick(IDanmakus iDanmakus, boolean z) {
        IDanmakuView.OnDanmakuClickListener onDanmakuClickListener = this.danmakuView.getOnDanmakuClickListener();
        if (onDanmakuClickListener != null) {
            if (z) {
                return onDanmakuClickListener.onDanmakuLongClick(iDanmakus);
            }
            return onDanmakuClickListener.onDanmakuClick(iDanmakus);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean performViewClick() {
        IDanmakuView.OnDanmakuClickListener onDanmakuClickListener = this.danmakuView.getOnDanmakuClickListener();
        if (onDanmakuClickListener != null) {
            return onDanmakuClickListener.onViewClick(this.danmakuView);
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IDanmakus touchHitDanmaku(final float f, final float f2) {
        final Danmakus danmakus = new Danmakus();
        this.mDanmakuBounds.setEmpty();
        IDanmakus currentVisibleDanmakus = this.danmakuView.getCurrentVisibleDanmakus();
        if (currentVisibleDanmakus != null && !currentVisibleDanmakus.isEmpty()) {
            currentVisibleDanmakus.forEachSync(new IDanmakus.DefaultConsumer<BaseDanmaku>() { // from class: master.flame.danmaku.ui.widget.DanmakuTouchHelper.2
                @Override // master.flame.danmaku.danmaku.model.IDanmakus.Consumer
                public int accept(BaseDanmaku baseDanmaku) {
                    if (baseDanmaku != null) {
                        DanmakuTouchHelper.this.mDanmakuBounds.set(baseDanmaku.getLeft(), baseDanmaku.getTop(), baseDanmaku.getRight(), baseDanmaku.getBottom());
                        if (!DanmakuTouchHelper.this.mDanmakuBounds.intersect(f - DanmakuTouchHelper.this.mXOff, f2 - DanmakuTouchHelper.this.mYOff, f + DanmakuTouchHelper.this.mXOff, f2 + DanmakuTouchHelper.this.mYOff)) {
                            return 0;
                        }
                        danmakus.addItem(baseDanmaku);
                        return 0;
                    }
                    return 0;
                }
            });
        }
        return danmakus;
    }
}
