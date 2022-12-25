package com.gyf.immersionbar;

import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.p002v4.view.ViewCompat;
import android.view.View;
import com.tomatolive.library.utils.ConstantUtils;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes3.dex */
public class BarParams implements Cloneable {
    @ColorInt
    public int flymeOSStatusBarFontColor;
    OnKeyboardListener onKeyboardListener;
    OnNavigationBarListener onNavigationBarListener;
    public View statusBarView;
    public View titleBarView;
    @ColorInt
    public int statusBarColor = 0;
    @ColorInt
    public int navigationBarColor = ViewCompat.MEASURED_STATE_MASK;
    public int defaultNavigationBarColor = ViewCompat.MEASURED_STATE_MASK;
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    public float statusBarAlpha = 0.0f;
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    public float navigationBarAlpha = 0.0f;
    public boolean fullScreen = false;
    public boolean hideNavigationBar = false;
    public BarHide barHide = BarHide.FLAG_SHOW_BAR;
    public boolean statusBarDarkFont = false;
    public boolean navigationBarDarkIcon = false;
    public boolean autoStatusBarDarkModeEnable = false;
    public boolean autoNavigationBarDarkModeEnable = false;
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    public float autoStatusBarDarkModeAlpha = 0.0f;
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    public float autoNavigationBarDarkModeAlpha = 0.0f;
    public boolean statusBarColorEnabled = true;
    @ColorInt
    public int statusBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    @ColorInt
    public int navigationBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    Map<View, Map<Integer, Integer>> viewMap = new HashMap();
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    public float viewAlpha = 0.0f;
    public boolean fits = false;
    public boolean isSupportActionBar = false;
    public boolean keyboardEnable = false;
    public int keyboardMode = 18;
    public boolean navigationBarEnable = true;
    public boolean navigationBarWithKitkatEnable = true;
    public boolean navigationBarWithEMUI3Enable = true;

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: clone */
    public BarParams m6324clone() {
        try {
            return (BarParams) super.clone();
        } catch (CloneNotSupportedException unused) {
            return null;
        }
    }
}
