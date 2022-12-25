package com.mcxtzhang.indexlib.suspension;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.List;

/* loaded from: classes3.dex */
public class SuspensionDecoration extends RecyclerView.ItemDecoration {
    private static int COLOR_TITLE_BG = Color.parseColor("#FFDFDFDF");
    private static int COLOR_TITLE_FONT = Color.parseColor("#FF999999");
    private static int mTitleFontSize;
    private List<? extends ISuspensionInterface> mDatas;
    private int mTitleHeight;
    private int mHeaderViewCount = 0;
    private Paint mPaint = new Paint();
    private Rect mBounds = new Rect();

    public SuspensionDecoration(Context context, List<? extends ISuspensionInterface> list) {
        this.mDatas = list;
        this.mTitleHeight = (int) TypedValue.applyDimension(1, 30.0f, context.getResources().getDisplayMetrics());
        mTitleFontSize = (int) TypedValue.applyDimension(2, 16.0f, context.getResources().getDisplayMetrics());
        this.mPaint.setTextSize(mTitleFontSize);
        this.mPaint.setAntiAlias(true);
        LayoutInflater.from(context);
    }

    public SuspensionDecoration setColorTitleBg(int i) {
        COLOR_TITLE_BG = i;
        return this;
    }

    public SuspensionDecoration setColorTitleFont(int i) {
        COLOR_TITLE_FONT = i;
        return this;
    }

    public SuspensionDecoration setmDatas(List<? extends ISuspensionInterface> list) {
        this.mDatas = list;
        return this;
    }

    public int getHeaderViewCount() {
        return this.mHeaderViewCount;
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        super.onDraw(canvas, recyclerView, state);
        int paddingLeft = recyclerView.getPaddingLeft();
        int width = recyclerView.getWidth() - recyclerView.getPaddingRight();
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) childAt.getLayoutParams();
            int viewLayoutPosition = layoutParams.getViewLayoutPosition() - getHeaderViewCount();
            List<? extends ISuspensionInterface> list = this.mDatas;
            if (list != null && !list.isEmpty() && viewLayoutPosition <= this.mDatas.size() - 1 && viewLayoutPosition >= 0 && this.mDatas.get(viewLayoutPosition).isShowSuspension() && viewLayoutPosition > -1) {
                if (viewLayoutPosition == 0) {
                    drawTitleArea(canvas, paddingLeft, width, childAt, layoutParams, viewLayoutPosition);
                } else if (this.mDatas.get(viewLayoutPosition).getSuspensionTag() != null && !this.mDatas.get(viewLayoutPosition).getSuspensionTag().equals(this.mDatas.get(viewLayoutPosition - 1).getSuspensionTag())) {
                    drawTitleArea(canvas, paddingLeft, width, childAt, layoutParams, viewLayoutPosition);
                }
            }
        }
    }

    private void drawTitleArea(Canvas canvas, int i, int i2, View view, RecyclerView.LayoutParams layoutParams, int i3) {
        this.mPaint.setColor(COLOR_TITLE_BG);
        canvas.drawRect(i, (view.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - this.mTitleHeight, i2, view.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin, this.mPaint);
        this.mPaint.setColor(COLOR_TITLE_FONT);
        this.mPaint.getTextBounds(this.mDatas.get(i3).getSuspensionTag(), 0, this.mDatas.get(i3).getSuspensionTag().length(), this.mBounds);
        canvas.drawText(this.mDatas.get(i3).getSuspensionTag(), view.getPaddingLeft(), (view.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) - ((this.mTitleHeight / 2) - (this.mBounds.height() / 2)), this.mPaint);
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void onDrawOver(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int findFirstVisibleItemPosition = ((LinearLayoutManager) recyclerView.getLayoutManager()).findFirstVisibleItemPosition() - getHeaderViewCount();
        List<? extends ISuspensionInterface> list = this.mDatas;
        if (list == null || list.isEmpty()) {
            return;
        }
        boolean z = true;
        if (findFirstVisibleItemPosition > this.mDatas.size() - 1 || findFirstVisibleItemPosition < 0 || !this.mDatas.get(findFirstVisibleItemPosition).isShowSuspension()) {
            return;
        }
        String suspensionTag = this.mDatas.get(findFirstVisibleItemPosition).getSuspensionTag();
        View view = recyclerView.findViewHolderForLayoutPosition(getHeaderViewCount() + findFirstVisibleItemPosition).itemView;
        int i = findFirstVisibleItemPosition + 1;
        if (i < this.mDatas.size() && suspensionTag != null && !suspensionTag.equals(this.mDatas.get(i).getSuspensionTag())) {
            Log.d("zxt", "onDrawOver() called with: c = [" + view.getTop());
            if (view.getHeight() + view.getTop() < this.mTitleHeight) {
                canvas.save();
                canvas.translate(0.0f, (view.getHeight() + view.getTop()) - this.mTitleHeight);
                this.mPaint.setColor(COLOR_TITLE_BG);
                canvas.drawRect(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(), recyclerView.getRight() - recyclerView.getPaddingRight(), recyclerView.getPaddingTop() + this.mTitleHeight, this.mPaint);
                this.mPaint.setColor(COLOR_TITLE_FONT);
                this.mPaint.getTextBounds(suspensionTag, 0, suspensionTag.length(), this.mBounds);
                int paddingTop = recyclerView.getPaddingTop();
                int i2 = this.mTitleHeight;
                canvas.drawText(suspensionTag, view.getPaddingLeft(), (paddingTop + i2) - ((i2 / 2) - (this.mBounds.height() / 2)), this.mPaint);
                if (z) {
                    return;
                }
                canvas.restore();
                return;
            }
        }
        z = false;
        this.mPaint.setColor(COLOR_TITLE_BG);
        canvas.drawRect(recyclerView.getPaddingLeft(), recyclerView.getPaddingTop(), recyclerView.getRight() - recyclerView.getPaddingRight(), recyclerView.getPaddingTop() + this.mTitleHeight, this.mPaint);
        this.mPaint.setColor(COLOR_TITLE_FONT);
        this.mPaint.getTextBounds(suspensionTag, 0, suspensionTag.length(), this.mBounds);
        int paddingTop2 = recyclerView.getPaddingTop();
        int i22 = this.mTitleHeight;
        canvas.drawText(suspensionTag, view.getPaddingLeft(), (paddingTop2 + i22) - ((i22 / 2) - (this.mBounds.height() / 2)), this.mPaint);
        if (z) {
        }
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        super.getItemOffsets(rect, view, recyclerView, state);
        int viewLayoutPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition() - getHeaderViewCount();
        List<? extends ISuspensionInterface> list = this.mDatas;
        if (list == null || list.isEmpty() || viewLayoutPosition > this.mDatas.size() - 1 || viewLayoutPosition <= -1) {
            return;
        }
        ISuspensionInterface iSuspensionInterface = this.mDatas.get(viewLayoutPosition);
        if (!iSuspensionInterface.isShowSuspension()) {
            return;
        }
        if (viewLayoutPosition == 0) {
            rect.set(0, this.mTitleHeight, 0, 0);
        } else if (iSuspensionInterface.getSuspensionTag() == null || iSuspensionInterface.getSuspensionTag().equals(this.mDatas.get(viewLayoutPosition - 1).getSuspensionTag())) {
        } else {
            rect.set(0, this.mTitleHeight, 0, 0);
        }
    }
}
