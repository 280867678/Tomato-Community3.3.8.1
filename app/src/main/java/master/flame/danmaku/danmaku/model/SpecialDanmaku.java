package master.flame.danmaku.danmaku.model;

import java.lang.reflect.Array;

/* loaded from: classes4.dex */
public class SpecialDanmaku extends BaseDanmaku {
    public long alphaDuration;
    public int beginAlpha;
    public float beginX;
    public float beginY;
    public int deltaAlpha;
    public float deltaX;
    public float deltaY;
    public int endAlpha;
    public float endX;
    public float endY;
    public LinePath[] linePaths;
    private ScaleFactor mScaleFactor;
    private int mScaleFactorChangedFlag;
    public float rotateZ;
    public long translationDuration;
    public long translationStartDelay;
    private int mCurrentWidth = 0;
    private int mCurrentHeight = 0;
    public boolean isQuadraticEaseOut = false;
    private float[] currStateValues = new float[4];

    private static final float getQuadEaseOutProgress(long j, long j2) {
        float f = ((float) j) / ((float) j2);
        return (-1.0f) * f * (f - 2.0f);
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public int getType() {
        return 7;
    }

    /* loaded from: classes4.dex */
    public static class ScaleFactor {
        int flag = 0;
        int height;
        float scaleX;
        float scaleY;
        int width;

        public ScaleFactor(int i, int i2, float f, float f2) {
            update(i, i2, f, f2);
        }

        public void update(int i, int i2, float f, float f2) {
            if (Float.compare(this.scaleX, f) != 0 || Float.compare(this.scaleY, f2) != 0) {
                this.flag++;
            }
            this.width = i;
            this.height = i2;
            this.scaleX = f;
            this.scaleY = f2;
        }

        public boolean isUpdated(int i, int i2, int i3) {
            return (this.flag == i || (this.width == i2 && this.height == i3)) ? false : true;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public class Point {

        /* renamed from: x */
        float f6038x;

        /* renamed from: y */
        float f6039y;

        public Point(SpecialDanmaku specialDanmaku, float f, float f2) {
            this.f6038x = f;
            this.f6039y = f2;
        }

        public float getDistance(Point point) {
            float abs = Math.abs(this.f6038x - point.f6038x);
            float abs2 = Math.abs(this.f6039y - point.f6039y);
            return (float) Math.sqrt((abs * abs) + (abs2 * abs2));
        }
    }

    /* loaded from: classes4.dex */
    public class LinePath {
        public long beginTime;
        float delatX;
        float deltaY;
        public long duration;
        public long endTime;
        Point pBegin;
        Point pEnd;

        public LinePath(SpecialDanmaku specialDanmaku) {
        }

        public void setPoints(Point point, Point point2) {
            this.pBegin = point;
            this.pEnd = point2;
            this.delatX = point2.f6038x - point.f6038x;
            this.deltaY = point2.f6039y - point.f6039y;
        }

        public float getDistance() {
            return this.pEnd.getDistance(this.pBegin);
        }

        public float[] getBeginPoint() {
            Point point = this.pBegin;
            return new float[]{point.f6038x, point.f6039y};
        }

        public float[] getEndPoint() {
            Point point = this.pEnd;
            return new float[]{point.f6038x, point.f6039y};
        }
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public void measure(IDisplayer iDisplayer, boolean z) {
        super.measure(iDisplayer, z);
        if (this.mCurrentWidth == 0 || this.mCurrentHeight == 0) {
            this.mCurrentWidth = iDisplayer.getWidth();
            this.mCurrentHeight = iDisplayer.getHeight();
        }
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public void layout(IDisplayer iDisplayer, float f, float f2) {
        getRectAtTime(iDisplayer, this.mTimer.currMillisecond);
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float[] getRectAtTime(IDisplayer iDisplayer, long j) {
        LinePath linePath;
        int i;
        if (!isMeasured()) {
            return null;
        }
        if (this.mScaleFactor.isUpdated(this.mScaleFactorChangedFlag, this.mCurrentWidth, this.mCurrentHeight)) {
            ScaleFactor scaleFactor = this.mScaleFactor;
            float f = scaleFactor.scaleX;
            float f2 = scaleFactor.scaleY;
            setTranslationData(this.beginX * f, this.beginY * f2, this.endX * f, this.endY * f2, this.translationDuration, this.translationStartDelay);
            LinePath[] linePathArr = this.linePaths;
            if (linePathArr != null && linePathArr.length > 0) {
                int length = linePathArr.length;
                float[][] fArr = (float[][]) Array.newInstance(float.class, length + 1, 2);
                int i2 = 0;
                while (i2 < length) {
                    fArr[i2] = this.linePaths[i2].getBeginPoint();
                    int i3 = i2 + 1;
                    fArr[i3] = this.linePaths[i2].getEndPoint();
                    i2 = i3;
                }
                for (int i4 = 0; i4 < fArr.length; i4++) {
                    float[] fArr2 = fArr[i4];
                    fArr2[0] = fArr2[0] * f;
                    float[] fArr3 = fArr[i4];
                    fArr3[1] = fArr3[1] * f2;
                }
                setLinePathData(fArr);
            }
            ScaleFactor scaleFactor2 = this.mScaleFactor;
            this.mScaleFactorChangedFlag = scaleFactor2.flag;
            this.mCurrentWidth = scaleFactor2.width;
            this.mCurrentHeight = scaleFactor2.height;
        }
        long actualTime = j - getActualTime();
        long j2 = this.alphaDuration;
        if (j2 > 0 && (i = this.deltaAlpha) != 0) {
            if (actualTime >= j2) {
                this.alpha = this.endAlpha;
            } else {
                this.alpha = this.beginAlpha + ((int) (i * (((float) actualTime) / ((float) j2))));
            }
        }
        float f3 = this.beginX;
        float f4 = this.beginY;
        long j3 = actualTime - this.translationStartDelay;
        long j4 = this.translationDuration;
        if (j4 > 0 && j3 >= 0 && j3 <= j4) {
            LinePath[] linePathArr2 = this.linePaths;
            if (linePathArr2 != null) {
                int length2 = linePathArr2.length;
                float f5 = f4;
                float f6 = f3;
                int i5 = 0;
                while (true) {
                    if (i5 >= length2) {
                        linePath = null;
                        break;
                    }
                    linePath = linePathArr2[i5];
                    if (j3 >= linePath.beginTime && j3 < linePath.endTime) {
                        break;
                    }
                    Point point = linePath.pEnd;
                    float f7 = point.f6038x;
                    f5 = point.f6039y;
                    i5++;
                    f6 = f7;
                }
                if (linePath != null) {
                    float f8 = linePath.delatX;
                    float f9 = linePath.deltaY;
                    float f10 = ((float) (actualTime - linePath.beginTime)) / ((float) linePath.duration);
                    Point point2 = linePath.pBegin;
                    float f11 = point2.f6038x;
                    float f12 = point2.f6039y;
                    if (f8 != 0.0f) {
                        f6 = f11 + (f8 * f10);
                    }
                    if (f9 != 0.0f) {
                        f5 = f12 + (f9 * f10);
                    }
                }
                f3 = f6;
                f4 = f5;
            } else {
                float quadEaseOutProgress = this.isQuadraticEaseOut ? getQuadEaseOutProgress(j3, j4) : ((float) j3) / ((float) j4);
                float f13 = this.deltaX;
                if (f13 != 0.0f) {
                    f3 = this.beginX + (f13 * quadEaseOutProgress);
                }
                float f14 = this.deltaY;
                if (f14 != 0.0f) {
                    f4 = this.beginY + (f14 * quadEaseOutProgress);
                }
            }
        } else if (j3 > this.translationDuration) {
            f3 = this.endX;
            f4 = this.endY;
        }
        float[] fArr4 = this.currStateValues;
        fArr4[0] = f3;
        fArr4[1] = f4;
        fArr4[2] = f3 + this.paintWidth;
        fArr4[3] = f4 + this.paintHeight;
        setVisibility(!isOutside());
        return this.currStateValues;
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getLeft() {
        return this.currStateValues[0];
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getTop() {
        return this.currStateValues[1];
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getRight() {
        return this.currStateValues[2];
    }

    @Override // master.flame.danmaku.danmaku.model.BaseDanmaku
    public float getBottom() {
        return this.currStateValues[3];
    }

    public void setTranslationData(float f, float f2, float f3, float f4, long j, long j2) {
        this.beginX = f;
        this.beginY = f2;
        this.endX = f3;
        this.endY = f4;
        this.deltaX = f3 - f;
        this.deltaY = f4 - f2;
        this.translationDuration = j;
        this.translationStartDelay = j2;
    }

    public void setAlphaData(int i, int i2, long j) {
        this.beginAlpha = i;
        this.endAlpha = i2;
        this.deltaAlpha = i2 - i;
        this.alphaDuration = j;
        if (i != AlphaValue.MAX) {
            this.alpha = i;
        }
    }

    public void setLinePathData(float[][] fArr) {
        LinePath[] linePathArr;
        if (fArr != null) {
            int length = fArr.length;
            int i = 0;
            this.beginX = fArr[0][0];
            this.beginY = fArr[0][1];
            int i2 = length - 1;
            this.endX = fArr[i2][0];
            this.endY = fArr[i2][1];
            if (fArr.length <= 1) {
                return;
            }
            this.linePaths = new LinePath[fArr.length - 1];
            int i3 = 0;
            while (true) {
                linePathArr = this.linePaths;
                if (i3 >= linePathArr.length) {
                    break;
                }
                linePathArr[i3] = new LinePath(this);
                i3++;
                this.linePaths[i3].setPoints(new Point(this, fArr[i3][0], fArr[i3][1]), new Point(this, fArr[i3][0], fArr[i3][1]));
            }
            float f = 0.0f;
            for (LinePath linePath : linePathArr) {
                f += linePath.getDistance();
            }
            LinePath linePath2 = null;
            LinePath[] linePathArr2 = this.linePaths;
            int length2 = linePathArr2.length;
            while (i < length2) {
                LinePath linePath3 = linePathArr2[i];
                linePath3.duration = (linePath3.getDistance() / f) * ((float) this.translationDuration);
                linePath3.beginTime = linePath2 == null ? 0L : linePath2.endTime;
                linePath3.endTime = linePath3.beginTime + linePath3.duration;
                i++;
                linePath2 = linePath3;
            }
        }
    }

    public void setScaleFactor(ScaleFactor scaleFactor) {
        this.mScaleFactor = scaleFactor;
        this.mScaleFactorChangedFlag = scaleFactor.flag;
    }
}
