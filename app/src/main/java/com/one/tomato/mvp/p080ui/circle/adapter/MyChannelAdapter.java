package com.one.tomato.mvp.p080ui.circle.adapter;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.chad.library.adapter.base.BaseViewHolder;
import com.one.tomato.entity.p079db.DefaultChannelBean;
import com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter;
import com.one.tomato.utils.PreferencesUtil;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: MyChannelAdapter.kt */
/* renamed from: com.one.tomato.mvp.ui.circle.adapter.MyChannelAdapter */
/* loaded from: classes3.dex */
public final class MyChannelAdapter<T> extends BaseRecyclerViewAdapter<T> {
    private boolean isEdit;
    private int isFlag;

    public MyChannelAdapter(Context context, RecyclerView recyclerView, int i) {
        super(context, R.layout.channel_choose_item, recyclerView);
        this.isFlag = 1;
        this.isFlag = i;
    }

    public final void setIsEidt(boolean z) {
        this.isEdit = z;
        notifyDataSetChanged();
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:20:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:23:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00c5  */
    @Override // com.one.tomato.thirdpart.recyclerview.BaseRecyclerViewAdapter, com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void convert(BaseViewHolder baseViewHolder, T t) {
        String str;
        super.convert(baseViewHolder, t);
        TextView textView = null;
        ImageView imageView = baseViewHolder != null ? (ImageView) baseViewHolder.getView(R.id.image) : null;
        if (baseViewHolder != null) {
            textView = (TextView) baseViewHolder.getView(R.id.text);
        }
        if (t instanceof DefaultChannelBean) {
            String string = PreferencesUtil.getInstance().getString("language_country");
            if (string != null) {
                int hashCode = string.hashCode();
                if (hashCode != 2155) {
                    if (hashCode != 2691) {
                        if (hashCode == 2718 && string.equals("US")) {
                            str = ((DefaultChannelBean) t).getEnglishName();
                            Intrinsics.checkExpressionValueIsNotNull(str, "itemData.englishName");
                        }
                    } else if (string.equals("TW")) {
                        str = ((DefaultChannelBean) t).getTraditionalName();
                        Intrinsics.checkExpressionValueIsNotNull(str, "itemData.traditionalName");
                    }
                } else if (string.equals("CN")) {
                    str = ((DefaultChannelBean) t).getName();
                    Intrinsics.checkExpressionValueIsNotNull(str, "itemData.name");
                }
                if (textView != null) {
                    textView.setText(str);
                }
                if (this.isFlag == 1) {
                    if (imageView == null) {
                        return;
                    }
                    imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.circle_channel_choose_add));
                    return;
                } else if (!this.isEdit) {
                    if (imageView == null) {
                        return;
                    }
                    imageView.setVisibility(8);
                    return;
                } else {
                    int channelId = ((DefaultChannelBean) t).getChannelId();
                    if (1 <= channelId && 3 >= channelId) {
                        if (imageView != null) {
                            imageView.setVisibility(8);
                        }
                    } else if (imageView != null) {
                        imageView.setVisibility(0);
                    }
                    if (imageView == null) {
                        return;
                    }
                    imageView.setImageDrawable(ContextCompat.getDrawable(this.mContext, R.drawable.circle_delete_channel));
                    return;
                }
            }
            str = "";
            if (textView != null) {
            }
            if (this.isFlag == 1) {
            }
        }
    }
}
