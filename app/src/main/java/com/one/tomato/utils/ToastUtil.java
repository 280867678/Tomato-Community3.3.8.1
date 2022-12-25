package com.one.tomato.utils;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.broccoli.p150bh.R;
import com.one.tomato.mvp.base.BaseApplication;

/* loaded from: classes3.dex */
public class ToastUtil {
    private static Context mContext = BaseApplication.getApplication();
    private static Toast mToast;
    private static RelativeLayout rl_toast_bg;
    private static TextView toastText;

    public static void showCenterToast(String str, int i) {
        try {
            if (TextUtils.isEmpty(str)) {
                return;
            }
            if (mToast == null) {
                mToast = new Toast(mContext);
                mToast.setView(LayoutInflater.from(mContext).inflate(R.layout.toast_custom_layout, (ViewGroup) null, false));
            }
            if (rl_toast_bg == null) {
                rl_toast_bg = (RelativeLayout) mToast.getView().findViewById(R.id.rl_toast_bg);
            }
            rl_toast_bg.setBackground(mContext.getResources().getDrawable(R.drawable.common_shape_solid_corner5_black));
            if (toastText == null) {
                toastText = (TextView) mToast.getView().findViewById(R.id.toast_text);
            }
            toastText.setText(str);
            mToast.setDuration(i);
            mToast.setGravity(17, 0, 0);
            mToast.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showCenterToast(int i) {
        showCenterToast(AppUtil.getString(i), 0);
    }

    public static void showCenterToast(String str) {
        showCenterToast(str, 0);
    }

    public static void destroy() {
        if (mToast != null) {
            mToast = null;
        }
        if (rl_toast_bg != null) {
            rl_toast_bg = null;
        }
        if (toastText != null) {
            toastText = null;
        }
    }
}
