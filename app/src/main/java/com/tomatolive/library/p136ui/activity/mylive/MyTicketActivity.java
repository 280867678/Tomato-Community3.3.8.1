package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.blankj.utilcode.util.BarUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.model.MyTicketEntity;
import com.tomatolive.library.p136ui.adapter.MyTicketAdapter;
import com.tomatolive.library.p136ui.presenter.MyTicketPresenter;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.iview.IMyTicketView;
import com.tomatolive.library.p136ui.view.widget.StateView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.DateUtils;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.activity.mylive.MyTicketActivity */
/* loaded from: classes3.dex */
public class MyTicketActivity extends BaseActivity<MyTicketPresenter> implements IMyTicketView {
    private MyTicketAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private SmartRefreshLayout mSmartRefreshLayout;
    private TimePickerView pvTime;
    private TextView tvCalendar;
    private TextView tvGetRecord;
    private TextView tvUseRecord;
    private boolean isGetRecord = true;
    private String selectDate = DateUtils.getCurrentDateTime("yyyy-MM-dd");

    @Override // com.tomatolive.library.base.BaseView
    public void onResultError(int i) {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter  reason: collision with other method in class */
    public MyTicketPresenter mo6636createPresenter() {
        return new MyTicketPresenter(this.mContext, this);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_my_ticket;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected View injectStateView() {
        return findViewById(R$id.fl_content_view);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_lottery_my_ticket);
        this.tvGetRecord = (TextView) findViewById(R$id.tv_get_record);
        this.tvUseRecord = (TextView) findViewById(R$id.tv_use_record);
        this.tvCalendar = (TextView) findViewById(R$id.tv_calendar);
        this.mRecyclerView = (RecyclerView) findViewById(R$id.recycler_view);
        this.mSmartRefreshLayout = (SmartRefreshLayout) findViewById(R$id.refreshLayout);
        this.tvCalendar.setText(DateUtils.getCurrentDateTime(DateUtils.C_DATE_PATTON_DATE_CHINA_2));
        initAdapter();
        initTimePickerView();
        initRecordStatus(true, true, true);
    }

    private void initAdapter() {
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mAdapter = new MyTicketAdapter(R$layout.fq_item_list_lottery_my_ticket, this.isGetRecord);
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
        this.mAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendRequest(boolean z, boolean z2) {
        this.mSmartRefreshLayout.resetNoMoreData();
        ((MyTicketPresenter) this.mPresenter).getDataList(this.mStateView, this.selectDate, this.isGetRecord ? 1 : 2, this.pageNum, z, z2);
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initListener() {
        super.initListener();
        this.mStateView.setOnRetryClickListener(new StateView.OnRetryClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyTicketActivity$_Gt1WwZPN_wzS0XL9jQO09ZQTPo
            @Override // com.tomatolive.library.p136ui.view.widget.StateView.OnRetryClickListener
            public final void onRetryClick() {
                MyTicketActivity.this.lambda$initListener$0$MyTicketActivity();
            }
        });
        this.tvGetRecord.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyTicketActivity$zLF_AWTFIqk_A6caCrwaapa74hw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyTicketActivity.this.lambda$initListener$1$MyTicketActivity(view);
            }
        });
        this.tvUseRecord.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyTicketActivity$BaAR0C9roKQUTC6dus6tZTvAz2Y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyTicketActivity.this.lambda$initListener$2$MyTicketActivity(view);
            }
        });
        this.tvCalendar.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.activity.mylive.-$$Lambda$MyTicketActivity$g6aqp8WHaqTfwQh8Pm0Sx6jgVMk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                MyTicketActivity.this.lambda$initListener$3$MyTicketActivity(view);
            }
        });
        this.mSmartRefreshLayout.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyTicketActivity.1
            @Override // com.scwang.smartrefresh.layout.listener.OnRefreshListener
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
            }

            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                MyTicketActivity myTicketActivity = MyTicketActivity.this;
                myTicketActivity.pageNum++;
                myTicketActivity.sendRequest(false, false);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$MyTicketActivity() {
        sendRequest(true, true);
    }

    public /* synthetic */ void lambda$initListener$1$MyTicketActivity(View view) {
        initRecordStatus(true, false, true);
    }

    public /* synthetic */ void lambda$initListener$2$MyTicketActivity(View view) {
        initRecordStatus(false, false, true);
    }

    public /* synthetic */ void lambda$initListener$3$MyTicketActivity(View view) {
        TimePickerView timePickerView = this.pvTime;
        if (timePickerView != null) {
            timePickerView.show();
        }
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyTicketView
    public void onDataListSuccess(List<MyTicketEntity> list, boolean z, boolean z2) {
        if (z2) {
            this.mAdapter.setNewData(list);
        } else {
            this.mAdapter.addData((Collection) list);
        }
        AppUtils.updateRefreshLayoutFinishStatus(this.mSmartRefreshLayout, z, z2);
    }

    @Override // com.tomatolive.library.p136ui.view.iview.IMyTicketView
    public void onDataListFail(boolean z) {
        if (!z) {
            this.mSmartRefreshLayout.finishLoadMore();
        }
    }

    private void initRecordStatus(boolean z, boolean z2, boolean z3) {
        this.pageNum = 1;
        this.isGetRecord = z;
        this.tvGetRecord.setSelected(z);
        this.tvUseRecord.setSelected(!z);
        this.mAdapter.setGetRecord(z);
        sendRequest(z2, z3);
    }

    private void initTimePickerView() {
        Calendar calendar = Calendar.getInstance();
        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2017, 0, 1);
        Calendar calendar3 = Calendar.getInstance();
        calendar3.set(2030, 11, 1);
        TimePickerBuilder timePickerBuilder = new TimePickerBuilder(this.mContext, new OnTimeSelectListener() { // from class: com.tomatolive.library.ui.activity.mylive.MyTicketActivity.2
            @Override // com.bigkoo.pickerview.listener.OnTimeSelectListener
            public void onTimeSelect(Date date, View view) {
                MyTicketActivity.this.tvCalendar.setText(DateUtils.dateToString(date, DateUtils.C_DATE_PATTON_DATE_CHINA_2));
                MyTicketActivity.this.selectDate = DateUtils.dateToString(date, "yyyy-MM-dd");
                MyTicketActivity myTicketActivity = MyTicketActivity.this;
                myTicketActivity.pageNum = 1;
                myTicketActivity.sendRequest(false, true);
            }
        });
        timePickerBuilder.setType(new boolean[]{true, true, true, false, false, false});
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
}
