package com.gen.p059mh.webapp_extensions.views.player.custom;

import android.support.annotation.NonNull;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gen.p059mh.webapps.utils.Logger;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomResolutionAdapter */
/* loaded from: classes2.dex */
public class CustomResolutionAdapter extends RecyclerView.Adapter<CustomResolutionViewHolder> {
    int layoutResId;
    ResolutionClickListener resolutionClickListener;
    int position = 0;
    boolean isNeedSizeShow = false;
    List<ResourceEntity> list = new ArrayList();

    /* renamed from: com.gen.mh.webapp_extensions.views.player.custom.CustomResolutionAdapter$ResolutionClickListener */
    /* loaded from: classes2.dex */
    public interface ResolutionClickListener {
        void onResolutionItemClick(boolean z, int i, ResourceEntity resourceEntity);
    }

    public CustomResolutionAdapter(int i) {
        this.layoutResId = i;
    }

    public void setResolutionClickListener(ResolutionClickListener resolutionClickListener) {
        this.resolutionClickListener = resolutionClickListener;
    }

    public void refreshData(List<ResourceEntity> list, int i) {
        this.list.clear();
        this.list.addAll(list);
        Logger.m4112i("refreshData", list.toString());
        this.position = i;
        notifyDataSetChanged();
    }

    public void setChoicePosition(int i) {
        this.position = i;
        notifyDataSetChanged();
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    @NonNull
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public CustomResolutionViewHolder mo6739onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new CustomResolutionViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(this.layoutResId, viewGroup, false));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull CustomResolutionViewHolder customResolutionViewHolder, int i) {
        String name;
        Logger.m4112i("onBindViewHolder", this.list.toString());
        final ResourceEntity resourceEntity = this.list.get(i);
        TextView textView = customResolutionViewHolder.textView;
        if (this.isNeedSizeShow) {
            name = resourceEntity.getName() + "(" + (resourceEntity.getSize() / 1024) + "MB)";
        } else {
            name = resourceEntity.getName();
        }
        textView.setText(name);
        if (!this.isNeedSizeShow) {
            customResolutionViewHolder.imageView.setVisibility(resourceEntity.getResolution() == 4 ? 0 : 8);
        }
        final int layoutPosition = customResolutionViewHolder.getLayoutPosition();
        if (this.position == resourceEntity.getResolution()) {
            customResolutionViewHolder.textView.setSelected(true);
        } else {
            customResolutionViewHolder.textView.setSelected(false);
        }
        customResolutionViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.player.custom.CustomResolutionAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CustomResolutionAdapter customResolutionAdapter = CustomResolutionAdapter.this;
                ResolutionClickListener resolutionClickListener = customResolutionAdapter.resolutionClickListener;
                if (resolutionClickListener != null) {
                    resolutionClickListener.onResolutionItemClick(customResolutionAdapter.position == resourceEntity.getResolution(), layoutPosition, resourceEntity);
                }
                CustomResolutionAdapter.this.setChoicePosition(resourceEntity.getResolution());
            }
        });
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.list.size();
    }
}
