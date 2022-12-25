package com.tomatolive.library.p136ui.view.emptyview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;

/* renamed from: com.tomatolive.library.ui.view.emptyview.QMTaskListEmptyView */
/* loaded from: classes3.dex */
public class QMTaskListEmptyView extends FrameLayout {
    private int emptyType;

    public QMTaskListEmptyView(Context context, int i) {
        this(context, null, i);
    }

    public QMTaskListEmptyView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, 0, i);
    }

    public QMTaskListEmptyView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i);
        this.emptyType = i2;
        initView();
    }

    private void initView() {
        FrameLayout.inflate(getContext(), R$layout.fq_layout_empty_view_qm_task, this);
        LinearLayout linearLayout = (LinearLayout) findViewById(R$id.ll_hd_task_bg);
        TextView textView = (TextView) findViewById(R$id.tv_task_list);
        TextView textView2 = (TextView) findViewById(R$id.tv_empty_add);
        FrameLayout frameLayout = (FrameLayout) findViewById(R$id.fl_btn_bg);
        switch (this.emptyType) {
            case 57:
                linearLayout.setVisibility(0);
                textView.setVisibility(4);
                frameLayout.setBackgroundResource(R$drawable.fq_qm_primary_btn_frame);
                textView2.setText(R$string.fq_qm_add_new_task);
                textView2.setCompoundDrawablesWithIntrinsicBounds(ContextCompat.getDrawable(getContext(), R$drawable.fq_ic_qm_task_add), (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            case 58:
                linearLayout.setVisibility(4);
                textView.setVisibility(0);
                return;
            case 59:
                linearLayout.setVisibility(0);
                textView.setVisibility(4);
                frameLayout.setBackgroundResource(R$drawable.fq_qm_primary_btn_selector);
                textView2.setText(R$string.fq_qm_send_qm_invitation);
                textView2.setCompoundDrawablesWithIntrinsicBounds((Drawable) null, (Drawable) null, (Drawable) null, (Drawable) null);
                return;
            default:
                return;
        }
    }

    public void setTaskAddListener(View.OnClickListener onClickListener) {
        findViewById(R$id.tv_empty_add).setOnClickListener(onClickListener);
    }
}
