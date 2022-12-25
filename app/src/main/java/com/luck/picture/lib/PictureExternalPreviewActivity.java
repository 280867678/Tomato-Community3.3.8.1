package com.luck.picture.lib;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.p002v4.app.FragmentActivity;
import android.support.p002v4.view.PagerAdapter;
import android.support.p002v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.permissions.RxPermissions;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.luck.picture.lib.tools.ToastManage;
import com.luck.picture.lib.widget.PreviewViewPager;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class PictureExternalPreviewActivity extends PictureBaseActivity implements View.OnClickListener {
    private SimpleFragmentAdapter adapter;
    private LayoutInflater inflater;
    private ImageButton left_back;
    private loadDataThread loadDataThread;
    private RxPermissions rxPermissions;
    private TextView tv_title;
    private PreviewViewPager viewPager;
    private List<LocalMedia> images = new ArrayList();
    private int position = 0;
    private Handler handler = new Handler() { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.4
        @Override // android.os.Handler
        public void handleMessage(Message message) {
            super.handleMessage(message);
            if (message.what != 200) {
                return;
            }
            Context context = PictureExternalPreviewActivity.this.mContext;
            ToastManage.m3846s(context, PictureExternalPreviewActivity.this.getString(R$string.picture_save_success) + "\n" + ((String) message.obj));
            PictureExternalPreviewActivity.this.dismissDialog();
        }
    };

    /* loaded from: classes3.dex */
    public class loadDataThread extends Thread {
    }

    @Override // com.luck.picture.lib.PictureBaseActivity, android.support.p002v4.app.FragmentActivity, android.support.p002v4.app.ComponentActivity, android.app.Activity
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R$layout.picture_activity_external_preview);
        this.inflater = LayoutInflater.from(this);
        this.tv_title = (TextView) findViewById(R$id.picture_title);
        this.left_back = (ImageButton) findViewById(R$id.left_back);
        this.viewPager = (PreviewViewPager) findViewById(R$id.preview_pager);
        this.position = getIntent().getIntExtra("position", 0);
        getIntent().getStringExtra("directory_path");
        this.images = (List) getIntent().getSerializableExtra("previewSelectList");
        this.left_back.setOnClickListener(this);
        initViewPageAdapterData();
    }

    private void initViewPageAdapterData() {
        TextView textView = this.tv_title;
        textView.setText((this.position + 1) + "/" + this.images.size());
        this.adapter = new SimpleFragmentAdapter();
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setCurrentItem(this.position);
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.1
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                TextView textView2 = PictureExternalPreviewActivity.this.tv_title;
                textView2.setText((i + 1) + "/" + PictureExternalPreviewActivity.this.images.size());
            }
        });
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        finish();
        overridePendingTransition(0, R$anim.f1555a3);
    }

    /* loaded from: classes3.dex */
    public class SimpleFragmentAdapter extends PagerAdapter {
        @Override // android.support.p002v4.view.PagerAdapter
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }

        public SimpleFragmentAdapter() {
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public int getCount() {
            return PictureExternalPreviewActivity.this.images.size();
        }

        @Override // android.support.p002v4.view.PagerAdapter
        public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
            viewGroup.removeView((View) obj);
        }

        @Override // android.support.p002v4.view.PagerAdapter
        /* renamed from: instantiateItem */
        public Object mo6346instantiateItem(ViewGroup viewGroup, int i) {
            String compressPath;
            View inflate = PictureExternalPreviewActivity.this.inflater.inflate(R$layout.picture_image_preview, viewGroup, false);
            final PhotoView photoView = (PhotoView) inflate.findViewById(R$id.preview_image);
            final SubsamplingScaleImageView subsamplingScaleImageView = (SubsamplingScaleImageView) inflate.findViewById(R$id.longImg);
            LocalMedia localMedia = (LocalMedia) PictureExternalPreviewActivity.this.images.get(i);
            if (localMedia != null) {
                String pictureType = localMedia.getPictureType();
                if (localMedia.isCut() && !localMedia.isCompressed()) {
                    compressPath = localMedia.getCutPath();
                } else if (localMedia.isCompressed() || (localMedia.isCut() && localMedia.isCompressed())) {
                    compressPath = localMedia.getCompressPath();
                } else {
                    compressPath = localMedia.getPath();
                }
                final String str = compressPath;
                if (PictureMimeType.isHttp(str)) {
                    PictureExternalPreviewActivity.this.showPleaseDialog();
                }
                boolean isGif = PictureMimeType.isGif(pictureType);
                final boolean isLongImg = PictureMimeType.isLongImg(localMedia);
                int i2 = 8;
                photoView.setVisibility((!isLongImg || isGif) ? 0 : 8);
                if (isLongImg && !isGif) {
                    i2 = 0;
                }
                subsamplingScaleImageView.setVisibility(i2);
                if (isGif && !localMedia.isCompressed()) {
                    Glide.with((FragmentActivity) PictureExternalPreviewActivity.this).mo6720asGif().mo6653apply((BaseRequestOptions<?>) new RequestOptions().mo6694override(480, 800).mo6697priority(Priority.HIGH).mo6661diskCacheStrategy(DiskCacheStrategy.NONE)).mo6729load(str).mo6676listener(new RequestListener<GifDrawable>() { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.SimpleFragmentAdapter.1
                        @Override // com.bumptech.glide.request.RequestListener
                        public boolean onLoadFailed(@Nullable GlideException glideException, Object obj, Target<GifDrawable> target, boolean z) {
                            PictureExternalPreviewActivity.this.dismissDialog();
                            return false;
                        }

                        @Override // com.bumptech.glide.request.RequestListener
                        public boolean onResourceReady(GifDrawable gifDrawable, Object obj, Target<GifDrawable> target, DataSource dataSource, boolean z) {
                            PictureExternalPreviewActivity.this.dismissDialog();
                            return false;
                        }
                    }).into(photoView);
                } else {
                    Glide.with((FragmentActivity) PictureExternalPreviewActivity.this).mo6717asBitmap().mo6729load(str).mo6653apply((BaseRequestOptions<?>) new RequestOptions().mo6661diskCacheStrategy(DiskCacheStrategy.ALL)).into((RequestBuilder<Bitmap>) new SimpleTarget<Bitmap>(480, 800) { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.SimpleFragmentAdapter.2
                        @Override // com.bumptech.glide.request.target.Target
                        public /* bridge */ /* synthetic */ void onResourceReady(Object obj, Transition transition) {
                            onResourceReady((Bitmap) obj, (Transition<? super Bitmap>) transition);
                        }

                        @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
                        public void onLoadFailed(@Nullable Drawable drawable) {
                            super.onLoadFailed(drawable);
                            PictureExternalPreviewActivity.this.dismissDialog();
                        }

                        public void onResourceReady(Bitmap bitmap, Transition<? super Bitmap> transition) {
                            PictureExternalPreviewActivity.this.dismissDialog();
                            if (isLongImg) {
                                PictureExternalPreviewActivity.this.displayLongPic(str, bitmap, subsamplingScaleImageView);
                            } else {
                                photoView.setImageBitmap(bitmap);
                            }
                        }
                    });
                }
                photoView.setOnViewTapListener(new OnViewTapListener() { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.SimpleFragmentAdapter.3
                    @Override // com.luck.picture.lib.photoview.OnViewTapListener
                    public void onViewTap(View view, float f, float f2) {
                        PictureExternalPreviewActivity.this.finish();
                        PictureExternalPreviewActivity.this.overridePendingTransition(0, R$anim.f1555a3);
                    }
                });
                subsamplingScaleImageView.setOnClickListener(new View.OnClickListener() { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.SimpleFragmentAdapter.4
                    @Override // android.view.View.OnClickListener
                    public void onClick(View view) {
                        PictureExternalPreviewActivity.this.finish();
                        PictureExternalPreviewActivity.this.overridePendingTransition(0, R$anim.f1555a3);
                    }
                });
                photoView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.SimpleFragmentAdapter.5
                    @Override // android.view.View.OnLongClickListener
                    public boolean onLongClick(View view) {
                        if (PictureExternalPreviewActivity.this.rxPermissions == null) {
                            PictureExternalPreviewActivity pictureExternalPreviewActivity = PictureExternalPreviewActivity.this;
                            pictureExternalPreviewActivity.rxPermissions = new RxPermissions(pictureExternalPreviewActivity);
                        }
                        PictureExternalPreviewActivity.this.rxPermissions.request("android.permission.WRITE_EXTERNAL_STORAGE").subscribe(new Observer<Boolean>(this) { // from class: com.luck.picture.lib.PictureExternalPreviewActivity.SimpleFragmentAdapter.5.1
                            @Override // io.reactivex.Observer
                            public void onComplete() {
                            }

                            @Override // io.reactivex.Observer
                            public void onError(Throwable th) {
                            }

                            @Override // io.reactivex.Observer
                            public void onNext(Boolean bool) {
                            }

                            @Override // io.reactivex.Observer
                            public void onSubscribe(Disposable disposable) {
                            }
                        });
                        return true;
                    }
                });
            }
            viewGroup.addView(inflate, 0);
            return inflate;
        }
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

    @Override // android.support.p002v4.app.FragmentActivity, android.app.Activity
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(0, R$anim.f1555a3);
    }

    @Override // com.luck.picture.lib.PictureBaseActivity, android.support.p002v4.app.FragmentActivity, android.app.Activity
    protected void onDestroy() {
        super.onDestroy();
        loadDataThread loaddatathread = this.loadDataThread;
        if (loaddatathread != null) {
            this.handler.removeCallbacks(loaddatathread);
            this.loadDataThread = null;
        }
    }
}
