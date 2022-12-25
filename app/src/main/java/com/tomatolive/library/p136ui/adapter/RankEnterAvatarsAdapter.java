package com.tomatolive.library.p136ui.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.p002v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tomatolive.library.R$color;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.R$id;
import com.tomatolive.library.utils.GlideUtils;
import com.tomatolive.library.utils.SystemUtils;

/* renamed from: com.tomatolive.library.ui.adapter.RankEnterAvatarsAdapter */
/* loaded from: classes3.dex */
public class RankEnterAvatarsAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public RankEnterAvatarsAdapter(int i) {
        super(i);
    }

    /* JADX INFO: Access modifiers changed from: protected */
    /* JADX WARN: Removed duplicated region for block: B:10:0x0088  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x007b  */
    @Override // com.chad.library.adapter.base.BaseQuickAdapter
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void convert(BaseViewHolder baseViewHolder, String str) {
        float dp2px;
        float f;
        boolean z;
        View view = baseViewHolder.getView(R$id.fl_avatar_bg);
        ImageView imageView = (ImageView) baseViewHolder.getView(R$id.iv_avatar);
        float dp2px2 = SystemUtils.dp2px(3.0f);
        Drawable drawable = null;
        float f2 = 0.0f;
        if (getData().size() == 1) {
            dp2px = SystemUtils.dp2px(5.0f);
            drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_top_crown);
        } else {
            if (baseViewHolder.getLayoutPosition() != 0) {
                if (baseViewHolder.getLayoutPosition() == getData().size() - 1) {
                    drawable = ContextCompat.getDrawable(this.mContext, R$drawable.fq_ic_top_crown);
                    dp2px = SystemUtils.dp2px(5.0f);
                    f2 = SystemUtils.dp2px(-20.0f);
                } else {
                    f2 = SystemUtils.dp2px(-20.0f);
                }
            }
            f = dp2px2;
            z = true;
            view.setBackground(drawable);
            setMargins(baseViewHolder.getView(R$id.fl_root), 0, 0, (int) f2, 0);
            setPadding(view, (int) f);
            if (!z) {
                Context context = this.mContext;
                GlideUtils.loadAvatar(context, imageView, str, 4, ContextCompat.getColor(context, R$color.fq_colorWhite));
                return;
            }
            GlideUtils.loadAvatar(this.mContext, imageView, str, R$drawable.fq_ic_placeholder_avatar);
            return;
        }
        f = dp2px;
        z = false;
        view.setBackground(drawable);
        setMargins(baseViewHolder.getView(R$id.fl_root), 0, 0, (int) f2, 0);
        setPadding(view, (int) f);
        if (!z) {
        }
    }

    public void setMargins(View view, int i, int i2, int i3, int i4) {
        if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).setMargins(i, i2, i3, i4);
            view.requestLayout();
        }
    }

    public void setPadding(View view, int i) {
        view.setPadding(i, i, i, i);
    }
}
