package com.sensorsdata.analytics.android.sdk.visual;

import android.annotation.SuppressLint;
import android.os.Build;
import android.view.View;
import android.view.ViewParent;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import com.sensorsdata.analytics.android.sdk.util.ViewUtil;

/* loaded from: classes3.dex */
public class VisualUtil {
    public static int getVisibility(View view) {
        if (view instanceof Spinner) {
            return 8;
        }
        return view.getVisibility();
    }

    @SuppressLint({"NewApi"})
    public static boolean isSupportElementContent(View view) {
        return !(view instanceof SeekBar) && !(view instanceof RatingBar) && !(view instanceof Switch);
    }

    public static boolean isForbiddenClick(View view) {
        if ((view instanceof WebView) || ViewUtil.instanceOfX5WebView(view) || (view instanceof AdapterView)) {
            return true;
        }
        if (!(view instanceof TextView)) {
            return false;
        }
        TextView textView = (TextView) view;
        return Build.VERSION.SDK_INT >= 15 && textView.isTextSelectable() && !textView.hasOnClickListeners();
    }

    public static boolean isSupportClick(View view) {
        ViewParent parent = view.getParent();
        return (parent instanceof AdapterView) || ViewUtil.instanceOfRecyclerView(parent) || (view instanceof RatingBar) || (view instanceof SeekBar);
    }
}
