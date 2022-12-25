package com.airbnb.lottie.model.layer;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.LottieDrawable;
import com.airbnb.lottie.LottieProperty;
import com.airbnb.lottie.TextDelegate;
import com.airbnb.lottie.animation.content.ContentGroup;
import com.airbnb.lottie.animation.keyframe.BaseKeyframeAnimation;
import com.airbnb.lottie.animation.keyframe.TextKeyframeAnimation;
import com.airbnb.lottie.model.DocumentData;
import com.airbnb.lottie.model.Font;
import com.airbnb.lottie.model.FontCharacter;
import com.airbnb.lottie.model.animatable.AnimatableColorValue;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTextProperties;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.LottieValueCallback;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class TextLayer extends BaseLayer {
    @Nullable
    private BaseKeyframeAnimation<Integer, Integer> colorAnimation;
    private final LottieComposition composition;
    private final LottieDrawable lottieDrawable;
    @Nullable
    private BaseKeyframeAnimation<Integer, Integer> strokeColorAnimation;
    @Nullable
    private BaseKeyframeAnimation<Float, Float> strokeWidthAnimation;
    private final TextKeyframeAnimation textAnimation;
    @Nullable
    private BaseKeyframeAnimation<Float, Float> trackingAnimation;
    private final char[] tempCharArray = new char[1];
    private final RectF rectF = new RectF();
    private final Matrix matrix = new Matrix();
    private final Paint fillPaint = new Paint(this, 1) { // from class: com.airbnb.lottie.model.layer.TextLayer.1
        {
            setStyle(Paint.Style.FILL);
        }
    };
    private final Paint strokePaint = new Paint(this, 1) { // from class: com.airbnb.lottie.model.layer.TextLayer.2
        {
            setStyle(Paint.Style.STROKE);
        }
    };
    private final Map<FontCharacter, List<ContentGroup>> contentsForCharacter = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    public TextLayer(LottieDrawable lottieDrawable, Layer layer) {
        super(lottieDrawable, layer);
        AnimatableFloatValue animatableFloatValue;
        AnimatableFloatValue animatableFloatValue2;
        AnimatableColorValue animatableColorValue;
        AnimatableColorValue animatableColorValue2;
        this.lottieDrawable = lottieDrawable;
        this.composition = layer.getComposition();
        this.textAnimation = layer.getText().mo5808createAnimation();
        this.textAnimation.addUpdateListener(this);
        addAnimation(this.textAnimation);
        AnimatableTextProperties textProperties = layer.getTextProperties();
        if (textProperties != null && (animatableColorValue2 = textProperties.color) != null) {
            this.colorAnimation = animatableColorValue2.mo5808createAnimation();
            this.colorAnimation.addUpdateListener(this);
            addAnimation(this.colorAnimation);
        }
        if (textProperties != null && (animatableColorValue = textProperties.stroke) != null) {
            this.strokeColorAnimation = animatableColorValue.mo5808createAnimation();
            this.strokeColorAnimation.addUpdateListener(this);
            addAnimation(this.strokeColorAnimation);
        }
        if (textProperties != null && (animatableFloatValue2 = textProperties.strokeWidth) != null) {
            this.strokeWidthAnimation = animatableFloatValue2.mo5808createAnimation();
            this.strokeWidthAnimation.addUpdateListener(this);
            addAnimation(this.strokeWidthAnimation);
        }
        if (textProperties == null || (animatableFloatValue = textProperties.tracking) == null) {
            return;
        }
        this.trackingAnimation = animatableFloatValue.mo5808createAnimation();
        this.trackingAnimation.addUpdateListener(this);
        addAnimation(this.trackingAnimation);
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer
    void drawLayer(Canvas canvas, Matrix matrix, int i) {
        canvas.save();
        if (!this.lottieDrawable.useTextGlyphs()) {
            canvas.setMatrix(matrix);
        }
        DocumentData mo5805getValue = this.textAnimation.mo5805getValue();
        Font font = this.composition.getFonts().get(mo5805getValue.fontName);
        if (font == null) {
            canvas.restore();
            return;
        }
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation = this.colorAnimation;
        if (baseKeyframeAnimation != null) {
            this.fillPaint.setColor(baseKeyframeAnimation.mo5805getValue().intValue());
        } else {
            this.fillPaint.setColor(mo5805getValue.color);
        }
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation2 = this.strokeColorAnimation;
        if (baseKeyframeAnimation2 != null) {
            this.strokePaint.setColor(baseKeyframeAnimation2.mo5805getValue().intValue());
        } else {
            this.strokePaint.setColor(mo5805getValue.strokeColor);
        }
        int intValue = (this.transform.getOpacity().mo5805getValue().intValue() * 255) / 100;
        this.fillPaint.setAlpha(intValue);
        this.strokePaint.setAlpha(intValue);
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation3 = this.strokeWidthAnimation;
        if (baseKeyframeAnimation3 != null) {
            this.strokePaint.setStrokeWidth(baseKeyframeAnimation3.mo5805getValue().floatValue());
        } else {
            this.strokePaint.setStrokeWidth(mo5805getValue.strokeWidth * Utils.dpScale() * Utils.getScale(matrix));
        }
        if (this.lottieDrawable.useTextGlyphs()) {
            drawTextGlyphs(mo5805getValue, matrix, font, canvas);
        } else {
            drawTextWithFont(mo5805getValue, font, matrix, canvas);
        }
        canvas.restore();
    }

    private void drawTextGlyphs(DocumentData documentData, Matrix matrix, Font font, Canvas canvas) {
        float f = ((float) documentData.size) / 100.0f;
        float scale = Utils.getScale(matrix);
        String str = documentData.text;
        for (int i = 0; i < str.length(); i++) {
            FontCharacter fontCharacter = this.composition.getCharacters().get(FontCharacter.hashFor(str.charAt(i), font.getFamily(), font.getStyle()));
            if (fontCharacter != null) {
                drawCharacterAsGlyph(fontCharacter, matrix, f, documentData, canvas);
                float width = ((float) fontCharacter.getWidth()) * f * Utils.dpScale() * scale;
                float f2 = documentData.tracking / 10.0f;
                BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = this.trackingAnimation;
                if (baseKeyframeAnimation != null) {
                    f2 += baseKeyframeAnimation.mo5805getValue().floatValue();
                }
                canvas.translate(width + (f2 * scale), 0.0f);
            }
        }
    }

    private void drawTextWithFont(DocumentData documentData, Font font, Matrix matrix, Canvas canvas) {
        float scale = Utils.getScale(matrix);
        Typeface typeface = this.lottieDrawable.getTypeface(font.getFamily(), font.getStyle());
        if (typeface == null) {
            return;
        }
        String str = documentData.text;
        TextDelegate textDelegate = this.lottieDrawable.getTextDelegate();
        if (textDelegate != null) {
            textDelegate.getTextInternal(str);
            throw null;
        }
        this.fillPaint.setTypeface(typeface);
        this.fillPaint.setTextSize((float) (documentData.size * Utils.dpScale()));
        this.strokePaint.setTypeface(this.fillPaint.getTypeface());
        this.strokePaint.setTextSize(this.fillPaint.getTextSize());
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            drawCharacterFromFont(charAt, documentData, canvas);
            char[] cArr = this.tempCharArray;
            cArr[0] = charAt;
            float measureText = this.fillPaint.measureText(cArr, 0, 1);
            float f = documentData.tracking / 10.0f;
            BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation = this.trackingAnimation;
            if (baseKeyframeAnimation != null) {
                f += baseKeyframeAnimation.mo5805getValue().floatValue();
            }
            canvas.translate(measureText + (f * scale), 0.0f);
        }
    }

    private void drawCharacterAsGlyph(FontCharacter fontCharacter, Matrix matrix, float f, DocumentData documentData, Canvas canvas) {
        List<ContentGroup> contentsForCharacter = getContentsForCharacter(fontCharacter);
        for (int i = 0; i < contentsForCharacter.size(); i++) {
            Path path = contentsForCharacter.get(i).getPath();
            path.computeBounds(this.rectF, false);
            this.matrix.set(matrix);
            this.matrix.preTranslate(0.0f, ((float) (-documentData.baselineShift)) * Utils.dpScale());
            this.matrix.preScale(f, f);
            path.transform(this.matrix);
            if (documentData.strokeOverFill) {
                drawGlyph(path, this.fillPaint, canvas);
                drawGlyph(path, this.strokePaint, canvas);
            } else {
                drawGlyph(path, this.strokePaint, canvas);
                drawGlyph(path, this.fillPaint, canvas);
            }
        }
    }

    private void drawGlyph(Path path, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawPath(path, paint);
    }

    private void drawCharacterFromFont(char c, DocumentData documentData, Canvas canvas) {
        char[] cArr = this.tempCharArray;
        cArr[0] = c;
        if (documentData.strokeOverFill) {
            drawCharacter(cArr, this.fillPaint, canvas);
            drawCharacter(this.tempCharArray, this.strokePaint, canvas);
            return;
        }
        drawCharacter(cArr, this.strokePaint, canvas);
        drawCharacter(this.tempCharArray, this.fillPaint, canvas);
    }

    private void drawCharacter(char[] cArr, Paint paint, Canvas canvas) {
        if (paint.getColor() == 0) {
            return;
        }
        if (paint.getStyle() == Paint.Style.STROKE && paint.getStrokeWidth() == 0.0f) {
            return;
        }
        canvas.drawText(cArr, 0, 1, 0.0f, 0.0f, paint);
    }

    private List<ContentGroup> getContentsForCharacter(FontCharacter fontCharacter) {
        if (this.contentsForCharacter.containsKey(fontCharacter)) {
            return this.contentsForCharacter.get(fontCharacter);
        }
        List<ShapeGroup> shapes = fontCharacter.getShapes();
        int size = shapes.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i = 0; i < size; i++) {
            arrayList.add(new ContentGroup(this.lottieDrawable, this, shapes.get(i)));
        }
        this.contentsForCharacter.put(fontCharacter, arrayList);
        return arrayList;
    }

    @Override // com.airbnb.lottie.model.layer.BaseLayer, com.airbnb.lottie.model.KeyPathElement
    public <T> void addValueCallback(T t, @Nullable LottieValueCallback<T> lottieValueCallback) {
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation;
        BaseKeyframeAnimation<Float, Float> baseKeyframeAnimation2;
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation3;
        BaseKeyframeAnimation<Integer, Integer> baseKeyframeAnimation4;
        super.addValueCallback(t, lottieValueCallback);
        if (t == LottieProperty.COLOR && (baseKeyframeAnimation4 = this.colorAnimation) != null) {
            baseKeyframeAnimation4.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.STROKE_COLOR && (baseKeyframeAnimation3 = this.strokeColorAnimation) != null) {
            baseKeyframeAnimation3.setValueCallback(lottieValueCallback);
        } else if (t == LottieProperty.STROKE_WIDTH && (baseKeyframeAnimation2 = this.strokeWidthAnimation) != null) {
            baseKeyframeAnimation2.setValueCallback(lottieValueCallback);
        } else if (t != LottieProperty.TEXT_TRACKING || (baseKeyframeAnimation = this.trackingAnimation) == null) {
        } else {
            baseKeyframeAnimation.setValueCallback(lottieValueCallback);
        }
    }
}
