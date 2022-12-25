package com.tomatolive.library.p136ui.view.dialog;

import android.os.Bundle;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.download.ResHotLoadManager;
import com.tomatolive.library.model.LotteryPrizeEntity;
import com.tomatolive.library.p136ui.adapter.LotteryPrizeAdapter;
import com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment;
import com.tomatolive.library.p136ui.view.divider.RVDividerLotteryResultGrid;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.SvgaUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.dialog.LotteryResultTipsDialog */
/* loaded from: classes3.dex */
public class LotteryResultTipsDialog extends BaseDialogFragment {
    private static final String ITEM_VALUE = "itemValue";
    private ArrayList<LotteryPrizeEntity> awardList;
    private ImageView ivSinglePrize;
    private View llSinglePrizBg;
    private LotteryPrizeAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private View.OnClickListener onStartListener;
    private RelativeLayout rlSinglePrizBg;
    private SVGAImageView svLuckAnim;
    private SVGAParser svgaParser;
    private TextView tvSingleNum;
    private TextView tvSinglePrizeGold;
    private TextView tvSinglePrizeName;
    private TextView tvTips;

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getWidthScale() {
        return 1.0d;
    }

    public static LotteryResultTipsDialog newInstance(ArrayList<LotteryPrizeEntity> arrayList, View.OnClickListener onClickListener) {
        Bundle bundle = new Bundle();
        LotteryResultTipsDialog lotteryResultTipsDialog = new LotteryResultTipsDialog();
        bundle.putParcelableArrayList(ITEM_VALUE, arrayList);
        lotteryResultTipsDialog.setArguments(bundle);
        lotteryResultTipsDialog.setOnStartListener(onClickListener);
        return lotteryResultTipsDialog;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseRxDialogFragment
    public void getBundle(Bundle bundle) {
        super.getBundle(bundle);
        this.awardList = bundle.getParcelableArrayList(ITEM_VALUE);
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected int getLayoutRes() {
        return R$layout.fq_dialog_lottery_result_tips;
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    protected void initView(View view) {
        this.tvTips = (TextView) view.findViewById(R$id.tv_top_tips);
        this.rlSinglePrizBg = (RelativeLayout) view.findViewById(R$id.rl_single_prize_bg);
        this.llSinglePrizBg = view.findViewById(R$id.ll_single_prize_bg);
        this.ivSinglePrize = (ImageView) view.findViewById(R$id.iv_single_prize);
        this.tvSingleNum = (TextView) view.findViewById(R$id.tv_single_num);
        this.tvSinglePrizeName = (TextView) view.findViewById(R$id.tv_gift_name);
        this.tvSinglePrizeGold = (TextView) view.findViewById(R$id.tv_gold);
        this.mRecyclerView = (RecyclerView) view.findViewById(R$id.ry_multi);
        this.svLuckAnim = (SVGAImageView) view.findViewById(R$id.iv_svga);
        this.svgaParser = new SVGAParser(this.mContext);
        initAdapter();
        AppUtils.setTextViewLeftDrawable(this.mContext, this.tvSinglePrizeGold, R$drawable.fq_ic_placeholder_gold, 15, 15);
        ArrayList<LotteryPrizeEntity> arrayList = this.awardList;
        if (arrayList == null || arrayList.isEmpty()) {
            this.rlSinglePrizBg.setVisibility(4);
            this.llSinglePrizBg.setVisibility(4);
            this.mRecyclerView.setVisibility(4);
        } else if (this.awardList.size() == 1) {
            this.rlSinglePrizBg.setVisibility(0);
            this.llSinglePrizBg.setVisibility(0);
            this.mRecyclerView.setVisibility(4);
            LotteryPrizeEntity lotteryPrizeEntity = this.awardList.get(0);
            GlideUtils.loadImage(this.mContext, this.ivSinglePrize, lotteryPrizeEntity.propUrl);
            this.tvSingleNum.setText(lotteryPrizeEntity.getPropNumStr());
            this.tvSinglePrizeName.setText(lotteryPrizeEntity.propName);
            this.tvSinglePrizeGold.setText(lotteryPrizeEntity.getPropGoldStr());
            initTipsText(this.awardList);
            if (lotteryPrizeEntity.isMaxPriceProp()) {
                loadAnim();
            } else {
                this.svLuckAnim.setVisibility(4);
            }
        } else if (this.awardList.size() <= 1) {
        } else {
            this.svLuckAnim.setVisibility(4);
            this.rlSinglePrizBg.setVisibility(4);
            this.llSinglePrizBg.setVisibility(4);
            this.mRecyclerView.setVisibility(0);
            initTipsText(this.awardList);
            this.mAdapter.setNewData(this.awardList);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public void initListener(View view) {
        super.initListener(view);
        view.findViewById(R$id.iv_close).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryResultTipsDialog$S5pBDdxVkrAQaiN0mGxdL5DWQ4w
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryResultTipsDialog.this.lambda$initListener$0$LotteryResultTipsDialog(view2);
            }
        });
        view.findViewById(R$id.tv_start).setOnClickListener(new View.OnClickListener() { // from class: com.tomatolive.library.ui.view.dialog.-$$Lambda$LotteryResultTipsDialog$3zRt7hhQEb51-yPHbLKKtbOc6Ww
            @Override // android.view.View.OnClickListener
            public final void onClick(View view2) {
                LotteryResultTipsDialog.this.lambda$initListener$1$LotteryResultTipsDialog(view2);
            }
        });
    }

    public /* synthetic */ void lambda$initListener$0$LotteryResultTipsDialog(View view) {
        dismiss();
    }

    public /* synthetic */ void lambda$initListener$1$LotteryResultTipsDialog(View view) {
        if (this.onStartListener != null) {
            dismiss();
            this.onStartListener.onClick(view);
        }
    }

    @Override // com.tomatolive.library.p136ui.view.dialog.base.BaseDialogFragment
    public double getHeightScale() {
        int i = this.mHeightPixels;
        if (i <= 1280) {
            return 0.63d;
        }
        return (i <= 1280 || i > 1920) ? 0.53d : 0.61d;
    }

    public View.OnClickListener getOnStartListener() {
        return this.onStartListener;
    }

    public void setOnStartListener(View.OnClickListener onClickListener) {
        this.onStartListener = onClickListener;
    }

    private void initAdapter() {
        this.mAdapter = new LotteryPrizeAdapter(R$layout.fq_item_dialog_winnig);
        this.mRecyclerView.setLayoutManager(new GridLayoutManager(this.mContext, 3));
        this.mRecyclerView.setHasFixedSize(true);
        this.mRecyclerView.addItemDecoration(new RVDividerLotteryResultGrid(this.mContext, R$color.fq_color_transparent));
        this.mRecyclerView.setAdapter(this.mAdapter);
        this.mAdapter.bindToRecyclerView(this.mRecyclerView);
    }

    private void initTipsText(List<LotteryPrizeEntity> list) {
        LotteryPrizeEntity lotteryPrizeEntity = list.get(0);
        if (lotteryPrizeEntity.isBroadcastScopeRoom()) {
            this.tvTips.setVisibility(0);
            this.tvTips.setText(Html.fromHtml(getString(R$string.fq_text_get_gift_tip, lotteryPrizeEntity.propName)));
        } else if (lotteryPrizeEntity.isBroadcastScopePlatform()) {
            this.tvTips.setVisibility(0);
            this.tvTips.setText(Html.fromHtml(getString(R$string.fq_text_get_gift_tip_2, lotteryPrizeEntity.propName)));
        } else {
            this.tvTips.setVisibility(4);
        }
    }

    private void loadAnim() {
        SvgaUtils.playHotLoadRes(ResHotLoadManager.getInstance().getResHotLoadPath(ResHotLoadManager.AWARD_ANIM_RES), this.svLuckAnim, this.svgaParser);
    }

    @Override // com.trello.rxlifecycle2.components.support.RxDialogFragment, android.support.p002v4.app.DialogFragment, android.support.p002v4.app.Fragment
    public void onDetach() {
        super.onDetach();
        SVGAImageView sVGAImageView = this.svLuckAnim;
        if (sVGAImageView != null) {
            sVGAImageView.stopAnimation(true);
        }
    }
}
