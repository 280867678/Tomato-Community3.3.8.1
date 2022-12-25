package com.tomatolive.library.p136ui.view.divider.decoration;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

/* renamed from: com.tomatolive.library.ui.view.divider.decoration.Y_DividerItemDecoration */
/* loaded from: classes3.dex */
public abstract class Y_DividerItemDecoration extends RecyclerView.ItemDecoration {
    private Context context;
    private Paint mPaint = new Paint(1);

    @Nullable
    public abstract Y_Divider getDivider(int i);

    public Y_DividerItemDecoration(Context context) {
        this.context = context;
        this.mPaint.setStyle(Paint.Style.FILL);
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void onDraw(Canvas canvas, RecyclerView recyclerView, RecyclerView.State state) {
        int childCount = recyclerView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = recyclerView.getChildAt(i);
            Y_Divider divider = getDivider(((RecyclerView.LayoutParams) childAt.getLayoutParams()).getViewLayoutPosition());
            if (divider == null) {
                super.onDraw(canvas, recyclerView, state);
                return;
            }
            if (divider.getLeftSideLine().isHave()) {
                drawChildLeftVertical(childAt, canvas, recyclerView, divider.getLeftSideLine().getColor(), Dp2Px.convert(this.context, divider.getLeftSideLine().getWidthDp()), Dp2Px.convert(this.context, divider.getLeftSideLine().getStartPaddingDp()), Dp2Px.convert(this.context, divider.getLeftSideLine().getEndPaddingDp()));
            }
            if (divider.getTopSideLine().isHave()) {
                drawChildTopHorizontal(childAt, canvas, recyclerView, divider.topSideLine.getColor(), Dp2Px.convert(this.context, divider.getTopSideLine().getWidthDp()), Dp2Px.convert(this.context, divider.getTopSideLine().getStartPaddingDp()), Dp2Px.convert(this.context, divider.getTopSideLine().getEndPaddingDp()));
            }
            if (divider.getRightSideLine().isHave()) {
                drawChildRightVertical(childAt, canvas, recyclerView, divider.getRightSideLine().getColor(), Dp2Px.convert(this.context, divider.getRightSideLine().getWidthDp()), Dp2Px.convert(this.context, divider.getRightSideLine().getStartPaddingDp()), Dp2Px.convert(this.context, divider.getRightSideLine().getEndPaddingDp()));
            }
            if (divider.getBottomSideLine().isHave()) {
                drawChildBottomHorizontal(childAt, canvas, recyclerView, divider.getBottomSideLine().getColor(), Dp2Px.convert(this.context, divider.getBottomSideLine().getWidthDp()), Dp2Px.convert(this.context, divider.getBottomSideLine().getStartPaddingDp()), Dp2Px.convert(this.context, divider.getBottomSideLine().getEndPaddingDp()));
            }
        }
    }

    private void drawChildBottomHorizontal(View view, Canvas canvas, RecyclerView recyclerView, @ColorInt int i, int i2, int i3, int i4) {
        if (i3 <= 0) {
            i3 = -i2;
        }
        int i5 = i4 <= 0 ? i2 : -i4;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int left = (view.getLeft() - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) + i3;
        int right = view.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + i5;
        int bottom = view.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin;
        this.mPaint.setColor(i);
        canvas.drawRect(left, bottom, right, i2 + bottom, this.mPaint);
    }

    private void drawChildTopHorizontal(View view, Canvas canvas, RecyclerView recyclerView, @ColorInt int i, int i2, int i3, int i4) {
        if (i3 <= 0) {
            i3 = -i2;
        }
        int i5 = i4 <= 0 ? i2 : -i4;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int left = (view.getLeft() - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin) + i3;
        int right = view.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin + i5;
        int top2 = view.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin;
        this.mPaint.setColor(i);
        canvas.drawRect(left, top2 - i2, right, top2, this.mPaint);
    }

    private void drawChildLeftVertical(View view, Canvas canvas, RecyclerView recyclerView, @ColorInt int i, int i2, int i3, int i4) {
        if (i3 <= 0) {
            i3 = -i2;
        }
        int i5 = i4 <= 0 ? i2 : -i4;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int top2 = (view.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) + i3;
        int bottom = view.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + i5;
        int left = view.getLeft() - ((ViewGroup.MarginLayoutParams) layoutParams).leftMargin;
        this.mPaint.setColor(i);
        canvas.drawRect(left - i2, top2, left, bottom, this.mPaint);
    }

    private void drawChildRightVertical(View view, Canvas canvas, RecyclerView recyclerView, @ColorInt int i, int i2, int i3, int i4) {
        if (i3 <= 0) {
            i3 = -i2;
        }
        int i5 = i4 <= 0 ? i2 : -i4;
        RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) view.getLayoutParams();
        int top2 = (view.getTop() - ((ViewGroup.MarginLayoutParams) layoutParams).topMargin) + i3;
        int bottom = view.getBottom() + ((ViewGroup.MarginLayoutParams) layoutParams).bottomMargin + i5;
        int right = view.getRight() + ((ViewGroup.MarginLayoutParams) layoutParams).rightMargin;
        this.mPaint.setColor(i);
        canvas.drawRect(right, top2, i2 + right, bottom, this.mPaint);
    }

    @Override // android.support.p005v7.widget.RecyclerView.ItemDecoration
    public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
        Y_Divider divider = getDivider(((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition());
        if (divider == null) {
            divider = new Y_DividerBuilder().create();
        }
        int i = 0;
        int convert = divider.getLeftSideLine().isHave() ? Dp2Px.convert(this.context, divider.getLeftSideLine().getWidthDp()) : 0;
        int convert2 = divider.getTopSideLine().isHave() ? Dp2Px.convert(this.context, divider.getTopSideLine().getWidthDp()) : 0;
        int convert3 = divider.getRightSideLine().isHave() ? Dp2Px.convert(this.context, divider.getRightSideLine().getWidthDp()) : 0;
        if (divider.getBottomSideLine().isHave()) {
            i = Dp2Px.convert(this.context, divider.getBottomSideLine().getWidthDp());
        }
        rect.set(convert, convert2, convert3, i);
    }
}
