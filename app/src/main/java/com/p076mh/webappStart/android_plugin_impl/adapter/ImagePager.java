package com.p076mh.webappStart.android_plugin_impl.adapter;

import android.content.Context;
import android.support.p002v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import com.gen.p059mh.webapp_extensions.matisse.internal.entity.SelectionSpec;
import com.gen.p059mh.webapps.listener.IWebFragmentController;
import com.p076mh.webappStart.view.MyClickListener;
import it.sephiroth.android.library.imagezoom.ImageViewTouch;
import it.sephiroth.android.library.imagezoom.ImageViewTouchBase;
import java.io.File;
import java.util.List;

/* renamed from: com.mh.webappStart.android_plugin_impl.adapter.ImagePager */
/* loaded from: classes3.dex */
public class ImagePager extends PagerAdapter {
    private Context context;
    private ImageViewTouch mCurrentView;
    private MyClickListener.MyClickCallBack myClickListener;
    private List<String> strDrawables;
    private IWebFragmentController webViewFragment;

    @Override // android.support.p002v4.view.PagerAdapter
    public boolean isViewFromObject(View view, Object obj) {
        return view == obj;
    }

    public ImagePager(Context context, List<String> list, IWebFragmentController iWebFragmentController) {
        this.context = context;
        this.strDrawables = list;
        this.webViewFragment = iWebFragmentController;
    }

    public void setMyClickListener(MyClickListener.MyClickCallBack myClickCallBack) {
        this.myClickListener = myClickCallBack;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public int getCount() {
        return this.strDrawables.size();
    }

    @Override // android.support.p002v4.view.PagerAdapter
    /* renamed from: instantiateItem */
    public View mo6346instantiateItem(ViewGroup viewGroup, int i) {
        String str;
        Log.e("ImagePager", "instantiateItem: position " + i);
        ImageViewTouch imageViewTouch = new ImageViewTouch(this.webViewFragment.getContext());
        imageViewTouch.setDisplayType(ImageViewTouchBase.DisplayType.FIT_TO_SCREEN);
        imageViewTouch.setDoubleTapEnabled(false);
        imageViewTouch.setOnTouchListener(new MyClickListener(this.webViewFragment.getContext(), new MyClickListener.MyClickCallBack() { // from class: com.mh.webappStart.android_plugin_impl.adapter.ImagePager.1
            @Override // com.p076mh.webappStart.view.MyClickListener.MyClickCallBack
            public void oneClick() {
                Log.e("ImagePager", "oneClick: ");
                if (ImagePager.this.myClickListener != null) {
                    ImagePager.this.myClickListener.oneClick();
                }
            }

            @Override // com.p076mh.webappStart.view.MyClickListener.MyClickCallBack
            public void doubleClick() {
                Log.e("ImagePager", "doubleClick: ");
                if (ImagePager.this.myClickListener != null) {
                    ImagePager.this.myClickListener.doubleClick();
                }
            }
        }));
        viewGroup.addView(imageViewTouch);
        if (this.strDrawables.get(i).startsWith("app:///")) {
            str = this.webViewFragment.realPath(this.strDrawables.get(i));
            SelectionSpec.getInstance().imageEngine.load(this.context, imageViewTouch, new File(str));
        } else {
            str = this.strDrawables.get(i);
            SelectionSpec.getInstance().imageEngine.load(this.context, imageViewTouch, str);
        }
        Log.e("ImagePager", "realPath: " + str);
        return imageViewTouch;
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public void destroyItem(ViewGroup viewGroup, int i, Object obj) {
        viewGroup.removeView((View) obj);
    }

    @Override // android.support.p002v4.view.PagerAdapter
    public void setPrimaryItem(ViewGroup viewGroup, int i, Object obj) {
        this.mCurrentView = (ImageViewTouch) obj;
    }

    public ImageViewTouch getPrimaryItem() {
        return this.mCurrentView;
    }
}
