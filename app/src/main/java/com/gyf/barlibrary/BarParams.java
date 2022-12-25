package com.gyf.barlibrary;

import android.database.ContentObserver;
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
    public KeyboardPatch keyboardPatch;
    public View navigationBarView;
    public ContentObserver navigationStatusObserver;
    public OnKeyboardListener onKeyboardListener;
    public View statusBarView;
    public View statusBarViewByHeight;
    public int titleBarHeight;
    public int titleBarPaddingTopHeight;
    public View titleBarView;
    @ColorInt
    public int statusBarColor = 0;
    @ColorInt
    public int navigationBarColor = ViewCompat.MEASURED_STATE_MASK;
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    public float statusBarAlpha = 0.0f;
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    float navigationBarAlpha = 0.0f;
    public boolean fullScreen = false;
    public boolean fullScreenTemp = this.fullScreen;
    public BarHide barHide = BarHide.FLAG_SHOW_BAR;
    public boolean darkFont = false;
    public boolean statusBarFlag = true;
    @ColorInt
    public int statusBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    @ColorInt
    public int navigationBarColorTransform = ViewCompat.MEASURED_STATE_MASK;
    public Map<View, Map<Integer, Integer>> viewMap = new HashMap();
    @FloatRange(from = 0.0d, m5592to = ConstantUtils.COMPONENTS_HEIGHT_PROPORTION)
    public float viewAlpha = 0.0f;
    public boolean fits = false;
    public boolean isSupportActionBar = false;
    public boolean keyboardEnable = false;
    public int keyboardMode = 18;
    public boolean navigationBarEnable = true;
    public boolean navigationBarWithKitkatEnable = true;
    public boolean systemWindows = false;

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: clone */
    public BarParams m6323clone() {
        try {
            return (BarParams) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }
}
