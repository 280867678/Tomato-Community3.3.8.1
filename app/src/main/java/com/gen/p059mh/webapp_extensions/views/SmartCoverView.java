package com.gen.p059mh.webapp_extensions.views;

import android.app.Activity;
import android.content.Context;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.gen.p059mh.webapp_extensions.listener.CoverOperationListener;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapp_extensions.views.ICover;
import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.utils.Utils;
import com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack;
import java.io.File;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.views.SmartCoverView */
/* loaded from: classes2.dex */
public class SmartCoverView extends RelativeLayout implements ICover {
    ImageView closeButton;
    ImageLoadingView loadingView;

    public static int transXY(float f, int i) {
        return (int) (f * i);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void loadFail(String str) {
        ICover.CC.$default$loadFail(this, str);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void loadProcess(SpannableString spannableString) {
        ICover.CC.$default$loadProcess(this, spannableString);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void onReady() {
        ICover.CC.$default$onReady(this);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void onRotateLandscape() {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void onUpdate() {
        ICover.CC.$default$onUpdate(this);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public View provideView() {
        return this;
    }

    public void setCoverOperationListener(CoverOperationListener coverOperationListener) {
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void setIconFile(File file) {
        ICover.CC.$default$setIconFile(this, file);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void setIconUrl(String str) {
        ICover.CC.$default$setIconUrl(this, str);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void setTitle(String str) {
        ICover.CC.$default$setTitle(this, str);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public /* synthetic */ void setWAppIconShow(boolean z) {
        ICover.CC.$default$setWAppIconShow(this, z);
    }

    private SmartCoverView(Context context) {
        super(context);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void loadingRelease() {
        this.loadingView.onRelease();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void loadingStop() {
        this.loadingView.onStop();
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void setCloseBtnShow(boolean z) {
        ImageView imageView = this.closeButton;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 4);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void startLoading() {
        ImageLoadingView imageLoadingView = this.loadingView;
        if (imageLoadingView != null) {
            imageLoadingView.setVisibility(0);
        }
    }

    public static ICover create(Activity activity, Map map, final CoverOperationListener coverOperationListener) {
        SmartCoverView smartCoverView = new SmartCoverView(activity);
        smartCoverView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        smartCoverView.setCoverOperationListener(coverOperationListener);
        float min = Math.min(Utils.getDisplayMetrics(activity).widthPixels, Utils.getDisplayMetrics(activity).heightPixels) / 750.0f;
        if (map.containsKey("spinFrame") && map.containsKey("spin")) {
            AppResponse.LoadingImgBean loadingImgBean = new AppResponse.LoadingImgBean();
            loadingImgBean.imgPath = (String) ((Map) map.get("spin")).get("url");
            loadingImgBean.cropWidth = ((Number) ((Map) map.get("spin")).get("cropWidth")).intValue();
            loadingImgBean.cropHeight = ((Number) ((Map) map.get("spin")).get("cropHeight")).intValue();
            loadingImgBean.spriteCount = ((Number) ((Map) map.get("spin")).get("spriteCount")).intValue();
            loadingImgBean.spriteFps = Float.valueOf(1.0f / ((Number) ((Map) map.get("spin")).get("interval")).floatValue()).intValue();
            int i = loadingImgBean.spriteFps;
            if (i > 20 || i <= 0) {
                loadingImgBean.spriteFps = 10;
            }
            ImageLoadingView imageLoadingView = new ImageLoadingView(activity);
            int transXY = transXY(min, ((Number) ((Map) map.get("spinFrame")).get("width")).intValue());
            int transXY2 = transXY(min, ((Number) ((Map) map.get("spinFrame")).get("height")).intValue());
            imageLoadingView.setX(checkX(activity, transXY(min, ((Number) ((Map) map.get("spinFrame")).get("x")).intValue())));
            imageLoadingView.setY(checkY(activity, transXY(min, ((Number) ((Map) map.get("spinFrame")).get("y")).intValue())));
            smartCoverView.addView(imageLoadingView, new RelativeLayout.LayoutParams(transXY, transXY2));
            smartCoverView.setLoadingView(imageLoadingView);
            SelectionSpec.getInstance().imageEngine.download(activity, loadingImgBean.imgPath, new C16581(imageLoadingView, loadingImgBean), loadingImgBean.imgPath.endsWith(Utils.DECODE_END));
        }
        if (map.containsKey(MainFragment.CLOSE_EVENT) && map.containsKey("closeFrame")) {
            ImageView imageView = new ImageView(activity);
            int transXY3 = transXY(min, ((Number) ((Map) map.get("closeFrame")).get("width")).intValue());
            int transXY4 = transXY(min, ((Number) ((Map) map.get("closeFrame")).get("height")).intValue());
            imageView.setX(checkX(activity, transXY(min, ((Number) ((Map) map.get("closeFrame")).get("x")).intValue())));
            imageView.setY(checkY(activity, transXY(min, ((Number) ((Map) map.get("closeFrame")).get("y")).intValue())));
            String str = (String) map.get(MainFragment.CLOSE_EVENT);
            if (str != null) {
                SelectionSpec.getInstance().imageEngine.load(activity, imageView, str, str.endsWith(Utils.DECODE_END));
            }
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(transXY3, transXY4);
            imageView.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.SmartCoverView.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view) {
                    CoverOperationListener.this.onClose();
                }
            });
            smartCoverView.setCloseButton(imageView);
            smartCoverView.addView(imageView, layoutParams);
        }
        return smartCoverView;
    }

    /* renamed from: com.gen.mh.webapp_extensions.views.SmartCoverView$1 */
    /* loaded from: classes2.dex */
    static class C16581 implements CommonCallBack<File> {
        final /* synthetic */ ImageLoadingView val$imageLoadingView;
        final /* synthetic */ AppResponse.LoadingImgBean val$loadingImgBean;

        C16581(ImageLoadingView imageLoadingView, AppResponse.LoadingImgBean loadingImgBean) {
            this.val$imageLoadingView = imageLoadingView;
            this.val$loadingImgBean = loadingImgBean;
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
        public void onResult(final File file) {
            final ImageLoadingView imageLoadingView = this.val$imageLoadingView;
            if (imageLoadingView != null) {
                final AppResponse.LoadingImgBean loadingImgBean = this.val$loadingImgBean;
                imageLoadingView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.-$$Lambda$SmartCoverView$1$U2KhO5rt9bGVk9l9a8D6-nOfrxg
                    @Override // java.lang.Runnable
                    public final void run() {
                        ImageLoadingView.this.startLoading(file, loadingImgBean);
                    }
                });
            }
        }

        @Override // com.p076mh.webappStart.android_plugin_impl.callback.CommonCallBack
        public void onFailure(Exception exc) {
            exc.printStackTrace();
        }
    }

    private static int getWidth(Activity activity) {
        int i = Utils.getDisplayMetrics(activity).widthPixels;
        int i2 = Utils.getDisplayMetrics(activity).heightPixels;
        if (isLand(activity)) {
            return Math.max(i, i2);
        }
        return Math.min(i, i2);
    }

    private static int getHeight(Activity activity) {
        int i = Utils.getDisplayMetrics(activity).widthPixels;
        int i2 = Utils.getDisplayMetrics(activity).heightPixels;
        if (isLand(activity)) {
            return Math.min(i, i2);
        }
        return Math.max(i, i2);
    }

    private static boolean isLand(Activity activity) {
        return activity.getRequestedOrientation() == 0;
    }

    private static int checkX(Activity activity, int i) {
        float f = i;
        if (f < Utils.d2p(activity, 40)) {
            return (int) Utils.d2p(activity, 40);
        }
        return f > ((float) getWidth(activity)) - Utils.d2p(activity, 40) ? (int) (getWidth(activity) - Utils.d2p(activity, 40)) : i;
    }

    private static int checkY(Activity activity, int i) {
        float f = i;
        if (f < Utils.d2p(activity, 40)) {
            return (int) Utils.d2p(activity, 40);
        }
        return f > ((float) getHeight(activity)) - Utils.d2p(activity, 40) ? (int) (Utils.getDisplayMetrics(activity).heightPixels - Utils.d2p(activity, 40)) : i;
    }

    private void setLoadingView(ImageLoadingView imageLoadingView) {
        this.loadingView = imageLoadingView;
    }

    private void setCloseButton(ImageView imageView) {
        this.closeButton = imageView;
    }
}
