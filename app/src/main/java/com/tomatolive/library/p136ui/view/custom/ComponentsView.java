package com.tomatolive.library.p136ui.view.custom;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback;
import com.tomatolive.library.p136ui.view.custom.luckpan.ArcProgress;
import com.tomatolive.library.utils.GlideUtils;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscription;

/* renamed from: com.tomatolive.library.ui.view.custom.ComponentsView */
/* loaded from: classes3.dex */
public class ComponentsView extends RelativeLayout {
    private int boomType;
    private boolean isStart;
    private ImageView ivCover;
    private ImageView ivGiftIcon;
    private Disposable mDisposable;
    private OnLotteryBoomCallback onLotteryBoomCallback;
    private ArcProgress progressView;
    private RelativeLayout rlBoomBg;
    private TextView tvBoom;

    public ComponentsView(Context context) {
        this(context, null);
    }

    public ComponentsView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ComponentsView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.boomType = -1;
        initView(context);
    }

    private void initView(Context context) {
        RelativeLayout.inflate(context, R$layout.fq_layout_components_view, this);
        this.ivGiftIcon = (ImageView) findViewById(R$id.fq_gift_icon);
        this.ivCover = (ImageView) findViewById(R$id.iv_cover);
        this.progressView = (ArcProgress) findViewById(R$id.fq_arc_downcount);
        this.tvBoom = (TextView) findViewById(R$id.tv_gift_boomMultiple);
        this.rlBoomBg = (RelativeLayout) findViewById(R$id.rl_boom_bg);
    }

    public void initCoverDrawableRes(@DrawableRes int i) {
        this.ivCover.setImageResource(i);
    }

    public void initCoverImgUrl(String str) {
        GlideUtils.loadAvatar(getContext(), this.ivCover, GlideUtils.formatDownUrl(str), R$drawable.fq_ic_live_game_placeholder);
    }

    public void onLotteryBoomOpen(String str, int i, int i2, int i3, int i4) {
        this.rlBoomBg.setVisibility(0);
        this.ivCover.setVisibility(4);
        onRelease();
        GlideUtils.loadImage(getContext(), this.ivGiftIcon, str);
        TextView textView = this.tvBoom;
        textView.setText("x" + i);
        this.boomType = i4;
        this.progressView.setMax(i3);
        this.progressView.setProgress((float) i2);
        showBoomCountDown(i2);
    }

    public void onLotteryBoomClosed() {
        this.rlBoomBg.setVisibility(4);
        this.ivCover.setVisibility(0);
        onRelease();
    }

    public boolean isBoomStatus() {
        return this.isStart;
    }

    public boolean isLuxuryBoomType() {
        return this.boomType == 20;
    }

    private void showBoomCountDown(final int i) {
        this.mDisposable = Flowable.intervalRange(0L, i + 1, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Subscription>() { // from class: com.tomatolive.library.ui.view.custom.ComponentsView.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Subscription subscription) throws Exception {
                ComponentsView.this.isStart = true;
            }
        }).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.custom.ComponentsView.2
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                ComponentsView.this.progressView.setProgress((float) (i - l.longValue()));
            }
        }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.custom.ComponentsView.1
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                ComponentsView.this.isStart = false;
                ComponentsView.this.boomType = -1;
                ComponentsView.this.rlBoomBg.setVisibility(4);
                ComponentsView.this.ivCover.setVisibility(0);
                if (ComponentsView.this.onLotteryBoomCallback != null) {
                    ComponentsView.this.onLotteryBoomCallback.onBoomCountDownEnd();
                }
            }
        }).subscribe();
    }

    public OnLotteryBoomCallback getOnLotteryBoomCallback() {
        return this.onLotteryBoomCallback;
    }

    public void setOnLotteryBoomEndCallback(OnLotteryBoomCallback onLotteryBoomCallback) {
        this.onLotteryBoomCallback = onLotteryBoomCallback;
    }

    public void onRelease() {
        Disposable disposable = this.mDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.isStart = false;
        this.boomType = -1;
        this.mDisposable.dispose();
    }
}
