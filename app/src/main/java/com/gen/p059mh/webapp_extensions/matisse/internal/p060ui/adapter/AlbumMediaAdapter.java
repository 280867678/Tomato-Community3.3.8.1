package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.support.p005v7.widget.GridLayoutManager;
import android.support.p005v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.gen.p059mh.webapp_extensions.R$attr;
import com.gen.p059mh.webapp_extensions.R$dimen;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Album;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.IncapableCause;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.matisse.internal.model.SelectedItemCollection;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.CheckView;
import com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.MediaGrid;
import com.gen.p059mh.webapp_extensions.views.camera.smartCamera.CameraView;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumMediaAdapter */
/* loaded from: classes2.dex */
public class AlbumMediaAdapter extends RecyclerViewCursorAdapter<RecyclerView.ViewHolder> implements MediaGrid.OnMediaGridClickListener {
    private static final int VIEW_TYPE_CAPTURE = 1;
    private static final int VIEW_TYPE_MEDIA = 2;
    CameraView cameraView;
    private CheckStateListener mCheckStateListener;
    private int mImageResize;
    private OnMediaClickListener mOnMediaClickListener;
    private final Drawable mPlaceholder;
    private RecyclerView mRecyclerView;
    private final SelectedItemCollection mSelectedCollection;
    boolean isStart = false;
    private SelectionSpec mSelectionSpec = SelectionSpec.getInstance();

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumMediaAdapter$CheckStateListener */
    /* loaded from: classes2.dex */
    public interface CheckStateListener {
        void onUpdate();
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumMediaAdapter$OnMediaClickListener */
    /* loaded from: classes2.dex */
    public interface OnMediaClickListener {
        void onMediaClick(Album album, Item item, int i);
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumMediaAdapter$OnPhotoCapture */
    /* loaded from: classes2.dex */
    public interface OnPhotoCapture {
        void capture();
    }

    public AlbumMediaAdapter(Context context, SelectedItemCollection selectedItemCollection, RecyclerView recyclerView) {
        super(null);
        this.mSelectedCollection = selectedItemCollection;
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(new int[]{R$attr.sdk_item_placeholder});
        this.mPlaceholder = obtainStyledAttributes.getDrawable(0);
        obtainStyledAttributes.recycle();
        this.mRecyclerView = recyclerView;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder */
    public RecyclerView.ViewHolder mo6739onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i != 1) {
            if (i != 2) {
                return null;
            }
            return new MediaViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.web_sdk_media_grid_item, viewGroup, false));
        }
        CaptureViewHolder captureViewHolder = new CaptureViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.web_sdk_photo_capture_item, viewGroup, false));
        this.cameraView = captureViewHolder.cameraView;
        this.cameraView.start();
        captureViewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumMediaAdapter.1
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (view.getContext() instanceof OnPhotoCapture) {
                    ((OnPhotoCapture) view.getContext()).capture();
                }
            }
        });
        return captureViewHolder;
    }

    public void start() {
        try {
            if (this.cameraView == null || this.isStart) {
                return;
            }
            this.cameraView.start();
            this.isStart = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        try {
            if (this.cameraView == null || !this.isStart) {
                return;
            }
            this.cameraView.stop();
            this.isStart = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.RecyclerViewCursorAdapter
    protected void onBindViewHolder(RecyclerView.ViewHolder viewHolder, Cursor cursor) {
        if (!(viewHolder instanceof CaptureViewHolder) && (viewHolder instanceof MediaViewHolder)) {
            MediaViewHolder mediaViewHolder = (MediaViewHolder) viewHolder;
            Item valueOf = Item.valueOf(cursor);
            mediaViewHolder.mMediaGrid.preBindMedia(new MediaGrid.PreBindInfo(getImageResize(mediaViewHolder.mMediaGrid.getContext()), this.mPlaceholder, this.mSelectionSpec.countable, viewHolder));
            mediaViewHolder.mMediaGrid.bindMedia(valueOf);
            mediaViewHolder.mMediaGrid.setOnMediaGridClickListener(this);
            setCheckStatus(valueOf, mediaViewHolder.mMediaGrid);
        }
    }

    private void setCheckStatus(Item item, MediaGrid mediaGrid) {
        if (this.mSelectionSpec.countable) {
            int checkedNumOf = this.mSelectedCollection.checkedNumOf(item);
            if (checkedNumOf > 0) {
                mediaGrid.setCheckEnabled(true);
                mediaGrid.setCheckedNum(checkedNumOf);
            } else if (this.mSelectedCollection.maxSelectableReached()) {
                mediaGrid.setCheckEnabled(false);
                mediaGrid.setCheckedNum(Integer.MIN_VALUE);
            } else {
                mediaGrid.setCheckEnabled(true);
                mediaGrid.setCheckedNum(checkedNumOf);
            }
        } else if (this.mSelectedCollection.isSelected(item)) {
            mediaGrid.setCheckEnabled(true);
            mediaGrid.setChecked(true);
        } else if (this.mSelectedCollection.maxSelectableReached()) {
            mediaGrid.setCheckEnabled(false);
            mediaGrid.setChecked(false);
        } else {
            mediaGrid.setCheckEnabled(true);
            mediaGrid.setChecked(false);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.MediaGrid.OnMediaGridClickListener
    public void onThumbnailClicked(ImageView imageView, Item item, RecyclerView.ViewHolder viewHolder) {
        OnMediaClickListener onMediaClickListener = this.mOnMediaClickListener;
        if (onMediaClickListener != null) {
            onMediaClickListener.onMediaClick(null, item, viewHolder.getAdapterPosition());
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget.MediaGrid.OnMediaGridClickListener
    public void onCheckViewClicked(CheckView checkView, Item item, RecyclerView.ViewHolder viewHolder) {
        if (this.mSelectionSpec.countable) {
            if (this.mSelectedCollection.checkedNumOf(item) == Integer.MIN_VALUE) {
                if (!assertAddSelection(viewHolder.itemView.getContext(), item)) {
                    return;
                }
                this.mSelectedCollection.add(item);
                notifyCheckStateChanged();
                return;
            }
            this.mSelectedCollection.remove(item);
            notifyCheckStateChanged();
        } else if (this.mSelectedCollection.isSelected(item)) {
            this.mSelectedCollection.remove(item);
            notifyCheckStateChanged();
        } else if (!assertAddSelection(viewHolder.itemView.getContext(), item)) {
        } else {
            this.mSelectedCollection.add(item);
            notifyCheckStateChanged();
        }
    }

    private void notifyCheckStateChanged() {
        notifyDataSetChanged();
        CheckStateListener checkStateListener = this.mCheckStateListener;
        if (checkStateListener != null) {
            checkStateListener.onUpdate();
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.adapter.RecyclerViewCursorAdapter
    public int getItemViewType(int i, Cursor cursor) {
        return Item.valueOf(cursor).isCapture() ? 1 : 2;
    }

    private boolean assertAddSelection(Context context, Item item) {
        IncapableCause isAcceptable = this.mSelectedCollection.isAcceptable(item);
        IncapableCause.handleCause(context, isAcceptable);
        return isAcceptable == null;
    }

    public void registerCheckStateListener(CheckStateListener checkStateListener) {
        this.mCheckStateListener = checkStateListener;
    }

    public void unregisterCheckStateListener() {
        this.mCheckStateListener = null;
    }

    public void registerOnMediaClickListener(OnMediaClickListener onMediaClickListener) {
        this.mOnMediaClickListener = onMediaClickListener;
    }

    public void unregisterOnMediaClickListener() {
        this.mOnMediaClickListener = null;
    }

    public void refreshSelection() {
        GridLayoutManager gridLayoutManager = (GridLayoutManager) this.mRecyclerView.getLayoutManager();
        int findFirstVisibleItemPosition = gridLayoutManager.findFirstVisibleItemPosition();
        int findLastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
        if (findFirstVisibleItemPosition == -1 || findLastVisibleItemPosition == -1) {
            return;
        }
        Cursor cursor = getCursor();
        for (int i = findFirstVisibleItemPosition; i <= findLastVisibleItemPosition; i++) {
            RecyclerView.ViewHolder findViewHolderForAdapterPosition = this.mRecyclerView.findViewHolderForAdapterPosition(findFirstVisibleItemPosition);
            if ((findViewHolderForAdapterPosition instanceof MediaViewHolder) && cursor.moveToPosition(i)) {
                setCheckStatus(Item.valueOf(cursor), ((MediaViewHolder) findViewHolderForAdapterPosition).mMediaGrid);
            }
        }
    }

    private int getImageResize(Context context) {
        if (this.mImageResize == 0) {
            int spanCount = ((GridLayoutManager) this.mRecyclerView.getLayoutManager()).getSpanCount();
            this.mImageResize = (context.getResources().getDisplayMetrics().widthPixels - (context.getResources().getDimensionPixelSize(R$dimen.media_grid_spacing) * (spanCount - 1))) / spanCount;
            this.mImageResize = (int) (this.mImageResize * this.mSelectionSpec.thumbnailScale);
        }
        return this.mImageResize;
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumMediaAdapter$MediaViewHolder */
    /* loaded from: classes2.dex */
    private static class MediaViewHolder extends RecyclerView.ViewHolder {
        private MediaGrid mMediaGrid;

        MediaViewHolder(View view) {
            super(view);
            this.mMediaGrid = (MediaGrid) view;
        }
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.adapter.AlbumMediaAdapter$CaptureViewHolder */
    /* loaded from: classes2.dex */
    private static class CaptureViewHolder extends RecyclerView.ViewHolder {
        private CameraView cameraView;
        private ImageView mHint;

        CaptureViewHolder(View view) {
            super(view);
            this.cameraView = (CameraView) view.findViewById(R$id.camera);
            this.mHint = (ImageView) view.findViewById(R$id.hint);
        }
    }
}
