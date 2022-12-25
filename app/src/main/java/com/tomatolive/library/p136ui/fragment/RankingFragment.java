package com.tomatolive.library.p136ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.p005v7.widget.DefaultItemAnimator;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseFragment;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.model.event.AttentionEvent;
import com.tomatolive.library.p136ui.adapter.RankingAdapter;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback;
import com.tomatolive.library.p136ui.presenter.RankingFragmentPresenter;
import com.tomatolive.library.p136ui.view.dialog.GiftWallDialog;
import com.tomatolive.library.p136ui.view.dialog.NobilityOpenTipsDialog;
import com.tomatolive.library.p136ui.view.dialog.RankingAllDialog;
import com.tomatolive.library.p136ui.view.dialog.UserAchieveDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNobilityAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.UserNormalAvatarDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.RankingHeadView;
import com.tomatolive.library.p136ui.view.iview.IRankingFragmentView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DBUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.UserInfoManager;
import java.util.ArrayList;
import java.util.List;
import org.greenrobot.eventbus.EventBus;

/* renamed from: com.tomatolive.library.ui.fragment.RankingFragment */
/* loaded from: classes3.dex */
public class RankingFragment extends BaseFragment<RankingFragmentPresenter> implements IRankingFragmentView {
    private Bundle bundleArgs;
    private GiftWallDialog giftWallDialog;
    private boolean isShowAllRanking;
    private RankingAdapter mAdapter;
    private RankingHeadView mHeadView;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private String rankingName;
    private int rankingType;
    private RelativeLayout rlBottomTopBg;
    private TextView tvBottomAllTop;
    private UserAchieveDialog userAchieveDialog;
    private UserNormalAvatarDialog userAvatarDialog;
    private UserNobilityAvatarDialog userNobilityAvatarDialog;

    @Override // com.tomatolive.library.base.BaseFragment
    public boolean isLazyLoad() {
        return true;
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingFragmentView
    public void onDataListFail(boolean z) {
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    public static RankingFragment newInstance(int i, String str, boolean z) {
        Bundle bundle = new Bundle();
        RankingFragment rankingFragment = new RankingFragment();
        bundle.putInt(ConstantUtils.RESULT_ID, i);
        bundle.putString(ConstantUtils.RESULT_ITEM, str);
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        rankingFragment.setArguments(bundle);
        return rankingFragment;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.rankingType = bundle.getInt(ConstantUtils.RESULT_ID, 4);
        this.rankingName = bundle.getString(ConstantUtils.RESULT_ITEM);
        this.isShowAllRanking = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseFragment
    /* renamed from: createPresenter  reason: avoid collision after fix types in other method */
    public RankingFragmentPresenter mo6641createPresenter() {
        return new RankingFragmentPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public int getLayoutId() {
        return R$layout.fq_fragment_ranking;
    }

    @Override // com.tomatolive.library.base.BaseFragment
    protected View injectStateView(View view) {
        return view.findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initView(View view, @Nullable Bundle bundle) {
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.refreshLayout);
        this.rlBottomTopBg = (RelativeLayout) view.findViewById(R$id.rl_bottom_top_bg);
        this.tvBottomAllTop = (TextView) view.findViewById(R$id.tv_bottom_top);
        this.tvBottomAllTop.getPaint().setFlags(8);
        this.tvBottomAllTop.getPaint().setAntiAlias(true);
        initAdapter();
        this.rlBottomTopBg.setVisibility(this.isShowAllRanking ? 0 : 4);
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void onLazyLoad() {
        sendRequest(true);
    }

    private void sendRequest(boolean z) {
        if (this.rankingType == 4) {
            ((RankingFragmentPresenter) this.mPresenter).getCharmTopList(this.mStateView, this.rankingName, z, true);
        } else {
            ((RankingFragmentPresenter) this.mPresenter).getStrengthTopList(this.mStateView, this.rankingName, z, true);
        }
    }

    @Override // com.tomatolive.library.base.BaseFragment
    public void initListener(View view) {
        super.initListener(view);
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingFragment$DRlbXC_bRuCF77azX7w0YncpZ4c
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                RankingFragment.this.lambda$initListener$0$RankingFragment();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshListener(new OnRefreshListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingFragment$lkIENRwUrfoeYZiHEQvDfAi2q0k
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public final void onRefresh(RefreshLayout refreshLayout) {
                RankingFragment.this.lambda$initListener$1$RankingFragment(refreshLayout);
            }
        });
        this.mAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingFragment$VmFw6FcKiBB_rYkNnYryyupkzi0
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                RankingFragment.this.lambda$initListener$2$RankingFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingFragment$Jfqj5Zmo81NSjPeeOwiaxZLycQM
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                RankingFragment.this.lambda$initListener$3$RankingFragment(baseQuickAdapter, view2, i);
            }
        });
        this.mHeadView.setOnUserCardCallback(new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.fragment.RankingFragment.1
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAnchorItemClickListener(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                if (RankingFragment.this.rankingType == 5) {
                    RankingFragment.this.showUserCard(anchorEntity);
                } else {
                    RankingFragment.this.toLiveActivity(anchorEntity);
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAttentionAnchorListener(View view2, AnchorEntity anchorEntity) {
                if (AppUtils.isAttentionUser(((BaseFragment) RankingFragment.this).mContext, anchorEntity.userId) && RankingFragment.this.rankingType != 5) {
                    RankingFragment.this.attentionAnchor(anchorEntity, view2);
                }
            }
        });
        this.tvBottomAllTop.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingFragment$LCfsvhWcRnMxCCnuLOG4GUo9G0w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                RankingFragment.this.lambda$initListener$4$RankingFragment(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$RankingFragment() {
        sendRequest(true);
    }

    public /* synthetic */ void lambda$initListener$1$RankingFragment(RefreshLayout refreshLayout) {
        sendRequest(false);
        this.mSmartRefreshLayout.mo6481finishRefresh();
    }

    public /* synthetic */ void lambda$initListener$2$RankingFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity;
        if (view.getId() != R$id.tv_attention || (anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i)) == null || !AppUtils.isAttentionUser(this.mContext, anchorEntity.userId)) {
            return;
        }
        attentionAnchor(anchorEntity, view);
    }

    public /* synthetic */ void lambda$initListener$3$RankingFragment(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        if (this.rankingType == 5) {
            showUserCard(anchorEntity);
        } else if (anchorEntity == null || TextUtils.isEmpty(anchorEntity.nickname)) {
        } else {
            toLiveActivity(anchorEntity);
        }
    }

    public /* synthetic */ void lambda$initListener$4$RankingFragment(View view) {
        RankingAllDialog.newInstance(this.rankingType, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.fragment.RankingFragment.2
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAnchorItemClickListener(AnchorEntity anchorEntity) {
                if (anchorEntity == null) {
                    return;
                }
                if (RankingFragment.this.rankingType == 5) {
                    RankingFragment.this.showUserCard(anchorEntity);
                } else {
                    RankingFragment.this.toLiveActivity(anchorEntity);
                }
            }

            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
            public void onAttentionAnchorListener(View view2, AnchorEntity anchorEntity) {
                if (AppUtils.isAttentionUser(((BaseFragment) RankingFragment.this).mContext, anchorEntity.userId) && RankingFragment.this.rankingType != 5) {
                    RankingFragment.this.attentionAnchor(anchorEntity, view2);
                }
            }
        }).show(getChildFragmentManager());
    }

    private void initAdapter() {
        ((DefaultItemAnimator) this.mRecyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        this.mHeadView = new RankingHeadView(this.mContext);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new RankingAdapter(R$layout.fq_item_list_live_top_new, this.rankingType);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.addHeaderView(this.mHeadView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 42));
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingFragmentView
    public void onDataListSuccess(List<AnchorEntity> list, boolean z) {
        if (list == null) {
            return;
        }
        this.mHeadView.initData(list, this.rankingType);
        if (list.size() == 1) {
            this.mAdapter.setNewData(getEmptyAnchorList());
        } else if (list.size() < 1) {
        } else {
            this.mAdapter.setNewData(list.subList(1, list.size()));
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IRankingFragmentView
    public void onAttentionSuccess() {
        EventBus.getDefault().postSticky(new AttentionEvent());
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void attentionAnchor(AnchorEntity anchorEntity, View view) {
        boolean z = !AppUtils.isAttentionAnchor(anchorEntity.userId) ? 1 : 0;
        showToast(z ? R$string.fq_text_attention_success : R$string.fq_text_attention_cancel_success);
        view.setSelected(z);
        DBUtils.attentionAnchor(anchorEntity.userId, z);
        ((RankingFragmentPresenter) this.mPresenter).attentionAnchor(anchorEntity.userId, z ? 1 : 0);
        LogEventUtils.uploadFollow(anchorEntity.openId, anchorEntity.appId, getString(R$string.fq_home_hot), anchorEntity.expGrade, anchorEntity.nickname, getString(R$string.fq_live_enter_source_ranking), z, anchorEntity.liveId);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserCard(AnchorEntity anchorEntity) {
        if (anchorEntity.isRankHideBoolean() && !TextUtils.equals(anchorEntity.userId, UserInfoManager.getInstance().getUserId())) {
            NobilityOpenTipsDialog.newInstance(13, new View.OnClickListener() { // from class: com.tomatolive.library.ui.fragment.-$$Lambda$RankingFragment$4rO7nqJ2RGPlbPRNPIBatOaQiUI
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    RankingFragment.this.lambda$showUserCard$5$RankingFragment(view);
                }
            }).show(getChildFragmentManager());
        } else if (AppUtils.isNobilityUser(anchorEntity.nobilityType)) {
            if (this.userNobilityAvatarDialog == null) {
                this.userNobilityAvatarDialog = UserNobilityAvatarDialog.newInstance(anchorEntity, 2, false, false, false, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.fragment.RankingFragment.3
                    @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                    public void onClickNobilityListener(View view) {
                        AppUtils.toNobilityOpenActivity(((BaseFragment) RankingFragment.this).mContext, null);
                    }

                    @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                    public void onUserAchieveListener(UserEntity userEntity, String str) {
                        super.onUserAchieveListener(userEntity, str);
                        UserAchieveDialog.newInstance(userEntity, str).show(RankingFragment.this.getFragmentManager());
                    }

                    @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                    public void onGiftWallClickListener(AnchorEntity anchorEntity2) {
                        super.onGiftWallClickListener(anchorEntity2);
                        RankingFragment.this.showGiftWallDialog(anchorEntity2);
                    }
                });
                this.userNobilityAvatarDialog.show(getChildFragmentManager());
                return;
            }
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
            this.bundleArgs.putInt("liveType", 2);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
            this.userNobilityAvatarDialog.setArguments(this.bundleArgs);
            showDialogFragment(this.userNobilityAvatarDialog);
        } else if (this.userAvatarDialog == null) {
            this.userAvatarDialog = UserNormalAvatarDialog.newInstance(anchorEntity, 2, false, false, false, new SimpleUserCardCallback() { // from class: com.tomatolive.library.ui.fragment.RankingFragment.4
                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onUserAchieveListener(UserEntity userEntity, String str) {
                    super.onUserAchieveListener(userEntity, str);
                    RankingFragment.this.showUserAchieveDialog(userEntity, str);
                }

                @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleUserCardCallback, com.tomatolive.library.p136ui.interfaces.OnUserCardCallback
                public void onGiftWallClickListener(AnchorEntity anchorEntity2) {
                    super.onGiftWallClickListener(anchorEntity2);
                    RankingFragment.this.showGiftWallDialog(anchorEntity2);
                }
            });
            this.userAvatarDialog.show(getChildFragmentManager());
        } else {
            this.bundleArgs = new Bundle();
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_MANAGER, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_IMPRESSION, false);
            this.bundleArgs.putBoolean(ConstantUtils.BUNDLE_VALUE_REPORT, false);
            this.bundleArgs.putInt("liveType", 2);
            this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
            this.userAvatarDialog.setArguments(this.bundleArgs);
            showDialogFragment(this.userAvatarDialog);
        }
    }

    public /* synthetic */ void lambda$showUserCard$5$RankingFragment(View view) {
        AppUtils.toNobilityOpenActivity(this.mContext, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void toLiveActivity(AnchorEntity anchorEntity) {
        AppUtils.startTomatoLiveActivity(this.mContext, AppUtils.formatLiveEntity(anchorEntity), "2", getString(R$string.fq_live_enter_source_ranking));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showGiftWallDialog(AnchorEntity anchorEntity) {
        if (this.giftWallDialog == null) {
            this.giftWallDialog = GiftWallDialog.newInstance(anchorEntity, true);
            this.giftWallDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, anchorEntity);
        this.bundleArgs.putBoolean(ConstantUtils.RESULT_FLAG, true);
        this.giftWallDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.giftWallDialog);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showUserAchieveDialog(UserEntity userEntity, String str) {
        if (this.userAchieveDialog == null) {
            this.userAchieveDialog = UserAchieveDialog.newInstance(userEntity, str, true);
            this.userAchieveDialog.show(getChildFragmentManager());
            return;
        }
        this.bundleArgs = new Bundle();
        this.bundleArgs.putParcelable(ConstantUtils.RESULT_ITEM, userEntity);
        this.bundleArgs.putString(ConstantUtils.RESULT_COUNT, str);
        this.bundleArgs.putBoolean(ConstantUtils.RESULT_FLAG, true);
        this.userAchieveDialog.setArguments(this.bundleArgs);
        showDialogFragment(this.userAchieveDialog);
    }

    private void showDialogFragment(BaseRxDialogFragment baseRxDialogFragment) {
        if (baseRxDialogFragment == null || baseRxDialogFragment.isAdded()) {
            return;
        }
        baseRxDialogFragment.show(getChildFragmentManager());
    }

    private List<AnchorEntity> getEmptyAnchorList() {
        ArrayList arrayList = new ArrayList();
        AnchorEntity anchorEntity = new AnchorEntity();
        anchorEntity.nickname = null;
        arrayList.add(anchorEntity);
        return arrayList;
    }
}
