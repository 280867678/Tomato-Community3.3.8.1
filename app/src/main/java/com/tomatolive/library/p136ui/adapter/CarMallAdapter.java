package com.tomatolive.library.p136ui.adapter;

import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.CarEntity;
import com.tomatolive.library.utils.AppUtils;
import com.tomatolive.library.utils.GlideUtils;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

/* renamed from: com.tomatolive.library.ui.adapter.CarMallAdapter */
/* loaded from: classes3.dex */
public class CarMallAdapter extends BaseQuickAdapter<CarEntity, BaseViewHolder> {
    public CarMallAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, CarEntity carEntity) {
        baseViewHolder.setText(R$id.tv_name, carEntity.name).setText(R$id.tv_tips, carEntity.brief).setText(R$id.tv_times, this.mContext.getString(R$string.fq_count_day, "7")).setText(R$id.tv_money, AppUtils.formatMoneyUnitStr(this.mContext, carEntity.getWeekPrice(), false)).setVisible(R$id.iv_label, carEntity.isPrivatePermission()).setVisible(R$id.tv_week_star_car, carEntity.isWeekStarCar()).setVisible(R$id.tv_money, !carEntity.isWeekStarCar()).addOnClickListener(R$id.tv_money);
        GlideUtils.loadRoundCornersImage(this.mContext, (ImageView) baseViewHolder.getView(R$id.iv_img), carEntity.imgUrl, 6, RoundedCornersTransformation.CornerType.TOP, R$drawable.fq_ic_placeholder_banner);
    }
}
