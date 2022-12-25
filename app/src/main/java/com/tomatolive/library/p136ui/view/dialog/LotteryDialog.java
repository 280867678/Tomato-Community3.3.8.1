package com.tomatolive.library.p136ui.view.dialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.LinearLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.CacheDiskUtils;
import com.blankj.utilcode.util.ConvertUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.tomatolive.library.R$array;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.download.GiftDownLoadManager;
import com.tomatolive.library.download.ResHotLoadManager;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.exception.ApiException;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.AnchorEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.model.LotteryBoomDetailEntity;
import com.tomatolive.library.model.LotteryLuckReportEntity;
import com.tomatolive.library.model.LotteryPrizeEntity;
import com.tomatolive.library.model.LotteryRecordEntity;
import com.tomatolive.library.model.LotteryTurntableDrawEntity;
import com.tomatolive.library.model.LotteryTurntableInfoEntity;
import com.tomatolive.library.model.LotteryUserRankEntity;
import com.tomatolive.library.model.LuckValueEntity;
import com.tomatolive.library.model.TurntableMode;
import com.tomatolive.library.model.UserEntity;
import com.tomatolive.library.p136ui.adapter.LotteryRecordAdapter;
import com.tomatolive.library.p136ui.adapter.LotteryTopAnchorAdapter;
import com.tomatolive.library.p136ui.adapter.LotteryTopUserAdapter;
import com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback;
import com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack;
import com.tomatolive.library.p136ui.view.custom.LotteryBoomView;
import com.tomatolive.library.p136ui.view.custom.LotteryTicketBtnView;
import com.tomatolive.library.p136ui.view.custom.MarqueeLotteryNoticeView;
import com.tomatolive.library.p136ui.view.custom.luckpan.LuckPanView;
import com.tomatolive.library.p136ui.view.custom.luckpan.RotateListener;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment;
import com.tomatolive.library.p136ui.view.dialog.confirm.SureCancelDialog;
import com.tomatolive.library.p136ui.view.divider.RVDividerLotteryRecord;
import com.tomatolive.library.p136ui.view.emptyview.LotteryTopEmptyView;
import com.tomatolive.library.p136ui.view.emptyview.RecyclerIncomeEmptyView;
import com.tomatolive.library.p136ui.view.headview.LotteryAnchorTopHeadView;
import com.tomatolive.library.p136ui.view.headview.LotteryRecordHeadView;
import com.tomatolive.library.p136ui.view.widget.Html5WebView;
import com.tomatolive.library.p136ui.view.widget.marqueen.MarqueeFactory;
import com.tomatolive.library.p136ui.view.widget.marqueen.MarqueeView;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.LogEventUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SvgaUtils;
import com.tomatolive.library.utils.SysConfigInfoManager;
import com.tomatolive.library.utils.live.SimpleRxObserver;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import razerdp.basepopup.BasePopupWindow;

/* renamed from: com.tomatolive.library.ui.view.dialog.LotteryDialog */
/* loaded from: classes3.dex */
public class LotteryDialog extends BaseBottomDialogFragment {
    public static final int DEF_BOOM_STATUS = -1;
    public static final int MAX_VALUE = 400;
    private LotteryTopAnchorAdapter anchorAdapter;
    private LotteryAnchorTopHeadView anchorTopHeadView;
    private ArrayList<LotteryPrizeEntity> awardList;
    private Bitmap defaultPrizeBitmap;
    private LotteryTopEmptyView emptyViewAnchor;
    private LotteryTopEmptyView emptyViewUser;
    private FrameLayout flDialogBg;
    private FrameLayout flRotateContentView;
    private LotteryBoomView generalBoomView;
    private LuckPanView generalLuckPanView;
    private String generalVersion;
    private boolean isGeneralBooming;
    private boolean isGeneralClick;
    private boolean isLuxuryBooming;
    private boolean isLuxuryClick;
    private ImageView ivBack;
    private ImageView ivFullStars;
    private ImageView ivLotteryTopLuck;
    private ImageView ivNoticeDot;
    private String liveId;
    private LinearLayout llLoadingFailBg;
    private LinearLayout llLotteryContentBg;
    private LinearLayout llMoreContentBg;
    private LinearLayout llTicketBtnContentBg;
    private LinearLayout llTopAnchorBg;
    private LinearLayout llTopContentBg;
    private String localGeneralVersion;
    private String localLuxuryVersion;
    private LotteryTicketBtnView lotteryTicketBtnView1;
    private LotteryTicketBtnView lotteryTicketBtnView10;
    private LotteryTicketBtnView lotteryTicketBtnView100;
    private LotteryBoomView luxuryBoomView;
    private LuckPanView luxuryLuckPanView;
    private String luxuryVersion;
    private MarqueeFactory<LinearLayout, LotteryLuckReportEntity> marqueeFactory;
    private MarqueeView marqueeViewNotice;
    private View marqueeViewRoot;
    private MorePopDialog morePopDialog;
    private double myBalance;
    private OnLotteryDialogCallback onLotteryDialogCallback;
    private LotteryRecordAdapter recordAdapter;
    private String recordDateStr;
    private LotteryRecordHeadView recordHeadView;
    private SmartRefreshLayout recordRefreshLayout;
    private RecyclerView recyclerViewAnchor;
    private RecyclerView recyclerViewRecord;
    private RecyclerView recyclerViewUser;
    private RelativeLayout rlNoticeBellBg;
    private SimpleSVGACallBack simpleSVGACallBack;
    private SVGAImageView svLuckAnim;
    private SVGAParser svgaParser;
    private TextView tvGeneralRoulette;
    private TextView tvLoadingText;
    private TextView tvLuckyValue;
    private TextView tvLuxuryRoulette;
    private TextView tvMore;
    private TextView tvMoreTitle;
    private TextView tvPrice;
    private TextView tvTicketTips;
    private TextView tvTopAnchor;
    private TextView tvTopTips;
    private TextView tvTopUser;
    private LotteryTopUserAdapter userAdapter;
    private Html5WebView webView;
    private final int CONTENT_TYPE_1 = 1;
    private final int CONTENT_TYPE_2 = 2;
    private final int CONTENT_TYPE_3 = 3;
    private final int CONTENT_TYPE_4 = 4;
    private int contentType = 1;
    private final int TICKET_TYPE_1 = 1;
    private final int TICKET_TYPE_10 = 10;
    private final int TICKET_TYPE_100 = 100;
    private int generalTicketType = 1;
    private int luxuryTicketType = 1;
    private int generalProgress = 0;
    private int luxuryProgress = 0;
    private boolean isLuxury = false;
    private boolean isLoadingGeneralSuccess = false;
    private boolean isLoadingLuxurySuccess = false;
    private List<LotteryLuckReportEntity> marqueeList = new ArrayList();
    private boolean isGeneralLoading = true;
    private boolean isLuxuryLoading = true;

    /* renamed from: com.tomatolive.library.ui.view.dialog.LotteryDialog$OnLotteryDialogCallback */
    /* loaded from: classes3.dex */
    public interface OnLotteryDialogCallback {
        void onClickAnchorAvatarListener(AnchorEntity anchorEntity);

        void onClickUserAvatarListener(UserEntity userEntity);

        void onJumpBackpackDialogListener();
    }

    private String getAreaBgColor(int i) {
        return (i == 0 || i == 1) ? "#FFECA2" : (i == 2 || i == 3) ? "#FFD4D4" : (i == 4 || i == 5) ? "#D4E9FF" : "#FFFFFF";
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static LotteryDialog newInstance(String str, OnLotteryDialogCallback onLotteryDialogCallback) {
        Bundle bundle = new Bundle();
        LotteryDialog lotteryDialog = new LotteryDialog();
        bundle.putString(ConstantUtils.RESULT_ITEM, str);
        lotteryDialog.setArguments(bundle);
        lotteryDialog.setOnLotteryDialogCallback(onLotteryDialogCallback);
        return lotteryDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.liveId = bundle.getString(ConstantUtils.RESULT_ITEM);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_lottery;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    protected void initView(View view) {
        this.generalLuckPanView = (LuckPanView) view.findViewById(R$id.general_rotate);
        this.luxuryLuckPanView = (LuckPanView) view.findViewById(R$id.luxury_rotate);
        this.tvPrice = (TextView) view.findViewById(R$id.tv_price);
        this.svLuckAnim = (SVGAImageView) view.findViewById(R$id.iv_svga);
        this.svgaParser = new SVGAParser(this.mContext);
        this.tvGeneralRoulette = (TextView) view.findViewById(R$id.tv_general_roulette);
        this.tvLuxuryRoulette = (TextView) view.findViewById(R$id.tv_luxury_roulette);
        this.tvLoadingText = (TextView) view.findViewById(R$id.tv_loading_text);
        this.tvMore = (TextView) view.findViewById(R$id.tv_more);
        this.tvLuckyValue = (TextView) view.findViewById(R$id.tv_lucky_value);
        this.tvTicketTips = (TextView) view.findViewById(R$id.tv_ticket_tips);
        this.ivNoticeDot = (ImageView) view.findViewById(R$id.iv_dot);
        this.lotteryTicketBtnView1 = (LotteryTicketBtnView) view.findViewById(R$id.btn_ticket_1);
        this.lotteryTicketBtnView10 = (LotteryTicketBtnView) view.findViewById(R$id.btn_ticket_10);
        this.lotteryTicketBtnView100 = (LotteryTicketBtnView) view.findViewById(R$id.btn_ticket_100);
        this.ivBack = (ImageView) view.findViewById(R$id.iv_back);
        this.ivLotteryTopLuck = (ImageView) view.findViewById(R$id.iv_lottery_top_luck);
        this.ivFullStars = (ImageView) view.findViewById(R$id.iv_full_stars);
        this.marqueeViewNotice = (MarqueeView) view.findViewById(R$id.marquee_notice_tips);
        this.marqueeViewRoot = view.findViewById(R$id.ll_marquee_root);
        this.flDialogBg = (FrameLayout) view.findViewById(R$id.fl_lottery_dialog_bg);
        this.llTopContentBg = (LinearLayout) view.findViewById(R$id.ll_top_content_bg);
        this.llLotteryContentBg = (LinearLayout) view.findViewById(R$id.ll_lottery_content_bg);
        this.llTopAnchorBg = (LinearLayout) view.findViewById(R$id.ll_top_anchor_bg);
        this.llMoreContentBg = (LinearLayout) view.findViewById(R$id.ll_more_content_bg);
        this.flRotateContentView = (FrameLayout) view.findViewById(R$id.fl_rotate_content_view);
        this.llTicketBtnContentBg = (LinearLayout) view.findViewById(R$id.ll_ticket_btn_bg);
        this.llLoadingFailBg = (LinearLayout) view.findViewById(R$id.ll_loading_fail_bg);
        this.rlNoticeBellBg = (RelativeLayout) view.findViewById(R$id.rl_notice_bell_bg);
        this.webView = (Html5WebView) view.findViewById(R$id.web_view);
        this.tvTopAnchor = (TextView) view.findViewById(R$id.tv_top_anchor);
        this.tvTopUser = (TextView) view.findViewById(R$id.tv_top_user);
        this.tvTopTips = (TextView) view.findViewById(R$id.tv_top_tips);
        this.tvMoreTitle = (TextView) view.findViewById(R$id.tv_more_title);
        this.recyclerViewAnchor = (RecyclerView) view.findViewById(R$id.recycler_view_anchor);
        this.recyclerViewUser = (RecyclerView) view.findViewById(R$id.recycler_view_user);
        this.recyclerViewRecord = (RecyclerView) view.findViewById(R$id.recycler_view_record);
        this.recordRefreshLayout = (SmartRefreshLayout) view.findViewById(R$id.record_refreshLayout);
        this.generalBoomView = (LotteryBoomView) view.findViewById(R$id.general_boom_view);
        this.luxuryBoomView = (LotteryBoomView) view.findViewById(R$id.luxury_boom_view);
        this.marqueeFactory = new MarqueeLotteryNoticeView(this.mContext);
        this.marqueeViewNotice.setMarqueeFactory(this.marqueeFactory);
        this.tvGeneralRoulette.setSelected(true);
        this.lotteryTicketBtnView1.setTicketName(getString(R$string.fq_lottery_1));
        this.lotteryTicketBtnView10.setTicketName(getString(R$string.fq_lottery_10));
        this.lotteryTicketBtnView100.setTicketName(getString(R$string.fq_lottery_100));
        this.defaultPrizeBitmap = BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_gift_default);
        int i = 0;
        this.ivNoticeDot.setVisibility(SPUtils.getInstance().getBoolean(ConstantUtils.SHOW_LOTTERY_NOTICE_DOT_TIP, true) ? 0 : 4);
        RelativeLayout relativeLayout = this.rlNoticeBellBg;
        if (!SysConfigInfoManager.getInstance().isEnableTurntableUpdateTip()) {
            i = 4;
        }
        relativeLayout.setVisibility(i);
        setTextViewLeftDrawable(this.tvPrice);
        updateLuckyValue();
        updateTicketTypeViewSelected();
        initMorePopDialog();
        initAnchorAdapter();
        initUserAdapter();
        initRecordAdapter();
        initWebView();
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, android.support.p002v4.app.DialogFragment, android.content.DialogInterface.OnDismissListener
    public void onDismiss(DialogInterface dialogInterface) {
        super.onDismiss(dialogInterface);
        MorePopDialog morePopDialog = this.morePopDialog;
        if (morePopDialog == null || !morePopDialog.isShowing()) {
            return;
        }
        this.morePopDialog.dismiss();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        super.onResume();
        if (this.contentType != 1) {
            this.contentType = 1;
            initContentView(this.contentType);
        }
        this.llLotteryContentBg.setVisibility(4);
        this.llLoadingFailBg.setVisibility(4);
        this.isLoadingGeneralSuccess = false;
        this.isLoadingLuxurySuccess = false;
        sendLuckValueRequest();
        sendLuckReportRequest();
        if (this.isLuxury) {
            sendLuxuryTurntableRequest(true);
        } else {
            sendGeneralRequest(true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendTurntableDrawRequest(final boolean z) {
        if (!isRestrictionUser()) {
            return;
        }
        if (getTicketTypeValue() == 0) {
            showToast(R$string.fq_select_lottery_type);
        } else if (GiftDownLoadManager.getInstance().getLuckyGiftItem() == null) {
            showToast(R$string.fq_luck_gift_error_tips);
        } else {
            double mul = NumberUtils.mul(this.isLuxury ? this.luxuryTicketType * 20 : this.generalTicketType, getLuckyGiftPrice());
            double d = this.myBalance;
            if (d <= 0.0d || d < mul) {
                SureCancelDialog.newInstance(getString(R$string.fq_virtual_gold_short), getString(R$string.fq_text_whether_recharge), getString(R$string.fq_btn_cancel), getString(R$string.fq_go_recharge), new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.1
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        AppUtils.onRechargeListener(((BaseRxDialogFragment) LotteryDialog.this).mContext);
                        LogEventUtils.uploadRechargeClick(LotteryDialog.this.getString(R$string.fq_lottery_recharge_entrance));
                    }
                }).show(getChildFragmentManager());
            } else if (!SPUtils.getInstance().getBoolean(ConstantUtils.SHOW_CONSUME_GOLD_LOTTERY_TIP)) {
                SureCancelDialog.newInstance(getString(R$string.fq_virtual_gold_lottery), getString(R$string.fq_virtual_gold_lottery_tips), getString(R$string.fq_think_again), getString(R$string.fq_allow), new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.2
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        SPUtils.getInstance().put(ConstantUtils.SHOW_CONSUME_GOLD_LOTTERY_TIP, true);
                        LotteryDialog.this.loadingSendTurntableDrawRequest(z);
                    }
                }).show(getChildFragmentManager());
            } else {
                loadingSendTurntableDrawRequest(z);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadingSendTurntableDrawRequest(final boolean z) {
        if (this.isLuxuryClick || this.isGeneralClick) {
            showToast(R$string.fq_luck_draw);
            return;
        }
        if (z) {
            this.isLuxuryClick = true;
        } else {
            this.isGeneralClick = true;
        }
        setDialogCancelable(false);
        turntableDrawEventReport();
        ApiRetrofit.getInstance().getApiService().getTurntableDrawService(new RequestParams().getTurntableDrawParams(this.liveId, this.isLuxury, getTicketTypeValue(), this.isLuxury ? this.luxuryVersion : this.generalVersion)).map(new ServerResultFunction<LotteryTurntableDrawEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.4
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<LotteryTurntableDrawEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.3
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(LotteryTurntableDrawEntity lotteryTurntableDrawEntity) {
                LotteryDialog.this.awardList = (ArrayList) lotteryTurntableDrawEntity.awardList;
                int i = lotteryTurntableDrawEntity.code;
                if (z) {
                    LotteryDialog.this.luxuryLuckPanView.startClickLottery(i);
                    LotteryDialog lotteryDialog = LotteryDialog.this;
                    lotteryDialog.luxuryProgress = Math.max(lotteryDialog.luxuryProgress, lotteryTurntableDrawEntity.luckValue);
                    LotteryDialog.this.luxuryLuckPanView.setProgress(LotteryDialog.this.luxuryProgress);
                    if (LotteryDialog.this.isLuxuryBoomStatus()) {
                        LotteryDialog.this.executeBoom(true);
                    }
                } else {
                    LotteryDialog.this.generalLuckPanView.startClickLottery(i);
                    LotteryDialog lotteryDialog2 = LotteryDialog.this;
                    lotteryDialog2.generalProgress = Math.max(lotteryDialog2.generalProgress, lotteryTurntableDrawEntity.luckValue);
                    LotteryDialog.this.generalLuckPanView.setProgress(LotteryDialog.this.generalProgress);
                    if (LotteryDialog.this.isGeneralBoomStatus()) {
                        LotteryDialog.this.executeBoom(false);
                    }
                }
                LotteryDialog.this.updateLuckyValue();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                th.printStackTrace();
                if (z) {
                    LotteryDialog.this.isLuxuryClick = false;
                } else {
                    LotteryDialog.this.isGeneralClick = false;
                }
                LotteryDialog.this.setDialogCancelable(true);
                if (th instanceof ApiException) {
                    ApiException apiException = (ApiException) th;
                    int code = apiException.getCode();
                    String msg = apiException.getMsg();
                    if (!TextUtils.isEmpty(msg)) {
                        LotteryDialog.this.showToast(msg);
                    }
                    if (code != 200038) {
                        return;
                    }
                    if (z) {
                        LotteryDialog.this.sendAgainLuxuryTurntableRequest();
                    } else {
                        LotteryDialog.this.sendAgainTurntableRequest();
                    }
                }
            }
        });
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        super.onPause();
        if (this.marqueeViewNotice.isFlipping()) {
            this.marqueeViewNotice.stopFlipping();
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        this.tvGeneralRoulette.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.5
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (!LotteryDialog.this.isLuxuryClick && !LotteryDialog.this.isGeneralClick) {
                    if (!LotteryDialog.this.isLuxury || !LotteryDialog.this.svLuckAnim.isAnimating()) {
                        LotteryDialog.this.svLuckAnim.setVisibility(0);
                    } else {
                        LotteryDialog.this.svLuckAnim.setVisibility(4);
                    }
                    LotteryDialog.this.initRouletteTagView(false);
                    LotteryDialog lotteryDialog = LotteryDialog.this;
                    lotteryDialog.initBoomBgViewVisibility(lotteryDialog.isGeneralBoomStatus());
                    LotteryDialog.this.initBoomViewVisibility();
                    LotteryDialog.this.sendGeneralRequest(true);
                    return;
                }
                LotteryDialog.this.showToast(R$string.fq_luck_draw);
            }
        });
        this.tvLuxuryRoulette.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.6
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (!LotteryDialog.this.isGeneralClick && !LotteryDialog.this.isLuxuryClick) {
                    if (LotteryDialog.this.isLuxury || !LotteryDialog.this.svLuckAnim.isAnimating()) {
                        LotteryDialog.this.svLuckAnim.setVisibility(0);
                    } else {
                        LotteryDialog.this.svLuckAnim.setVisibility(4);
                    }
                    LotteryDialog.this.initRouletteTagView(true);
                    LotteryDialog lotteryDialog = LotteryDialog.this;
                    lotteryDialog.initBoomBgViewVisibility(lotteryDialog.isLuxuryBoomStatus());
                    LotteryDialog.this.initBoomViewVisibility();
                    LotteryDialog.this.sendLuxuryTurntableRequest(true);
                    return;
                }
                LotteryDialog.this.showToast(R$string.fq_luck_draw);
            }
        });
        this.lotteryTicketBtnView1.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.7
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LotteryDialog.this.updateTicketTypeValue(1);
            }
        });
        this.lotteryTicketBtnView10.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.8
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LotteryDialog.this.updateTicketTypeValue(10);
            }
        });
        this.lotteryTicketBtnView100.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.9
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LotteryDialog.this.updateTicketTypeValue(100);
            }
        });
        view.findViewById(R$id.tv_lucky_top).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.10
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (LotteryDialog.this.llLoadingFailBg.getVisibility() == 0) {
                    return;
                }
                LotteryDialog.this.flDialogBg.setBackgroundResource(R$drawable.fq_shape_lottery_dialog_bg);
                LotteryDialog.this.ivFullStars.setVisibility(4);
                LotteryDialog.this.tvTopAnchor.setSelected(true);
                LotteryDialog.this.tvTopUser.setSelected(false);
                LotteryDialog.this.initContentView(2);
                LotteryDialog.this.sendAnchorTopRequest();
            }
        });
        this.ivBack.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.11
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                int i = 4;
                if (!LotteryDialog.this.isLuxury) {
                    LotteryDialog.this.flDialogBg.setBackgroundResource(LotteryDialog.this.isGeneralBoomStatus() ? R$drawable.fq_shape_lottery_dialog_full_bg : R$drawable.fq_shape_lottery_dialog_bg);
                    ImageView imageView = LotteryDialog.this.ivFullStars;
                    if (LotteryDialog.this.isGeneralBoomStatus()) {
                        i = 0;
                    }
                    imageView.setVisibility(i);
                } else {
                    LotteryDialog.this.flDialogBg.setBackgroundResource(LotteryDialog.this.isLuxuryBoomStatus() ? R$drawable.fq_shape_lottery_dialog_full_bg : R$drawable.fq_shape_lottery_dialog_bg);
                    ImageView imageView2 = LotteryDialog.this.ivFullStars;
                    if (LotteryDialog.this.isLuxuryBoomStatus()) {
                        i = 0;
                    }
                    imageView2.setVisibility(i);
                }
                LotteryDialog.this.tvTopAnchor.setSelected(true);
                LotteryDialog.this.tvTopUser.setSelected(false);
                LotteryDialog.this.initContentView(1);
            }
        });
        this.tvTopAnchor.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.12
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LotteryDialog.this.initTopContentView(true);
                LotteryDialog.this.sendAnchorTopRequest();
            }
        });
        this.tvTopUser.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.13
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LotteryDialog.this.initTopContentView(false);
                LotteryDialog.this.sendUserTopRequest();
            }
        });
        this.tvMore.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.14
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (LotteryDialog.this.llLoadingFailBg.getVisibility() == 0 || LotteryDialog.this.morePopDialog == null) {
                    return;
                }
                LotteryDialog.this.morePopDialog.showPopupWindow(LotteryDialog.this.tvMore);
            }
        });
        view.findViewById(R$id.tv_my_backpack).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.15
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                if (LotteryDialog.this.onLotteryDialogCallback != null) {
                    LotteryDialog.this.dismiss();
                    LotteryDialog.this.onLotteryDialogCallback.onJumpBackpackDialogListener();
                }
            }
        });
        this.generalLuckPanView.setRotateListener(new RotateListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.16
            @Override // com.tomatolive.library.p136ui.view.custom.luckpan.RotateListener
            public void rotateEnd(int i, String str) {
                LotteryDialog.this.isGeneralClick = false;
                LotteryDialog.this.setDialogCancelable(true);
                if (!LotteryDialog.this.isAdded()) {
                    return;
                }
                if (LotteryDialog.this.awardList == null || LotteryDialog.this.awardList.isEmpty()) {
                    LotteryDialog.this.showToast(R$string.fq_lottery_no_winning_tips);
                } else {
                    LotteryResultTipsDialog.newInstance(LotteryDialog.this.awardList, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.16.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            LotteryDialog.this.sendTurntableDrawRequest(false);
                        }
                    }).show(LotteryDialog.this.getChildFragmentManager());
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.luckpan.RotateListener
            public void rotateBefore() {
                LotteryDialog.this.sendTurntableDrawRequest(false);
            }
        });
        this.luxuryLuckPanView.setRotateListener(new RotateListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.17
            @Override // com.tomatolive.library.p136ui.view.custom.luckpan.RotateListener
            public void rotateEnd(int i, String str) {
                LotteryDialog.this.isLuxuryClick = false;
                LotteryDialog.this.setDialogCancelable(true);
                if (!LotteryDialog.this.isAdded()) {
                    return;
                }
                if (LotteryDialog.this.awardList == null || LotteryDialog.this.awardList.isEmpty()) {
                    LotteryDialog.this.showToast(R$string.fq_lottery_no_winning_tips);
                } else {
                    LotteryResultTipsDialog.newInstance(LotteryDialog.this.awardList, new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.17.1
                        @Override // android.view.View.OnClickListener
                        public void onClick(View view2) {
                            LotteryDialog.this.sendTurntableDrawRequest(true);
                        }
                    }).show(LotteryDialog.this.getChildFragmentManager());
                }
            }

            @Override // com.tomatolive.library.p136ui.view.custom.luckpan.RotateListener
            public void rotateBefore() {
                LotteryDialog.this.sendTurntableDrawRequest(true);
            }
        });
        this.generalBoomView.setOnLotteryBoomCallback(new OnLotteryBoomCallback() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.18
            @Override // com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback
            public void onBoomCountDownEnd() {
                LotteryDialog.this.isGeneralBooming = false;
                LotteryDialog.this.generalProgress = 0;
                if (!LotteryDialog.this.isLuxury) {
                    LotteryDialog lotteryDialog = LotteryDialog.this;
                    lotteryDialog.initBoomBgViewVisibility(lotteryDialog.isGeneralBoomStatus());
                }
                LotteryDialog.this.generalLuckPanView.onReset();
                LotteryDialog.this.sendAgainTurntableRequest();
            }
        });
        this.luxuryBoomView.setOnLotteryBoomCallback(new OnLotteryBoomCallback() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.19
            @Override // com.tomatolive.library.p136ui.interfaces.OnLotteryBoomCallback
            public void onBoomCountDownEnd() {
                LotteryDialog.this.isLuxuryBooming = false;
                LotteryDialog.this.luxuryProgress = 0;
                if (LotteryDialog.this.isLuxury) {
                    LotteryDialog lotteryDialog = LotteryDialog.this;
                    lotteryDialog.initBoomBgViewVisibility(lotteryDialog.isLuxuryBoomStatus());
                }
                LotteryDialog.this.luxuryLuckPanView.onReset();
                LotteryDialog.this.sendAgainLuxuryTurntableRequest();
            }
        });
        this.simpleSVGACallBack = new SimpleSVGACallBack() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.20
            @Override // com.tomatolive.library.p136ui.interfaces.impl.SimpleSVGACallBack, com.opensource.svgaplayer.SVGACallback
            public void onFinished() {
                super.onFinished();
                LotteryDialog.this.initBoomViewVisibility();
            }
        };
        this.svLuckAnim.setCallback(this.simpleSVGACallBack);
        view.findViewById(R$id.tv_reload).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.21
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                LotteryDialog.this.llLoadingFailBg.setVisibility(4);
                if (LotteryDialog.this.isLuxury) {
                    LotteryDialog.this.sendLuxuryTurntableRequest(true);
                } else {
                    LotteryDialog.this.sendGeneralRequest(true);
                }
            }
        });
        this.recordRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$uFpJnI1PgKJOWjjkzLELfUs2CBE
            @Override // com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
            public final void onLoadMore(RefreshLayout refreshLayout) {
                LotteryDialog.this.lambda$initListener$0$LotteryDialog(refreshLayout);
            }
        });
        this.tvLuckyValue.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$FCfk0jpL2inlOepKysgAilMjwFs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryDialog.this.lambda$initListener$1$LotteryDialog(view2);
            }
        });
        this.tvPrice.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$9VXUfJ40zlfDkaVB8MIy8ZJz7oA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryDialog.this.lambda$initListener$3$LotteryDialog(view2);
            }
        });
        view.findViewById(R$id.iv_notice_bell).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$tV9dAS4ZhOV2AsJQWJtaxBh3NWI
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryDialog.this.lambda$initListener$4$LotteryDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LotteryDialog(RefreshLayout refreshLayout) {
        this.pageNum++;
        sendRecordRequest(this.recordDateStr, false);
    }

    public /* synthetic */ void lambda$initListener$1$LotteryDialog(View view) {
        LotteryLuckyValueTipsDialog.newInstance().show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$initListener$3$LotteryDialog(View view) {
        if (!isRestrictionUser()) {
            return;
        }
        SureCancelDialog.newInstance(getString(R$string.fq_text_whether_recharge), new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$7lWlNhQQcCUvfjkOLwRJlsUzpLU
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryDialog.this.lambda$null$2$LotteryDialog(view2);
            }
        }).show(getChildFragmentManager());
    }

    public /* synthetic */ void lambda$null$2$LotteryDialog(View view) {
        AppUtils.onRechargeListener(this.mContext);
        LogEventUtils.uploadRechargeClick(getString(R$string.fq_lottery_recharge_entrance));
    }

    public /* synthetic */ void lambda$initListener$4$LotteryDialog(View view) {
        if (SPUtils.getInstance().getBoolean(ConstantUtils.SHOW_LOTTERY_NOTICE_DOT_TIP, true)) {
            this.ivNoticeDot.setVisibility(4);
            SPUtils.getInstance().put(ConstantUtils.SHOW_LOTTERY_NOTICE_DOT_TIP, false);
        }
        LotteryNoticeCenterDialog.newInstance().show(getChildFragmentManager());
    }

    public void loadBoomAnim() {
        SvgaUtils.playHotLoadRes(ResHotLoadManager.getInstance().getResHotLoadPath(ResHotLoadManager.BOOM_ANIM_RES), this.svLuckAnim, this.svgaParser, this.simpleSVGACallBack);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public double getHeightScale() {
        return (this.mHeightPixels == 1280 && this.mWidthPixels == 720) ? 0.82d : 0.75d;
    }

    public OnLotteryDialogCallback getOnLotteryDialogCallback() {
        return this.onLotteryDialogCallback;
    }

    public void setOnLotteryDialogCallback(OnLotteryDialogCallback onLotteryDialogCallback) {
        this.onLotteryDialogCallback = onLotteryDialogCallback;
    }

    public void onRelease() {
        this.generalBoomView.onRelease();
        this.luxuryBoomView.onRelease();
        this.generalLuckPanView.onRelease();
        this.luxuryLuckPanView.onRelease();
        this.marqueeViewNotice.stopFlipping();
        SPUtils.getInstance().put(ConstantUtils.LOAD_GENERAL_BOOM_ANIM, false);
        SPUtils.getInstance().put(ConstantUtils.LOAD_LUXURY_BOOM_ANIM, false);
    }

    private void initMorePopDialog() {
        this.morePopDialog = new MorePopDialog(getContext());
        this.morePopDialog.setPopupGravity(BasePopupWindow.GravityMode.ALIGN_TO_ANCHOR_SIDE, 5).setBackground(0);
        this.morePopDialog.findViewById(R$id.tv_plate_play).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$lirkO0dI84k7ywAvmtaIBnin4Io
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LotteryDialog.this.lambda$initMorePopDialog$5$LotteryDialog(view);
            }
        });
        this.morePopDialog.findViewById(R$id.tv_lottery_record).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$giyFc5qvztUVKnziPElsHh9D5Jk
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                LotteryDialog.this.lambda$initMorePopDialog$6$LotteryDialog(view);
            }
        });
    }

    public /* synthetic */ void lambda$initMorePopDialog$5$LotteryDialog(View view) {
        TomatoLiveSDK.getSingleton().statisticsReport("ZP_more_desc");
        this.flDialogBg.setBackgroundResource(R$drawable.fq_shape_lottery_dialog_bg);
        this.ivFullStars.setVisibility(4);
        this.morePopDialog.dismiss();
        this.tvMoreTitle.setText(R$string.fq_lottery_plate_play_desc);
        initContentView(3);
        this.webView.loadUrl(AppUtils.getApiURl() + "static/turntableIntroduce.htm");
    }

    public /* synthetic */ void lambda$initMorePopDialog$6$LotteryDialog(View view) {
        if (!isRestrictionUser()) {
            return;
        }
        TomatoLiveSDK.getSingleton().statisticsReport("ZP_more_record");
        this.flDialogBg.setBackgroundResource(R$drawable.fq_shape_lottery_dialog_bg);
        this.ivFullStars.setVisibility(4);
        this.morePopDialog.dismiss();
        this.tvMoreTitle.setText(R$string.fq_lottery_record);
        initContentView(4);
        if (TextUtils.isEmpty(this.recordDateStr)) {
            this.recordDateStr = DateUtils.getCurrentDateTime("yyyy-MM-dd");
        }
        sendRecordRequest(this.recordDateStr, true);
    }

    private void initAnchorAdapter() {
        this.emptyViewAnchor = new LotteryTopEmptyView(this.mContext, 43);
        this.anchorTopHeadView = new LotteryAnchorTopHeadView(this.mContext);
        this.anchorAdapter = new LotteryTopAnchorAdapter(R$layout.fq_item_list_lottery_top_anchor);
        this.recyclerViewAnchor.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recyclerViewAnchor.setAdapter(this.anchorAdapter);
        this.anchorAdapter.bindToRecyclerView(this.recyclerViewAnchor);
        this.anchorAdapter.addHeaderView(this.anchorTopHeadView);
        this.anchorAdapter.setEmptyView(this.emptyViewAnchor);
        this.emptyViewAnchor.setOnEventListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.22
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LotteryDialog.this.tvTopAnchor.setSelected(true);
                LotteryDialog.this.tvTopUser.setSelected(false);
                LotteryDialog.this.initContentView(1);
            }
        });
        this.anchorTopHeadView.setOnAvatarClickListener(new LotteryAnchorTopHeadView.OnAvatarClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$8gCbZXNclolQMhDfdCcq4eXUkUI
            @Override // com.tomatolive.library.p136ui.view.headview.LotteryAnchorTopHeadView.OnAvatarClickListener
            public final void onAvatarClick(AnchorEntity anchorEntity) {
                LotteryDialog.this.lambda$initAnchorAdapter$7$LotteryDialog(anchorEntity);
            }
        });
        this.anchorAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$9QMZTJ6OQfPwMWkS2wV8Yz6CnFA
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
            public final void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                LotteryDialog.this.lambda$initAnchorAdapter$8$LotteryDialog(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$initAnchorAdapter$7$LotteryDialog(AnchorEntity anchorEntity) {
        if (anchorEntity == null || TextUtils.isEmpty(anchorEntity.nickname) || this.onLotteryDialogCallback == null) {
            return;
        }
        dismiss();
        this.onLotteryDialogCallback.onClickAnchorAvatarListener(anchorEntity);
    }

    public /* synthetic */ void lambda$initAnchorAdapter$8$LotteryDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        if (anchorEntity == null || TextUtils.isEmpty(anchorEntity.nickname) || this.onLotteryDialogCallback == null) {
            return;
        }
        dismiss();
        this.onLotteryDialogCallback.onClickAnchorAvatarListener(anchorEntity);
    }

    private void initUserAdapter() {
        this.emptyViewUser = new LotteryTopEmptyView(this.mContext, 44);
        this.userAdapter = new LotteryTopUserAdapter(R$layout.fq_item_list_lottery_top_user);
        this.recyclerViewUser.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recyclerViewUser.setAdapter(this.userAdapter);
        this.userAdapter.bindToRecyclerView(this.recyclerViewUser);
        this.userAdapter.setEmptyView(this.emptyViewUser);
        this.emptyViewUser.setOnEventListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.23
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                LotteryDialog.this.tvTopAnchor.setSelected(true);
                LotteryDialog.this.tvTopUser.setSelected(false);
                LotteryDialog.this.initContentView(1);
            }
        });
        this.userAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$kkCspIiA2KyxL_JyxAzuphUIBZY
            @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemChildClickListener
            public final void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                LotteryDialog.this.lambda$initUserAdapter$9$LotteryDialog(baseQuickAdapter, view, i);
            }
        });
    }

    public /* synthetic */ void lambda$initUserAdapter$9$LotteryDialog(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        OnLotteryDialogCallback onLotteryDialogCallback;
        AnchorEntity anchorEntity = (AnchorEntity) baseQuickAdapter.getItem(i);
        if (anchorEntity == null) {
            return;
        }
        if ((view.getId() != R$id.iv_avatar && view.getId() != R$id.tv_anchor_name) || (onLotteryDialogCallback = this.onLotteryDialogCallback) == null) {
            return;
        }
        onLotteryDialogCallback.onClickUserAvatarListener(AppUtils.formatUserEntity(anchorEntity));
    }

    private void initRecordAdapter() {
        this.recordHeadView = new LotteryRecordHeadView(this.mContext);
        this.recordAdapter = new LotteryRecordAdapter(R$layout.fq_item_list_lottery_record);
        this.recyclerViewRecord.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.recyclerViewRecord.addItemDecoration(new RVDividerLotteryRecord(this.mContext));
        this.recyclerViewRecord.setAdapter(this.recordAdapter);
        this.recordAdapter.bindToRecyclerView(this.recyclerViewRecord);
        this.recordAdapter.setEmptyView(new RecyclerIncomeEmptyView(this.mContext, 45));
        this.recordAdapter.addHeaderView(this.recordHeadView);
        this.recordAdapter.setHeaderAndEmpty(true);
        this.recordHeadView.setOnDateSelectedListener(new LotteryRecordHeadView.OnDateSelectedListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$N8BuNAs9xXgeucxMeindQYd2eXI
            @Override // com.tomatolive.library.p136ui.view.headview.LotteryRecordHeadView.OnDateSelectedListener
            public final void onDateSelected(Date date) {
                LotteryDialog.this.lambda$initRecordAdapter$10$LotteryDialog(date);
            }
        });
    }

    public /* synthetic */ void lambda$initRecordAdapter$10$LotteryDialog(Date date) {
        this.pageNum = 1;
        this.recordDateStr = DateUtils.dateToString(date, "yyyy-MM-dd");
        sendRecordRequest(this.recordDateStr, true);
    }

    private void initWebView() {
        this.webView.setWebViewClient(new Html5WebViewClient());
        this.webView.setBackgroundColor(0);
        this.webView.getBackground().setAlpha(0);
        this.webView.setHorizontalScrollBarEnabled(false);
        this.webView.setVerticalScrollBarEnabled(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initRouletteTagView(boolean z) {
        this.isLuxury = z;
        this.tvGeneralRoulette.setSelected(!z);
        this.tvLuxuryRoulette.setSelected(z);
        int i = 0;
        this.generalLuckPanView.setVisibility(!z ? 0 : 4);
        LuckPanView luckPanView = this.luxuryLuckPanView;
        if (!z) {
            i = 4;
        }
        luckPanView.setVisibility(i);
        updateLuckyValue();
        updateTicketTypeViewSelected();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBoomBgViewVisibility(boolean z) {
        this.flDialogBg.setBackgroundResource(z ? R$drawable.fq_shape_lottery_dialog_full_bg : R$drawable.fq_shape_lottery_dialog_bg);
        this.ivFullStars.setVisibility(z ? 0 : 4);
        this.lotteryTicketBtnView1.initTicketBtnBg(z);
        this.lotteryTicketBtnView10.initTicketBtnBg(z);
        this.lotteryTicketBtnView100.initTicketBtnBg(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initBoomViewVisibility() {
        int i = 0;
        this.generalBoomView.setVisibility((this.isLuxury || !isGeneralBoomStatus()) ? 4 : 0);
        LotteryBoomView lotteryBoomView = this.luxuryBoomView;
        if (!this.isLuxury || !isLuxuryBoomStatus()) {
            i = 4;
        }
        lotteryBoomView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isGeneralBoomStatus() {
        return this.generalProgress >= 400;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isLuxuryBoomStatus() {
        return this.luxuryProgress >= 400;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateLuckyValue() {
        int i = this.isLuxury ? this.luxuryProgress : this.generalProgress;
        if (i > 400) {
            i = MAX_VALUE;
        }
        this.tvLuckyValue.setText(getString(R$string.fq_lottery_lucky_value, String.valueOf(i), String.valueOf((int) MAX_VALUE)));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initMarqueeViewNotice() {
        this.marqueeFactory.setData(this.marqueeList);
        this.marqueeViewNotice.startFlipping();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAgainTurntableRequest() {
        this.isLoadingGeneralSuccess = false;
        this.isGeneralLoading = false;
        sendGeneralRequest(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAgainLuxuryTurntableRequest() {
        this.isLoadingLuxurySuccess = false;
        this.isLuxuryLoading = false;
        sendLuxuryTurntableRequest(false);
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"CheckResult"})
    public void sendGeneralRequest(boolean z) {
        if (this.isLoadingGeneralSuccess) {
            showRotateContentView();
        } else if (z) {
            Observable.just(true).map(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$ZmyhhENBFot2BY4gdS6uszRcmyU
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo6755apply(Object obj) {
                    return LotteryDialog.this.lambda$sendGeneralRequest$11$LotteryDialog((Boolean) obj);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LotteryTurntableInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.24
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    if (LotteryDialog.this.isGeneralLoading) {
                        LotteryDialog.this.showRotateLoadingView();
                    }
                }

                @Override // io.reactivex.Observer
                public void onNext(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) {
                    Map<String, LotteryPrizeEntity> map;
                    if (lotteryTurntableInfoEntity != null && (map = lotteryTurntableInfoEntity.turntableAward) != null && map.size() > 0) {
                        LotteryDialog.this.isGeneralLoading = false;
                        LotteryDialog.this.localGeneralVersion = lotteryTurntableInfoEntity.version;
                        LotteryDialog.this.dealGeneralTurntableLocalInfo(lotteryTurntableInfoEntity);
                        LotteryDialog.this.isLoadingGeneralSuccess = true;
                        LotteryDialog.this.showRotateContentView();
                    }
                    LotteryDialog.this.getGeneralInfo();
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    LotteryDialog.this.getGeneralInfo();
                }
            });
        } else {
            getGeneralInfo();
        }
    }

    public /* synthetic */ LotteryTurntableInfoEntity lambda$sendGeneralRequest$11$LotteryDialog(Boolean bool) throws Exception {
        return getLocalCacheTurntableInfo(false, ConstantUtils.GENERAL_TURNTABLE_KEY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getGeneralInfo() {
        ApiRetrofit.getInstance().getApiService().getTurntableAwardInfoService(new RequestParams().getTurntableAwardInfoParams(this.liveId, false)).map(new ServerResultFunction<LotteryTurntableInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.26
        }).flatMap(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$URVcTbfZ3jY-QtR8GujFFA1GzUk
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return LotteryDialog.this.lambda$getGeneralInfo$12$LotteryDialog((LotteryTurntableInfoEntity) obj);
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<LotteryTurntableInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.25
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                if (LotteryDialog.this.isGeneralLoading) {
                    LotteryDialog.this.showRotateLoadingView();
                }
            }

            @Override // io.reactivex.Observer
            public void onNext(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) {
                if (lotteryTurntableInfoEntity == null) {
                    LotteryDialog.this.isLoadingGeneralSuccess = false;
                    if (!LotteryDialog.this.isGeneralLoading) {
                        return;
                    }
                    LotteryDialog.this.showRotateFailView();
                    return;
                }
                LotteryDialog.this.dealGeneralTurntableLocalInfo(lotteryTurntableInfoEntity);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                th.printStackTrace();
                LotteryDialog.this.isLoadingGeneralSuccess = false;
                if (LotteryDialog.this.isGeneralLoading) {
                    LotteryDialog.this.showRotateFailView();
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                LotteryDialog.this.isLoadingGeneralSuccess = true;
                LotteryDialog.this.showRotateContentView();
            }
        });
    }

    public /* synthetic */ ObservableSource lambda$getGeneralInfo$12$LotteryDialog(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) throws Exception {
        return Observable.just(putLocalCacheTurntableInfo(false, ConstantUtils.GENERAL_TURNTABLE_KEY, lotteryTurntableInfoEntity));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealGeneralTurntableLocalInfo(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) {
        this.generalVersion = lotteryTurntableInfoEntity.version;
        TurntableMode turntableMode = lotteryTurntableInfoEntity.turntableInfoData;
        LotteryBoomDetailEntity lotteryBoomDetailEntity = lotteryTurntableInfoEntity.boomDetail;
        int i = lotteryTurntableInfoEntity.luckValue;
        this.generalProgress = i;
        if (i == 0) {
            this.generalProgress = i;
        } else {
            this.generalProgress = Math.max(this.generalProgress, i);
        }
        this.generalLuckPanView.setConfig(new LuckPanView.Builder().setmColors(turntableMode.bgColors).setmNum(turntableMode.prizeNum).setmName(turntableMode.prizeNames).setmIcons(turntableMode.bitmaps).setmTypeNum(turntableMode.bgColors.length).setCodeList(turntableMode.codes).setBoomStatus(lotteryBoomDetailEntity == null ? -1 : lotteryBoomDetailEntity.boomStatus).setBoomMultiple(lotteryBoomDetailEntity == null ? 0 : lotteryBoomDetailEntity.boomMultiple).build());
        this.generalLuckPanView.setProgress(this.generalProgress);
        this.generalBoomView.initData(lotteryTurntableInfoEntity.boomDetail);
        updateLuckyValue();
        if (lotteryTurntableInfoEntity.isBoomStatus()) {
            this.isGeneralBooming = true;
            this.generalProgress = MAX_VALUE;
            this.generalLuckPanView.setProgress(this.generalProgress);
            this.generalLuckPanView.startBoom();
            this.generalBoomView.setVisibility(4);
            this.generalBoomView.showBoomCountDown();
            if (this.isLuxury) {
                return;
            }
            initBoomBgViewVisibility(isGeneralBoomStatus());
            if (SPUtils.getInstance().getBoolean(ConstantUtils.LOAD_GENERAL_BOOM_ANIM) || lotteryBoomDetailEntity.boomRemainTime < 5) {
                initBoomViewVisibility();
                return;
            }
            SPUtils.getInstance().put(ConstantUtils.LOAD_GENERAL_BOOM_ANIM, true);
            loadBoomAnim();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    @SuppressLint({"CheckResult"})
    public void sendLuxuryTurntableRequest(boolean z) {
        if (this.isLoadingLuxurySuccess) {
            showRotateContentView();
        } else if (z) {
            Observable.just(true).map(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$Hd7-XElHYAvUpVSFE2qfQtfk7tk
                @Override // io.reactivex.functions.Function
                /* renamed from: apply */
                public final Object mo6755apply(Object obj) {
                    return LotteryDialog.this.lambda$sendLuxuryTurntableRequest$13$LotteryDialog((Boolean) obj);
                }
            }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LotteryTurntableInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.27
                @Override // io.reactivex.Observer
                public void onComplete() {
                }

                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    if (LotteryDialog.this.isLuxuryLoading) {
                        LotteryDialog.this.showRotateLoadingView();
                    }
                }

                @Override // io.reactivex.Observer
                public void onNext(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) {
                    Map<String, LotteryPrizeEntity> map;
                    if (lotteryTurntableInfoEntity != null && (map = lotteryTurntableInfoEntity.turntableAward) != null && map.size() > 0) {
                        LotteryDialog.this.isLuxuryLoading = false;
                        LotteryDialog.this.localLuxuryVersion = lotteryTurntableInfoEntity.version;
                        LotteryDialog.this.dealLuxuryTurntableInfo(lotteryTurntableInfoEntity);
                        LotteryDialog.this.isLoadingLuxurySuccess = true;
                        LotteryDialog.this.showRotateContentView();
                    }
                    LotteryDialog.this.getLuxuryInfo();
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    LotteryDialog.this.getLuxuryInfo();
                }
            });
        } else {
            getLuxuryInfo();
        }
    }

    public /* synthetic */ LotteryTurntableInfoEntity lambda$sendLuxuryTurntableRequest$13$LotteryDialog(Boolean bool) throws Exception {
        return getLocalCacheTurntableInfo(true, ConstantUtils.LUXURY_TURNTABLE_KEY);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void getLuxuryInfo() {
        ApiRetrofit.getInstance().getApiService().getTurntableAwardInfoService(new RequestParams().getTurntableAwardInfoParams(this.liveId, true)).map(new ServerResultFunction<LotteryTurntableInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.29
        }).flatMap(new Function() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryDialog$ZxT66JyaIQaphyZZKo9xpkwduwQ
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return LotteryDialog.this.lambda$getLuxuryInfo$14$LotteryDialog((LotteryTurntableInfoEntity) obj);
            }
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<LotteryTurntableInfoEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.28
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                if (LotteryDialog.this.isLuxuryLoading) {
                    LotteryDialog.this.showRotateLoadingView();
                }
            }

            @Override // io.reactivex.Observer
            public void onNext(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) {
                if (lotteryTurntableInfoEntity == null) {
                    LotteryDialog.this.isLoadingLuxurySuccess = false;
                    if (!LotteryDialog.this.isLuxuryLoading) {
                        return;
                    }
                    LotteryDialog.this.showRotateFailView();
                    return;
                }
                LotteryDialog.this.dealLuxuryTurntableInfo(lotteryTurntableInfoEntity);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                th.printStackTrace();
                LotteryDialog.this.isLoadingLuxurySuccess = false;
                if (LotteryDialog.this.isLuxuryLoading) {
                    LotteryDialog.this.showRotateFailView();
                }
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                LotteryDialog.this.isLoadingLuxurySuccess = true;
                LotteryDialog.this.showRotateContentView();
            }
        });
    }

    public /* synthetic */ ObservableSource lambda$getLuxuryInfo$14$LotteryDialog(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) throws Exception {
        return Observable.just(putLocalCacheTurntableInfo(true, ConstantUtils.LUXURY_TURNTABLE_KEY, lotteryTurntableInfoEntity));
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dealLuxuryTurntableInfo(LotteryTurntableInfoEntity lotteryTurntableInfoEntity) {
        TurntableMode turntableMode = lotteryTurntableInfoEntity.turntableInfoData;
        LotteryBoomDetailEntity lotteryBoomDetailEntity = lotteryTurntableInfoEntity.boomDetail;
        int i = lotteryTurntableInfoEntity.luckValue;
        if (i == 0) {
            this.luxuryProgress = i;
        } else {
            this.luxuryProgress = Math.max(this.luxuryProgress, i);
        }
        this.luxuryVersion = lotteryTurntableInfoEntity.version;
        this.luxuryLuckPanView.setConfig(new LuckPanView.Builder().setmColors(turntableMode.bgColors).setmNum(turntableMode.prizeNum).setmName(turntableMode.prizeNames).setmIcons(turntableMode.bitmaps).setmTypeNum(turntableMode.bgColors.length).setCodeList(turntableMode.codes).setBoomStatus(lotteryBoomDetailEntity == null ? -1 : lotteryBoomDetailEntity.boomStatus).setBoomMultiple(lotteryBoomDetailEntity == null ? 0 : lotteryBoomDetailEntity.boomMultiple).build());
        this.luxuryLuckPanView.setProgress(this.luxuryProgress);
        this.luxuryBoomView.initData(lotteryTurntableInfoEntity.boomDetail);
        updateLuckyValue();
        if (lotteryTurntableInfoEntity.isBoomStatus()) {
            this.luxuryProgress = MAX_VALUE;
            this.luxuryLuckPanView.setProgress(this.luxuryProgress);
            this.luxuryLuckPanView.startBoom();
            this.luxuryBoomView.setVisibility(4);
            this.luxuryBoomView.showBoomCountDown();
            if (!this.isLuxury) {
                return;
            }
            initBoomBgViewVisibility(isLuxuryBoomStatus());
            if (SPUtils.getInstance().getBoolean(ConstantUtils.LOAD_LUXURY_BOOM_ANIM) || lotteryBoomDetailEntity.boomRemainTime < 5) {
                initBoomViewVisibility();
                return;
            }
            SPUtils.getInstance().put(ConstantUtils.LOAD_LUXURY_BOOM_ANIM, true);
            loadBoomAnim();
        }
    }

    public void executeBoom(boolean z) {
        if (z) {
            if (this.isLuxuryBooming) {
                return;
            }
            this.isLuxuryBooming = true;
            SPUtils.getInstance().put(ConstantUtils.LOAD_LUXURY_BOOM_ANIM, false);
            sendAgainLuxuryTurntableRequest();
        } else if (this.isGeneralBooming) {
        } else {
            this.isGeneralBooming = true;
            SPUtils.getInstance().put(ConstantUtils.LOAD_GENERAL_BOOM_ANIM, false);
            sendAgainTurntableRequest();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendAnchorTopRequest() {
        ApiRetrofit.getInstance().getApiService().getTurntableAnchorRankService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.31
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<AnchorEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.30
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                LotteryDialog.this.tvLoadingText.setVisibility(0);
                LotteryDialog.this.llTopAnchorBg.setVisibility(4);
                LotteryDialog.this.recyclerViewUser.setVisibility(4);
            }

            @Override // io.reactivex.Observer
            public void onNext(List<AnchorEntity> list) {
                if (list == null) {
                    return;
                }
                LotteryDialog.this.anchorTopHeadView.initData(list);
                if (!list.isEmpty() && list.size() <= 3) {
                    LotteryDialog.this.anchorAdapter.setNewData(LotteryDialog.this.getEmptyAnchorList());
                } else if (list.size() <= 3) {
                } else {
                    LotteryDialog.this.anchorAdapter.setNewData(list.subList(3, list.size()));
                }
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                LotteryDialog.this.tvLoadingText.setVisibility(4);
                LotteryDialog lotteryDialog = LotteryDialog.this;
                lotteryDialog.loadTopContentView(lotteryDialog.tvTopAnchor.isSelected());
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                LotteryDialog.this.tvLoadingText.setVisibility(4);
                LotteryDialog lotteryDialog = LotteryDialog.this;
                lotteryDialog.loadTopContentView(lotteryDialog.tvTopAnchor.isSelected());
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void sendUserTopRequest() {
        ApiRetrofit.getInstance().getApiService().getTurntableUserRankService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<List<LotteryUserRankEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.33
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<List<LotteryUserRankEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.32
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                LotteryDialog.this.tvLoadingText.setVisibility(0);
                LotteryDialog.this.llTopAnchorBg.setVisibility(4);
                LotteryDialog.this.recyclerViewUser.setVisibility(4);
            }

            @Override // io.reactivex.Observer
            public void onNext(List<LotteryUserRankEntity> list) {
                if (list == null) {
                    return;
                }
                LotteryDialog.this.userAdapter.setNewData(list);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                LotteryDialog.this.tvLoadingText.setVisibility(4);
                LotteryDialog lotteryDialog = LotteryDialog.this;
                lotteryDialog.loadTopContentView(lotteryDialog.tvTopAnchor.isSelected());
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                LotteryDialog.this.tvLoadingText.setVisibility(4);
                LotteryDialog lotteryDialog = LotteryDialog.this;
                lotteryDialog.loadTopContentView(lotteryDialog.tvTopAnchor.isSelected());
            }
        });
    }

    private void sendRecordRequest(String str, final boolean z) {
        ApiRetrofit.getInstance().getApiService().getLotteryDrawRecordService(new RequestParams().getTurntableDrawRecordParams(this.pageNum, str)).map(new ServerResultFunction<HttpResultPageModel<LotteryRecordEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.35
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindToLifecycle()).subscribe(new Observer<HttpResultPageModel<LotteryRecordEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.34
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                if (z) {
                    LotteryDialog.this.tvLoadingText.setVisibility(0);
                    LotteryDialog.this.recordRefreshLayout.setVisibility(4);
                    LotteryDialog.this.webView.setVisibility(4);
                }
            }

            @Override // io.reactivex.Observer
            public void onNext(HttpResultPageModel<LotteryRecordEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                if (z) {
                    LotteryDialog.this.recordAdapter.replaceData(httpResultPageModel.dataList);
                } else {
                    LotteryDialog.this.recordAdapter.addData((Collection) httpResultPageModel.dataList);
                }
                AppUtils.updateRefreshLayoutFinishStatus(LotteryDialog.this.recordRefreshLayout, httpResultPageModel.isMorePage(), z);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                if (!z) {
                    LotteryDialog.this.recordRefreshLayout.finishLoadMore();
                    return;
                }
                LotteryDialog.this.tvLoadingText.setVisibility(4);
                LotteryDialog.this.recordRefreshLayout.setVisibility(0);
                LotteryDialog.this.webView.setVisibility(4);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                if (!z) {
                    return;
                }
                LotteryDialog.this.tvLoadingText.setVisibility(4);
                LotteryDialog.this.recordRefreshLayout.setVisibility(0);
                LotteryDialog.this.webView.setVisibility(4);
            }
        });
    }

    private void sendLuckValueRequest() {
        ApiRetrofit.getInstance().getApiService().getLuckValueService(new RequestParams().getTurntableLuckValueList(this.liveId)).map(new ServerResultFunction<LuckValueEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.38
        }).onErrorResumeNext(new HttpResultFunction()).delay(10L, TimeUnit.SECONDS).repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.37
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public ObservableSource<?> mo6755apply(Observable<Object> observable) throws Exception {
                return observable.flatMap(new Function<Object, ObservableSource<?>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.37.1
                    /* JADX WARN: Can't rename method to resolve collision */
                    @Override // io.reactivex.functions.Function
                    /* renamed from: apply */
                    public ObservableSource<?> mo6755apply(Object obj) throws Exception {
                        return Observable.just(1).delay(10L, TimeUnit.SECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindUntilEvent(FragmentEvent.PAUSE)).subscribe(new SimpleRxObserver<LuckValueEntity>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.36
            @Override // com.tomatolive.library.utils.live.SimpleRxObserver
            public void accept(LuckValueEntity luckValueEntity) {
                if (luckValueEntity == null) {
                    return;
                }
                int i = luckValueEntity.normalLuckValue;
                if (i == 0) {
                    LotteryDialog.this.generalProgress = i;
                } else {
                    LotteryDialog lotteryDialog = LotteryDialog.this;
                    lotteryDialog.generalProgress = Math.max(lotteryDialog.generalProgress, luckValueEntity.normalLuckValue);
                }
                int i2 = luckValueEntity.richLuckValue;
                if (i2 == 0) {
                    LotteryDialog.this.luxuryProgress = i2;
                } else {
                    LotteryDialog lotteryDialog2 = LotteryDialog.this;
                    lotteryDialog2.luxuryProgress = Math.max(lotteryDialog2.luxuryProgress, luckValueEntity.richLuckValue);
                }
                LotteryDialog.this.luxuryLuckPanView.setProgress(LotteryDialog.this.luxuryProgress);
                LotteryDialog.this.generalLuckPanView.setProgress(LotteryDialog.this.generalProgress);
                LotteryDialog.this.updateLuckyValue();
            }

            @Override // com.tomatolive.library.utils.live.SimpleRxObserver, io.reactivex.Observer
            public void onError(Throwable th) {
                super.onError(th);
            }
        });
    }

    private void sendLuckReportRequest() {
        ApiRetrofit.getInstance().getApiService().getTurntableLuckReportService(new RequestParams().getDefaultParams()).map(new ServerResultFunction<HttpResultPageModel<LotteryLuckReportEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.40
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindUntilEvent(FragmentEvent.PAUSE)).subscribe(new Observer<HttpResultPageModel<LotteryLuckReportEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.LotteryDialog.39
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
            }

            @Override // io.reactivex.Observer
            public void onNext(HttpResultPageModel<LotteryLuckReportEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                List<LotteryLuckReportEntity> list = httpResultPageModel.dataList;
                if (LotteryDialog.this.marqueeViewNotice.isFlipping()) {
                    LotteryDialog.this.marqueeViewNotice.stopFlipping();
                }
                if (list == null || list.size() <= 0) {
                    LotteryDialog.this.marqueeViewRoot.setVisibility(4);
                    return;
                }
                LotteryDialog.this.marqueeViewRoot.setVisibility(0);
                LotteryDialog.this.marqueeList.clear();
                LotteryDialog.this.marqueeList.addAll(list);
                LotteryDialog.this.initMarqueeViewNotice();
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                th.printStackTrace();
                LotteryDialog.this.marqueeViewRoot.setVisibility(4);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateTicketTypeValue(int i) {
        if (this.isLuxury) {
            this.luxuryTicketType = i;
        } else {
            this.generalTicketType = i;
        }
        updateTicketTypeViewSelected();
    }

    private void updateTicketTypeViewSelected() {
        String formatTicketTips;
        int ticketTypeValue = getTicketTypeValue();
        this.lotteryTicketBtnView1.setItemSelected(ticketTypeValue == 1);
        this.lotteryTicketBtnView10.setItemSelected(ticketTypeValue == 10);
        this.lotteryTicketBtnView100.setItemSelected(ticketTypeValue == 100);
        String string = getString(R$string.fq_lottery_use_tips);
        if (ticketTypeValue == 1) {
            formatTicketTips = this.isLuxury ? formatTicketTips(3) : formatTicketTips(0);
        } else if (ticketTypeValue == 10) {
            formatTicketTips = this.isLuxury ? formatTicketTips(4) : formatTicketTips(1);
        } else {
            if (ticketTypeValue == 100) {
                formatTicketTips = formatTicketTips(this.isLuxury ? 5 : 2);
            }
            this.tvTicketTips.setText(string);
        }
        string = formatTicketTips;
        this.tvTicketTips.setText(string);
    }

    private String formatTicketTips(int i) {
        return String.format(getResources().getStringArray(R$array.fq_lottery_ticket_tips)[i], formatTipsPrice(getResources().getStringArray(R$array.fq_lottery_ticket_gold)[i]));
    }

    private int getTicketTypeValue() {
        return this.isLuxury ? this.luxuryTicketType : this.generalTicketType;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initContentView(int i) {
        this.contentType = i;
        int i2 = 0;
        this.ivBack.setVisibility(i == 1 ? 4 : 0);
        this.ivLotteryTopLuck.setVisibility(i == 1 ? 0 : 4);
        this.llLotteryContentBg.setVisibility(i == 1 ? 0 : 4);
        this.llTopContentBg.setVisibility(i == 2 ? 0 : 4);
        LinearLayout linearLayout = this.llMoreContentBg;
        if (i != 3 && i != 4) {
            i2 = 4;
        }
        linearLayout.setVisibility(i2);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initTopContentView(boolean z) {
        this.tvTopAnchor.setSelected(z);
        this.tvTopUser.setSelected(!z);
        this.tvTopTips.setText(z ? R$string.fq_lottery_anchor_top_tips : R$string.fq_lottery_user_top_tips);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadTopContentView(boolean z) {
        int i = 0;
        this.llTopAnchorBg.setVisibility(z ? 0 : 4);
        RecyclerView recyclerView = this.recyclerViewUser;
        if (z) {
            i = 4;
        }
        recyclerView.setVisibility(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRotateLoadingView() {
        this.tvLoadingText.setVisibility(0);
        if (this.llLotteryContentBg.getVisibility() == 0) {
            this.flRotateContentView.setVisibility(4);
            this.llTicketBtnContentBg.setVisibility(4);
            this.tvLuckyValue.setVisibility(4);
            return;
        }
        this.llLotteryContentBg.setVisibility(4);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRotateContentView() {
        int i = 4;
        this.tvLoadingText.setVisibility(4);
        this.llLoadingFailBg.setVisibility(4);
        if (this.llLotteryContentBg.getVisibility() != 0) {
            LinearLayout linearLayout = this.llLotteryContentBg;
            if (this.contentType == 1) {
                i = 0;
            }
            linearLayout.setVisibility(i);
        }
        if (this.flRotateContentView.getVisibility() != 0) {
            this.flRotateContentView.setVisibility(0);
            this.llTicketBtnContentBg.setVisibility(0);
            this.tvLuckyValue.setVisibility(0);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showRotateFailView() {
        this.tvLoadingText.setVisibility(4);
        this.llLoadingFailBg.setVisibility(0);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public List<AnchorEntity> getEmptyAnchorList() {
        ArrayList arrayList = new ArrayList();
        AnchorEntity anchorEntity = new AnchorEntity();
        anchorEntity.nickname = "";
        arrayList.add(anchorEntity);
        return arrayList;
    }

    public TurntableMode getTurntableDataModel(boolean z, Map<String, LotteryPrizeEntity> map) {
        TurntableMode turntableMode = new TurntableMode();
        if (map == null) {
            return null;
        }
        Integer[] numArr = new Integer[9];
        String[] strArr = new String[9];
        String[] strArr2 = new String[9];
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        if (z) {
            numArr[0] = getItemBgColor(0, map);
            strArr[0] = getItemPrizeNum(0, map);
            strArr2[0] = getItemPrizeName(0, map);
            arrayList.add(getItemBitmap(0, map));
            arrayList2.add(0);
            numArr[1] = getItemBgColor(5, map);
            strArr[1] = getItemPrizeNum(5, map);
            strArr2[1] = getItemPrizeName(5, map);
            arrayList.add(getItemBitmap(5, map));
            arrayList2.add(5);
            numArr[2] = getItemBgColor(2, map);
            strArr[2] = getItemPrizeNum(2, map);
            strArr2[2] = getItemPrizeName(2, map);
            arrayList.add(getItemBitmap(2, map));
            arrayList2.add(2);
            numArr[3] = getItemBgColor(7, map);
            strArr[3] = getItemPrizeNum(7, map);
            strArr2[3] = getItemPrizeName(7, map);
            arrayList.add(getItemBitmap(7, map));
            arrayList2.add(7);
            numArr[4] = getItemBgColor(4, map);
            strArr[4] = getItemPrizeNum(4, map);
            strArr2[4] = getItemPrizeName(4, map);
            arrayList.add(getItemBitmap(4, map));
            arrayList2.add(4);
            numArr[5] = getItemBgColor(1, map);
            strArr[5] = getItemPrizeNum(1, map);
            strArr2[5] = getItemPrizeName(1, map);
            arrayList.add(getItemBitmap(1, map));
            arrayList2.add(1);
            numArr[6] = getItemBgColor(8, map);
            strArr[6] = getItemPrizeNum(8, map);
            strArr2[6] = getItemPrizeName(8, map);
            arrayList.add(getItemBitmap(8, map));
            arrayList2.add(8);
            numArr[7] = getItemBgColor(3, map);
            strArr[7] = getItemPrizeNum(3, map);
            strArr2[7] = getItemPrizeName(3, map);
            arrayList.add(getItemBitmap(3, map));
            arrayList2.add(3);
            numArr[8] = getItemBgColor(6, map);
            strArr[8] = getItemPrizeNum(6, map);
            strArr2[8] = getItemPrizeName(6, map);
            arrayList.add(getItemBitmap(6, map));
            arrayList2.add(6);
        } else {
            numArr[0] = getItemBgColor(0, map);
            strArr[0] = getItemPrizeNum(0, map);
            strArr2[0] = getItemPrizeName(0, map);
            arrayList.add(getItemBitmap(0, map));
            arrayList2.add(0);
            numArr[1] = getItemBgColor(7, map);
            strArr[1] = getItemPrizeNum(7, map);
            strArr2[1] = getItemPrizeName(7, map);
            arrayList.add(getItemBitmap(7, map));
            arrayList2.add(7);
            numArr[2] = getItemBgColor(2, map);
            strArr[2] = getItemPrizeNum(2, map);
            strArr2[2] = getItemPrizeName(2, map);
            arrayList.add(getItemBitmap(2, map));
            arrayList2.add(2);
            numArr[3] = getItemBgColor(5, map);
            strArr[3] = getItemPrizeNum(5, map);
            strArr2[3] = getItemPrizeName(5, map);
            arrayList.add(getItemBitmap(5, map));
            arrayList2.add(5);
            numArr[4] = getItemBgColor(6, map);
            strArr[4] = getItemPrizeNum(6, map);
            strArr2[4] = getItemPrizeName(6, map);
            arrayList.add(getItemBitmap(6, map));
            arrayList2.add(6);
            numArr[5] = getItemBgColor(1, map);
            strArr[5] = getItemPrizeNum(1, map);
            strArr2[5] = getItemPrizeName(1, map);
            arrayList.add(getItemBitmap(1, map));
            arrayList2.add(1);
            numArr[6] = getItemBgColor(4, map);
            strArr[6] = getItemPrizeNum(4, map);
            strArr2[6] = getItemPrizeName(4, map);
            arrayList.add(getItemBitmap(4, map));
            arrayList2.add(4);
            numArr[7] = getItemBgColor(8, map);
            strArr[7] = getItemPrizeNum(8, map);
            strArr2[7] = getItemPrizeName(8, map);
            arrayList.add(getItemBitmap(8, map));
            arrayList2.add(8);
            numArr[8] = getItemBgColor(3, map);
            strArr[8] = getItemPrizeNum(3, map);
            strArr2[8] = getItemPrizeName(3, map);
            arrayList.add(getItemBitmap(3, map));
            arrayList2.add(3);
        }
        turntableMode.bgColors = numArr;
        turntableMode.prizeNum = strArr;
        turntableMode.prizeNames = strArr2;
        turntableMode.bitmaps = arrayList;
        turntableMode.codes = arrayList2;
        return turntableMode;
    }

    private LotteryPrizeEntity getLotteryPrizeEntityOfMap(String str, Map<String, LotteryPrizeEntity> map) {
        if (map == null) {
            return null;
        }
        return map.get(str);
    }

    private String getAreaBgColor(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == -734239628) {
            if (str.equals("yellow")) {
                c = 0;
            }
            c = 65535;
        } else if (hashCode != 112785) {
            if (hashCode == 3027034 && str.equals("blue")) {
                c = 2;
            }
            c = 65535;
        } else {
            if (str.equals("red")) {
                c = 1;
            }
            c = 65535;
        }
        return c != 0 ? c != 1 ? c != 2 ? "#FFFFFF" : "#D4E9FF" : "#FFD4D4" : "#FFECA2";
    }

    private Bitmap getBitmap(String str) {
        if (TextUtils.isEmpty(str)) {
            return this.defaultPrizeBitmap;
        }
        try {
            return Glide.with(this).mo6717asBitmap().mo6729load(GlideUtils.formatDownUrl(str)).submit(80, 80).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return this.defaultPrizeBitmap;
        } catch (ExecutionException e2) {
            e2.printStackTrace();
            return this.defaultPrizeBitmap;
        }
    }

    private Integer getItemBgColor(int i, Map<String, LotteryPrizeEntity> map) {
        LotteryPrizeEntity lotteryPrizeEntityOfMap = getLotteryPrizeEntityOfMap(String.valueOf(i), map);
        if (lotteryPrizeEntityOfMap != null) {
            try {
                return Integer.valueOf(Color.parseColor(getAreaBgColor(lotteryPrizeEntityOfMap.color)));
            } catch (Exception unused) {
                return Integer.valueOf(Color.parseColor("#FFFFFF"));
            }
        }
        return Integer.valueOf(Color.parseColor("#FFFFFF"));
    }

    private String getItemPrizeNum(int i, Map<String, LotteryPrizeEntity> map) {
        LotteryPrizeEntity lotteryPrizeEntityOfMap = getLotteryPrizeEntityOfMap(String.valueOf(i), map);
        return lotteryPrizeEntityOfMap != null ? lotteryPrizeEntityOfMap.propNum : "";
    }

    private String getItemPrizeName(int i, Map<String, LotteryPrizeEntity> map) {
        LotteryPrizeEntity lotteryPrizeEntityOfMap = getLotteryPrizeEntityOfMap(String.valueOf(i), map);
        return lotteryPrizeEntityOfMap != null ? lotteryPrizeEntityOfMap.propName : "";
    }

    private Bitmap getItemBitmap(int i, Map<String, LotteryPrizeEntity> map) {
        LotteryPrizeEntity lotteryPrizeEntityOfMap = getLotteryPrizeEntityOfMap(String.valueOf(i), map);
        if (lotteryPrizeEntityOfMap != null) {
            if (NumberUtils.string2int(lotteryPrizeEntityOfMap.propNum) == 0) {
                return BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_lottery_turntable_xxcy);
            }
            return getBitmap(lotteryPrizeEntityOfMap.propUrl);
        }
        return this.defaultPrizeBitmap;
    }

    private LotteryTurntableInfoEntity getLocalCacheTurntableInfo(boolean z, String str) {
        Map<String, LotteryPrizeEntity> map;
        LotteryTurntableInfoEntity lotteryTurntableInfoEntity = (LotteryTurntableInfoEntity) CacheDiskUtils.getInstance().getParcelable(str, LotteryTurntableInfoEntity.CREATOR);
        if (lotteryTurntableInfoEntity != null && (map = lotteryTurntableInfoEntity.turntableAward) != null && !map.isEmpty()) {
            lotteryTurntableInfoEntity.turntableInfoData = getTurntableDataModel(z, lotteryTurntableInfoEntity.turntableAward);
        }
        return lotteryTurntableInfoEntity;
    }

    private LotteryTurntableInfoEntity putLocalCacheTurntableInfo(boolean z, String str, LotteryTurntableInfoEntity lotteryTurntableInfoEntity) {
        if (lotteryTurntableInfoEntity.isBoomStatus()) {
            CacheDiskUtils.getInstance().put(str, new LotteryTurntableInfoEntity());
        } else {
            CacheDiskUtils.getInstance().put(str, lotteryTurntableInfoEntity);
        }
        lotteryTurntableInfoEntity.turntableInfoData = getTurntableDataModel(z, lotteryTurntableInfoEntity.turntableAward);
        return lotteryTurntableInfoEntity;
    }

    private void turntableDrawEventReport() {
        String str;
        if (this.isLuxury) {
            if (isLuxuryBoomStatus()) {
                int i = this.luxuryTicketType;
                if (i == 1) {
                    str = "ZP_luxury_eggs_1";
                } else if (i != 10) {
                    if (i == 100) {
                        str = "ZP_luxury_eggs_100";
                    }
                    str = "";
                } else {
                    str = "ZP_luxury_eggs_10";
                }
            } else {
                int i2 = this.luxuryTicketType;
                if (i2 == 1) {
                    str = "ZP_luxury_1";
                } else if (i2 != 10) {
                    if (i2 == 100) {
                        str = "ZP_luxury_100";
                    }
                    str = "";
                } else {
                    str = "ZP_luxury_10";
                }
            }
        } else if (isGeneralBoomStatus()) {
            int i3 = this.generalTicketType;
            if (i3 == 1) {
                str = "ZP_ordinary_eggs_1";
            } else if (i3 != 10) {
                if (i3 == 100) {
                    str = "ZP_ordinary_eggs_100";
                }
                str = "";
            } else {
                str = "ZP_ordinary_eggs_10";
            }
        } else {
            int i4 = this.generalTicketType;
            if (i4 == 1) {
                str = "ZP_ordinary_1";
            } else if (i4 != 10) {
                if (i4 == 100) {
                    str = "ZP_ordinary_100";
                }
                str = "";
            } else {
                str = "ZP_ordinary_10";
            }
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        TomatoLiveSDK.getSingleton().statisticsReport(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: com.tomatolive.library.ui.view.dialog.LotteryDialog$Html5WebViewClient */
    /* loaded from: classes3.dex */
    public class Html5WebViewClient extends Html5WebView.BaseWebViewClient {
        private Html5WebViewClient() {
        }

        @Override // android.webkit.WebViewClient
        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            super.onPageStarted(webView, str, bitmap);
            LotteryDialog.this.tvLoadingText.setVisibility(0);
            LotteryDialog.this.recordRefreshLayout.setVisibility(4);
            LotteryDialog.this.webView.setVisibility(4);
        }

        @Override // android.webkit.WebViewClient
        public void onPageFinished(WebView webView, String str) {
            super.onPageFinished(webView, str);
            LotteryDialog.this.tvLoadingText.setVisibility(4);
            LotteryDialog.this.recordRefreshLayout.setVisibility(4);
            LotteryDialog.this.webView.setVisibility(0);
        }
    }

    public void setUserBalanceTip(@StringRes int i) {
        TextView textView = this.tvPrice;
        if (textView != null) {
            textView.setText(i);
        }
    }

    public void setUserBalance(double d) {
        this.myBalance = d;
        TextView textView = this.tvPrice;
        if (textView != null) {
            textView.setText(AppUtils.formatDisplayPrice(d, true));
        }
    }

    private String formatTipsPrice(String str) {
        return String.valueOf(new Double(NumberUtils.mul(AppUtils.changeF2Y(String.valueOf(getLuckyGiftPrice())), NumberUtils.string2Double(str))).longValue());
    }

    private long getLuckyGiftPrice() {
        GiftDownloadItemEntity luckyGiftItem = GiftDownLoadManager.getInstance().getLuckyGiftItem();
        if (luckyGiftItem != null) {
            return NumberUtils.string2long(luckyGiftItem.price);
        }
        return 100L;
    }

    private void setTextViewLeftDrawable(TextView textView) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_placeholder_gold);
        drawable.setBounds(0, 0, ConvertUtils.dp2px(20.0f), ConvertUtils.dp2px(20.0f));
        Drawable drawable2 = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_lottery_arrow_y);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, drawable2, null);
    }
}
