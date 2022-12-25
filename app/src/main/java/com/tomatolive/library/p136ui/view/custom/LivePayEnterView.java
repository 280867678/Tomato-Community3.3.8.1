package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Html;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.activity.home.WebViewActivity;
import com.tomatolive.library.p136ui.interfaces.OnPayLiveCallback;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.RxViewUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

/* renamed from: com.tomatolive.library.ui.view.custom.LivePayEnterView */
/* loaded from: classes3.dex */
public class LivePayEnterView extends RelativeLayout {
    private Disposable mDisposable;
    private OnPayLiveCallback onPayLiveEnterCallback;
    private TextView tvEnter;
    private TextView tvExit;
    private TextView tvHistoryScore;
    private TextView tvLiveTime;
    private TextView tvRuleDesc;
    private TextView tvTicketsPrice;
    private TextView tvTotalCount;
    private long times = 10;
    private boolean isDownTimeEnable = false;

    public LivePayEnterView(Context context) {
        super(context);
        initView(context);
    }

    public LivePayEnterView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initView(context);
    }

    private void initView(Context context) {
        setBackground(new ColorDrawable(Color.parseColor("#B8000000")));
        RelativeLayout.inflate(context, R$layout.fq_layout_pay_tickets_enter_view, this);
        this.tvTicketsPrice = (TextView) findViewById(R$id.tv_tickets_price);
        this.tvExit = (TextView) findViewById(R$id.tv_exit);
        this.tvEnter = (TextView) findViewById(R$id.tv_enter);
        this.tvHistoryScore = (TextView) findViewById(R$id.tv_history_score);
        this.tvTotalCount = (TextView) findViewById(R$id.tv_total_count);
        this.tvLiveTime = (TextView) findViewById(R$id.tv_live_time);
        this.tvRuleDesc = (TextView) findViewById(R$id.tv_rule_desc);
        initListener();
    }

    public void initData(String str, String str2, String str3, String str4, String str5) {
        String str6 = AppUtils.getLiveMoneyUnitStr(getContext()) + AppUtils.formatDisplayPrice(str2, false);
        this.tvTicketsPrice.setText(Html.fromHtml(getContext().getString(R$string.fq_current_live_tickets_room_price_tips, str6, str6)));
        TextView textView = this.tvHistoryScore;
        Context context = getContext();
        int i = R$string.fq_pay_history_score;
        Object[] objArr = new Object[1];
        if (TextUtils.isEmpty(str4)) {
            str4 = getContext().getString(R$string.fq_pay_history_score_empty);
        }
        objArr[0] = str4;
        textView.setText(context.getString(i, objArr));
        this.tvTotalCount.setText(getContext().getString(R$string.fq_pay_live_total_count, str5));
        this.tvLiveTime.setText(getContext().getString(R$string.fq_pay_live_time, DateUtils.secondToMinutesString(NumberUtils.string2long(str3))));
        this.times = 10L;
        this.isDownTimeEnable = true;
        showCountDown(this.times);
    }

    public void setOnPayLiveEnterCallback(OnPayLiveCallback onPayLiveCallback) {
        this.onPayLiveEnterCallback = onPayLiveCallback;
    }

    private void initListener() {
        RxViewUtils.getInstance().throttleFirst(this.tvEnter, 1000, new RxViewUtils.RxViewAction() { // from class: com.tomatolive.library.ui.view.custom.LivePayEnterView.1
            @Override // com.tomatolive.library.utils.RxViewUtils.RxViewAction
            public void action(Object obj) {
                if (LivePayEnterView.this.onPayLiveEnterCallback != null) {
                    LivePayEnterView.this.onPayLiveEnterCallback.onPayEnterClickListener(LivePayEnterView.this.tvEnter);
                }
            }
        });
        this.tvExit.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePayEnterView$bs8dC3MLy5r4MzoXCSkKHvvbawY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LivePayEnterView.this.lambda$initListener$0$LivePayEnterView(view);
            }
        });
        this.tvRuleDesc.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.custom.-$$Lambda$LivePayEnterView$dWHc8b1-3MjsbfB5B6rIxSChZCo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LivePayEnterView.this.lambda$initListener$1$LivePayEnterView(view);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LivePayEnterView(View view) {
        if (this.onPayLiveEnterCallback != null) {
            onRelease();
            this.onPayLiveEnterCallback.onPayExitClickListener();
        }
    }

    public /* synthetic */ void lambda$initListener$1$LivePayEnterView(View view) {
        Intent intent = new Intent(getContext(), WebViewActivity.class);
        intent.putExtra(ConstantUtils.WEB_VIEW_FROM_SERVICE, true);
        intent.putExtra("url", ConstantUtils.APP_PARAM_PAY_LIVE);
        intent.putExtra("title", getContext().getString(R$string.fq_week_star_rule_desc));
        getContext().startActivity(intent);
    }

    public void setEnterEnable(boolean z) {
        this.tvEnter.setEnabled(z);
    }

    public void onResume() {
        if (!this.isDownTimeEnable) {
            return;
        }
        showCountDown(this.times);
    }

    public void onPause() {
        Disposable disposable;
        if (this.isDownTimeEnable && (disposable = this.mDisposable) != null && !disposable.isDisposed()) {
            this.mDisposable.dispose();
            this.mDisposable = null;
        }
    }

    public void onRelease() {
        Disposable disposable = this.mDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDisposable.dispose();
            this.mDisposable = null;
        }
        this.isDownTimeEnable = false;
        setEnterEnable(true);
    }

    public void onReset() {
        this.isDownTimeEnable = true;
        onResume();
    }

    private void showCountDown(final long j) {
        Disposable disposable = this.mDisposable;
        if (disposable != null && !disposable.isDisposed()) {
            this.mDisposable.dispose();
        }
        this.tvExit.setText(getContext().getString(R$string.fq_pay_exit_room_tips, String.valueOf(j)));
        if (this.mDisposable == null) {
            this.mDisposable = Flowable.intervalRange(0L, 1 + j, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Subscription>() { // from class: com.tomatolive.library.ui.view.custom.LivePayEnterView.4
                @Override // io.reactivex.functions.Consumer
                public void accept(Subscription subscription) throws Exception {
                }
            }).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.custom.LivePayEnterView.3
                @Override // io.reactivex.functions.Consumer
                public void accept(Long l) throws Exception {
                    long longValue = j - l.longValue();
                    LivePayEnterView.this.times = longValue;
                    LivePayEnterView.this.tvExit.setText(LivePayEnterView.this.getContext().getString(R$string.fq_pay_exit_room_tips, String.valueOf(longValue)));
                }
            }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.custom.LivePayEnterView.2
                @Override // io.reactivex.functions.Action
                public void run() throws Exception {
                    if (LivePayEnterView.this.onPayLiveEnterCallback != null) {
                        ToastUtils.showShort(LivePayEnterView.this.getContext().getString(R$string.fq_pay_live_timeout_toast));
                        LivePayEnterView.this.onPayLiveEnterCallback.onPayExitClickListener();
                    }
                }
            }).subscribe();
        }
    }
}
