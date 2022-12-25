package com.tomato.ucrop;

import android.content.Context;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.one.tomato.ucrop.R$color;
import com.one.tomato.ucrop.R$drawable;
import com.one.tomato.ucrop.R$id;
import com.one.tomato.ucrop.R$layout;
import com.tomato.ucrop.model.CutInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PicturePhotoGalleryAdapter extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private List<CutInfo> list;
    private LayoutInflater mInflater;

    public PicturePhotoGalleryAdapter(Context context, List<CutInfo> list) {
        this.list = new ArrayList();
        this.mInflater = LayoutInflater.from(context);
        this.context = context;
        this.list = list;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public ViewHolder mo6739onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this.mInflater.inflate(R$layout.tomato_ucrop_picture_gf_adapter_edit_list, viewGroup, false));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        CutInfo cutInfo = this.list.get(i);
        String path = cutInfo != null ? cutInfo.getPath() : "";
        if (cutInfo.isCut()) {
            viewHolder.iv_dot.setVisibility(0);
            viewHolder.iv_dot.setImageResource(R$drawable.ucrop_oval_true);
        } else {
            viewHolder.iv_dot.setVisibility(8);
        }
        Glide.with(this.context).mo6729load(path).mo6711transition(DrawableTransitionOptions.withCrossFade()).mo6653apply((BaseRequestOptions<?>) new RequestOptions().mo6695placeholder(R$color.ucrop_color_grey).mo6654centerCrop().mo6661diskCacheStrategy(DiskCacheStrategy.ALL)).into(viewHolder.mIvPhoto);
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }

    /* loaded from: classes3.dex */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_dot;
        ImageView mIvPhoto;

        public ViewHolder(View view) {
            super(view);
            this.mIvPhoto = (ImageView) view.findViewById(R$id.iv_photo);
            this.iv_dot = (ImageView) view.findViewById(R$id.iv_dot);
        }
    }
}
