package com.tomatolive.library.p136ui.activity.mylive;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.BarUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.AwardHistoryEntity;
import com.tomatolive.library.model.event.AwardDetailEvent;
import com.tomatolive.library.model.event.BaseEvent;
import com.tomatolive.library.p136ui.adapter.AwardsHistoryAdapter;
import com.tomatolive.library.p136ui.presenter.AwardHistoryPresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IAwardListView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/* renamed from: com.tomatolive.library.ui.activity.mylive.AwardHistoryActivity */
/* loaded from: classes3.dex */
public class AwardHistoryActivity extends BaseActivity<AwardHistoryPresenter> implements IAwardListView {
    private Date currentDate;
    private LinearLayout llSelectorBg;
    private AwardsHistoryAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TextView mTvDatePick;
    private TextView mTvStatusPick;
    private OptionsPickerView<String> pvOptions;
    private TimePickerView pvTime;
    private RelativeLayout rlTipsShadowBg;
    private String winningTime;
    private String winningStatus = "";
    private boolean isAwarded = true;

    private String getStatusId(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : "3" : "2" : "1" : "0";
    }

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public AwardHistoryPresenter mo6636createPresenter() {
        return new AwardHistoryPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_awards_history;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        this.isAwarded = getIntent().getBooleanExtra(ConstantUtils.RESULT_FLAG, true);
        if (this.isAwarded) {
            setActivityRightTitle(R$string.fq_hd_awards_history, R$string.fq_hd_appeal_history, new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardHistoryActivity$UvnX7R9dx4WdOnl_dhxYPdvzvvA
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    AwardHistoryActivity.this.lambda$initView$0$AwardHistoryActivity(view);
                }
            });
        } else {
            setActivityTitle(R$string.fq_my_live_give_awards);
        }
        this.llSelectorBg = (LinearLayout) findViewById(R$id.ll_selector);
        this.mTvDatePick = (TextView) findViewById(R$id.fq_tv_date_pick);
        this.mTvStatusPick = (TextView) findViewById(R$id.fq_tv_status_pick);
        this.rlTipsShadowBg = (RelativeLayout) findViewById(R$id.rl_tips_shadow_bg);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        int i = 8;
        findViewById(R$id.v_divider).setVisibility(this.isAwarded ? 8 : 0);
        findViewById(R$id.v_divider_2).setVisibility(this.isAwarded ? 8 : 0);
        findViewById(R$id.v_top).setVisibility(!this.isAwarded ? 8 : 0);
        this.llSelectorBg.setVisibility(this.isAwarded ? 8 : 0);
        RelativeLayout relativeLayout = this.rlTipsShadowBg;
        if (this.isAwarded) {
            i = 0;
        }
        relativeLayout.setVisibility(i);
        this.mTvDatePick.setText(new SimpleDateFormat(DateUtils.C_DATE_PATTON_DATE_CHINA_1, Locale.getDefault()).format(new Date()));
        initAdapter();
        initTimePickerView();
        initOptionsPickerView();
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initView$0$AwardHistoryActivity(View view) {
        startActivity(AppealHistoryActivity.class);
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new AwardsHistoryAdapter(this.isAwarded, R$layout.fq_item_list_awards_history);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, this.isAwarded ? 54 : 53));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        ((AwardHistoryPresenter) this.mPresenter).getDataList(this.isAwarded, this.winningTime, this.winningStatus, this.mStateView, this.pageNum, z, z2);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardHistoryActivity.1
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                AwardHistoryEntity awardHistoryEntity = (AwardHistoryEntity) baseQuickAdapter.getItem(i);
                if (awardHistoryEntity == null) {
                    return;
                }
                Intent intent = new Intent(((BaseActivity) AwardHistoryActivity.this).mContext, AwardDetailActivity.class);
                intent.putExtra(ConstantUtils.RESULT_ITEM, AwardHistoryActivity.this.isAwarded ? awardHistoryEntity.getAnchorName() : awardHistoryEntity.getUserName());
                intent.putExtra(ConstantUtils.RESULT_ID, awardHistoryEntity.getWinningRecordId());
                intent.putExtra(ConstantUtils.RESULT_FLAG, AwardHistoryActivity.this.isAwarded);
                AwardHistoryActivity.this.startActivity(intent);
            }
        });
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardHistoryActivity$w803ydGn-lcEidkH5IksBiBFl5E
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                AwardHistoryActivity.this.lambda$initListener$1$AwardHistoryActivity();
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.AwardHistoryActivity.2
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                AwardHistoryActivity awardHistoryActivity = AwardHistoryActivity.this;
                awardHistoryActivity.pageNum++;
                awardHistoryActivity.sendRequest(false, false);
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refreshLayout.resetNoMoreData();
                AwardHistoryActivity awardHistoryActivity = AwardHistoryActivity.this;
                awardHistoryActivity.pageNum = 1;
                awardHistoryActivity.sendRequest(false, true);
                refreshLayout.mo6481finishRefresh();
            }
        });
        this.mTvDatePick.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardHistoryActivity$7jjcykyRyw0HrXQQeNlB5CU4deQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AwardHistoryActivity.this.lambda$initListener$2$AwardHistoryActivity(view);
            }
        });
        this.mTvStatusPick.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardHistoryActivity$E2vz2HOo6_PmJyt0gQS5IoGJm58
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                AwardHistoryActivity.this.lambda$initListener$3$AwardHistoryActivity(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$AwardHistoryActivity() {
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$2$AwardHistoryActivity(View view) {
        TimePickerView timePickerView = this.pvTime;
        if (timePickerView != null) {
            timePickerView.show();
        }
    }

    public /* synthetic */ void lambda$initListener$3$AwardHistoryActivity(View view) {
        OptionsPickerView<String> optionsPickerView = this.pvOptions;
        if (optionsPickerView != null) {
            optionsPickerView.show();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardListView
    public void onDataListSuccess(List<AwardHistoryEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
            if (this.isAwarded) {
                this.rlTipsShadowBg.setVisibility(list.isEmpty() ? 8 : 0);
            }
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IAwardListView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    private void initTimePickerView() {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2017, 0, 1);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2030, 11, 1);
        TimePickerBuilder timePickerBuilder = new TimePickerBuilder(this.mContext, new OnTimeSelectListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardHistoryActivity$AM85RLlX-ITniDYVgYN2n4k7I5A
            @Override // com.bigkoo.pickerview.listener.OnTimeSelectListener
            public final void onTimeSelect(Date date, View view) {
                AwardHistoryActivity.this.lambda$initTimePickerView$4$AwardHistoryActivity(date, view);
            }
        });
        timePickerBuilder.setType(new boolean[]{true, true, false, false, false, false});
        timePickerBuilder.setCancelText(this.mContext.getString(R$string.fq_btn_cancel));
        timePickerBuilder.setSubmitText(this.mContext.getString(R$string.fq_done));
        timePickerBuilder.setOutSideCancelable(true);
        timePickerBuilder.isCyclic(false);
        timePickerBuilder.setTitleColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        timePickerBuilder.setSubmitColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary));
        timePickerBuilder.setCancelColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        timePickerBuilder.setTitleBgColor(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        timePickerBuilder.setBgColor(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        timePickerBuilder.setContentTextSize(20);
        timePickerBuilder.setDate(calendar);
        timePickerBuilder.setRangDate(calendar2, calendar3);
        timePickerBuilder.setLabel("年", "月", "日", "时", "分", "秒");
        this.pvTime = timePickerBuilder.build();
        if (BarUtils.isSupportNavBar()) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.pvTime.getDialogContainerLayout().getLayoutParams();
            layoutParams.bottomMargin = BarUtils.getNavBarHeight();
            this.pvTime.getDialogContainerLayout().setLayoutParams(layoutParams);
        }
    }

    public /* synthetic */ void lambda$initTimePickerView$4$AwardHistoryActivity(Date date, View view) {
        this.currentDate = date;
        this.mTvDatePick.setText(new SimpleDateFormat(DateUtils.C_DATE_PATTON_DATE_CHINA_1, Locale.getDefault()).format(this.currentDate));
        this.winningTime = String.valueOf(this.currentDate.getTime());
        this.pageNum = 1;
        sendRequest(false, true);
    }

    private void initOptionsPickerView() {
        final ArrayList arrayList = new ArrayList();
        arrayList.add(getString(R$string.fq_hd_text_all));
        arrayList.addAll(Arrays.asList(getResources().getStringArray(R$array.fq_given_award_status)));
        OptionsPickerBuilder optionsPickerBuilder = new OptionsPickerBuilder(this.mContext, new OnOptionsSelectListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$AwardHistoryActivity$VFh5RZHL1rbKEmKtHXyar42eMko
            @Override // com.bigkoo.pickerview.listener.OnOptionsSelectListener
            public final void onOptionsSelect(int i, int i2, int i3, View view) {
                AwardHistoryActivity.this.lambda$initOptionsPickerView$5$AwardHistoryActivity(arrayList, i, i2, i3, view);
            }
        });
        optionsPickerBuilder.setCancelText(this.mContext.getString(R$string.fq_btn_cancel));
        optionsPickerBuilder.setSubmitText(this.mContext.getString(R$string.fq_done));
        optionsPickerBuilder.setOutSideCancelable(true);
        optionsPickerBuilder.setTitleColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        optionsPickerBuilder.setSubmitColor(ContextCompat.getColor(this.mContext, R$color.fq_colorPrimary));
        optionsPickerBuilder.setCancelColor(ContextCompat.getColor(this.mContext, R$color.fq_text_black));
        optionsPickerBuilder.setTitleBgColor(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        optionsPickerBuilder.setBgColor(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        optionsPickerBuilder.setContentTextSize(20);
        this.pvOptions = optionsPickerBuilder.build();
        this.pvOptions.setPicker(arrayList);
        if (BarUtils.isSupportNavBar()) {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) this.pvOptions.getDialogContainerLayout().getLayoutParams();
            layoutParams.bottomMargin = BarUtils.getNavBarHeight();
            this.pvOptions.getDialogContainerLayout().setLayoutParams(layoutParams);
        }
    }

    public /* synthetic */ void lambda$initOptionsPickerView$5$AwardHistoryActivity(ArrayList arrayList, int i, int i2, int i3, View view) {
        this.mTvStatusPick.setText((CharSequence) arrayList.get(i));
        this.winningStatus = getStatusId(i);
        this.pageNum = 1;
        sendRequest(false, true);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void onEventMainThread(BaseEvent baseEvent) {
        super.onEventMainThread(baseEvent);
        if (baseEvent instanceof AwardDetailEvent) {
            this.pageNum = 1;
            sendRequest(false, true);
        }
    }
}
