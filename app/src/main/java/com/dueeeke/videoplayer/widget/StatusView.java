package com.dueeeke.videoplayer.widget;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.dueeeke.videoplayer.C1228R;

/* loaded from: classes2.dex */
public class StatusView extends LinearLayout {
    private TextView btnAction;
    private float downX;
    private float downY;
    private TextView tvMessage;

    public StatusView(Context context) {
        this(context, null);
    }

    public StatusView(Context context, @Nullable AttributeSet attributeSet) {
        super(context, attributeSet);
        init();
    }

    private void init() {
        View inflate = LayoutInflater.from(getContext()).inflate(C1228R.C1232layout.dkplayer_layout_status_view, this);
        this.tvMessage = (TextView) inflate.findViewById(C1228R.C1231id.message);
        this.btnAction = (TextView) inflate.findViewById(C1228R.C1231id.status_btn);
        setBackgroundResource(17170445);
        setClickable(true);
    }

    public void setMessage(String str) {
        TextView textView = this.tvMessage;
        if (textView != null) {
            textView.setText(str);
        }
    }

    public void setButtonTextAndAction(String str, View.OnClickListener onClickListener) {
        TextView textView = this.btnAction;
        if (textView != null) {
            textView.setText(str);
            this.btnAction.setOnClickListener(onClickListener);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchTouchEvent(MotionEvent motionEvent) {
        int action = motionEvent.getAction();
        if (action == 0) {
            this.downX = motionEvent.getX();
            this.downY = motionEvent.getY();
            getParent().requestDisallowInterceptTouchEvent(true);
        } else if (action == 2) {
            float abs = Math.abs(motionEvent.getX() - this.downX);
            float abs2 = Math.abs(motionEvent.getY() - this.downY);
            if (abs > ViewConfiguration.get(getContext()).getScaledTouchSlop() || abs2 > ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                getParent().requestDisallowInterceptTouchEvent(false);
            }
        }
        return super.dispatchTouchEvent(motionEvent);
    }
}
