package com.tomatolive.library.p136ui.view.dialog;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.text.SpannableString;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.GiftItemEntity;
import com.tomatolive.library.model.HdDrawEndEntity;
import com.tomatolive.library.model.PopularCardEntity;
import com.tomatolive.library.p136ui.activity.mylive.AwardDetailActivity;
import com.tomatolive.library.p136ui.activity.mylive.AwardHistoryActivity;
import com.tomatolive.library.p136ui.adapter.HdWinningNamesAdapter;
import com.tomatolive.library.p136ui.interfaces.OnHdLotteryCallback;
import com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerLinear;
import com.tomatolive.library.p136ui.view.emptyview.HdWinningEmptyView;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;
import com.tomatolive.library.utils.AnimUtils;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.reactivestreams.Subscription;

/* renamed from: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog */
/* loaded from: classes3.dex */
public class HdLotteryDrawingDialog extends BaseGeneralDialog {
    private String anchorName;
    private GiftDownloadItemEntity downloadItemEntity;
    private String giftNum;
    private HdWinningNamesAdapter hdWinningNamesAdapter;
    private ImageView ivClose;
    private ImageView ivFlower;
    private ImageView ivGiftImg;
    private ImageView ivLotteryDraw;
    private ImageView ivLotteryGift;
    private SVGAImageView ivOpenAnim;
    private ImageView ivRule;
    private String liveDrawRecordId;
    private LinearLayout llDrawEndBg;
    private LinearLayout llDrawOpenBg;
    private LinearLayout llDrawingBg;
    private LinearLayout llHasWonBg;
    private LinearLayout llNowJoinBg;
    private LinearLayout llRuleBg;
    private Disposable mCountdownDisposable;
    private OnHdLotteryCallback onHdLotteryCallback;
    private RelativeLayout rlContentBg;
    private RecyclerView rvWinningNames;
    private String scope;
    private SVGAParser svgaParser;
    private Disposable timerDisposable;
    private TextView tvAddress;
    private TextView tvConditionTips;
    private TextView tvGiftNum;
    private TextView tvNoWinningTips;
    private TextView tvPartCount;
    private TextView tvPriceTips;
    private TextView tvPrizeTips;
    private TextView tvTime_1;
    private TextView tvTime_2;
    private TextView tvTime_3;
    private TextView tvTime_4;
    private TextView tvWinnersName;
    private TextView tvWinningPwsTips;
    private Html5WebView webView;
    private String winningRecordId;
    private final int CONTENT_TYPE_DRAWING = 1;
    private final int CONTENT_TYPE_DRAW_OPEN = 2;
    private final int CONTENT_TYPE_DRAW_END = 3;
    private final int CONTENT_TYPE_DRAW_END_WINNING = 4;
    private final int CONTENT_TYPE_RULE = 5;
    private int contentType = 1;
    private long countdownSecond = 0;
    private volatile boolean isLotteryEnd = false;
    private boolean isAgainStartLottery = false;
    private String markId = "";
    private AtomicInteger participateCount = new AtomicInteger(0);

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    public int getGravityType() {
        return 17;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    protected double getWidthScale() {
        return 0.72d;
    }

    public HdLotteryDrawingDialog(@NonNull Context context, int i, OnHdLotteryCallback onHdLotteryCallback) {
        super(context, i);
        this.onHdLotteryCallback = onHdLotteryCallback;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    protected int getLayoutRes() {
        return R$layout.fq_dialog_hd_lottery_drawing;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    protected void initView() {
        this.rlContentBg = (RelativeLayout) findViewById(R$id.rl_content_bg);
        this.llDrawingBg = (LinearLayout) findViewById(R$id.ll_drawing_bg);
        this.tvPrizeTips = (TextView) findViewById(R$id.tv_prize_tips);
        this.tvTime_1 = (TextView) findViewById(R$id.tv_time_1);
        this.tvTime_2 = (TextView) findViewById(R$id.tv_time_2);
        this.tvTime_3 = (TextView) findViewById(R$id.tv_time_3);
        this.tvTime_4 = (TextView) findViewById(R$id.tv_time_4);
        this.tvConditionTips = (TextView) findViewById(R$id.tv_condition_tips);
        this.ivGiftImg = (ImageView) findViewById(R$id.iv_gift_img);
        this.tvPriceTips = (TextView) findViewById(R$id.tv_price_tips);
        this.tvPartCount = (TextView) findViewById(R$id.tv_now_join);
        this.ivRule = (ImageView) findViewById(R$id.iv_rule);
        this.ivLotteryGift = (ImageView) findViewById(R$id.iv_lottery_gift);
        this.ivLotteryDraw = (ImageView) findViewById(R$id.iv_lottery_draw);
        this.tvGiftNum = (TextView) findViewById(R$id.tv_gift_num);
        this.ivClose = (ImageView) findViewById(R$id.iv_close);
        this.ivFlower = (ImageView) findViewById(R$id.iv_flower);
        this.llNowJoinBg = (LinearLayout) findViewById(R$id.ll_now_join_bg);
        this.llDrawEndBg = (LinearLayout) findViewById(R$id.ll_draw_end_bg);
        this.llHasWonBg = (LinearLayout) findViewById(R$id.ll_has_won_bg);
        this.tvWinningPwsTips = (TextView) findViewById(R$id.tv_winning_pws_tips);
        this.tvNoWinningTips = (TextView) findViewById(R$id.tv_no_winning_tips);
        this.tvAddress = (TextView) findViewById(R$id.tv_address);
        this.tvWinnersName = (TextView) findViewById(R$id.tv_winners_name);
        this.rvWinningNames = (RecyclerView) findViewById(R$id.rv_winning_names);
        this.llRuleBg = (LinearLayout) findViewById(R$id.ll_rule_bg);
        this.webView = (Html5WebView) findViewById(R$id.web_view);
        this.llDrawOpenBg = (LinearLayout) findViewById(R$id.ll_draw_open_bg);
        this.ivOpenAnim = (SVGAImageView) findViewById(R$id.iv_open_anim);
        this.webView.getSettings().setLoadWithOverviewMode(false);
        this.webView.getSettings().setUseWideViewPort(false);
        initWinningNamesAdapter();
        String string = this.mContext.getString(R$string.fq_hd_now_write_address);
        SpannableString spannableString = new SpannableString(string);
        spannableString.setSpan(new ClickableSpan() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.1
            @Override // android.text.style.ClickableSpan, android.text.style.CharacterStyle
            public void updateDrawState(@NonNull TextPaint textPaint) {
                super.updateDrawState(textPaint);
                textPaint.setColor(Color.parseColor("#FFD713"));
                textPaint.setUnderlineText(true);
            }

            @Override // android.text.style.ClickableSpan
            public void onClick(@NonNull View view) {
                if (!TextUtils.isEmpty(HdLotteryDrawingDialog.this.winningRecordId)) {
                    Intent intent = new Intent(((BaseGeneralDialog) HdLotteryDrawingDialog.this).mContext, AwardDetailActivity.class);
                    intent.putExtra(ConstantUtils.RESULT_ITEM, HdLotteryDrawingDialog.this.anchorName);
                    intent.putExtra(ConstantUtils.RESULT_ID, HdLotteryDrawingDialog.this.winningRecordId);
                    intent.putExtra(ConstantUtils.RESULT_FLAG, true);
                    ((BaseGeneralDialog) HdLotteryDrawingDialog.this).mContext.startActivity(intent);
                    return;
                }
                Intent intent2 = new Intent(((BaseGeneralDialog) HdLotteryDrawingDialog.this).mContext, AwardHistoryActivity.class);
                intent2.putExtra(ConstantUtils.RESULT_FLAG, true);
                ((BaseGeneralDialog) HdLotteryDrawingDialog.this).mContext.startActivity(intent2);
            }
        }, 2, string.length() - 2, 33);
        this.tvAddress.setText(spannableString);
        this.tvAddress.setMovementMethod(LinkMovementMethod.getInstance());
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog
    public void initListener() {
        super.initListener();
        this.ivRule.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HdLotteryDrawingDialog.this.showContentView(5);
                HdLotteryDrawingDialog.this.sendDescRequest();
            }
        });
        findViewById(R$id.iv_back).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HdLotteryDrawingDialog.this.showContentView(1);
            }
        });
        this.ivClose.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.4
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                HdLotteryDrawingDialog.this.dismiss();
            }
        });
        this.llNowJoinBg.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (HdLotteryDrawingDialog.this.onHdLotteryCallback != null) {
                    HdLotteryDrawingDialog.this.onHdLotteryCallback.onJoinLotteryListener(HdLotteryDrawingDialog.this.downloadItemEntity, HdLotteryDrawingDialog.this.scope);
                    AnimUtils.playScaleAnim(view);
                }
            }
        });
    }

    public void initDrawInfo(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, String str9, long j, String str10, int i) {
        this.countdownSecond = j;
        this.downloadItemEntity = getGiftDownloadItemEntity(str5, str6, str7, str8, str9);
        this.scope = str4;
        this.isLotteryEnd = false;
        this.liveDrawRecordId = str;
        this.anchorName = str10;
        this.markId = str5;
        this.giftNum = str6;
        setParticipateCount(i);
        startCountdown();
        this.tvPrizeTips.setText(Html.fromHtml(this.mContext.getString(R$string.fq_hd_winning_prize, str2, str3)));
        this.tvConditionTips.setText(this.mContext.getString(R$string.fq_hd_lottery_condition_tips, getConditionTips(str4)));
        String str11 = "x" + str6;
        String str12 = str7 + str11;
        this.tvGiftNum.setText(str11);
        TextView textView = this.tvPriceTips;
        Context context = this.mContext;
        textView.setText(context.getString(R$string.fq_hd_gift_price_tips, str12, AppUtils.formatMoneyUnitStr(context, str8, false)));
        GlideUtils.loadAvatar(this.mContext, this.ivGiftImg, str9, R$drawable.fq_ic_gift_default);
        int i2 = this.contentType;
        if (i2 == 3 || i2 == 4) {
            setParticipateCount(0);
            this.isAgainStartLottery = true;
            this.contentType = 1;
            return;
        }
        showContentView(1);
    }

    public void updatePartCount(int i) {
        this.participateCount.addAndGet(i);
        this.tvPartCount.setVisibility(getParticipateCount() > 0 ? 0 : 8);
        this.tvPartCount.setText(this.mContext.getString(R$string.fq_hd_lottery_part_count, String.valueOf(this.participateCount.get())));
    }

    public void onLotteryEnd() {
        this.isLotteryEnd = true;
        this.isAgainStartLottery = false;
        countdownDispose();
        timerDispose();
        sendLotteryOpenRequest(true);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseGeneralDialog, android.app.Dialog
    public void show() {
        super.show();
        if (this.isAgainStartLottery && this.contentType == 1) {
            showContentView(1);
        }
    }

    public void onCompleteLottery(String str) {
        this.isLotteryEnd = true;
        this.isAgainStartLottery = false;
        this.liveDrawRecordId = str;
        sendLotteryOpenRequest(false);
    }

    public void onStartTimerLotteryEnd(String str) {
        this.liveDrawRecordId = str;
        onStartTimerLotteryEnd();
    }

    public void onStartTimerLotteryEnd() {
        if (this.isLotteryEnd) {
            return;
        }
        showOpenAnim();
        this.timerDisposable = Observable.timer(10L, TimeUnit.SECONDS).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.6
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                if (HdLotteryDrawingDialog.this.isLotteryEnd) {
                    return;
                }
                HdLotteryDrawingDialog.this.onLotteryEnd();
            }
        });
    }

    public void onReleaseData() {
        countdownDispose();
        timerDispose();
        stopOpenAnim();
        this.isLotteryEnd = false;
        this.liveDrawRecordId = "";
        this.participateCount.set(0);
    }

    public String getLiveDrawRecordId() {
        return this.liveDrawRecordId;
    }

    private void initWinningNamesAdapter() {
        this.hdWinningNamesAdapter = new HdWinningNamesAdapter(R$layout.fq_item_grid_hd_winning_names);
        this.rvWinningNames.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.rvWinningNames.addItemDecoration(new RVDividerLinear(this.mContext, R$color.fq_colorWhite, 10.0f));
        this.rvWinningNames.setAdapter(this.hdWinningNamesAdapter);
        this.hdWinningNamesAdapter.bindToRecyclerView(this.rvWinningNames);
        this.hdWinningNamesAdapter.setEmptyView(new HdWinningEmptyView(this.mContext));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showContentView(int i) {
        this.contentType = i;
        int i2 = 4;
        this.llDrawingBg.setVisibility(i == 1 ? 0 : 4);
        this.llDrawOpenBg.setVisibility(i == 2 ? 0 : 4);
        this.llDrawEndBg.setVisibility((i == 3 || i == 4) ? 0 : 4);
        this.llRuleBg.setVisibility(i == 5 ? 0 : 4);
        this.ivRule.setVisibility(i == 1 ? 0 : 4);
        this.ivFlower.setVisibility(i != 4 ? 0 : 4);
        this.llHasWonBg.setVisibility(i == 4 ? 0 : 4);
        this.tvWinningPwsTips.setVisibility(i == 4 ? 0 : 4);
        TextView textView = this.tvNoWinningTips;
        if (i == 3) {
            i2 = 0;
        }
        textView.setVisibility(i2);
        int i3 = 8;
        this.ivLotteryGift.setVisibility(i == 1 ? 0 : 8);
        this.ivLotteryDraw.setVisibility(isDrawEndStatus() ? 0 : 8);
        ImageView imageView = this.ivClose;
        if (isDrawEndStatus()) {
            i3 = 0;
        }
        imageView.setVisibility(i3);
        this.ivLotteryDraw.setImageResource(getLotteryDrawResId());
        if (i != 2) {
            stopOpenAnim();
        }
        if (this.contentType == 1) {
            this.isAgainStartLottery = false;
        }
    }

    @DrawableRes
    private int getLotteryDrawResId() {
        int i = this.contentType;
        if (i != 2) {
            if (i == 3) {
                return R$drawable.fq_ic_hd_lottery_draw_over;
            }
            if (i == 4) {
                return R$drawable.fq_ic_hd_lottery_draw_winning;
            }
            return R$drawable.fq_ic_hd_lottery_draw_over;
        }
        return R$drawable.fq_ic_hd_lottery_draw_open;
    }

    private boolean isDrawEndStatus() {
        int i = this.contentType;
        return i == 3 || i == 4 || i == 2;
    }

    private void startCountdown() {
        if (this.countdownSecond <= 0) {
            return;
        }
        countdownDispose();
        this.mCountdownDisposable = Flowable.intervalRange(0L, this.countdownSecond + 1, 0L, 1L, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).doOnSubscribe(new Consumer<Subscription>() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.9
            @Override // io.reactivex.functions.Consumer
            public void accept(Subscription subscription) throws Exception {
            }
        }).doOnNext(new Consumer<Long>() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.8
            @Override // io.reactivex.functions.Consumer
            public void accept(Long l) throws Exception {
                List formatCountTime = HdLotteryDrawingDialog.this.formatCountTime(HdLotteryDrawingDialog.this.secondToMinutesString(HdLotteryDrawingDialog.this.countdownSecond - l.longValue()));
                if (formatCountTime == null || formatCountTime.isEmpty()) {
                    HdLotteryDrawingDialog.this.tvTime_1.setText("0");
                    HdLotteryDrawingDialog.this.tvTime_2.setText("0");
                    HdLotteryDrawingDialog.this.tvTime_3.setText("0");
                    HdLotteryDrawingDialog.this.tvTime_4.setText("0");
                    return;
                }
                HdLotteryDrawingDialog.this.tvTime_1.setText((CharSequence) formatCountTime.get(0));
                HdLotteryDrawingDialog.this.tvTime_2.setText((CharSequence) formatCountTime.get(1));
                HdLotteryDrawingDialog.this.tvTime_3.setText((CharSequence) formatCountTime.get(2));
                HdLotteryDrawingDialog.this.tvTime_4.setText((CharSequence) formatCountTime.get(3));
            }
        }).doOnComplete(new Action() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.7
            @Override // io.reactivex.functions.Action
            public void run() throws Exception {
                HdLotteryDrawingDialog.this.onStartTimerLotteryEnd();
            }
        }).subscribe();
    }

    private String getConditionTips(String str) {
        String[] stringArray = this.mContext.getResources().getStringArray(R$array.fq_hd_lottery_scope);
        return TextUtils.equals("1", str) ? stringArray[1] : stringArray[0];
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<String> formatCountTime(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            ArrayList arrayList = new ArrayList();
            arrayList.add(String.valueOf(str.charAt(0)));
            arrayList.add(String.valueOf(str.charAt(1)));
            arrayList.add(String.valueOf(str.charAt(2)));
            arrayList.add(String.valueOf(str.charAt(3)));
            return arrayList;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String secondToMinutesString(long j) {
        StringBuilder sb = new StringBuilder();
        Formatter formatter = new Formatter(sb, Locale.getDefault());
        sb.setLength(0);
        return formatter.format("%02d%02d", Long.valueOf(j / 60), Long.valueOf(j % 60)).toString();
    }

    private void sendLotteryOpenRequest(final boolean z) {
        if (TextUtils.isEmpty(this.liveDrawRecordId)) {
            return;
        }
        ApiRetrofit.getInstance().getApiService().getEndLiveDrawInfoService(new RequestParams().getEndLiveDrawInfoParams(this.liveDrawRecordId)).map(new ServerResultFunction<HdDrawEndEntity>() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.11
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<HdDrawEndEntity>() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.10
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(HdDrawEndEntity hdDrawEndEntity) {
                if (hdDrawEndEntity == null) {
                    return;
                }
                HdLotteryDrawingDialog.this.tvWinnersName.setText(((BaseGeneralDialog) HdLotteryDrawingDialog.this).mContext.getString(R$string.fq_hd_lottery_winners, hdDrawEndEntity.getWinningUserNum()));
                HdLotteryDrawingDialog.this.showContentView(hdDrawEndEntity.isOneselfWinning() ? 4 : 3);
                HdLotteryDrawingDialog.this.winningRecordId = hdDrawEndEntity.getWinningRecordId();
                HdLotteryDrawingDialog.this.hdWinningNamesAdapter.setNewData(hdDrawEndEntity.winningUserList);
                if ((HdLotteryDrawingDialog.this.getParticipateCount() > 0 || hdDrawEndEntity.isOneselfWinning()) && z) {
                    HdLotteryDrawingDialog.this.show();
                }
                HdLotteryDrawingDialog.this.setParticipateCount(0);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
                if (HdLotteryDrawingDialog.this.onHdLotteryCallback != null) {
                    HdLotteryDrawingDialog.this.dismiss();
                    HdLotteryDrawingDialog.this.onHdLotteryCallback.onFloatingWindowCloseListener();
                }
            }
        });
    }

    private void initOpenAnim() {
        if (this.svgaParser == null) {
            this.svgaParser = new SVGAParser(this.mContext);
            this.svgaParser.decodeFromAssets(ConstantUtils.HD_LOTTERY_DRAWING_PATH, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.12
                @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                public void onError() {
                }

                @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
                public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                    if (HdLotteryDrawingDialog.this.ivOpenAnim == null) {
                        return;
                    }
                    HdLotteryDrawingDialog.this.ivOpenAnim.setVisibility(0);
                    HdLotteryDrawingDialog.this.ivOpenAnim.setImageDrawable(new SVGADrawable(sVGAVideoEntity));
                    HdLotteryDrawingDialog.this.ivOpenAnim.startAnimation();
                }
            });
        }
    }

    private void stopOpenAnim() {
        if (this.ivOpenAnim.isAnimating()) {
            this.ivOpenAnim.stopAnimation();
        }
    }

    private void countdownDispose() {
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
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendDescRequest() {
        ApiRetrofit.getInstance().getApiService().getAppParamConfigService(new RequestParams().getCodeParams(ConstantUtils.APP_PARAM_HD_LOTTERY_RULE)).map(new ServerResultFunction<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.14
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new SimpleRxObserver<PopularCardEntity>() { // from class: com.tomatolive.library.ui.view.dialog.HdLotteryDrawingDialog.13
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                super.onSubscribe(disposable);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(PopularCardEntity popularCardEntity) {
                if (popularCardEntity == null) {
                    return;
                }
                HdLotteryDrawingDialog.this.webView.loadDataWithBaseURL(null, popularCardEntity.value, "text/html", "UTF-8", null);
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    private void showOpenAnim() {
        showContentView(2);
        initOpenAnim();
        if (this.svgaParser == null || this.ivOpenAnim.isAnimating()) {
            return;
        }
        this.ivOpenAnim.startAnimation();
    }

    private GiftDownloadItemEntity getGiftDownloadItemEntity(String str, String str2, String str3, String str4, String str5) {
        GiftDownloadItemEntity giftDownloadItemEntity = new GiftDownloadItemEntity();
        giftDownloadItemEntity.markId = str;
        giftDownloadItemEntity.price = str4;
        giftDownloadItemEntity.giftCostType = "1";
        giftDownloadItemEntity.giftNum = str2;
        giftDownloadItemEntity.name = str3;
        giftDownloadItemEntity.imgurl = str5;
        return giftDownloadItemEntity;
    }

    public String getMarkId() {
        return this.markId;
    }

    public void updateGiftInfo() {
        GiftItemEntity giftItemEntity;
        if (!TextUtils.isEmpty(this.markId) && (giftItemEntity = GiftDownLoadManager.getInstance().getGiftItemEntity(this.markId)) != null) {
            TextView textView = this.tvPriceTips;
            Context context = this.mContext;
            textView.setText(context.getString(R$string.fq_hd_gift_price_tips, giftItemEntity.name + ("x" + this.giftNum), AppUtils.formatMoneyUnitStr(context, giftItemEntity.price, false)));
            GlideUtils.loadAvatar(this.mContext, this.ivGiftImg, giftItemEntity.imgurl, R$drawable.fq_ic_gift_default);
            this.downloadItemEntity.price = giftItemEntity.price;
        }
    }

    public void setParticipateCount(int i) {
        this.participateCount.set(i);
        this.tvPartCount.setVisibility(getParticipateCount() > 0 ? 0 : 8);
        this.tvPartCount.setText(this.mContext.getString(R$string.fq_hd_lottery_part_count, String.valueOf(this.participateCount.get())));
    }

    public int getParticipateCount() {
        return this.participateCount.get();
    }
}
