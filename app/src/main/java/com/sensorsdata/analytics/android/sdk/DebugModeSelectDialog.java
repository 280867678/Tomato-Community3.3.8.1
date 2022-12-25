package com.sensorsdata.analytics.android.sdk;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.sensorsdata.analytics.android.sdk.SensorsDataAPI;

/* loaded from: classes3.dex */
class DebugModeSelectDialog extends Dialog implements View.OnClickListener {
    private SensorsDataAPI.DebugMode currentDebugMode;
    private OnDebugModeViewClickListener onDebugModeDialogClickListener;

    /* loaded from: classes3.dex */
    interface OnDebugModeViewClickListener {
        void onCancel(Dialog dialog);

        void setDebugMode(Dialog dialog, SensorsDataAPI.DebugMode debugMode);
    }

    DebugModeSelectDialog(Context context, SensorsDataAPI.DebugMode debugMode) {
        super(context);
        this.currentDebugMode = debugMode;
    }

    void setOnDebugModeDialogClickListener(OnDebugModeViewClickListener onDebugModeViewClickListener) {
        this.onDebugModeDialogClickListener = onDebugModeViewClickListener;
    }

    @Override // android.app.Dialog
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        requestWindowFeature(1);
        setContentView(C3089R.C3091layout.sensors_analytics_debug_mode_dialog_content);
        initView();
        Window window = getWindow();
        if (window != null) {
            WindowManager.LayoutParams attributes = window.getAttributes();
            attributes.width = dip2px(getContext(), 270.0f);
            attributes.height = dip2px(getContext(), 240.0f);
            window.setAttributes(attributes);
            GradientDrawable gradientDrawable = new GradientDrawable();
            gradientDrawable.setShape(0);
            gradientDrawable.setColor(-1);
            gradientDrawable.setCornerRadius(dip2px(getContext(), 7.0f));
            window.setBackgroundDrawable(gradientDrawable);
        }
    }

    private void initView() {
        String str;
        ((TextView) findViewById(C3089R.C3090id.sensors_analytics_debug_mode_title)).setText("SDK 调试模式选择");
        TextView textView = (TextView) findViewById(C3089R.C3090id.sensors_analytics_debug_mode_cancel);
        textView.setText("取消");
        textView.setOnClickListener(this);
        TextView textView2 = (TextView) findViewById(C3089R.C3090id.sensors_analytics_debug_mode_only);
        textView2.setText("开启调试模式（不导入数据）");
        textView2.setOnClickListener(this);
        TextView textView3 = (TextView) findViewById(C3089R.C3090id.sensors_analytics_debug_mode_track);
        textView3.setText("开启调试模式（导入数据）");
        textView3.setOnClickListener(this);
        SensorsDataAPI.DebugMode debugMode = this.currentDebugMode;
        if (debugMode == SensorsDataAPI.DebugMode.DEBUG_ONLY) {
            str = "当前为 调试模式（不导入数据）";
        } else {
            str = debugMode == SensorsDataAPI.DebugMode.DEBUG_AND_TRACK ? "当前为 测试模式（导入数据）" : "调试模式已关闭";
        }
        ((TextView) findViewById(C3089R.C3090id.sensors_analytics_debug_mode_message)).setText(str);
        if (Build.VERSION.SDK_INT >= 16) {
            textView.setBackground(getDrawable());
            textView2.setBackground(getDrawable());
            textView3.setBackground(getDrawable());
            return;
        }
        textView.setBackgroundDrawable(getDrawable());
        textView2.setBackgroundDrawable(getDrawable());
        textView3.setBackgroundDrawable(getDrawable());
    }

    private StateListDrawable getDrawable() {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(0);
        gradientDrawable.setColor(Color.parseColor("#dddddd"));
        GradientDrawable gradientDrawable2 = new GradientDrawable();
        gradientDrawable2.setShape(0);
        gradientDrawable2.setColor(-1);
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{16842919}, gradientDrawable);
        stateListDrawable.addState(new int[0], gradientDrawable2);
        return stateListDrawable;
    }

    private int dip2px(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (this.onDebugModeDialogClickListener == null) {
            return;
        }
        int id = view.getId();
        if (id == C3089R.C3090id.sensors_analytics_debug_mode_track) {
            this.onDebugModeDialogClickListener.setDebugMode(this, SensorsDataAPI.DebugMode.DEBUG_AND_TRACK);
        } else if (id == C3089R.C3090id.sensors_analytics_debug_mode_only) {
            this.onDebugModeDialogClickListener.setDebugMode(this, SensorsDataAPI.DebugMode.DEBUG_ONLY);
        } else if (id != C3089R.C3090id.sensors_analytics_debug_mode_cancel) {
        } else {
            this.onDebugModeDialogClickListener.onCancel(this);
        }
    }
}
