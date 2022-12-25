package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.support.annotation.NonNull;
import android.support.p005v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomResolutionViewHolder */
/* loaded from: classes2.dex */
public class CustomResolutionViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView textView;

    public CustomResolutionViewHolder(@NonNull View view) {
        super(view);
        this.textView = (TextView) view.findViewById(R$id.tv_player_resolution);
        this.imageView = (ImageView) view.findViewById(R$id.img_tag);
    }
}
