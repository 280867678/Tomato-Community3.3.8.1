package com.gen.p059mh.webapp_extensions.adapter;

import android.support.annotation.NonNull;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.adapter.SheetAdapter */
/* loaded from: classes2.dex */
public class SheetAdapter extends RecyclerView.Adapter<ViewHolder> {
    public List<String> mList = new ArrayList();
    SheetCallBack sheetCallBack;

    public void setSheetCallBack(SheetCallBack sheetCallBack) {
        this.sheetCallBack = sheetCallBack;
    }

    public void refreshData(List<String> list) {
        this.mList = list;
        notifyDataSetChanged();
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    @NonNull
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public ViewHolder mo6739onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.item_web_sdk_sheet, viewGroup, false));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.tvItemSheet.setText(this.mList.get(i));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.mList.size();
    }

    /* renamed from: com.gen.mh.webapp_extensions.adapter.SheetAdapter$ViewHolder */
    /* loaded from: classes2.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvItemSheet;

        public ViewHolder(View view) {
            super(view);
            this.tvItemSheet = (TextView) view.findViewById(R$id.tv_item_sheet);
            this.tvItemSheet.setOnClickListener(new View.OnClickListener(SheetAdapter.this) { // from class: com.gen.mh.webapp_extensions.adapter.SheetAdapter.ViewHolder.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    ViewHolder viewHolder = ViewHolder.this;
                    SheetCallBack sheetCallBack = SheetAdapter.this.sheetCallBack;
                    if (sheetCallBack != null) {
                        int adapterPosition = viewHolder.getAdapterPosition();
                        ViewHolder viewHolder2 = ViewHolder.this;
                        sheetCallBack.onSelect(adapterPosition, SheetAdapter.this.mList.get(viewHolder2.getAdapterPosition()));
                    }
                }
            });
        }
    }
}
