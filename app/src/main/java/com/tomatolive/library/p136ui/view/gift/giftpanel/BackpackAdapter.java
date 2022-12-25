package com.tomatolive.library.p136ui.view.gift.giftpanel;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.BackpackItemEntity;
import com.tomatolive.library.p136ui.view.widget.marqueen.SimpleMF;
import com.tomatolive.library.p136ui.view.widget.marqueen.SimpleMarqueeView;
import com.tomatolive.library.utils.DateUtils;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.NumberUtils;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.tomatolive.library.ui.view.gift.giftpanel.BackpackAdapter */
/* loaded from: classes3.dex */
public class BackpackAdapter extends BaseQuickAdapter<BackpackItemEntity, BaseViewHolder> {
    private SimpleMF<String> marqueeFactory;
    private int selectedPosition = -1;

    public BackpackAdapter(@Nullable List<BackpackItemEntity> list) {
        super(R$layout.fq_item_grid_bag, list);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, BackpackItemEntity backpackItemEntity) {
        boolean z = this.selectedPosition == baseViewHolder.getAdapterPosition();
        baseViewHolder.setText(R$id.tv_item_title, backpackItemEntity.name).setText(R$id.tv_item_desc, getRemainTime(backpackItemEntity)).setText(R$id.tv_count, formatCount(backpackItemEntity.count)).setVisible(R$id.tv_item_title, !backpackItemEntity.isStayTuned).setVisible(R$id.tv_count, !backpackItemEntity.isStayTuned).setVisible(R$id.iv_item_logo, true ^ backpackItemEntity.isStayTuned).setVisible(R$id.iv_lock_icon, false).getView(R$id.ll_item_layout).setSelected(z);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_item_logo);
        if (!backpackItemEntity.isStayTuned) {
            GlideUtils.loadImage(this.mContext, imageView, backpackItemEntity.coverUrl, R$drawable.fq_ic_gift_default);
        }
        TextView textView = (TextView) baseViewHolder.getView(R$id.tv_item_desc);
        SimpleMarqueeView simpleMarqueeView = (SimpleMarqueeView) baseViewHolder.getView(R$id.marqueeView);
        if (backpackItemEntity.isNobilityTrumpetBoolean()) {
            baseViewHolder.setText(R$id.tv_item_title, this.mContext.getString(R$string.fq_trumpet_tip));
            imageView.setImageResource(R$drawable.fq_img_trumpet);
            baseViewHolder.setVisible(R$id.iv_lock_icon, backpackItemEntity.isFreeze());
            textView.setVisibility(8);
            simpleMarqueeView.setVisibility(8);
            return;
        }
        int i = 4;
        if (z) {
            if (TextUtils.equals(backpackItemEntity.remainTime, "-1")) {
                textView.setVisibility(0);
                simpleMarqueeView.setVisibility(4);
                if (!simpleMarqueeView.isFlipping()) {
                    return;
                }
                simpleMarqueeView.stopFlipping();
                return;
            }
            textView.setVisibility(4);
            if (!backpackItemEntity.isStayTuned) {
                i = 0;
            }
            simpleMarqueeView.setVisibility(i);
            this.marqueeFactory = new SimpleMF<>(this.mContext);
            this.marqueeFactory.setData(backpackItemEntity.isPropFragmentBoolean() ? getPropMarqueeData(backpackItemEntity) : getMarqueeData(backpackItemEntity));
            simpleMarqueeView.setMarqueeFactory(this.marqueeFactory);
            simpleMarqueeView.startFlipping();
            return;
        }
        simpleMarqueeView.setVisibility(4);
        if (simpleMarqueeView.isFlipping()) {
            simpleMarqueeView.stopFlipping();
        }
        if (!backpackItemEntity.isStayTuned) {
            i = 0;
        }
        textView.setVisibility(i);
    }

    public void setSelectedPosition(int i) {
        this.selectedPosition = i;
        notifyDataSetChanged();
    }

    public void clearSelectedPosition() {
        View viewByPosition;
        if (isPosition(this.selectedPosition) && (viewByPosition = getViewByPosition(this.selectedPosition, R$id.ll_item_layout)) != null) {
            viewByPosition.setSelected(false);
        }
    }

    public int getSelectedPosition() {
        return this.selectedPosition;
    }

    public BackpackItemEntity getSelectedItem() {
        int i = this.selectedPosition;
        if (i <= 0) {
            return null;
        }
        return getItem(i);
    }

    private String getRemainTime(BackpackItemEntity backpackItemEntity) {
        if (TextUtils.equals(backpackItemEntity.remainTime, "-1")) {
            String str = backpackItemEntity.exp;
            return str != null ? this.mContext.getString(R$string.fq_props_add_exp, str) : "";
        }
        return DateUtils.getLaveMinuteTimeSpan(NumberUtils.string2long(backpackItemEntity.remainTime));
    }

    private List<String> getMarqueeData(BackpackItemEntity backpackItemEntity) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(getRemainTime(backpackItemEntity));
        String str = backpackItemEntity.exp;
        if (str != null) {
            arrayList.add(this.mContext.getString(R$string.fq_props_add_exp, str));
        }
        return arrayList;
    }

    private List<String> getPropMarqueeData(BackpackItemEntity backpackItemEntity) {
        ArrayList arrayList = new ArrayList();
        arrayList.add(DateUtils.getLaveMinuteTimeSpan(NumberUtils.string2long(backpackItemEntity.remainTime)));
        arrayList.add(backpackItemEntity.count + "/50");
        return arrayList;
    }

    private String formatCount(String str) {
        return NumberUtils.string2int(str) > 99 ? "99+" : str;
    }

    private boolean isPosition(int i) {
        return i >= 0 && i < getData().size();
    }
}
