package com.gen.p059mh.webapp_extensions.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.views.player.VideoResolutionView;
import com.gen.p059mh.webapp_extensions.views.player.VideoScaleView;
import com.gen.p059mh.webapp_extensions.views.player.VideoSettingView;
import com.gen.p059mh.webapp_extensions.views.player.VideoSpeedPlayView;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.adapter.SettingViewAdapter */
/* loaded from: classes2.dex */
public class SettingViewAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<SettingData> mList = new ArrayList();
    VideoSettingView.SettingClickCallBack settingClickCallBack;

    public void refresh(List<SettingData> list) {
        this.mList.clear();
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public void setSettingClickCallBack(VideoSettingView.SettingClickCallBack settingClickCallBack) {
        this.settingClickCallBack = settingClickCallBack;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    @NonNull
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public ViewHolder mo6739onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.item_web_sdk_setting, viewGroup, false));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String str;
        if (i == 0) {
            str = this.mList.get(i).getName() + VideoResolutionView.provideSelectName();
        } else if (i == 1) {
            str = this.mList.get(i).getName() + VideoSpeedPlayView.provideSelectName();
        } else if (i != 2) {
            str = "";
        } else {
            str = this.mList.get(i).getName() + VideoScaleView.provideSelectName(viewHolder.itemView.getContext());
        }
        SpannableString spannableString = new SpannableString(str);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), str.indexOf("·"), str.length(), 34);
        viewHolder.ivVideoImage.setImageResource(this.mList.get(i).getImageRes());
        viewHolder.tvVideoData.setText(spannableString);
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    /* renamed from: com.gen.mh.webapp_extensions.adapter.SettingViewAdapter$ViewHolder */
    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivVideoImage;
        private TextView tvVideoData;

        public ViewHolder(View view) {
            super(view);
            this.ivVideoImage = (ImageView) view.findViewById(R$id.iv_video_image);
            this.tvVideoData = (TextView) view.findViewById(R$id.tv_video_data);
            view.setOnClickListener(new View.OnClickListener(SettingViewAdapter.this) { // from class: com.gen.mh.webapp_extensions.adapter.SettingViewAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ViewHolder viewHolder = ViewHolder.this;
                    VideoSettingView.SettingClickCallBack settingClickCallBack = SettingViewAdapter.this.settingClickCallBack;
                    if (settingClickCallBack != null) {
                        settingClickCallBack.onSettingCallBack(viewHolder.getAdapterPosition());
                    }
                }
            });
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.adapter.SettingViewAdapter$SettingData */
    /* loaded from: classes2.dex */
    public static class SettingData {
        public int imageRes;
        public String name;

        public void setSelectIndex(int i) {
        }

        public int getImageRes() {
            return this.imageRes;
        }

        public void setImageRes(int i) {
            this.imageRes = i;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String str) {
            this.name = str;
        }
    }
}
