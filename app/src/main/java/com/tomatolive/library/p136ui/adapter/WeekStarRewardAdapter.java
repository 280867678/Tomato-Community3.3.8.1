package com.tomatolive.library.p136ui.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.model.MenuEntity;

/* renamed from: com.tomatolive.library.ui.adapter.WeekStarRewardAdapter */
/* loaded from: classes3.dex */
public class WeekStarRewardAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
    public WeekStarRewardAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
        baseViewHolder.setText(R$id.tv_tips, menuEntity.menuTitle).setImageResource(R$id.iv_img, menuEntity.menuIcon);
    }
}
