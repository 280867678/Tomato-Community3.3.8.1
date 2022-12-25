package com.one.tomato.mvp.p080ui.login.adapter;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.Country;
import com.one.tomato.mvp.p080ui.login.view.CountryCodeActivity;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.tomatolive.library.utils.ConstantUtils;
import kotlin.TypeCastException;

/* compiled from: CountryCodeAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.login.adapter.CountryCodeAdapter */
/* loaded from: classes3.dex */
public final class CountryCodeAdapter extends BaseRecyclerViewAdapter<Country> {
    private final Context context;

    public CountryCodeAdapter(Context context, RecyclerView recyclerView) {
        super(context, R.layout.item_country, recyclerView);
        this.context = context;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, Country country) {
        super.convert(baseViewHolder, (BaseViewHolder) country);
        String str = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.textView) : null;
        if (textView != null) {
            StringBuilder sb = new StringBuilder();
            sb.append(country != null ? country.getCountryName() : null);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            if (country != null) {
                str = country.getCountryCode();
            }
            sb.append(str);
            textView.setText(sb.toString());
        }
    }

    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter
    public void onRecyclerItemClick(BaseQuickAdapter<?, ?> baseQuickAdapter, View view, int i) {
        super.onRecyclerItemClick(baseQuickAdapter, view, i);
        Context context = this.context;
        if (context != null) {
            ((CountryCodeActivity) context).onItemClick(i);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.mvp.ui.login.view.CountryCodeActivity");
    }
}
