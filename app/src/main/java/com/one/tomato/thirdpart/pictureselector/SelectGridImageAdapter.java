package com.one.tomato.thirdpart.pictureselector;

import android.content.Context;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.broccoli.p150bh.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.one.tomato.widget.SquareImageView;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class SelectGridImageAdapter extends RecyclerView.Adapter<ViewHolder> {
    private int add_mark_img_bgId;
    private Context context;
    private LayoutInflater mInflater;
    public OnItemClickListener mItemClickListener;
    public OnAddPicClickListener mOnAddPicClickListener;
    public OnItemChangeListener mOnItemChangeListener;
    private List<LocalMedia> list = new ArrayList();
    private int selectMax = 9;

    /* loaded from: classes3.dex */
    public interface OnAddPicClickListener {
        void onAddPicClick();
    }

    /* loaded from: classes3.dex */
    public interface OnItemChangeListener {
        void onItemClear();

        void onItemRemove(int i);
    }

    /* loaded from: classes3.dex */
    public interface OnItemClickListener {
        void onItemClick(int i, View view);
    }

    public SelectGridImageAdapter(Context context) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.add_mark_img_bgId = context.getResources().getColor(R.color.app_bg_grey);
    }

    public void setAdd_mark_img_bg_id(int i) {
        this.add_mark_img_bgId = i;
        notifyDataSetChanged();
    }

    public void setSelectMax(int i) {
        this.selectMax = i;
    }

    public void setList(List<LocalMedia> list) {
        this.list = list;
    }

    /* loaded from: classes3.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_del;
        SquareImageView mImg;
        TextView tv_duration;
        TextView tv_recharge_demo;

        public ViewHolder(SelectGridImageAdapter selectGridImageAdapter, View view) {
            super(view);
            this.mImg = (SquareImageView) view.findViewById(R.id.fiv);
            this.iv_del = (ImageView) view.findViewById(R.id.iv_delete);
            this.tv_duration = (TextView) view.findViewById(R.id.tv_duration);
            this.tv_recharge_demo = (TextView) view.findViewById(R.id.tv_recharge_demo);
        }
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        if (this.list.size() < this.selectMax) {
            return this.list.size() + 1;
        }
        return this.list.size();
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return isShowAddItem(i) ? 1 : 2;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder  reason: collision with other method in class */
    public ViewHolder mo6739onCreateViewHolder(ViewGroup viewGroup, int i) {
        return new ViewHolder(this, this.mInflater.inflate(R.layout.item_gv_filter_image, viewGroup, false));
    }

    private boolean isShowAddItem(int i) {
        return i == (this.list.size() == 0 ? 0 : this.list.size());
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        Object compressPath;
        if (getItemViewType(i) == 1) {
            viewHolder.mImg.setBackgroundColor(this.add_mark_img_bgId);
            viewHolder.mImg.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    OnAddPicClickListener onAddPicClickListener = SelectGridImageAdapter.this.mOnAddPicClickListener;
                    if (onAddPicClickListener != null) {
                        onAddPicClickListener.onAddPicClick();
                    }
                }
            });
            viewHolder.iv_del.setVisibility(4);
            return;
        }
        viewHolder.iv_del.setVisibility(0);
        viewHolder.iv_del.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                OnItemChangeListener onItemChangeListener;
                int adapterPosition = viewHolder.getAdapterPosition();
                if (adapterPosition != -1) {
                    SelectGridImageAdapter.this.list.remove(adapterPosition);
                    OnItemChangeListener onItemChangeListener2 = SelectGridImageAdapter.this.mOnItemChangeListener;
                    if (onItemChangeListener2 != null) {
                        onItemChangeListener2.onItemRemove(adapterPosition);
                    }
                    SelectGridImageAdapter.this.notifyItemRemoved(adapterPosition);
                    SelectGridImageAdapter selectGridImageAdapter = SelectGridImageAdapter.this;
                    selectGridImageAdapter.notifyItemRangeChanged(adapterPosition, selectGridImageAdapter.list.size());
                    if (SelectGridImageAdapter.this.list == null || !SelectGridImageAdapter.this.list.isEmpty() || (onItemChangeListener = SelectGridImageAdapter.this.mOnItemChangeListener) == null) {
                        return;
                    }
                    onItemChangeListener.onItemClear();
                }
            }
        });
        LocalMedia localMedia = this.list.get(i);
        if (localMedia.isDemo()) {
            viewHolder.iv_del.setVisibility(4);
            viewHolder.tv_recharge_demo.setVisibility(0);
        }
        int mimeType = localMedia.getMimeType();
        if (localMedia.isCut() && !localMedia.isCompressed()) {
            compressPath = localMedia.getCutPath();
        } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia.isCompressed())) {
            compressPath = localMedia.getCompressPath();
        } else {
            compressPath = localMedia.getPath();
        }
        if (localMedia.isCompressed()) {
            Log.i("compress image result:", (new File(localMedia.getCompressPath()).length() / 1024) + "k");
            Log.i("压缩地址::", localMedia.getCompressPath());
        }
        Log.i("原图地址::", localMedia.getPath());
        int isPictureType = PictureMimeType.isPictureType(localMedia.getPictureType());
        if (localMedia.isCut()) {
            Log.i("裁剪地址::", localMedia.getCutPath());
        }
        long duration = localMedia.getDuration();
        viewHolder.tv_duration.setVisibility(isPictureType == 2 ? 0 : 8);
        if (mimeType == PictureMimeType.ofAudio()) {
            viewHolder.tv_duration.setVisibility(0);
            StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, ContextCompat.getDrawable(this.context, R.drawable.picture_audio), 0);
        } else {
            StringUtils.modifyTextViewDrawable(viewHolder.tv_duration, ContextCompat.getDrawable(this.context, R.drawable.icon_video), 0);
        }
        viewHolder.tv_duration.setText(DateUtils.timeParse(duration));
        if (mimeType == PictureMimeType.ofAudio()) {
            viewHolder.mImg.setImageResource(R.drawable.audio_placeholder);
        } else {
            RequestOptions mo6661diskCacheStrategy = new RequestOptions().mo6654centerCrop().mo6661diskCacheStrategy(DiskCacheStrategy.ALL);
            RequestManager with = Glide.with(viewHolder.itemView.getContext());
            if (localMedia.isDemo()) {
                compressPath = Integer.valueOf(localMedia.getDemoResId());
            }
            with.mo6728load(compressPath).mo6653apply((BaseRequestOptions<?>) mo6661diskCacheStrategy).into(viewHolder.mImg);
        }
        if (this.mItemClickListener == null) {
            return;
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.thirdpart.pictureselector.SelectGridImageAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                SelectGridImageAdapter.this.mItemClickListener.onItemClick(viewHolder.getAdapterPosition(), view);
            }
        });
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnAddPicClickListener(OnAddPicClickListener onAddPicClickListener) {
        this.mOnAddPicClickListener = onAddPicClickListener;
    }

    public void setOnItemChangeListener(OnItemChangeListener onItemChangeListener) {
        this.mOnItemChangeListener = onItemChangeListener;
    }
}
