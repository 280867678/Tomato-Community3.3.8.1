package com.gen.p059mh.webapp_extensions.views;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.os.Build;
import android.support.p002v4.content.ContextCompat;
import android.text.SpannableString;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$color;
import com.gen.p059mh.webapp_extensions.R$drawable;
import com.gen.p059mh.webapp_extensions.R$mipmap;
import com.gen.p059mh.webapp_extensions.R$string;
import com.gen.p059mh.webapp_extensions.listener.CoverOperationListener;
import com.gen.p059mh.webapp_extensions.views.ImageView;
import com.gen.p059mh.webapps.listener.AppResponse;
import com.gen.p059mh.webapps.utils.Utils;
import com.google.gson.Gson;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/* renamed from: com.gen.mh.webapp_extensions.views.CoverView */
/* loaded from: classes2.dex */
public class CoverView extends RelativeLayout implements ICover {
    public static int viewId = 100001;
    protected ImageView closeButton;
    protected CoverOperationListener coverOperationListener;
    protected ImageView iconView;
    protected ImageLoadingView loadingView;
    protected TextView refreshButton;
    protected TextView startLoadView;
    protected TextView tipsView;
    protected TextView titleView;
    protected ImageView wappIconView;

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public View provideView() {
        return this;
    }

    public void setCoverOperationListener(CoverOperationListener coverOperationListener) {
        this.coverOperationListener = coverOperationListener;
    }

    public CoverView(Context context, CoverOperationListener coverOperationListener) {
        super(context);
        this.coverOperationListener = coverOperationListener;
        initView();
    }

    private void initView() {
        setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        float d2p = Utils.d2p(getContext(), 1);
        this.titleView = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (240.0f * d2p), -2);
        layoutParams.addRule(14, -1);
        layoutParams.addRule(13, -1);
        layoutParams.setMargins(0, 0, 0, 0);
        TextView textView = this.titleView;
        int i = viewId;
        viewId = i + 1;
        textView.setId(i);
        this.titleView.setTextColor(Color.parseColor("#303133"));
        this.titleView.setLayoutParams(layoutParams);
        this.titleView.setTextSize(17.0f);
        this.titleView.setTypeface(Typeface.defaultFromStyle(1));
        this.titleView.setGravity(17);
        this.titleView.setText("");
        addView(this.titleView);
        this.wappIconView = new ImageView(getContext());
        int i2 = (int) (52.0f * d2p);
        RelativeLayout.LayoutParams layoutParams2 = new RelativeLayout.LayoutParams(i2, i2);
        int i3 = (int) (d2p * 12.0f);
        layoutParams2.setMargins(0, 0, 0, i3);
        layoutParams2.addRule(14, -1);
        layoutParams2.addRule(2, this.titleView.getId());
        this.wappIconView.setLayoutParams(layoutParams2);
        this.wappIconView.imageView.setImageResource(R$mipmap.app_logo);
        addView(this.wappIconView);
        this.iconView = new ImageView(getContext(), 30);
        ImageView imageView = this.iconView;
        int i4 = viewId;
        viewId = i4 + 1;
        imageView.setId(i4);
        this.iconView.imageView.setImageResource(R$mipmap.app_logo);
        RelativeLayout.LayoutParams layoutParams3 = new RelativeLayout.LayoutParams(i2, i2);
        layoutParams3.setMargins(0, 0, 0, i3);
        layoutParams3.addRule(2, this.titleView.getId());
        layoutParams3.addRule(14, -1);
        this.iconView.setLayoutParams(layoutParams3);
        this.iconView.setVisibility(8);
        this.iconView.setOnFileDownloadListener(new ImageView.OnFileDownloadListener() { // from class: com.gen.mh.webapp_extensions.views.CoverView.1
            @Override // com.gen.p059mh.webapp_extensions.views.ImageView.OnFileDownloadListener
            public void onDownloaded(byte[] bArr) {
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(CoverView.this.coverOperationListener.provideIconFile());
                    fileOutputStream.write(bArr);
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        addView(this.iconView);
        this.loadingView = new ImageLoadingView(getContext());
        ImageLoadingView imageLoadingView = this.loadingView;
        int i5 = viewId;
        viewId = i5 + 1;
        imageLoadingView.setId(i5);
        int i6 = (int) (35.0f * d2p);
        RelativeLayout.LayoutParams layoutParams4 = new RelativeLayout.LayoutParams(-2, i6);
        layoutParams4.addRule(14, -1);
        layoutParams4.addRule(3, this.titleView.getId());
        layoutParams4.setMargins(0, (int) (30.0f * d2p), 0, 0);
        this.loadingView.setLayoutParams(layoutParams4);
        addView(this.loadingView);
        this.loadingView.setVisibility(8);
        this.startLoadView = new TextView(getContext());
        TextView textView2 = this.startLoadView;
        int i7 = viewId;
        viewId = i7 + 1;
        textView2.setId(i7);
        RelativeLayout.LayoutParams layoutParams5 = new RelativeLayout.LayoutParams((int) (280.0f * d2p), -2);
        layoutParams5.addRule(14, -1);
        layoutParams5.addRule(3, this.loadingView.getId());
        layoutParams5.setMargins(0, (int) (21.0f * d2p), 0, 0);
        this.startLoadView.setTextColor(Color.parseColor("#303133"));
        this.startLoadView.setTextSize(13.0f);
        this.startLoadView.setGravity(17);
        this.startLoadView.setLayoutParams(layoutParams5);
        addView(this.startLoadView);
        this.closeButton = new android.widget.ImageView(getContext());
        this.closeButton.setImageResource(R$mipmap.close);
        this.closeButton.setColorFilter(-13421773, PorterDuff.Mode.SRC_IN);
        this.closeButton.setScaleType(ImageView.ScaleType.CENTER);
        int i8 = (int) (40.0f * d2p);
        RelativeLayout.LayoutParams layoutParams6 = new RelativeLayout.LayoutParams(i8, i8);
        layoutParams6.addRule(11, -1);
        layoutParams6.setMargins(0, (int) (Utils.getStatusBarHeight(getContext()) + (24.0f * d2p)), (int) (16.0f * d2p), 0);
        this.closeButton.setLayoutParams(layoutParams6);
        this.closeButton.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.CoverView.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CoverView.this.coverOperationListener.onClose();
            }
        });
        addView(this.closeButton);
        this.refreshButton = new TextView(getContext());
        RelativeLayout.LayoutParams layoutParams7 = new RelativeLayout.LayoutParams((int) (d2p * 120.0f), i6);
        layoutParams7.addRule(14, -1);
        layoutParams7.addRule(2, this.startLoadView.getId());
        layoutParams7.setMargins(0, 25, 0, -25);
        if (Build.VERSION.SDK_INT >= 21) {
            this.refreshButton.setElevation(0.0f);
            this.refreshButton.setStateListAnimator(null);
        }
        this.refreshButton.setBackgroundResource(R$drawable.border);
        this.refreshButton.setLayoutParams(layoutParams7);
        this.refreshButton.setGravity(17);
        this.refreshButton.setText(getContext().getString(R$string.reload));
        this.refreshButton.setVisibility(8);
        this.refreshButton.setTextColor(ContextCompat.getColorStateList(getContext(), R$color.button));
        this.refreshButton.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapp_extensions.views.CoverView.3
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                CoverView.this.coverOperationListener.onRefresh();
            }
        });
        this.refreshButton.setTextSize(13.0f);
        addView(this.refreshButton);
        this.tipsView = new TextView(getContext());
        TextView textView3 = this.tipsView;
        int i9 = viewId;
        viewId = i9 + 1;
        textView3.setId(i9);
        RelativeLayout.LayoutParams layoutParams8 = new RelativeLayout.LayoutParams(-1, -2);
        layoutParams8.addRule(14, -1);
        layoutParams8.addRule(12, -1);
        layoutParams8.setMargins(0, 0, 0, i8);
        this.tipsView.setVisibility(8);
        this.tipsView.setGravity(17);
        this.tipsView.setLayoutParams(layoutParams8);
        this.tipsView.setTextColor(Color.parseColor("#959595"));
        this.tipsView.setTextSize(2, 12.0f);
        addView(this.tipsView);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void setCloseBtnShow(boolean z) {
        android.widget.ImageView imageView = this.closeButton;
        if (imageView != null) {
            imageView.setVisibility(z ? 0 : 4);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void setWAppIconShow(boolean z) {
        this.wappIconView.setVisibility(z ? 0 : 4);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void setIconFile(File file) {
        this.iconView.setFile(file);
        this.iconView.setVisibility(0);
        this.wappIconView.setVisibility(8);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void setTitle(String str) {
        if (this.refreshButton.getVisibility() != 0) {
            this.titleView.setText(str);
            this.titleView.setVisibility(0);
        }
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void setIconUrl(String str) {
        this.iconView.setUrl(str);
        this.iconView.setVisibility(0);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void onReady() {
        TextView textView = this.startLoadView;
        if (textView == null) {
            return;
        }
        textView.setTextColor(Color.parseColor("#303133"));
        this.startLoadView.setText(getContext().getString(R$string.launching));
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void onUpdate() {
        TextView textView = this.tipsView;
        if (textView == null) {
            return;
        }
        textView.setVisibility(0);
        this.tipsView.setText(getContext().getString(R$string.loading));
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void startLoading() {
        TextView textView = this.startLoadView;
        if (textView == null || this.refreshButton == null || this.loadingView == null) {
            return;
        }
        textView.setTextColor(Color.parseColor("#303133"));
        this.startLoadView.setText(getContext().getString(R$string.launching));
        this.refreshButton.setVisibility(8);
        String provideConfigData = this.coverOperationListener.provideConfigData();
        if (provideConfigData != null && provideConfigData.length() > 0) {
            final AppResponse.LoadingImgBean loadingImgBean = (AppResponse.LoadingImgBean) new Gson().fromJson(provideConfigData, (Class<Object>) AppResponse.LoadingImgBean.class);
            this.loadingView.post(new Runnable() { // from class: com.gen.mh.webapp_extensions.views.-$$Lambda$CoverView$X-7PDFoF7ypmB1j9hk3b-baM4dM
                @Override // java.lang.Runnable
                public final void run() {
                    CoverView.this.lambda$startLoading$0$CoverView(loadingImgBean);
                }
            });
        } else {
            this.loadingView.setDefault(true);
        }
        this.loadingView.setVisibility(0);
    }

    public /* synthetic */ void lambda$startLoading$0$CoverView(AppResponse.LoadingImgBean loadingImgBean) {
        this.loadingView.startLoading(this.coverOperationListener.provideAnimFile(), loadingImgBean);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void loadProcess(SpannableString spannableString) {
        this.startLoadView.setText(spannableString);
        this.refreshButton.setVisibility(8);
    }

    @Override // com.gen.p059mh.webapp_extensions.views.ICover
    public void loadFail(String str) {
        loadingStop();
        this.titleView.setVisibility(4);
        this.loadingView.setVisibility(8);
        this.startLoadView.setText(str);
        this.startLoadView.setTextColor(Color.rgb(247, 57, 106));
        this.refreshButton.setVisibility(0);
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
    public void onRotateLandscape() {
        this.refreshButton.setAlpha(0.0f);
        this.loadingView.setAlpha(0.0f);
    }
}
