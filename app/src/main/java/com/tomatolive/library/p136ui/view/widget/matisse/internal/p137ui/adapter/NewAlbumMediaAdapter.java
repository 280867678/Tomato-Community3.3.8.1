package com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$layout;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Album;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.Item;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.entity.SelectionSpec;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.CheckView;
import com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.MediaGrid;
import java.util.List;
import java.util.Set;

/* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.adapter.NewAlbumMediaAdapter */
/* loaded from: classes4.dex */
public class NewAlbumMediaAdapter extends RecyclerView.Adapter<MediaViewHolder> implements MediaGrid.OnMediaGridClickListener {
    private List<Item> itemList;
    private Set<Item> itemSet;
    private int mImageResize;
    private OnMediaClickListener mOnMediaClickListener;
    private RecyclerView mRecyclerView;
    private final SelectionSpec mSelectionSpec = SelectionSpec.getInstance();

    /* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.adapter.NewAlbumMediaAdapter$OnMediaClickListener */
    /* loaded from: classes4.dex */
    public interface OnMediaClickListener {
        void onMediaClick(Album album, Item item, int i);
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.MediaGrid.OnMediaGridClickListener
    public void onCheckViewClicked(CheckView checkView, Item item, RecyclerView.ViewHolder viewHolder) {
    }

    public NewAlbumMediaAdapter(List<Item> list, RecyclerView recyclerView) {
        this.itemList = list;
        this.mRecyclerView = recyclerView;
    }

    public NewAlbumMediaAdapter(Set<Item> set, RecyclerView recyclerView) {
        this.itemSet = set;
        this.mRecyclerView = recyclerView;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    @NonNull
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public MediaViewHolder mo6739onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MediaViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.fq_matisse_media_grid_item, viewGroup, false));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(@NonNull MediaViewHolder mediaViewHolder, int i) {
        mediaViewHolder.mMediaGrid.preBindMedia(new MediaGrid.PreBindInfo(getImageResize(mediaViewHolder.mMediaGrid.getContext()), null, this.mSelectionSpec.countable, mediaViewHolder));
        mediaViewHolder.mMediaGrid.bindMedia(this.itemList.get(i));
        mediaViewHolder.mMediaGrid.setOnMediaGridClickListener(this);
    }

    private int getImageResize(Context context) {
        if (this.mImageResize == 0) {
            int spanCount = ((GridLayoutManager) this.mRecyclerView.getLayoutManager()).getSpanCount();
            this.mImageResize = (context.getResources().getDisplayMetrics().widthPixels - (context.getResources().getDimensionPixelSize(R$dimen.fq_matisse_media_grid_spacing) * (spanCount - 1))) / spanCount;
            this.mImageResize = (int) (this.mImageResize * this.mSelectionSpec.thumbnailScale);
        }
        return this.mImageResize;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        List<Item> list = this.itemList;
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    @Override // com.tomatolive.library.p136ui.view.widget.matisse.internal.p137ui.widget.MediaGrid.OnMediaGridClickListener
    public void onThumbnailClicked(ImageView imageView, Item item, RecyclerView.ViewHolder viewHolder) {
        OnMediaClickListener onMediaClickListener = this.mOnMediaClickListener;
        if (onMediaClickListener != null) {
            onMediaClickListener.onMediaClick(null, item, viewHolder.getAdapterPosition());
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.tomatolive.library.ui.view.widget.matisse.internal.ui.adapter.NewAlbumMediaAdapter$MediaViewHolder */
    /* loaded from: classes4.dex */
    public static class MediaViewHolder extends RecyclerView.ViewHolder {
        private MediaGrid mMediaGrid;

        MediaViewHolder(View view) {
            super(view);
            this.mMediaGrid = (MediaGrid) view;
        }
    }

    public void registerOnMediaClickListener(OnMediaClickListener onMediaClickListener) {
        this.mOnMediaClickListener = onMediaClickListener;
    }

    public void unregisterOnMediaClickListener() {
        this.mOnMediaClickListener = null;
    }

    public void setData(List<Item> list) {
        this.itemList = list;
        notifyDataSetChanged();
    }
}
