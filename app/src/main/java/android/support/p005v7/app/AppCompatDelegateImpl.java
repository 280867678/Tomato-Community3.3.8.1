package android.support.p005v7.app;

import android.app.Activity;
import android.app.Dialog;
import android.app.UiModeManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.VisibleForTesting;
import android.support.p002v4.app.NavUtils;
import android.support.p002v4.view.KeyEventDispatcher;
import android.support.p002v4.view.LayoutInflaterCompat;
import android.support.p002v4.view.OnApplyWindowInsetsListener;
import android.support.p002v4.view.ViewCompat;
import android.support.p002v4.view.ViewPropertyAnimatorCompat;
import android.support.p002v4.view.ViewPropertyAnimatorListenerAdapter;
import android.support.p002v4.view.WindowInsetsCompat;
import android.support.p002v4.widget.PopupWindowCompat;
import android.support.p005v7.app.ActionBarDrawerToggle;
import android.support.p005v7.appcompat.C0441R;
import android.support.p005v7.content.res.AppCompatResources;
import android.support.p005v7.view.ActionMode;
import android.support.p005v7.view.ContextThemeWrapper;
import android.support.p005v7.view.StandaloneActionMode;
import android.support.p005v7.view.SupportActionModeWrapper;
import android.support.p005v7.view.SupportMenuInflater;
import android.support.p005v7.view.WindowCallbackWrapper;
import android.support.p005v7.view.menu.ListMenuPresenter;
import android.support.p005v7.view.menu.MenuBuilder;
import android.support.p005v7.view.menu.MenuPresenter;
import android.support.p005v7.view.menu.MenuView;
import android.support.p005v7.widget.ActionBarContextView;
import android.support.p005v7.widget.AppCompatDrawableManager;
import android.support.p005v7.widget.ContentFrameLayout;
import android.support.p005v7.widget.DecorContentParent;
import android.support.p005v7.widget.FitWindowsViewGroup;
import android.support.p005v7.widget.TintTypedArray;
import android.support.p005v7.widget.Toolbar;
import android.support.p005v7.widget.VectorEnabledTintResources;
import android.support.p005v7.widget.ViewStubCompat;
import android.support.p005v7.widget.ViewUtils;
import android.text.TextUtils;
import android.util.AndroidRuntimeException;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.ActionMode;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import java.lang.Thread;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: android.support.v7.app.AppCompatDelegateImpl */
/* loaded from: classes2.dex */
public class AppCompatDelegateImpl extends AppCompatDelegate implements MenuBuilder.Callback, LayoutInflater.Factory2 {
    private static final boolean DEBUG = false;
    static final String EXCEPTION_HANDLER_MESSAGE_SUFFIX = ". If the resource you are trying to use is a vector resource, you may be referencing it in an unsupported way. See AppCompatDelegate.setCompatVectorFromResourcesEnabled() for more info.";
    private static final boolean IS_PRE_LOLLIPOP;
    private static final String KEY_LOCAL_NIGHT_MODE = "appcompat:local_night_mode";
    private static boolean sInstalledExceptionHandler;
    private static final int[] sWindowBackgroundStyleable;
    ActionBar mActionBar;
    private ActionMenuPresenterCallback mActionMenuPresenterCallback;
    ActionMode mActionMode;
    PopupWindow mActionModePopup;
    ActionBarContextView mActionModeView;
    final AppCompatCallback mAppCompatCallback;
    private AppCompatViewInflater mAppCompatViewInflater;
    final Window.Callback mAppCompatWindowCallback;
    private boolean mApplyDayNightCalled;
    private AutoNightModeManager mAutoNightModeManager;
    private boolean mClosingActionMenu;
    final Context mContext;
    private DecorContentParent mDecorContentParent;
    private boolean mEnableDefaultActionBarUp;
    private boolean mFeatureIndeterminateProgress;
    private boolean mFeatureProgress;
    boolean mHasActionBar;
    int mInvalidatePanelMenuFeatures;
    boolean mInvalidatePanelMenuPosted;
    boolean mIsDestroyed;
    boolean mIsFloating;
    private boolean mLongPressBackDown;
    MenuInflater mMenuInflater;
    final Window.Callback mOriginalWindowCallback;
    boolean mOverlayActionBar;
    boolean mOverlayActionMode;
    private PanelMenuPresenterCallback mPanelMenuPresenterCallback;
    private PanelFeatureState[] mPanels;
    private PanelFeatureState mPreparedPanel;
    Runnable mShowActionModePopup;
    private View mStatusGuard;
    private ViewGroup mSubDecor;
    private boolean mSubDecorInstalled;
    private Rect mTempRect1;
    private Rect mTempRect2;
    private CharSequence mTitle;
    private TextView mTitleView;
    final Window mWindow;
    boolean mWindowNoTitle;
    ViewPropertyAnimatorCompat mFadeAnim = null;
    private boolean mHandleNativeActionModes = true;
    private int mLocalNightMode = -100;
    private final Runnable mInvalidatePanelMenuRunnable = new Runnable() { // from class: android.support.v7.app.AppCompatDelegateImpl.2
        @Override // java.lang.Runnable
        public void run() {
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl.mInvalidatePanelMenuFeatures & 1) != 0) {
                appCompatDelegateImpl.doInvalidatePanelMenu(0);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if ((appCompatDelegateImpl2.mInvalidatePanelMenuFeatures & 4096) != 0) {
                appCompatDelegateImpl2.doInvalidatePanelMenu(108);
            }
            AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
            appCompatDelegateImpl3.mInvalidatePanelMenuPosted = false;
            appCompatDelegateImpl3.mInvalidatePanelMenuFeatures = 0;
        }
    };

    void onSubDecorInstalled(ViewGroup viewGroup) {
    }

    static {
        IS_PRE_LOLLIPOP = Build.VERSION.SDK_INT < 21;
        sWindowBackgroundStyleable = new int[]{16842836};
        if (!IS_PRE_LOLLIPOP || sInstalledExceptionHandler) {
            return;
        }
        final Thread.UncaughtExceptionHandler defaultUncaughtExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { // from class: android.support.v7.app.AppCompatDelegateImpl.1
            @Override // java.lang.Thread.UncaughtExceptionHandler
            public void uncaughtException(Thread thread, Throwable th) {
                if (shouldWrapException(th)) {
                    Resources.NotFoundException notFoundException = new Resources.NotFoundException(th.getMessage() + AppCompatDelegateImpl.EXCEPTION_HANDLER_MESSAGE_SUFFIX);
                    notFoundException.initCause(th.getCause());
                    notFoundException.setStackTrace(th.getStackTrace());
                    defaultUncaughtExceptionHandler.uncaughtException(thread, notFoundException);
                    return;
                }
                defaultUncaughtExceptionHandler.uncaughtException(thread, th);
            }

            private boolean shouldWrapException(Throwable th) {
                String message;
                if (!(th instanceof Resources.NotFoundException) || (message = th.getMessage()) == null) {
                    return false;
                }
                return message.contains("drawable") || message.contains("Drawable");
            }
        });
        sInstalledExceptionHandler = true;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public AppCompatDelegateImpl(Context context, Window window, AppCompatCallback appCompatCallback) {
        this.mContext = context;
        this.mWindow = window;
        this.mAppCompatCallback = appCompatCallback;
        this.mOriginalWindowCallback = this.mWindow.getCallback();
        Window.Callback callback = this.mOriginalWindowCallback;
        if (callback instanceof AppCompatWindowCallback) {
            throw new IllegalStateException("AppCompat has already installed itself into the Window");
        }
        this.mAppCompatWindowCallback = new AppCompatWindowCallback(callback);
        this.mWindow.setCallback(this.mAppCompatWindowCallback);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context, (AttributeSet) null, sWindowBackgroundStyleable);
        Drawable drawableIfKnown = obtainStyledAttributes.getDrawableIfKnown(0);
        if (drawableIfKnown != null) {
            this.mWindow.setBackgroundDrawable(drawableIfKnown);
        }
        obtainStyledAttributes.recycle();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onCreate(Bundle bundle) {
        Window.Callback callback = this.mOriginalWindowCallback;
        if (callback instanceof Activity) {
            String str = null;
            try {
                str = NavUtils.getParentActivityName((Activity) callback);
            } catch (IllegalArgumentException unused) {
            }
            if (str != null) {
                ActionBar peekSupportActionBar = peekSupportActionBar();
                if (peekSupportActionBar == null) {
                    this.mEnableDefaultActionBarUp = true;
                } else {
                    peekSupportActionBar.setDefaultDisplayHomeAsUpEnabled(true);
                }
            }
        }
        if (bundle == null || this.mLocalNightMode != -100) {
            return;
        }
        this.mLocalNightMode = bundle.getInt(KEY_LOCAL_NIGHT_MODE, -100);
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onPostCreate(Bundle bundle) {
        ensureSubDecor();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public ActionBar getSupportActionBar() {
        initWindowDecorActionBar();
        return this.mActionBar;
    }

    final ActionBar peekSupportActionBar() {
        return this.mActionBar;
    }

    final Window.Callback getWindowCallback() {
        return this.mWindow.getCallback();
    }

    private void initWindowDecorActionBar() {
        ensureSubDecor();
        if (!this.mHasActionBar || this.mActionBar != null) {
            return;
        }
        Window.Callback callback = this.mOriginalWindowCallback;
        if (callback instanceof Activity) {
            this.mActionBar = new WindowDecorActionBar((Activity) callback, this.mOverlayActionBar);
        } else if (callback instanceof Dialog) {
            this.mActionBar = new WindowDecorActionBar((Dialog) callback);
        }
        ActionBar actionBar = this.mActionBar;
        if (actionBar == null) {
            return;
        }
        actionBar.setDefaultDisplayHomeAsUpEnabled(this.mEnableDefaultActionBarUp);
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void setSupportActionBar(Toolbar toolbar) {
        if (!(this.mOriginalWindowCallback instanceof Activity)) {
            return;
        }
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar instanceof WindowDecorActionBar) {
            throw new IllegalStateException("This Activity already has an action bar supplied by the window decor. Do not request Window.FEATURE_SUPPORT_ACTION_BAR and set windowActionBar to false in your theme to use a Toolbar instead.");
        }
        this.mMenuInflater = null;
        if (supportActionBar != null) {
            supportActionBar.onDestroy();
        }
        if (toolbar != null) {
            ToolbarActionBar toolbarActionBar = new ToolbarActionBar(toolbar, ((Activity) this.mOriginalWindowCallback).getTitle(), this.mAppCompatWindowCallback);
            this.mActionBar = toolbarActionBar;
            this.mWindow.setCallback(toolbarActionBar.getWrappedWindowCallback());
        } else {
            this.mActionBar = null;
            this.mWindow.setCallback(this.mAppCompatWindowCallback);
        }
        invalidateOptionsMenu();
    }

    final Context getActionBarThemedContext() {
        ActionBar supportActionBar = getSupportActionBar();
        Context themedContext = supportActionBar != null ? supportActionBar.getThemedContext() : null;
        return themedContext == null ? this.mContext : themedContext;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public MenuInflater getMenuInflater() {
        if (this.mMenuInflater == null) {
            initWindowDecorActionBar();
            ActionBar actionBar = this.mActionBar;
            this.mMenuInflater = new SupportMenuInflater(actionBar != null ? actionBar.getThemedContext() : this.mContext);
        }
        return this.mMenuInflater;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    @Nullable
    public <T extends View> T findViewById(@IdRes int i) {
        ensureSubDecor();
        return (T) this.mWindow.findViewById(i);
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onConfigurationChanged(Configuration configuration) {
        ActionBar supportActionBar;
        if (this.mHasActionBar && this.mSubDecorInstalled && (supportActionBar = getSupportActionBar()) != null) {
            supportActionBar.onConfigurationChanged(configuration);
        }
        AppCompatDrawableManager.get().onConfigurationChanged(this.mContext);
        applyDayNight();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onStart() {
        applyDayNight();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onStop() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(false);
        }
        AutoNightModeManager autoNightModeManager = this.mAutoNightModeManager;
        if (autoNightModeManager != null) {
            autoNightModeManager.cleanup();
        }
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onPostResume() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setShowHideAnimationEnabled(true);
        }
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void setContentView(View view) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void setContentView(int i) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        LayoutInflater.from(this.mContext).inflate(i, viewGroup);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void setContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ViewGroup viewGroup = (ViewGroup) this.mSubDecor.findViewById(16908290);
        viewGroup.removeAllViews();
        viewGroup.addView(view, layoutParams);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void addContentView(View view, ViewGroup.LayoutParams layoutParams) {
        ensureSubDecor();
        ((ViewGroup) this.mSubDecor.findViewById(16908290)).addView(view, layoutParams);
        this.mOriginalWindowCallback.onContentChanged();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onSaveInstanceState(Bundle bundle) {
        int i = this.mLocalNightMode;
        if (i != -100) {
            bundle.putInt(KEY_LOCAL_NIGHT_MODE, i);
        }
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void onDestroy() {
        if (this.mInvalidatePanelMenuPosted) {
            this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
        }
        this.mIsDestroyed = true;
        ActionBar actionBar = this.mActionBar;
        if (actionBar != null) {
            actionBar.onDestroy();
        }
        AutoNightModeManager autoNightModeManager = this.mAutoNightModeManager;
        if (autoNightModeManager != null) {
            autoNightModeManager.cleanup();
        }
    }

    private void ensureSubDecor() {
        if (!this.mSubDecorInstalled) {
            this.mSubDecor = createSubDecor();
            CharSequence title = getTitle();
            if (!TextUtils.isEmpty(title)) {
                DecorContentParent decorContentParent = this.mDecorContentParent;
                if (decorContentParent != null) {
                    decorContentParent.setWindowTitle(title);
                } else if (peekSupportActionBar() != null) {
                    peekSupportActionBar().setWindowTitle(title);
                } else {
                    TextView textView = this.mTitleView;
                    if (textView != null) {
                        textView.setText(title);
                    }
                }
            }
            applyFixedSizeWindow();
            onSubDecorInstalled(this.mSubDecor);
            this.mSubDecorInstalled = true;
            PanelFeatureState panelState = getPanelState(0, false);
            if (this.mIsDestroyed) {
                return;
            }
            if (panelState != null && panelState.menu != null) {
                return;
            }
            invalidatePanelMenu(108);
        }
    }

    private ViewGroup createSubDecor() {
        ViewGroup viewGroup;
        Context context;
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(C0441R.styleable.AppCompatTheme);
        if (!obtainStyledAttributes.hasValue(C0441R.styleable.AppCompatTheme_windowActionBar)) {
            obtainStyledAttributes.recycle();
            throw new IllegalStateException("You need to use a Theme.AppCompat theme (or descendant) with this activity.");
        }
        if (obtainStyledAttributes.getBoolean(C0441R.styleable.AppCompatTheme_windowNoTitle, false)) {
            requestWindowFeature(1);
        } else if (obtainStyledAttributes.getBoolean(C0441R.styleable.AppCompatTheme_windowActionBar, false)) {
            requestWindowFeature(108);
        }
        if (obtainStyledAttributes.getBoolean(C0441R.styleable.AppCompatTheme_windowActionBarOverlay, false)) {
            requestWindowFeature(109);
        }
        if (obtainStyledAttributes.getBoolean(C0441R.styleable.AppCompatTheme_windowActionModeOverlay, false)) {
            requestWindowFeature(10);
        }
        this.mIsFloating = obtainStyledAttributes.getBoolean(C0441R.styleable.AppCompatTheme_android_windowIsFloating, false);
        obtainStyledAttributes.recycle();
        this.mWindow.getDecorView();
        LayoutInflater from = LayoutInflater.from(this.mContext);
        if (!this.mWindowNoTitle) {
            if (this.mIsFloating) {
                viewGroup = (ViewGroup) from.inflate(C0441R.C0445layout.abc_dialog_title_material, (ViewGroup) null);
                this.mOverlayActionBar = false;
                this.mHasActionBar = false;
            } else if (this.mHasActionBar) {
                TypedValue typedValue = new TypedValue();
                this.mContext.getTheme().resolveAttribute(C0441R.attr.actionBarTheme, typedValue, true);
                int i = typedValue.resourceId;
                if (i != 0) {
                    context = new ContextThemeWrapper(this.mContext, i);
                } else {
                    context = this.mContext;
                }
                viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(C0441R.C0445layout.abc_screen_toolbar, (ViewGroup) null);
                this.mDecorContentParent = (DecorContentParent) viewGroup.findViewById(C0441R.C0444id.decor_content_parent);
                this.mDecorContentParent.setWindowCallback(getWindowCallback());
                if (this.mOverlayActionBar) {
                    this.mDecorContentParent.initFeature(109);
                }
                if (this.mFeatureProgress) {
                    this.mDecorContentParent.initFeature(2);
                }
                if (this.mFeatureIndeterminateProgress) {
                    this.mDecorContentParent.initFeature(5);
                }
            } else {
                viewGroup = null;
            }
        } else {
            if (this.mOverlayActionMode) {
                viewGroup = (ViewGroup) from.inflate(C0441R.C0445layout.abc_screen_simple_overlay_action_mode, (ViewGroup) null);
            } else {
                viewGroup = (ViewGroup) from.inflate(C0441R.C0445layout.abc_screen_simple, (ViewGroup) null);
            }
            if (Build.VERSION.SDK_INT >= 21) {
                ViewCompat.setOnApplyWindowInsetsListener(viewGroup, new OnApplyWindowInsetsListener() { // from class: android.support.v7.app.AppCompatDelegateImpl.3
                    @Override // android.support.p002v4.view.OnApplyWindowInsetsListener
                    public WindowInsetsCompat onApplyWindowInsets(View view, WindowInsetsCompat windowInsetsCompat) {
                        int systemWindowInsetTop = windowInsetsCompat.getSystemWindowInsetTop();
                        int updateStatusGuard = AppCompatDelegateImpl.this.updateStatusGuard(systemWindowInsetTop);
                        if (systemWindowInsetTop != updateStatusGuard) {
                            windowInsetsCompat = windowInsetsCompat.replaceSystemWindowInsets(windowInsetsCompat.getSystemWindowInsetLeft(), updateStatusGuard, windowInsetsCompat.getSystemWindowInsetRight(), windowInsetsCompat.getSystemWindowInsetBottom());
                        }
                        return ViewCompat.onApplyWindowInsets(view, windowInsetsCompat);
                    }
                });
            } else {
                ((FitWindowsViewGroup) viewGroup).setOnFitSystemWindowsListener(new FitWindowsViewGroup.OnFitSystemWindowsListener() { // from class: android.support.v7.app.AppCompatDelegateImpl.4
                    @Override // android.support.p005v7.widget.FitWindowsViewGroup.OnFitSystemWindowsListener
                    public void onFitSystemWindows(Rect rect) {
                        rect.top = AppCompatDelegateImpl.this.updateStatusGuard(rect.top);
                    }
                });
            }
        }
        if (viewGroup == null) {
            throw new IllegalArgumentException("AppCompat does not support the current theme features: { windowActionBar: " + this.mHasActionBar + ", windowActionBarOverlay: " + this.mOverlayActionBar + ", android:windowIsFloating: " + this.mIsFloating + ", windowActionModeOverlay: " + this.mOverlayActionMode + ", windowNoTitle: " + this.mWindowNoTitle + " }");
        }
        if (this.mDecorContentParent == null) {
            this.mTitleView = (TextView) viewGroup.findViewById(C0441R.C0444id.title);
        }
        ViewUtils.makeOptionalFitsSystemWindows(viewGroup);
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) viewGroup.findViewById(C0441R.C0444id.action_bar_activity_content);
        ViewGroup viewGroup2 = (ViewGroup) this.mWindow.findViewById(16908290);
        if (viewGroup2 != null) {
            while (viewGroup2.getChildCount() > 0) {
                View childAt = viewGroup2.getChildAt(0);
                viewGroup2.removeViewAt(0);
                contentFrameLayout.addView(childAt);
            }
            viewGroup2.setId(-1);
            contentFrameLayout.setId(16908290);
            if (viewGroup2 instanceof FrameLayout) {
                ((FrameLayout) viewGroup2).setForeground(null);
            }
        }
        this.mWindow.setContentView(viewGroup);
        contentFrameLayout.setAttachListener(new ContentFrameLayout.OnAttachListener() { // from class: android.support.v7.app.AppCompatDelegateImpl.5
            @Override // android.support.p005v7.widget.ContentFrameLayout.OnAttachListener
            public void onAttachedFromWindow() {
            }

            @Override // android.support.p005v7.widget.ContentFrameLayout.OnAttachListener
            public void onDetachedFromWindow() {
                AppCompatDelegateImpl.this.dismissPopups();
            }
        });
        return viewGroup;
    }

    private void applyFixedSizeWindow() {
        ContentFrameLayout contentFrameLayout = (ContentFrameLayout) this.mSubDecor.findViewById(16908290);
        View decorView = this.mWindow.getDecorView();
        contentFrameLayout.setDecorPadding(decorView.getPaddingLeft(), decorView.getPaddingTop(), decorView.getPaddingRight(), decorView.getPaddingBottom());
        TypedArray obtainStyledAttributes = this.mContext.obtainStyledAttributes(C0441R.styleable.AppCompatTheme);
        obtainStyledAttributes.getValue(C0441R.styleable.AppCompatTheme_windowMinWidthMajor, contentFrameLayout.getMinWidthMajor());
        obtainStyledAttributes.getValue(C0441R.styleable.AppCompatTheme_windowMinWidthMinor, contentFrameLayout.getMinWidthMinor());
        if (obtainStyledAttributes.hasValue(C0441R.styleable.AppCompatTheme_windowFixedWidthMajor)) {
            obtainStyledAttributes.getValue(C0441R.styleable.AppCompatTheme_windowFixedWidthMajor, contentFrameLayout.getFixedWidthMajor());
        }
        if (obtainStyledAttributes.hasValue(C0441R.styleable.AppCompatTheme_windowFixedWidthMinor)) {
            obtainStyledAttributes.getValue(C0441R.styleable.AppCompatTheme_windowFixedWidthMinor, contentFrameLayout.getFixedWidthMinor());
        }
        if (obtainStyledAttributes.hasValue(C0441R.styleable.AppCompatTheme_windowFixedHeightMajor)) {
            obtainStyledAttributes.getValue(C0441R.styleable.AppCompatTheme_windowFixedHeightMajor, contentFrameLayout.getFixedHeightMajor());
        }
        if (obtainStyledAttributes.hasValue(C0441R.styleable.AppCompatTheme_windowFixedHeightMinor)) {
            obtainStyledAttributes.getValue(C0441R.styleable.AppCompatTheme_windowFixedHeightMinor, contentFrameLayout.getFixedHeightMinor());
        }
        obtainStyledAttributes.recycle();
        contentFrameLayout.requestLayout();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public boolean requestWindowFeature(int i) {
        int sanitizeWindowFeatureId = sanitizeWindowFeatureId(i);
        if (!this.mWindowNoTitle || sanitizeWindowFeatureId != 108) {
            if (this.mHasActionBar && sanitizeWindowFeatureId == 1) {
                this.mHasActionBar = false;
            }
            if (sanitizeWindowFeatureId == 1) {
                throwFeatureRequestIfSubDecorInstalled();
                this.mWindowNoTitle = true;
                return true;
            } else if (sanitizeWindowFeatureId == 2) {
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureProgress = true;
                return true;
            } else if (sanitizeWindowFeatureId == 5) {
                throwFeatureRequestIfSubDecorInstalled();
                this.mFeatureIndeterminateProgress = true;
                return true;
            } else if (sanitizeWindowFeatureId == 10) {
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionMode = true;
                return true;
            } else if (sanitizeWindowFeatureId == 108) {
                throwFeatureRequestIfSubDecorInstalled();
                this.mHasActionBar = true;
                return true;
            } else if (sanitizeWindowFeatureId == 109) {
                throwFeatureRequestIfSubDecorInstalled();
                this.mOverlayActionBar = true;
                return true;
            } else {
                return this.mWindow.requestFeature(sanitizeWindowFeatureId);
            }
        }
        return false;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public boolean hasWindowFeature(int i) {
        boolean z;
        int sanitizeWindowFeatureId = sanitizeWindowFeatureId(i);
        if (sanitizeWindowFeatureId == 1) {
            z = this.mWindowNoTitle;
        } else if (sanitizeWindowFeatureId == 2) {
            z = this.mFeatureProgress;
        } else if (sanitizeWindowFeatureId == 5) {
            z = this.mFeatureIndeterminateProgress;
        } else if (sanitizeWindowFeatureId == 10) {
            z = this.mOverlayActionMode;
        } else if (sanitizeWindowFeatureId == 108) {
            z = this.mHasActionBar;
        } else {
            z = sanitizeWindowFeatureId != 109 ? false : this.mOverlayActionBar;
        }
        return z || this.mWindow.hasFeature(i);
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public final void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.setWindowTitle(charSequence);
        } else if (peekSupportActionBar() != null) {
            peekSupportActionBar().setWindowTitle(charSequence);
        } else {
            TextView textView = this.mTitleView;
            if (textView == null) {
                return;
            }
            textView.setText(charSequence);
        }
    }

    final CharSequence getTitle() {
        Window.Callback callback = this.mOriginalWindowCallback;
        if (callback instanceof Activity) {
            return ((Activity) callback).getTitle();
        }
        return this.mTitle;
    }

    void onPanelClosed(int i) {
        if (i == 108) {
            ActionBar supportActionBar = getSupportActionBar();
            if (supportActionBar == null) {
                return;
            }
            supportActionBar.dispatchMenuVisibilityChanged(false);
        } else if (i != 0) {
        } else {
            PanelFeatureState panelState = getPanelState(i, true);
            if (!panelState.isOpen) {
                return;
            }
            closePanel(panelState, false);
        }
    }

    void onMenuOpened(int i) {
        ActionBar supportActionBar;
        if (i != 108 || (supportActionBar = getSupportActionBar()) == null) {
            return;
        }
        supportActionBar.dispatchMenuVisibilityChanged(true);
    }

    @Override // android.support.p005v7.view.menu.MenuBuilder.Callback
    public boolean onMenuItemSelected(MenuBuilder menuBuilder, MenuItem menuItem) {
        PanelFeatureState findMenuPanel;
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback == null || this.mIsDestroyed || (findMenuPanel = findMenuPanel(menuBuilder.getRootMenu())) == null) {
            return false;
        }
        return windowCallback.onMenuItemSelected(findMenuPanel.featureId, menuItem);
    }

    @Override // android.support.p005v7.view.menu.MenuBuilder.Callback
    public void onMenuModeChange(MenuBuilder menuBuilder) {
        reopenMenu(menuBuilder, true);
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public ActionMode startSupportActionMode(@NonNull ActionMode.Callback callback) {
        AppCompatCallback appCompatCallback;
        if (callback == null) {
            throw new IllegalArgumentException("ActionMode callback can not be null.");
        }
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
        }
        ActionModeCallbackWrapperV9 actionModeCallbackWrapperV9 = new ActionModeCallbackWrapperV9(callback);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            this.mActionMode = supportActionBar.startActionMode(actionModeCallbackWrapperV9);
            ActionMode actionMode2 = this.mActionMode;
            if (actionMode2 != null && (appCompatCallback = this.mAppCompatCallback) != null) {
                appCompatCallback.onSupportActionModeStarted(actionMode2);
            }
        }
        if (this.mActionMode == null) {
            this.mActionMode = startSupportActionModeFromWindow(actionModeCallbackWrapperV9);
        }
        return this.mActionMode;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void invalidateOptionsMenu() {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null || !supportActionBar.invalidateOptionsMenu()) {
            invalidatePanelMenu(0);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0029  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    ActionMode startSupportActionModeFromWindow(@NonNull ActionMode.Callback callback) {
        ActionMode actionMode;
        Context context;
        ActionMode actionMode2;
        AppCompatCallback appCompatCallback;
        endOnGoingFadeAnimation();
        ActionMode actionMode3 = this.mActionMode;
        if (actionMode3 != null) {
            actionMode3.finish();
        }
        if (!(callback instanceof ActionModeCallbackWrapperV9)) {
            callback = new ActionModeCallbackWrapperV9(callback);
        }
        AppCompatCallback appCompatCallback2 = this.mAppCompatCallback;
        if (appCompatCallback2 != null && !this.mIsDestroyed) {
            try {
                actionMode = appCompatCallback2.onWindowStartingSupportActionMode(callback);
            } catch (AbstractMethodError unused) {
            }
            if (actionMode == null) {
                this.mActionMode = actionMode;
            } else {
                boolean z = true;
                if (this.mActionModeView == null) {
                    if (this.mIsFloating) {
                        TypedValue typedValue = new TypedValue();
                        Resources.Theme theme = this.mContext.getTheme();
                        theme.resolveAttribute(C0441R.attr.actionBarTheme, typedValue, true);
                        if (typedValue.resourceId != 0) {
                            Resources.Theme newTheme = this.mContext.getResources().newTheme();
                            newTheme.setTo(theme);
                            newTheme.applyStyle(typedValue.resourceId, true);
                            context = new ContextThemeWrapper(this.mContext, 0);
                            context.getTheme().setTo(newTheme);
                        } else {
                            context = this.mContext;
                        }
                        this.mActionModeView = new ActionBarContextView(context);
                        this.mActionModePopup = new PopupWindow(context, (AttributeSet) null, C0441R.attr.actionModePopupWindowStyle);
                        PopupWindowCompat.setWindowLayoutType(this.mActionModePopup, 2);
                        this.mActionModePopup.setContentView(this.mActionModeView);
                        this.mActionModePopup.setWidth(-1);
                        context.getTheme().resolveAttribute(C0441R.attr.actionBarSize, typedValue, true);
                        this.mActionModeView.setContentHeight(TypedValue.complexToDimensionPixelSize(typedValue.data, context.getResources().getDisplayMetrics()));
                        this.mActionModePopup.setHeight(-2);
                        this.mShowActionModePopup = new Runnable() { // from class: android.support.v7.app.AppCompatDelegateImpl.6
                            @Override // java.lang.Runnable
                            public void run() {
                                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                                appCompatDelegateImpl.mActionModePopup.showAtLocation(appCompatDelegateImpl.mActionModeView, 55, 0, 0);
                                AppCompatDelegateImpl.this.endOnGoingFadeAnimation();
                                if (AppCompatDelegateImpl.this.shouldAnimateActionModeView()) {
                                    AppCompatDelegateImpl.this.mActionModeView.setAlpha(0.0f);
                                    AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
                                    appCompatDelegateImpl2.mFadeAnim = ViewCompat.animate(appCompatDelegateImpl2.mActionModeView).alpha(1.0f);
                                    AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.AppCompatDelegateImpl.6.1
                                        @Override // android.support.p002v4.view.ViewPropertyAnimatorListenerAdapter, android.support.p002v4.view.ViewPropertyAnimatorListener
                                        public void onAnimationStart(View view) {
                                            AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                        }

                                        @Override // android.support.p002v4.view.ViewPropertyAnimatorListenerAdapter, android.support.p002v4.view.ViewPropertyAnimatorListener
                                        public void onAnimationEnd(View view) {
                                            AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                            AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                            AppCompatDelegateImpl.this.mFadeAnim = null;
                                        }
                                    });
                                    return;
                                }
                                AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                            }
                        };
                    } else {
                        ViewStubCompat viewStubCompat = (ViewStubCompat) this.mSubDecor.findViewById(C0441R.C0444id.action_mode_bar_stub);
                        if (viewStubCompat != null) {
                            viewStubCompat.setLayoutInflater(LayoutInflater.from(getActionBarThemedContext()));
                            this.mActionModeView = (ActionBarContextView) viewStubCompat.inflate();
                        }
                    }
                }
                if (this.mActionModeView != null) {
                    endOnGoingFadeAnimation();
                    this.mActionModeView.killMode();
                    Context context2 = this.mActionModeView.getContext();
                    ActionBarContextView actionBarContextView = this.mActionModeView;
                    if (this.mActionModePopup != null) {
                        z = false;
                    }
                    StandaloneActionMode standaloneActionMode = new StandaloneActionMode(context2, actionBarContextView, callback, z);
                    if (callback.onCreateActionMode(standaloneActionMode, standaloneActionMode.getMenu())) {
                        standaloneActionMode.invalidate();
                        this.mActionModeView.initForMode(standaloneActionMode);
                        this.mActionMode = standaloneActionMode;
                        if (shouldAnimateActionModeView()) {
                            this.mActionModeView.setAlpha(0.0f);
                            this.mFadeAnim = ViewCompat.animate(this.mActionModeView).alpha(1.0f);
                            this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.AppCompatDelegateImpl.7
                                @Override // android.support.p002v4.view.ViewPropertyAnimatorListenerAdapter, android.support.p002v4.view.ViewPropertyAnimatorListener
                                public void onAnimationStart(View view) {
                                    AppCompatDelegateImpl.this.mActionModeView.setVisibility(0);
                                    AppCompatDelegateImpl.this.mActionModeView.sendAccessibilityEvent(32);
                                    if (AppCompatDelegateImpl.this.mActionModeView.getParent() instanceof View) {
                                        ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent());
                                    }
                                }

                                @Override // android.support.p002v4.view.ViewPropertyAnimatorListenerAdapter, android.support.p002v4.view.ViewPropertyAnimatorListener
                                public void onAnimationEnd(View view) {
                                    AppCompatDelegateImpl.this.mActionModeView.setAlpha(1.0f);
                                    AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                                    AppCompatDelegateImpl.this.mFadeAnim = null;
                                }
                            });
                        } else {
                            this.mActionModeView.setAlpha(1.0f);
                            this.mActionModeView.setVisibility(0);
                            this.mActionModeView.sendAccessibilityEvent(32);
                            if (this.mActionModeView.getParent() instanceof View) {
                                ViewCompat.requestApplyInsets((View) this.mActionModeView.getParent());
                            }
                        }
                        if (this.mActionModePopup != null) {
                            this.mWindow.getDecorView().post(this.mShowActionModePopup);
                        }
                    } else {
                        this.mActionMode = null;
                    }
                }
            }
            actionMode2 = this.mActionMode;
            if (actionMode2 != null && (appCompatCallback = this.mAppCompatCallback) != null) {
                appCompatCallback.onSupportActionModeStarted(actionMode2);
            }
            return this.mActionMode;
        }
        actionMode = null;
        if (actionMode == null) {
        }
        actionMode2 = this.mActionMode;
        if (actionMode2 != null) {
            appCompatCallback.onSupportActionModeStarted(actionMode2);
        }
        return this.mActionMode;
    }

    final boolean shouldAnimateActionModeView() {
        ViewGroup viewGroup;
        return this.mSubDecorInstalled && (viewGroup = this.mSubDecor) != null && ViewCompat.isLaidOut(viewGroup);
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void setHandleNativeActionModesEnabled(boolean z) {
        this.mHandleNativeActionModes = z;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public boolean isHandleNativeActionModesEnabled() {
        return this.mHandleNativeActionModes;
    }

    void endOnGoingFadeAnimation() {
        ViewPropertyAnimatorCompat viewPropertyAnimatorCompat = this.mFadeAnim;
        if (viewPropertyAnimatorCompat != null) {
            viewPropertyAnimatorCompat.cancel();
        }
    }

    boolean onBackPressed() {
        ActionMode actionMode = this.mActionMode;
        if (actionMode != null) {
            actionMode.finish();
            return true;
        }
        ActionBar supportActionBar = getSupportActionBar();
        return supportActionBar != null && supportActionBar.collapseActionView();
    }

    boolean onKeyShortcut(int i, KeyEvent keyEvent) {
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar == null || !supportActionBar.onKeyShortcut(i, keyEvent)) {
            PanelFeatureState panelFeatureState = this.mPreparedPanel;
            if (panelFeatureState != null && performPanelShortcut(panelFeatureState, keyEvent.getKeyCode(), keyEvent, 1)) {
                PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
                if (panelFeatureState2 != null) {
                    panelFeatureState2.isHandled = true;
                }
                return true;
            }
            if (this.mPreparedPanel == null) {
                PanelFeatureState panelState = getPanelState(0, true);
                preparePanel(panelState, keyEvent);
                boolean performPanelShortcut = performPanelShortcut(panelState, keyEvent.getKeyCode(), keyEvent, 1);
                panelState.isPrepared = false;
                if (performPanelShortcut) {
                    return true;
                }
            }
            return false;
        }
        return true;
    }

    boolean dispatchKeyEvent(KeyEvent keyEvent) {
        View decorView;
        Window.Callback callback = this.mOriginalWindowCallback;
        boolean z = true;
        if (((callback instanceof KeyEventDispatcher.Component) || (callback instanceof AppCompatDialog)) && (decorView = this.mWindow.getDecorView()) != null && KeyEventDispatcher.dispatchBeforeHierarchy(decorView, keyEvent)) {
            return true;
        }
        if (keyEvent.getKeyCode() == 82 && this.mOriginalWindowCallback.dispatchKeyEvent(keyEvent)) {
            return true;
        }
        int keyCode = keyEvent.getKeyCode();
        if (keyEvent.getAction() != 0) {
            z = false;
        }
        return z ? onKeyDown(keyCode, keyEvent) : onKeyUp(keyCode, keyEvent);
    }

    boolean onKeyUp(int i, KeyEvent keyEvent) {
        if (i == 4) {
            boolean z = this.mLongPressBackDown;
            this.mLongPressBackDown = false;
            PanelFeatureState panelState = getPanelState(0, false);
            if (panelState != null && panelState.isOpen) {
                if (!z) {
                    closePanel(panelState, true);
                }
                return true;
            } else if (onBackPressed()) {
                return true;
            }
        } else if (i == 82) {
            onKeyUpPanel(0, keyEvent);
            return true;
        }
        return false;
    }

    boolean onKeyDown(int i, KeyEvent keyEvent) {
        boolean z = true;
        if (i == 4) {
            if ((keyEvent.getFlags() & 128) == 0) {
                z = false;
            }
            this.mLongPressBackDown = z;
        } else if (i == 82) {
            onKeyDownPanel(0, keyEvent);
            return true;
        }
        return false;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public View createView(View view, String str, @NonNull Context context, @NonNull AttributeSet attributeSet) {
        boolean z;
        boolean z2 = false;
        if (this.mAppCompatViewInflater == null) {
            String string = this.mContext.obtainStyledAttributes(C0441R.styleable.AppCompatTheme).getString(C0441R.styleable.AppCompatTheme_viewInflaterClass);
            if (string == null || AppCompatViewInflater.class.getName().equals(string)) {
                this.mAppCompatViewInflater = new AppCompatViewInflater();
            } else {
                try {
                    this.mAppCompatViewInflater = (AppCompatViewInflater) Class.forName(string).getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
                } catch (Throwable th) {
                    Log.i("AppCompatDelegate", "Failed to instantiate custom view inflater " + string + ". Falling back to default.", th);
                    this.mAppCompatViewInflater = new AppCompatViewInflater();
                }
            }
        }
        if (IS_PRE_LOLLIPOP) {
            if (attributeSet instanceof XmlPullParser) {
                if (((XmlPullParser) attributeSet).getDepth() > 1) {
                    z2 = true;
                }
            } else {
                z2 = shouldInheritContext((ViewParent) view);
            }
            z = z2;
        } else {
            z = false;
        }
        return this.mAppCompatViewInflater.createView(view, str, context, attributeSet, z, IS_PRE_LOLLIPOP, true, VectorEnabledTintResources.shouldBeUsed());
    }

    private boolean shouldInheritContext(ViewParent viewParent) {
        if (viewParent == null) {
            return false;
        }
        View decorView = this.mWindow.getDecorView();
        while (viewParent != null) {
            if (viewParent == decorView || !(viewParent instanceof View) || ViewCompat.isAttachedToWindow((View) viewParent)) {
                return false;
            }
            viewParent = viewParent.getParent();
        }
        return true;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void installViewFactory() {
        LayoutInflater from = LayoutInflater.from(this.mContext);
        if (from.getFactory() == null) {
            LayoutInflaterCompat.setFactory2(from, this);
        } else if (from.getFactory2() instanceof AppCompatDelegateImpl) {
        } else {
            Log.i("AppCompatDelegate", "The Activity's LayoutInflater already has a Factory installed so we can not install AppCompat's");
        }
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        return createView(view, str, context, attributeSet);
    }

    @Override // android.view.LayoutInflater.Factory
    public View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }

    private void openPanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        int i;
        ViewGroup.LayoutParams layoutParams;
        if (panelFeatureState.isOpen || this.mIsDestroyed) {
            return;
        }
        if (panelFeatureState.featureId == 0) {
            if ((this.mContext.getResources().getConfiguration().screenLayout & 15) == 4) {
                return;
            }
        }
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !windowCallback.onMenuOpened(panelFeatureState.featureId, panelFeatureState.menu)) {
            closePanel(panelFeatureState, true);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager == null || !preparePanel(panelFeatureState, keyEvent)) {
            return;
        }
        if (panelFeatureState.decorView == null || panelFeatureState.refreshDecorView) {
            ViewGroup viewGroup = panelFeatureState.decorView;
            if (viewGroup == null) {
                if (!initializePanelDecor(panelFeatureState) || panelFeatureState.decorView == null) {
                    return;
                }
            } else if (panelFeatureState.refreshDecorView && viewGroup.getChildCount() > 0) {
                panelFeatureState.decorView.removeAllViews();
            }
            if (!initializePanelContent(panelFeatureState) || !panelFeatureState.hasPanelItems()) {
                return;
            }
            ViewGroup.LayoutParams layoutParams2 = panelFeatureState.shownPanelView.getLayoutParams();
            if (layoutParams2 == null) {
                layoutParams2 = new ViewGroup.LayoutParams(-2, -2);
            }
            panelFeatureState.decorView.setBackgroundResource(panelFeatureState.background);
            ViewParent parent = panelFeatureState.shownPanelView.getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ((ViewGroup) parent).removeView(panelFeatureState.shownPanelView);
            }
            panelFeatureState.decorView.addView(panelFeatureState.shownPanelView, layoutParams2);
            if (!panelFeatureState.shownPanelView.hasFocus()) {
                panelFeatureState.shownPanelView.requestFocus();
            }
        } else {
            View view = panelFeatureState.createdPanelView;
            if (view != null && (layoutParams = view.getLayoutParams()) != null && layoutParams.width == -1) {
                i = -1;
                panelFeatureState.isHandled = false;
                WindowManager.LayoutParams layoutParams3 = new WindowManager.LayoutParams(i, -2, panelFeatureState.f33x, panelFeatureState.f34y, 1002, 8519680, -3);
                layoutParams3.gravity = panelFeatureState.gravity;
                layoutParams3.windowAnimations = panelFeatureState.windowAnimations;
                windowManager.addView(panelFeatureState.decorView, layoutParams3);
                panelFeatureState.isOpen = true;
            }
        }
        i = -2;
        panelFeatureState.isHandled = false;
        WindowManager.LayoutParams layoutParams32 = new WindowManager.LayoutParams(i, -2, panelFeatureState.f33x, panelFeatureState.f34y, 1002, 8519680, -3);
        layoutParams32.gravity = panelFeatureState.gravity;
        layoutParams32.windowAnimations = panelFeatureState.windowAnimations;
        windowManager.addView(panelFeatureState.decorView, layoutParams32);
        panelFeatureState.isOpen = true;
    }

    private boolean initializePanelDecor(PanelFeatureState panelFeatureState) {
        panelFeatureState.setStyle(getActionBarThemedContext());
        panelFeatureState.decorView = new ListMenuDecorView(panelFeatureState.listPresenterContext);
        panelFeatureState.gravity = 81;
        return true;
    }

    private void reopenMenu(MenuBuilder menuBuilder, boolean z) {
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null && decorContentParent.canShowOverflowMenu() && (!ViewConfiguration.get(this.mContext).hasPermanentMenuKey() || this.mDecorContentParent.isOverflowMenuShowPending())) {
            Window.Callback windowCallback = getWindowCallback();
            if (!this.mDecorContentParent.isOverflowMenuShowing() || !z) {
                if (windowCallback == null || this.mIsDestroyed) {
                    return;
                }
                if (this.mInvalidatePanelMenuPosted && (this.mInvalidatePanelMenuFeatures & 1) != 0) {
                    this.mWindow.getDecorView().removeCallbacks(this.mInvalidatePanelMenuRunnable);
                    this.mInvalidatePanelMenuRunnable.run();
                }
                PanelFeatureState panelState = getPanelState(0, true);
                MenuBuilder menuBuilder2 = panelState.menu;
                if (menuBuilder2 == null || panelState.refreshMenuContent || !windowCallback.onPreparePanel(0, panelState.createdPanelView, menuBuilder2)) {
                    return;
                }
                windowCallback.onMenuOpened(108, panelState.menu);
                this.mDecorContentParent.showOverflowMenu();
                return;
            }
            this.mDecorContentParent.hideOverflowMenu();
            if (this.mIsDestroyed) {
                return;
            }
            windowCallback.onPanelClosed(108, getPanelState(0, true).menu);
            return;
        }
        PanelFeatureState panelState2 = getPanelState(0, true);
        panelState2.refreshDecorView = true;
        closePanel(panelState2, false);
        openPanel(panelState2, null);
    }

    private boolean initializePanelMenu(PanelFeatureState panelFeatureState) {
        Context context = this.mContext;
        int i = panelFeatureState.featureId;
        if ((i == 0 || i == 108) && this.mDecorContentParent != null) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme theme = context.getTheme();
            theme.resolveAttribute(C0441R.attr.actionBarTheme, typedValue, true);
            Resources.Theme theme2 = null;
            if (typedValue.resourceId != 0) {
                theme2 = context.getResources().newTheme();
                theme2.setTo(theme);
                theme2.applyStyle(typedValue.resourceId, true);
                theme2.resolveAttribute(C0441R.attr.actionBarWidgetTheme, typedValue, true);
            } else {
                theme.resolveAttribute(C0441R.attr.actionBarWidgetTheme, typedValue, true);
            }
            if (typedValue.resourceId != 0) {
                if (theme2 == null) {
                    theme2 = context.getResources().newTheme();
                    theme2.setTo(theme);
                }
                theme2.applyStyle(typedValue.resourceId, true);
            }
            if (theme2 != null) {
                ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
                contextThemeWrapper.getTheme().setTo(theme2);
                context = contextThemeWrapper;
            }
        }
        MenuBuilder menuBuilder = new MenuBuilder(context);
        menuBuilder.setCallback(this);
        panelFeatureState.setMenu(menuBuilder);
        return true;
    }

    private boolean initializePanelContent(PanelFeatureState panelFeatureState) {
        View view = panelFeatureState.createdPanelView;
        if (view != null) {
            panelFeatureState.shownPanelView = view;
            return true;
        } else if (panelFeatureState.menu == null) {
            return false;
        } else {
            if (this.mPanelMenuPresenterCallback == null) {
                this.mPanelMenuPresenterCallback = new PanelMenuPresenterCallback();
            }
            panelFeatureState.shownPanelView = (View) panelFeatureState.getListMenuView(this.mPanelMenuPresenterCallback);
            return panelFeatureState.shownPanelView != null;
        }
    }

    private boolean preparePanel(PanelFeatureState panelFeatureState, KeyEvent keyEvent) {
        DecorContentParent decorContentParent;
        DecorContentParent decorContentParent2;
        DecorContentParent decorContentParent3;
        if (this.mIsDestroyed) {
            return false;
        }
        if (panelFeatureState.isPrepared) {
            return true;
        }
        PanelFeatureState panelFeatureState2 = this.mPreparedPanel;
        if (panelFeatureState2 != null && panelFeatureState2 != panelFeatureState) {
            closePanel(panelFeatureState2, false);
        }
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null) {
            panelFeatureState.createdPanelView = windowCallback.onCreatePanelView(panelFeatureState.featureId);
        }
        int i = panelFeatureState.featureId;
        boolean z = i == 0 || i == 108;
        if (z && (decorContentParent3 = this.mDecorContentParent) != null) {
            decorContentParent3.setMenuPrepared();
        }
        if (panelFeatureState.createdPanelView == null && (!z || !(peekSupportActionBar() instanceof ToolbarActionBar))) {
            if (panelFeatureState.menu == null || panelFeatureState.refreshMenuContent) {
                if (panelFeatureState.menu == null && (!initializePanelMenu(panelFeatureState) || panelFeatureState.menu == null)) {
                    return false;
                }
                if (z && this.mDecorContentParent != null) {
                    if (this.mActionMenuPresenterCallback == null) {
                        this.mActionMenuPresenterCallback = new ActionMenuPresenterCallback();
                    }
                    this.mDecorContentParent.setMenu(panelFeatureState.menu, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.stopDispatchingItemsChanged();
                if (!windowCallback.onCreatePanelMenu(panelFeatureState.featureId, panelFeatureState.menu)) {
                    panelFeatureState.setMenu(null);
                    if (z && (decorContentParent = this.mDecorContentParent) != null) {
                        decorContentParent.setMenu(null, this.mActionMenuPresenterCallback);
                    }
                    return false;
                }
                panelFeatureState.refreshMenuContent = false;
            }
            panelFeatureState.menu.stopDispatchingItemsChanged();
            Bundle bundle = panelFeatureState.frozenActionViewState;
            if (bundle != null) {
                panelFeatureState.menu.restoreActionViewStates(bundle);
                panelFeatureState.frozenActionViewState = null;
            }
            if (!windowCallback.onPreparePanel(0, panelFeatureState.createdPanelView, panelFeatureState.menu)) {
                if (z && (decorContentParent2 = this.mDecorContentParent) != null) {
                    decorContentParent2.setMenu(null, this.mActionMenuPresenterCallback);
                }
                panelFeatureState.menu.startDispatchingItemsChanged();
                return false;
            }
            panelFeatureState.qwertyMode = KeyCharacterMap.load(keyEvent != null ? keyEvent.getDeviceId() : -1).getKeyboardType() != 1;
            panelFeatureState.menu.setQwertyMode(panelFeatureState.qwertyMode);
            panelFeatureState.menu.startDispatchingItemsChanged();
        }
        panelFeatureState.isPrepared = true;
        panelFeatureState.isHandled = false;
        this.mPreparedPanel = panelFeatureState;
        return true;
    }

    void checkCloseActionMenu(MenuBuilder menuBuilder) {
        if (this.mClosingActionMenu) {
            return;
        }
        this.mClosingActionMenu = true;
        this.mDecorContentParent.dismissPopups();
        Window.Callback windowCallback = getWindowCallback();
        if (windowCallback != null && !this.mIsDestroyed) {
            windowCallback.onPanelClosed(108, menuBuilder);
        }
        this.mClosingActionMenu = false;
    }

    void closePanel(int i) {
        closePanel(getPanelState(i, true), true);
    }

    void closePanel(PanelFeatureState panelFeatureState, boolean z) {
        ViewGroup viewGroup;
        DecorContentParent decorContentParent;
        if (z && panelFeatureState.featureId == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.isOverflowMenuShowing()) {
            checkCloseActionMenu(panelFeatureState.menu);
            return;
        }
        WindowManager windowManager = (WindowManager) this.mContext.getSystemService("window");
        if (windowManager != null && panelFeatureState.isOpen && (viewGroup = panelFeatureState.decorView) != null) {
            windowManager.removeView(viewGroup);
            if (z) {
                callOnPanelClosed(panelFeatureState.featureId, panelFeatureState, null);
            }
        }
        panelFeatureState.isPrepared = false;
        panelFeatureState.isHandled = false;
        panelFeatureState.isOpen = false;
        panelFeatureState.shownPanelView = null;
        panelFeatureState.refreshDecorView = true;
        if (this.mPreparedPanel != panelFeatureState) {
            return;
        }
        this.mPreparedPanel = null;
    }

    private boolean onKeyDownPanel(int i, KeyEvent keyEvent) {
        if (keyEvent.getRepeatCount() == 0) {
            PanelFeatureState panelState = getPanelState(i, true);
            if (panelState.isOpen) {
                return false;
            }
            return preparePanel(panelState, keyEvent);
        }
        return false;
    }

    private boolean onKeyUpPanel(int i, KeyEvent keyEvent) {
        boolean z;
        boolean z2;
        DecorContentParent decorContentParent;
        if (this.mActionMode != null) {
            return false;
        }
        PanelFeatureState panelState = getPanelState(i, true);
        if (i == 0 && (decorContentParent = this.mDecorContentParent) != null && decorContentParent.canShowOverflowMenu() && !ViewConfiguration.get(this.mContext).hasPermanentMenuKey()) {
            if (!this.mDecorContentParent.isOverflowMenuShowing()) {
                if (!this.mIsDestroyed && preparePanel(panelState, keyEvent)) {
                    z = this.mDecorContentParent.showOverflowMenu();
                }
                z = false;
            } else {
                z = this.mDecorContentParent.hideOverflowMenu();
            }
        } else if (panelState.isOpen || panelState.isHandled) {
            z = panelState.isOpen;
            closePanel(panelState, true);
        } else {
            if (panelState.isPrepared) {
                if (panelState.refreshMenuContent) {
                    panelState.isPrepared = false;
                    z2 = preparePanel(panelState, keyEvent);
                } else {
                    z2 = true;
                }
                if (z2) {
                    openPanel(panelState, keyEvent);
                    z = true;
                }
            }
            z = false;
        }
        if (z) {
            AudioManager audioManager = (AudioManager) this.mContext.getSystemService("audio");
            if (audioManager != null) {
                audioManager.playSoundEffect(0);
            } else {
                Log.w("AppCompatDelegate", "Couldn't get audio manager");
            }
        }
        return z;
    }

    void callOnPanelClosed(int i, PanelFeatureState panelFeatureState, Menu menu) {
        if (menu == null) {
            if (panelFeatureState == null && i >= 0) {
                PanelFeatureState[] panelFeatureStateArr = this.mPanels;
                if (i < panelFeatureStateArr.length) {
                    panelFeatureState = panelFeatureStateArr[i];
                }
            }
            if (panelFeatureState != null) {
                menu = panelFeatureState.menu;
            }
        }
        if ((panelFeatureState == null || panelFeatureState.isOpen) && !this.mIsDestroyed) {
            this.mOriginalWindowCallback.onPanelClosed(i, menu);
        }
    }

    PanelFeatureState findMenuPanel(Menu menu) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        int length = panelFeatureStateArr != null ? panelFeatureStateArr.length : 0;
        for (int i = 0; i < length; i++) {
            PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
            if (panelFeatureState != null && panelFeatureState.menu == menu) {
                return panelFeatureState;
            }
        }
        return null;
    }

    protected PanelFeatureState getPanelState(int i, boolean z) {
        PanelFeatureState[] panelFeatureStateArr = this.mPanels;
        if (panelFeatureStateArr == null || panelFeatureStateArr.length <= i) {
            PanelFeatureState[] panelFeatureStateArr2 = new PanelFeatureState[i + 1];
            if (panelFeatureStateArr != null) {
                System.arraycopy(panelFeatureStateArr, 0, panelFeatureStateArr2, 0, panelFeatureStateArr.length);
            }
            this.mPanels = panelFeatureStateArr2;
            panelFeatureStateArr = panelFeatureStateArr2;
        }
        PanelFeatureState panelFeatureState = panelFeatureStateArr[i];
        if (panelFeatureState == null) {
            PanelFeatureState panelFeatureState2 = new PanelFeatureState(i);
            panelFeatureStateArr[i] = panelFeatureState2;
            return panelFeatureState2;
        }
        return panelFeatureState;
    }

    private boolean performPanelShortcut(PanelFeatureState panelFeatureState, int i, KeyEvent keyEvent, int i2) {
        MenuBuilder menuBuilder;
        boolean z = false;
        if (keyEvent.isSystem()) {
            return false;
        }
        if ((panelFeatureState.isPrepared || preparePanel(panelFeatureState, keyEvent)) && (menuBuilder = panelFeatureState.menu) != null) {
            z = menuBuilder.performShortcut(i, keyEvent, i2);
        }
        if (z && (i2 & 1) == 0 && this.mDecorContentParent == null) {
            closePanel(panelFeatureState, true);
        }
        return z;
    }

    private void invalidatePanelMenu(int i) {
        this.mInvalidatePanelMenuFeatures = (1 << i) | this.mInvalidatePanelMenuFeatures;
        if (!this.mInvalidatePanelMenuPosted) {
            ViewCompat.postOnAnimation(this.mWindow.getDecorView(), this.mInvalidatePanelMenuRunnable);
            this.mInvalidatePanelMenuPosted = true;
        }
    }

    void doInvalidatePanelMenu(int i) {
        PanelFeatureState panelState;
        PanelFeatureState panelState2 = getPanelState(i, true);
        if (panelState2.menu != null) {
            Bundle bundle = new Bundle();
            panelState2.menu.saveActionViewStates(bundle);
            if (bundle.size() > 0) {
                panelState2.frozenActionViewState = bundle;
            }
            panelState2.menu.stopDispatchingItemsChanged();
            panelState2.menu.clear();
        }
        panelState2.refreshMenuContent = true;
        panelState2.refreshDecorView = true;
        if ((i != 108 && i != 0) || this.mDecorContentParent == null || (panelState = getPanelState(0, false)) == null) {
            return;
        }
        panelState.isPrepared = false;
        preparePanel(panelState, null);
    }

    int updateStatusGuard(int i) {
        boolean z;
        boolean z2;
        ActionBarContextView actionBarContextView = this.mActionModeView;
        int i2 = 0;
        if (actionBarContextView == null || !(actionBarContextView.getLayoutParams() instanceof ViewGroup.MarginLayoutParams)) {
            z = false;
        } else {
            ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) this.mActionModeView.getLayoutParams();
            z = true;
            if (this.mActionModeView.isShown()) {
                if (this.mTempRect1 == null) {
                    this.mTempRect1 = new Rect();
                    this.mTempRect2 = new Rect();
                }
                Rect rect = this.mTempRect1;
                Rect rect2 = this.mTempRect2;
                rect.set(0, i, 0, 0);
                ViewUtils.computeFitSystemWindows(this.mSubDecor, rect, rect2);
                if (marginLayoutParams.topMargin != (rect2.top == 0 ? i : 0)) {
                    marginLayoutParams.topMargin = i;
                    View view = this.mStatusGuard;
                    if (view == null) {
                        this.mStatusGuard = new View(this.mContext);
                        this.mStatusGuard.setBackgroundColor(this.mContext.getResources().getColor(C0441R.color.abc_input_method_navigation_guard));
                        this.mSubDecor.addView(this.mStatusGuard, -1, new ViewGroup.LayoutParams(-1, i));
                    } else {
                        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                        if (layoutParams.height != i) {
                            layoutParams.height = i;
                            this.mStatusGuard.setLayoutParams(layoutParams);
                        }
                    }
                    z2 = true;
                } else {
                    z2 = false;
                }
                if (this.mStatusGuard == null) {
                    z = false;
                }
                if (!this.mOverlayActionMode && z) {
                    i = 0;
                }
            } else {
                if (marginLayoutParams.topMargin != 0) {
                    marginLayoutParams.topMargin = 0;
                    z2 = true;
                } else {
                    z2 = false;
                }
                z = false;
            }
            if (z2) {
                this.mActionModeView.setLayoutParams(marginLayoutParams);
            }
        }
        View view2 = this.mStatusGuard;
        if (view2 != null) {
            if (!z) {
                i2 = 8;
            }
            view2.setVisibility(i2);
        }
        return i;
    }

    private void throwFeatureRequestIfSubDecorInstalled() {
        if (!this.mSubDecorInstalled) {
            return;
        }
        throw new AndroidRuntimeException("Window feature must be requested before adding content");
    }

    private int sanitizeWindowFeatureId(int i) {
        if (i == 8) {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR id when requesting this feature.");
            return 108;
        } else if (i != 9) {
            return i;
        } else {
            Log.i("AppCompatDelegate", "You should now use the AppCompatDelegate.FEATURE_SUPPORT_ACTION_BAR_OVERLAY id when requesting this feature.");
            return 109;
        }
    }

    ViewGroup getSubDecor() {
        return this.mSubDecor;
    }

    void dismissPopups() {
        MenuBuilder menuBuilder;
        DecorContentParent decorContentParent = this.mDecorContentParent;
        if (decorContentParent != null) {
            decorContentParent.dismissPopups();
        }
        if (this.mActionModePopup != null) {
            this.mWindow.getDecorView().removeCallbacks(this.mShowActionModePopup);
            if (this.mActionModePopup.isShowing()) {
                try {
                    this.mActionModePopup.dismiss();
                } catch (IllegalArgumentException unused) {
                }
            }
            this.mActionModePopup = null;
        }
        endOnGoingFadeAnimation();
        PanelFeatureState panelState = getPanelState(0, false);
        if (panelState == null || (menuBuilder = panelState.menu) == null) {
            return;
        }
        menuBuilder.close();
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public boolean applyDayNight() {
        int nightMode = getNightMode();
        int mapNightMode = mapNightMode(nightMode);
        boolean updateForNightMode = mapNightMode != -1 ? updateForNightMode(mapNightMode) : false;
        if (nightMode == 0) {
            ensureAutoNightModeManager();
            this.mAutoNightModeManager.setup();
        }
        this.mApplyDayNightCalled = true;
        return updateForNightMode;
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public void setLocalNightMode(int i) {
        if (i == -1 || i == 0 || i == 1 || i == 2) {
            if (this.mLocalNightMode == i) {
                return;
            }
            this.mLocalNightMode = i;
            if (!this.mApplyDayNightCalled) {
                return;
            }
            applyDayNight();
            return;
        }
        Log.i("AppCompatDelegate", "setLocalNightMode() called with an unknown mode");
    }

    int mapNightMode(int i) {
        if (i != -100) {
            if (i != 0) {
                return i;
            }
            if (Build.VERSION.SDK_INT >= 23 && ((UiModeManager) this.mContext.getSystemService(UiModeManager.class)).getNightMode() == 0) {
                return -1;
            }
            ensureAutoNightModeManager();
            return this.mAutoNightModeManager.getApplyableNightMode();
        }
        return -1;
    }

    private int getNightMode() {
        int i = this.mLocalNightMode;
        return i != -100 ? i : AppCompatDelegate.getDefaultNightMode();
    }

    private boolean updateForNightMode(int i) {
        Resources resources = this.mContext.getResources();
        Configuration configuration = resources.getConfiguration();
        int i2 = configuration.uiMode & 48;
        int i3 = i == 2 ? 32 : 16;
        if (i2 != i3) {
            if (shouldRecreateOnNightModeChange()) {
                ((Activity) this.mContext).recreate();
                return true;
            }
            Configuration configuration2 = new Configuration(configuration);
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            configuration2.uiMode = i3 | (configuration2.uiMode & (-49));
            resources.updateConfiguration(configuration2, displayMetrics);
            if (Build.VERSION.SDK_INT >= 26) {
                return true;
            }
            ResourcesFlusher.flush(resources);
            return true;
        }
        return false;
    }

    private void ensureAutoNightModeManager() {
        if (this.mAutoNightModeManager == null) {
            this.mAutoNightModeManager = new AutoNightModeManager(TwilightManager.getInstance(this.mContext));
        }
    }

    @VisibleForTesting
    final AutoNightModeManager getAutoNightModeManager() {
        ensureAutoNightModeManager();
        return this.mAutoNightModeManager;
    }

    private boolean shouldRecreateOnNightModeChange() {
        if (this.mApplyDayNightCalled) {
            Context context = this.mContext;
            if (context instanceof Activity) {
                try {
                    return (context.getPackageManager().getActivityInfo(new ComponentName(this.mContext, this.mContext.getClass()), 0).configChanges & 512) == 0;
                } catch (PackageManager.NameNotFoundException e) {
                    Log.d("AppCompatDelegate", "Exception while getting ActivityInfo", e);
                    return true;
                }
            }
        }
        return false;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ActionModeCallbackWrapperV9 */
    /* loaded from: classes2.dex */
    public class ActionModeCallbackWrapperV9 implements ActionMode.Callback {
        private ActionMode.Callback mWrapped;

        public ActionModeCallbackWrapperV9(ActionMode.Callback callback) {
            this.mWrapped = callback;
        }

        @Override // android.support.p005v7.view.ActionMode.Callback
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onCreateActionMode(actionMode, menu);
        }

        @Override // android.support.p005v7.view.ActionMode.Callback
        public boolean onPrepareActionMode(ActionMode actionMode, Menu menu) {
            return this.mWrapped.onPrepareActionMode(actionMode, menu);
        }

        @Override // android.support.p005v7.view.ActionMode.Callback
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            return this.mWrapped.onActionItemClicked(actionMode, menuItem);
        }

        @Override // android.support.p005v7.view.ActionMode.Callback
        public void onDestroyActionMode(ActionMode actionMode) {
            this.mWrapped.onDestroyActionMode(actionMode);
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl.mActionModePopup != null) {
                appCompatDelegateImpl.mWindow.getDecorView().removeCallbacks(AppCompatDelegateImpl.this.mShowActionModePopup);
            }
            AppCompatDelegateImpl appCompatDelegateImpl2 = AppCompatDelegateImpl.this;
            if (appCompatDelegateImpl2.mActionModeView != null) {
                appCompatDelegateImpl2.endOnGoingFadeAnimation();
                AppCompatDelegateImpl appCompatDelegateImpl3 = AppCompatDelegateImpl.this;
                appCompatDelegateImpl3.mFadeAnim = ViewCompat.animate(appCompatDelegateImpl3.mActionModeView).alpha(0.0f);
                AppCompatDelegateImpl.this.mFadeAnim.setListener(new ViewPropertyAnimatorListenerAdapter() { // from class: android.support.v7.app.AppCompatDelegateImpl.ActionModeCallbackWrapperV9.1
                    @Override // android.support.p002v4.view.ViewPropertyAnimatorListenerAdapter, android.support.p002v4.view.ViewPropertyAnimatorListener
                    public void onAnimationEnd(View view) {
                        AppCompatDelegateImpl.this.mActionModeView.setVisibility(8);
                        AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
                        PopupWindow popupWindow = appCompatDelegateImpl4.mActionModePopup;
                        if (popupWindow != null) {
                            popupWindow.dismiss();
                        } else if (appCompatDelegateImpl4.mActionModeView.getParent() instanceof View) {
                            ViewCompat.requestApplyInsets((View) AppCompatDelegateImpl.this.mActionModeView.getParent());
                        }
                        AppCompatDelegateImpl.this.mActionModeView.removeAllViews();
                        AppCompatDelegateImpl.this.mFadeAnim.setListener(null);
                        AppCompatDelegateImpl.this.mFadeAnim = null;
                    }
                });
            }
            AppCompatDelegateImpl appCompatDelegateImpl4 = AppCompatDelegateImpl.this;
            AppCompatCallback appCompatCallback = appCompatDelegateImpl4.mAppCompatCallback;
            if (appCompatCallback != null) {
                appCompatCallback.onSupportActionModeFinished(appCompatDelegateImpl4.mActionMode);
            }
            AppCompatDelegateImpl.this.mActionMode = null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$PanelMenuPresenterCallback */
    /* loaded from: classes2.dex */
    public final class PanelMenuPresenterCallback implements MenuPresenter.Callback {
        PanelMenuPresenterCallback() {
        }

        @Override // android.support.p005v7.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            MenuBuilder rootMenu = menuBuilder.getRootMenu();
            boolean z2 = rootMenu != menuBuilder;
            AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
            if (z2) {
                menuBuilder = rootMenu;
            }
            PanelFeatureState findMenuPanel = appCompatDelegateImpl.findMenuPanel(menuBuilder);
            if (findMenuPanel != null) {
                if (z2) {
                    AppCompatDelegateImpl.this.callOnPanelClosed(findMenuPanel.featureId, findMenuPanel, rootMenu);
                    AppCompatDelegateImpl.this.closePanel(findMenuPanel, true);
                    return;
                }
                AppCompatDelegateImpl.this.closePanel(findMenuPanel, z);
            }
        }

        @Override // android.support.p005v7.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback;
            if (menuBuilder == null) {
                AppCompatDelegateImpl appCompatDelegateImpl = AppCompatDelegateImpl.this;
                if (!appCompatDelegateImpl.mHasActionBar || (windowCallback = appCompatDelegateImpl.getWindowCallback()) == null || AppCompatDelegateImpl.this.mIsDestroyed) {
                    return true;
                }
                windowCallback.onMenuOpened(108, menuBuilder);
                return true;
            }
            return true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ActionMenuPresenterCallback */
    /* loaded from: classes2.dex */
    public final class ActionMenuPresenterCallback implements MenuPresenter.Callback {
        ActionMenuPresenterCallback() {
        }

        @Override // android.support.p005v7.view.menu.MenuPresenter.Callback
        public boolean onOpenSubMenu(MenuBuilder menuBuilder) {
            Window.Callback windowCallback = AppCompatDelegateImpl.this.getWindowCallback();
            if (windowCallback != null) {
                windowCallback.onMenuOpened(108, menuBuilder);
                return true;
            }
            return true;
        }

        @Override // android.support.p005v7.view.menu.MenuPresenter.Callback
        public void onCloseMenu(MenuBuilder menuBuilder, boolean z) {
            AppCompatDelegateImpl.this.checkCloseActionMenu(menuBuilder);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState */
    /* loaded from: classes2.dex */
    public static final class PanelFeatureState {
        int background;
        View createdPanelView;
        ViewGroup decorView;
        int featureId;
        Bundle frozenActionViewState;
        Bundle frozenMenuState;
        int gravity;
        boolean isHandled;
        boolean isOpen;
        boolean isPrepared;
        ListMenuPresenter listMenuPresenter;
        Context listPresenterContext;
        MenuBuilder menu;
        public boolean qwertyMode;
        boolean refreshDecorView = false;
        boolean refreshMenuContent;
        View shownPanelView;
        boolean wasLastOpen;
        int windowAnimations;

        /* renamed from: x */
        int f33x;

        /* renamed from: y */
        int f34y;

        PanelFeatureState(int i) {
            this.featureId = i;
        }

        public boolean hasPanelItems() {
            if (this.shownPanelView == null) {
                return false;
            }
            return this.createdPanelView != null || this.listMenuPresenter.getAdapter().getCount() > 0;
        }

        public void clearMenuPresenters() {
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder != null) {
                menuBuilder.removeMenuPresenter(this.listMenuPresenter);
            }
            this.listMenuPresenter = null;
        }

        void setStyle(Context context) {
            TypedValue typedValue = new TypedValue();
            Resources.Theme newTheme = context.getResources().newTheme();
            newTheme.setTo(context.getTheme());
            newTheme.resolveAttribute(C0441R.attr.actionBarPopupTheme, typedValue, true);
            int i = typedValue.resourceId;
            if (i != 0) {
                newTheme.applyStyle(i, true);
            }
            newTheme.resolveAttribute(C0441R.attr.panelMenuListTheme, typedValue, true);
            int i2 = typedValue.resourceId;
            if (i2 != 0) {
                newTheme.applyStyle(i2, true);
            } else {
                newTheme.applyStyle(C0441R.C0446style.Theme_AppCompat_CompactMenu, true);
            }
            ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, 0);
            contextThemeWrapper.getTheme().setTo(newTheme);
            this.listPresenterContext = contextThemeWrapper;
            TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(C0441R.styleable.AppCompatTheme);
            this.background = obtainStyledAttributes.getResourceId(C0441R.styleable.AppCompatTheme_panelBackground, 0);
            this.windowAnimations = obtainStyledAttributes.getResourceId(C0441R.styleable.AppCompatTheme_android_windowAnimationStyle, 0);
            obtainStyledAttributes.recycle();
        }

        void setMenu(MenuBuilder menuBuilder) {
            ListMenuPresenter listMenuPresenter;
            MenuBuilder menuBuilder2 = this.menu;
            if (menuBuilder == menuBuilder2) {
                return;
            }
            if (menuBuilder2 != null) {
                menuBuilder2.removeMenuPresenter(this.listMenuPresenter);
            }
            this.menu = menuBuilder;
            if (menuBuilder == null || (listMenuPresenter = this.listMenuPresenter) == null) {
                return;
            }
            menuBuilder.addMenuPresenter(listMenuPresenter);
        }

        MenuView getListMenuView(MenuPresenter.Callback callback) {
            if (this.menu == null) {
                return null;
            }
            if (this.listMenuPresenter == null) {
                this.listMenuPresenter = new ListMenuPresenter(this.listPresenterContext, C0441R.C0445layout.abc_list_menu_item_layout);
                this.listMenuPresenter.setCallback(callback);
                this.menu.addMenuPresenter(this.listMenuPresenter);
            }
            return this.listMenuPresenter.getMenuView(this.decorView);
        }

        Parcelable onSaveInstanceState() {
            SavedState savedState = new SavedState();
            savedState.featureId = this.featureId;
            savedState.isOpen = this.isOpen;
            if (this.menu != null) {
                savedState.menuState = new Bundle();
                this.menu.savePresenterStates(savedState.menuState);
            }
            return savedState;
        }

        void onRestoreInstanceState(Parcelable parcelable) {
            SavedState savedState = (SavedState) parcelable;
            this.featureId = savedState.featureId;
            this.wasLastOpen = savedState.isOpen;
            this.frozenMenuState = savedState.menuState;
            this.shownPanelView = null;
            this.decorView = null;
        }

        void applyFrozenState() {
            Bundle bundle;
            MenuBuilder menuBuilder = this.menu;
            if (menuBuilder == null || (bundle = this.frozenMenuState) == null) {
                return;
            }
            menuBuilder.restorePresenterStates(bundle);
            this.frozenMenuState = null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        /* renamed from: android.support.v7.app.AppCompatDelegateImpl$PanelFeatureState$SavedState */
        /* loaded from: classes2.dex */
        public static class SavedState implements Parcelable {
            public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>() { // from class: android.support.v7.app.AppCompatDelegateImpl.PanelFeatureState.SavedState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.ClassLoaderCreator
                /* renamed from: createFromParcel */
                public SavedState mo5739createFromParcel(Parcel parcel, ClassLoader classLoader) {
                    return SavedState.readFromParcel(parcel, classLoader);
                }

                @Override // android.os.Parcelable.Creator
                /* renamed from: createFromParcel */
                public SavedState mo5738createFromParcel(Parcel parcel) {
                    return SavedState.readFromParcel(parcel, null);
                }

                @Override // android.os.Parcelable.Creator
                /* renamed from: newArray */
                public SavedState[] mo5740newArray(int i) {
                    return new SavedState[i];
                }
            };
            int featureId;
            boolean isOpen;
            Bundle menuState;

            @Override // android.os.Parcelable
            public int describeContents() {
                return 0;
            }

            SavedState() {
            }

            @Override // android.os.Parcelable
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeInt(this.featureId);
                parcel.writeInt(this.isOpen ? 1 : 0);
                if (this.isOpen) {
                    parcel.writeBundle(this.menuState);
                }
            }

            static SavedState readFromParcel(Parcel parcel, ClassLoader classLoader) {
                SavedState savedState = new SavedState();
                savedState.featureId = parcel.readInt();
                boolean z = true;
                if (parcel.readInt() != 1) {
                    z = false;
                }
                savedState.isOpen = z;
                if (savedState.isOpen) {
                    savedState.menuState = parcel.readBundle(classLoader);
                }
                return savedState;
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ListMenuDecorView */
    /* loaded from: classes2.dex */
    public class ListMenuDecorView extends ContentFrameLayout {
        public ListMenuDecorView(Context context) {
            super(context);
        }

        @Override // android.view.ViewGroup, android.view.View
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.view.ViewGroup
        public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0 && isOutOfBounds((int) motionEvent.getX(), (int) motionEvent.getY())) {
                AppCompatDelegateImpl.this.closePanel(0);
                return true;
            }
            return super.onInterceptTouchEvent(motionEvent);
        }

        @Override // android.view.View
        public void setBackgroundResource(int i) {
            setBackgroundDrawable(AppCompatResources.getDrawable(getContext(), i));
        }

        private boolean isOutOfBounds(int i, int i2) {
            return i < -5 || i2 < -5 || i > getWidth() + 5 || i2 > getHeight() + 5;
        }
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$AppCompatWindowCallback */
    /* loaded from: classes2.dex */
    class AppCompatWindowCallback extends WindowCallbackWrapper {
        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onContentChanged() {
        }

        AppCompatWindowCallback(Window.Callback callback) {
            super(callback);
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyEvent(KeyEvent keyEvent) {
            return AppCompatDelegateImpl.this.dispatchKeyEvent(keyEvent) || super.dispatchKeyEvent(keyEvent);
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean dispatchKeyShortcutEvent(KeyEvent keyEvent) {
            return super.dispatchKeyShortcutEvent(keyEvent) || AppCompatDelegateImpl.this.onKeyShortcut(keyEvent.getKeyCode(), keyEvent);
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onCreatePanelMenu(int i, Menu menu) {
            if (i != 0 || (menu instanceof MenuBuilder)) {
                return super.onCreatePanelMenu(i, menu);
            }
            return false;
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onPreparePanel(int i, View view, Menu menu) {
            MenuBuilder menuBuilder = menu instanceof MenuBuilder ? (MenuBuilder) menu : null;
            if (i == 0 && menuBuilder == null) {
                return false;
            }
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(true);
            }
            boolean onPreparePanel = super.onPreparePanel(i, view, menu);
            if (menuBuilder != null) {
                menuBuilder.setOverrideVisibleItems(false);
            }
            return onPreparePanel;
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public boolean onMenuOpened(int i, Menu menu) {
            super.onMenuOpened(i, menu);
            AppCompatDelegateImpl.this.onMenuOpened(i);
            return true;
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public void onPanelClosed(int i, Menu menu) {
            super.onPanelClosed(i, menu);
            AppCompatDelegateImpl.this.onPanelClosed(i);
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback) {
            if (Build.VERSION.SDK_INT >= 23) {
                return null;
            }
            if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled()) {
                return startAsSupportActionMode(callback);
            }
            return super.onWindowStartingActionMode(callback);
        }

        final android.view.ActionMode startAsSupportActionMode(ActionMode.Callback callback) {
            SupportActionModeWrapper.CallbackWrapper callbackWrapper = new SupportActionModeWrapper.CallbackWrapper(AppCompatDelegateImpl.this.mContext, callback);
            android.support.p005v7.view.ActionMode startSupportActionMode = AppCompatDelegateImpl.this.startSupportActionMode(callbackWrapper);
            if (startSupportActionMode != null) {
                return callbackWrapper.getActionModeWrapper(startSupportActionMode);
            }
            return null;
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(23)
        public android.view.ActionMode onWindowStartingActionMode(ActionMode.Callback callback, int i) {
            if (AppCompatDelegateImpl.this.isHandleNativeActionModesEnabled() && i == 0) {
                return startAsSupportActionMode(callback);
            }
            return super.onWindowStartingActionMode(callback, i);
        }

        @Override // android.support.p005v7.view.WindowCallbackWrapper, android.view.Window.Callback
        @RequiresApi(24)
        public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> list, Menu menu, int i) {
            MenuBuilder menuBuilder;
            PanelFeatureState panelState = AppCompatDelegateImpl.this.getPanelState(0, true);
            if (panelState != null && (menuBuilder = panelState.menu) != null) {
                super.onProvideKeyboardShortcuts(list, menuBuilder, i);
            } else {
                super.onProvideKeyboardShortcuts(list, menu, i);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @VisibleForTesting
    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$AutoNightModeManager */
    /* loaded from: classes2.dex */
    public final class AutoNightModeManager {
        private BroadcastReceiver mAutoTimeChangeReceiver;
        private IntentFilter mAutoTimeChangeReceiverFilter;
        private boolean mIsNight;
        private TwilightManager mTwilightManager;

        AutoNightModeManager(@NonNull TwilightManager twilightManager) {
            this.mTwilightManager = twilightManager;
            this.mIsNight = twilightManager.isNight();
        }

        int getApplyableNightMode() {
            this.mIsNight = this.mTwilightManager.isNight();
            return this.mIsNight ? 2 : 1;
        }

        void dispatchTimeChanged() {
            boolean isNight = this.mTwilightManager.isNight();
            if (isNight != this.mIsNight) {
                this.mIsNight = isNight;
                AppCompatDelegateImpl.this.applyDayNight();
            }
        }

        void setup() {
            cleanup();
            if (this.mAutoTimeChangeReceiver == null) {
                this.mAutoTimeChangeReceiver = new BroadcastReceiver() { // from class: android.support.v7.app.AppCompatDelegateImpl.AutoNightModeManager.1
                    @Override // android.content.BroadcastReceiver
                    public void onReceive(Context context, Intent intent) {
                        AutoNightModeManager.this.dispatchTimeChanged();
                    }
                };
            }
            if (this.mAutoTimeChangeReceiverFilter == null) {
                this.mAutoTimeChangeReceiverFilter = new IntentFilter();
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_SET");
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIMEZONE_CHANGED");
                this.mAutoTimeChangeReceiverFilter.addAction("android.intent.action.TIME_TICK");
            }
            AppCompatDelegateImpl.this.mContext.registerReceiver(this.mAutoTimeChangeReceiver, this.mAutoTimeChangeReceiverFilter);
        }

        void cleanup() {
            BroadcastReceiver broadcastReceiver = this.mAutoTimeChangeReceiver;
            if (broadcastReceiver != null) {
                AppCompatDelegateImpl.this.mContext.unregisterReceiver(broadcastReceiver);
                this.mAutoTimeChangeReceiver = null;
            }
        }
    }

    @Override // android.support.p005v7.app.AppCompatDelegate
    public final ActionBarDrawerToggle.Delegate getDrawerToggleDelegate() {
        return new ActionBarDrawableToggleImpl();
    }

    /* renamed from: android.support.v7.app.AppCompatDelegateImpl$ActionBarDrawableToggleImpl */
    /* loaded from: classes2.dex */
    private class ActionBarDrawableToggleImpl implements ActionBarDrawerToggle.Delegate {
        ActionBarDrawableToggleImpl() {
        }

        @Override // android.support.p005v7.app.ActionBarDrawerToggle.Delegate
        public Drawable getThemeUpIndicator() {
            TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(getActionBarThemedContext(), (AttributeSet) null, new int[]{C0441R.attr.homeAsUpIndicator});
            Drawable drawable = obtainStyledAttributes.getDrawable(0);
            obtainStyledAttributes.recycle();
            return drawable;
        }

        @Override // android.support.p005v7.app.ActionBarDrawerToggle.Delegate
        public Context getActionBarThemedContext() {
            return AppCompatDelegateImpl.this.getActionBarThemedContext();
        }

        @Override // android.support.p005v7.app.ActionBarDrawerToggle.Delegate
        public boolean isNavigationVisible() {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            return (supportActionBar == null || (supportActionBar.getDisplayOptions() & 4) == 0) ? false : true;
        }

        @Override // android.support.p005v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarUpIndicator(Drawable drawable, int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeAsUpIndicator(drawable);
                supportActionBar.setHomeActionContentDescription(i);
            }
        }

        @Override // android.support.p005v7.app.ActionBarDrawerToggle.Delegate
        public void setActionBarDescription(int i) {
            ActionBar supportActionBar = AppCompatDelegateImpl.this.getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setHomeActionContentDescription(i);
            }
        }
    }
}
