package com.luck.picture.lib.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.p002v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.luck.picture.lib.PictureVideoPlayActivity;
import com.luck.picture.lib.R$id;
import com.luck.picture.lib.R$layout;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import java.util.List;

/* loaded from: classes3.dex */
public class SimpleFragmentAdapter extends PagerAdapter {
    private List<LocalMedia> images;
    private Context mContext;
    private OnCallBackActivity onBackPressed;

    /* loaded from: classes3.dex */
    public interface OnCallBackActivity {
        void onActivityBackPressed();
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public SimpleFragmentAdapter(List<LocalMedia> list, Context context, OnCallBackActivity onCallBackActivity) {
        this.images = list;
        this.mContext = context;
        this.onBackPressed = onCallBackActivity;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        List<LocalMedia> list = this.images;
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    /* renamed from: instantiateItem */
    public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
        String compressPath;
        View inflate = LayoutInflater.from(viewGroup.getContext()).inflate(R$layout.picture_image_preview, viewGroup, false);
        final PhotoView photoView = (PhotoView) inflate.findViewById(R$id.preview_image);
        final SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) inflate.findViewById(R$id.longImg);
        ImageView imageView = (ImageView) inflate.findViewById(R$id.iv_play);
        LocalMedia localMedia = this.images.get(i);
        if (localMedia != null) {
            String pictureType = localMedia.getPictureType();
            int i2 = 8;
            imageView.setVisibility(pictureType.startsWith("video") ? 0 : 8);
            if (localMedia.isCut() && !localMedia.isCompressed()) {
                compressPath = localMedia.getCutPath();
            } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia.isCompressed())) {
                compressPath = localMedia.getCompressPath();
            } else {
                compressPath = localMedia.getPath();
            }
            final String str = compressPath;
            boolean isGif = PictureMimeType.isGif(pictureType);
            final boolean isLongImg = PictureMimeType.isLongImg(localMedia);
            photoView.setVisibility((!isLongImg || isGif) ? 0 : 8);
            if (isLongImg && !isGif) {
                i2 = 0;
            }
            subsamplingScaleImageView.setVisibility(i2);
            if (isGif && !localMedia.isCompressed()) {
                Glide.with(inflate.getContext()).mo6720asGif().mo6729load(str).mo6653apply((BaseRequestOptions<?>) new RequestOptions().mo6694override(480, 800).mo6697priority(Priority.HIGH).mo6661diskCacheStrategy(DiskCacheStrategy.NONE)).into(photoView);
            } else {
                Glide.with(inflate.getContext()).mo6717asBitmap().mo6729load(str).mo6653apply((BaseRequestOptions<?>) new RequestOptions().mo6661diskCacheStrategy(DiskCacheStrategy.ALL)).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>(480, 800) { // from class: com.luck.picture.lib.adapter.SimpleFragmentAdapter.1
                    @Override // com.bumptech.glide.request.target.Target
                    public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                        onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                    }

                    public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                        if (isLongImg) {
                            SimpleFragmentAdapter.this.displayLongPic(str, bitmap, subsamplingScaleImageView);
                        } else {
                            photoView.setImageBitmap(bitmap);
                        }
                    }
                });
            }
            photoView.setOnViewTapListener(new OnViewTapListener() { // from class: com.luck.picture.lib.adapter.SimpleFragmentAdapter.2
                @Override // com.luck.picture.lib.photoview.OnViewTapListener
                public void onViewTap(View view, float f, float f2) {
                    if (SimpleFragmentAdapter.this.onBackPressed != null) {
                        SimpleFragmentAdapter.this.onBackPressed.onActivityBackPressed();
                    }
                }
            });
            subsamplingScaleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.SimpleFragmentAdapter.3
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    if (SimpleFragmentAdapter.this.onBackPressed != null) {
                        SimpleFragmentAdapter.this.onBackPressed.onActivityBackPressed();
                    }
                }
            });
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.adapter.SimpleFragmentAdapter.4
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    Intent intent = new Intent();
                    Bundle bundle = new Bundle();
                    bundle.putString("video_path", str);
                    intent.putExtras(bundle);
                    intent.setClass(SimpleFragmentAdapter.this.mContext, PictureVideoPlayActivity.class);
                    SimpleFragmentAdapter.this.mContext.startActivity(intent);
                }
            });
        }
        viewGroup.addView(inflate, 0);
        return inflate;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void displayLongPic(String str, Bitmap bitmap, SubsamplingScaleImageView subsamplingScaleImageView) {
        subsamplingScaleImageView.setQuickScaleEnabled(true);
        subsamplingScaleImageView.setZoomEnabled(true);
        subsamplingScaleImageView.setPanEnabled(true);
        subsamplingScaleImageView.setDoubleTapZoomDuration(100);
        subsamplingScaleImageView.setMinimumScaleType(2);
        subsamplingScaleImageView.setDoubleTapZoomDpi(2);
        subsamplingScaleImageView.setImage(ImageSource.uri(str), new ImageViewState(1.0f, new PointF(0.0f, 0.0f), 0));
    }
}
