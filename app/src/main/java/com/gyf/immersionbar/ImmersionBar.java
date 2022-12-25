package com.gyf.immersionbar;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.app.Fragment;
import android.os.Build;
import android.support.annotation.FloatRange;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.p002v4.app.DialogFragment;
import android.support.p002v4.graphics.ColorUtils;
import android.support.p002v4.view.GravityCompat;
import android.support.p002v4.widget.DrawerLayout;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

@TargetApi(19)
/* loaded from: classes3.dex */
public final class ImmersionBar implements ImmersionCallback {
    private Activity mActivity;
    private BarConfig mBarConfig;
    private BarParams mBarParams;
    private ViewGroup mContentView;
    private ViewGroup mDecorView;
    private Dialog mDialog;
    private FitsKeyboard mFitsKeyboard;
    private int mFitsStatusBarType;
    private Fragment mFragment;
    private boolean mHasNavigationBarColor;
    private boolean mInitialized;
    private boolean mIsDialog;
    private boolean mIsFitsLayoutOverlap;
    private boolean mIsFitsNotch;
    private boolean mIsFragment;
    private boolean mKeyboardTempEnable;
    private int mNavigationBarHeight;
    private int mNavigationBarWidth;
    private int mPaddingBottom;
    private int mPaddingLeft;
    private int mPaddingRight;
    private int mPaddingTop;
    private android.support.p002v4.app.Fragment mSupportFragment;
    private Window mWindow;

    public static ImmersionBar with(@NonNull Activity activity) {
        return getRetriever().get(activity);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmersionBar(Activity activity) {
        this.mNavigationBarHeight = 0;
        this.mNavigationBarWidth = 0;
        this.mIsFragment = false;
        this.mIsDialog = false;
        this.mFitsKeyboard = null;
        new HashMap();
        this.mIsFitsLayoutOverlap = false;
        this.mFitsStatusBarType = 0;
        this.mHasNavigationBarColor = false;
        this.mIsFitsNotch = false;
        this.mInitialized = false;
        this.mKeyboardTempEnable = false;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mActivity = activity;
        initCommonParameter(this.mActivity.getWindow());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmersionBar(android.support.p002v4.app.Fragment fragment) {
        this.mNavigationBarHeight = 0;
        this.mNavigationBarWidth = 0;
        this.mIsFragment = false;
        this.mIsDialog = false;
        this.mFitsKeyboard = null;
        new HashMap();
        this.mIsFitsLayoutOverlap = false;
        this.mFitsStatusBarType = 0;
        this.mHasNavigationBarColor = false;
        this.mIsFitsNotch = false;
        this.mInitialized = false;
        this.mKeyboardTempEnable = false;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mIsFragment = true;
        this.mActivity = fragment.getActivity();
        this.mSupportFragment = fragment;
        checkInitWithActivity();
        initCommonParameter(this.mActivity.getWindow());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmersionBar(Fragment fragment) {
        this.mNavigationBarHeight = 0;
        this.mNavigationBarWidth = 0;
        this.mIsFragment = false;
        this.mIsDialog = false;
        this.mFitsKeyboard = null;
        new HashMap();
        this.mIsFitsLayoutOverlap = false;
        this.mFitsStatusBarType = 0;
        this.mHasNavigationBarColor = false;
        this.mIsFitsNotch = false;
        this.mInitialized = false;
        this.mKeyboardTempEnable = false;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mIsFragment = true;
        this.mActivity = fragment.getActivity();
        this.mFragment = fragment;
        checkInitWithActivity();
        initCommonParameter(this.mActivity.getWindow());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmersionBar(DialogFragment dialogFragment) {
        this.mNavigationBarHeight = 0;
        this.mNavigationBarWidth = 0;
        this.mIsFragment = false;
        this.mIsDialog = false;
        this.mFitsKeyboard = null;
        new HashMap();
        this.mIsFitsLayoutOverlap = false;
        this.mFitsStatusBarType = 0;
        this.mHasNavigationBarColor = false;
        this.mIsFitsNotch = false;
        this.mInitialized = false;
        this.mKeyboardTempEnable = false;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mIsDialog = true;
        this.mActivity = dialogFragment.getActivity();
        this.mSupportFragment = dialogFragment;
        this.mDialog = dialogFragment.getDialog();
        checkInitWithActivity();
        initCommonParameter(this.mDialog.getWindow());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public ImmersionBar(android.app.DialogFragment dialogFragment) {
        this.mNavigationBarHeight = 0;
        this.mNavigationBarWidth = 0;
        this.mIsFragment = false;
        this.mIsDialog = false;
        this.mFitsKeyboard = null;
        new HashMap();
        this.mIsFitsLayoutOverlap = false;
        this.mFitsStatusBarType = 0;
        this.mHasNavigationBarColor = false;
        this.mIsFitsNotch = false;
        this.mInitialized = false;
        this.mKeyboardTempEnable = false;
        this.mPaddingLeft = 0;
        this.mPaddingTop = 0;
        this.mPaddingRight = 0;
        this.mPaddingBottom = 0;
        this.mIsDialog = true;
        this.mActivity = dialogFragment.getActivity();
        this.mFragment = dialogFragment;
        this.mDialog = dialogFragment.getDialog();
        checkInitWithActivity();
        initCommonParameter(this.mDialog.getWindow());
    }

    private void checkInitWithActivity() {
        if (!with(this.mActivity).initialized()) {
            with(this.mActivity).init();
        }
    }

    private void initCommonParameter(Window window) {
        this.mWindow = window;
        this.mBarParams = new BarParams();
        this.mDecorView = (ViewGroup) this.mWindow.getDecorView();
        this.mContentView = (ViewGroup) this.mDecorView.findViewById(16908290);
    }

    public ImmersionBar statusBarDarkFont(boolean z) {
        statusBarDarkFont(z, 0.0f);
        return this;
    }

    public ImmersionBar statusBarDarkFont(boolean z, @FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        this.mBarParams.statusBarDarkFont = z;
        if (z && !isSupportStatusBarDarkFont()) {
            this.mBarParams.statusBarAlpha = f;
        } else {
            BarParams barParams = this.mBarParams;
            barParams.flymeOSStatusBarFontColor = 0;
            barParams.statusBarAlpha = 0.0f;
        }
        return this;
    }

    public ImmersionBar navigationBarDarkIcon(boolean z, @FloatRange(from = 0.0d, m5592to = 1.0d) float f) {
        this.mBarParams.navigationBarDarkIcon = z;
        if (z && !isSupportNavigationIconDark()) {
            this.mBarParams.navigationBarAlpha = f;
        } else {
            this.mBarParams.navigationBarAlpha = 0.0f;
        }
        return this;
    }

    public ImmersionBar titleBar(View view) {
        if (view == null) {
            return this;
        }
        titleBar(view, true);
        return this;
    }

    public ImmersionBar titleBar(View view, boolean z) {
        if (view == null) {
            return this;
        }
        if (this.mFitsStatusBarType == 0) {
            this.mFitsStatusBarType = 1;
        }
        BarParams barParams = this.mBarParams;
        barParams.titleBarView = view;
        barParams.statusBarColorEnabled = z;
        return this;
    }

    public ImmersionBar titleBar(@IdRes int i) {
        titleBar(i, true);
        return this;
    }

    public ImmersionBar titleBar(@IdRes int i, boolean z) {
        android.support.p002v4.app.Fragment fragment = this.mSupportFragment;
        if (fragment != null && fragment.getView() != null) {
            titleBar(this.mSupportFragment.getView().findViewById(i), z);
            return this;
        }
        Fragment fragment2 = this.mFragment;
        if (fragment2 != null && fragment2.getView() != null) {
            titleBar(this.mFragment.getView().findViewById(i), z);
            return this;
        }
        titleBar(this.mActivity.findViewById(i), z);
        return this;
    }

    public void init() {
        updateBarParams();
        setBar();
        fitsLayoutOverlap();
        fitsKeyboard();
        transformView();
        this.mInitialized = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void destroy() {
        ImmersionBar with;
        cancelListener();
        if (this.mIsDialog && (with = with(this.mActivity)) != null) {
            with.mBarParams.keyboardEnable = with.mKeyboardTempEnable;
        }
        this.mInitialized = false;
    }

    private void updateBarParams() {
        ImmersionBar with;
        ImmersionBar with2;
        adjustDarkModeParams();
        if (Build.VERSION.SDK_INT >= 19) {
            this.mBarConfig = new BarConfig(this.mActivity);
            if (this.mIsFragment && (with2 = with(this.mActivity)) != null) {
                with2.mBarParams = this.mBarParams;
            }
            if (!this.mIsDialog || (with = with(this.mActivity)) == null || !with.mKeyboardTempEnable) {
                return;
            }
            with.mBarParams.keyboardEnable = false;
        }
    }

    private void setBar() {
        int i = Build.VERSION.SDK_INT;
        if (i >= 19) {
            int i2 = 256;
            if (i >= 21 && !OSUtils.isEMUI3_x()) {
                fitsNotchScreen();
                i2 = setNavigationIconDark(setStatusBarDarkFont(initBarAboveLOLLIPOP(256)));
            } else {
                initBarBelowLOLLIPOP();
            }
            int hideBar = hideBar(i2);
            fitsWindows();
            this.mDecorView.setSystemUiVisibility(hideBar);
        }
        if (OSUtils.isMIUI6Later()) {
            setMIUIBarDark(this.mWindow, "EXTRA_FLAG_STATUS_BAR_DARK_MODE", this.mBarParams.statusBarDarkFont);
            BarParams barParams = this.mBarParams;
            if (barParams.navigationBarEnable) {
                setMIUIBarDark(this.mWindow, "EXTRA_FLAG_NAVIGATION_BAR_DARK_MODE", barParams.navigationBarDarkIcon);
            }
        }
        if (OSUtils.isFlymeOS4Later()) {
            BarParams barParams2 = this.mBarParams;
            int i3 = barParams2.flymeOSStatusBarFontColor;
            if (i3 != 0) {
                FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this.mActivity, i3);
            } else {
                FlymeOSStatusBarFontUtils.setStatusBarDarkIcon(this.mActivity, barParams2.statusBarDarkFont);
            }
        }
        if (this.mBarParams.onNavigationBarListener != null) {
            NavigationBarObserver.getInstance().register(this.mActivity.getApplication());
        }
    }

    private void fitsNotchScreen() {
        if (Build.VERSION.SDK_INT < 28 || this.mIsFitsNotch) {
            return;
        }
        WindowManager.LayoutParams attributes = this.mWindow.getAttributes();
        attributes.layoutInDisplayCutoutMode = 1;
        this.mWindow.setAttributes(attributes);
        this.mIsFitsNotch = true;
    }

    @RequiresApi(api = 21)
    private int initBarAboveLOLLIPOP(int i) {
        if (!this.mHasNavigationBarColor) {
            this.mBarParams.defaultNavigationBarColor = this.mWindow.getNavigationBarColor();
            this.mHasNavigationBarColor = true;
        }
        int i2 = i | 1024;
        BarParams barParams = this.mBarParams;
        if (barParams.fullScreen && barParams.navigationBarEnable) {
            i2 |= 512;
        }
        this.mWindow.clearFlags(67108864);
        if (this.mBarConfig.hasNavigationBar()) {
            this.mWindow.clearFlags(134217728);
        }
        this.mWindow.addFlags(Integer.MIN_VALUE);
        BarParams barParams2 = this.mBarParams;
        if (barParams2.statusBarColorEnabled) {
            this.mWindow.setStatusBarColor(ColorUtils.blendARGB(barParams2.statusBarColor, barParams2.statusBarColorTransform, barParams2.statusBarAlpha));
        } else {
            this.mWindow.setStatusBarColor(ColorUtils.blendARGB(barParams2.statusBarColor, 0, barParams2.statusBarAlpha));
        }
        BarParams barParams3 = this.mBarParams;
        if (barParams3.navigationBarEnable) {
            this.mWindow.setNavigationBarColor(ColorUtils.blendARGB(barParams3.navigationBarColor, barParams3.navigationBarColorTransform, barParams3.navigationBarAlpha));
        } else {
            this.mWindow.setNavigationBarColor(barParams3.defaultNavigationBarColor);
        }
        return i2;
    }

    private void initBarBelowLOLLIPOP() {
        this.mWindow.addFlags(67108864);
        setupStatusBarView();
        if (this.mBarConfig.hasNavigationBar() || OSUtils.isEMUI3_x()) {
            BarParams barParams = this.mBarParams;
            if (barParams.navigationBarEnable && barParams.navigationBarWithKitkatEnable) {
                this.mWindow.addFlags(134217728);
            } else {
                this.mWindow.clearFlags(134217728);
            }
            if (this.mNavigationBarHeight == 0) {
                this.mNavigationBarHeight = this.mBarConfig.getNavigationBarHeight();
            }
            if (this.mNavigationBarWidth == 0) {
                this.mNavigationBarWidth = this.mBarConfig.getNavigationBarWidth();
            }
            setupNavBarView();
        }
    }

    private void setupStatusBarView() {
        View findViewById = this.mDecorView.findViewById(Constants.IMMERSION_ID_STATUS_BAR_VIEW);
        if (findViewById == null) {
            findViewById = new View(this.mActivity);
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(-1, this.mBarConfig.getStatusBarHeight());
            layoutParams.gravity = 48;
            findViewById.setLayoutParams(layoutParams);
            findViewById.setVisibility(0);
            findViewById.setId(Constants.IMMERSION_ID_STATUS_BAR_VIEW);
            this.mDecorView.addView(findViewById);
        }
        BarParams barParams = this.mBarParams;
        if (barParams.statusBarColorEnabled) {
            findViewById.setBackgroundColor(ColorUtils.blendARGB(barParams.statusBarColor, barParams.statusBarColorTransform, barParams.statusBarAlpha));
        } else {
            findViewById.setBackgroundColor(ColorUtils.blendARGB(barParams.statusBarColor, 0, barParams.statusBarAlpha));
        }
    }

    private void setupNavBarView() {
        FrameLayout.LayoutParams layoutParams;
        View findViewById = this.mDecorView.findViewById(Constants.IMMERSION_ID_NAVIGATION_BAR_VIEW);
        if (findViewById == null) {
            findViewById = new View(this.mActivity);
            findViewById.setId(Constants.IMMERSION_ID_NAVIGATION_BAR_VIEW);
            this.mDecorView.addView(findViewById);
        }
        if (this.mBarConfig.isNavigationAtBottom()) {
            layoutParams = new FrameLayout.LayoutParams(-1, this.mBarConfig.getNavigationBarHeight());
            layoutParams.gravity = 80;
        } else {
            layoutParams = new FrameLayout.LayoutParams(this.mBarConfig.getNavigationBarWidth(), -1);
            layoutParams.gravity = GravityCompat.END;
        }
        findViewById.setLayoutParams(layoutParams);
        BarParams barParams = this.mBarParams;
        findViewById.setBackgroundColor(ColorUtils.blendARGB(barParams.navigationBarColor, barParams.navigationBarColorTransform, barParams.navigationBarAlpha));
        BarParams barParams2 = this.mBarParams;
        if (barParams2.navigationBarEnable && barParams2.navigationBarWithKitkatEnable && !barParams2.hideNavigationBar) {
            findViewById.setVisibility(0);
        } else {
            findViewById.setVisibility(8);
        }
    }

    private void adjustDarkModeParams() {
        int i;
        int i2;
        BarParams barParams = this.mBarParams;
        boolean z = true;
        if (barParams.autoStatusBarDarkModeEnable && (i2 = barParams.statusBarColor) != 0) {
            statusBarDarkFont(i2 > -4539718, this.mBarParams.autoStatusBarDarkModeAlpha);
        }
        BarParams barParams2 = this.mBarParams;
        if (!barParams2.autoNavigationBarDarkModeEnable || (i = barParams2.navigationBarColor) == 0) {
            return;
        }
        if (i <= -4539718) {
            z = false;
        }
        navigationBarDarkIcon(z, this.mBarParams.autoNavigationBarDarkModeAlpha);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.gyf.immersionbar.ImmersionBar$2 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C21132 {
        static final /* synthetic */ int[] $SwitchMap$com$gyf$immersionbar$BarHide = new int[BarHide.values().length];

        static {
            try {
                $SwitchMap$com$gyf$immersionbar$BarHide[BarHide.FLAG_HIDE_BAR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$gyf$immersionbar$BarHide[BarHide.FLAG_HIDE_STATUS_BAR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$gyf$immersionbar$BarHide[BarHide.FLAG_HIDE_NAVIGATION_BAR.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$gyf$immersionbar$BarHide[BarHide.FLAG_SHOW_BAR.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private int hideBar(int i) {
        if (Build.VERSION.SDK_INT >= 16) {
            int i2 = C21132.$SwitchMap$com$gyf$immersionbar$BarHide[this.mBarParams.barHide.ordinal()];
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

    private void fitsWindows() {
        if (Build.VERSION.SDK_INT >= 21 && !OSUtils.isEMUI3_x()) {
            fitsWindowsAboveLOLLIPOP();
            return;
        }
        fitsWindowsBelowLOLLIPOP();
        if (this.mIsFragment || !OSUtils.isEMUI3_x()) {
            return;
        }
        fitsWindowsEMUI();
    }

    private void fitsWindowsAboveLOLLIPOP() {
        if (checkFitsSystemWindows(this.mDecorView.findViewById(16908290))) {
            if (!this.mBarParams.isSupportActionBar) {
                return;
            }
            setPadding(0, this.mBarConfig.getActionBarHeight(), 0, 0);
            return;
        }
        int statusBarHeight = (!this.mBarParams.fits || this.mFitsStatusBarType != 4) ? 0 : this.mBarConfig.getStatusBarHeight();
        if (this.mBarParams.isSupportActionBar) {
            statusBarHeight = this.mBarConfig.getStatusBarHeight() + this.mBarConfig.getActionBarHeight();
        }
        setPadding(0, statusBarHeight, 0, 0);
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x007c  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x0087  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void fitsWindowsBelowLOLLIPOP() {
        int i;
        int i2;
        if (checkFitsSystemWindows(this.mDecorView.findViewById(16908290))) {
            if (!this.mBarParams.isSupportActionBar) {
                return;
            }
            setPadding(0, this.mBarConfig.getActionBarHeight(), 0, 0);
            return;
        }
        int statusBarHeight = (!this.mBarParams.fits || this.mFitsStatusBarType != 4) ? 0 : this.mBarConfig.getStatusBarHeight();
        if (this.mBarParams.isSupportActionBar) {
            statusBarHeight = this.mBarConfig.getStatusBarHeight() + this.mBarConfig.getActionBarHeight();
        }
        if (this.mBarConfig.hasNavigationBar()) {
            BarParams barParams = this.mBarParams;
            if (barParams.navigationBarEnable && barParams.navigationBarWithKitkatEnable) {
                if (barParams.fullScreen) {
                    i = 0;
                } else if (this.mBarConfig.isNavigationAtBottom()) {
                    i2 = this.mBarConfig.getNavigationBarHeight();
                    i = 0;
                    if (!this.mBarParams.hideNavigationBar) {
                        if (!this.mBarConfig.isNavigationAtBottom()) {
                            i = 0;
                        }
                        i2 = 0;
                    } else if (!this.mBarConfig.isNavigationAtBottom()) {
                        i = this.mBarConfig.getNavigationBarWidth();
                    }
                    setPadding(0, statusBarHeight, i, i2);
                } else {
                    i = this.mBarConfig.getNavigationBarWidth();
                }
                i2 = 0;
                if (!this.mBarParams.hideNavigationBar) {
                }
                setPadding(0, statusBarHeight, i, i2);
            }
        }
        i = 0;
        i2 = 0;
        setPadding(0, statusBarHeight, i, i2);
    }

    private void fitsWindowsEMUI() {
        View findViewById = this.mDecorView.findViewById(Constants.IMMERSION_ID_NAVIGATION_BAR_VIEW);
        BarParams barParams = this.mBarParams;
        if (!barParams.navigationBarEnable || !barParams.navigationBarWithKitkatEnable) {
            EMUI3NavigationBarObserver.getInstance().removeOnNavigationBarListener(this);
            findViewById.setVisibility(8);
        } else if (findViewById == null) {
        } else {
            EMUI3NavigationBarObserver.getInstance().addOnNavigationBarListener(this);
            EMUI3NavigationBarObserver.getInstance().register(this.mActivity.getApplication());
        }
    }

    @Override // com.gyf.immersionbar.OnNavigationBarListener
    public void onNavigationBarChange(boolean z) {
        View findViewById = this.mDecorView.findViewById(Constants.IMMERSION_ID_NAVIGATION_BAR_VIEW);
        if (findViewById != null) {
            this.mBarConfig = new BarConfig(this.mActivity);
            int paddingBottom = this.mContentView.getPaddingBottom();
            int paddingRight = this.mContentView.getPaddingRight();
            if (!z) {
                findViewById.setVisibility(8);
            } else {
                findViewById.setVisibility(0);
                if (!checkFitsSystemWindows(this.mDecorView.findViewById(16908290))) {
                    if (this.mNavigationBarHeight == 0) {
                        this.mNavigationBarHeight = this.mBarConfig.getNavigationBarHeight();
                    }
                    if (this.mNavigationBarWidth == 0) {
                        this.mNavigationBarWidth = this.mBarConfig.getNavigationBarWidth();
                    }
                    if (!this.mBarParams.hideNavigationBar) {
                        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) findViewById.getLayoutParams();
                        if (this.mBarConfig.isNavigationAtBottom()) {
                            layoutParams.gravity = 80;
                            paddingBottom = this.mNavigationBarHeight;
                            layoutParams.height = paddingBottom;
                            if (this.mBarParams.fullScreen) {
                                paddingBottom = 0;
                            }
                            paddingRight = 0;
                        } else {
                            layoutParams.gravity = GravityCompat.END;
                            int i = this.mNavigationBarWidth;
                            layoutParams.width = i;
                            if (this.mBarParams.fullScreen) {
                                i = 0;
                            }
                            paddingRight = i;
                            paddingBottom = 0;
                        }
                        findViewById.setLayoutParams(layoutParams);
                    }
                    setPadding(0, this.mContentView.getPaddingTop(), paddingRight, paddingBottom);
                }
            }
            paddingBottom = 0;
            paddingRight = 0;
            setPadding(0, this.mContentView.getPaddingTop(), paddingRight, paddingBottom);
        }
    }

    private int setStatusBarDarkFont(int i) {
        return (Build.VERSION.SDK_INT < 23 || !this.mBarParams.statusBarDarkFont) ? i : i | 8192;
    }

    private int setNavigationIconDark(int i) {
        return (Build.VERSION.SDK_INT < 26 || !this.mBarParams.navigationBarDarkIcon) ? i : i | 16;
    }

    @SuppressLint({"PrivateApi"})
    private void setMIUIBarDark(Window window, String str, boolean z) {
        if (window != null) {
            Class<?> cls = window.getClass();
            try {
                Class<?> cls2 = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                int i = cls2.getField(str).getInt(cls2);
                Method method = cls.getMethod("setExtraFlags", Integer.TYPE, Integer.TYPE);
                if (z) {
                    method.invoke(window, Integer.valueOf(i), Integer.valueOf(i));
                } else {
                    method.invoke(window, 0, Integer.valueOf(i));
                }
            } catch (Exception unused) {
            }
        }
    }

    private void fitsLayoutOverlap() {
        if (Build.VERSION.SDK_INT < 19 || this.mIsFitsLayoutOverlap) {
            return;
        }
        int i = this.mFitsStatusBarType;
        if (i == 1) {
            setTitleBar(this.mActivity, this.mBarParams.titleBarView);
            this.mIsFitsLayoutOverlap = true;
        } else if (i == 2) {
            setTitleBarMarginTop(this.mActivity, this.mBarParams.titleBarView);
            this.mIsFitsLayoutOverlap = true;
        } else if (i != 3) {
        } else {
            setStatusBarView(this.mActivity, this.mBarParams.statusBarView);
            this.mIsFitsLayoutOverlap = true;
        }
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

    private void cancelListener() {
        if (this.mActivity != null) {
            FitsKeyboard fitsKeyboard = this.mFitsKeyboard;
            if (fitsKeyboard != null) {
                fitsKeyboard.cancel();
                this.mFitsKeyboard = null;
            }
            EMUI3NavigationBarObserver.getInstance().removeOnNavigationBarListener(this);
            NavigationBarObserver.getInstance().removeOnNavigationBarListener(this.mBarParams.onNavigationBarListener);
        }
    }

    private void fitsKeyboard() {
        if (Build.VERSION.SDK_INT >= 19) {
            if (!this.mIsFragment) {
                if (this.mBarParams.keyboardEnable) {
                    if (this.mFitsKeyboard == null) {
                        this.mFitsKeyboard = new FitsKeyboard(this, this.mActivity, this.mWindow);
                    }
                    this.mFitsKeyboard.enable(this.mBarParams.keyboardMode);
                    return;
                }
                FitsKeyboard fitsKeyboard = this.mFitsKeyboard;
                if (fitsKeyboard == null) {
                    return;
                }
                fitsKeyboard.disable();
                return;
            }
            ImmersionBar with = with(this.mActivity);
            if (with == null) {
                return;
            }
            if (with.mBarParams.keyboardEnable) {
                if (with.mFitsKeyboard == null) {
                    with.mFitsKeyboard = new FitsKeyboard(with, with.mActivity, with.mWindow);
                }
                with.mFitsKeyboard.enable(with.mBarParams.keyboardMode);
                return;
            }
            FitsKeyboard fitsKeyboard2 = with.mFitsKeyboard;
            if (fitsKeyboard2 == null) {
                return;
            }
            fitsKeyboard2.disable();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean isFragment() {
        return this.mIsFragment;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean initialized() {
        return this.mInitialized;
    }

    public BarParams getBarParams() {
        return this.mBarParams;
    }

    private void setPadding(int i, int i2, int i3, int i4) {
        ViewGroup viewGroup = this.mContentView;
        if (viewGroup != null) {
            viewGroup.setPadding(i, i2, i3, i4);
        }
        this.mPaddingLeft = i;
        this.mPaddingTop = i2;
        this.mPaddingRight = i3;
        this.mPaddingBottom = i4;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPaddingLeft() {
        return this.mPaddingLeft;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPaddingTop() {
        return this.mPaddingTop;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPaddingRight() {
        return this.mPaddingRight;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public int getPaddingBottom() {
        return this.mPaddingBottom;
    }

    public static boolean isSupportStatusBarDarkFont() {
        return OSUtils.isMIUI6Later() || OSUtils.isFlymeOS4Later() || Build.VERSION.SDK_INT >= 23;
    }

    public static boolean isSupportNavigationIconDark() {
        return OSUtils.isMIUI6Later() || Build.VERSION.SDK_INT >= 26;
    }

    public static void setTitleBar(final Activity activity, View... viewArr) {
        for (final View view : viewArr) {
            if (activity == null || view == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                final ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.LayoutParams(-1, -2);
                }
                int i = layoutParams.height;
                if (i == -2 || i == -1) {
                    view.post(new Runnable() { // from class: com.gyf.immersionbar.ImmersionBar.1
                        @Override // java.lang.Runnable
                        public void run() {
                            layoutParams.height = view.getHeight() + ImmersionBar.getStatusBarHeight(activity);
                            View view2 = view;
                            view2.setPadding(view2.getPaddingLeft(), view.getPaddingTop() + ImmersionBar.getStatusBarHeight(activity), view.getPaddingRight(), view.getPaddingBottom());
                            view.setLayoutParams(layoutParams);
                        }
                    });
                } else {
                    layoutParams.height = i + getStatusBarHeight(activity);
                    view.setPadding(view.getPaddingLeft(), view.getPaddingTop() + getStatusBarHeight(activity), view.getPaddingRight(), view.getPaddingBottom());
                    view.setLayoutParams(layoutParams);
                }
            }
        }
    }

    public static void setTitleBar(android.support.p002v4.app.Fragment fragment, View... viewArr) {
        setTitleBar(fragment.getActivity(), viewArr);
    }

    public static void setTitleBarMarginTop(Activity activity, View... viewArr) {
        for (View view : viewArr) {
            if (activity == null || view == null) {
                return;
            }
            if (Build.VERSION.SDK_INT >= 19) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                if (layoutParams == null) {
                    layoutParams = new ViewGroup.MarginLayoutParams(-1, -2);
                }
                ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                marginLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin + getStatusBarHeight(activity), marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                view.setLayoutParams(marginLayoutParams);
            }
        }
    }

    public static void setStatusBarView(Activity activity, View view) {
        if (activity == null || view == null || Build.VERSION.SDK_INT < 19) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (layoutParams == null) {
            layoutParams = new ViewGroup.LayoutParams(-1, 0);
        }
        layoutParams.height = getStatusBarHeight(activity);
        view.setLayoutParams(layoutParams);
    }

    public static boolean checkFitsSystemWindows(View view) {
        if (view == null) {
            return false;
        }
        if (view.getFitsSystemWindows()) {
            return true;
        }
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View childAt = viewGroup.getChildAt(i);
                if (((childAt instanceof DrawerLayout) && checkFitsSystemWindows(childAt)) || childAt.getFitsSystemWindows()) {
                    return true;
                }
            }
        }
        return false;
    }

    @TargetApi(14)
    public static boolean hasNavigationBar(@NonNull Activity activity) {
        return new BarConfig(activity).hasNavigationBar();
    }

    @TargetApi(14)
    public static int getNavigationBarHeight(@NonNull Activity activity) {
        return new BarConfig(activity).getNavigationBarHeight();
    }

    @TargetApi(14)
    public static int getStatusBarHeight(@NonNull Activity activity) {
        return new BarConfig(activity).getStatusBarHeight();
    }

    public static void hideStatusBar(@NonNull Window window) {
        window.setFlags(1024, 1024);
    }

    private static RequestManagerRetriever getRetriever() {
        return RequestManagerRetriever.getInstance();
    }
}
