package com.one.tomato.thirdpart.recyclerview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

/* loaded from: classes3.dex */
public class ExaminePostRecyclerView extends RecyclerView {
    @Override // android.support.p005v7.widget.RecyclerView, android.view.ViewGroup
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    public ExaminePostRecyclerView(Context context) {
        super(context);
    }

    public ExaminePostRecyclerView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public ExaminePostRecyclerView(Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }

    @Override // android.support.p005v7.widget.RecyclerView, android.view.View
    public boolean onTouchEvent(MotionEvent motionEvent) {
        if (motionEvent.getPointerCount() > 1) {
            return true;
        }
        return super.onTouchEvent(motionEvent);
    }
}
