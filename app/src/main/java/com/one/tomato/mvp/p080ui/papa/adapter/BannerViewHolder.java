package com.one.tomato.mvp.p080ui.papa.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.broccoli.p150bh.R;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.p079db.AdPage;
import com.one.tomato.thirdpart.roundimage.RoundedImageView;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.widget.mzbanner.holder.MZViewHolder;

/* renamed from: com.one.tomato.mvp.ui.papa.adapter.BannerViewHolder */
/* loaded from: classes3.dex */
public class BannerViewHolder implements MZViewHolder<AdPage> {
    private boolean isShowViewBg;
    private RoundedImageView mImageView;
    private View view_bg;

    public BannerViewHolder() {
    }

    public BannerViewHolder(boolean z) {
        this.isShowViewBg = z;
    }

    @Override // com.one.tomato.widget.mzbanner.holder.MZViewHolder
    public View createView(Context context) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.banner_item, (ViewGroup) null);
        this.mImageView = (RoundedImageView) inflate.findViewById(R.id.banner_image);
        this.view_bg = inflate.findViewById(R.id.view_bg);
        return inflate;
    }

    @Override // com.one.tomato.widget.mzbanner.holder.MZViewHolder
    public void onBind(Context context, int i, AdPage adPage) {
        ImageLoaderUtil.loadRecyclerThumbImage(context, this.mImageView, new ImageBean(adPage.getImageUrlSec()));
        if (this.isShowViewBg) {
            this.view_bg.setVisibility(0);
        }
    }
}
