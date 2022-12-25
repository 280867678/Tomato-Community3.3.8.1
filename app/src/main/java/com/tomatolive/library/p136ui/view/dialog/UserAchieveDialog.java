package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p002v4.widget.NestedScrollView;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.UserAchieveEntity;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.adapter.UserAchieveAdapter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.headview.UserAchieveHeadView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.Collection;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.UserAchieveDialog */
/* loaded from: classes3.dex */
public class UserAchieveDialog extends BaseBottomDialogFragment {
    private ImageView ivClose;
    private ImageView ivDetailAchieve;
    private ImageView ivHelp;
    private ImageView ivStaBg;
    private RecyclerView recyclerView;
    private NestedScrollView rlAchieveDetailBg;
    private String role;
    private SmartRefreshLayout smartRefreshLayout;
    private TextView tvAchieveTitle;
    private TextView tvActivityTime;
    private TextView tvActivityTitle;
    private TextView tvDialogTitle;
    private TextView tvLoading;
    private TextView tvLoadingFail;
    private UserAchieveAdapter userAchieveAdapter;
    private UserAchieveHeadView userAchieveHeadView;
    private UserEntity userEntity;
    private String userId;
    private final int CONTENT_TYPE_1 = 1;
    private final int CONTENT_TYPE_2 = 2;
    private int contentType = 1;
    private String totalAchieveCount = "0";
    private boolean isDimAmount = false;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected double getHeightScale() {
        return 0.6d;
    }

    public static UserAchieveDialog newInstance(UserEntity userEntity, String str) {
        UserAchieveDialog userAchieveDialog = new UserAchieveDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.RESULT_COUNT, str);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, userEntity);
        userAchieveDialog.setArguments(bundle);
        return userAchieveDialog;
    }

    public static UserAchieveDialog newInstance(UserEntity userEntity, String str, boolean z) {
        UserAchieveDialog userAchieveDialog = new UserAchieveDialog();
        Bundle bundle = new Bundle();
        bundle.putString(ConstantUtils.RESULT_COUNT, str);
        bundle.putBoolean(ConstantUtils.RESULT_FLAG, z);
        bundle.putParcelable(ConstantUtils.RESULT_ITEM, userEntity);
        userAchieveDialog.setArguments(bundle);
        return userAchieveDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.totalAchieveCount = bundle.getString(ConstantUtils.RESULT_COUNT, "0");
        this.isDimAmount = bundle.getBoolean(ConstantUtils.RESULT_FLAG, false);
        this.userEntity = (UserEntity) bundle.getParcelable(ConstantUtils.RESULT_ITEM);
        UserEntity userEntity = this.userEntity;
        String str = "";
        this.userId = userEntity == null ? str : userEntity.getUserId();
        UserEntity userEntity2 = this.userEntity;
        if (userEntity2 != null) {
            str = TextUtils.isEmpty(userEntity2.getUserRole()) ? this.userEntity.getRole() : this.userEntity.getUserRole();
        }
        this.role = str;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_achieve_ta_achieve_view;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.ivClose = (ImageView) view.findViewById(R$id.iv_close);
        this.ivHelp = (ImageView) view.findViewById(R$id.iv_help);
        this.ivDetailAchieve = (ImageView) view.findViewById(R$id.iv_detail_achieve);
        this.ivStaBg = (ImageView) view.findViewById(R$id.iv_star_bg);
        this.tvDialogTitle = (TextView) view.findViewById(R$id.tv_dialog_title);
        this.tvAchieveTitle = (TextView) view.findViewById(R$id.tv_achieve_title);
        this.tvActivityTitle = (TextView) view.findViewById(R$id.tv_activity_title);
        this.tvActivityTime = (TextView) view.findViewById(R$id.tv_activity_time);
        this.tvLoading = (TextView) view.findViewById(R$id.tv_loading);
        this.tvLoadingFail = (TextView) view.findViewById(R$id.tv_loading_fail);
        this.rlAchieveDetailBg = (NestedScrollView) view.findViewById(R$id.rl_achieve_detail_bg);
        this.smartRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.record_refreshLayout);
        this.recyclerView = (RecyclerView) view.findViewById(R$id.recycler_view_record);
        initUserAchieveAdapter();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        UserAchieveHeadView userAchieveHeadView;
        super.onResume();
        if (this.userEntity != null && (userAchieveHeadView = this.userAchieveHeadView) != null) {
            userAchieveHeadView.initData(AppUtils.isAnchor(this.role), this.userEntity.getName(), this.userEntity.getAvatar(), this.totalAchieveCount);
        }
        this.pageNum = 1;
        sendRequest(this.role, this.pageNum, true, true);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserAchieveDialog$3jfkFoXyc4wzh76U0oI5Ydr-Ce4
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserAchieveDialog.this.lambda$initListener$0$UserAchieveDialog(view2);
            }
        });
        this.userAchieveHeadView.setOnAchieveTagClickListener(new UserAchieveHeadView.OnAchieveTagClickListener() { // from class: com.tomatolive.library.ui.view.dialog.UserAchieveDialog.1
            @Override // com.tomatolive.library.p136ui.view.headview.UserAchieveHeadView.OnAchieveTagClickListener
            public void onAnchorAchieveListener() {
                UserAchieveDialog.this.role = "1";
                UserAchieveDialog userAchieveDialog = UserAchieveDialog.this;
                userAchieveDialog.pageNum = 1;
                userAchieveDialog.sendRequest(userAchieveDialog.role, UserAchieveDialog.this.pageNum, false, true);
            }

            @Override // com.tomatolive.library.p136ui.view.headview.UserAchieveHeadView.OnAchieveTagClickListener
            public void onUserAchieveListener() {
                UserAchieveDialog.this.role = "2";
                UserAchieveDialog userAchieveDialog = UserAchieveDialog.this;
                userAchieveDialog.pageNum = 1;
                userAchieveDialog.sendRequest(userAchieveDialog.role, UserAchieveDialog.this.pageNum, false, true);
            }
        });
        this.userAchieveAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.UserAchieveDialog.2
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view2, int i) {
                UserAchieveEntity userAchieveEntity = (UserAchieveEntity) baseQuickAdapter.getItem(i);
                if (userAchieveEntity == null) {
                    return;
                }
                UserAchieveDialog.this.showContentView(2);
                UserAchieveDialog.this.showAchieveDetail(userAchieveEntity);
            }
        });
        this.ivHelp.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserAchieveDialog$iq-tUdBCRJCqP926I-6gy9QHcxc
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserAchieveDialog.this.lambda$initListener$1$UserAchieveDialog(view2);
            }
        });
        this.smartRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.tomatolive.library.ui.view.dialog.UserAchieveDialog.3
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                UserAchieveDialog userAchieveDialog = UserAchieveDialog.this;
                userAchieveDialog.pageNum++;
                userAchieveDialog.sendRequest(userAchieveDialog.role, UserAchieveDialog.this.pageNum, false, false);
            }
        });
        this.tvLoadingFail.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$UserAchieveDialog$sPw-pybzofFtuvHRpQXgQmDmqEE
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                UserAchieveDialog.this.lambda$initListener$2$UserAchieveDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$UserAchieveDialog(View view) {
        if (this.contentType == 1) {
            dismiss();
        } else {
            showContentView(1);
        }
    }

    public /* synthetic */ void lambda$initListener$1$UserAchieveDialog(View view) {
        CommonRuleTipsDialog.newInstance(ConstantUtils.APP_PARAM_ACHIEVEMENT_DESC, getString(R$string.fq_achieve_rule_tips), true, 0.46d).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$initListener$2$UserAchieveDialog(View view) {
        this.pageNum = 1;
        sendRequest(this.role, this.pageNum, true, true);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        if (this.isDimAmount) {
            return super.getDimAmount();
        }
        return 0.0f;
    }

    private void initUserAchieveAdapter() {
        UserAchieveHeadView userAchieveHeadView;
        this.userAchieveHeadView = new UserAchieveHeadView(this.mContext);
        this.userAchieveAdapter = new UserAchieveAdapter(R$layout.fq_item_grid_achieve_ta_achieve_view);
        this.recyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.recyclerView.setHasFixedSize(true);
        this.recyclerView.setAdapter(this.userAchieveAdapter);
        this.userAchieveAdapter.bindToRecyclerView(this.recyclerView);
        this.userAchieveAdapter.addHeaderView(this.userAchieveHeadView);
        if (this.userEntity == null || (userAchieveHeadView = this.userAchieveHeadView) == null) {
            return;
        }
        userAchieveHeadView.initData(AppUtils.isAnchor(this.role), this.userEntity.getName(), this.userEntity.getAvatar(), this.totalAchieveCount);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(String str, int i, final boolean z, final boolean z2) {
        ApiRetrofit.getInstance().getApiService().getAchieveWallService(new RequestParams().getAchievementWallParams(this.userId, str, i)).map(new ServerResultFunction<HttpResultPageModel<UserAchieveEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.UserAchieveDialog.5
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new SimpleRxObserver<HttpResultPageModel<UserAchieveEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.UserAchieveDialog.4
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
                if (z) {
                    UserAchieveDialog.this.showLoading();
                }
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(HttpResultPageModel<UserAchieveEntity> httpResultPageModel) {
                List<UserAchieveEntity> list;
                if (httpResultPageModel == null || (list = httpResultPageModel.dataList) == null) {
                    return;
                }
                if (z2) {
                    UserAchieveDialog.this.userAchieveAdapter.setNewData(list);
                } else {
                    UserAchieveDialog.this.userAchieveAdapter.addData((Collection) list);
                }
                AppUtils.updateRefreshLayoutFinishStatus(UserAchieveDialog.this.smartRefreshLayout, httpResultPageModel.isMorePage(), z2);
                UserAchieveDialog.this.showContentView(1);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                UserAchieveDialog.this.showFailView();
                if (!z2) {
                    UserAchieveDialog.this.smartRefreshLayout.finishLoadMore();
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView(int i) {
        this.contentType = i;
        int i2 = 4;
        this.tvLoading.setVisibility(4);
        this.tvLoadingFail.setVisibility(4);
        this.tvDialogTitle.setVisibility(i == 1 ? 0 : 4);
        this.smartRefreshLayout.setVisibility(i == 1 ? 0 : 4);
        NestedScrollView nestedScrollView = this.rlAchieveDetailBg;
        if (i == 2) {
            i2 = 0;
        }
        nestedScrollView.setVisibility(i2);
        this.ivClose.setImageResource(i == 1 ? R$drawable.fq_ic_achieve_close_white : R$drawable.fq_ic_achieve_back_white);
        this.ivStaBg.setImageResource(i == 1 ? R$drawable.fq_ic_achieve_star_bg : R$drawable.fq_ic_achieve_star_bg_2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showLoading() {
        this.tvLoading.setVisibility(0);
        this.tvLoadingFail.setVisibility(4);
        this.smartRefreshLayout.setVisibility(4);
        this.rlAchieveDetailBg.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showFailView() {
        this.tvLoadingFail.setVisibility(0);
        this.tvLoading.setVisibility(4);
        this.smartRefreshLayout.setVisibility(4);
        this.rlAchieveDetailBg.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showAchieveDetail(UserAchieveEntity userAchieveEntity) {
        GlideUtils.loadImage(this.mContext, this.ivDetailAchieve, userAchieveEntity.url);
        this.tvAchieveTitle.setText(userAchieveEntity.name);
        this.tvActivityTitle.setText(userAchieveEntity.remark);
        if (userAchieveEntity.isForeverValid()) {
            this.tvActivityTime.setText(this.mContext.getString(R$string.fq_achieve_get_achieve_time_desc, userAchieveEntity.getCreateTime(), getString(R$string.fq_achieve_get_achieve_forever_valid)));
        } else {
            this.tvActivityTime.setText(this.mContext.getString(R$string.fq_achieve_get_achieve_time_desc, userAchieveEntity.getCreateTime(), getString(R$string.fq_achieve_get_achieve_valid_days, userAchieveEntity.getDays())));
        }
    }
}
