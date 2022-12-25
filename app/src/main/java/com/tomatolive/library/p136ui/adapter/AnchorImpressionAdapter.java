package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.ColorRes;
import android.support.annotation.Nullable;
import android.support.p002v4.content.ContextCompat;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.ImpressionEntity;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.adapter.AnchorImpressionAdapter */
/* loaded from: classes3.dex */
public class AnchorImpressionAdapter extends BaseQuickAdapter<ImpressionEntity, BaseViewHolder> {
    private List<String> checkPosList = new ArrayList();
    private List<String> impressionIdList = new ArrayList();
    @ColorRes
    private int[] impressionColors = {R$color.fq_achieve_impression_0, R$color.fq_achieve_impression_1, R$color.fq_achieve_impression_2, R$color.fq_achieve_impression_3, R$color.fq_achieve_impression_4, R$color.fq_achieve_impression_5, R$color.fq_achieve_impression_6, R$color.fq_achieve_impression_7, R$color.fq_achieve_impression_8};

    public AnchorImpressionAdapter(int i) {
        super(i);
    }

    public AnchorImpressionAdapter(int i, @Nullable List<ImpressionEntity> list) {
        super(i, list);
    }

    public void addCheckPos(String str, ImpressionEntity impressionEntity) {
        if (this.checkPosList.contains(str)) {
            this.checkPosList.remove(str);
            this.impressionIdList.remove(impressionEntity.impressionId);
        } else {
            if (this.checkPosList.size() == 3) {
                this.checkPosList.remove(0);
                this.impressionIdList.remove(0);
            }
            this.checkPosList.add(str);
            this.impressionIdList.add(impressionEntity.impressionId);
        }
        notifyDataSetChanged();
    }

    public void addLocalCheckPos(String str, String str2) {
        if (this.impressionIdList.contains(str2)) {
            return;
        }
        this.checkPosList.add(str);
        this.impressionIdList.add(str2);
    }

    public List<String> getDesList() {
        return this.impressionIdList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, ImpressionEntity impressionEntity) {
        int adapterPosition = baseViewHolder.getAdapterPosition();
        boolean contains = this.checkPosList.contains(String.valueOf(adapterPosition));
        int i = this.impressionColors[adapterPosition % 9];
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_menu_title);
        textView.setText(impressionEntity.impressionName);
        textView.setBackground(getCornerBgDrawable(contains, i));
        Context context = this.mContext;
        if (contains) {
            i = R$color.fq_colorWhite;
        }
        textView.setTextColor(ContextCompat.getColor(context, i));
    }

    private GradientDrawable getCornerBgDrawable(boolean z, @ColorRes int i) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(this.mContext.getResources().getDimensionPixelOffset(R$dimen.fq_achieve_radius_30));
        if (z) {
            gradientDrawable.setColor(ContextCompat.getColor(this.mContext, i));
        } else {
            gradientDrawable.setStroke(1, ContextCompat.getColor(this.mContext, i));
            gradientDrawable.setColor(ContextCompat.getColor(this.mContext, R$color.fq_colorWhite));
        }
        return gradientDrawable;
    }
}
