package com.tomatolive.library.p136ui.activity.live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.blankj.utilcode.util.SPUtils;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.http.ResultCallBack;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.p136ui.activity.live.TomatoLiveFragment;
import com.tomatolive.library.p136ui.view.widget.CustomVerticalViewPager;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.live.LiveManagerUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveActivity */
/* loaded from: classes3.dex */
public class TomatoLiveActivity extends BaseActivity<BasePresenter> implements TomatoLiveFragment.OnFragmentInteractionListener {
    private String liveEnterWay;
    private LiveEntity liveInfoItem;
    private FrameLayout mFragmentContainer;
    private FragmentManager mFragmentManager;
    private ArrayList<LiveEntity> mLiveList;
    private PagerAdapter mPagerAdapter;
    private RelativeLayout mRoomContainer;
    private CustomVerticalViewPager mViewPager;
    private int mViewPagerCurrentPosition;
    private TomatoLiveFragment tomatoLiveFragment;
    private boolean mIsFirstLoading = false;
    private int mViewPagerLastPosition = -1;
    private int mLiveListPosition = -1;

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public BasePresenter mo6636createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_tomato_live;
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.trello.rxlifecycle2.components.support.RxAppCompatActivity, android.support.p005v7.app.AppCompatActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    public void onCreate(@Nullable Bundle bundle) {
        this.mLiveList = LiveManagerUtils.getInstance().getLiveList();
        this.liveInfoItem = LiveManagerUtils.getInstance().getCurrentLiveItem();
        this.mLiveListPosition = LiveManagerUtils.getInstance().getCurrentLivePosition();
        this.liveEnterWay = getIntent().getStringExtra(ConstantUtils.RESULT_FLAG);
        super.onCreate(bundle);
    }

    @Override // android.app.Activity
    protected void onRestoreInstanceState(Bundle bundle) {
        super.onRestoreInstanceState(bundle);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setTitle(getString(R$string.fq_title_user_watch_live));
        this.mRoomContainer = (RelativeLayout) LayoutInflater.from(this).inflate(R$layout.fq_view_room_container, (ViewGroup) null);
        this.mFragmentContainer = (FrameLayout) this.mRoomContainer.findViewById(R$id.fragment_container);
        this.mViewPager = (CustomVerticalViewPager) findViewById(R$id.view_pager);
        this.tomatoLiveFragment = TomatoLiveFragment.newInstance(this.liveInfoItem, this.liveEnterWay);
        this.mFragmentManager = getSupportFragmentManager();
        this.mPagerAdapter = new PagerAdapter();
        this.mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveActivity.1
            @Override // android.support.p002v4.view.ViewPager.SimpleOnPageChangeListener, android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
                TomatoLiveActivity.this.mViewPagerCurrentPosition = i;
            }
        });
        this.mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() { // from class: com.tomatolive.library.ui.activity.live.-$$Lambda$TomatoLiveActivity$-YWpTeH_t-cxQ1DYD0V0e4hSysw
            @Override // android.support.p002v4.view.ViewPager.PageTransformer
            public final void transformPage(View view, float f) {
                TomatoLiveActivity.this.lambda$initView$0$TomatoLiveActivity(view, f);
            }
        });
        this.mViewPager.setAdapter(this.mPagerAdapter);
        if (this.mLiveList.size() > 1) {
            this.mViewPager.setCurrentItem((this.mLiveList.size() * 1000) + this.mLiveListPosition);
        }
    }

    public /* synthetic */ void lambda$initView$0$TomatoLiveActivity(View view, float f) {
        View findViewById;
        ViewGroup viewGroup = (ViewGroup) view;
        if (f < 0.0f && viewGroup.getId() != this.mViewPagerCurrentPosition && (findViewById = viewGroup.findViewById(R$id.room_container)) != null && findViewById.getParent() != null && (findViewById.getParent() instanceof ViewGroup)) {
            TomatoLiveFragment tomatoLiveFragment = this.tomatoLiveFragment;
            if (tomatoLiveFragment != null && tomatoLiveFragment.isAdded()) {
                this.tomatoLiveFragment.onFragmentPageChangeListener();
            }
            ((ViewGroup) findViewById.getParent()).removeView(findViewById);
        }
        int id = viewGroup.getId();
        int i = this.mViewPagerCurrentPosition;
        if (id == i && f == 0.0f && i != this.mViewPagerLastPosition) {
            if (this.mRoomContainer.getParent() != null && (this.mRoomContainer.getParent() instanceof ViewGroup)) {
                ((ViewGroup) this.mRoomContainer.getParent()).removeView(this.mRoomContainer);
            }
            loadVideoAndChatRoom(viewGroup, this.mViewPagerCurrentPosition);
        }
    }

    private void loadVideoAndChatRoom(ViewGroup viewGroup, int i) {
        if (!this.mIsFirstLoading) {
            this.mFragmentManager.beginTransaction().add(this.mFragmentContainer.getId(), this.tomatoLiveFragment).commitAllowingStateLoss();
            this.mIsFirstLoading = true;
        } else if (this.mLiveList.size() > 0) {
            this.tomatoLiveFragment.resetLiveRoom(getCurrentItem(i), "1");
        }
        viewGroup.addView(this.mRoomContainer);
        this.mViewPagerLastPosition = i;
    }

    @Override // com.tomatolive.library.base.BaseActivity, com.tomatolive.library.service.NetworkChangeReceiver.NetChangeListener
    public void onNetChangeListener(int i) {
        try {
            if (i != -1) {
                if (i == 0) {
                    showMobileNetDialog();
                }
            } else if (this.tomatoLiveFragment != null && this.tomatoLiveFragment.isAdded()) {
                this.tomatoLiveFragment.onNetNone();
            }
        } catch (Exception unused) {
        }
    }

    private void showMobileNetDialog() {
        if (SPUtils.getInstance().getBoolean(ConstantUtils.SHOW_MOBIE_TIP, false)) {
            if (BaseActivity.hasRemindTraffic) {
                return;
            }
            BaseActivity.hasRemindTraffic = true;
            showToast(R$string.fq_mobile_tip);
            return;
        }
        TomatoLiveFragment tomatoLiveFragment = this.tomatoLiveFragment;
        if (tomatoLiveFragment == null || !tomatoLiveFragment.isAdded()) {
            return;
        }
        this.tomatoLiveFragment.on4G();
    }

    private int formatLiveListPosition(int i) {
        if (i < 0 || i >= this.mLiveList.size()) {
            return 0;
        }
        return i;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public LiveEntity getCurrentItem(int i) {
        ArrayList<LiveEntity> arrayList = this.mLiveList;
        return arrayList.get(formatLiveListPosition(i % arrayList.size()));
    }

    @Override // com.tomatolive.library.p136ui.activity.live.TomatoLiveFragment.OnFragmentInteractionListener
    public void setViewPagerScroll(boolean z) {
        this.mViewPager.setScroll(z);
    }

    @Override // com.tomatolive.library.p136ui.activity.live.TomatoLiveFragment.OnFragmentInteractionListener
    public void updateLiveRoomInfo() {
        TomatoLiveSDK.getSingleton().onAllLiveListUpdate(bindToLifecycle(), new ResultCallBack<List<LiveEntity>>() { // from class: com.tomatolive.library.ui.activity.live.TomatoLiveActivity.2
            @Override // com.tomatolive.library.http.ResultCallBack
            public void onError(int i, String str) {
            }

            @Override // com.tomatolive.library.http.ResultCallBack
            public void onSuccess(List<LiveEntity> list) {
                TomatoLiveActivity.this.mLiveList = new ArrayList(list);
                TomatoLiveActivity.this.mPagerAdapter.notifyDataSetChanged();
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.activity.live.TomatoLiveActivity$PagerAdapter */
    /* loaded from: classes3.dex */
    public class PagerAdapter extends android.support.p002v4.view.PagerAdapter {
        @Override // android.support.p002v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        PagerAdapter() {
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public int getCount() {
            return (TomatoLiveActivity.this.mLiveList.isEmpty() || TomatoLiveActivity.this.mLiveList.size() == 1) ? 1 : Integer.MAX_VALUE;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        /* renamed from: instantiateItem */
        public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
            View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.fq_view_room_item, (ViewGroup) null);
            inflate.setId(i);
            ImageView imageView = (ImageView) inflate.findViewById(R$id.anchor_img);
            if (TomatoLiveActivity.this.mLiveList.size() > 0) {
                LiveEntity currentItem = TomatoLiveActivity.this.getCurrentItem(i);
                if (currentItem != null) {
                    GlideUtils.loadImageBlur(((BaseActivity) TomatoLiveActivity.this).mContext, imageView, TextUtils.isEmpty(currentItem.liveCoverUrl) ? currentItem.avatar : currentItem.liveCoverUrl, R$drawable.fq_shape_default_cover_bg);
                } else {
                    GlideUtils.loadImageBlur(((BaseActivity) TomatoLiveActivity.this).mContext, imageView, null, R$drawable.fq_shape_default_cover_bg);
                }
            }
            viewGroup.addView(inflate);
            return inflate;
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView(viewGroup.findViewById(i));
        }
    }
}
