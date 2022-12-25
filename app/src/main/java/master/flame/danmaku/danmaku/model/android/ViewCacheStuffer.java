package master.flame.danmaku.danmaku.model.android;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.SparseArray;
import android.view.View;
import java.util.ArrayList;
import java.util.List;
import master.flame.danmaku.danmaku.model.BaseDanmaku;
import master.flame.danmaku.danmaku.model.android.AndroidDisplayer;
import master.flame.danmaku.danmaku.model.android.ViewCacheStuffer.ViewHolder;

/* loaded from: classes4.dex */
public abstract class ViewCacheStuffer<VH extends ViewHolder> extends BaseCacheStuffer {
    public static final int CACHE_VIEW_TYPE = -3;
    public static final int DRAW_VIEW_TYPE = -3;
    public static final int INVALID_TYPE = -1;
    public static final int MEASURE_VIEW_TYPE = -2;
    private SparseArray<List<VH>> mViewHolderArray = new SparseArray<>();
    private final int mMaximumWidthPixels = -1;
    private final int mMaximumHeightPixels = -1;

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void clearCaches() {
    }

    public int getItemViewType(int i, BaseDanmaku baseDanmaku) {
        return 0;
    }

    public abstract void onBindViewHolder(int i, VH vh, BaseDanmaku baseDanmaku, AndroidDisplayer.DisplayerConfig displayerConfig, TextPaint textPaint);

    /* renamed from: onCreateViewHolder */
    public abstract VH mo6642onCreateViewHolder(int i);

    /* loaded from: classes4.dex */
    public static abstract class ViewHolder {
        protected final View itemView;

        public ViewHolder(View view) {
            if (view == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = view;
        }

        public void measure(int i, int i2) {
            this.itemView.measure(i, i2);
        }

        public int getMeasureWidth() {
            return this.itemView.getMeasuredWidth();
        }

        public int getMeasureHeight() {
            return this.itemView.getMeasuredHeight();
        }

        public void layout(int i, int i2, int i3, int i4) {
            this.itemView.layout(i, i2, i3, i4);
        }

        public void draw(Canvas canvas, AndroidDisplayer.DisplayerConfig displayerConfig) {
            this.itemView.draw(canvas);
        }
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void measure(BaseDanmaku baseDanmaku, TextPaint textPaint, boolean z) {
        int itemViewType = getItemViewType(baseDanmaku.index, baseDanmaku);
        List<VH> list = this.mViewHolderArray.get(itemViewType);
        if (list == null) {
            list = new ArrayList<>();
            list.add(mo6642onCreateViewHolder(itemViewType));
            list.add(mo6642onCreateViewHolder(itemViewType));
            list.add(mo6642onCreateViewHolder(itemViewType));
            this.mViewHolderArray.put(itemViewType, list);
        }
        VH vh = list.get(0);
        onBindViewHolder(itemViewType, vh, baseDanmaku, null, textPaint);
        vh.measure(View.MeasureSpec.makeMeasureSpec(this.mMaximumWidthPixels, Integer.MIN_VALUE), View.MeasureSpec.makeMeasureSpec(this.mMaximumHeightPixels, Integer.MIN_VALUE));
        vh.layout(0, 0, vh.getMeasureWidth(), vh.getMeasureHeight());
        baseDanmaku.paintWidth = vh.getMeasureWidth();
        baseDanmaku.paintHeight = vh.getMeasureHeight();
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void releaseResource(BaseDanmaku baseDanmaku) {
        super.releaseResource(baseDanmaku);
        baseDanmaku.tag = null;
    }

    @Override // master.flame.danmaku.danmaku.model.android.BaseCacheStuffer
    public void drawDanmaku(BaseDanmaku baseDanmaku, Canvas canvas, float f, float f2, boolean z, AndroidDisplayer.DisplayerConfig displayerConfig) {
        VH vh;
        int itemViewType = getItemViewType(baseDanmaku.index, baseDanmaku);
        List<VH> list = this.mViewHolderArray.get(itemViewType);
        boolean z2 = true;
        if (list != null) {
            vh = list.get(z ? 1 : 2);
        } else {
            vh = null;
        }
        if (vh == null) {
            return;
        }
        displayerConfig.definePaintParams(z);
        TextPaint paint = displayerConfig.getPaint(baseDanmaku, z);
        displayerConfig.applyPaintConfig(baseDanmaku, paint, false);
        onBindViewHolder(itemViewType, vh, baseDanmaku, displayerConfig, paint);
        vh.measure(View.MeasureSpec.makeMeasureSpec(Math.round(baseDanmaku.paintWidth), 1073741824), View.MeasureSpec.makeMeasureSpec(Math.round(baseDanmaku.paintHeight), 1073741824));
        if (!z) {
            canvas.save();
            canvas.translate(f, f2);
        } else {
            z2 = false;
        }
        if (baseDanmaku.underlineColor != 0) {
            Paint underlinePaint = displayerConfig.getUnderlinePaint(baseDanmaku);
            float f3 = (baseDanmaku.paintHeight + f2) - displayerConfig.UNDERLINE_HEIGHT;
            canvas.drawLine(f, f3, f + baseDanmaku.paintWidth, f3, underlinePaint);
        }
        if (baseDanmaku.borderColor != 0) {
            canvas.drawRect(f, f2, f + baseDanmaku.paintWidth, f2 + baseDanmaku.paintHeight, displayerConfig.getBorderPaint(baseDanmaku));
        }
        vh.layout(0, 0, (int) baseDanmaku.paintWidth, (int) baseDanmaku.paintHeight);
        vh.draw(canvas, displayerConfig);
        if (!z2) {
            return;
        }
        canvas.restore();
    }
}
