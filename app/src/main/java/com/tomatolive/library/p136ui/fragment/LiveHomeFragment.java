package com.tomatolive.library.p136ui.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.content.LocalBroadcastManager;
import android.support.p002v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.gyf.barlibrary.ImmersionBar;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.LabelEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.LabelMenuEvent;
import com.tomatolive.library.model.event.ListDataUpdateEvent;
import com.tomatolive.library.p136ui.activity.home.SearchActivity;
import com.tomatolive.library.p136ui.adapter.HomeMenuTagAdapter;
import com.tomatolive.library.p136ui.fragment.LiveHomeFragment;
import com.tomatolive.library.p136ui.presenter.HomePresenter;
import com.tomatolive.library.p136ui.view.dialog.alert.LiveKickOutDialog;
import com.tomatolive.library.p136ui.view.dialog.alert.TokenInvalidDialog;
import com.tomatolive.library.p136ui.view.iview.IHomeView;
import com.tomatolive.library.p136ui.view.widget.ScaleTransitionPagerTitleView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import java.util.ArrayList;
import java.util.List;
import net.lucode.hackware.magicindicator.MagicIndicator;
import net.lucode.hackware.magicindicator.ViewPagerHelper;
import net.lucode.hackware.magicindicator.buildins.UIUtil;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.CommonNavigator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerTitleView;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators.LinePagerIndicator;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.fragment.LiveHomeFragment */
/* loaded from: classes3.dex */
public class LiveHomeFragment extends BaseFragment<HomePresenter> implements IHomeView {
    private CommonNavigator commonNavigator;
    private CommonNavigatorAdapter commonNavigatorAdapter;
    private List<BaseFragment> fragmentList = new ArrayList();
    private List<LabelEntity> labelList = new ArrayList();
    private LocalBroadcastManager localBroadcastManager;
    private ViewPager mViewPager;
    private MagicIndicator magicIndicator;
    private HomeMenuTagAdapter menuTagAdapter;
    private MyKickOutBroadCastReceiver myKickOutBroadCastReceiver;
    private MyTokenInvalidBroadCastReceiver myTokenInvalidBroadCastReceiver;

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static LiveHomeFragment newInstance() {
        Bundle bundle = new Bundle();
        LiveHomeFragment liveHomeFragment = new LiveHomeFragment();
        liveHomeFragment.setArguments(bundle);
        return liveHomeFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public HomePresenter mo6641createPresenter() {
        return new HomePresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_live_home;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        initTitleBarStyle(view);
        this.magicIndicator = (MagicIndicator) view.findViewById(R$id.magic_indicator);
        this.mViewPager = (ViewPager) view.findViewById(R$id.view_pager);
        initViewPager();
        ((HomePresenter) this.mPresenter).sendInitRequest(this.mStateView, true);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$LiveHomeFragment$eym7PJDp1I0ykGMUZzcILtn3rLY
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                LiveHomeFragment.this.lambda$initListener$0$LiveHomeFragment();
            }
        });
        view.findViewById(R$id.iv_search).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$LiveHomeFragment$--jtMmu0nochhxGde17URkLt_Zk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LiveHomeFragment.this.lambda$initListener$1$LiveHomeFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LiveHomeFragment() {
        ((HomePresenter) this.mPresenter).sendInitRequest(this.mStateView, true);
    }

    public /* synthetic */ void lambda$initListener$1$LiveHomeFragment(View view) {
        startActivity(SearchActivity.class);
        LogEventUtils.uploadSearchButtonClick();
    }

    private void initViewPager() {
        this.menuTagAdapter = new HomeMenuTagAdapter(this.fragmentList, this.labelList, getChildFragmentManager());
        this.commonNavigator = new CommonNavigator(this.mContext);
        this.commonNavigatorAdapter = new C41171();
        this.commonNavigator.setAdapter(this.commonNavigatorAdapter);
        this.magicIndicator.setNavigator(this.commonNavigator);
        ViewPagerHelper.bind(this.magicIndicator, this.mViewPager);
        this.mViewPager.setAdapter(this.menuTagAdapter);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.fragment.LiveHomeFragment$1 */
    /* loaded from: classes3.dex */
    public class C41171 extends CommonNavigatorAdapter {
        C41171() {
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public int getCount() {
            if (LiveHomeFragment.this.fragmentList == null) {
                return 0;
            }
            return LiveHomeFragment.this.fragmentList.size();
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerTitleView getTitleView(Context context, final int i) {
            ScaleTransitionPagerTitleView scaleTransitionPagerTitleView = new ScaleTransitionPagerTitleView(context);
            scaleTransitionPagerTitleView.setText(((LabelEntity) LiveHomeFragment.this.labelList.get(i)).name);
            scaleTransitionPagerTitleView.setTextSize(20.0f);
            scaleTransitionPagerTitleView.setNormalColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_color));
            scaleTransitionPagerTitleView.setSelectedColor(ContextCompat.getColor(context, R$color.fq_tab_menu_text_select_color));
            scaleTransitionPagerTitleView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$LiveHomeFragment$1$XSUfYGxnPwl7eG3kqEovppwTl90
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    LiveHomeFragment.C41171.this.lambda$getTitleView$0$LiveHomeFragment$1(i, view);
                }
            });
            return scaleTransitionPagerTitleView;
        }

        public /* synthetic */ void lambda$getTitleView$0$LiveHomeFragment$1(int i, View view) {
            LiveHomeFragment.this.mViewPager.setCurrentItem(i, false);
        }

        @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
        public IPagerIndicator getIndicator(Context context) {
            return LiveHomeFragment.this.getIPagerIndicator(context);
        }
    }

    private void initTitleBarStyle(View view) {
        try {
            this.mImmersionBar = ImmersionBar.with(this.mActivity);
            ImmersionBar immersionBar = this.mImmersionBar;
            immersionBar.titleBar(view.findViewById(R$id.title_top_view));
            immersionBar.statusBarDarkFont(true);
            immersionBar.init();
        } catch (Exception unused) {
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        registerKickDialogReceiver();
        registerTokenDialogReceiver();
    }

    @Override // com.tomatolive.library.base.BaseFragment, com.trello.rxlifecycle2.components.support.RxFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        unRegisterKickDialogReceiver();
        unRegisterTokenDialogReceiver();
    }

    private void registerKickDialogReceiver() {
        this.localBroadcastManager = LocalBroadcastManager.getInstance(this.mContext);
        IntentFilter intentFilter = new IntentFilter(ConstantUtils.LIVE_KICK_OUT_ACTION);
        this.myKickOutBroadCastReceiver = new MyKickOutBroadCastReceiver(this, null);
        this.localBroadcastManager.registerReceiver(this.myKickOutBroadCastReceiver, intentFilter);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showKickOutDialog(String str) {
        LiveKickOutDialog.newInstance(str).show(getChildFragmentManager());
    }

    private void unRegisterKickDialogReceiver() {
        this.localBroadcastManager.unregisterReceiver(this.myKickOutBroadCastReceiver);
    }

    private void registerTokenDialogReceiver() {
        this.localBroadcastManager = LocalBroadcastManager.getInstance(this.mActivity);
        IntentFilter intentFilter = new IntentFilter(ConstantUtils.LIVE_TOKEN_INVALID_ACTION);
        this.myTokenInvalidBroadCastReceiver = new MyTokenInvalidBroadCastReceiver(this, null);
        this.localBroadcastManager.registerReceiver(this.myTokenInvalidBroadCastReceiver, intentFilter);
    }

    private void unRegisterTokenDialogReceiver() {
        MyTokenInvalidBroadCastReceiver myTokenInvalidBroadCastReceiver;
        LocalBroadcastManager localBroadcastManager = this.localBroadcastManager;
        if (localBroadcastManager == null || (myTokenInvalidBroadCastReceiver = this.myTokenInvalidBroadCastReceiver) == null) {
            return;
        }
        localBroadcastManager.unregisterReceiver(myTokenInvalidBroadCastReceiver);
    }

    private void initFragmentLabelList(List<LabelEntity> list) {
        List<BaseFragment> list2 = this.fragmentList;
        if (list2 != null) {
            list2.clear();
        }
        List<LabelEntity> list3 = this.labelList;
        if (list3 != null) {
            list3.clear();
        }
        if (SysConfigInfoManager.getInstance().isEnableFeeTag()) {
            addFragment(HomeSortFragment.newInstance(true), getString(R$string.fq_home_fee_tag));
        }
        addFragment(HomeAttentionFragment.newInstance(), getString(R$string.fq_home_attention));
        addFragment(HomeHotFragment.newInstance(), getString(R$string.fq_home_hot));
        addFragment(HomeAllFragment.newInstance(), getString(R$string.fq_home_all));
        if (list == null || list.size() <= 0) {
            return;
        }
        for (LabelEntity labelEntity : list) {
            addFragment(HomeSortFragment.newInstance(labelEntity.name), labelEntity.name);
        }
    }

    private void addFragment(BaseFragment baseFragment, String str) {
        if (this.fragmentList == null) {
            this.fragmentList = new ArrayList();
        }
        if (this.labelList == null) {
            this.labelList = new ArrayList();
        }
        if (baseFragment == null || baseFragment.isAdded()) {
            return;
        }
        this.fragmentList.add(baseFragment);
        this.labelList.add(new LabelEntity(str));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeView
    public void onTagListSuccess(List<LabelEntity> list) {
        try {
            initFragmentLabelList(list);
            SPUtils.getInstance().put(ConstantUtils.LIVE_LABEL_MENU, true);
            notifyDataSetChangedViewPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IHomeView
    public void onTagListFail() {
        try {
            initFragmentLabelList(null);
            SPUtils.getInstance().put(ConstantUtils.LIVE_LABEL_MENU, false);
            notifyDataSetChangedViewPager();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notifyDataSetChangedViewPager() {
        int i = (!SysConfigInfoManager.getInstance().isEnableFeeTag() || !TextUtils.equals(this.labelList.get(0).name, getString(R$string.fq_home_fee_tag))) ? 1 : 2;
        this.commonNavigatorAdapter.notifyDataSetChanged();
        this.menuTagAdapter.notifyDataSetChanged();
        this.mViewPager.setOffscreenPageLimit(this.fragmentList.size());
        this.mViewPager.setCurrentItem(i, false);
        EventBus.getDefault().post(new ListDataUpdateEvent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.fragment.LiveHomeFragment$MyTokenInvalidBroadCastReceiver */
    /* loaded from: classes3.dex */
    public class MyTokenInvalidBroadCastReceiver extends BroadcastReceiver {
        private MyTokenInvalidBroadCastReceiver() {
        }

        /* synthetic */ MyTokenInvalidBroadCastReceiver(LiveHomeFragment liveHomeFragment, C41171 c41171) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && TextUtils.equals(intent.getAction(), ConstantUtils.LIVE_TOKEN_INVALID_ACTION)) {
                LiveHomeFragment.this.showTokenInvalidDialog();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.fragment.LiveHomeFragment$MyKickOutBroadCastReceiver */
    /* loaded from: classes3.dex */
    public class MyKickOutBroadCastReceiver extends BroadcastReceiver {
        private MyKickOutBroadCastReceiver() {
        }

        /* synthetic */ MyKickOutBroadCastReceiver(LiveHomeFragment liveHomeFragment, C41171 c41171) {
            this();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            if (intent != null && TextUtils.equals(intent.getAction(), ConstantUtils.LIVE_KICK_OUT_ACTION)) {
                LiveHomeFragment.this.showKickOutDialog(intent.getStringExtra(ConstantUtils.RESULT_ITEM));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showTokenInvalidDialog() {
        TokenInvalidDialog.newInstance().show(getChildFragmentManager());
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThreadSticky(BaseEvent baseEvent) {
        super.onEventMainThreadSticky(baseEvent);
        if (baseEvent instanceof LabelMenuEvent) {
            ((HomePresenter) this.mPresenter).getTagList(this.mStateView, false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public IPagerIndicator getIPagerIndicator(Context context) {
        LinePagerIndicator linePagerIndicator = new LinePagerIndicator(context);
        linePagerIndicator.setColors(Integer.valueOf(ContextCompat.getColor(context, R$color.fq_tab_menu_text_select_color)));
        linePagerIndicator.setLineHeight(ConvertUtils.dp2px(2.0f));
        linePagerIndicator.setRoundRadius(UIUtil.dip2px(context, 3.0d));
        linePagerIndicator.setLineWidth(ConvertUtils.dp2px(18.0f));
        linePagerIndicator.setMode(2);
        return linePagerIndicator;
    }
}
