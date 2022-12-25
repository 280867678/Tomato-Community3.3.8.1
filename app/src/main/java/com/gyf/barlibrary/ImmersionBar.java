package com.gyf.barlibrary;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.database.ContentObserver;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.p002v4.graphics.ColorUtils;
import android.support.p002v4.view.GravityCompat;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.FrameLayout;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@TargetApi(19)
/* loaded from: classes3.dex */
public class ImmersionBar {
    private Activity mActivity;
    private String mActivityName;
    private BarParams mBarParams;
    private BarConfig mConfig;
    private ViewGroup mContentView;
    private ViewGroup mDecorView;
    private Dialog mDialog;
    private String mFragmentName;
    private String mImmersionBarName;
    private Window mWindow;
    private static Map<String, BarParams> mMap = new HashMap();
    private static Map<String, BarParams> mTagMap = new HashMap();
    private static Map<String, ArrayList<String>> mTagKeyMap = new HashMap();

    private ImmersionBar(Activity activity) {
        this.mActivity = (Activity) new WeakReference(activity).get();
        this.mWindow = this.mActivity.getWindow();
        this.mActivityName = activity.getClass().getName();
        this.mImmersionBarName = this.mActivityName;
        initParams();
    }

    private void initParams() {
        this.mDecorView = (ViewGroup) this.mWindow.getDecorView();
        this.mContentView = (ViewGroup) this.mDecorView.findViewById(16908290);
        this.mConfig = new BarConfig(this.mActivity);
        if (mMap.get(this.mImmersionBarName) == null) {
            this.mBarParams = new BarParams();
            if (!isEmpty(this.mFragmentName)) {
                if (mMap.get(this.mActivityName) == null) {
                    throw new IllegalArgumentException("在Fragment里使用时，请先在加载Fragment的Activity里初始化！！！");
                }
                if (Build.VERSION.SDK_INT == 19 || OSUtils.isEMUI3_1()) {
                    this.mBarParams.statusBarView = mMap.get(this.mActivityName).statusBarView;
                    this.mBarParams.navigationBarView = mMap.get(this.mActivityName).navigationBarView;
                }
                this.mBarParams.keyboardPatch = mMap.get(this.mActivityName).keyboardPatch;
            }
            mMap.put(this.mImmersionBarName, this.mBarParams);
            return;
        }
        this.mBarParams = mMap.get(this.mImmersionBarName);
    }

    public static ImmersionBar with(@NonNull Activity activity) {
        if (activity == null) {
            throw new IllegalArgumentException("Activity不能为null");
        }
        return new ImmersionBar(activity);
    }

    public ImmersionBar transparentStatusBar() {
        this.mBarParams.statusBarColor = 0;
        return this;
    }

    public ImmersionBar statusBarDarkFont(boolean z) {
        statusBarDarkFont(z, 0.0f);
        return this;
    }

    public ImmersionBar statusBarDarkFont(boolean z, @FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        BarParams barParams = this.mBarParams;
        barParams.darkFont = z;
        if (!z) {
            barParams.flymeOSStatusBarFontColor = 0;
        }
        if (isSupportStatusBarDarkFont()) {
            this.mBarParams.statusBarAlpha = 0.0f;
        } else {
            this.mBarParams.statusBarAlpha = f;
        }
        return this;
    }

    public ImmersionBar statusBarView(View view) {
        if (view == null) {
            throw new IllegalArgumentException("View参数不能为空");
        }
        this.mBarParams.statusBarViewByHeight = view;
        return this;
    }

    public ImmersionBar titleBar(View view) {
        if (view == null) {
            throw new IllegalArgumentException("View参数不能为空");
        }
        titleBar(view, true);
        return this;
    }

    public ImmersionBar titleBar(View view, boolean z) {
        if (view == null) {
            throw new IllegalArgumentException("View参数不能为空");
        }
        BarParams barParams = this.mBarParams;
        barParams.titleBarView = view;
        barParams.statusBarFlag = z;
        setTitleBar();
        return this;
    }

    public ImmersionBar keyboardEnable(boolean z) {
        keyboardEnable(z, 18);
        return this;
    }

    public ImmersionBar keyboardEnable(boolean z, int i) {
        BarParams barParams = this.mBarParams;
        barParams.keyboardEnable = z;
        barParams.keyboardMode = i;
        return this;
    }

    public void init() {
        mMap.put(this.mImmersionBarName, this.mBarParams);
        initBar();
        setStatusBarView();
        transformView();
        keyboardEnable();
        registerEMUI3_x();
    }

    public void destroy() {
        unRegisterEMUI3_x();
        BarParams barParams = this.mBarParams;
        KeyboardPatch keyboardPatch = barParams.keyboardPatch;
        if (keyboardPatch != null) {
            keyboardPatch.disable(barParams.keyboardMode);
            this.mBarParams.keyboardPatch = null;
        }
        if (this.mDecorView != null) {
            this.mDecorView = null;
        }
        if (this.mContentView != null) {
            this.mContentView = null;
        }
        if (this.mConfig != null) {
            this.mConfig = null;
        }
        if (this.mWindow != null) {
            this.mWindow = null;
        }
        if (this.mDialog != null) {
            this.mDialog = null;
        }
        if (this.mActivity != null) {
            this.mActivity = null;
        }
        if (!isEmpty(this.mImmersionBarName)) {
            if (this.mBarParams != null) {
                this.mBarParams = null;
            }
            ArrayList<String> arrayList = mTagKeyMap.get(this.mActivityName);
            if (arrayList != null && arrayList.size() > 0) {
                Iterator<String> it2 = arrayList.iterator();
                while (it2.hasNext()) {
                    mTagMap.remove(it2.next());
                }
                mTagKeyMap.remove(this.mActivityName);
            }
            mMap.remove(this.mImmersionBarName);
        }
    }

    private void initBar() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 19) {
            int i2 = 256;
            if (i >= 21 && !OSUtils.isEMUI3_1()) {
                i2 = setStatusBarDarkFont(initBarAboveLOLLIPOP(256));
                supportActionBar();
            } else {
                initBarBelowLOLLIPOP();
                solveNavigation();
            }
            this.mWindow.getDecorView().setSystemUiVisibility(hideBar(i2));
        }
        if (OSUtils.isMIUI6Later()) {
            setMIUIStatusBarDarkFont(this.mWindow, this.mBarParams.darkFont);
        }
        if (OSUtils.isFlymeOS4Later()) {
            BarParams barParams = this.mBarParams;
            int i3 = barParams.flymeOSStatusBarFontColor;
            if (i3 != 0) {
                FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this.mActivity, i3);
            } else if (Build.VERSION.SDK_INT >= 23) {
            } else {
                FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this.mActivity, barParams.darkFont);
            }
        }
    }

    @RequiresApi(api = 21)
    private int initBarAboveLOLLIPOP(int i) {
        int i2 = i | 1024;
        BarParams barParams = this.mBarParams;
        if (barParams.fullScreen && barParams.navigationBarEnable) {
            i2 |= 512;
        }
        this.mWindow.clearFlags(67108864);
        if (this.mConfig.hasNavigtionBar()) {
            this.mWindow.clearFlags(134217728);
        }
        this.mWindow.addFlags(Integer.MIN_VALUE);
        BarParams barParams2 = this.mBarParams;
        if (barParams2.statusBarFlag) {
            this.mWindow.setStatusBarColor(ColorUtils.blendARGB(barParams2.statusBarColor, barParams2.statusBarColorTransform, barParams2.statusBarAlpha));
        } else {
            this.mWindow.setStatusBarColor(ColorUtils.blendARGB(barParams2.statusBarColor, 0, barParams2.statusBarAlpha));
        }
        BarParams barParams3 = this.mBarParams;
        if (barParams3.navigationBarEnable) {
            this.mWindow.setNavigationBarColor(ColorUtils.blendARGB(barParams3.navigationBarColor, barParams3.navigationBarColorTransform, barParams3.navigationBarAlpha));
        }
        return i2;
    }

    private void initBarBelowLOLLIPOP() {
        this.mWindow.addFlags(67108864);
        setupStatusBarView();
        if (this.mConfig.hasNavigtionBar()) {
            BarParams barParams = this.mBarParams;
            if (barParams.navigationBarEnable && barParams.navigationBarWithKitkatEnable) {
                this.mWindow.addFlags(134217728);
            } else {
                this.mWindow.clearFlags(134217728);
            }
            setupNavBarView();
        }
    }

    private void setupStatusBarView() {
        BarParams barParams = this.mBarParams;
        if (barParams.statusBarView == null) {
            barParams.statusBarView = new View(this.mActivity);
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, this.mConfig.getStatusBarHeight());
        layoutParams.gravity = 48;
        this.mBarParams.statusBarView.setLayoutParams(layoutParams);
        BarParams barParams2 = this.mBarParams;
        if (barParams2.statusBarFlag) {
            barParams2.statusBarView.setBackgroundColor(ColorUtils.blendARGB(barParams2.statusBarColor, barParams2.statusBarColorTransform, barParams2.statusBarAlpha));
        } else {
            barParams2.statusBarView.setBackgroundColor(ColorUtils.blendARGB(barParams2.statusBarColor, 0, barParams2.statusBarAlpha));
        }
        this.mBarParams.statusBarView.setVisibility(0);
        ViewGroup viewGroup = (ViewGroup) this.mBarParams.statusBarView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mBarParams.statusBarView);
        }
        this.mDecorView.addView(this.mBarParams.statusBarView);
    }

    private void setupNavBarView() {
        FrameLayout.LayoutParams layoutParams;
        BarParams barParams = this.mBarParams;
        if (barParams.navigationBarView == null) {
            barParams.navigationBarView = new View(this.mActivity);
        }
        if (this.mConfig.isNavigationAtBottom()) {
            layoutParams = new FrameLayout.LayoutParams(-1, this.mConfig.getNavigationBarHeight());
            layoutParams.gravity = 80;
        } else {
            layoutParams = new FrameLayout.LayoutParams(this.mConfig.getNavigationBarWidth(), -1);
            layoutParams.gravity = GravityCompat.END;
        }
        this.mBarParams.navigationBarView.setLayoutParams(layoutParams);
        BarParams barParams2 = this.mBarParams;
        if (barParams2.navigationBarEnable && barParams2.navigationBarWithKitkatEnable) {
            if (!barParams2.fullScreen && barParams2.navigationBarColorTransform == 0) {
                barParams2.navigationBarView.setBackgroundColor(ColorUtils.blendARGB(barParams2.navigationBarColor, ViewCompat.MEASURED_STATE_MASK, barParams2.navigationBarAlpha));
            } else {
                BarParams barParams3 = this.mBarParams;
                barParams3.navigationBarView.setBackgroundColor(ColorUtils.blendARGB(barParams3.navigationBarColor, barParams3.navigationBarColorTransform, barParams3.navigationBarAlpha));
            }
        } else {
            this.mBarParams.navigationBarView.setBackgroundColor(0);
        }
        this.mBarParams.navigationBarView.setVisibility(0);
        ViewGroup viewGroup = (ViewGroup) this.mBarParams.navigationBarView.getParent();
        if (viewGroup != null) {
            viewGroup.removeView(this.mBarParams.navigationBarView);
        }
        this.mDecorView.addView(this.mBarParams.navigationBarView);
    }

    private void solveNavigation() {
        int childCount = this.mContentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mContentView.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                if (childAt instanceof DrawerLayout) {
                    View childAt2 = ((DrawerLayout) childAt).getChildAt(0);
                    if (childAt2 != null) {
                        this.mBarParams.systemWindows = childAt2.getFitsSystemWindows();
                        if (this.mBarParams.systemWindows) {
                            this.mContentView.setPadding(0, 0, 0, 0);
                            return;
                        }
                    } else {
                        continue;
                    }
                } else {
                    this.mBarParams.systemWindows = childAt.getFitsSystemWindows();
                    if (this.mBarParams.systemWindows) {
                        this.mContentView.setPadding(0, 0, 0, 0);
                        return;
                    }
                }
            }
        }
        if (this.mConfig.hasNavigtionBar()) {
            BarParams barParams = this.mBarParams;
            if (!barParams.fullScreenTemp && !barParams.fullScreen) {
                if (this.mConfig.isNavigationAtBottom()) {
                    BarParams barParams2 = this.mBarParams;
                    if (!barParams2.isSupportActionBar) {
                        if (barParams2.navigationBarEnable && barParams2.navigationBarWithKitkatEnable) {
                            if (barParams2.fits) {
                                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, this.mConfig.getNavigationBarHeight());
                                return;
                            } else {
                                this.mContentView.setPadding(0, 0, 0, this.mConfig.getNavigationBarHeight());
                                return;
                            }
                        } else if (this.mBarParams.fits) {
                            this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
                            return;
                        } else {
                            this.mContentView.setPadding(0, 0, 0, 0);
                            return;
                        }
                    } else if (barParams2.navigationBarEnable && barParams2.navigationBarWithKitkatEnable) {
                        this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, this.mConfig.getNavigationBarHeight());
                        return;
                    } else {
                        this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, 0);
                        return;
                    }
                }
                BarParams barParams3 = this.mBarParams;
                if (!barParams3.isSupportActionBar) {
                    if (barParams3.navigationBarEnable && barParams3.navigationBarWithKitkatEnable) {
                        if (barParams3.fits) {
                            this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), this.mConfig.getNavigationBarWidth(), 0);
                            return;
                        } else {
                            this.mContentView.setPadding(0, 0, this.mConfig.getNavigationBarWidth(), 0);
                            return;
                        }
                    } else if (this.mBarParams.fits) {
                        this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
                        return;
                    } else {
                        this.mContentView.setPadding(0, 0, 0, 0);
                        return;
                    }
                } else if (barParams3.navigationBarEnable && barParams3.navigationBarWithKitkatEnable) {
                    this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, this.mConfig.getNavigationBarWidth(), 0);
                    return;
                } else {
                    this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, 0);
                    return;
                }
            }
        }
        BarParams barParams4 = this.mBarParams;
        if (!barParams4.isSupportActionBar) {
            if (barParams4.fits) {
                this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
                return;
            } else {
                this.mContentView.setPadding(0, 0, 0, 0);
                return;
            }
        }
        this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight() + 10, 0, 0);
    }

    private void registerEMUI3_x() {
        if ((OSUtils.isEMUI3_1() || OSUtils.isEMUI3_0()) && this.mConfig.hasNavigtionBar()) {
            BarParams barParams = this.mBarParams;
            if (!barParams.navigationBarEnable || !barParams.navigationBarWithKitkatEnable) {
                return;
            }
            if (barParams.navigationStatusObserver == null && barParams.navigationBarView != null) {
                barParams.navigationStatusObserver = new ContentObserver(new Handler()) { // from class: com.gyf.barlibrary.ImmersionBar.1
                    @Override // android.database.ContentObserver
                    public void onChange(boolean z) {
                        if (Settings.System.getInt(ImmersionBar.this.mActivity.getContentResolver(), "navigationbar_is_min", 0) == 1) {
                            ImmersionBar.this.mBarParams.navigationBarView.setVisibility(8);
                            ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), 0, 0);
                            return;
                        }
                        ImmersionBar.this.mBarParams.navigationBarView.setVisibility(0);
                        if (!ImmersionBar.this.mBarParams.systemWindows) {
                            if (ImmersionBar.this.mConfig.isNavigationAtBottom()) {
                                ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), 0, ImmersionBar.this.mConfig.getNavigationBarHeight());
                                return;
                            } else {
                                ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), ImmersionBar.this.mConfig.getNavigationBarWidth(), 0);
                                return;
                            }
                        }
                        ImmersionBar.this.mContentView.setPadding(0, ImmersionBar.this.mContentView.getPaddingTop(), 0, 0);
                    }
                };
            }
            this.mActivity.getContentResolver().registerContentObserver(Settings.System.getUriFor("navigationbar_is_min"), true, this.mBarParams.navigationStatusObserver);
        }
    }

    private void unRegisterEMUI3_x() {
        if ((OSUtils.isEMUI3_1() || OSUtils.isEMUI3_0()) && this.mConfig.hasNavigtionBar()) {
            BarParams barParams = this.mBarParams;
            if (!barParams.navigationBarEnable || !barParams.navigationBarWithKitkatEnable || barParams.navigationStatusObserver == null || barParams.navigationBarView == null) {
                return;
            }
            this.mActivity.getContentResolver().unregisterContentObserver(this.mBarParams.navigationStatusObserver);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gyf.barlibrary.ImmersionBar$4 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C21094 {
        static final /* synthetic */ int[] $SwitchMap$com$gyf$barlibrary$BarHide = new int[BarHide.values().length];

        static {
            try {
                $SwitchMap$com$gyf$barlibrary$BarHide[BarHide.FLAG_HIDE_BAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$gyf$barlibrary$BarHide[BarHide.FLAG_HIDE_STATUS_BAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$gyf$barlibrary$BarHide[BarHide.FLAG_HIDE_NAVIGATION_BAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$gyf$barlibrary$BarHide[BarHide.FLAG_SHOW_BAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private int hideBar(int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            int i2 = C21094.$SwitchMap$com$gyf$barlibrary$BarHide[this.mBarParams.barHide.ordinal()];
            if (i2 == 1) {
                i |= 518;
            } else if (i2 == 2) {
                i |= 1028;
            } else if (i2 == 3) {
                i |= 514;
            } else if (i2 == 4) {
                i |= 0;
            }
        }
        return i | 4096;
    }

    private int setStatusBarDarkFont(int i) {
        return (Build.VERSION.SDK_INT < 23 || !this.mBarParams.darkFont) ? i : i | 8192;
    }

    private void transformView() {
        if (this.mBarParams.viewMap.size() != 0) {
            for (Map.Entry<View, Map<Integer, Integer>> entry : this.mBarParams.viewMap.entrySet()) {
                View key = entry.getKey();
                Integer valueOf = Integer.valueOf(this.mBarParams.statusBarColor);
                Integer valueOf2 = Integer.valueOf(this.mBarParams.statusBarColorTransform);
                for (Map.Entry<Integer, Integer> entry2 : entry.getValue().entrySet()) {
                    Integer key2 = entry2.getKey();
                    valueOf2 = entry2.getValue();
                    valueOf = key2;
                }
                if (key != null) {
                    if (Math.abs(this.mBarParams.viewAlpha - 0.0f) == 0.0f) {
                        key.setBackgroundColor(ColorUtils.blendARGB(valueOf.intValue(), valueOf2.intValue(), this.mBarParams.statusBarAlpha));
                    } else {
                        key.setBackgroundColor(ColorUtils.blendARGB(valueOf.intValue(), valueOf2.intValue(), this.mBarParams.viewAlpha));
                    }
                }
            }
        }
    }

    private void setStatusBarView() {
        View view;
        if (Build.VERSION.SDK_INT < 19 || (view = this.mBarParams.statusBarViewByHeight) == null) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = this.mConfig.getStatusBarHeight();
        this.mBarParams.statusBarViewByHeight.setLayoutParams(layoutParams);
    }

    private void setTitleBar() {
        View view;
        if (Build.VERSION.SDK_INT < 19 || (view = this.mBarParams.titleBarView) == null) {
            return;
        }
        final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        int i = layoutParams.height;
        if (i == -2 || i == -1) {
            this.mBarParams.titleBarView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() { // from class: com.gyf.barlibrary.ImmersionBar.2
                @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
                public void onGlobalLayout() {
                    ImmersionBar.this.mBarParams.titleBarView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    if (ImmersionBar.this.mBarParams.titleBarHeight == 0) {
                        ImmersionBar.this.mBarParams.titleBarHeight = ImmersionBar.this.mBarParams.titleBarView.getHeight() + ImmersionBar.this.mConfig.getStatusBarHeight();
                    }
                    if (ImmersionBar.this.mBarParams.titleBarPaddingTopHeight == 0) {
                        ImmersionBar.this.mBarParams.titleBarPaddingTopHeight = ImmersionBar.this.mBarParams.titleBarView.getPaddingTop() + ImmersionBar.this.mConfig.getStatusBarHeight();
                    }
                    layoutParams.height = ImmersionBar.this.mBarParams.titleBarHeight;
                    ImmersionBar.this.mBarParams.titleBarView.setPadding(ImmersionBar.this.mBarParams.titleBarView.getPaddingLeft(), ImmersionBar.this.mBarParams.titleBarPaddingTopHeight, ImmersionBar.this.mBarParams.titleBarView.getPaddingRight(), ImmersionBar.this.mBarParams.titleBarView.getPaddingBottom());
                    ImmersionBar.this.mBarParams.titleBarView.setLayoutParams(layoutParams);
                }
            });
            return;
        }
        BarParams barParams = this.mBarParams;
        if (barParams.titleBarHeight == 0) {
            barParams.titleBarHeight = i + this.mConfig.getStatusBarHeight();
        }
        BarParams barParams2 = this.mBarParams;
        if (barParams2.titleBarPaddingTopHeight == 0) {
            barParams2.titleBarPaddingTopHeight = barParams2.titleBarView.getPaddingTop() + this.mConfig.getStatusBarHeight();
        }
        BarParams barParams3 = this.mBarParams;
        layoutParams.height = barParams3.titleBarHeight;
        View view2 = barParams3.titleBarView;
        int paddingLeft = view2.getPaddingLeft();
        BarParams barParams4 = this.mBarParams;
        view2.setPadding(paddingLeft, barParams4.titleBarPaddingTopHeight, barParams4.titleBarView.getPaddingRight(), this.mBarParams.titleBarView.getPaddingBottom());
        this.mBarParams.titleBarView.setLayoutParams(layoutParams);
    }

    private void supportActionBar() {
        if (Build.VERSION.SDK_INT < 21 || OSUtils.isEMUI3_1()) {
            return;
        }
        int childCount = this.mContentView.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = this.mContentView.getChildAt(i);
            if (childAt instanceof ViewGroup) {
                this.mBarParams.systemWindows = childAt.getFitsSystemWindows();
                if (this.mBarParams.systemWindows) {
                    this.mContentView.setPadding(0, 0, 0, 0);
                    return;
                }
            }
        }
        BarParams barParams = this.mBarParams;
        if (barParams.isSupportActionBar) {
            this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight() + this.mConfig.getActionBarHeight(), 0, 0);
        } else if (barParams.fits) {
            this.mContentView.setPadding(0, this.mConfig.getStatusBarHeight(), 0, 0);
        } else {
            this.mContentView.setPadding(0, 0, 0, 0);
        }
    }

    private void keyboardEnable() {
        if (Build.VERSION.SDK_INT >= 19) {
            BarParams barParams = this.mBarParams;
            if (barParams.keyboardPatch == null) {
                barParams.keyboardPatch = KeyboardPatch.patch(this.mActivity, this.mWindow);
            }
            BarParams barParams2 = this.mBarParams;
            barParams2.keyboardPatch.setBarParams(barParams2);
            BarParams barParams3 = this.mBarParams;
            if (barParams3.keyboardEnable) {
                barParams3.keyboardPatch.enable(barParams3.keyboardMode);
            } else {
                barParams3.keyboardPatch.disable(barParams3.keyboardMode);
            }
        }
    }

    private void setMIUIStatusBarDarkFont(Window window, boolean z) {
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE").getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                if (z) {
                    method.invoke(window, Integer.valueOf(i), Integer.valueOf(i));
                } else {
                    method.invoke(window, 0, Integer.valueOf(i));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean isSupportStatusBarDarkFont() {
        return OSUtils.isMIUI6Later() || OSUtils.isFlymeOS4Later() || Build.VERSION.SDK_INT >= 23;
    }

    private static boolean isEmpty(String str) {
        return str == null || str.trim().length() == 0;
    }
}
