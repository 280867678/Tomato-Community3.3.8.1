package com.opensource.svgaplayer.drawer;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Shader;
import android.media.SoundPool;
import android.os.Build;
import android.text.BoringLayout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.text.TextUtils;
import android.widget.ImageView;
import com.opensource.svgaplayer.IClickAreaListener;
import com.opensource.svgaplayer.SVGADynamicEntity;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.opensource.svgaplayer.drawer.SGVADrawer;
import com.opensource.svgaplayer.entities.SVGAAudioEntity;
import com.opensource.svgaplayer.entities.SVGAPathEntity;
import com.opensource.svgaplayer.entities.SVGAVideoShapeEntity;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsJVM;

/* compiled from: SVGACanvasDrawer.kt */
/* loaded from: classes3.dex */
public final class SVGACanvasDrawer extends SGVADrawer {
    private Boolean[] beginIndexList;
    private final SVGADynamicEntity dynamicItem;
    private Boolean[] endIndexList;
    private final ShareValues sharedValues = new ShareValues();
    private final HashMap<String, Bitmap> drawTextCache = new HashMap<>();
    private final PathCache pathCache = new PathCache();
    private final float[] matrixScaleTempValues = new float[16];

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SVGACanvasDrawer(SVGAVideoEntity videoItem, SVGADynamicEntity dynamicItem) {
        super(videoItem);
        Intrinsics.checkParameterIsNotNull(videoItem, "videoItem");
        Intrinsics.checkParameterIsNotNull(dynamicItem, "dynamicItem");
        this.dynamicItem = dynamicItem;
    }

    @Override // com.opensource.svgaplayer.drawer.SGVADrawer
    public void drawFrame(Canvas canvas, int i, ImageView.ScaleType scaleType) {
        boolean z;
        SGVADrawer.SVGADrawerSprite sVGADrawerSprite;
        int i2;
        int i3;
        SGVADrawer.SVGADrawerSprite sVGADrawerSprite2;
        boolean endsWith$default;
        boolean endsWith$default2;
        Intrinsics.checkParameterIsNotNull(canvas, "canvas");
        Intrinsics.checkParameterIsNotNull(scaleType, "scaleType");
        super.drawFrame(canvas, i, scaleType);
        playAudio(i);
        this.pathCache.onSizeChanged(canvas);
        List<SGVADrawer.SVGADrawerSprite> requestFrameSprites$library_release = requestFrameSprites$library_release(i);
        if (requestFrameSprites$library_release.size() <= 0) {
            return;
        }
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        Object obj = null;
        this.beginIndexList = null;
        this.endIndexList = null;
        boolean z2 = false;
        String imageKey = requestFrameSprites$library_release.get(0).getImageKey();
        int i4 = 2;
        if (imageKey != null) {
            endsWith$default2 = StringsJVM.endsWith$default(imageKey, ".matte", false, 2, null);
            z = endsWith$default2;
        } else {
            z = false;
        }
        int i5 = -1;
        int i6 = 0;
        for (Object obj2 : requestFrameSprites$library_release) {
            int i7 = i6 + 1;
            if (i6 >= 0) {
                SGVADrawer.SVGADrawerSprite sVGADrawerSprite3 = (SGVADrawer.SVGADrawerSprite) obj2;
                String imageKey2 = sVGADrawerSprite3.getImageKey();
                if (imageKey2 != null) {
                    if (!z || Build.VERSION.SDK_INT < 21) {
                        drawSprite(sVGADrawerSprite3, canvas, i);
                    } else {
                        endsWith$default = StringsJVM.endsWith$default(imageKey2, ".matte", z2, i4, obj);
                        if (endsWith$default) {
                            linkedHashMap.put(imageKey2, sVGADrawerSprite3);
                        }
                    }
                    i6 = i7;
                    obj = null;
                    z2 = false;
                    i4 = 2;
                }
                if (!isMatteBegin(i6, requestFrameSprites$library_release)) {
                    sVGADrawerSprite = sVGADrawerSprite3;
                    i2 = i6;
                    i3 = -1;
                } else if (Build.VERSION.SDK_INT >= 21) {
                    sVGADrawerSprite = sVGADrawerSprite3;
                    i2 = i6;
                    i3 = -1;
                    i5 = canvas.saveLayer(0.0f, 0.0f, canvas.getWidth(), canvas.getHeight(), null);
                } else {
                    sVGADrawerSprite = sVGADrawerSprite3;
                    i2 = i6;
                    i3 = -1;
                    canvas.save();
                }
                drawSprite(sVGADrawerSprite, canvas, i);
                if (isMatteEnd(i2, requestFrameSprites$library_release) && (sVGADrawerSprite2 = (SGVADrawer.SVGADrawerSprite) linkedHashMap.get(sVGADrawerSprite.getMatteKey())) != null) {
                    drawSprite(sVGADrawerSprite2, this.sharedValues.shareMatteCanvas(canvas.getWidth(), canvas.getHeight()), i);
                    canvas.drawBitmap(this.sharedValues.sharedMatteBitmap(), 0.0f, 0.0f, this.sharedValues.shareMattePaint());
                    if (i5 != i3) {
                        canvas.restoreToCount(i5);
                    } else {
                        canvas.restore();
                    }
                }
                i6 = i7;
                obj = null;
                z2 = false;
                i4 = 2;
            } else {
                CollectionsKt.throwIndexOverflow();
                throw null;
            }
        }
    }

    private final boolean isMatteBegin(int i, List<SGVADrawer.SVGADrawerSprite> list) {
        Boolean bool;
        SGVADrawer.SVGADrawerSprite sVGADrawerSprite;
        boolean endsWith$default;
        if (this.beginIndexList == null) {
            Boolean[] boolArr = new Boolean[list.size()];
            int length = boolArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                boolArr[i2] = false;
            }
            int i3 = 0;
            for (Object obj : list) {
                int i4 = i3 + 1;
                if (i3 >= 0) {
                    SGVADrawer.SVGADrawerSprite sVGADrawerSprite2 = (SGVADrawer.SVGADrawerSprite) obj;
                    String imageKey = sVGADrawerSprite2.getImageKey();
                    if (imageKey != null) {
                        endsWith$default = StringsJVM.endsWith$default(imageKey, ".matte", false, 2, null);
                        if (endsWith$default) {
                            i3 = i4;
                        }
                    }
                    String matteKey = sVGADrawerSprite2.getMatteKey();
                    if (matteKey != null && matteKey.length() > 0 && (sVGADrawerSprite = list.get(i3 - 1)) != null) {
                        if (sVGADrawerSprite.getMatteKey() == null || sVGADrawerSprite.getMatteKey().length() == 0) {
                            boolArr[i3] = true;
                        } else if (!Intrinsics.areEqual(sVGADrawerSprite.getMatteKey(), sVGADrawerSprite2.getMatteKey())) {
                            boolArr[i3] = true;
                        }
                    }
                    i3 = i4;
                } else {
                    CollectionsKt.throwIndexOverflow();
                    throw null;
                }
            }
            this.beginIndexList = boolArr;
        }
        Boolean[] boolArr2 = this.beginIndexList;
        if (boolArr2 == null || (bool = boolArr2[i]) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    private final boolean isMatteEnd(int i, List<SGVADrawer.SVGADrawerSprite> list) {
        Boolean bool;
        boolean endsWith$default;
        if (this.endIndexList == null) {
            Boolean[] boolArr = new Boolean[list.size()];
            int length = boolArr.length;
            for (int i2 = 0; i2 < length; i2++) {
                boolArr[i2] = false;
            }
            int i3 = 0;
            for (Object obj : list) {
                int i4 = i3 + 1;
                if (i3 >= 0) {
                    SGVADrawer.SVGADrawerSprite sVGADrawerSprite = (SGVADrawer.SVGADrawerSprite) obj;
                    String imageKey = sVGADrawerSprite.getImageKey();
                    if (imageKey != null) {
                        endsWith$default = StringsJVM.endsWith$default(imageKey, ".matte", false, 2, null);
                        if (endsWith$default) {
                            i3 = i4;
                        }
                    }
                    String matteKey = sVGADrawerSprite.getMatteKey();
                    if (matteKey != null && matteKey.length() > 0) {
                        if (i3 == list.size() - 1) {
                            boolArr[i3] = true;
                        } else {
                            SGVADrawer.SVGADrawerSprite sVGADrawerSprite2 = list.get(i4);
                            if (sVGADrawerSprite2 != null) {
                                if (sVGADrawerSprite2.getMatteKey() == null || sVGADrawerSprite2.getMatteKey().length() == 0) {
                                    boolArr[i3] = true;
                                } else if (!Intrinsics.areEqual(sVGADrawerSprite2.getMatteKey(), sVGADrawerSprite.getMatteKey())) {
                                    boolArr[i3] = true;
                                }
                            }
                        }
                    }
                    i3 = i4;
                } else {
                    CollectionsKt.throwIndexOverflow();
                    throw null;
                }
            }
            this.endIndexList = boolArr;
        }
        Boolean[] boolArr2 = this.endIndexList;
        if (boolArr2 == null || (bool = boolArr2[i]) == null) {
            return false;
        }
        return bool.booleanValue();
    }

    private final void playAudio(int i) {
        SoundPool soundPool$library_release;
        Integer soundID;
        for (SVGAAudioEntity sVGAAudioEntity : getVideoItem().getAudios$library_release()) {
            if (sVGAAudioEntity.getStartFrame() == i && (soundPool$library_release = getVideoItem().getSoundPool$library_release()) != null && (soundID = sVGAAudioEntity.getSoundID()) != null) {
                sVGAAudioEntity.setPlayID(Integer.valueOf(soundPool$library_release.play(soundID.intValue(), 1.0f, 1.0f, 1, 0, 1.0f)));
            }
            if (sVGAAudioEntity.getEndFrame() <= i) {
                Integer playID = sVGAAudioEntity.getPlayID();
                if (playID != null) {
                    int intValue = playID.intValue();
                    SoundPool soundPool$library_release2 = getVideoItem().getSoundPool$library_release();
                    if (soundPool$library_release2 != null) {
                        soundPool$library_release2.stop(intValue);
                    }
                }
                sVGAAudioEntity.setPlayID(null);
            }
        }
    }

    private final Matrix shareFrameMatrix(Matrix matrix) {
        Matrix sharedMatrix = this.sharedValues.sharedMatrix();
        sharedMatrix.postScale(getScaleInfo().getScaleFx(), getScaleInfo().getScaleFy());
        sharedMatrix.postTranslate(getScaleInfo().getTranFx(), getScaleInfo().getTranFy());
        sharedMatrix.preConcat(matrix);
        return sharedMatrix;
    }

    private final void drawSprite(SGVADrawer.SVGADrawerSprite sVGADrawerSprite, Canvas canvas, int i) {
        drawImage(sVGADrawerSprite, canvas);
        drawShape(sVGADrawerSprite, canvas);
        drawDynamic(sVGADrawerSprite, canvas, i);
    }

    private final void drawImage(SGVADrawer.SVGADrawerSprite sVGADrawerSprite, Canvas canvas) {
        String replace$default;
        String imageKey = sVGADrawerSprite.getImageKey();
        if (imageKey == null || Intrinsics.areEqual(this.dynamicItem.getDynamicHidden$library_release().get(imageKey), true)) {
            return;
        }
        replace$default = StringsJVM.replace$default(imageKey, ".matte", "", false, 4, null);
        Bitmap bitmap = this.dynamicItem.getDynamicImage$library_release().get(replace$default);
        if (bitmap == null) {
            bitmap = getVideoItem().getImages$library_release().get(replace$default);
        }
        Bitmap bitmap2 = bitmap;
        if (bitmap2 == null) {
            return;
        }
        Matrix shareFrameMatrix = shareFrameMatrix(sVGADrawerSprite.getFrameEntity().getTransform());
        Paint sharedPaint = this.sharedValues.sharedPaint();
        sharedPaint.setAntiAlias(getVideoItem().getAntiAlias());
        sharedPaint.setFilterBitmap(getVideoItem().getAntiAlias());
        sharedPaint.setAlpha((int) (sVGADrawerSprite.getFrameEntity().getAlpha() * 255));
        if (sVGADrawerSprite.getFrameEntity().getMaskPath() != null) {
            SVGAPathEntity maskPath = sVGADrawerSprite.getFrameEntity().getMaskPath();
            if (maskPath == null) {
                return;
            }
            canvas.save();
            Path sharedPath = this.sharedValues.sharedPath();
            maskPath.buildPath(sharedPath);
            sharedPath.transform(shareFrameMatrix);
            canvas.clipPath(sharedPath);
            shareFrameMatrix.preScale((float) (sVGADrawerSprite.getFrameEntity().getLayout().getWidth() / bitmap2.getWidth()), (float) (sVGADrawerSprite.getFrameEntity().getLayout().getWidth() / bitmap2.getWidth()));
            if (!bitmap2.isRecycled()) {
                canvas.drawBitmap(bitmap2, shareFrameMatrix, sharedPaint);
            }
            canvas.restore();
        } else {
            shareFrameMatrix.preScale((float) (sVGADrawerSprite.getFrameEntity().getLayout().getWidth() / bitmap2.getWidth()), (float) (sVGADrawerSprite.getFrameEntity().getLayout().getWidth() / bitmap2.getWidth()));
            if (!bitmap2.isRecycled()) {
                canvas.drawBitmap(bitmap2, shareFrameMatrix, sharedPaint);
            }
        }
        IClickAreaListener iClickAreaListener = this.dynamicItem.getDynamicIClickArea$library_release().get(imageKey);
        if (iClickAreaListener != null) {
            float[] fArr = {0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f, 0.0f};
            shareFrameMatrix.getValues(fArr);
            iClickAreaListener.onResponseArea(imageKey, (int) fArr[2], (int) fArr[5], (int) ((bitmap2.getWidth() * fArr[0]) + fArr[2]), (int) ((bitmap2.getHeight() * fArr[4]) + fArr[5]));
        }
        drawTextOnBitmap(canvas, bitmap2, sVGADrawerSprite, shareFrameMatrix);
    }

    private final void drawTextOnBitmap(Canvas canvas, Bitmap bitmap, SGVADrawer.SVGADrawerSprite sVGADrawerSprite, Matrix matrix) {
        int i;
        StaticLayout layout;
        TextPaint drawingTextPaint;
        if (this.dynamicItem.isTextDirty$library_release()) {
            this.drawTextCache.clear();
            this.dynamicItem.setTextDirty$library_release(false);
        }
        String imageKey = sVGADrawerSprite.getImageKey();
        if (imageKey != null) {
            Bitmap bitmap2 = null;
            String str = this.dynamicItem.getDynamicText$library_release().get(imageKey);
            if (str != null && (drawingTextPaint = this.dynamicItem.getDynamicTextPaint$library_release().get(imageKey)) != null && (bitmap2 = this.drawTextCache.get(imageKey)) == null) {
                bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                Canvas canvas2 = new Canvas(bitmap2);
                Intrinsics.checkExpressionValueIsNotNull(drawingTextPaint, "drawingTextPaint");
                drawingTextPaint.setAntiAlias(true);
                drawingTextPaint.setStyle(Paint.Style.FILL);
                drawingTextPaint.setTextAlign(Paint.Align.CENTER);
                Paint.FontMetrics fontMetrics = drawingTextPaint.getFontMetrics();
                float f = 2;
                canvas2.drawText(str, rect.centerX(), (rect.centerY() - (fontMetrics.top / f)) - (fontMetrics.bottom / f), drawingTextPaint);
                HashMap<String, Bitmap> hashMap = this.drawTextCache;
                if (bitmap2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
                }
                hashMap.put(imageKey, bitmap2);
            }
            BoringLayout it2 = this.dynamicItem.getDynamicBoringLayoutText$library_release().get(imageKey);
            if (it2 != null && (bitmap2 = this.drawTextCache.get(imageKey)) == null) {
                Intrinsics.checkExpressionValueIsNotNull(it2, "it");
                TextPaint paint = it2.getPaint();
                Intrinsics.checkExpressionValueIsNotNull(paint, "it.paint");
                paint.setAntiAlias(true);
                bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas3 = new Canvas(bitmap2);
                canvas3.translate(0.0f, (bitmap.getHeight() - it2.getHeight()) / 2);
                it2.draw(canvas3);
                HashMap<String, Bitmap> hashMap2 = this.drawTextCache;
                if (bitmap2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
                }
                hashMap2.put(imageKey, bitmap2);
            }
            StaticLayout it3 = this.dynamicItem.getDynamicStaticLayoutText$library_release().get(imageKey);
            if (it3 != null && (bitmap2 = this.drawTextCache.get(imageKey)) == null) {
                Intrinsics.checkExpressionValueIsNotNull(it3, "it");
                TextPaint paint2 = it3.getPaint();
                Intrinsics.checkExpressionValueIsNotNull(paint2, "it.paint");
                paint2.setAntiAlias(true);
                if (Build.VERSION.SDK_INT >= 23) {
                    try {
                        Field field = StaticLayout.class.getDeclaredField("mMaximumVisibleLineCount");
                        Intrinsics.checkExpressionValueIsNotNull(field, "field");
                        field.setAccessible(true);
                        i = field.getInt(it3);
                    } catch (Exception unused) {
                        i = Integer.MAX_VALUE;
                    }
                    layout = StaticLayout.Builder.obtain(it3.getText(), 0, it3.getText().length(), it3.getPaint(), bitmap.getWidth()).setAlignment(it3.getAlignment()).setMaxLines(i).setEllipsize(TextUtils.TruncateAt.END).build();
                } else {
                    layout = new StaticLayout(it3.getText(), 0, it3.getText().length(), it3.getPaint(), bitmap.getWidth(), it3.getAlignment(), it3.getSpacingMultiplier(), it3.getSpacingAdd(), false);
                }
                bitmap2 = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
                Canvas canvas4 = new Canvas(bitmap2);
                int height = bitmap.getHeight();
                Intrinsics.checkExpressionValueIsNotNull(layout, "layout");
                canvas4.translate(0.0f, (height - layout.getHeight()) / 2);
                layout.draw(canvas4);
                HashMap<String, Bitmap> hashMap3 = this.drawTextCache;
                if (bitmap2 == null) {
                    throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
                }
                hashMap3.put(imageKey, bitmap2);
            }
            if (bitmap2 == null) {
                return;
            }
            Paint sharedPaint = this.sharedValues.sharedPaint();
            sharedPaint.setAntiAlias(getVideoItem().getAntiAlias());
            sharedPaint.setAlpha((int) (sVGADrawerSprite.getFrameEntity().getAlpha() * 255));
            if (sVGADrawerSprite.getFrameEntity().getMaskPath() != null) {
                SVGAPathEntity maskPath = sVGADrawerSprite.getFrameEntity().getMaskPath();
                if (maskPath == null) {
                    return;
                }
                canvas.save();
                canvas.concat(matrix);
                canvas.clipRect(0, 0, bitmap.getWidth(), bitmap.getHeight());
                Shader.TileMode tileMode = Shader.TileMode.REPEAT;
                sharedPaint.setShader(new BitmapShader(bitmap2, tileMode, tileMode));
                Path sharedPath = this.sharedValues.sharedPath();
                maskPath.buildPath(sharedPath);
                canvas.drawPath(sharedPath, sharedPaint);
                canvas.restore();
                return;
            }
            sharedPaint.setFilterBitmap(getVideoItem().getAntiAlias());
            canvas.drawBitmap(bitmap2, matrix, sharedPaint);
        }
    }

    private final void drawShape(SGVADrawer.SVGADrawerSprite sVGADrawerSprite, Canvas canvas) {
        SVGAVideoShapeEntity.Styles styles;
        float[] lineDash;
        String lineJoin;
        boolean equals;
        boolean equals2;
        boolean equals3;
        String lineCap;
        boolean equals4;
        boolean equals5;
        boolean equals6;
        int fill;
        Matrix shareFrameMatrix = shareFrameMatrix(sVGADrawerSprite.getFrameEntity().getTransform());
        for (SVGAVideoShapeEntity sVGAVideoShapeEntity : sVGADrawerSprite.getFrameEntity().getShapes()) {
            sVGAVideoShapeEntity.buildPath();
            if (sVGAVideoShapeEntity.getShapePath() != null) {
                Paint sharedPaint = this.sharedValues.sharedPaint();
                sharedPaint.reset();
                sharedPaint.setAntiAlias(getVideoItem().getAntiAlias());
                double d = 255;
                sharedPaint.setAlpha((int) (sVGADrawerSprite.getFrameEntity().getAlpha() * d));
                Path sharedPath = this.sharedValues.sharedPath();
                sharedPath.reset();
                sharedPath.addPath(this.pathCache.buildPath(sVGAVideoShapeEntity));
                Matrix sharedMatrix2 = this.sharedValues.sharedMatrix2();
                sharedMatrix2.reset();
                Matrix transform = sVGAVideoShapeEntity.getTransform();
                if (transform != null) {
                    sharedMatrix2.postConcat(transform);
                }
                sharedMatrix2.postConcat(shareFrameMatrix);
                sharedPath.transform(sharedMatrix2);
                SVGAVideoShapeEntity.Styles styles2 = sVGAVideoShapeEntity.getStyles();
                if (styles2 != null && (fill = styles2.getFill()) != 0) {
                    sharedPaint.setStyle(Paint.Style.FILL);
                    sharedPaint.setColor(fill);
                    sharedPaint.setAlpha(Math.min(255, Math.max(0, (int) (sVGADrawerSprite.getFrameEntity().getAlpha() * d))));
                    if (sVGADrawerSprite.getFrameEntity().getMaskPath() != null) {
                        canvas.save();
                    }
                    SVGAPathEntity maskPath = sVGADrawerSprite.getFrameEntity().getMaskPath();
                    if (maskPath != null) {
                        Path sharedPath2 = this.sharedValues.sharedPath2();
                        maskPath.buildPath(sharedPath2);
                        sharedPath2.transform(shareFrameMatrix);
                        canvas.clipPath(sharedPath2);
                    }
                    canvas.drawPath(sharedPath, sharedPaint);
                    if (sVGADrawerSprite.getFrameEntity().getMaskPath() != null) {
                        canvas.restore();
                    }
                }
                SVGAVideoShapeEntity.Styles styles3 = sVGAVideoShapeEntity.getStyles();
                if (styles3 != null) {
                    float f = 0;
                    if (styles3.getStrokeWidth() > f) {
                        sharedPaint.setStyle(Paint.Style.STROKE);
                        SVGAVideoShapeEntity.Styles styles4 = sVGAVideoShapeEntity.getStyles();
                        if (styles4 != null) {
                            sharedPaint.setColor(styles4.getStroke());
                            sharedPaint.setAlpha(Math.min(255, Math.max(0, (int) (sVGADrawerSprite.getFrameEntity().getAlpha() * d))));
                        }
                        float matrixScale = matrixScale(shareFrameMatrix);
                        SVGAVideoShapeEntity.Styles styles5 = sVGAVideoShapeEntity.getStyles();
                        if (styles5 != null) {
                            sharedPaint.setStrokeWidth(styles5.getStrokeWidth() * matrixScale);
                        }
                        SVGAVideoShapeEntity.Styles styles6 = sVGAVideoShapeEntity.getStyles();
                        if (styles6 != null && (lineCap = styles6.getLineCap()) != null) {
                            equals4 = StringsJVM.equals(lineCap, "butt", true);
                            if (equals4) {
                                sharedPaint.setStrokeCap(Paint.Cap.BUTT);
                            } else {
                                equals5 = StringsJVM.equals(lineCap, "round", true);
                                if (equals5) {
                                    sharedPaint.setStrokeCap(Paint.Cap.ROUND);
                                } else {
                                    equals6 = StringsJVM.equals(lineCap, "square", true);
                                    if (equals6) {
                                        sharedPaint.setStrokeCap(Paint.Cap.SQUARE);
                                    }
                                }
                            }
                        }
                        SVGAVideoShapeEntity.Styles styles7 = sVGAVideoShapeEntity.getStyles();
                        if (styles7 != null && (lineJoin = styles7.getLineJoin()) != null) {
                            equals = StringsJVM.equals(lineJoin, "miter", true);
                            if (equals) {
                                sharedPaint.setStrokeJoin(Paint.Join.MITER);
                            } else {
                                equals2 = StringsJVM.equals(lineJoin, "round", true);
                                if (equals2) {
                                    sharedPaint.setStrokeJoin(Paint.Join.ROUND);
                                } else {
                                    equals3 = StringsJVM.equals(lineJoin, "bevel", true);
                                    if (equals3) {
                                        sharedPaint.setStrokeJoin(Paint.Join.BEVEL);
                                    }
                                }
                            }
                        }
                        if (sVGAVideoShapeEntity.getStyles() != null) {
                            sharedPaint.setStrokeMiter(styles.getMiterLimit() * matrixScale);
                        }
                        SVGAVideoShapeEntity.Styles styles8 = sVGAVideoShapeEntity.getStyles();
                        if (styles8 != null && (lineDash = styles8.getLineDash()) != null && lineDash.length == 3 && (lineDash[0] > f || lineDash[1] > f)) {
                            float[] fArr = new float[2];
                            float f2 = 1.0f;
                            if (lineDash[0] >= 1.0f) {
                                f2 = lineDash[0];
                            }
                            fArr[0] = f2 * matrixScale;
                            float f3 = 0.1f;
                            if (lineDash[1] >= 0.1f) {
                                f3 = lineDash[1];
                            }
                            fArr[1] = f3 * matrixScale;
                            sharedPaint.setPathEffect(new DashPathEffect(fArr, lineDash[2] * matrixScale));
                        }
                        if (sVGADrawerSprite.getFrameEntity().getMaskPath() != null) {
                            canvas.save();
                        }
                        SVGAPathEntity maskPath2 = sVGADrawerSprite.getFrameEntity().getMaskPath();
                        if (maskPath2 != null) {
                            Path sharedPath22 = this.sharedValues.sharedPath2();
                            maskPath2.buildPath(sharedPath22);
                            sharedPath22.transform(shareFrameMatrix);
                            canvas.clipPath(sharedPath22);
                        }
                        canvas.drawPath(sharedPath, sharedPaint);
                        if (sVGADrawerSprite.getFrameEntity().getMaskPath() != null) {
                            canvas.restore();
                        }
                    }
                }
            }
        }
    }

    private final float matrixScale(Matrix matrix) {
        matrix.getValues(this.matrixScaleTempValues);
        float[] fArr = this.matrixScaleTempValues;
        if (fArr[0] == 0.0f) {
            return 0.0f;
        }
        double d = fArr[0];
        double d2 = fArr[3];
        double d3 = fArr[1];
        double d4 = fArr[4];
        if (d * d4 == d2 * d3) {
            return 0.0f;
        }
        double sqrt = Math.sqrt((d * d) + (d2 * d2));
        double d5 = d / sqrt;
        double d6 = d2 / sqrt;
        double d7 = (d5 * d3) + (d6 * d4);
        double d8 = d3 - (d5 * d7);
        double d9 = d4 - (d7 * d6);
        double sqrt2 = Math.sqrt((d8 * d8) + (d9 * d9));
        if (d5 * (d9 / sqrt2) < d6 * (d8 / sqrt2)) {
            sqrt = -sqrt;
        }
        return Math.abs(getScaleInfo().getRatioX() ? (float) sqrt : (float) sqrt2);
    }

    private final void drawDynamic(SGVADrawer.SVGADrawerSprite sVGADrawerSprite, Canvas canvas, int i) {
        String imageKey = sVGADrawerSprite.getImageKey();
        if (imageKey != null) {
            Function2<Canvas, Integer, Boolean> function2 = this.dynamicItem.getDynamicDrawer$library_release().get(imageKey);
            if (function2 != null) {
                Matrix shareFrameMatrix = shareFrameMatrix(sVGADrawerSprite.getFrameEntity().getTransform());
                canvas.save();
                canvas.concat(shareFrameMatrix);
                function2.invoke(canvas, Integer.valueOf(i));
                canvas.restore();
            }
            Function4<Canvas, Integer, Integer, Integer, Boolean> function4 = this.dynamicItem.getDynamicDrawerSized$library_release().get(imageKey);
            if (function4 == null) {
                return;
            }
            Matrix shareFrameMatrix2 = shareFrameMatrix(sVGADrawerSprite.getFrameEntity().getTransform());
            canvas.save();
            canvas.concat(shareFrameMatrix2);
            function4.invoke(canvas, Integer.valueOf(i), Integer.valueOf((int) sVGADrawerSprite.getFrameEntity().getLayout().getWidth()), Integer.valueOf((int) sVGADrawerSprite.getFrameEntity().getLayout().getHeight()));
            canvas.restore();
        }
    }

    /* compiled from: SVGACanvasDrawer.kt */
    /* loaded from: classes3.dex */
    public static final class ShareValues {
        private Canvas shareMatteCanvas;
        private Bitmap sharedMatteBitmap;
        private final Paint sharedPaint = new Paint();
        private final Path sharedPath = new Path();
        private final Path sharedPath2 = new Path();
        private final Matrix sharedMatrix = new Matrix();
        private final Matrix sharedMatrix2 = new Matrix();
        private final Paint shareMattePaint = new Paint();

        public final Paint sharedPaint() {
            this.sharedPaint.reset();
            return this.sharedPaint;
        }

        public final Path sharedPath() {
            this.sharedPath.reset();
            return this.sharedPath;
        }

        public final Path sharedPath2() {
            this.sharedPath2.reset();
            return this.sharedPath2;
        }

        public final Matrix sharedMatrix() {
            this.sharedMatrix.reset();
            return this.sharedMatrix;
        }

        public final Matrix sharedMatrix2() {
            this.sharedMatrix2.reset();
            return this.sharedMatrix2;
        }

        public final Paint shareMattePaint() {
            this.shareMattePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
            return this.shareMattePaint;
        }

        public final Bitmap sharedMatteBitmap() {
            Bitmap bitmap = this.sharedMatteBitmap;
            if (bitmap != null) {
                return bitmap;
            }
            throw new TypeCastException("null cannot be cast to non-null type android.graphics.Bitmap");
        }

        public final Canvas shareMatteCanvas(int i, int i2) {
            if (this.shareMatteCanvas == null) {
                this.sharedMatteBitmap = Bitmap.createBitmap(i, i2, Bitmap.Config.ALPHA_8);
            }
            return new Canvas(this.sharedMatteBitmap);
        }
    }

    /* compiled from: SVGACanvasDrawer.kt */
    /* loaded from: classes3.dex */
    public static final class PathCache {
        private final HashMap<SVGAVideoShapeEntity, Path> cache = new HashMap<>();
        private int canvasHeight;
        private int canvasWidth;

        public final void onSizeChanged(Canvas canvas) {
            Intrinsics.checkParameterIsNotNull(canvas, "canvas");
            if (this.canvasWidth != canvas.getWidth() || this.canvasHeight != canvas.getHeight()) {
                this.cache.clear();
            }
            this.canvasWidth = canvas.getWidth();
            this.canvasHeight = canvas.getHeight();
        }

        public final Path buildPath(SVGAVideoShapeEntity shape) {
            Intrinsics.checkParameterIsNotNull(shape, "shape");
            if (!this.cache.containsKey(shape)) {
                Path path = new Path();
                path.set(shape.getShapePath());
                this.cache.put(shape, path);
            }
            Path path2 = this.cache.get(shape);
            if (path2 != null) {
                return path2;
            }
            Intrinsics.throwNpe();
            throw null;
        }
    }
}
