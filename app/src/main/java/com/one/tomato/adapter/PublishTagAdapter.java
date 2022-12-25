package com.one.tomato.adapter;

import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.p079db.Tag;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* compiled from: PublishTagAdapter.kt */
/* loaded from: classes3.dex */
public final class PublishTagAdapter extends BaseQuickAdapter<Tag, BaseViewHolder> {
    private Function1<? super Integer, Unit> callRemoveItem;

    public PublishTagAdapter() {
        super((int) R.layout.publish_post_tag_item);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    public void convert(BaseViewHolder baseViewHolder, final Tag tag) {
        String str;
        RelativeLayout relativeLayout = null;
        TextView textView = baseViewHolder != null ? (TextView) baseViewHolder.getView(R.id.tv_content) : null;
        if (baseViewHolder != null) {
            relativeLayout = (RelativeLayout) baseViewHolder.getView(R.id.relate_tag);
        }
        if (textView != null) {
            if (tag == null || (str = tag.getTagName()) == null) {
                str = "";
            }
            textView.setText(str);
        }
        if (relativeLayout != null) {
            relativeLayout.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.adapter.PublishTagAdapter$convert$1
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    Function1 function1;
                    PublishTagAdapter publishTagAdapter = PublishTagAdapter.this;
                    publishTagAdapter.remove(publishTagAdapter.getData().indexOf(tag));
                    function1 = PublishTagAdapter.this.callRemoveItem;
                    if (function1 != null) {
                        Unit unit = (Unit) function1.mo6794invoke(Integer.valueOf(PublishTagAdapter.this.getData().size()));
                    }
                }
            });
        }
    }

    public final void addCallBackRemoveItem(Function1<? super Integer, Unit> function1) {
        this.callRemoveItem = function1;
    }
}
