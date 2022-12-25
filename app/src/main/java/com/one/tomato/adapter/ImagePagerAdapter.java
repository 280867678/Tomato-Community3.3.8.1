package com.one.tomato.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.p002v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import com.broccoli.p150bh.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.luck.picture.lib.photoview.OnViewTapListener;
import com.luck.picture.lib.photoview.PhotoView;
import com.one.tomato.dialog.ImageDownloadDialog;
import com.one.tomato.dialog.VideoSaveTipDialog;
import com.one.tomato.entity.ImageBean;
import com.one.tomato.entity.PostList;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.p080ui.p082up.PostRewardPayUtils;
import com.one.tomato.mvp.p080ui.showimage.ImageShowActivity;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.utils.AppSecretUtil;
import com.one.tomato.utils.AppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.ToastUtil;
import com.one.tomato.utils.encrypt.MD5Util;
import com.one.tomato.utils.image.ImageGlideUrl;
import com.one.tomato.utils.image.ImageLoaderUtil;
import com.one.tomato.utils.post.VideoPlayCountUtils;
import com.shizhefei.view.largeimage.LargeImageView;
import com.shizhefei.view.largeimage.factory.FileBitmapDecoderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import kotlin.jvm.functions.Function1;

/* loaded from: classes3.dex */
public class ImagePagerAdapter extends PagerAdapter {
    private BottomSheetBehavior behavior;
    private Context context;
    private ImageDownloadDialog downloadDialog;
    private ArrayList<ImageBean> imageBeans = new ArrayList<>();
    private LayoutInflater inflater;
    private PostList postList;

    public ImagePagerAdapter(Context context, ArrayList<ImageBean> arrayList) {
        new ArrayList();
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.imageBeans.addAll(arrayList);
    }

    public void setPostList(PostList postList) {
        this.postList = postList;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    /* renamed from: instantiateItem */
    public Object mo6346instantiateItem(ViewGroup viewGroup, final int i) {
        View inflate = this.inflater.inflate(R.layout.item_show_img, viewGroup, false);
        ViewGroup viewGroup2 = (ViewGroup) inflate.getParent();
        if (viewGroup2 != null) {
            viewGroup2.removeAllViews();
        }
        PhotoView photoView = (PhotoView) inflate.findViewById(R.id.iv_image);
        LargeImageView largeImageView = (LargeImageView) inflate.findViewById(R.id.longImg);
        ProgressBar progressBar = (ProgressBar) inflate.findViewById(R.id.loading);
        final ImageBean imageBean = this.imageBeans.get(i);
        if (imageBean.isLocal()) {
            largeImageView.setVisibility(0);
            photoView.setVisibility(8);
            largeImageView.setImage(new FileBitmapDecoderFactory(imageBean.getImage()));
        } else {
            int width = imageBean.getWidth();
            int height = imageBean.getHeight();
            progressBar.setVisibility(0);
            if (height > width * 3) {
                largeImageView.setVisibility(0);
                photoView.setVisibility(8);
                if (imageBean.isSecret()) {
                    ImageLoaderUtil.loadViewPagerLongImage(this.context, largeImageView, progressBar, imageBean);
                } else if (imageBean.isAbsolute()) {
                    largeImageView.setVisibility(8);
                    photoView.setVisibility(0);
                    progressBar.setVisibility(8);
                    ImageLoaderUtil.loadNormalAbsoluteImage(this.context, photoView, imageBean.getImage(), ImageLoaderUtil.getFitCenterImageOption(photoView));
                }
            } else {
                photoView.setVisibility(0);
                largeImageView.setVisibility(8);
                if (imageBean.isSecret()) {
                    ImageLoaderUtil.loadViewPagerOriginImage(this.context, photoView, progressBar, imageBean, R.drawable.video_cover_default);
                } else if (imageBean.isAbsolute()) {
                    progressBar.setVisibility(8);
                    ImageLoaderUtil.loadNormalAbsoluteImage(this.context, photoView, imageBean.getImage(), ImageLoaderUtil.getFitCenterImageOption(photoView));
                }
            }
        }
        photoView.setOnViewTapListener(new OnViewTapListener() { // from class: com.one.tomato.adapter.ImagePagerAdapter.1
            @Override // com.luck.picture.lib.photoview.OnViewTapListener
            public void onViewTap(View view, float f, float f2) {
                if (ImagePagerAdapter.this.context instanceof ImageShowActivity) {
                    ((ImageShowActivity) ImagePagerAdapter.this.context).onBackPressed();
                }
            }
        });
        largeImageView.setOnClickListener(new View.OnClickListener() { // from class: com.one.tomato.adapter.ImagePagerAdapter.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                if (ImagePagerAdapter.this.context instanceof ImageShowActivity) {
                    ((ImageShowActivity) ImagePagerAdapter.this.context).onBackPressed();
                }
            }
        });
        photoView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.one.tomato.adapter.ImagePagerAdapter.3
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (!imageBean.isLocal()) {
                    if (ImagePagerAdapter.this.postList == null) {
                        ImagePagerAdapter.this.showDownloadDialog(i);
                        return true;
                    } else if (ImagePagerAdapter.this.postList.getCanDownload() == 1) {
                        ImagePagerAdapter.this.showDownloadDialog(i);
                    } else {
                        ToastUtil.showCenterToast("发布者未开放下载！");
                    }
                }
                return true;
            }
        });
        largeImageView.setOnLongClickListener(new View.OnLongClickListener() { // from class: com.one.tomato.adapter.ImagePagerAdapter.4
            @Override // android.view.View.OnLongClickListener
            public boolean onLongClick(View view) {
                if (!imageBean.isLocal()) {
                    if (ImagePagerAdapter.this.postList == null) {
                        ImagePagerAdapter.this.showDownloadDialog(i);
                        return true;
                    } else if (ImagePagerAdapter.this.postList.getCanDownload() == 1) {
                        ImagePagerAdapter.this.showDownloadDialog(i);
                    } else {
                        ToastUtil.showCenterToast("发布者未开放下载！");
                    }
                }
                return true;
            }
        });
        viewGroup.addView(inflate, 0);
        return inflate;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.imageBeans.size();
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        View view2 = (View) obj;
        return view == obj;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void showDownloadDialog(int i) {
        Context context = this.context;
        if (context == null || ((ImageShowActivity) context) == null || ((ImageShowActivity) context).isDestroyed()) {
            return;
        }
        if (this.downloadDialog == null) {
            this.downloadDialog = new ImageDownloadDialog(this.context);
            this.behavior = BottomSheetBehavior.from((View) ((CoordinatorLayout) this.downloadDialog.findViewById(R.id.coordinatorLayout)).getParent());
        }
        this.downloadDialog.show();
        this.behavior.setState(3);
        this.downloadDialog.setOnDismissListener(new DialogInterface.OnDismissListener() { // from class: com.one.tomato.adapter.ImagePagerAdapter.5
            @Override // android.content.DialogInterface.OnDismissListener
            public void onDismiss(DialogInterface dialogInterface) {
                ImagePagerAdapter.this.behavior.setState(4);
            }
        });
        this.downloadDialog.setImageDownloadListener(new C24236(i));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.one.tomato.adapter.ImagePagerAdapter$6 */
    /* loaded from: classes3.dex */
    public class C24236 implements ImageDownloadDialog.ImageDownloadListener {
        final /* synthetic */ int val$position;

        C24236(int i) {
            this.val$position = i;
        }

        @Override // com.one.tomato.dialog.ImageDownloadDialog.ImageDownloadListener
        public void download() {
            if (ImagePagerAdapter.this.postList == null) {
                ImagePagerAdapter.this.downloadImg(this.val$position);
            } else if (PostRewardPayUtils.INSTANCE.isAreadlyDownPay(ImagePagerAdapter.this.postList.getId())) {
                ImagePagerAdapter.this.downloadImg(this.val$position);
            } else if (PostRewardPayUtils.INSTANCE.isAreadlyPay(ImagePagerAdapter.this.postList.getId())) {
                ImagePagerAdapter.this.downloadImg(this.val$position);
            } else if (ImagePagerAdapter.this.postList.getDownPrice() <= 0) {
                ImagePagerAdapter.this.downloadImg(this.val$position);
            } else {
                VideoSaveTipDialog videoSaveTipDialog = new VideoSaveTipDialog(ImagePagerAdapter.this.context);
                videoSaveTipDialog.setVideoSaveTipListener(new VideoSaveTipDialog.VideoSaveTipListener() { // from class: com.one.tomato.adapter.ImagePagerAdapter.6.1
                    @Override // com.one.tomato.dialog.VideoSaveTipDialog.VideoSaveTipListener
                    public void potatoDownload() {
                        HashMap hashMap = new HashMap();
                        hashMap.put("memberId", Integer.valueOf(DBUtil.getMemberId()));
                        hashMap.put("articleId", Integer.valueOf(ImagePagerAdapter.this.postList.getId()));
                        hashMap.put("payType", 4);
                        hashMap.put("money", Integer.valueOf(ImagePagerAdapter.this.postList.getDownPrice()));
                        VideoPlayCountUtils.getInstance().postRewardPay(ImagePagerAdapter.this.context, hashMap, new Function1<String, Object>() { // from class: com.one.tomato.adapter.ImagePagerAdapter.6.1.1
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke  reason: avoid collision after fix types in other method */
                            public Object mo6794invoke(String str) {
                                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_pay_sucess));
                                PostRewardPayUtils.INSTANCE.setDownPayPost(ImagePagerAdapter.this.postList.getId());
                                C24236 c24236 = C24236.this;
                                ImagePagerAdapter.this.downloadImg(c24236.val$position);
                                return null;
                            }
                        }, new Function1<ResponseThrowable, Object>(this) { // from class: com.one.tomato.adapter.ImagePagerAdapter.6.1.2
                            @Override // kotlin.jvm.functions.Function1
                            /* renamed from: invoke  reason: avoid collision after fix types in other method */
                            public Object mo6794invoke(ResponseThrowable responseThrowable) {
                                ToastUtil.showCenterToast(AppUtil.getString(R.string.post_pay_error));
                                return null;
                            }
                        });
                    }
                });
                videoSaveTipDialog.setData(ImagePagerAdapter.this.postList.getDownPrice());
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void downloadImg(int i) {
        ImageBean imageBean = this.imageBeans.get(i);
        final String image = this.imageBeans.get(i).getImage();
        final boolean isGif = imageBean.isGif();
        boolean isLongImg = imageBean.isLongImg();
        Glide.with(this.context).mo6728load((Object) new ImageGlideUrl(DomainServer.getInstance().getTtViewPicture(), ImageLoaderUtil.compressViewPagerImage(image, isGif, isLongImg))).downloadOnly(new SimpleTarget<File>() { // from class: com.one.tomato.adapter.ImagePagerAdapter.7
            @Override // com.bumptech.glide.request.target.Target
            public /* bridge */ /* synthetic */ void onResourceReady(@NonNull Object obj, @Nullable Transition transition) {
                onResourceReady((File) obj, (Transition<? super File>) transition);
            }

            public void onResourceReady(@NonNull File file, @Nullable Transition<? super File> transition) {
                String str = FileUtil.getSDDCIMDir().getPath() + File.separator + MD5Util.md5(image) + (isGif ? ".GIF" : ".png");
                File file2 = new File(str);
                if (!file2.exists()) {
                    AppSecretUtil.decodeS3Image(file, file2);
                }
                ToastUtil.showCenterToast((int) R.string.common_image_download_success);
                ImagePagerAdapter.this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(new File(str))));
            }

            @Override // com.bumptech.glide.request.target.BaseTarget, com.bumptech.glide.request.target.Target
            public void onLoadFailed(@Nullable Drawable drawable) {
                super.onLoadFailed(drawable);
                ToastUtil.showCenterToast((int) R.string.common_image_download_fail);
            }
        });
    }
}
