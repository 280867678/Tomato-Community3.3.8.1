package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.p136ui.view.widget.bgabanner.TopBannerUtils;
import com.tomatolive.library.utils.DateUtils;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

/* renamed from: com.tomatolive.library.ui.view.custom.HdLotteryWindowView */
/* loaded from: classes3.dex */
public class HdLotteryWindowView extends RelativeLayout {
    private long countdownSecond;
    private TopBannerUtils.InteractWindowListener listener;
    private Context mContext;
    private Disposable mCountdownDisposable;
    private Disposable timerDisposable;
    private TextView tvLotteryTime;
    private TextView tvLotteryType;

    public HdLotteryWindowView(Context context) {
        this(context, null);
    }

    public HdLotteryWindowView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.countdownSecond = 0L;
        this.mContext = context;
        initView();
    }

    private void initView() {
        RelativeLayout.inflate(this.mContext, R$layout.fq_layout_hd_window_view, this);
        this.tvLotteryTime = (TextView) findViewById(R$id.tv_lottery_time);
        this.tvLotteryType = (TextView) findViewById(R$id.tv_lottery_type);
    }

    public void initDrawInfo(boolean z, long j) {
        timerDispose();
        setVisibility(0);
        this.countdownSecond = j;
        this.tvLotteryType.setText(z ? R$string.fq_hd_gift_lottery : R$string.fq_hd_gift_password);
        startCountdown();
    }

    public void setOnHdLotteryCallback(TopBannerUtils.InteractWindowListener interactWindowListener) {
        this.listener = interactWindowListener;
    }

    private void startCountdown() {
        if (this.countdownSecond <= 0) {
            return;
        }
        countdownDispose();
        this.mCountdownDisposable = Flowable.intervalRange(0L, this.countdownSecond + 1, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Subscription>() { // from class: com.tomatolive.library.ui.view.custom.HdLotteryWindowView.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Subscription subscription) throws Exception {
            }
        }).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.custom.HdLotteryWindowView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                HdLotteryWindowView.this.tvLotteryTime.setText(DateUtils.secondToMinutesString(HdLotteryWindowView.this.countdownSecond - l.longValue()));
            }
        }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.custom.HdLotteryWindowView.1
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
            }
        }).subscribe();
    }

    private void startTimer() {
        this.timerDisposable = Observable.timer(30L, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.custom.HdLotteryWindowView.4
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                if (HdLotteryWindowView.this.timerDisposable == null || HdLotteryWindowView.this.listener == null) {
                    return;
                }
                HdLotteryWindowView.this.listener.onDelete(HdLotteryWindowView.this);
            }
        });
    }

    public void onLotteryEnd() {
        countdownDispose();
        this.countdownSecond = 0L;
        this.tvLotteryTime.setText(R$string.fq_hd_gift_lottery_open_tips);
        this.tvLotteryType.setText(R$string.fq_hd_gift_lottery_result);
        startTimer();
    }

    public void onReleaseData() {
        setVisibility(4);
        countdownDispose();
        timerDispose();
        this.countdownSecond = 0L;
        this.tvLotteryTime.setText("");
    }

    public void countdownDispose() {
        Disposable disposable = this.mCountdownDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.mCountdownDisposable.dispose();
    }

    private void timerDispose() {
        Disposable disposable = this.timerDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.timerDisposable.dispose();
        this.timerDisposable = null;
    }
}
