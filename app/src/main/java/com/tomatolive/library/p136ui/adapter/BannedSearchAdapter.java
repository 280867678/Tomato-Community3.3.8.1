package com.tomatolive.library.p136ui.adapter;

import android.text.Html;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$id;
import com.tomatolive.library.R$string;
import com.tomatolive.library.model.MenuEntity;

/* renamed from: com.tomatolive.library.ui.adapter.BannedSearchAdapter */
/* loaded from: classes3.dex */
public class BannedSearchAdapter extends BaseQuickAdapter<MenuEntity, BaseViewHolder> {
    public BannedSearchAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, MenuEntity menuEntity) {
        baseViewHolder.setText(R$id.tv_keyword, Html.fromHtml(this.mContext.getString(R$string.fq_my_live_search_key_desc, menuEntity.getMenuTitle())));
    }
}
