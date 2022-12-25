package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.content.Context;
import android.support.p002v4.view.ViewCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.RewardRecordBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DisplayMetricsUtils;
import com.one.tomato.utils.FormatUtil;
import com.one.tomato.utils.image.ImageLoaderUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: RewardRecordAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.adapter.RewardRecordAdapter */
/* loaded from: classes3.dex */
public final class RewardRecordAdapter extends BaseRecyclerViewAdapter<RewardRecordBean> {
    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public RewardRecordAdapter(Context context, RecyclerView recycleView) {
        super(context, R.layout.item_reward_record, recycleView);
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(recycleView, "recycleView");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, RewardRecordBean rewardRecordBean) {
        String str;
        super.convert(baseViewHolder, (BaseViewHolder) rewardRecordBean);
        String str2 = null;
        RoundedImageView roundedImageView = baseViewHolder != null ? (RoundedImageView) baseViewHolder.getView(R.id.image_head) : null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_tot_num) : null;
        if (rewardRecordBean == null || (str = rewardRecordBean.getMoney()) == null) {
            str = "";
        }
        if (textView != null) {
            textView.setText(rewardRecordBean != null ? rewardRecordBean.getName() : null);
        }
        Context context = this.mContext;
        if (rewardRecordBean != null) {
            str2 = rewardRecordBean.getAvatar();
        }
        ImageLoaderUtil.loadHeadImage(context, roundedImageView, new ImageBean(str2));
        String str3 = FormatUtil.formatTomato2RMB(str) + ' ' + AppUtil.getString(R.string.common_renmingbi);
        try {
            SpannableString spannableString = new SpannableString(str3);
            spannableString.setSpan(new AbsoluteSizeSpan((int) DisplayMetricsUtils.sp2px(18.0f)), 0, str.length(), 33);
            spannableString.setSpan(new ForegroundColorSpan((int) ViewCompat.MEASURED_STATE_MASK), 0, str.length(), 33);
            if (textView2 == null) {
                return;
            }
            textView2.setText(spannableString);
        } catch (Exception unused) {
            if (textView2 == null) {
                return;
            }
            textView2.setText(str3);
        }
    }
}
