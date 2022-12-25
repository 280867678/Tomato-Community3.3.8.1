package sj.keyboard.widget;

import android.content.Context;
import android.support.p002v4.view.PagerAdapter;
import android.support.p002v4.view.ViewPager;
import android.util.AttributeSet;
import java.util.Iterator;
import sj.keyboard.adpater.PageSetAdapter;
import sj.keyboard.data.PageSetEntity;

/* loaded from: classes4.dex */
public class EmoticonsFuncView extends ViewPager {
    protected int mCurrentPagePosition;
    private OnEmoticonsPageViewListener mOnEmoticonsPageViewListener;
    protected PageSetAdapter mPageSetAdapter;

    /* loaded from: classes4.dex */
    public interface OnEmoticonsPageViewListener {
        void emoticonSetChanged(PageSetEntity pageSetEntity);

        void playBy(int i, int i2, PageSetEntity pageSetEntity);

        void playTo(int i, PageSetEntity pageSetEntity);
    }

    public EmoticonsFuncView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setAdapter(PageSetAdapter pageSetAdapter) {
        super.setAdapter((PagerAdapter) pageSetAdapter);
        this.mPageSetAdapter = pageSetAdapter;
        setOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: sj.keyboard.widget.EmoticonsFuncView.1
            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // android.support.p002v4.view.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
                EmoticonsFuncView.this.checkPageChange(i);
                EmoticonsFuncView.this.mCurrentPagePosition = i;
            }
        });
        if (this.mOnEmoticonsPageViewListener == null || this.mPageSetAdapter.getPageSetEntityList().isEmpty()) {
            return;
        }
        PageSetEntity pageSetEntity = this.mPageSetAdapter.getPageSetEntityList().get(0);
        this.mOnEmoticonsPageViewListener.playTo(0, pageSetEntity);
        this.mOnEmoticonsPageViewListener.emoticonSetChanged(pageSetEntity);
    }

    public void setCurrentPageSet(PageSetEntity pageSetEntity) {
        PageSetAdapter pageSetAdapter = this.mPageSetAdapter;
        if (pageSetAdapter == null || pageSetAdapter.getCount() <= 0) {
            return;
        }
        setCurrentItem(this.mPageSetAdapter.getPageSetStartPosition(pageSetEntity));
    }

    public void checkPageChange(int i) {
        OnEmoticonsPageViewListener onEmoticonsPageViewListener;
        PageSetAdapter pageSetAdapter = this.mPageSetAdapter;
        if (pageSetAdapter == null) {
            return;
        }
        Iterator<PageSetEntity> it2 = pageSetAdapter.getPageSetEntityList().iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            PageSetEntity next = it2.next();
            int pageCount = next.getPageCount();
            int i3 = i2 + pageCount;
            if (i3 > i) {
                boolean z = true;
                int i4 = this.mCurrentPagePosition;
                if (i4 - i2 >= pageCount) {
                    OnEmoticonsPageViewListener onEmoticonsPageViewListener2 = this.mOnEmoticonsPageViewListener;
                    if (onEmoticonsPageViewListener2 != null) {
                        onEmoticonsPageViewListener2.playTo(i - i2, next);
                    }
                } else if (i4 - i2 < 0) {
                    OnEmoticonsPageViewListener onEmoticonsPageViewListener3 = this.mOnEmoticonsPageViewListener;
                    if (onEmoticonsPageViewListener3 != null) {
                        onEmoticonsPageViewListener3.playTo(0, next);
                    }
                } else {
                    OnEmoticonsPageViewListener onEmoticonsPageViewListener4 = this.mOnEmoticonsPageViewListener;
                    if (onEmoticonsPageViewListener4 != null) {
                        onEmoticonsPageViewListener4.playBy(i4 - i2, i - i2, next);
                    }
                    z = false;
                }
                if (!z || (onEmoticonsPageViewListener = this.mOnEmoticonsPageViewListener) == null) {
                    return;
                }
                onEmoticonsPageViewListener.emoticonSetChanged(next);
                return;
            }
            i2 = i3;
        }
    }

    public void setOnIndicatorListener(OnEmoticonsPageViewListener onEmoticonsPageViewListener) {
        this.mOnEmoticonsPageViewListener = onEmoticonsPageViewListener;
    }
}
