package com.tomatolive.library.p136ui.view.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.p002v4.view.NestedScrollingChild;
import android.support.p002v4.view.NestedScrollingParent;
import android.support.p002v4.view.ScrollingView;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import com.tomatolive.library.R$attr;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$styleable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* renamed from: com.tomatolive.library.ui.view.widget.StateView */
/* loaded from: classes4.dex */
public class StateView extends View {
    private static final int EMPTY = 0;
    private static final int LOADING = 2;
    private static final int RETRY = 1;
    private int mEmptyResource;
    private View mEmptyView;
    private OnInflateListener mInflateListener;
    private LayoutInflater mInflater;
    private RelativeLayout.LayoutParams mLayoutParams;
    private LoadingView mLoadingImageView;
    private int mLoadingResource;
    private View mLoadingView;
    private AnimatorProvider mProvider;
    private OnRetryClickListener mRetryClickListener;
    private int mRetryResource;
    private View mRetryView;

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.widget.StateView$OnInflateListener */
    /* loaded from: classes4.dex */
    public interface OnInflateListener {
        void onInflate(int i, View view);
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.StateView$OnRetryClickListener */
    /* loaded from: classes4.dex */
    public interface OnRetryClickListener {
        void onRetryClick();
    }

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.tomatolive.library.ui.view.widget.StateView$ViewType */
    /* loaded from: classes4.dex */
    public @interface ViewType {
    }

    @Override // android.view.View
    protected void dispatchDraw(Canvas canvas) {
    }

    @Override // android.view.View
    @SuppressLint({"MissingSuperCall"})
    public void draw(Canvas canvas) {
    }

    public static StateView inject(@NonNull Activity activity) {
        return inject(activity, false);
    }

    private static StateView inject(@NonNull Activity activity, boolean z) {
        return inject((ViewGroup) activity.getWindow().getDecorView().findViewById(16908290), z);
    }

    public static StateView inject(@NonNull ViewGroup viewGroup) {
        return inject(viewGroup, false);
    }

    private static StateView inject(@NonNull ViewGroup viewGroup, boolean z) {
        boolean z2 = viewGroup instanceof LinearLayout;
        int i = 0;
        if (z2 || (viewGroup instanceof ScrollView) || (viewGroup instanceof AdapterView) || (((viewGroup instanceof ScrollingView) && (viewGroup instanceof NestedScrollingChild)) || ((viewGroup instanceof NestedScrollingParent) && (viewGroup instanceof NestedScrollingChild)))) {
            ViewParent parent = viewGroup.getParent();
            if (parent == null) {
                FrameLayout frameLayout = new FrameLayout(viewGroup.getContext());
                frameLayout.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                if (z2) {
                    LinearLayout linearLayout = new LinearLayout(viewGroup.getContext());
                    linearLayout.setLayoutParams(viewGroup.getLayoutParams());
                    linearLayout.setOrientation(((LinearLayout) viewGroup).getOrientation());
                    int childCount = viewGroup.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        View childAt = viewGroup.getChildAt(0);
                        viewGroup.removeView(childAt);
                        linearLayout.addView(childAt);
                    }
                    frameLayout.addView(linearLayout);
                } else if ((viewGroup instanceof ScrollView) || (viewGroup instanceof ScrollingView)) {
                    if (viewGroup.getChildCount() != 1) {
                        throw new IllegalStateException("the ScrollView does not have one direct child");
                    }
                    View childAt2 = viewGroup.getChildAt(0);
                    viewGroup.removeView(childAt2);
                    frameLayout.addView(childAt2);
                    DisplayMetrics displayMetrics = new DisplayMetrics();
                    ((WindowManager) viewGroup.getContext().getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
                    i = displayMetrics.heightPixels;
                } else if ((viewGroup instanceof NestedScrollingParent) && (viewGroup instanceof NestedScrollingChild)) {
                    if (viewGroup.getChildCount() == 2) {
                        View childAt3 = viewGroup.getChildAt(1);
                        viewGroup.removeView(childAt3);
                        frameLayout.addView(childAt3);
                    } else if (viewGroup.getChildCount() > 2) {
                        throw new IllegalStateException("the view is not refresh layout? view = " + viewGroup.toString());
                    }
                } else {
                    throw new IllegalStateException("the view does not have parent, view = " + viewGroup.toString());
                }
                viewGroup.addView(frameLayout);
                viewGroup = frameLayout;
            } else {
                FrameLayout frameLayout2 = new FrameLayout(viewGroup.getContext());
                frameLayout2.setLayoutParams(viewGroup.getLayoutParams());
                viewGroup.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
                if (parent instanceof ViewGroup) {
                    ViewGroup viewGroup2 = (ViewGroup) parent;
                    viewGroup2.removeView(viewGroup);
                    viewGroup2.addView(frameLayout2);
                }
                frameLayout2.addView(viewGroup);
                viewGroup = frameLayout2;
            }
        }
        StateView stateView = new StateView(viewGroup.getContext());
        if (i > 0) {
            if (z) {
                i -= stateView.getActionBarHeight();
            }
            viewGroup.addView(stateView, new ViewGroup.LayoutParams(-1, i));
        } else {
            viewGroup.addView(stateView);
        }
        if (z) {
            stateView.setTopMargin();
        }
        stateView.getLayoutParams().width = -1;
        stateView.getLayoutParams().height = -1;
        return stateView;
    }

    public static StateView inject(@NonNull View view) {
        return inject(view, false);
    }

    private static StateView inject(@NonNull View view, boolean z) {
        if (view instanceof ViewGroup) {
            return inject((ViewGroup) view, z);
        }
        ViewParent parent = view.getParent();
        if (parent instanceof ViewGroup) {
            return inject((ViewGroup) parent, z);
        }
        throw new ClassCastException("view or view.getParent() must be ViewGroup");
    }

    public static StateView wrap(@NonNull View view) {
        ViewGroup viewGroup = (ViewGroup) view.getParent();
        viewGroup.removeView(view);
        FrameLayout frameLayout = new FrameLayout(view.getContext());
        frameLayout.addView(view);
        StateView stateView = new StateView(view.getContext());
        frameLayout.addView(stateView, view.getLayoutParams());
        viewGroup.addView(frameLayout);
        return stateView;
    }

    public StateView(Context context) {
        this(context, null);
    }

    public StateView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public StateView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.mProvider = null;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.StateView);
        this.mEmptyResource = obtainStyledAttributes.getResourceId(R$styleable.StateView_emptyResource, 0);
        this.mRetryResource = obtainStyledAttributes.getResourceId(R$styleable.StateView_retryResource, 0);
        this.mLoadingResource = obtainStyledAttributes.getResourceId(R$styleable.StateView_loadingResource, 0);
        obtainStyledAttributes.recycle();
        if (this.mEmptyResource == 0) {
            this.mEmptyResource = R$layout.fq_base_empty;
        }
        if (this.mRetryResource == 0) {
            this.mRetryResource = R$layout.fq_base_retry;
        }
        if (this.mLoadingResource == 0) {
            this.mLoadingResource = R$layout.fq_base_loading;
        }
        if (attributeSet == null) {
            this.mLayoutParams = new RelativeLayout.LayoutParams(-1, -1);
        } else {
            this.mLayoutParams = new RelativeLayout.LayoutParams(context, attributeSet);
        }
        setVisibility(8);
        setWillNotDraw(true);
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(0, 0);
    }

    @Override // android.view.View
    public void setVisibility(int i) {
        setVisibility(this.mEmptyView, i);
        setVisibility(this.mRetryView, i);
        setVisibility(this.mLoadingView, i);
    }

    private void setVisibility(View view, int i) {
        if (view == null || i == view.getVisibility()) {
            return;
        }
        if (this.mProvider != null) {
            startAnimation(view);
        } else {
            view.setVisibility(i);
        }
    }

    public void showContent() {
        LoadingView loadingView = this.mLoadingImageView;
        if (loadingView != null) {
            loadingView.stopLoading();
        }
        setVisibility(8);
    }

    public View showEmpty() {
        if (this.mEmptyView == null) {
            this.mEmptyView = inflate(this.mEmptyResource, 0);
        }
        LoadingView loadingView = this.mLoadingImageView;
        if (loadingView != null) {
            loadingView.stopLoading();
        }
        showView(this.mEmptyView);
        return this.mEmptyView;
    }

    public View showRetry() {
        if (this.mRetryView == null) {
            this.mRetryView = inflate(this.mRetryResource, 1);
            final View findViewById = this.mRetryView.findViewById(R$id.tv_btn_reload);
            if (findViewById != null) {
                findViewById.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.StateView.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (StateView.this.mRetryClickListener != null) {
                            StateView.this.showLoading();
                            findViewById.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.StateView.1.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    StateView.this.mRetryClickListener.onRetryClick();
                                }
                            }, 400L);
                        }
                    }
                });
            } else {
                this.mRetryView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.widget.StateView.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        if (StateView.this.mRetryClickListener != null) {
                            StateView.this.showLoading();
                            StateView.this.mRetryView.postDelayed(new Runnable() { // from class: com.tomatolive.library.ui.view.widget.StateView.2.1
                                @Override // java.lang.Runnable
                                public void run() {
                                    StateView.this.mRetryClickListener.onRetryClick();
                                }
                            }, 400L);
                        }
                    }
                });
            }
        }
        LoadingView loadingView = this.mLoadingImageView;
        if (loadingView != null) {
            loadingView.stopLoading();
        }
        showView(this.mRetryView);
        return this.mRetryView;
    }

    public View showLoading() {
        if (this.mLoadingView == null) {
            this.mLoadingView = inflate(this.mLoadingResource, 2);
            this.mLoadingImageView = (LoadingView) this.mLoadingView.findViewById(R$id.iv_loading);
        }
        showView(this.mLoadingView);
        LoadingView loadingView = this.mLoadingImageView;
        if (loadingView != null) {
            loadingView.showLoading();
        }
        return this.mLoadingView;
    }

    private void showView(View view) {
        setVisibility(view, 0);
        hideViews(view);
    }

    private void hideViews(View view) {
        View view2 = this.mEmptyView;
        if (view2 == view) {
            setVisibility(this.mLoadingView, 8);
            setVisibility(this.mRetryView, 8);
        } else if (this.mLoadingView == view) {
            setVisibility(view2, 8);
            setVisibility(this.mRetryView, 8);
        } else {
            setVisibility(view2, 8);
            setVisibility(this.mLoadingView, 8);
        }
    }

    private void startAnimation(final View view) {
        int i = 0;
        final boolean z = view.getVisibility() == 8;
        AnimatorProvider animatorProvider = this.mProvider;
        Animator showAnimation = z ? animatorProvider.showAnimation(view) : animatorProvider.hideAnimation(view);
        if (showAnimation == null) {
            if (!z) {
                i = 8;
            }
            view.setVisibility(i);
            return;
        }
        showAnimation.addListener(new AnimatorListenerAdapter() { // from class: com.tomatolive.library.ui.view.widget.StateView.3
            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationEnd(Animator animator) {
                super.onAnimationEnd(animator);
                if (!z) {
                    view.setVisibility(8);
                }
            }

            @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
            public void onAnimationStart(Animator animator) {
                super.onAnimationStart(animator);
                if (z) {
                    view.setVisibility(0);
                }
            }
        });
        showAnimation.start();
    }

    public void setAnimatorProvider(AnimatorProvider animatorProvider) {
        this.mProvider = animatorProvider;
        reset(this.mEmptyView);
        reset(this.mLoadingView);
        reset(this.mRetryView);
    }

    private void reset(View view) {
        if (view != null) {
            view.setTranslationX(0.0f);
            view.setTranslationY(0.0f);
            view.setAlpha(1.0f);
            view.setRotation(0.0f);
            view.setScaleX(1.0f);
            view.setScaleY(1.0f);
        }
    }

    private View inflate(@LayoutRes int i, int i2) {
        ViewParent parent = getParent();
        if (parent instanceof ViewGroup) {
            if (i != 0) {
                ViewGroup viewGroup = (ViewGroup) parent;
                LayoutInflater layoutInflater = this.mInflater;
                if (layoutInflater == null) {
                    layoutInflater = LayoutInflater.from(getContext());
                }
                View inflate = layoutInflater.inflate(i, viewGroup, false);
                int indexOfChild = viewGroup.indexOfChild(this);
                inflate.setClickable(true);
                inflate.setVisibility(8);
                ViewGroup.LayoutParams layoutParams = getLayoutParams();
                if (layoutParams != null) {
                    if (viewGroup instanceof RelativeLayout) {
                        ViewGroup.MarginLayoutParams marginLayoutParams = (ViewGroup.MarginLayoutParams) layoutParams;
                        this.mLayoutParams.setMargins(marginLayoutParams.leftMargin, marginLayoutParams.topMargin, marginLayoutParams.rightMargin, marginLayoutParams.bottomMargin);
                        viewGroup.addView(inflate, indexOfChild, this.mLayoutParams);
                    } else {
                        viewGroup.addView(inflate, indexOfChild, layoutParams);
                    }
                } else {
                    viewGroup.addView(inflate, indexOfChild);
                }
                if (this.mLoadingView != null && this.mRetryView != null && this.mEmptyView != null) {
                    viewGroup.removeViewInLayout(this);
                }
                OnInflateListener onInflateListener = this.mInflateListener;
                if (onInflateListener != null) {
                    onInflateListener.onInflate(i2, inflate);
                }
                return inflate;
            }
            throw new IllegalArgumentException("StateView must have a valid layoutResource");
        }
        throw new IllegalStateException("StateView must have a non-null ViewGroup viewParent");
    }

    private void setTopMargin() {
        ((ViewGroup.MarginLayoutParams) getLayoutParams()).topMargin = getActionBarHeight();
    }

    private int getActionBarHeight() {
        TypedValue typedValue = new TypedValue();
        if (getContext().getTheme().resolveAttribute(R$attr.actionBarSize, typedValue, true)) {
            return TypedValue.complexToDimensionPixelSize(typedValue.data, getResources().getDisplayMetrics());
        }
        return 0;
    }

    public void setEmptyResource(@LayoutRes int i) {
        this.mEmptyResource = i;
    }

    public void setRetryResource(@LayoutRes int i) {
        this.mRetryResource = i;
    }

    public void setLoadingResource(@LayoutRes int i) {
        this.mLoadingResource = i;
    }

    public LayoutInflater getInflater() {
        return this.mInflater;
    }

    public void setInflater(LayoutInflater layoutInflater) {
        this.mInflater = layoutInflater;
    }

    public void setOnRetryClickListener(OnRetryClickListener onRetryClickListener) {
        this.mRetryClickListener = onRetryClickListener;
    }

    public void setOnInflateListener(OnInflateListener onInflateListener) {
        this.mInflateListener = onInflateListener;
    }
}
