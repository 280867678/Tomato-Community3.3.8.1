package razerdp.basepopup;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.PopupWindow;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import razerdp.blur.PopupBlurOption;
import razerdp.interceptor.PopupWindowEventInterceptor;
import razerdp.util.InputMethodUtils;
import razerdp.util.PopupUiUtils;
import razerdp.util.PopupUtils;
import razerdp.util.SimpleAnimationUtils;
import razerdp.util.log.PopupLog;

/* loaded from: classes4.dex */
public abstract class BasePopupWindow implements BasePopup, PopupWindow.OnDismissListener, PopupTouchController, PopupWindowLocationListener {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static final int MATCH_PARENT = -1;
    private static final int MAX_RETRY_SHOW_TIME = 3;
    private static final String TAG = "BasePopupWindow";
    public static final int WRAP_CONTENT = -2;
    private volatile boolean isExitAnimatePlaying;
    Object lifeCycleObserver;
    private EditText mAutoShowInputEdittext;
    private View mContentView;
    private WeakReference<Context> mContext;
    private DelayInitCached mDelayInitCached;
    private View mDisplayAnimateView;
    private PopupWindowEventInterceptor mEventInterceptor;
    private GlobalLayoutListenerWrapper mGlobalLayoutListenerWrapper;
    private BasePopupHelper mHelper;
    private LinkedViewLayoutChangeListenerWrapper mLinkedViewLayoutChangeListenerWrapper;
    private WeakReference<View> mLinkedViewRef;
    private PopupWindowProxy mPopupWindow;
    private int retryCounter;
    public static int DEFAULT_BACKGROUND_COLOR = Color.parseColor("#8f000000");
    public static boolean DEBUG = false;

    /* loaded from: classes4.dex */
    public enum GravityMode {
        RELATIVE_TO_ANCHOR,
        ALIGN_TO_ANCHOR_SIDE
    }

    /* loaded from: classes4.dex */
    public interface OnBeforeShowCallback {
        boolean onBeforeShow(View view, View view2, boolean z);
    }

    /* loaded from: classes4.dex */
    public interface OnBlurOptionInitListener {
        void onCreateBlurOption(PopupBlurOption popupBlurOption);
    }

    /* loaded from: classes4.dex */
    public static abstract class OnDismissListener implements PopupWindow.OnDismissListener {
        public boolean onBeforeDismiss() {
            return true;
        }

        public void onDismissAnimationStart() {
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes4.dex */
    public interface OnKeyboardStateChangeListener {
        void onKeyboardChange(int i, int i2, boolean z, boolean z2);
    }

    @Override // razerdp.basepopup.PopupWindowLocationListener
    public void onAnchorBottom() {
    }

    @Deprecated
    public void onAnchorBottom(View view, View view2) {
    }

    @Override // razerdp.basepopup.PopupWindowLocationListener
    public void onAnchorTop() {
    }

    @Deprecated
    public void onAnchorTop(View view, View view2) {
    }

    protected View onCreateAnimateView() {
        return null;
    }

    protected Animation onCreateDismissAnimation() {
        return null;
    }

    protected Animator onCreateDismissAnimator() {
        return null;
    }

    protected Animation onCreateShowAnimation() {
        return null;
    }

    protected Animator onCreateShowAnimator() {
        return null;
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onDispatchKeyEvent(KeyEvent keyEvent) {
        return false;
    }

    protected View onFindDecorView(Activity activity) {
        return null;
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onInterceptTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onTouchEvent(MotionEvent motionEvent) {
        return false;
    }

    static /* synthetic */ int access$408(BasePopupWindow basePopupWindow) {
        int i = basePopupWindow.retryCounter;
        basePopupWindow.retryCounter = i + 1;
        return i;
    }

    public BasePopupWindow(Context context) {
        this(context, false);
    }

    public BasePopupWindow(Context context, boolean z) {
        this(context, -2, -2, z);
    }

    public BasePopupWindow(Context context, int i, int i2) {
        this(context, i, i2, false);
    }

    public BasePopupWindow(Context context, int i, int i2, boolean z) {
        this.isExitAnimatePlaying = false;
        this.mContext = new WeakReference<>(context);
        if (!z) {
            initView(i, i2);
            return;
        }
        this.mDelayInitCached = new DelayInitCached();
        DelayInitCached delayInitCached = this.mDelayInitCached;
        delayInitCached.width = i;
        delayInitCached.height = i2;
    }

    public void delayInit() {
        DelayInitCached delayInitCached = this.mDelayInitCached;
        if (delayInitCached == null) {
            return;
        }
        initView(delayInitCached.width, delayInitCached.height);
        this.mDelayInitCached = null;
    }

    private void initView(int i, int i2) {
        attachLifeCycle(getContext());
        this.mHelper = new BasePopupHelper(this);
        registerListener(this.mHelper);
        this.mContentView = onCreateContentView();
        this.mHelper.setContentRootId(this.mContentView);
        if (this.mHelper.getParaseFromXmlParams() == null) {
            Log.e(TAG, "为了更准确的适配您的布局，BasePopupWindow建议您使用createPopupById()进行inflate");
        }
        this.mDisplayAnimateView = onCreateAnimateView();
        if (this.mDisplayAnimateView == null) {
            this.mDisplayAnimateView = this.mContentView;
        }
        setWidth(i);
        setHeight(i2);
        if (this.mHelper.getParaseFromXmlParams() != null) {
            i = this.mHelper.getParaseFromXmlParams().width;
            i2 = this.mHelper.getParaseFromXmlParams().height;
        }
        this.mPopupWindow = new PopupWindowProxy(this.mContentView, i, i2, this.mHelper);
        this.mPopupWindow.setOnDismissListener(this);
        this.mPopupWindow.attachPopupHelper(this.mHelper);
        setOutSideDismiss(true);
        setPopupAnimationStyle(0);
        this.mHelper.setPopupViewWidth(i);
        this.mHelper.setPopupViewHeight(i2);
        hookContentViewDismissClick(i, i2);
        preMeasurePopupView(i, i2);
        BasePopupHelper basePopupHelper = this.mHelper;
        basePopupHelper.setShowAnimation(onCreateShowAnimation());
        basePopupHelper.setShowAnimator(onCreateShowAnimator());
        basePopupHelper.setDismissAnimation(onCreateDismissAnimation());
        basePopupHelper.setDismissAnimator(onCreateDismissAnimator());
    }

    private void registerListener(BasePopupHelper basePopupHelper) {
        basePopupHelper.registerLocationLisener(this);
    }

    private void hookContentViewDismissClick(int i, int i2) {
        View view;
        if (i != -1 || i2 != -1 || (view = this.mContentView) == null || (view instanceof AdapterView) || !(view instanceof ViewGroup)) {
            return;
        }
        ViewGroup viewGroup = (ViewGroup) view;
        int childCount = viewGroup.getChildCount();
        final ArrayList arrayList = new ArrayList(childCount);
        for (int i3 = 0; i3 < childCount; i3++) {
            View childAt = viewGroup.getChildAt(i3);
            if (childAt.getVisibility() == 0) {
                arrayList.add(new WeakReference(childAt));
            }
        }
        this.mContentView.setOnTouchListener(new View.OnTouchListener() { // from class: razerdp.basepopup.BasePopupWindow.1
            RectF childBounds = new RectF();

            @Override // android.view.View.OnTouchListener
            public boolean onTouch(View view2, MotionEvent motionEvent) {
                int action = motionEvent.getAction();
                if (action != 0) {
                    boolean z = true;
                    if (action == 1) {
                        this.childBounds.setEmpty();
                        if (BasePopupWindow.this.isAllowDismissWhenTouchOutside()) {
                            view2.performClick();
                            int x = (int) motionEvent.getX();
                            int y = (int) motionEvent.getY();
                            Iterator it2 = arrayList.iterator();
                            while (true) {
                                if (!it2.hasNext()) {
                                    z = false;
                                    break;
                                }
                                WeakReference weakReference = (WeakReference) it2.next();
                                if (weakReference != null && weakReference.get() != null && ((View) weakReference.get()).isShown()) {
                                    View view3 = (View) weakReference.get();
                                    this.childBounds.set(view3.getLeft(), view3.getTop(), view3.getRight(), view3.getBottom());
                                    if (this.childBounds.contains(x, y)) {
                                        break;
                                    }
                                }
                            }
                            if (!z) {
                                BasePopupWindow.this.dismiss();
                            }
                        }
                    }
                    return false;
                }
                return BasePopupWindow.this.isAllowDismissWhenTouchOutside();
            }
        });
    }

    private void preMeasurePopupView(int i, int i2) {
        View view = this.mContentView;
        if (view != null) {
            PopupWindowEventInterceptor popupWindowEventInterceptor = this.mEventInterceptor;
            if (!(popupWindowEventInterceptor != null && popupWindowEventInterceptor.onPreMeasurePopupView(this, view, i, i2))) {
                int i3 = 1073741824;
                int makeMeasureSpec = View.MeasureSpec.makeMeasureSpec(i, i == -2 ? 0 : 1073741824);
                if (i2 == -2) {
                    i3 = 0;
                }
                this.mContentView.measure(makeMeasureSpec, View.MeasureSpec.makeMeasureSpec(i2, i3));
            }
            BasePopupHelper basePopupHelper = this.mHelper;
            basePopupHelper.setPreMeasureWidth(this.mContentView.getMeasuredWidth());
            basePopupHelper.setPreMeasureHeight(this.mContentView.getMeasuredHeight());
            this.mContentView.setFocusableInTouchMode(true);
        }
    }

    public BasePopupWindow setPopupFadeEnable(boolean z) {
        this.mHelper.setPopupFadeEnable(this.mPopupWindow, z);
        return this;
    }

    public boolean isPopupFadeEnable() {
        return this.mHelper.isPopupFadeEnable();
    }

    public BasePopupWindow setPopupAnimationStyle(int i) {
        this.mPopupWindow.setAnimationStyle(i);
        return this;
    }

    public void showPopupWindow() {
        if (checkPerformShow(null)) {
            this.mHelper.setShowAsDropDown(false);
            tryToShowPopup(null, false, false);
        }
    }

    public void showPopupWindow(int i) {
        Activity context = getContext();
        if (context instanceof Activity) {
            showPopupWindow(context.findViewById(i));
        } else {
            Log.e(TAG, "can not get token from context,make sure that context is instance of activity");
        }
    }

    public void showPopupWindow(View view) {
        if (checkPerformShow(view)) {
            if (view != null) {
                this.mHelper.setShowAsDropDown(true);
            }
            tryToShowPopup(view, false, false);
        }
    }

    public void showPopupWindow(int i, int i2) {
        if (checkPerformShow(null)) {
            this.mHelper.setShowLocation(i, i2);
            this.mHelper.setShowAsDropDown(true);
            tryToShowPopup(null, true, false);
        }
    }

    public void update() {
        tryToUpdate(null, false);
    }

    public void update(View view) {
        if (!isShowing() || getContentView() == null) {
            return;
        }
        tryToUpdate(view, false);
    }

    public void update(int i, int i2) {
        if (!isShowing() || getContentView() == null) {
            return;
        }
        this.mHelper.setShowLocation(i, i2);
        this.mHelper.setShowAsDropDown(true);
        tryToUpdate(null, true);
    }

    public void update(float f, float f2) {
        if (!isShowing() || getContentView() == null) {
            return;
        }
        setWidth((int) f).setHeight((int) f2).update();
    }

    public void update(int i, int i2, float f, float f2) {
        if (!isShowing() || getContentView() == null) {
            return;
        }
        this.mHelper.setShowLocation(i, i2);
        this.mHelper.setShowAsDropDown(true);
        setWidth((int) f).setHeight((int) f2).tryToUpdate(null, true);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0096 A[Catch: Exception -> 0x00d7, TryCatch #0 {Exception -> 0x00d7, blocks: (B:9:0x0032, B:13:0x003b, B:15:0x0043, B:16:0x006f, B:18:0x0079, B:22:0x0085, B:25:0x008e, B:27:0x0096, B:28:0x00ab, B:30:0x00b3, B:31:0x00bc, B:33:0x00c4, B:35:0x00c8, B:36:0x00d4, B:40:0x004d, B:41:0x0057, B:43:0x0060, B:44:0x0066), top: B:8:0x0032 }] */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00ab A[Catch: Exception -> 0x00d7, TryCatch #0 {Exception -> 0x00d7, blocks: (B:9:0x0032, B:13:0x003b, B:15:0x0043, B:16:0x006f, B:18:0x0079, B:22:0x0085, B:25:0x008e, B:27:0x0096, B:28:0x00ab, B:30:0x00b3, B:31:0x00bc, B:33:0x00c4, B:35:0x00c8, B:36:0x00d4, B:40:0x004d, B:41:0x0057, B:43:0x0060, B:44:0x0066), top: B:8:0x0032 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void tryToShowPopup(View view, boolean z, boolean z2) {
        boolean z3;
        addListener();
        this.mHelper.handleShow();
        this.mHelper.prepare(view, z);
        PopupWindowEventInterceptor popupWindowEventInterceptor = this.mEventInterceptor;
        if (popupWindowEventInterceptor == null || !popupWindowEventInterceptor.onTryToShowPopup(this, this.mPopupWindow, view, this.mHelper.getPopupGravity(), this.mHelper.getOffsetX(), this.mHelper.getOffsetY())) {
            try {
                if (isShowing()) {
                    return;
                }
                if (view != null) {
                    if (this.mHelper.isShowAsDropDown()) {
                        this.mPopupWindow.showAsDropDownProxy(view, 0, 0, getPopupGravity());
                    } else {
                        this.mPopupWindow.showAtLocationProxy(view, getPopupGravity(), 0, 0);
                    }
                } else {
                    getContext();
                    Activity context = getContext();
                    if (context == null) {
                        Log.e(TAG, "can not get token from context,make sure that context is instance of activity");
                    } else {
                        this.mPopupWindow.showAtLocationProxy(findDecorView(context), 0, 0, 0);
                    }
                }
                BasePopupHelper basePopupHelper = this.mHelper;
                if (this.mHelper.getShowAnimation() == null && this.mHelper.getShowAnimator() == null) {
                    z3 = false;
                    basePopupHelper.onShow(z3);
                    if (this.mDisplayAnimateView != null && !z2) {
                        if (this.mHelper.getShowAnimation() == null) {
                            this.mHelper.getShowAnimation().cancel();
                            this.mDisplayAnimateView.startAnimation(this.mHelper.getShowAnimation());
                        } else if (this.mHelper.getShowAnimator() != null) {
                            this.mHelper.getShowAnimator().start();
                        }
                    }
                    if (this.mHelper.isAutoShowInputMethod() && this.mAutoShowInputEdittext != null) {
                        this.mAutoShowInputEdittext.requestFocus();
                        InputMethodUtils.showInputMethod(this.mAutoShowInputEdittext, 350L);
                    }
                    this.retryCounter = 0;
                }
                z3 = true;
                basePopupHelper.onShow(z3);
                if (this.mDisplayAnimateView != null) {
                    if (this.mHelper.getShowAnimation() == null) {
                    }
                }
                if (this.mHelper.isAutoShowInputMethod()) {
                    this.mAutoShowInputEdittext.requestFocus();
                    InputMethodUtils.showInputMethod(this.mAutoShowInputEdittext, 350L);
                }
                this.retryCounter = 0;
            } catch (Exception e) {
                retryToShowPopup(view, z, z2);
                PopupLog.m27e(TAG, e);
                e.printStackTrace();
            }
        }
    }

    private View findDecorView(Activity activity) {
        View onFindDecorView = onFindDecorView(activity);
        if (onFindDecorView == null) {
            onFindDecorView = BasePopupSupporterManager.getInstance().proxy.findDecorView(this, activity);
        }
        return onFindDecorView == null ? activity.findViewById(16908290) : onFindDecorView;
    }

    private void tryToUpdate(View view, boolean z) {
        if (!isShowing() || getContentView() == null) {
            return;
        }
        this.mHelper.prepare(view, z);
        this.mPopupWindow.update();
    }

    private void addListener() {
        addGlobalListener();
        addLinkedLayoutListener();
    }

    private void addGlobalListener() {
        Activity context;
        GlobalLayoutListenerWrapper globalLayoutListenerWrapper = this.mGlobalLayoutListenerWrapper;
        if ((globalLayoutListenerWrapper == null || !globalLayoutListenerWrapper.isAttached()) && (context = getContext()) != null) {
            boolean z = false;
            View childAt = ((ViewGroup) context.getWindow().getDecorView()).getChildAt(0);
            if ((context.getWindow().getAttributes().flags & 1024) != 0) {
                z = true;
            }
            this.mGlobalLayoutListenerWrapper = new GlobalLayoutListenerWrapper(childAt, z, new OnKeyboardStateChangeListener() { // from class: razerdp.basepopup.BasePopupWindow.2
                @Override // razerdp.basepopup.BasePopupWindow.OnKeyboardStateChangeListener
                public void onKeyboardChange(int i, int i2, boolean z2, boolean z3) {
                    BasePopupWindow.this.mHelper.onKeyboardChange(i, i2, z2, z3);
                }
            });
            this.mGlobalLayoutListenerWrapper.addSelf();
        }
    }

    private void addLinkedLayoutListener() {
        LinkedViewLayoutChangeListenerWrapper linkedViewLayoutChangeListenerWrapper = this.mLinkedViewLayoutChangeListenerWrapper;
        if (linkedViewLayoutChangeListenerWrapper == null || !linkedViewLayoutChangeListenerWrapper.isAdded) {
            this.mLinkedViewLayoutChangeListenerWrapper = new LinkedViewLayoutChangeListenerWrapper();
            this.mLinkedViewLayoutChangeListenerWrapper.addSelf();
        }
    }

    private void removeGlobalListener() {
        GlobalLayoutListenerWrapper globalLayoutListenerWrapper = this.mGlobalLayoutListenerWrapper;
        if (globalLayoutListenerWrapper != null) {
            globalLayoutListenerWrapper.remove();
        }
        this.mHelper.handleDismiss();
    }

    private void removeLinkedLayoutListener() {
        LinkedViewLayoutChangeListenerWrapper linkedViewLayoutChangeListenerWrapper = this.mLinkedViewLayoutChangeListenerWrapper;
        if (linkedViewLayoutChangeListenerWrapper != null) {
            linkedViewLayoutChangeListenerWrapper.removeListener();
        }
    }

    private void retryToShowPopup(final View view, final boolean z, final boolean z2) {
        if (this.retryCounter > 3) {
            return;
        }
        boolean z3 = false;
        PopupLog.m27e("catch an exception on showing popupwindow ...now retrying to show ... retry count  >>  " + this.retryCounter, new Object[0]);
        if (this.mPopupWindow.callSuperIsShowing()) {
            this.mPopupWindow.callSuperDismiss();
        }
        Activity context = getContext();
        if (context == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= 17) {
            if (!context.isFinishing() && !context.isDestroyed()) {
                z3 = true;
            }
        } else {
            z3 = !context.isFinishing();
        }
        if (!z3) {
            return;
        }
        context.getWindow().getDecorView().postDelayed(new Runnable() { // from class: razerdp.basepopup.BasePopupWindow.3
            @Override // java.lang.Runnable
            public void run() {
                BasePopupWindow.access$408(BasePopupWindow.this);
                BasePopupWindow.this.tryToShowPopup(view, z, z2);
                PopupLog.m27e(BasePopupWindow.TAG, "retry to show >> " + BasePopupWindow.this.retryCounter);
            }
        }, 350L);
    }

    public BasePopupWindow setAdjustInputMethod(boolean z) {
        setAdjustInputMethod(z, 16);
        return this;
    }

    public BasePopupWindow setAdjustInputMethod(boolean z, int i) {
        if (z) {
            this.mPopupWindow.setSoftInputMode(i);
            setSoftInputMode(i);
        } else {
            this.mPopupWindow.setSoftInputMode(48);
            setSoftInputMode(48);
        }
        return this;
    }

    public BasePopupWindow setAutoShowInputMethod(EditText editText, boolean z) {
        this.mHelper.autoShowInputMethod(this.mPopupWindow, z);
        this.mAutoShowInputEdittext = editText;
        return this;
    }

    public BasePopupWindow setSoftInputMode(int i) {
        this.mHelper.setSoftInputMode(i);
        return this;
    }

    public BasePopupWindow setBackPressEnable(boolean z) {
        this.mHelper.backPressEnable(this.mPopupWindow, z);
        return this;
    }

    public View createPopupById(int i) {
        return this.mHelper.inflate(getContext(), i);
    }

    public <T extends View> T findViewById(int i) {
        View view = this.mContentView;
        if (view == null || i == 0) {
            return null;
        }
        return (T) view.findViewById(i);
    }

    public BasePopupWindow setPopupWindowFullScreen(boolean z) {
        this.mHelper.fullScreen(z);
        return this;
    }

    public BasePopupWindow setBackgroundColor(int i) {
        this.mHelper.setPopupBackground(new ColorDrawable(i));
        return this;
    }

    public BasePopupWindow setBackground(int i) {
        if (i == 0) {
            return setBackground((Drawable) null);
        }
        if (Build.VERSION.SDK_INT >= 21) {
            return setBackground(getContext().getDrawable(i));
        }
        return setBackground(getContext().getResources().getDrawable(i));
    }

    public BasePopupWindow setBackground(Drawable drawable) {
        this.mHelper.setPopupBackground(drawable);
        return this;
    }

    public BasePopupWindow setBackgroundView(View view) {
        this.mHelper.setBackgroundView(view);
        return this;
    }

    public Drawable getPopupBackground() {
        return this.mHelper.getPopupBackground();
    }

    public BasePopupWindow setBlurBackgroundEnable(boolean z) {
        return setBlurBackgroundEnable(z, null);
    }

    public BasePopupWindow setBlurBackgroundEnable(boolean z, OnBlurOptionInitListener onBlurOptionInitListener) {
        Activity context = getContext();
        if (context == null) {
            PopupLog.m27e(TAG, "无法配置默认模糊脚本，因为context不是activity");
            return this;
        }
        PopupBlurOption popupBlurOption = null;
        if (z) {
            popupBlurOption = new PopupBlurOption();
            popupBlurOption.setFullScreen(true);
            popupBlurOption.setBlurInDuration(-1L);
            popupBlurOption.setBlurOutDuration(-1L);
            if (onBlurOptionInitListener != null) {
                onBlurOptionInitListener.onCreateBlurOption(popupBlurOption);
            }
            View findDecorView = findDecorView(context);
            if ((findDecorView instanceof ViewGroup) && findDecorView.getId() == 16908290) {
                popupBlurOption.setBlurView(((ViewGroup) context.getWindow().getDecorView()).getChildAt(0));
                popupBlurOption.setFullScreen(true);
            } else {
                popupBlurOption.setBlurView(findDecorView);
            }
        }
        return setBlurOption(popupBlurOption);
    }

    public BasePopupWindow setBlurOption(PopupBlurOption popupBlurOption) {
        this.mHelper.applyBlur(popupBlurOption);
        return this;
    }

    protected void setViewClickListener(View.OnClickListener onClickListener, View... viewArr) {
        for (View view : viewArr) {
            if (view != null && onClickListener != null) {
                view.setOnClickListener(onClickListener);
            }
        }
    }

    public boolean isShowing() {
        return this.mPopupWindow.isShowing();
    }

    public OnDismissListener getOnDismissListener() {
        return this.mHelper.getOnDismissListener();
    }

    public BasePopupWindow setOnDismissListener(OnDismissListener onDismissListener) {
        this.mHelper.setOnDismissListener(onDismissListener);
        return this;
    }

    public OnBeforeShowCallback getOnBeforeShowCallback() {
        return this.mHelper.getOnBeforeShowCallback();
    }

    public BasePopupWindow setOnBeforeShowCallback(OnBeforeShowCallback onBeforeShowCallback) {
        this.mHelper.setOnBeforeShowCallback(onBeforeShowCallback);
        return this;
    }

    public BasePopupWindow setShowAnimation(Animation animation) {
        this.mHelper.setShowAnimation(animation);
        return this;
    }

    public Animation getShowAnimation() {
        return this.mHelper.getShowAnimation();
    }

    public BasePopupWindow setShowAnimator(Animator animator) {
        this.mHelper.setShowAnimator(animator);
        return this;
    }

    public Animator getShowAnimator() {
        return this.mHelper.getShowAnimator();
    }

    public BasePopupWindow setDismissAnimation(Animation animation) {
        this.mHelper.setDismissAnimation(animation);
        return this;
    }

    public Animation getDismissAnimation() {
        return this.mHelper.getDismissAnimation();
    }

    public BasePopupWindow setDismissAnimator(Animator animator) {
        this.mHelper.setDismissAnimator(animator);
        return this;
    }

    public Animator getDismissAnimator() {
        return this.mHelper.getDismissAnimator();
    }

    public Activity getContext() {
        WeakReference<Context> weakReference = this.mContext;
        if (weakReference == null) {
            return null;
        }
        return PopupUtils.scanForActivity(weakReference.get(), 15);
    }

    public View getContentView() {
        return this.mContentView;
    }

    public View getDisplayAnimateView() {
        return this.mDisplayAnimateView;
    }

    public PopupWindow getPopupWindow() {
        return this.mPopupWindow;
    }

    public int getOffsetX() {
        return this.mHelper.getOffsetX();
    }

    public BasePopupWindow setOffsetX(int i) {
        this.mHelper.setOffsetX(i);
        return this;
    }

    public int getOffsetY() {
        return this.mHelper.getOffsetY();
    }

    public BasePopupWindow setOffsetY(int i) {
        this.mHelper.setOffsetY(i);
        return this;
    }

    public int getPopupGravity() {
        return this.mHelper.getPopupGravity();
    }

    public BasePopupWindow setPopupGravity(int i) {
        return setPopupGravity(GravityMode.RELATIVE_TO_ANCHOR, i);
    }

    public BasePopupWindow setPopupGravity(GravityMode gravityMode, int i) {
        this.mHelper.setPopupGravity(gravityMode, i);
        return this;
    }

    public boolean isAutoLocatePopup() {
        return this.mHelper.isAutoLocatePopup();
    }

    public BasePopupWindow setAutoLocatePopup(boolean z) {
        this.mHelper.autoLocatePopup(z);
        return this;
    }

    public int getHeight() {
        View view = this.mContentView;
        if (view != null && view.getHeight() > 0) {
            return this.mContentView.getHeight();
        }
        return this.mHelper.getPreMeasureHeight();
    }

    public int getWidth() {
        View view = this.mContentView;
        if (view != null && view.getWidth() > 0) {
            return this.mContentView.getWidth();
        }
        return this.mHelper.getPreMeasureWidth();
    }

    @Deprecated
    public BasePopupWindow setAllowDismissWhenTouchOutside(boolean z) {
        setOutSideDismiss(z);
        return this;
    }

    public BasePopupWindow setOutSideDismiss(boolean z) {
        this.mHelper.dismissOutSideTouch(this.mPopupWindow, z);
        return this;
    }

    @Deprecated
    public BasePopupWindow setAllowInterceptTouchEvent(boolean z) {
        setOutSideTouchable(!z);
        return this;
    }

    public BasePopupWindow setOutSideTouchable(boolean z) {
        this.mHelper.outSideTouchable(this.mPopupWindow, z);
        return this;
    }

    public BasePopupWindow setClipChildren(boolean z) {
        this.mHelper.setClipChildren(z);
        return this;
    }

    public BasePopupWindow setClipToScreen(boolean z) {
        this.mHelper.setClipToScreen(z);
        return this;
    }

    public boolean isAllowDismissWhenTouchOutside() {
        return this.mHelper.isOutSideDismiss();
    }

    @Deprecated
    public boolean isAllowInterceptTouchEvent() {
        return !this.mHelper.isOutSideTouchable();
    }

    public boolean isOutSideTouchable() {
        return this.mHelper.isOutSideTouchable();
    }

    public BasePopupWindow setAlignBackground(boolean z) {
        this.mHelper.setAlignBackgound(z);
        return this;
    }

    public BasePopupWindow setAlignBackgroundGravity(int i) {
        this.mHelper.setAlignBackgroundGravity(i);
        return this;
    }

    public BasePopupWindow linkTo(View view) {
        if (view == null) {
            LinkedViewLayoutChangeListenerWrapper linkedViewLayoutChangeListenerWrapper = this.mLinkedViewLayoutChangeListenerWrapper;
            if (linkedViewLayoutChangeListenerWrapper != null) {
                linkedViewLayoutChangeListenerWrapper.removeListener();
                this.mLinkedViewLayoutChangeListenerWrapper = null;
            }
            WeakReference<View> weakReference = this.mLinkedViewRef;
            if (weakReference != null) {
                weakReference.clear();
                this.mLinkedViewRef = null;
                return this;
            }
        }
        this.mLinkedViewRef = new WeakReference<>(view);
        return this;
    }

    public BasePopupWindow setWidth(int i) {
        this.mHelper.setPopupViewWidth(i);
        return this;
    }

    public BasePopupWindow setHeight(int i) {
        this.mHelper.setPopupViewHeight(i);
        return this;
    }

    public BasePopupWindow setMaxWidth(int i) {
        this.mHelper.setMaxWidth(i);
        return this;
    }

    public BasePopupWindow setMaxHeight(int i) {
        this.mHelper.setMaxHeight(i);
        return this;
    }

    public BasePopupWindow setMinWidth(int i) {
        this.mHelper.setMinWidth(i);
        return this;
    }

    public BasePopupWindow setMinHeight(int i) {
        this.mHelper.setMinHeight(i);
        return this;
    }

    public BasePopupWindow setKeepSize(boolean z) {
        this.mHelper.keepSize(z);
        return this;
    }

    public BasePopupWindow attachLifeCycle(Object obj) {
        return BasePopupSupporterManager.getInstance().proxy.attachLifeCycle(this, obj);
    }

    public BasePopupWindow removeLifeCycle(Object obj) {
        return BasePopupSupporterManager.getInstance().proxy.removeLifeCycle(this, obj);
    }

    public <P extends BasePopupWindow> BasePopupWindow setEventInterceptor(PopupWindowEventInterceptor<P> popupWindowEventInterceptor) {
        this.mEventInterceptor = popupWindowEventInterceptor;
        this.mHelper.setEventInterceptor(popupWindowEventInterceptor);
        return this;
    }

    public void dismiss() {
        dismiss(true);
    }

    public void dismiss(boolean z) {
        if (z) {
            try {
                try {
                    if (this.mAutoShowInputEdittext != null && this.mHelper.isAutoShowInputMethod()) {
                        InputMethodUtils.close(this.mAutoShowInputEdittext);
                    }
                } catch (Exception e) {
                    PopupLog.m27e(TAG, e);
                    e.printStackTrace();
                }
            } finally {
                this.mPopupWindow.dismiss();
            }
        } else {
            dismissWithOutAnimate();
        }
        removeListener();
    }

    void removeListener() {
        removeGlobalListener();
        removeLinkedLayoutListener();
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onBeforeDismiss() {
        return checkPerformDismiss();
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean callDismissAtOnce() {
        long duration;
        if (this.mHelper.getDismissAnimation() != null && this.mDisplayAnimateView != null) {
            if (!this.isExitAnimatePlaying) {
                duration = this.mHelper.getDismissAnimation().getDuration();
                this.mHelper.getDismissAnimation().cancel();
                this.mDisplayAnimateView.startAnimation(this.mHelper.getDismissAnimation());
                callDismissAnimationStart();
                this.isExitAnimatePlaying = true;
            }
            duration = -1;
        } else {
            if (this.mHelper.getDismissAnimator() != null && !this.isExitAnimatePlaying) {
                duration = this.mHelper.getDismissAnimator().getDuration();
                this.mHelper.getDismissAnimator().start();
                callDismissAnimationStart();
                this.isExitAnimatePlaying = true;
            }
            duration = -1;
        }
        this.mContentView.postDelayed(new Runnable() { // from class: razerdp.basepopup.BasePopupWindow.4
            @Override // java.lang.Runnable
            public void run() {
                BasePopupWindow.this.isExitAnimatePlaying = false;
                BasePopupWindow.this.mPopupWindow.callSuperDismiss();
            }
        }, Math.max(this.mHelper.getDismissAnimationDuration(), duration));
        this.mHelper.onDismiss(duration > -1);
        return duration <= 0;
    }

    private void callDismissAnimationStart() {
        if (getOnDismissListener() != null) {
            getOnDismissListener().onDismissAnimationStart();
        }
    }

    public void dismissWithOutAnimate() {
        if (!checkPerformDismiss()) {
            return;
        }
        if (this.mHelper.getDismissAnimation() != null && this.mDisplayAnimateView != null) {
            this.mHelper.getDismissAnimation().cancel();
        }
        if (this.mHelper.getDismissAnimator() != null) {
            this.mHelper.getDismissAnimator().cancel();
        }
        if (this.mAutoShowInputEdittext != null && this.mHelper.isAutoShowInputMethod()) {
            InputMethodUtils.close(this.mAutoShowInputEdittext);
        }
        this.mPopupWindow.callSuperDismiss();
        this.mHelper.onDismiss(false);
        removeListener();
    }

    void forceDismiss() {
        if (this.mHelper.getDismissAnimation() != null && this.mDisplayAnimateView != null) {
            this.mHelper.getDismissAnimation().cancel();
        }
        if (this.mHelper.getDismissAnimator() != null) {
            this.mHelper.getDismissAnimator().cancel();
        }
        if (this.mAutoShowInputEdittext != null && this.mHelper.isAutoShowInputMethod()) {
            InputMethodUtils.close(this.mAutoShowInputEdittext);
        }
        this.mPopupWindow.callSuperDismiss();
        this.mHelper.onDismiss(false);
        removeListener();
    }

    private boolean checkPerformDismiss() {
        return (this.mHelper.getOnDismissListener() != null ? this.mHelper.getOnDismissListener().onBeforeDismiss() : true) && !this.isExitAnimatePlaying;
    }

    private boolean checkPerformShow(View view) {
        boolean z = true;
        if (this.mHelper.getOnBeforeShowCallback() != null) {
            OnBeforeShowCallback onBeforeShowCallback = this.mHelper.getOnBeforeShowCallback();
            View view2 = this.mContentView;
            if (this.mHelper.getShowAnimation() == null && this.mHelper.getShowAnimator() == null) {
                z = false;
            }
            return onBeforeShowCallback.onBeforeShow(view2, view, z);
        }
        return true;
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onBackPressed() {
        if (this.mHelper.isBackPressEnable()) {
            dismiss();
            return true;
        }
        return false;
    }

    @Override // razerdp.basepopup.PopupTouchController
    public boolean onOutSideTouch() {
        if (!this.mHelper.isOutSideDismiss()) {
            return !this.mHelper.isOutSideTouchable();
        }
        dismiss();
        return true;
    }

    protected Animation getTranslateVerticalAnimation(int i, int i2, int i3) {
        return SimpleAnimationUtils.getTranslateVerticalAnimation(i, i2, i3);
    }

    protected Animation getTranslateVerticalAnimation(float f, float f2, int i) {
        return SimpleAnimationUtils.getTranslateVerticalAnimation(f, f2, i);
    }

    protected Animation getScaleAnimation(float f, float f2, float f3, float f4, int i, float f5, int i2, float f6) {
        return SimpleAnimationUtils.getScaleAnimation(f, f2, f3, f4, i, f5, i2, f6);
    }

    protected Animation getDefaultScaleAnimation() {
        return getDefaultScaleAnimation(true);
    }

    protected Animation getDefaultScaleAnimation(boolean z) {
        return SimpleAnimationUtils.getDefaultScaleAnimation(z);
    }

    protected Animation getDefaultAlphaAnimation() {
        return getDefaultAlphaAnimation(true);
    }

    protected Animation getDefaultAlphaAnimation(boolean z) {
        return SimpleAnimationUtils.getDefaultAlphaAnimation(z);
    }

    protected AnimatorSet getDefaultSlideFromBottomAnimationSet() {
        return SimpleAnimationUtils.getDefaultSlideFromBottomAnimationSet(this.mDisplayAnimateView);
    }

    public int getScreenHeight() {
        return PopupUiUtils.getScreenHeightCompat(getContext());
    }

    public int getScreenWidth() {
        return PopupUiUtils.getScreenWidthCompat(getContext());
    }

    @Override // android.widget.PopupWindow.OnDismissListener
    public void onDismiss() {
        if (this.mHelper.getOnDismissListener() != null) {
            this.mHelper.getOnDismissListener().onDismiss();
        }
        this.isExitAnimatePlaying = false;
    }

    protected float dipToPx(float f) {
        return getContext() == null ? f : (f * getContext().getResources().getDisplayMetrics().density) + 0.5f;
    }

    public static void setDebugMode(boolean z) {
        DEBUG = z;
        PopupLog.setOpenLog(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class GlobalLayoutListenerWrapper implements ViewTreeObserver.OnGlobalLayoutListener {
        private boolean fullScreen;
        private OnKeyboardStateChangeListener mListener;
        private WeakReference<View> target;
        int preKeyboardHeight = -1;
        Rect rect = new Rect();
        boolean preVisible = false;
        private volatile boolean isAttached = false;

        GlobalLayoutListenerWrapper(View view, boolean z, OnKeyboardStateChangeListener onKeyboardStateChangeListener) {
            this.target = new WeakReference<>(view);
            this.fullScreen = z;
            this.mListener = onKeyboardStateChangeListener;
        }

        boolean isAttached() {
            return this.isAttached;
        }

        void addSelf() {
            if (getTarget() == null || this.isAttached) {
                return;
            }
            getTarget().getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.isAttached = true;
        }

        void remove() {
            if (getTarget() == null || !this.isAttached) {
                return;
            }
            getTarget().getViewTreeObserver().removeOnGlobalLayoutListener(this);
            this.isAttached = false;
        }

        View getTarget() {
            WeakReference<View> weakReference = this.target;
            if (weakReference == null) {
                return null;
            }
            return weakReference.get();
        }

        @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
        public void onGlobalLayout() {
            View target = getTarget();
            if (target == null) {
                return;
            }
            this.rect.setEmpty();
            target.getWindowVisibleDisplayFrame(this.rect);
            boolean z = false;
            if (!this.fullScreen) {
                this.rect.offset(0, -PopupUiUtils.getStatusBarHeight(target.getContext()));
            }
            int height = this.rect.height();
            int height2 = target.getHeight();
            int i = height2 - height;
            if (i > height2 * 0.25f) {
                z = true;
            }
            int i2 = z ? this.rect.bottom : -1;
            if (z == this.preVisible && this.preKeyboardHeight == i) {
                return;
            }
            OnKeyboardStateChangeListener onKeyboardStateChangeListener = this.mListener;
            if (onKeyboardStateChangeListener != null) {
                onKeyboardStateChangeListener.onKeyboardChange(i2, i, z, this.fullScreen);
            }
            this.preVisible = z;
            this.preKeyboardHeight = i;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class LinkedViewLayoutChangeListenerWrapper implements ViewTreeObserver.OnPreDrawListener {
        private boolean hasChange;
        private boolean isAdded;
        private int lastHeight;
        Rect lastLocationRect;
        private boolean lastShowState;
        private int lastVisible;
        private int lastWidth;
        private float lastX;
        private float lastY;
        Rect newLocationRect;

        private LinkedViewLayoutChangeListenerWrapper() {
            this.lastLocationRect = new Rect();
            this.newLocationRect = new Rect();
        }

        void addSelf() {
            if (BasePopupWindow.this.mLinkedViewRef == null || BasePopupWindow.this.mLinkedViewRef.get() == null || this.isAdded) {
                return;
            }
            View view = (View) BasePopupWindow.this.mLinkedViewRef.get();
            view.getGlobalVisibleRect(this.lastLocationRect);
            refreshViewParams();
            view.getViewTreeObserver().addOnPreDrawListener(this);
            this.isAdded = true;
        }

        void removeListener() {
            if (BasePopupWindow.this.mLinkedViewRef == null || BasePopupWindow.this.mLinkedViewRef.get() == null || !this.isAdded) {
                return;
            }
            ((View) BasePopupWindow.this.mLinkedViewRef.get()).getViewTreeObserver().removeOnPreDrawListener(this);
            this.isAdded = false;
        }

        void refreshViewParams() {
            if (BasePopupWindow.this.mLinkedViewRef == null || BasePopupWindow.this.mLinkedViewRef.get() == null) {
                return;
            }
            View view = (View) BasePopupWindow.this.mLinkedViewRef.get();
            float x = view.getX();
            float y = view.getY();
            int width = view.getWidth();
            int height = view.getHeight();
            int visibility = view.getVisibility();
            boolean isShown = view.isShown();
            this.hasChange = !(x == this.lastX && y == this.lastY && width == this.lastWidth && height == this.lastHeight && visibility == this.lastVisible) && this.isAdded;
            if (!this.hasChange) {
                view.getGlobalVisibleRect(this.newLocationRect);
                if (!this.newLocationRect.equals(this.lastLocationRect)) {
                    this.lastLocationRect.set(this.newLocationRect);
                    if (!handleShowChange(view, this.lastShowState, isShown)) {
                        this.hasChange = true;
                    }
                }
            }
            this.lastX = x;
            this.lastY = y;
            this.lastWidth = width;
            this.lastHeight = height;
            this.lastVisible = visibility;
            this.lastShowState = isShown;
        }

        private boolean handleShowChange(View view, boolean z, boolean z2) {
            if (z && !z2) {
                if (BasePopupWindow.this.isShowing()) {
                    BasePopupWindow.this.dismiss(false);
                    return true;
                }
            } else if (!z && z2 && !BasePopupWindow.this.isShowing()) {
                BasePopupWindow.this.tryToShowPopup(view, false, true);
                return true;
            }
            return false;
        }

        @Override // android.view.ViewTreeObserver.OnPreDrawListener
        public boolean onPreDraw() {
            if (BasePopupWindow.this.mLinkedViewRef != null && BasePopupWindow.this.mLinkedViewRef.get() != null) {
                refreshViewParams();
                if (this.hasChange) {
                    BasePopupWindow basePopupWindow = BasePopupWindow.this;
                    basePopupWindow.update((View) basePopupWindow.mLinkedViewRef.get());
                }
            }
            return true;
        }
    }

    /* loaded from: classes4.dex */
    private class DelayInitCached {
        int height;
        int width;

        private DelayInitCached(BasePopupWindow basePopupWindow) {
        }
    }
}
