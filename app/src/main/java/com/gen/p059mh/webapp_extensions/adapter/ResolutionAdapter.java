package com.gen.p059mh.webapp_extensions.adapter;

import android.support.annotation.NonNull;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.views.player.VideoResolutionView;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.adapter.ResolutionAdapter */
/* loaded from: classes2.dex */
public class ResolutionAdapter extends RecyclerView.Adapter<ViewHolder> {
    List<Map> mList = new ArrayList();
    ClickListener onClickListener;

    /* renamed from: com.gen.mh.webapp_extensions.adapter.ResolutionAdapter$ClickListener */
    /* loaded from: classes2.dex */
    public interface ClickListener {
        void onCancel();

        void onSelected(int i);
    }

    public void setOnClickListener(ClickListener clickListener) {
        this.onClickListener = clickListener;
    }

    public void setmList(List<Map> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    @NonNull
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public ViewHolder mo6739onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.item_web_sdk_resolution, viewGroup, false));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvResolution.setText((String) this.mList.get(i).get("title"));
        viewHolder.ivResolution.setVisibility(i == VideoResolutionView.SELECT_POSITION ? 0 : 4);
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    /* renamed from: com.gen.mh.webapp_extensions.adapter.ResolutionAdapter$ViewHolder */
    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivResolution;
        TextView tvResolution;

        public ViewHolder(View view) {
            super(view);
            this.tvResolution = (TextView) view.findViewById(R$id.tv_video_resolution);
            this.ivResolution = (ImageView) view.findViewById(R$id.iv_video_resolution);
            view.setOnClickListener(new View.OnClickListener(ResolutionAdapter.this) { // from class: com.gen.mh.webapp_extensions.adapter.ResolutionAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (VideoResolutionView.SELECT_POSITION == ViewHolder.this.getAdapterPosition()) {
                        ResolutionAdapter.this.onClickListener.onCancel();
                        return;
                    }
                    VideoResolutionView.SELECT_POSITION = ViewHolder.this.getAdapterPosition();
                    ResolutionAdapter.this.notifyDataSetChanged();
                    ClickListener clickListener = ResolutionAdapter.this.onClickListener;
                    if (clickListener == null) {
                        return;
                    }
                    clickListener.onSelected(VideoResolutionView.SELECT_POSITION);
                }
            });
        }
    }
}
