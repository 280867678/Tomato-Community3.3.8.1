package com.gen.p059mh.webapp_extensions.matisse.internal.p060ui.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.p005v7.widget.RecyclerView;
import android.text.format.DateUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapp_extensions.matisse.engine.ImageEngine;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.Item;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;

/* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.widget.MediaGrid */
/* loaded from: classes2.dex */
public class MediaGrid extends SquareFrameLayout implements View.OnClickListener {
    private CheckView mCheckView;
    private ImageView mGifTag;
    private OnMediaGridClickListener mListener;
    private Item mMedia;
    private PreBindInfo mPreBindInfo;
    private ImageView mThumbnail;
    private TextView mVideoDuration;

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.widget.MediaGrid$OnMediaGridClickListener */
    /* loaded from: classes2.dex */
    public interface OnMediaGridClickListener {
        void onCheckViewClicked(CheckView checkView, Item item, RecyclerView.ViewHolder viewHolder);

        void onThumbnailClicked(ImageView imageView, Item item, RecyclerView.ViewHolder viewHolder);
    }

    public MediaGrid(Context context) {
        super(context);
        init(context);
    }

    public MediaGrid(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater.from(context).inflate(R$layout.web_sdk_media_grid_content, (ViewGroup) this, true);
        this.mThumbnail = (ImageView) findViewById(R$id.media_thumbnail);
        this.mCheckView = (CheckView) findViewById(R$id.check_view);
        this.mGifTag = (ImageView) findViewById(R$id.gif);
        this.mVideoDuration = (TextView) findViewById(R$id.video_duration);
        this.mThumbnail.setOnClickListener(this);
        this.mCheckView.setOnClickListener(this);
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        OnMediaGridClickListener onMediaGridClickListener = this.mListener;
        if (onMediaGridClickListener != null) {
            ImageView imageView = this.mThumbnail;
            if (view == imageView) {
                onMediaGridClickListener.onThumbnailClicked(imageView, this.mMedia, this.mPreBindInfo.mViewHolder);
                return;
            }
            CheckView checkView = this.mCheckView;
            if (view != checkView) {
                return;
            }
            onMediaGridClickListener.onCheckViewClicked(checkView, this.mMedia, this.mPreBindInfo.mViewHolder);
        }
    }

    public void preBindMedia(PreBindInfo preBindInfo) {
        this.mPreBindInfo = preBindInfo;
    }

    public void bindMedia(Item item) {
        this.mMedia = item;
        setGifTag();
        initCheckView();
        setImage();
        setVideoDuration();
    }

    public Item getMedia() {
        return this.mMedia;
    }

    private void setGifTag() {
        this.mGifTag.setVisibility(this.mMedia.isGif() ? 0 : 8);
    }

    private void initCheckView() {
        this.mCheckView.setCountable(this.mPreBindInfo.mCheckViewCountable);
    }

    public void setCheckEnabled(boolean z) {
        this.mCheckView.setEnabled(z);
    }

    public void setCheckedNum(int i) {
        this.mCheckView.setCheckedNum(i);
    }

    public void setChecked(boolean z) {
        this.mCheckView.setChecked(z);
    }

    private void setImage() {
        if (this.mMedia.isGif()) {
            ImageEngine imageEngine = SelectionSpec.getInstance().imageEngine;
            Context context = getContext();
            PreBindInfo preBindInfo = this.mPreBindInfo;
            imageEngine.loadGifThumbnail(context, preBindInfo.mResize, preBindInfo.mPlaceholder, this.mThumbnail, this.mMedia.getContentUri());
            return;
        }
        ImageEngine imageEngine2 = SelectionSpec.getInstance().imageEngine;
        Context context2 = getContext();
        PreBindInfo preBindInfo2 = this.mPreBindInfo;
        imageEngine2.loadThumbnail(context2, preBindInfo2.mResize, preBindInfo2.mPlaceholder, this.mThumbnail, this.mMedia.getContentUri());
    }

    private void setVideoDuration() {
        if (this.mMedia.isVideo()) {
            this.mVideoDuration.setVisibility(0);
            this.mVideoDuration.setText(DateUtils.formatElapsedTime(this.mMedia.duration / 1000));
            return;
        }
        this.mVideoDuration.setVisibility(8);
    }

    public void setOnMediaGridClickListener(OnMediaGridClickListener onMediaGridClickListener) {
        this.mListener = onMediaGridClickListener;
    }

    public void removeOnMediaGridClickListener() {
        this.mListener = null;
    }

    /* renamed from: com.gen.mh.webapp_extensions.matisse.internal.ui.widget.MediaGrid$PreBindInfo */
    /* loaded from: classes2.dex */
    public static class PreBindInfo {
        boolean mCheckViewCountable;
        Drawable mPlaceholder;
        int mResize;
        RecyclerView.ViewHolder mViewHolder;

        public PreBindInfo(int i, Drawable drawable, boolean z, RecyclerView.ViewHolder viewHolder) {
            this.mResize = i;
            this.mPlaceholder = drawable;
            this.mCheckViewCountable = z;
            this.mViewHolder = viewHolder;
        }
    }
}
