package net.lucode.hackware.magicindicator.buildins.commonnavigator.indicators;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.p002v4.internal.view.SupportMenu;
import android.view.View;
import java.util.List;
import net.lucode.hackware.magicindicator.FragmentContainerHelper;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator;
import net.lucode.hackware.magicindicator.buildins.commonnavigator.model.PositionData;

/* loaded from: classes4.dex */
public class TestPagerIndicator extends View implements IPagerIndicator {
    private int mInnerRectColor;
    private int mOutRectColor;
    private Paint mPaint;
    private List<PositionData> mPositionDataList;
    private RectF mOutRect = new RectF();
    private RectF mInnerRect = new RectF();

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageScrollStateChanged(int i) {
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageSelected(int i) {
    }

    public TestPagerIndicator(Context context) {
        super(context);
        init(context);
    }

    private void init(Context context) {
        this.mPaint = new Paint(1);
        this.mPaint.setStyle(Paint.Style.STROKE);
        this.mOutRectColor = SupportMenu.CATEGORY_MASK;
        this.mInnerRectColor = -16711936;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        this.mPaint.setColor(this.mOutRectColor);
        canvas.drawRect(this.mOutRect, this.mPaint);
        this.mPaint.setColor(this.mInnerRectColor);
        canvas.drawRect(this.mInnerRect, this.mPaint);
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPageScrolled(int i, float f, int i2) {
        List<PositionData> list = this.mPositionDataList;
        if (list == null || list.isEmpty()) {
            return;
        }
        PositionData imitativePositionData = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i);
        PositionData imitativePositionData2 = FragmentContainerHelper.getImitativePositionData(this.mPositionDataList, i + 1);
        RectF rectF = this.mOutRect;
        int i3 = imitativePositionData.mLeft;
        rectF.left = i3 + ((imitativePositionData2.mLeft - i3) * f);
        int i4 = imitativePositionData.mTop;
        rectF.top = i4 + ((imitativePositionData2.mTop - i4) * f);
        int i5 = imitativePositionData.mRight;
        rectF.right = i5 + ((imitativePositionData2.mRight - i5) * f);
        int i6 = imitativePositionData.mBottom;
        rectF.bottom = i6 + ((imitativePositionData2.mBottom - i6) * f);
        RectF rectF2 = this.mInnerRect;
        int i7 = imitativePositionData.mContentLeft;
        rectF2.left = i7 + ((imitativePositionData2.mContentLeft - i7) * f);
        int i8 = imitativePositionData.mContentTop;
        rectF2.top = i8 + ((imitativePositionData2.mContentTop - i8) * f);
        int i9 = imitativePositionData.mContentRight;
        rectF2.right = i9 + ((imitativePositionData2.mContentRight - i9) * f);
        int i10 = imitativePositionData.mContentBottom;
        rectF2.bottom = i10 + ((imitativePositionData2.mContentBottom - i10) * f);
        invalidate();
    }

    @Override // net.lucode.hackware.magicindicator.buildins.commonnavigator.abs.IPagerIndicator
    public void onPositionDataProvide(List<PositionData> list) {
        this.mPositionDataList = list;
    }

    public int getOutRectColor() {
        return this.mOutRectColor;
    }

    public void setOutRectColor(int i) {
        this.mOutRectColor = i;
    }

    public int getInnerRectColor() {
        return this.mInnerRectColor;
    }

    public void setInnerRectColor(int i) {
        this.mInnerRectColor = i;
    }
}
