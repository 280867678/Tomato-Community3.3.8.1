package com.one.tomato.mvp.p080ui.p082up.adapter;

import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.ApplyConditionBean;
import com.one.tomato.utils.FormatUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpAchievementAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.up.adapter.UpAchievementAdapter */
/* loaded from: classes3.dex */
public final class UpAchievementAdapter extends BaseQuickAdapter<ApplyConditionBean, BaseViewHolder> {
    public UpAchievementAdapter() {
        super((int) R.layout.item_up_achivement);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ApplyConditionBean applyConditionBean) {
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_num) : null;
        TextView textView2 = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.text_name) : null;
        if (getData().indexOf(applyConditionBean) == 0) {
            if (textView != null) {
                if (applyConditionBean == null) {
                    Intrinsics.throwNpe();
                    throw null;
                }
                textView.setText(FormatUtil.formatTomato2RMB(applyConditionBean.getCurrentCount()));
            }
        } else if (textView != null) {
            if (applyConditionBean == null) {
                Intrinsics.throwNpe();
                throw null;
            }
            textView.setText(FormatUtil.formatNumOverTenThousand(applyConditionBean.getCurrentCount()));
        }
        if (textView2 != null) {
            textView2.setText(applyConditionBean.getTitleName());
        }
    }
}
