package com.tomatolive.library.p136ui.activity.mylive;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;
import com.blankj.utilcode.util.TimeUtils;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.base.BaseActivity;
import com.tomatolive.library.base.BasePresenter;
import com.tomatolive.library.model.PaidLiveIncomeExpenseEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.NumberUtils;

/* renamed from: com.tomatolive.library.ui.activity.mylive.UserIncomeDetailActivity */
/* loaded from: classes3.dex */
public class UserIncomeDetailActivity extends BaseActivity {
    private PaidLiveIncomeExpenseEntity incomeExpenseEntity;
    private TextView tvEndLiveTime;
    private TextView tvLiveId;
    private TextView tvLiveTimes;
    private TextView tvLiveTitle;
    private TextView tvMaxPopularity;
    private TextView tvPrice;
    private TextView tvStartLiveTime;
    private TextView tvTicket;
    private TextView tvWatchCount;

    @Override // com.tomatolive.library.base.BaseActivity
    /* renamed from: createPresenter */
    protected BasePresenter mo6636createPresenter() {
        return null;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    protected int getLayoutId() {
        return R$layout.fq_activity_user_income_detail;
    }

    @Override // com.tomatolive.library.base.BaseActivity
    public void initView(Bundle bundle) {
        setActivityTitle(R$string.fq_income_detail_tips);
        this.tvStartLiveTime = (TextView) findViewById(R$id.tv_start_live_time);
        this.tvEndLiveTime = (TextView) findViewById(R$id.tv_end_live_time);
        this.tvLiveId = (TextView) findViewById(R$id.tv_live_id);
        this.tvLiveTitle = (TextView) findViewById(R$id.tv_live_title);
        this.tvWatchCount = (TextView) findViewById(R$id.tv_watch_count);
        this.tvTicket = (TextView) findViewById(R$id.tv_ticket);
        this.tvLiveTimes = (TextView) findViewById(R$id.tv_live_times);
        this.tvMaxPopularity = (TextView) findViewById(R$id.tv_max_popularity);
        this.tvPrice = (TextView) findViewById(R$id.tv_price);
        this.incomeExpenseEntity = (PaidLiveIncomeExpenseEntity) getIntent().getSerializableExtra(ConstantUtils.RESULT_ITEM);
        PaidLiveIncomeExpenseEntity paidLiveIncomeExpenseEntity = this.incomeExpenseEntity;
        if (paidLiveIncomeExpenseEntity != null) {
            String formatDisplayPrice = AppUtils.formatDisplayPrice(paidLiveIncomeExpenseEntity.getTicketPracticalPrice(), true);
            this.tvPrice.setText(formatDisplayPrice);
            this.tvStartLiveTime.setText(Html.fromHtml(getString(R$string.fq_income_detail_start_live_time_tips, new Object[]{TimeUtils.millis2String(NumberUtils.string2long(this.incomeExpenseEntity.getBeginTime()) * 1000)})));
            this.tvEndLiveTime.setText(Html.fromHtml(getString(R$string.fq_income_detail_end_live_time_tips, new Object[]{TimeUtils.millis2String(NumberUtils.string2long(this.incomeExpenseEntity.getEndTime()) * 1000)})));
            this.tvLiveId.setText(Html.fromHtml(getString(R$string.fq_income_detail_live_id_tips, new Object[]{this.incomeExpenseEntity.getLiveCount()})));
            this.tvLiveTitle.setText(Html.fromHtml(getString(R$string.fq_income_detail_live_title_tips, new Object[]{this.incomeExpenseEntity.getTopic()})));
            this.tvWatchCount.setText(Html.fromHtml(getString(R$string.fq_income_detail_watch_count_tips, new Object[]{this.incomeExpenseEntity.getWatchMemberCount()})));
            this.tvTicket.setText(Html.fromHtml(getString(R$string.fq_income_detail_ticket_tips, new Object[]{formatDisplayPrice})));
            this.tvLiveTimes.setText(Html.fromHtml(getString(R$string.fq_income_detail_live_time_tips, new Object[]{this.incomeExpenseEntity.getLiveTime()})));
            this.tvMaxPopularity.setText(Html.fromHtml(getString(R$string.fq_income_detail_max_popularity_tips, new Object[]{this.incomeExpenseEntity.getMaxPopularity()})));
        }
    }
}
