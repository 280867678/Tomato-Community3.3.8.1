package com.one.tomato.dialog;

import android.view.View;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.UpSubscribePayBean;
import java.util.ArrayList;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: UpSubscribePayDialog.kt */
/* loaded from: classes3.dex */
final class UpSubscribePayDialog$initView$2 implements BaseQuickAdapter.OnItemClickListener {
    public static final UpSubscribePayDialog$initView$2 INSTANCE = new UpSubscribePayDialog$initView$2();

    UpSubscribePayDialog$initView$2() {
    }

    @Override // com.chad.library.adapter.base.BaseQuickAdapter.OnItemClickListener
    public final void onItemClick(BaseQuickAdapter<Object, BaseViewHolder> adapter, View view, int i) {
        Intrinsics.checkExpressionValueIsNotNull(adapter, "adapter");
        Object obj = adapter.getData().get(i);
        if (obj == null) {
            throw new TypeCastException("null cannot be cast to non-null type com.one.tomato.entity.UpSubscribePayBean");
        }
        UpSubscribePayBean upSubscribePayBean = (UpSubscribePayBean) obj;
        List<Object> data = adapter.getData();
        if (data == null) {
            throw new TypeCastException("null cannot be cast to non-null type kotlin.collections.ArrayList<com.one.tomato.entity.UpSubscribePayBean> /* = java.util.ArrayList<com.one.tomato.entity.UpSubscribePayBean> */");
        }
        for (UpSubscribePayBean upSubscribePayBean2 : (ArrayList) data) {
            upSubscribePayBean2.setSelect(Intrinsics.areEqual(upSubscribePayBean2, upSubscribePayBean));
        }
        adapter.notifyDataSetChanged();
    }
}
