package sj.keyboard.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.keyboard.view.R$drawable;
import com.keyboard.view.R$styleable;
import java.util.ArrayList;
import java.util.Iterator;
import sj.keyboard.data.PageSetEntity;
import sj.keyboard.utils.EmoticonsKeyboardUtils;

/* loaded from: classes4.dex */
public class EmoticonsIndicatorView extends LinearLayout {
    private static final int MARGIN_LEFT = 4;
    protected Context mContext;
    protected Drawable mDrawableNomal;
    protected Drawable mDrawableSelect;
    protected ArrayList<ImageView> mImageViews;
    protected LinearLayout.LayoutParams mLeftLayoutParams;

    public EmoticonsIndicatorView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        setOrientation(0);
        TypedArray obtainStyledAttributes = context.getTheme().obtainStyledAttributes(attributeSet, R$styleable.EmoticonsIndicatorView, 0, 0);
        try {
            this.mDrawableSelect = obtainStyledAttributes.getDrawable(R$styleable.EmoticonsIndicatorView_bmpSelect);
            this.mDrawableNomal = obtainStyledAttributes.getDrawable(R$styleable.EmoticonsIndicatorView_bmpNomal);
            obtainStyledAttributes.recycle();
            if (this.mDrawableNomal == null) {
                this.mDrawableNomal = getResources().getDrawable(R$drawable.indicator_point_nomal);
            }
            if (this.mDrawableSelect == null) {
                this.mDrawableSelect = getResources().getDrawable(R$drawable.indicator_point_select);
            }
            this.mLeftLayoutParams = new LinearLayout.LayoutParams(-2, -2);
            this.mLeftLayoutParams.leftMargin = EmoticonsKeyboardUtils.dip2px(context, 4.0f);
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }

    public void playTo(int i, PageSetEntity pageSetEntity) {
        if (!checkPageSetEntity(pageSetEntity)) {
            return;
        }
        updateIndicatorCount(pageSetEntity.getPageCount());
        Iterator<ImageView> it2 = this.mImageViews.iterator();
        while (it2.hasNext()) {
            it2.next().setImageDrawable(this.mDrawableNomal);
        }
        this.mImageViews.get(i).setImageDrawable(this.mDrawableSelect);
    }

    public void playBy(int i, int i2, PageSetEntity pageSetEntity) {
        if (!checkPageSetEntity(pageSetEntity)) {
            return;
        }
        updateIndicatorCount(pageSetEntity.getPageCount());
        if (i < 0 || i2 < 0 || i2 == i) {
            i = 0;
            i2 = 0;
        }
        if (i < 0) {
            i = 0;
            i2 = 0;
        }
        this.mImageViews.get(i).setImageDrawable(this.mDrawableNomal);
        this.mImageViews.get(i2).setImageDrawable(this.mDrawableSelect);
    }

    protected boolean checkPageSetEntity(PageSetEntity pageSetEntity) {
        if (pageSetEntity != null && pageSetEntity.isShowIndicator()) {
            setVisibility(0);
            return true;
        }
        setVisibility(8);
        return false;
    }

    protected void updateIndicatorCount(int i) {
        if (this.mImageViews == null) {
            this.mImageViews = new ArrayList<>();
        }
        if (i > this.mImageViews.size()) {
            int size = this.mImageViews.size();
            while (size < i) {
                ImageView imageView = new ImageView(this.mContext);
                imageView.setImageDrawable(size == 0 ? this.mDrawableSelect : this.mDrawableNomal);
                addView(imageView, this.mLeftLayoutParams);
                this.mImageViews.add(imageView);
                size++;
            }
        }
        for (int i2 = 0; i2 < this.mImageViews.size(); i2++) {
            if (i2 >= i) {
                this.mImageViews.get(i2).setVisibility(8);
            } else {
                this.mImageViews.get(i2).setVisibility(0);
            }
        }
    }
}
