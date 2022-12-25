package com.p076mh.webappStart.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import com.gen.p059mh.webapp_extensions.R$id;
import com.gen.p059mh.webapp_extensions.R$layout;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.android_plugin_impl.adapter.ImagePager;
import com.p076mh.webappStart.android_plugin_impl.beans.PreviewImageParamsBean;
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWebViewFragmentImpl;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.view.MyClickListener;
import java.util.List;

/* renamed from: com.mh.webappStart.view.CZImagePreviewOnWebViewFragment */
/* loaded from: classes3.dex */
public class CZImagePreviewOnWebViewFragment extends FrameLayout implements MyClickListener.MyClickCallBack {
    private int curPosition;
    private int imageCount;
    private ImagePager mypagerAdapter;
    private PreviewImageParamsBean previewImageParamsBean;
    private TextView tv_indicator;
    private ViewPager viewpager;
    private IWebFragmentController webViewFragment;
    private PreviewImageOnWebViewFragmentImpl.WebViewFragmentController webViewFragmentController;

    public CZImagePreviewOnWebViewFragment(@NonNull Context context) {
        this(context, null);
    }

    public CZImagePreviewOnWebViewFragment(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public CZImagePreviewOnWebViewFragment(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.previewImageParamsBean = null;
        this.imageCount = 0;
        this.curPosition = 0;
    }

    public void init(PreviewImageOnWebViewFragmentImpl.WebViewFragmentController webViewFragmentController, IWebFragmentController iWebFragmentController, PreviewImageParamsBean previewImageParamsBean) {
        this.webViewFragmentController = webViewFragmentController;
        this.webViewFragment = iWebFragmentController;
        this.previewImageParamsBean = previewImageParamsBean;
        initView();
        initData();
    }

    private void initData() {
        this.imageCount = this.previewImageParamsBean.getUrls().size();
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.view.CZImagePreviewOnWebViewFragment.1
            @Override // java.lang.Runnable
            public void run() {
                CZImagePreviewOnWebViewFragment cZImagePreviewOnWebViewFragment = CZImagePreviewOnWebViewFragment.this;
                cZImagePreviewOnWebViewFragment.mypagerAdapter = new ImagePager(cZImagePreviewOnWebViewFragment.getContext(), CZImagePreviewOnWebViewFragment.this.previewImageParamsBean.getUrls(), CZImagePreviewOnWebViewFragment.this.webViewFragment);
                CZImagePreviewOnWebViewFragment.this.mypagerAdapter.setMyClickListener(CZImagePreviewOnWebViewFragment.this);
                CZImagePreviewOnWebViewFragment.this.viewpager.setAdapter(CZImagePreviewOnWebViewFragment.this.mypagerAdapter);
                CZImagePreviewOnWebViewFragment cZImagePreviewOnWebViewFragment2 = CZImagePreviewOnWebViewFragment.this;
                cZImagePreviewOnWebViewFragment2.curPosition = cZImagePreviewOnWebViewFragment2.findCurIndex(cZImagePreviewOnWebViewFragment2.previewImageParamsBean.getUrls(), CZImagePreviewOnWebViewFragment.this.previewImageParamsBean.getCurrent());
                CZImagePreviewOnWebViewFragment.this.viewpager.setCurrentItem(CZImagePreviewOnWebViewFragment.this.curPosition);
                TextView textView = CZImagePreviewOnWebViewFragment.this.tv_indicator;
                textView.setText((CZImagePreviewOnWebViewFragment.this.curPosition + 1) + "/" + CZImagePreviewOnWebViewFragment.this.imageCount);
                if (CZImagePreviewOnWebViewFragment.this.imageCount <= 1) {
                    CZImagePreviewOnWebViewFragment.this.tv_indicator.setVisibility(8);
                } else {
                    CZImagePreviewOnWebViewFragment.this.tv_indicator.setVisibility(0);
                }
            }
        });
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R$layout.web_sdk_wx_native_preview_image, (ViewGroup) this, true);
        this.viewpager = (ViewPager) inflate.findViewById(R$id.viewpager);
        this.tv_indicator = (TextView) inflate.findViewById(R$id.tv_indicator);
        this.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.mh.webappStart.view.CZImagePreviewOnWebViewFragment.2
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                TextView textView = CZImagePreviewOnWebViewFragment.this.tv_indicator;
                textView.setText((i + 1) + "/" + CZImagePreviewOnWebViewFragment.this.imageCount);
                CZImagePreviewOnWebViewFragment.this.curPosition = i;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int findCurIndex(List<String> list, String str) {
        for (int i = 0; i < list.size(); i++) {
            if (str.equals(list.get(i))) {
                return i;
            }
        }
        return -1;
    }

    @Override // android.view.ViewGroup, android.view.View
    public boolean dispatchKeyEvent(KeyEvent keyEvent) {
        if (keyEvent.getKeyCode() == 4) {
            this.webViewFragmentController.remove();
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // com.p076mh.webappStart.view.MyClickListener.MyClickCallBack
    public void oneClick() {
        this.webViewFragmentController.remove();
    }

    @Override // com.p076mh.webappStart.view.MyClickListener.MyClickCallBack
    public void doubleClick() {
        if (this.mypagerAdapter.getPrimaryItem().getScale() != 1.0f) {
            this.mypagerAdapter.getPrimaryItem().resetDisplay();
        } else {
            this.mypagerAdapter.getPrimaryItem().zoomTo(2.0f, 300.0f);
        }
    }
}
