package com.tomatolive.library.base;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.p002v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.View;
import com.blankj.utilcode.util.ToastUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.p136ui.activity.live.PrepareLiveActivity;
import com.tomatolive.library.p136ui.activity.live.TomatoLiveActivity;
import com.tomatolive.library.p136ui.view.dialog.alert.LiveKickOutDialog;
import com.tomatolive.library.p136ui.view.dialog.alert.TokenInvalidDialog;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar;
import com.tomatolive.library.service.NetworkChangeReceiver;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.SoftKeyboardUtils;
import com.tomatolive.library.utils.language.MultiLanguageUtil;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/* loaded from: classes3.dex */
public abstract class BaseActivity<T extends BasePresenter> extends RxAppCompatActivity implements NetworkChangeReceiver.NetChangeListener {
    protected static boolean hasRemindTraffic;
    protected String TAG;
    private CompositeDisposable compositeDisposable;
    private LocalBroadcastManager localBroadcastManager;
    protected Activity mActivity;
    protected Context mContext;
    protected ImmersionBar mImmersionBar;
    protected T mPresenter;
    protected StateView mStateView;
    private BaseActivity<T>.MyKickOutBroadCastReceiver myKickOutBroadCastReceiver;
    private BaseActivity<T>.MyTokenInvalidBroadCastReceiver myTokenInvalidBroadCastReceiver;
    private NetworkChangeReceiver networkChangeReceiver;
    public int pageNum = 1;
    protected Bundle savedInstanceState;

    /* renamed from: createPresenter */
    protected abstract T mo6636createPresenter();

    @LayoutRes
    protected abstract int getLayoutId();

    public LifecycleProvider<ActivityEvent> getLifecycleProvider() {
        return this;
    }

    public void initData() {
    }

    public void initListener() {
    }

    public abstract void initView(Bundle bundle);

    protected View injectStateView() {
        return null;
    }

    public boolean isAutoRefreshDataEnable() {
        return false;
    }

    public void onAutoRefreshData() {
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(BaseEvent baseEvent) {
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
    }

    @Override // com.tomatolive.library.service.NetworkChangeReceiver.NetChangeListener
    public void onNetChangeListener(int i) {
    }

    @Override // android.app.Activity, android.view.ContextThemeWrapper, android.content.ContextWrapper
    protected void attachBaseContext(Context context) {
        super.attachBaseContext(MultiLanguageUtil.getInstance().attachBaseContext(context));
    }

    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        getWindow().setFlags(8192, 8192);
        super.onCreate(bundle);
        init(bundle);
    }

    private void init(Bundle bundle) {
        this.TAG = getClass().getSimpleName();
        this.mActivity = this;
        this.mContext = this;
        EventBus.getDefault().register(this);
        setContentView(getLayoutId());
        SoftKeyboardUtils.init(this);
        this.savedInstanceState = bundle;
        initNetworkChangeReceiver();
        this.mStateView = injectStateView() != null ? StateView.inject(injectStateView()) : StateView.inject(this);
        initImmersionBar();
        this.mPresenter = mo6636createPresenter();
        initView(bundle);
        initData();
        initListener();
        registerDialogReceiver();
    }

    public void initImmersionBar() {
        View findViewById = findViewById(R$id.title_top_view);
        float f = 0.0f;
        if (findViewById == null) {
            this.mImmersionBar = ImmersionBar.with(this);
            ImmersionBar immersionBar = this.mImmersionBar;
            if (!ImmersionBar.isSupportStatusBarDarkFont()) {
                f = 0.2f;
            }
            immersionBar.statusBarDarkFont(true, f);
            immersionBar.init();
            return;
        }
        this.mImmersionBar = ImmersionBar.with(this);
        ImmersionBar immersionBar2 = this.mImmersionBar;
        immersionBar2.titleBar(findViewById);
        if (!ImmersionBar.isSupportStatusBarDarkFont()) {
            f = 0.2f;
        }
        immersionBar2.statusBarDarkFont(true, f);
        immersionBar2.init();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onStart() {
        try {
            super.onStart();
        } catch (Exception unused) {
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onResume() {
        super.onResume();
        if (!isAutoRefreshDataEnable()) {
            return;
        }
        onReleaseDisposable();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onPause() {
        super.onPause();
        if (!isAutoRefreshDataEnable()) {
            return;
        }
        if (this.compositeDisposable == null) {
            this.compositeDisposable = new CompositeDisposable();
        }
        this.compositeDisposable.add(Observable.interval(300L, 300L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.base.BaseActivity.1
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                BaseActivity.this.onAutoRefreshData();
            }
        }));
    }

    @Override // com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        onReleaseDisposable();
        ImmersionBar immersionBar = this.mImmersionBar;
        if (immersionBar != null) {
            immersionBar.destroy();
        }
        unRegisterDialogReceiver();
        NetworkChangeReceiver networkChangeReceiver = this.networkChangeReceiver;
        if (networkChangeReceiver != null) {
            unregisterReceiver(networkChangeReceiver);
        }
        T t = this.mPresenter;
        if (t != null) {
            t.detachView();
        }
    }

    @Override // android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity, android.content.ComponentCallbacks
    public void onConfigurationChanged(Configuration configuration) {
        if (configuration.fontScale != 1.0f) {
            getResources();
        }
        super.onConfigurationChanged(configuration);
    }

    @Override // android.support.p005v7.app.AppCompatActivity, android.view.ContextThemeWrapper, android.content.ContextWrapper, android.content.Context
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1.0f) {
            Configuration configuration = new Configuration();
            configuration.setToDefaults();
            resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        }
        return resources;
    }

    public void showToast(String str) {
        ToastUtils.showShort(str);
    }

    public void showToast(@StringRes int i) {
        showToast(getString(i));
    }

    public void startActivity(Class<? extends Activity> cls) {
        startActivity(new Intent(this, cls));
    }

    public void startActivityByLogin(Class<? extends Activity> cls) {
        if (!AppUtils.isLogin(this.mContext)) {
            return;
        }
        startActivity(new Intent(this, cls));
    }

    public void startActivityByRestrictionUserLogin(Class<? extends Activity> cls) {
        if (!AppUtils.isConsumptionPermissionUser(this.mContext)) {
            return;
        }
        startActivity(new Intent(this, cls));
    }

    public void setActivityTitle(@StringRes int i) {
        setActivityTitle(getString(i));
    }

    public void setActivityTitle(String str) {
        BGATitleBar bGATitleBar = (BGATitleBar) findViewById(R$id.tb_prepare_title_bar);
        if (bGATitleBar == null) {
            return;
        }
        bGATitleBar.setLeftDrawable(R$drawable.fq_ic_title_back);
        bGATitleBar.setTitleText(str);
        setTitle(str);
        bGATitleBar.setDelegate(new TitleBarOnClickListener());
    }

    public void setActivityTitle(@DrawableRes int i, String str) {
        BGATitleBar bGATitleBar = (BGATitleBar) findViewById(R$id.tb_prepare_title_bar);
        if (bGATitleBar == null) {
            return;
        }
        bGATitleBar.setLeftDrawable(i);
        bGATitleBar.setTitleText(str);
        setTitle(str);
        bGATitleBar.setDelegate(new TitleBarOnClickListener());
    }

    public void setActivityRightTitle(String str, String str2, View.OnClickListener onClickListener) {
        BGATitleBar bGATitleBar = (BGATitleBar) findViewById(R$id.tb_prepare_title_bar);
        if (bGATitleBar == null) {
            return;
        }
        bGATitleBar.setLeftDrawable(R$drawable.fq_ic_title_back);
        bGATitleBar.setTitleText(str);
        setTitle(str);
        bGATitleBar.setRightText(str2);
        bGATitleBar.setDelegate(new TitleBarOnClickListener(bGATitleBar, onClickListener));
    }

    public void setActivityRightIconTitle(String str, @DrawableRes int i, View.OnClickListener onClickListener) {
        BGATitleBar bGATitleBar = (BGATitleBar) findViewById(R$id.tb_prepare_title_bar);
        if (bGATitleBar == null) {
            return;
        }
        bGATitleBar.setLeftDrawable(R$drawable.fq_ic_title_back);
        bGATitleBar.setTitleText(str);
        setTitle(str);
        bGATitleBar.setRightDrawable(i);
        bGATitleBar.setDelegate(new TitleBarOnClickListener(bGATitleBar, onClickListener));
    }

    public void setActivityRightTitle(@StringRes int i, @StringRes int i2, View.OnClickListener onClickListener) {
        setActivityRightTitle(getString(i), getString(i2), onClickListener);
    }

    public void setActivityTitle(@StringRes int i, @StringRes int i2, @StringRes int i3, View.OnClickListener onClickListener) {
        setActivityTitle(getString(i), getString(i2), getString(i3), onClickListener);
    }

    public void setActivityTitle(String str, String str2, String str3, View.OnClickListener onClickListener) {
        BGATitleBar bGATitleBar = (BGATitleBar) findViewById(R$id.tb_prepare_title_bar);
        if (bGATitleBar == null) {
            return;
        }
        bGATitleBar.setLeftText(str);
        bGATitleBar.setTitleText(str2);
        setTitle(str2);
        bGATitleBar.setRightText(str3);
        bGATitleBar.setDelegate(new TitleBarOnClickListener(bGATitleBar, onClickListener));
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class TitleBarOnClickListener implements BGATitleBar.Delegate {
        private View.OnClickListener rightListener;
        private View.OnClickListener rightListenerSec;
        private BGATitleBar titleBar;

        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickTitleCtv() {
        }

        public TitleBarOnClickListener() {
        }

        public TitleBarOnClickListener(BGATitleBar bGATitleBar, View.OnClickListener onClickListener) {
            this.titleBar = bGATitleBar;
            this.rightListener = onClickListener;
        }

        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickLeftCtv() {
            BaseActivity.this.onBackPressed();
        }

        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickRightCtv() {
            BGATitleBar bGATitleBar;
            View.OnClickListener onClickListener = this.rightListener;
            if (onClickListener == null || (bGATitleBar = this.titleBar) == null) {
                return;
            }
            onClickListener.onClick(bGATitleBar.getRightCtv());
        }

        @Override // com.tomatolive.library.p136ui.view.widget.titlebar.BGATitleBar.Delegate
        public void onClickRightSecondaryCtv() {
            BGATitleBar bGATitleBar;
            View.OnClickListener onClickListener = this.rightListenerSec;
            if (onClickListener == null || (bGATitleBar = this.titleBar) == null) {
                return;
            }
            onClickListener.onClick(bGATitleBar.getRightSecondaryCtv());
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class MyKickOutBroadCastReceiver extends BroadcastReceiver {
        private MyKickOutBroadCastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && TextUtils.equals(intent.getAction(), ConstantUtils.LIVE_KICK_OUT_ACTION)) {
                BaseActivity.this.showKickOutDialog(intent.getStringExtra(ConstantUtils.RESULT_ITEM));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class MyTokenInvalidBroadCastReceiver extends BroadcastReceiver {
        private MyTokenInvalidBroadCastReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && TextUtils.equals(intent.getAction(), ConstantUtils.LIVE_TOKEN_INVALID_ACTION)) {
                BaseActivity.this.showTokenInvalidDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTokenInvalidDialog() {
        if (AppUtils.isRunBackground(this.mContext)) {
            return;
        }
        TokenInvalidDialog.newInstance().show(getSupportFragmentManager());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKickOutDialog(String str) {
        if (AppUtils.isRunBackground(this.mContext)) {
            return;
        }
        LiveKickOutDialog.newInstance(str).show(getSupportFragmentManager());
    }

    private void registerDialogReceiver() {
        registerKickDialogReceiver();
        registerTokenDialogReceiver();
    }

    private void unRegisterDialogReceiver() {
        unRegisterKickDialogReceiver();
        unRegisterTokenDialogReceiver();
    }

    private void registerKickDialogReceiver() {
        this.localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(ConstantUtils.LIVE_KICK_OUT_ACTION);
        this.myKickOutBroadCastReceiver = new MyKickOutBroadCastReceiver();
        this.localBroadcastManager.registerReceiver(this.myKickOutBroadCastReceiver, intentFilter);
    }

    private void unRegisterKickDialogReceiver() {
        BaseActivity<T>.MyKickOutBroadCastReceiver myKickOutBroadCastReceiver;
        LocalBroadcastManager localBroadcastManager = this.localBroadcastManager;
        if (localBroadcastManager == null || (myKickOutBroadCastReceiver = this.myKickOutBroadCastReceiver) == null) {
            return;
        }
        localBroadcastManager.unregisterReceiver(myKickOutBroadCastReceiver);
    }

    private void registerTokenDialogReceiver() {
        this.localBroadcastManager = LocalBroadcastManager.getInstance(this);
        IntentFilter intentFilter = new IntentFilter(ConstantUtils.LIVE_TOKEN_INVALID_ACTION);
        this.myTokenInvalidBroadCastReceiver = new MyTokenInvalidBroadCastReceiver();
        this.localBroadcastManager.registerReceiver(this.myTokenInvalidBroadCastReceiver, intentFilter);
    }

    private void unRegisterTokenDialogReceiver() {
        BaseActivity<T>.MyTokenInvalidBroadCastReceiver myTokenInvalidBroadCastReceiver;
        LocalBroadcastManager localBroadcastManager = this.localBroadcastManager;
        if (localBroadcastManager == null || (myTokenInvalidBroadCastReceiver = this.myTokenInvalidBroadCastReceiver) == null) {
            return;
        }
        localBroadcastManager.unregisterReceiver(myTokenInvalidBroadCastReceiver);
    }

    private void initNetworkChangeReceiver() {
        Activity activity = this.mActivity;
        if ((activity instanceof TomatoLiveActivity) || (activity instanceof PrepareLiveActivity)) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
            this.networkChangeReceiver = new NetworkChangeReceiver();
            this.networkChangeReceiver.setOnNetChangeListener(this);
            registerReceiver(this.networkChangeReceiver, intentFilter);
        }
    }

    private void onReleaseDisposable() {
        CompositeDisposable compositeDisposable = this.compositeDisposable;
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            this.compositeDisposable = null;
        }
    }
}
