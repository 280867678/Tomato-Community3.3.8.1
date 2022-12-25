package com.tomatolive.library.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.barlibrary.ImmersionBar;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxFragment;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class BaseFragment<T extends BasePresenter> extends RxFragment {
    protected String TAG;
    private CompositeDisposable compositeDisposable;
    private boolean isLazyLoaded;
    protected boolean isLoadingMore;
    protected boolean isNoMoreData;
    private boolean isPrepared;
    protected Activity mActivity;
    protected Context mContext;
    protected View mFragmentRootView;
    protected ImmersionBar mImmersionBar;
    protected T mPresenter;
    protected StateView mStateView;
    protected String pageStayTimer;
    protected int pageNum = 1;
    protected boolean isDownRefresh = false;

    /* renamed from: createPresenter */
    protected abstract T mo6641createPresenter();

    public void getBundle(Bundle bundle) {
    }

    @LayoutRes
    public abstract int getLayoutId();

    public View getLayoutView() {
        return null;
    }

    public LifecycleProvider<FragmentEvent> getLifecycleProvider() {
        return this;
    }

    public void initListener(View view) {
    }

    public abstract void initView(View view, @Nullable Bundle bundle);

    protected View injectStateView(View view) {
        return null;
    }

    public boolean isAutoRefreshDataEnable() {
        return false;
    }

    public boolean isEnablePageStayReport() {
        return false;
    }

    public boolean isLazyLoad() {
        return false;
    }

    public void onAutoRefreshData() {
    }

    @Subscribe
    public void onEventMainThread(BaseEvent baseEvent) {
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
    }

    public void onFragmentVisible(boolean z) {
    }

    @UiThread
    public void onLazyLoad() {
    }

    @Override // android.support.p002v4.app.Fragment
    public void onAttach(Context context) {
        super.onAttach(context);
        onAttachToContext(context);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (Build.VERSION.SDK_INT < 23) {
            onAttachToContext(activity);
        }
    }

    public void onAttachToContext(Context context) {
        this.mContext = context;
        this.mActivity = getActivity() == null ? (Activity) context : getActivity();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.TAG = getClass().getSimpleName();
        this.mPresenter = mo6641createPresenter();
        getBundle(getArguments());
        EventBus.getDefault().register(this);
    }

    @Override // android.support.p002v4.app.Fragment
    public View onCreateView(@NonNull LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        if (getLayoutView() != null) {
            return getLayoutView();
        }
        this.mFragmentRootView = layoutInflater.inflate(getLayoutId(), viewGroup, false);
        return this.mFragmentRootView;
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onViewCreated(View view, @Nullable Bundle bundle) {
        super.onViewCreated(view, bundle);
        if (injectStateView(view) != null) {
            this.mStateView = StateView.inject(injectStateView(view));
        }
        initView(view, bundle);
        initListener(view);
    }

    @Override // android.support.p002v4.app.Fragment
    public void onActivityCreated(@Nullable Bundle bundle) {
        super.onActivityCreated(bundle);
        if (isLazyLoad()) {
            this.isPrepared = true;
            lazyLoad();
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onStart() {
        try {
            super.onStart();
        } catch (Exception unused) {
        }
    }

    @Override // android.support.p002v4.app.Fragment
    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (isEnablePageStayReport()) {
            if (z) {
                this.pageStayTimer = LogEventUtils.startLiveListDuration();
            } else if (isAdded()) {
                LogEventUtils.uploadLiveListDuration(this.pageStayTimer, getPageStayTimerType());
            }
        }
        if (isLazyLoad()) {
            lazyLoad();
        }
        if (!this.isLazyLoaded) {
            return;
        }
        onFragmentVisible(z);
    }

    private void lazyLoad() {
        if (!getUserVisibleHint() || !this.isPrepared || this.isLazyLoaded) {
            return;
        }
        onLazyLoad();
        this.isLazyLoaded = true;
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (!isAutoRefreshDataEnable()) {
            return;
        }
        onReleaseDisposable();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (!isAutoRefreshDataEnable()) {
            return;
        }
        onReleaseDisposable();
        if (this.compositeDisposable == null) {
            this.compositeDisposable = new CompositeDisposable();
        }
        this.compositeDisposable.add(Observable.interval(300L, 300L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.base.BaseFragment.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                if (BaseFragment.this.getUserVisibleHint()) {
                    BaseFragment.this.onAutoRefreshData();
                }
            }
        }));
    }

    @Override // com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        onReleaseDisposable();
        ImmersionBar immersionBar = this.mImmersionBar;
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        T t = this.mPresenter;
        if (t != null) {
            t.detachView();
        }
    }

    public String getPageStayTimerType() {
        return getString(R$string.fq_hot_list);
    }

    private void onReleaseDisposable() {
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            this.compositeDisposable = null;
        }
    }

    public boolean isLoginUser() {
        return AppUtils.isLogin(this.mContext);
    }

    public boolean isConsumptionPermissionUser() {
        return AppUtils.isConsumptionPermissionUser(this.mContext);
    }

    public boolean isAutoPreLoadingMore(RecyclerView recyclerView) {
        if (!this.isLoadingMore && !this.isNoMoreData) {
            GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
            BaseQuickAdapter baseQuickAdapter = (BaseQuickAdapter) recyclerView.getAdapter();
            int itemCount = baseQuickAdapter.getItemCount();
            int findLastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
            int childCount = recyclerView.getChildCount();
            int headerLayoutCount = baseQuickAdapter.getHeaderLayoutCount();
            int spanCount = gridLayoutManager.getSpanCount();
            int i = (itemCount - headerLayoutCount) % spanCount;
            if (i != 0) {
                spanCount = i;
            }
            int i2 = itemCount - 1;
            int i3 = i2 - spanCount;
            if ((findLastVisibleItemPosition == i3 || findLastVisibleItemPosition == i3 - 2 || findLastVisibleItemPosition == i2) && childCount > 0) {
                return true;
            }
        }
        return false;
    }

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this.mContext, cls));
    }

    public void startActivity(Class<? extends Activity> cls, String str, int i) {
        Intent intent = new Intent(this.mContext, cls);
        intent.putExtra(str, i);
        startActivity(intent);
    }

    public void showToast(String str) {
        ToastUtils.showShort(str);
    }

    public void showToast(@StringRes int i) {
        showToast(getString(i));
    }
}
