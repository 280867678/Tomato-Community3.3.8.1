package com.tomatolive.library.p136ui.view.custom.luckpan;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import com.tomatolive.library.R$drawable;
import com.tomatolive.library.utils.NumberUtils;
import com.tomatolive.library.utils.SystemUtils;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import java.util.List;
import java.util.concurrent.TimeUnit;

/* renamed from: com.tomatolive.library.ui.view.custom.luckpan.WheelSurfPanView */
/* loaded from: classes3.dex */
public class WheelSurfPanView extends View {
    private RectF arcRect;
    private RectF badgeIconRect;
    private Paint badgePaint;
    private int boomMultiple;
    private Path circlePath;
    private Paint.FontMetrics fontMetrics;
    private Bitmap fullWhite;
    private Bitmap fullyellow;
    private RectF iconRect;
    private boolean isBoom;
    private boolean isLight;
    private Disposable lightDisposable;
    private float mAngle;
    private int mCenter;
    private Integer[] mColors;
    private Context mContext;
    private Rect mDestRect;
    private Integer mHuanImgRes;
    private List<Bitmap> mListBitmap;
    private String[] mName;
    private String[] mNum;
    private Paint mPaint;
    private int mRadius;
    private int mTextColor;
    private Paint mTextPaint;
    private float mTextSize;
    private int mWidth;
    private Bitmap mYuanHuan;
    private Bitmap nomarlWhite;
    private Bitmap nomarlyellow;
    private PaintFlagsDrawFilter paintFlagsDrawFilter;
    private RotateListener rotateListener;
    private StringBuilder stringBuilder;
    private RectF textRect;
    private int mTypeNum = 6;
    private int boomstatus = -1;
    private final String OPERATOR_STR = "x";

    public RotateListener getRotateListener() {
        return this.rotateListener;
    }

    public void setRotateListener(RotateListener rotateListener) {
        this.rotateListener = rotateListener;
    }

    public WheelSurfPanView(Context context) {
        super(context);
        init(context);
    }

    public WheelSurfPanView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        init(context);
    }

    public void setmTypeNum(int i) {
        this.mTypeNum = i;
    }

    public void setNum(String[] strArr) {
        this.mNum = strArr;
    }

    public void setName(String[] strArr) {
        this.mName = strArr;
    }

    public void setmIcons(List<Bitmap> list) {
        this.mListBitmap = list;
    }

    public void setmColors(Integer[] numArr) {
        this.mColors = numArr;
    }

    public void setmHuanImgRes(Integer num) {
        this.mHuanImgRes = num;
    }

    private void init(Context context) {
        this.mContext = context;
        setBackgroundColor(0);
        this.fontMetrics = new Paint.FontMetrics();
        this.badgePaint = new Paint(1);
        this.badgePaint.setColor(-1);
        this.badgePaint.setTextSize(SystemUtils.sp2px(6.0f));
        this.badgePaint.setStyle(Paint.Style.FILL);
        this.badgePaint.setTextAlign(Paint.Align.CENTER);
        this.fullWhite = BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_lottery_turntable_bg_full_white);
        this.fullyellow = BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_lottery_turntable_bg_full_yellow);
        this.nomarlWhite = BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_lottery_turntable_bg_white);
        this.nomarlyellow = BitmapFactory.decodeResource(getResources(), R$drawable.fq_ic_lottery_turntable_bg_yellow);
        this.mPaint = new Paint();
        this.mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        this.mPaint.setAntiAlias(true);
        this.mPaint.setDither(true);
        this.paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, 3);
        this.mDestRect = new Rect();
        this.arcRect = new RectF();
        this.iconRect = new RectF();
        this.badgeIconRect = new RectF();
        this.textRect = new RectF();
        this.circlePath = new Path();
        this.stringBuilder = new StringBuilder();
    }

    @Override // android.view.View
    protected void onMeasure(int i, int i2) {
        setMeasuredDimension(i, i2);
        int min = Math.min(View.MeasureSpec.getSize(i), View.MeasureSpec.getSize(i2));
        this.mWidth = min;
        int i3 = this.mWidth;
        this.mCenter = i3 / 2;
        this.mRadius = (((i3 - getPaddingLeft()) - getPaddingRight()) / 2) - (this.mWidth / 10);
        setMeasuredDimension(min, min);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r9v1 */
    /* JADX WARN: Type inference failed for: r9v2, types: [android.graphics.Rect, android.graphics.Paint] */
    /* JADX WARN: Type inference failed for: r9v3 */
    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (this.mYuanHuan == null || this.mColors == null || this.mNum == null || this.mListBitmap == null) {
            return;
        }
        canvas.setDrawFilter(this.paintFlagsDrawFilter);
        this.mDestRect.set(getPaddingLeft(), getPaddingTop(), this.mWidth - getPaddingRight(), this.mWidth - getPaddingBottom());
        ?? r9 = 0;
        canvas.drawBitmap(this.mYuanHuan, (Rect) null, this.mDestRect, this.mPaint);
        RectF rectF = this.arcRect;
        int i = this.mCenter;
        int i2 = this.mRadius;
        rectF.set(i - i2, i - i2, i + i2, i + i2);
        int i3 = 0;
        float f = ((-this.mAngle) / 2.0f) - 90.0f;
        int i4 = 0;
        while (i4 < this.mTypeNum) {
            this.mPaint.setColor(this.mColors[i4].intValue());
            canvas.drawArc(this.arcRect, f, this.mAngle, true, this.mPaint);
            this.mTextPaint.setColor(this.mTextColor);
            String str = this.mName[i4];
            String str2 = this.mNum[i4];
            this.stringBuilder.setLength(i3);
            if (!TextUtils.isEmpty(str2) && NumberUtils.string2int(str2) > 0) {
                StringBuilder sb = this.stringBuilder;
                sb.append(str);
                sb.append("x");
                sb.append(str2);
                drawText(f, sb.toString(), this.mRadius, this.mTextPaint, canvas);
            }
            int i5 = this.mRadius;
            double radians = (float) Math.toRadians((this.mAngle / 2.0f) + f);
            float cos = (float) (this.mCenter + (((i5 / 2) + (i5 / 7)) * Math.cos(radians)));
            int i6 = this.mRadius;
            float sin = (float) (this.mCenter + (((i6 / 2) + (i6 / 7)) * Math.sin(radians)));
            float f2 = ((this.mRadius / 4) * 2) / 3;
            this.iconRect.set(cos - f2, sin - f2, cos + f2, sin + f2);
            canvas.save();
            canvas.rotate(this.mAngle * i4, this.iconRect.centerX(), this.iconRect.centerY());
            canvas.drawBitmap(this.mListBitmap.get(i4), (Rect) r9, this.iconRect, (Paint) r9);
            canvas.restore();
            if (this.boomstatus == i4) {
                float f3 = this.mAngle;
                double radians2 = Math.toRadians((f3 / 2.0f) + f + (f3 / 5.0f));
                this.badgeIconRect.left = (float) (this.mCenter + (Math.cos(radians2) * this.mRadius * 0.9f));
                this.badgeIconRect.top = (float) (this.mCenter + (Math.sin(radians2) * this.mRadius * 0.9f));
                RectF rectF2 = this.badgeIconRect;
                rectF2.right = rectF2.left + SystemUtils.dp2px(13.0f);
                RectF rectF3 = this.badgeIconRect;
                rectF3.bottom = rectF3.top + SystemUtils.dp2px(10.0f);
                this.badgePaint.setColor(Color.parseColor("#FF4154"));
                canvas.drawRoundRect(this.badgeIconRect, SystemUtils.dp2px(2.0f), SystemUtils.dp2px(2.0f), this.badgePaint);
                this.badgePaint.setColor(-1);
                this.badgePaint.getFontMetrics(this.fontMetrics);
                this.stringBuilder.setLength(0);
                StringBuilder sb2 = this.stringBuilder;
                sb2.append("x");
                sb2.append(this.boomMultiple);
                String sb3 = sb2.toString();
                float centerX = this.badgeIconRect.centerX();
                float centerY = this.badgeIconRect.centerY();
                Paint.FontMetrics fontMetrics = this.fontMetrics;
                canvas.drawText(sb3, centerX, centerY - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f), this.badgePaint);
            }
            f += this.mAngle;
            i4++;
            r9 = 0;
            i3 = 0;
        }
    }

    public void startBoom(boolean z) {
        this.isBoom = z;
        if (z) {
            this.mYuanHuan = this.isLight ? this.fullWhite : this.fullyellow;
        } else {
            this.mYuanHuan = this.isLight ? this.nomarlWhite : this.nomarlyellow;
        }
        if (this.mYuanHuan != null) {
            invalidate();
        }
    }

    public void stopLight() {
        Disposable disposable = this.lightDisposable;
        if (disposable == null || disposable.isDisposed()) {
            return;
        }
        this.lightDisposable.dispose();
    }

    public void startLight() {
        Observable.interval(0L, 200L, TimeUnit.MILLISECONDS).map(new Function() { // from class: com.tomatolive.library.ui.view.custom.luckpan.-$$Lambda$WheelSurfPanView$cs6GSqnPFt80tXGWt46bcyMwiWY
            @Override // io.reactivex.functions.Function
            /* renamed from: apply */
            public final Object mo6755apply(Object obj) {
                return WheelSurfPanView.this.lambda$startLight$0$WheelSurfPanView((Long) obj);
            }
        }).subscribeOn(Schedulers.m90io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Boolean>() { // from class: com.tomatolive.library.ui.view.custom.luckpan.WheelSurfPanView.1
            @Override // io.reactivex.Observer
            public void onComplete() {
            }

            @Override // io.reactivex.Observer
            public void onError(Throwable th) {
            }

            @Override // io.reactivex.Observer
            public void onSubscribe(Disposable disposable) {
                WheelSurfPanView.this.lightDisposable = disposable;
            }

            @Override // io.reactivex.Observer
            public void onNext(Boolean bool) {
                WheelSurfPanView wheelSurfPanView = WheelSurfPanView.this;
                wheelSurfPanView.isLight = !wheelSurfPanView.isLight;
                if (WheelSurfPanView.this.mYuanHuan != null) {
                    WheelSurfPanView.this.invalidate();
                }
            }
        });
    }

    public /* synthetic */ Boolean lambda$startLight$0$WheelSurfPanView(Long l) throws Exception {
        if (this.isBoom) {
            this.mYuanHuan = this.isLight ? this.fullWhite : this.fullyellow;
        } else {
            this.mYuanHuan = this.isLight ? this.nomarlWhite : this.nomarlyellow;
        }
        return true;
    }

    private void drawText(float f, String str, int i, Paint paint, Canvas canvas) {
        RectF rectF = this.textRect;
        int i2 = this.mCenter;
        rectF.set(i2 - i, i2 - i, i2 + i, i2 + i);
        this.circlePath.reset();
        this.circlePath.addArc(this.textRect, f, this.mAngle);
        float measureText = paint.measureText(str);
        canvas.drawTextOnPath(str, this.circlePath, (((float) (((i * 3.141592653589793d) * this.mAngle) / 180.0d)) / 2.0f) - (measureText / 2.0f), i / 6, paint);
    }

    public void show() {
        Integer num = this.mHuanImgRes;
        if (num == null || num.intValue() == 0) {
            this.mYuanHuan = BitmapFactory.decodeResource(this.mContext.getResources(), R$drawable.fq_ic_lottery_turntable_bg_yellow);
        } else {
            this.mYuanHuan = BitmapFactory.decodeResource(this.mContext.getResources(), this.mHuanImgRes.intValue());
        }
        if (this.mTextSize == 0.0f) {
            this.mTextSize = SystemUtils.sp2px(8.0f);
        }
        if (this.mTextColor == 0) {
            this.mTextColor = Color.parseColor("#521F77");
        }
        if (this.mTextPaint == null) {
            this.mTextPaint = new Paint();
            this.mTextPaint.setStyle(Paint.Style.FILL);
            this.mTextPaint.setAntiAlias(true);
            this.mTextPaint.setDither(true);
            this.mTextPaint.setColor(this.mTextColor);
            this.mTextPaint.setTextSize(this.mTextSize);
            this.mTextPaint.setFakeBoldText(true);
            if (Build.VERSION.SDK_INT >= 21) {
                this.mTextPaint.setLetterSpacing(0.1f);
            }
        }
        int i = this.mTypeNum;
        if (i != 0) {
            this.mAngle = (float) (360.0d / i);
        }
        invalidate();
    }

    public void release() {
        recycleBitmap(this.fullWhite);
        recycleBitmap(this.fullyellow);
        recycleBitmap(this.nomarlWhite);
        recycleBitmap(this.nomarlyellow);
    }

    public void recycleBitmap(Bitmap bitmap) {
        if (bitmap == null || bitmap.isRecycled()) {
            return;
        }
        bitmap.recycle();
    }

    public void setBoomStatus(int i) {
        this.boomstatus = i;
    }

    public void setBoomMultiple(int i) {
        this.boomMultiple = i;
    }
}
