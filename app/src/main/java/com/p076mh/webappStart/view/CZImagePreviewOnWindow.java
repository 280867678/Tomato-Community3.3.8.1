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
import com.p076mh.webappStart.android_plugin_impl.plugins.plugin_impl.PreviewImageOnWindowImpl;
import com.p076mh.webappStart.util.MainHandler;
import com.p076mh.webappStart.view.MyClickListener;
import java.util.List;

/* renamed from: com.mh.webappStart.view.CZImagePreviewOnWindow */
/* loaded from: classes3.dex */
public class CZImagePreviewOnWindow extends FrameLayout implements MyClickListener.MyClickCallBack {
    private int curPosition;
    private int imageCount;
    private ImagePager mypagerAdapter;
    private PreviewImageParamsBean previewImageParamsBean;
    private TextView tv_indicator;
    private ViewPager viewpager;
    private IWebFragmentController webViewFragment;
    private PreviewImageOnWindowImpl.WindowViewController windowViewController;

    public CZImagePreviewOnWindow(@NonNull Context context) {
        this(context, null);
    }

    public CZImagePreviewOnWindow(@NonNull Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, -1);
    }

    public CZImagePreviewOnWindow(@NonNull Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.previewImageParamsBean = null;
        this.imageCount = 0;
        this.curPosition = 0;
    }

    public void init(PreviewImageOnWindowImpl.WindowViewController windowViewController, IWebFragmentController iWebFragmentController, PreviewImageParamsBean previewImageParamsBean) {
        this.windowViewController = windowViewController;
        this.webViewFragment = iWebFragmentController;
        this.previewImageParamsBean = previewImageParamsBean;
        initView();
        initData();
    }

    private void initData() {
        this.imageCount = this.previewImageParamsBean.getUrls().size();
        MainHandler.getInstance().post(new Runnable() { // from class: com.mh.webappStart.view.CZImagePreviewOnWindow.1
            @Override // java.lang.Runnable
            public void run() {
                CZImagePreviewOnWindow cZImagePreviewOnWindow = CZImagePreviewOnWindow.this;
                cZImagePreviewOnWindow.mypagerAdapter = new ImagePager(cZImagePreviewOnWindow.getContext(), CZImagePreviewOnWindow.this.previewImageParamsBean.getUrls(), CZImagePreviewOnWindow.this.webViewFragment);
                CZImagePreviewOnWindow.this.mypagerAdapter.setMyClickListener(CZImagePreviewOnWindow.this);
                CZImagePreviewOnWindow.this.viewpager.setAdapter(CZImagePreviewOnWindow.this.mypagerAdapter);
                CZImagePreviewOnWindow cZImagePreviewOnWindow2 = CZImagePreviewOnWindow.this;
                cZImagePreviewOnWindow2.curPosition = cZImagePreviewOnWindow2.findCurIndex(cZImagePreviewOnWindow2.previewImageParamsBean.getUrls(), CZImagePreviewOnWindow.this.previewImageParamsBean.getCurrent());
                CZImagePreviewOnWindow.this.viewpager.setCurrentItem(CZImagePreviewOnWindow.this.curPosition);
                TextView textView = CZImagePreviewOnWindow.this.tv_indicator;
                textView.setText((CZImagePreviewOnWindow.this.curPosition + 1) + "/" + CZImagePreviewOnWindow.this.imageCount);
                if (CZImagePreviewOnWindow.this.imageCount <= 1) {
                    CZImagePreviewOnWindow.this.tv_indicator.setVisibility(8);
                } else {
                    CZImagePreviewOnWindow.this.tv_indicator.setVisibility(0);
                }
            }
        });
    }

    private void initView() {
        View inflate = LayoutInflater.from(getContext()).inflate(R$layout.web_sdk_wx_native_preview_image, (ViewGroup) this, true);
        this.viewpager = (ViewPager) inflate.findViewById(R$id.viewpager);
        this.tv_indicator = (TextView) inflate.findViewById(R$id.tv_indicator);
        this.viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.mh.webappStart.view.CZImagePreviewOnWindow.2
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                TextView textView = CZImagePreviewOnWindow.this.tv_indicator;
                textView.setText((i + 1) + "/" + CZImagePreviewOnWindow.this.imageCount);
                CZImagePreviewOnWindow.this.curPosition = i;
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
            this.windowViewController.remove();
        }
        return super.dispatchKeyEvent(keyEvent);
    }

    @Override // com.p076mh.webappStart.view.MyClickListener.MyClickCallBack
    public void oneClick() {
        this.windowViewController.remove();
    }

    @Override // com.p076mh.webappStart.view.MyClickListener.MyClickCallBack
    public void doubleClick() {
        this.mypagerAdapter.getPrimaryItem().resetDisplay();
    }
}
