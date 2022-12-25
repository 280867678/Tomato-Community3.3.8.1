package com.tomatolive.library.p136ui.view.dialog;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.p002v4.app.FragmentManager;
import android.support.p002v4.content.ContextCompat;
import android.support.p002v4.view.ViewPager;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.blankj.utilcode.util.ConvertUtils;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.TomatoLiveSDK;
import com.tomatolive.library.http.ApiRetrofit;
import com.tomatolive.library.http.HttpResultPageModel;
import com.tomatolive.library.http.RequestParams;
import com.tomatolive.library.http.function.HttpResultFunction;
import com.tomatolive.library.http.function.ServerResultFunction;
import com.tomatolive.library.model.BackpackItemEntity;
import com.tomatolive.library.model.BaseGiftBackpackEntity;
import com.tomatolive.library.model.GiftBatchItemEntity;
import com.tomatolive.library.model.GiftDownloadItemEntity;
import com.tomatolive.library.p136ui.view.dialog.GiftNumPopDialog;
import com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment;
import com.tomatolive.library.p136ui.view.gift.giftpanel.BackpackPanelControl;
import com.tomatolive.library.p136ui.view.gift.giftpanel.GiftPanelControl;
import com.tomatolive.library.p136ui.view.widget.heard.animation.RxAbstractPathAnimator;
import com.tomatolive.library.p136ui.view.widget.heard.animation.RxPathAnimator;
import com.tomatolive.library.p136ui.view.widget.progress.AnimDownCountProgressButton;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.ConstantUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SystemUtils;
import com.trello.rxlifecycle2.android.FragmentEvent;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import razerdp.basepopup.BasePopupWindow;

/* renamed from: com.tomatolive.library.ui.view.dialog.GiftBackpackDialog */
/* loaded from: classes3.dex */
public class GiftBackpackDialog extends BaseBottomDialogFragment {
    private static final String KEY_LAYOUT_RES = "bottom_layout_res";
    private int[] backPackEndXy;
    private View backPackLine;
    private BackpackPanelControl backpackPanelControl;
    private AnimDownCountProgressButton btnSend;
    private LinearLayout dotsBackpack;
    private LinearLayout dotsGift;
    private int[] giftEndXy;
    private View giftLine;
    private GiftNumPopDialog giftNumPopView;
    private GiftPanelControl giftPanelControl;
    private SendClickListener giftSendClickListener;
    private LinearLayout llBackpackView;
    private LinearLayout llGiftView;
    private List<GiftDownloadItemEntity> mData;
    private FragmentManager mFragmentManager;
    @LayoutRes
    private int mLayoutRes;
    private SVGAImageView openSvga;
    private RelativeLayout rlRoot;
    private View rlRootTip;
    private RelativeLayout rlTipRoot;
    private BackpackItemEntity selectBackpackEntity;
    private GiftDownloadItemEntity selectGiftEntity;
    private SVGAParser svgaParser;
    private TextView tvBackpackTitle;
    private TextView tvEmptyView;
    private TextView tvGiftNum;
    private TextView tvGiftTip;
    private TextView tvGiftTitle;
    private TextView tvGoWeekStarList;
    private TextView tvLoadingView;
    private TextView tvPrice;
    private TextView tvScore;
    private ViewPager viewPagerBackpack;
    private ViewPager viewPagerGift;
    private int giftNum = 1;
    private boolean isSelectGift = true;
    private double userBalance = 0.0d;
    private double score = 0.0d;

    /* renamed from: com.tomatolive.library.ui.view.dialog.GiftBackpackDialog$SendClickListener */
    /* loaded from: classes3.dex */
    public interface SendClickListener {
        void onGoToWeekStarList();

        void onOpenNobilityCallback();

        void onRechargeCallback(View view);

        void onScoreCallback(View view);

        void onSendCallback(boolean z, boolean z2, BaseGiftBackpackEntity baseGiftBackpackEntity);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public float getDimAmount() {
        return 0.0f;
    }

    public static GiftBackpackDialog create(FragmentManager fragmentManager, List<GiftDownloadItemEntity> list, SendClickListener sendClickListener) {
        GiftBackpackDialog giftBackpackDialog = new GiftBackpackDialog();
        giftBackpackDialog.setFragmentManager(fragmentManager);
        giftBackpackDialog.setGiftList(list);
        giftBackpackDialog.setOnSendClickListener(sendClickListener);
        return giftBackpackDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment, com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment, com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        if (bundle != null) {
            this.mLayoutRes = bundle.getInt(KEY_LAYOUT_RES);
        }
    }

    @Override // android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt(KEY_LAYOUT_RES, this.mLayoutRes);
        super.onSaveInstanceState(bundle);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public int getLayoutRes() {
        this.mLayoutRes = R$layout.fq_dialog_gift_backpack;
        return this.mLayoutRes;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initView(View view) {
        this.rlRoot = (RelativeLayout) view.findViewById(R$id.rl_dialog_root);
        this.llGiftView = (LinearLayout) view.findViewById(R$id.ll_gift_view);
        this.rlTipRoot = (RelativeLayout) view.findViewById(R$id.rl_tip_root);
        this.llBackpackView = (LinearLayout) view.findViewById(R$id.ll_backpack_view);
        this.tvGiftTitle = (TextView) view.findViewById(R$id.tv_gift);
        this.openSvga = (SVGAImageView) view.findViewById(R$id.open_svga);
        this.tvBackpackTitle = (TextView) view.findViewById(R$id.tv_backpack);
        this.tvPrice = (TextView) view.findViewById(R$id.tv_price);
        this.tvScore = (TextView) view.findViewById(R$id.tv_score);
        this.tvEmptyView = (TextView) view.findViewById(R$id.tv_empty_view);
        this.tvLoadingView = (TextView) view.findViewById(R$id.tv_loading_view);
        this.btnSend = (AnimDownCountProgressButton) view.findViewById(R$id.tv_gift_send);
        this.tvGiftNum = (TextView) view.findViewById(R$id.tv_gift_num_choose);
        this.viewPagerGift = (ViewPager) view.findViewById(R$id.view_pager_gift);
        this.viewPagerBackpack = (ViewPager) view.findViewById(R$id.view_pager_backpack);
        this.dotsGift = (LinearLayout) view.findViewById(R$id.dots_gift);
        this.dotsBackpack = (LinearLayout) view.findViewById(R$id.dots_backpack);
        this.giftPanelControl = new GiftPanelControl(this.mContext, this.viewPagerGift, this.dotsGift, this.mData);
        this.backpackPanelControl = new BackpackPanelControl(this.mContext, this.viewPagerBackpack, this.dotsBackpack);
        this.tvGoWeekStarList = (TextView) view.findViewById(R$id.tv_goto_week_star);
        this.tvGiftTip = (TextView) view.findViewById(R$id.tv_gift_tip);
        this.rlRootTip = view.findViewById(R$id.ll_root_tip);
        this.giftLine = view.findViewById(R$id.gift_line);
        this.backPackLine = view.findViewById(R$id.backpack_line);
        int i = 0;
        this.btnSend.setEnabled(false);
        this.giftLine.setVisibility(0);
        this.backPackLine.setVisibility(4);
        this.giftNumPopView = new GiftNumPopDialog(getContext());
        this.giftNumPopView.setOnGiftNumSelectListener(new GiftNumPopDialog.OnGiftNumSelectListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$cmV-2QbKzryFkunS3d3FJDRvtiI
            @Override // com.tomatolive.library.p136ui.view.dialog.GiftNumPopDialog.OnGiftNumSelectListener
            public final void onGiftNumSelect(GiftBatchItemEntity giftBatchItemEntity) {
                GiftBackpackDialog.this.lambda$initView$0$GiftBackpackDialog(giftBatchItemEntity);
            }
        }).setOnDismissListener(new BasePopupWindow.OnDismissListener() { // from class: com.tomatolive.library.ui.view.dialog.GiftBackpackDialog.1
            @Override // android.widget.PopupWindow.OnDismissListener
            public void onDismiss() {
                GiftBackpackDialog.this.setArrow(R$drawable.fq_icon_arrow_up);
            }
        }).setBackground(0).setPopupGravity(48).setOffsetY(-20);
        TextView textView = this.tvScore;
        if (!AppUtils.isEnableScore()) {
            i = 4;
        }
        textView.setVisibility(i);
        setTextViewLeftDrawable(this.tvPrice, R$drawable.fq_ic_placeholder_gold);
        setTextViewLeftDrawable(this.tvScore, R$drawable.fq_ic_score_star);
        this.svgaParser = new SVGAParser(this.mContext);
        initTagSelector(true);
        initOpenAnim();
    }

    public /* synthetic */ void lambda$initView$0$GiftBackpackDialog(GiftBatchItemEntity giftBatchItemEntity) {
        this.tvGiftNum.setText(String.valueOf(giftBatchItemEntity.num));
    }

    private void initOpenAnim() {
        if (this.openSvga == null) {
            return;
        }
        if (!AppUtils.isEnableNobility()) {
            this.openSvga.setVisibility(4);
            return;
        }
        this.openSvga.setVisibility(0);
        if (this.svgaParser == null) {
            this.svgaParser = new SVGAParser(this.mContext);
        }
        this.svgaParser.decodeFromAssets(ConstantUtils.GIFT_GIFT_DIALOG_OPEN_ANIM_PATH, new SVGAParser.ParseCompletion() { // from class: com.tomatolive.library.ui.view.dialog.GiftBackpackDialog.2
            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onComplete(SVGAVideoEntity sVGAVideoEntity) {
                if (GiftBackpackDialog.this.openSvga == null) {
                    return;
                }
                GiftBackpackDialog.this.openSvga.setVisibility(0);
                GiftBackpackDialog.this.openSvga.setImageDrawable(new SVGADrawable(sVGAVideoEntity));
                GiftBackpackDialog.this.openSvga.startAnimation();
            }

            @Override // com.opensource.svgaplayer.SVGAParser.ParseCompletion
            public void onError() {
                if (GiftBackpackDialog.this.openSvga == null) {
                    return;
                }
                GiftBackpackDialog.this.openSvga.setVisibility(4);
            }
        });
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onResume() {
        SVGAImageView sVGAImageView;
        super.onResume();
        if (AppUtils.isEnableNobility() && this.svgaParser != null && (sVGAImageView = this.openSvga) != null) {
            sVGAImageView.startAnimation();
        }
        TextView textView = this.tvPrice;
        if (textView != null) {
            textView.setText(AppUtils.formatDisplayPrice(this.userBalance, true));
        }
        TextView textView2 = this.tvScore;
        if (textView2 != null) {
            textView2.setText(formatPrice(this.score));
        }
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.Fragment
    public void onPause() {
        SVGAImageView sVGAImageView;
        super.onPause();
        if (!AppUtils.isEnableNobility() || this.svgaParser == null || (sVGAImageView = this.openSvga) == null) {
            return;
        }
        sVGAImageView.pauseAnimation();
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onStop() {
        super.onStop();
        SVGAImageView sVGAImageView = this.openSvga;
        if (sVGAImageView == null || !sVGAImageView.isAnimating()) {
            return;
        }
        this.openSvga.stopAnimation();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseBottomDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        view.findViewById(R$id.f6111top).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.GiftBackpackDialog.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view2) {
                GiftBackpackDialog.this.dismiss();
            }
        });
        this.giftPanelControl.setGiftClickListener(new GiftPanelControl.GiftClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$ClpwhrJCBjjwul9d663mNiDf8-0
            @Override // com.tomatolive.library.p136ui.view.gift.giftpanel.GiftPanelControl.GiftClickListener
            public final void onClick(int[] iArr, GiftDownloadItemEntity giftDownloadItemEntity) {
                GiftBackpackDialog.this.lambda$initListener$1$GiftBackpackDialog(iArr, giftDownloadItemEntity);
            }
        });
        this.backpackPanelControl.setGiftClickListener(new BackpackPanelControl.GiftClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$qhE8FId7Ld5tydAnQxljhDSZrBg
            @Override // com.tomatolive.library.p136ui.view.gift.giftpanel.BackpackPanelControl.GiftClickListener
            public final void onClick(int[] iArr, BackpackItemEntity backpackItemEntity) {
                GiftBackpackDialog.this.lambda$initListener$2$GiftBackpackDialog(iArr, backpackItemEntity);
            }
        });
        this.btnSend.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$8zm9lxRO7TqegciaMJR-wuoIr94
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$3$GiftBackpackDialog(view2);
            }
        });
        this.tvPrice.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$kpwfdg_ak8HfXQOZpOOdEtWnSZQ
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$4$GiftBackpackDialog(view2);
            }
        });
        this.tvScore.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$-g8QJ0wZZRAeZv4Y3p35I_-6KiY
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$5$GiftBackpackDialog(view2);
            }
        });
        this.llGiftView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$ISLKavvZTAP4EKcGZ3iFZ8pbg5Y
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$6$GiftBackpackDialog(view2);
            }
        });
        this.llBackpackView.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$WNvtJzuRJjcZrooTZ4Bl6AtG4Yo
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$7$GiftBackpackDialog(view2);
            }
        });
        this.tvGiftNum.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$IhX7LweOTDrEuWSPrp2U2XT6LFA
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$8$GiftBackpackDialog(view2);
            }
        });
        this.tvGoWeekStarList.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$we2wAeZ_p9q6ESlfMXUqRNZbKEs
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$9$GiftBackpackDialog(view2);
            }
        });
        this.openSvga.setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$GiftBackpackDialog$hCjS3aYQvCAFhH9GowUrK8jAkYw
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                GiftBackpackDialog.this.lambda$initListener$10$GiftBackpackDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$1$GiftBackpackDialog(int[] iArr, GiftDownloadItemEntity giftDownloadItemEntity) {
        if (giftDownloadItemEntity == null) {
            return;
        }
        this.selectGiftEntity = giftDownloadItemEntity;
        this.giftNumPopView.updateAdapterData(giftDownloadItemEntity.giftBatchItemList);
        this.tvGiftNum.setText("1");
        showGiftCaption();
        this.btnSend.setEnabled(this.isSelectGift && this.selectGiftEntity != null);
        this.btnSend.setCurrentText(R$string.fq_text_send);
        initBatchGiftView();
        this.giftEndXy = iArr;
    }

    public /* synthetic */ void lambda$initListener$2$GiftBackpackDialog(int[] iArr, BackpackItemEntity backpackItemEntity) {
        if (backpackItemEntity == null) {
            this.btnSend.setCurrentText(R$string.fq_text_send);
            this.btnSend.setEnabled(false);
            return;
        }
        this.selectBackpackEntity = backpackItemEntity;
        BackpackItemEntity backpackItemEntity2 = this.selectBackpackEntity;
        if (backpackItemEntity2 != null) {
            if (backpackItemEntity2.isNobilityTrumpetBoolean()) {
                this.btnSend.setCurrentText(R$string.fq_matisse_button_apply_default);
                this.btnSend.setEnabled(!this.selectBackpackEntity.isFreeze());
                return;
            } else if (this.selectBackpackEntity.isPropFragmentBoolean()) {
                this.btnSend.setCurrentText(R$string.fq_matisse_button_apply_default);
                this.btnSend.setEnabled(true);
                return;
            } else {
                this.btnSend.setEnabled(true);
                this.btnSend.setCurrentText(R$string.fq_text_send);
                this.backPackEndXy = iArr;
                return;
            }
        }
        this.btnSend.setEnabled(false);
        this.btnSend.setCurrentText(R$string.fq_text_send);
    }

    public /* synthetic */ void lambda$initListener$3$GiftBackpackDialog(View view) {
        boolean z;
        if (this.giftSendClickListener == null || !isRestrictionUser()) {
            return;
        }
        this.giftNum = 1;
        if (this.isSelectGift && this.tvGiftNum.getVisibility() == 0) {
            String charSequence = this.tvGiftNum.getText().toString();
            this.giftNum = NumberUtils.string2int(charSequence);
            this.selectGiftEntity.giftNum = charSequence;
        }
        if (!this.isSelectGift) {
            z = this.backpackPanelControl.updateSelectedItemCount();
            if (this.backpackPanelControl.isEmpty()) {
                this.tvEmptyView.setVisibility(0);
                this.viewPagerBackpack.setVisibility(8);
            }
        } else {
            z = false;
        }
        SendClickListener sendClickListener = this.giftSendClickListener;
        boolean z2 = this.isSelectGift;
        sendClickListener.onSendCallback(z, z2, z2 ? this.selectGiftEntity : this.selectBackpackEntity);
        GiftDownloadItemEntity giftDownloadItemEntity = this.selectGiftEntity;
        if (giftDownloadItemEntity == null || !giftDownloadItemEntity.isLuckyGift()) {
            return;
        }
        TomatoLiveSDK.getSingleton().statisticsReport("Gift_quantity_lucky");
    }

    public /* synthetic */ void lambda$initListener$4$GiftBackpackDialog(View view) {
        if (isRestrictionUser() && this.giftSendClickListener != null) {
            dismiss();
            this.giftSendClickListener.onRechargeCallback(view);
        }
    }

    public /* synthetic */ void lambda$initListener$5$GiftBackpackDialog(View view) {
        if (isRestrictionUser() && this.giftSendClickListener != null) {
            dismiss();
            this.giftSendClickListener.onScoreCallback(view);
        }
    }

    public /* synthetic */ void lambda$initListener$6$GiftBackpackDialog(View view) {
        initTagSelector(true);
    }

    public /* synthetic */ void lambda$initListener$7$GiftBackpackDialog(View view) {
        initTagSelector(false);
    }

    public /* synthetic */ void lambda$initListener$8$GiftBackpackDialog(View view) {
        setArrow(R$drawable.fq_icon_arrow_down);
        GiftNumPopDialog giftNumPopDialog = this.giftNumPopView;
        if (giftNumPopDialog != null) {
            giftNumPopDialog.showPopupWindow(this.tvGiftNum);
        }
    }

    public /* synthetic */ void lambda$initListener$9$GiftBackpackDialog(View view) {
        dismiss();
        SendClickListener sendClickListener = this.giftSendClickListener;
        if (sendClickListener != null) {
            sendClickListener.onGoToWeekStarList();
        }
    }

    public /* synthetic */ void lambda$initListener$10$GiftBackpackDialog(View view) {
        SendClickListener sendClickListener;
        if (isRestrictionUser() && (sendClickListener = this.giftSendClickListener) != null) {
            sendClickListener.onOpenNobilityCallback();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setArrow(int i) {
        Drawable drawable = getResources().getDrawable(i);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        this.tvGiftNum.setCompoundDrawables(null, null, drawable, null);
    }

    public void showDownCountAndFlyAnim(boolean z) {
        if (this.btnSend.isEnabled()) {
            this.btnSend.startDownCont(50);
        }
        RxPathAnimator rxPathAnimator = new RxPathAnimator(RxAbstractPathAnimator.Config.defaultConfig((int) ((this.isSelectGift ? this.giftEndXy[0] : this.backPackEndXy[0]) + SystemUtils.dp2px(10.0f)), this.isSelectGift ? this.giftEndXy[1] : this.backPackEndXy[1]));
        ImageView imageView = new ImageView(getContext());
        GlideUtils.loadImage(getContext(), imageView, this.isSelectGift ? this.selectGiftEntity.imgurl : this.selectBackpackEntity.coverUrl, R$drawable.fq_ic_gift_default);
        rxPathAnimator.start(z, imageView, this.rlRoot);
    }

    private void setFragmentManager(FragmentManager fragmentManager) {
        this.mFragmentManager = fragmentManager;
    }

    public void show() {
        show(this.mFragmentManager);
    }

    public void setUserBalanceTip(@StringRes int i) {
        TextView textView = this.tvPrice;
        if (textView != null) {
            textView.setText(i);
        }
        TextView textView2 = this.tvScore;
        if (textView2 != null) {
            textView2.setText("-");
        }
    }

    private void setOnSendClickListener(SendClickListener sendClickListener) {
        this.giftSendClickListener = sendClickListener;
    }

    public void setUserBalance(double d) {
        this.userBalance = d;
        TextView textView = this.tvPrice;
        if (textView != null) {
            textView.setText(AppUtils.formatDisplayPrice(d, true));
        }
    }

    public void setUserScore(double d) {
        this.score = d;
        TextView textView = this.tvScore;
        if (textView != null) {
            textView.setText(formatPrice(d));
        }
    }

    public int getGiftNum() {
        return this.giftNum;
    }

    public void setGiftNum(int i) {
        this.giftNum = i;
    }

    private String formatPrice(double d) {
        return NumberUtils.formatThreeNumStr(d);
    }

    public void updateBackPackCount() {
        initTagSelector(false);
    }

    public void initTagSelector(boolean z) {
        if (this.rlTipRoot == null) {
            return;
        }
        int i = 0;
        this.giftLine.setVisibility(z ? 0 : 4);
        this.backPackLine.setVisibility(z ? 4 : 0);
        this.isSelectGift = z;
        this.rlTipRoot.bringChildToFront(z ? this.llGiftView : this.llBackpackView);
        this.llGiftView.setSelected(z);
        this.tvGiftTitle.setSelected(z);
        this.llBackpackView.setSelected(!z);
        this.tvBackpackTitle.setSelected(!z);
        this.viewPagerGift.setVisibility(z ? 0 : 4);
        this.dotsGift.setVisibility(z ? 0 : 4);
        this.viewPagerBackpack.setVisibility((z || !this.backpackPanelControl.isCountEnabled()) ? 8 : 0);
        LinearLayout linearLayout = this.dotsBackpack;
        if (z) {
            i = 8;
        }
        linearLayout.setVisibility(i);
        this.btnSend.setCurrentText(R$string.fq_text_send);
        this.btnSend.setEnabled(getSendEnabled());
        this.tvEmptyView.setVisibility(8);
        if (z) {
            initBatchGiftView();
            showGiftCaption();
        } else {
            this.rlRootTip.setVisibility(4);
            this.tvGiftNum.setVisibility(8);
            this.btnSend.setFullCorner(true);
        }
        sendBackpackRequest(z);
    }

    private void sendBackpackRequest(boolean z) {
        if (z) {
            return;
        }
        if (!AppUtils.isConsumptionPermissionUser()) {
            this.tvEmptyView.setVisibility(0);
            this.viewPagerBackpack.setVisibility(8);
            return;
        }
        this.btnSend.setEnabled(false);
        ApiRetrofit.getInstance().getApiService().getUserPropsListService(new RequestParams().getUserIdParams()).map(new ServerResultFunction<HttpResultPageModel<BackpackItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GiftBackpackDialog.5
        }).onErrorResumeNext(new HttpResultFunction()).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).compose(bindUntilEvent(FragmentEvent.DESTROY)).subscribe(new Observer<HttpResultPageModel<BackpackItemEntity>>() { // from class: com.tomatolive.library.ui.view.dialog.GiftBackpackDialog.4
            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                GiftBackpackDialog.this.tvLoadingView.setVisibility(0);
                GiftBackpackDialog.this.tvEmptyView.setVisibility(8);
                GiftBackpackDialog.this.viewPagerBackpack.setVisibility(8);
            }

            @Override // io.reactivex.Observer
            public void onNext(HttpResultPageModel<BackpackItemEntity> httpResultPageModel) {
                if (httpResultPageModel == null) {
                    return;
                }
                GiftBackpackDialog.this.backpackPanelControl.updateBackpackList(httpResultPageModel.dataList);
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
                GiftBackpackDialog.this.tvLoadingView.setVisibility(8);
                GiftBackpackDialog.this.tvEmptyView.setVisibility(0);
                GiftBackpackDialog.this.viewPagerBackpack.setVisibility(8);
            }

            @Override // io.reactivex.Observer
            public void onComplete() {
                int i = 8;
                if (GiftBackpackDialog.this.isSelectGift) {
                    GiftBackpackDialog.this.tvLoadingView.setVisibility(8);
                    GiftBackpackDialog.this.tvEmptyView.setVisibility(8);
                    GiftBackpackDialog.this.viewPagerBackpack.setVisibility(8);
                    return;
                }
                GiftBackpackDialog.this.tvLoadingView.setVisibility(8);
                GiftBackpackDialog.this.tvEmptyView.setVisibility(!GiftBackpackDialog.this.backpackPanelControl.isCountEnabled() ? 0 : 8);
                ViewPager viewPager = GiftBackpackDialog.this.viewPagerBackpack;
                if (GiftBackpackDialog.this.backpackPanelControl.isCountEnabled()) {
                    i = 0;
                }
                viewPager.setVisibility(i);
            }
        });
    }

    private boolean getSendEnabled() {
        if (this.isSelectGift) {
            if (this.selectGiftEntity != null) {
                return true;
            }
        } else if (this.selectBackpackEntity != null) {
            return true;
        }
        return false;
    }

    private void initBatchGiftView() {
        GiftDownloadItemEntity giftDownloadItemEntity = this.selectGiftEntity;
        if (giftDownloadItemEntity != null && giftDownloadItemEntity.isBatchGiftFlag()) {
            this.tvGiftNum.setVisibility(0);
            this.btnSend.setFullCorner(false);
            return;
        }
        this.tvGiftNum.setVisibility(4);
        this.btnSend.setFullCorner(true);
    }

    private void showGiftCaption() {
        if (this.selectGiftEntity == null) {
            return;
        }
        this.rlRootTip.setVisibility(0);
        this.tvGoWeekStarList.setVisibility(8);
        this.tvGiftTip.setText(this.selectGiftEntity.caption);
        if (!TextUtils.isEmpty(this.selectGiftEntity.caption)) {
            this.tvGiftTip.setSelected(true);
        }
        if (this.selectGiftEntity.isCurWeekStarGift() && AppUtils.isEnableWeekStar()) {
            this.tvGoWeekStarList.setVisibility(0);
        }
        if (!this.selectGiftEntity.isLastWeekStarGift()) {
            return;
        }
        TextView textView = this.tvGiftTip;
        int i = R$string.fq_last_week_star_tip;
        GiftDownloadItemEntity giftDownloadItemEntity = this.selectGiftEntity;
        textView.setText(Html.fromHtml(getString(i, giftDownloadItemEntity.anchorName, giftDownloadItemEntity.userName)));
        this.tvGiftTip.setSelected(true);
    }

    private void setGiftList(List<GiftDownloadItemEntity> list) {
        this.mData = list;
    }

    private void setTextViewLeftDrawable(TextView textView, @DrawableRes int i) {
        Drawable drawable = ContextCompat.getDrawable(this.mContext, i);
        drawable.setBounds(0, 0, ConvertUtils.dp2px(20.0f), ConvertUtils.dp2px(20.0f));
        Drawable drawable2 = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_arrow_right);
        drawable2.setBounds(0, 0, drawable2.getMinimumWidth(), drawable2.getMinimumHeight());
        textView.setCompoundDrawables(drawable, null, drawable2, null);
    }

    public void updateGiftList(List<GiftDownloadItemEntity> list) {
        setGiftList(list);
        GiftPanelControl giftPanelControl = this.giftPanelControl;
        if (giftPanelControl != null) {
            giftPanelControl.updateGiftList(list);
        }
    }

    public void release() {
        List<GiftDownloadItemEntity> list = this.mData;
        if (list != null) {
            list.clear();
            this.mData = null;
        }
        SVGAImageView sVGAImageView = this.openSvga;
        if (sVGAImageView != null) {
            sVGAImageView.clearAnimation();
            this.openSvga = null;
        }
        if (this.svgaParser != null) {
            this.svgaParser = null;
        }
        GiftPanelControl giftPanelControl = this.giftPanelControl;
        if (giftPanelControl != null) {
            giftPanelControl.onDestroy();
        }
        BackpackPanelControl backpackPanelControl = this.backpackPanelControl;
        if (backpackPanelControl != null) {
            backpackPanelControl.onDestroy();
        }
        setOnSendClickListener(null);
    }
}
