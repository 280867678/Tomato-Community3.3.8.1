package com.dueeeke.videoplayer.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.TextureView;
import android.view.View;

/* loaded from: classes2.dex */
public class ResizeTextureView extends TextureView {
    private int mVideoHeight;
    private int mVideoWidth;
    private int screenType;

    public ResizeTextureView(Context context) {
        super(context);
    }

    public ResizeTextureView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public void setVideoSize(int i, int i2) {
        this.mVideoWidth = i;
        this.mVideoHeight = i2;
    }

    public void setScreenScale(int i) {
        this.screenType = i;
        requestLayout();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        int i3;
        if (getRotation() == 90.0f || getRotation() == 270.0f) {
            int i4 = i + i2;
            i2 = i4 - i2;
            i = i4 - i2;
        }
        int defaultSize = TextureView.getDefaultSize(this.mVideoWidth, i);
        int defaultSize2 = TextureView.getDefaultSize(this.mVideoHeight, i2);
        int i5 = this.screenType;
        if (i5 == 1) {
            i2 = (defaultSize / 16) * 9;
            if (defaultSize2 <= i2) {
                i = (defaultSize2 / 9) * 16;
                i2 = defaultSize2;
            }
            i = defaultSize;
        } else if (i5 == 2) {
            i2 = (defaultSize / 4) * 3;
            if (defaultSize2 <= i2) {
                i = (defaultSize2 / 3) * 4;
                i2 = defaultSize2;
            }
            i = defaultSize;
        } else if (i5 != 3) {
            if (i5 != 4) {
                if (i5 == 5) {
                    int i6 = this.mVideoWidth;
                    if (i6 > 0 && (i3 = this.mVideoHeight) > 0) {
                        if (i6 * defaultSize2 > defaultSize * i3) {
                            i = (i6 * defaultSize2) / i3;
                            i2 = defaultSize2;
                        } else {
                            i2 = (i3 * defaultSize) / i6;
                            i = defaultSize;
                        }
                    }
                } else if (this.mVideoWidth > 0 && this.mVideoHeight > 0) {
                    int mode = View.MeasureSpec.getMode(i);
                    i = View.MeasureSpec.getSize(i);
                    int mode2 = View.MeasureSpec.getMode(i2);
                    i2 = View.MeasureSpec.getSize(i2);
                    if (mode == 1073741824 && mode2 == 1073741824) {
                        int i7 = this.mVideoWidth;
                        int i8 = i7 * i2;
                        int i9 = this.mVideoHeight;
                        if (i8 < i * i9) {
                            i = (i7 * i2) / i9;
                        } else if (i7 * i2 > i * i9) {
                            i2 = (i9 * i) / i7;
                        }
                    } else if (mode == 1073741824) {
                        int i10 = (this.mVideoHeight * i) / this.mVideoWidth;
                        if (mode2 != Integer.MIN_VALUE || i10 <= i2) {
                            i2 = i10;
                        }
                    } else if (mode2 == 1073741824) {
                        int i11 = (this.mVideoWidth * i2) / this.mVideoHeight;
                        if (mode != Integer.MIN_VALUE || i11 <= i) {
                            i = i11;
                        }
                    } else {
                        int i12 = this.mVideoWidth;
                        int i13 = this.mVideoHeight;
                        if (mode2 != Integer.MIN_VALUE || i13 <= i2) {
                            i2 = i13;
                        } else {
                            i12 = (i12 * i2) / i13;
                        }
                        if (mode != Integer.MIN_VALUE || i12 <= i) {
                            i = i12;
                        } else {
                            i2 = (this.mVideoHeight * i) / this.mVideoWidth;
                        }
                    }
                }
                i = defaultSize;
                i2 = defaultSize2;
            } else {
                i = this.mVideoWidth;
                i2 = this.mVideoHeight;
            }
        }
        setMeasuredDimension(i, i2);
    }
}
