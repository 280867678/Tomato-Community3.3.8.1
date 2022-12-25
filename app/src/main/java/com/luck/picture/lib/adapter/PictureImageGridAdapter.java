package com.luck.picture.lib.adapter;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PorterDuff;
import android.support.p002v4.content.ContextCompat;
import android.support.p005v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.luck.picture.lib.R$anim;
import com.luck.picture.lib.R$color;
import com.luck.picture.lib.R$drawable;
import com.luck.picture.lib.R$id;
import com.luck.picture.lib.R$layout;
import com.luck.picture.lib.R$string;
import com.luck.picture.lib.anim.OptAnimationLoader;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.config.PictureSelectionConfig;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.DateUtils;
import com.luck.picture.lib.tools.StringUtils;
import com.luck.picture.lib.tools.ToastManage;
import com.luck.picture.lib.tools.VoiceUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes3.dex */
public class PictureImageGridAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Animation animation;
    private PictureSelectionConfig config;
    private Context context;
    private boolean enablePreview;
    private boolean enablePreviewAudio;
    private boolean enablePreviewVideo;
    private boolean enableVoice;
    private OnPhotoSelectChangedListener imageSelectChangedListener;
    private boolean isGo;
    private boolean is_checked_num;
    private int maxSelectNum;
    private int mimeType;
    private int overrideHeight;
    private int overrideWidth;
    private int selectMode;
    private boolean showCamera;
    private float sizeMultiplier;
    private boolean zoomAnim;
    private List<LocalMedia> images = new ArrayList();
    private List<LocalMedia> selectImages = new ArrayList();

    /* loaded from: classes3.dex */
    public interface OnPhotoSelectChangedListener {
        void onChange(List<LocalMedia> list);

        void onPictureClick(LocalMedia localMedia, int i);

        void onTakePhoto();
    }

    public PictureImageGridAdapter(Context context, PictureSelectionConfig pictureSelectionConfig) {
        this.showCamera = true;
        this.selectMode = 2;
        this.enablePreviewVideo = false;
        this.enablePreviewAudio = false;
        this.context = context;
        this.config = pictureSelectionConfig;
        this.selectMode = pictureSelectionConfig.selectionMode;
        this.showCamera = pictureSelectionConfig.isCamera;
        this.maxSelectNum = pictureSelectionConfig.maxSelectNum;
        this.enablePreview = pictureSelectionConfig.enablePreview;
        this.enablePreviewVideo = pictureSelectionConfig.enPreviewVideo;
        this.enablePreviewAudio = pictureSelectionConfig.enablePreviewAudio;
        this.is_checked_num = pictureSelectionConfig.checkNumMode;
        this.overrideWidth = pictureSelectionConfig.overrideWidth;
        this.overrideHeight = pictureSelectionConfig.overrideHeight;
        this.enableVoice = pictureSelectionConfig.openClickSound;
        this.sizeMultiplier = pictureSelectionConfig.sizeMultiplier;
        this.mimeType = pictureSelectionConfig.mimeType;
        this.zoomAnim = pictureSelectionConfig.zoomAnim;
        this.animation = OptAnimationLoader.loadAnimation(context, R$anim.modal_in);
    }

    public void setShowCamera(boolean z) {
        this.showCamera = z;
    }

    public void bindImagesData(List<LocalMedia> list) {
        this.images = list;
        notifyDataSetChanged();
    }

    public void bindSelectImages(List<LocalMedia> list) {
        ArrayList arrayList = new ArrayList();
        for (LocalMedia localMedia : list) {
            arrayList.add(localMedia);
        }
        this.selectImages = arrayList;
        subSelectPosition();
        OnPhotoSelectChangedListener onPhotoSelectChangedListener = this.imageSelectChangedListener;
        if (onPhotoSelectChangedListener != null) {
            onPhotoSelectChangedListener.onChange(this.selectImages);
        }
    }

    public List<LocalMedia> getSelectedImages() {
        if (this.selectImages == null) {
            this.selectImages = new ArrayList();
        }
        return this.selectImages;
    }

    public List<LocalMedia> getImages() {
        if (this.images == null) {
            this.images = new ArrayList();
        }
        return this.images;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemViewType(int i) {
        return (!this.showCamera || i != 0) ? 2 : 1;
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    /* renamed from: onCreateViewHolder */
    public RecyclerView.ViewHolder mo6739onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == 1) {
            return new HeaderViewHolder(this, LayoutInflater.from(this.context).inflate(R$layout.picture_item_camera, viewGroup, false));
        }
        return new ViewHolder(this, LayoutInflater.from(this.context).inflate(R$layout.picture_image_grid_item, viewGroup, false));
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int i) {
        if (getItemViewType(i) == 1) {
            ((HeaderViewHolder) viewHolder).headerView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.PictureImageGridAdapter.1
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (PictureImageGridAdapter.this.imageSelectChangedListener != null) {
                        PictureImageGridAdapter.this.imageSelectChangedListener.onTakePhoto();
                    }
                }
            });
            return;
        }
        final ViewHolder viewHolder2 = (ViewHolder) viewHolder;
        final LocalMedia localMedia = this.images.get(this.showCamera ? i - 1 : i);
        localMedia.position = viewHolder2.getAdapterPosition();
        final String path = localMedia.getPath();
        String pictureType = localMedia.getPictureType();
        if (this.is_checked_num) {
            notifyCheckChanged(viewHolder2, localMedia);
        }
        int i2 = 0;
        selectImage(viewHolder2, isSelected(localMedia), false);
        final int isPictureType = PictureMimeType.isPictureType(pictureType);
        viewHolder2.tv_isGif.setVisibility(PictureMimeType.isGif(pictureType) ? 0 : 8);
        if (this.mimeType == PictureMimeType.ofAudio()) {
            viewHolder2.tv_duration.setVisibility(0);
            StringUtils.modifyTextViewDrawable(viewHolder2.tv_duration, ContextCompat.getDrawable(this.context, R$drawable.picture_audio), 0);
        } else {
            StringUtils.modifyTextViewDrawable(viewHolder2.tv_duration, ContextCompat.getDrawable(this.context, R$drawable.video_icon), 0);
            viewHolder2.tv_duration.setVisibility(isPictureType == 2 ? 0 : 8);
        }
        boolean isLongImg = PictureMimeType.isLongImg(localMedia);
        TextView textView = viewHolder2.tv_long_chart;
        if (!isLongImg) {
            i2 = 8;
        }
        textView.setVisibility(i2);
        viewHolder2.tv_duration.setText(DateUtils.timeParse(localMedia.getDuration()));
        if (this.mimeType == PictureMimeType.ofAudio()) {
            viewHolder2.iv_picture.setImageResource(R$drawable.audio_placeholder);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            if (this.overrideWidth <= 0 && this.overrideHeight <= 0) {
                requestOptions.mo6700sizeMultiplier(this.sizeMultiplier);
            } else {
                requestOptions.mo6694override(this.overrideWidth, this.overrideHeight);
            }
            requestOptions.mo6661diskCacheStrategy(DiskCacheStrategy.ALL);
            requestOptions.mo6654centerCrop();
            requestOptions.mo6695placeholder(R$drawable.image_placeholder);
            Glide.with(this.context).mo6717asBitmap().mo6729load(path).mo6653apply((BaseRequestOptions<?>) requestOptions).into(viewHolder2.iv_picture);
        }
        if (this.enablePreview || this.enablePreviewVideo || this.enablePreviewAudio) {
            viewHolder2.ll_check.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.PictureImageGridAdapter.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (!new File(path).exists()) {
                        ToastManage.m3846s(PictureImageGridAdapter.this.context, PictureMimeType.m3847s(PictureImageGridAdapter.this.context, isPictureType));
                    } else {
                        PictureImageGridAdapter.this.changeCheckboxState(viewHolder2, localMedia);
                    }
                }
            });
        }
        viewHolder2.contentView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.PictureImageGridAdapter.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (!new File(path).exists()) {
                    ToastManage.m3846s(PictureImageGridAdapter.this.context, PictureMimeType.m3847s(PictureImageGridAdapter.this.context, isPictureType));
                    return;
                }
                boolean z = true;
                int i3 = PictureImageGridAdapter.this.showCamera ? i - 1 : i;
                if ((isPictureType != 1 || !PictureImageGridAdapter.this.enablePreview) && ((isPictureType != 2 || (!PictureImageGridAdapter.this.enablePreviewVideo && PictureImageGridAdapter.this.selectMode != 1)) && (isPictureType != 3 || (!PictureImageGridAdapter.this.enablePreviewAudio && PictureImageGridAdapter.this.selectMode != 1)))) {
                    z = false;
                }
                if (z) {
                    PictureImageGridAdapter.this.imageSelectChangedListener.onPictureClick(localMedia, i3);
                } else {
                    PictureImageGridAdapter.this.changeCheckboxState(viewHolder2, localMedia);
                }
            }
        });
    }

    @Override // android.support.p005v7.widget.RecyclerView.Adapter
    public int getItemCount() {
        return this.showCamera ? this.images.size() + 1 : this.images.size();
    }

    /* loaded from: classes3.dex */
    public class HeaderViewHolder extends RecyclerView.ViewHolder {
        View headerView;
        TextView tv_title_camera;

        public HeaderViewHolder(PictureImageGridAdapter pictureImageGridAdapter, View view) {
            super(view);
            this.headerView = view;
            this.tv_title_camera = (TextView) view.findViewById(R$id.tv_title_camera);
            this.tv_title_camera.setText(pictureImageGridAdapter.mimeType == PictureMimeType.ofAudio() ? pictureImageGridAdapter.context.getString(R$string.picture_tape) : pictureImageGridAdapter.context.getString(R$string.picture_take_picture));
        }
    }

    /* loaded from: classes3.dex */
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView check;
        View contentView;
        ImageView iv_picture;
        LinearLayout ll_check;
        TextView tv_duration;
        TextView tv_isGif;
        TextView tv_long_chart;

        public ViewHolder(PictureImageGridAdapter pictureImageGridAdapter, View view) {
            super(view);
            this.contentView = view;
            this.iv_picture = (ImageView) view.findViewById(R$id.iv_picture);
            this.check = (TextView) view.findViewById(R$id.check);
            this.ll_check = (LinearLayout) view.findViewById(R$id.ll_check);
            this.tv_duration = (TextView) view.findViewById(R$id.tv_duration);
            this.tv_isGif = (TextView) view.findViewById(R$id.tv_isGif);
            this.tv_long_chart = (TextView) view.findViewById(R$id.tv_long_chart);
        }
    }

    public boolean isSelected(LocalMedia localMedia) {
        for (LocalMedia localMedia2 : this.selectImages) {
            if (localMedia2.getPath().equals(localMedia.getPath())) {
                return true;
            }
        }
        return false;
    }

    private void notifyCheckChanged(ViewHolder viewHolder, LocalMedia localMedia) {
        viewHolder.check.setText("");
        for (LocalMedia localMedia2 : this.selectImages) {
            if (localMedia2.getPath().equals(localMedia.getPath())) {
                localMedia.setNum(localMedia2.getNum());
                localMedia2.setPosition(localMedia.getPosition());
                viewHolder.check.setText(String.valueOf(localMedia.getNum()));
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void changeCheckboxState(ViewHolder viewHolder, LocalMedia localMedia) {
        boolean isSelected = viewHolder.check.isSelected();
        String pictureType = this.selectImages.size() > 0 ? this.selectImages.get(0).getPictureType() : "";
        if (!TextUtils.isEmpty(pictureType) && !PictureMimeType.mimeToEqual(pictureType, localMedia.getPictureType())) {
            Context context = this.context;
            ToastManage.m3846s(context, context.getString(R$string.picture_rule));
        } else if (this.selectImages.size() >= this.maxSelectNum && !isSelected) {
            ToastManage.m3846s(this.context, pictureType.startsWith("image") ? this.context.getString(R$string.picture_message_max_num, Integer.valueOf(this.maxSelectNum)) : this.context.getString(R$string.picture_message_video_max_num, Integer.valueOf(this.maxSelectNum)));
        } else {
            if (isSelected) {
                Iterator<LocalMedia> it2 = this.selectImages.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    LocalMedia next = it2.next();
                    if (next.getPath().equals(localMedia.getPath())) {
                        this.selectImages.remove(next);
                        subSelectPosition();
                        disZoom(viewHolder.iv_picture);
                        break;
                    }
                }
            } else {
                if (this.selectMode == 1) {
                    singleRadioMediaImage();
                }
                this.selectImages.add(localMedia);
                localMedia.setNum(this.selectImages.size());
                VoiceUtils.playVoice(this.context, this.enableVoice);
                zoom(viewHolder.iv_picture);
            }
            notifyItemChanged(viewHolder.getAdapterPosition());
            selectImage(viewHolder, !isSelected, true);
            OnPhotoSelectChangedListener onPhotoSelectChangedListener = this.imageSelectChangedListener;
            if (onPhotoSelectChangedListener == null) {
                return;
            }
            onPhotoSelectChangedListener.onChange(this.selectImages);
        }
    }

    private void singleRadioMediaImage() {
        List<LocalMedia> list = this.selectImages;
        if (list == null || list.size() <= 0) {
            return;
        }
        this.isGo = true;
        int i = 0;
        LocalMedia localMedia = this.selectImages.get(0);
        if (!this.config.isCamera && !this.isGo) {
            int i2 = localMedia.position;
            if (i2 > 0) {
                i = i2 - 1;
            }
        } else {
            i = localMedia.position;
        }
        notifyItemChanged(i);
        this.selectImages.clear();
    }

    private void subSelectPosition() {
        if (this.is_checked_num) {
            int size = this.selectImages.size();
            int i = 0;
            while (i < size) {
                LocalMedia localMedia = this.selectImages.get(i);
                i++;
                localMedia.setNum(i);
                notifyItemChanged(localMedia.position);
            }
        }
    }

    public void selectImage(ViewHolder viewHolder, boolean z, boolean z2) {
        Animation animation;
        viewHolder.check.setSelected(z);
        if (z) {
            if (z2 && (animation = this.animation) != null) {
                viewHolder.check.startAnimation(animation);
            }
            viewHolder.iv_picture.setColorFilter(ContextCompat.getColor(this.context, R$color.image_overlay_true), PorterDuff.Mode.SRC_ATOP);
            return;
        }
        viewHolder.iv_picture.setColorFilter(ContextCompat.getColor(this.context, R$color.image_overlay_false), PorterDuff.Mode.SRC_ATOP);
    }

    public void setOnPhotoSelectChangedListener(OnPhotoSelectChangedListener onPhotoSelectChangedListener) {
        this.imageSelectChangedListener = onPhotoSelectChangedListener;
    }

    private void zoom(ImageView imageView) {
        if (this.zoomAnim) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ObjectAnimator.ofFloat(imageView, "scaleX", 1.0f, 1.12f), ObjectAnimator.ofFloat(imageView, "scaleY", 1.0f, 1.12f));
            animatorSet.setDuration(450L);
            animatorSet.start();
        }
    }

    private void disZoom(ImageView imageView) {
        if (this.zoomAnim) {
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.playTogether(ObjectAnimator.ofFloat(imageView, "scaleX", 1.12f, 1.0f), ObjectAnimator.ofFloat(imageView, "scaleY", 1.12f, 1.0f));
            animatorSet.setDuration(450L);
            animatorSet.start();
        }
    }
}
