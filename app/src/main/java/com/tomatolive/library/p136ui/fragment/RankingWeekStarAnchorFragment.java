package com.tomatolive.library.p136ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.LiveEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.WeekStarAnchorEntity;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.model.event.ShowCurrentTopEvent;
import com.tomatolive.library.model.event.WeekStarAnchorEvent;
import com.tomatolive.library.p136ui.adapter.WeekStarRankingAnchorAdapter;
import com.tomatolive.library.p136ui.interfaces.OnUserCardCallback;
import com.tomatolive.library.p136ui.presenter.WeekStarRankingAnchorPresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IWeekStarRankingAnchorView;
import com.tomatolive.library.p136ui.view.widget.WrapContentHeightViewPager;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.fragment.RankingWeekStarAnchorFragment */
/* loaded from: classes3.dex */
public class RankingWeekStarAnchorFragment extends BaseFragment<WeekStarRankingAnchorPresenter> implements IWeekStarRankingAnchorView {
    private AnchorEntity anchorItemEntity;
    private WeekStarAnchorEntity currentWeekStarAnchorEntity;
    private ImageView ivRankingAvatar;
    private ImageView ivRankingLive;
    private LinearLayout llBottomBg;
    private LinearLayout llContentView;
    private WeekStarRankingAnchorAdapter mAdapter;
    private List<WeekStarAnchorEntity> mDataList;
    private RecyclerView mRecyclerView;
    private String markId;
    private TextView tvContributionCount;
    private TextView tvCurrentTop10;
    private TextView tvFail;
    private TextView tvLoading;
    private TextView tvRanking;
    private TextView tvRankingAnchor;
    private TextView tvRankingUser;
    private TextView tvReceiveCount;
    private WrapContentHeightViewPager viewPager = null;
    private boolean isUserRanking = false;
    private boolean isHomeStarRanking = true;
    private String noRankingNumTips = "--";
    private OnUserCardCallback onUserCardCallback = null;

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isLazyLoad() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWeekStarRankingAnchorView
    public void onDataListFail(boolean z) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static RankingWeekStarAnchorFragment newInstance(String str, boolean z, int i, WrapContentHeightViewPager wrapContentHeightViewPager) {
        Bundle bundle = new Bundle();
        RankingWeekStarAnchorFragment rankingWeekStarAnchorFragment = new RankingWeekStarAnchorFragment();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        bundle.putInt(ConstantUtils.RESULT_COUNT, i);
        rankingWeekStarAnchorFragment.setArguments(bundle);
        rankingWeekStarAnchorFragment.setViewPager(wrapContentHeightViewPager);
        return rankingWeekStarAnchorFragment;
    }

    public static RankingWeekStarAnchorFragment newInstance(AnchorEntity anchorEntity, String str, boolean z, OnUserCardCallback onUserCardCallback) {
        Bundle bundle = new Bundle();
        RankingWeekStarAnchorFragment rankingWeekStarAnchorFragment = new RankingWeekStarAnchorFragment();
        bundle.putString(ConstantUtils.RESULT_ID, str);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        rankingWeekStarAnchorFragment.setArguments(bundle);
        rankingWeekStarAnchorFragment.setOnUserCardCallback(onUserCardCallback);
        return rankingWeekStarAnchorFragment;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public WeekStarRankingAnchorPresenter mo6641createPresenter() {
        return new WeekStarRankingAnchorPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.markId = bundle.getString(ConstantUtils.RESULT_ID);
        this.anchorItemEntity = (AnchorEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
        boolean z = false;
        this.isUserRanking = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
        if (this.onUserCardCallback == null) {
            z = true;
        }
        this.isHomeStarRanking = z;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_ranking_week_star_anchor;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        WrapContentHeightViewPager wrapContentHeightViewPager;
        if (this.isHomeStarRanking && (wrapContentHeightViewPager = this.viewPager) != null) {
            wrapContentHeightViewPager.setViewForPosition(view, getArguments().getInt(ConstantUtils.RESULT_COUNT, 0));
        }
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.llBottomBg = (LinearLayout) view.findViewById(R$id.ll_bottom_bg);
        this.tvRanking = (TextView) view.findViewById(R$id.tv_index);
        this.tvRankingAnchor = (TextView) view.findViewById(R$id.tv_ranking_anchor);
        this.tvRankingUser = (TextView) view.findViewById(R$id.tv_ranking_user);
        this.tvReceiveCount = (TextView) view.findViewById(R$id.tv_receive_count);
        this.tvContributionCount = (TextView) view.findViewById(R$id.tv_contribution_count);
        this.tvCurrentTop10 = (TextView) view.findViewById(R$id.tv_current_top_10);
        this.ivRankingAvatar = (ImageView) view.findViewById(R$id.iv_ranking_avatar);
        this.ivRankingLive = (ImageView) view.findViewById(R$id.iv_ranking_live);
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading);
        this.tvFail = (TextView) view.findViewById(R$id.tv_fail);
        this.llContentView = (LinearLayout) view.findViewById(R$id.ll_content_view);
        initAdapter();
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onLazyLoad() {
        if (TextUtils.isEmpty(this.markId)) {
            ((WeekStarRankingAnchorPresenter) this.mPresenter).getDefaultHomeStarRanking(this.llContentView, this.tvLoading, this.tvFail, true, true);
            return;
        }
        sendDataRequest(true, true);
        if (this.isHomeStarRanking) {
            return;
        }
        AnchorEntity anchorEntity = this.anchorItemEntity;
        ((WeekStarRankingAnchorPresenter) this.mPresenter).getUserRanking(this.isUserRanking, this.markId, anchorEntity == null ? "" : anchorEntity.userId);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.RankingWeekStarAnchorFragment.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                WeekStarAnchorEntity weekStarAnchorEntity = (WeekStarAnchorEntity) baseQuickAdapter.getItem(i);
                if (weekStarAnchorEntity == null) {
                    return;
                }
                if (!RankingWeekStarAnchorFragment.this.isHomeStarRanking) {
                    RankingWeekStarAnchorFragment.this.onUserCardCallbackClick(weekStarAnchorEntity);
                    return;
                }
                LiveEntity formatLiveEntity = AppUtils.formatLiveEntity(weekStarAnchorEntity);
                if (formatLiveEntity == null || TextUtils.isEmpty(formatLiveEntity.liveId)) {
                    return;
                }
                AppUtils.startTomatoLiveActivity(((BaseFragment) RankingWeekStarAnchorFragment.this).mContext, formatLiveEntity, "2", RankingWeekStarAnchorFragment.this.getString(R$string.fq_live_enter_source_week_star_ranking));
            }
        });
        this.tvCurrentTop10.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingWeekStarAnchorFragment$PLBmN5EQFqtjML_EO7T_spmWCpA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RankingWeekStarAnchorFragment.this.lambda$initListener$0$RankingWeekStarAnchorFragment(view2);
            }
        });
        this.tvFail.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingWeekStarAnchorFragment$EqBmagypRYfjpuU4H8HHSWxwhRY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RankingWeekStarAnchorFragment.this.lambda$initListener$1$RankingWeekStarAnchorFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$RankingWeekStarAnchorFragment(View view) {
        this.tvCurrentTop10.setVisibility(8);
        SysConfigInfoManager.getInstance().setEnableShowCurrentTop10(true);
        EventBus.getDefault().post(new ShowCurrentTopEvent());
    }

    public /* synthetic */ void lambda$initListener$1$RankingWeekStarAnchorFragment(View view) {
        sendDataRequest(true, true);
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new WeekStarRankingAnchorAdapter(R$layout.fq_item_list_week_star_ranking, this.isUserRanking);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 42));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWeekStarRankingAnchorView
    public void onDataListSuccess(List<WeekStarAnchorEntity> list, boolean z) {
        if (list == null) {
            return;
        }
        int i = 0;
        if (this.onUserCardCallback != null) {
            this.mAdapter.setNewData(list);
        } else if (SysConfigInfoManager.getInstance().isEnableShowCurrentTop10()) {
            this.tvCurrentTop10.setVisibility(8);
            this.mAdapter.setNewData(list);
        } else if (list.size() > 5) {
            this.mDataList = list;
            this.tvCurrentTop10.setVisibility(0);
            this.mAdapter.setNewData(list.subList(0, 5));
        } else {
            this.tvCurrentTop10.setVisibility(8);
            this.mAdapter.setNewData(list);
        }
        LinearLayout linearLayout = this.llBottomBg;
        if (this.isHomeStarRanking || TextUtils.isEmpty(this.markId) || list.isEmpty()) {
            i = 8;
        }
        linearLayout.setVisibility(i);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWeekStarRankingAnchorView
    public void onUserRankingSuccess(WeekStarAnchorEntity weekStarAnchorEntity) {
        initSingleRanking(weekStarAnchorEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IWeekStarRankingAnchorView
    public void onUserRankingFail() {
        initSingleRanking(null);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof ShowCurrentTopEvent) {
            if (this.mDataList == null) {
                return;
            }
            this.tvCurrentTop10.setVisibility(8);
            this.mAdapter.setNewData(this.mDataList);
        } else if (!(baseEvent instanceof WeekStarAnchorEvent) || TextUtils.isEmpty(this.markId) || !TextUtils.equals(this.markId, ((WeekStarAnchorEvent) baseEvent).markId)) {
        } else {
            sendDataRequest(true, false);
        }
    }

    private void sendDataRequest(boolean z, boolean z2) {
        ((WeekStarRankingAnchorPresenter) this.mPresenter).getDataList(this.isHomeStarRanking, this.isUserRanking, this.markId, this.llContentView, this.tvLoading, this.tvFail, z, z2);
    }

    public void setOnUserCardCallback(OnUserCardCallback onUserCardCallback) {
        this.onUserCardCallback = onUserCardCallback;
    }

    public WrapContentHeightViewPager getViewPager() {
        return this.viewPager;
    }

    public void setViewPager(WrapContentHeightViewPager wrapContentHeightViewPager) {
        this.viewPager = wrapContentHeightViewPager;
    }

    private UserEntity formatUserEntity(WeekStarAnchorEntity weekStarAnchorEntity) {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(weekStarAnchorEntity.userId);
        userEntity.setAvatar(weekStarAnchorEntity.avatar);
        userEntity.setName(weekStarAnchorEntity.name);
        userEntity.setSex(weekStarAnchorEntity.sex);
        userEntity.setExpGrade(weekStarAnchorEntity.expGrade);
        userEntity.setGuardType(NumberUtils.string2int(weekStarAnchorEntity.guardType));
        userEntity.setRole(weekStarAnchorEntity.role);
        userEntity.setNobilityType(weekStarAnchorEntity.nobilityType);
        userEntity.setLiveId(weekStarAnchorEntity.liveId);
        userEntity.setOpenId(weekStarAnchorEntity.openId);
        userEntity.setAppId(weekStarAnchorEntity.appId);
        userEntity.setLiveStatus(weekStarAnchorEntity.isLiving);
        userEntity.isRankHide = weekStarAnchorEntity.isRankHide;
        return userEntity;
    }

    private AnchorEntity formatAnchorEntity(WeekStarAnchorEntity weekStarAnchorEntity) {
        AnchorEntity anchorEntity = new AnchorEntity();
        anchorEntity.liveId = weekStarAnchorEntity.liveId;
        anchorEntity.userId = weekStarAnchorEntity.anchorId;
        anchorEntity.avatar = weekStarAnchorEntity.avatar;
        anchorEntity.sex = weekStarAnchorEntity.sex;
        anchorEntity.nickname = weekStarAnchorEntity.anchorName;
        anchorEntity.expGrade = weekStarAnchorEntity.expGrade;
        return anchorEntity;
    }

    private void initSingleRanking(WeekStarAnchorEntity weekStarAnchorEntity) {
        int i = 8;
        if (this.isUserRanking && AppUtils.isVisitor()) {
            this.tvRanking.setText(this.noRankingNumTips);
            this.tvRankingAnchor.setText(R$string.fq_not_login_user);
            this.tvReceiveCount.setText(R$string.fq_not_ranking);
            this.tvRankingUser.setVisibility(8);
            this.tvContributionCount.setVisibility(8);
            this.ivRankingLive.setVisibility(8);
            GlideUtils.loadAvatar(this.mContext, this.ivRankingAvatar, R$drawable.fq_ic_placeholder_avatar_white);
        } else if (weekStarAnchorEntity == null) {
            if (this.isUserRanking) {
                showDefaultUserRanking();
            } else {
                showDefaultAnchorRanking();
            }
        } else {
            this.currentWeekStarAnchorEntity = weekStarAnchorEntity;
            String formatNum = formatNum(weekStarAnchorEntity.rank);
            boolean equals = TextUtils.equals(formatNum, this.noRankingNumTips);
            this.tvRanking.setText(formatNum);
            if (equals) {
                if (this.isUserRanking) {
                    showDefaultUserRanking();
                    return;
                } else {
                    showDefaultAnchorRanking();
                    return;
                }
            }
            this.tvRankingAnchor.setText(AppUtils.formatUserNickName(this.isUserRanking ? weekStarAnchorEntity.name : weekStarAnchorEntity.anchorName));
            this.tvRankingUser.setText(AppUtils.formatUserNickName(weekStarAnchorEntity.name));
            this.tvReceiveCount.setText(equals ? getString(R$string.fq_not_ranking) : getReceiveStr(weekStarAnchorEntity));
            int i2 = 0;
            this.tvContributionCount.setText(this.mContext.getString(R$string.fq_week_star_assists, weekStarAnchorEntity.userStarGiftNum));
            this.tvRankingUser.setVisibility((this.isUserRanking || equals) ? 8 : 0);
            TextView textView = this.tvContributionCount;
            if (!this.isUserRanking && !equals) {
                i = 0;
            }
            textView.setVisibility(i);
            ImageView imageView = this.ivRankingLive;
            if (!AppUtils.isLiving(weekStarAnchorEntity.liveStatus)) {
                i2 = 4;
            }
            imageView.setVisibility(i2);
            GlideUtils.loadAvatar(this.mContext, this.ivRankingAvatar, weekStarAnchorEntity.avatar);
            GlideUtils.loadLivingGif(this.mContext, this.ivRankingLive);
        }
    }

    private String getReceiveStr(WeekStarAnchorEntity weekStarAnchorEntity) {
        return this.isUserRanking ? this.mContext.getString(R$string.fq_week_star_send, weekStarAnchorEntity.giftNum) : this.mContext.getString(R$string.fq_week_star_receive, weekStarAnchorEntity.anchorStarGiftNum);
    }

    private String formatNum(int i) {
        if (i <= 0) {
            return this.noRankingNumTips;
        }
        return i > 99 ? "99+" : String.valueOf(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void onUserCardCallbackClick(WeekStarAnchorEntity weekStarAnchorEntity) {
        if (this.onUserCardCallback == null) {
            return;
        }
        if (this.isUserRanking) {
            if (AppUtils.isLiving(weekStarAnchorEntity.liveStatus)) {
                AnchorEntity formatAnchorEntity = formatAnchorEntity(weekStarAnchorEntity);
                if (formatAnchorEntity == null || TextUtils.isEmpty(formatAnchorEntity.liveId)) {
                    return;
                }
                this.onUserCardCallback.onAnchorItemClickListener(formatAnchorEntity);
                return;
            }
            if (TextUtils.equals(weekStarAnchorEntity.role, "1")) {
                weekStarAnchorEntity.role = "2";
            }
            this.onUserCardCallback.onUserItemClickListener(formatUserEntity(weekStarAnchorEntity));
            return;
        }
        AnchorEntity formatAnchorEntity2 = formatAnchorEntity(weekStarAnchorEntity);
        if (formatAnchorEntity2 == null || TextUtils.isEmpty(formatAnchorEntity2.liveId)) {
            return;
        }
        this.onUserCardCallback.onAnchorItemClickListener(formatAnchorEntity2);
    }

    private void showDefaultUserRanking() {
        this.tvRanking.setText(formatNum(-1));
        this.tvRankingAnchor.setText(AppUtils.formatUserNickName(UserInfoManager.getInstance().getUserNickname()));
        this.tvReceiveCount.setText(R$string.fq_not_ranking);
        this.tvRankingUser.setVisibility(8);
        this.tvContributionCount.setVisibility(8);
        this.ivRankingLive.setVisibility(8);
        GlideUtils.loadAvatar(this.mContext, this.ivRankingAvatar, UserInfoManager.getInstance().getAvatar());
    }

    private void showDefaultAnchorRanking() {
        if (this.anchorItemEntity == null) {
            return;
        }
        this.tvRanking.setText(formatNum(-1));
        this.tvRankingAnchor.setText(AppUtils.formatUserNickName(this.anchorItemEntity.nickname));
        this.tvReceiveCount.setText(R$string.fq_not_ranking);
        this.tvRankingUser.setVisibility(8);
        this.tvContributionCount.setVisibility(8);
        this.ivRankingLive.setVisibility(8);
        GlideUtils.loadAvatar(this.mContext, this.ivRankingAvatar, this.anchorItemEntity.avatar);
    }
}
