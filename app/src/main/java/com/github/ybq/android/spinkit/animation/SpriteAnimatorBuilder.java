package com.github.ybq.android.spinkit.animation;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.util.Log;
import android.util.Property;
import android.view.animation.Interpolator;
import com.github.ybq.android.spinkit.animation.interpolator.KeyFrameInterpolator;
import com.github.ybq.android.spinkit.sprite.Sprite;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/* loaded from: classes2.dex */
public class SpriteAnimatorBuilder {
    private Interpolator interpolator;
    private Sprite sprite;
    private int repeatCount = -1;
    private long duration = 2000;
    private int startFrame = 0;
    private Map<String, FrameData> fds = new HashMap();

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class FrameData<T> {
        float[] fractions;
        Property property;
        T[] values;

        public FrameData(SpriteAnimatorBuilder spriteAnimatorBuilder, float[] fArr, Property property, T[] tArr) {
            this.fractions = fArr;
            this.property = property;
            this.values = tArr;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class IntFrameData extends FrameData<Integer> {
        public IntFrameData(SpriteAnimatorBuilder spriteAnimatorBuilder, float[] fArr, Property property, Integer[] numArr) {
            super(spriteAnimatorBuilder, fArr, property, numArr);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public class FloatFrameData extends FrameData<Float> {
        public FloatFrameData(SpriteAnimatorBuilder spriteAnimatorBuilder, float[] fArr, Property property, Float[] fArr2) {
            super(spriteAnimatorBuilder, fArr, property, fArr2);
        }
    }

    public SpriteAnimatorBuilder(Sprite sprite) {
        this.sprite = sprite;
    }

    public SpriteAnimatorBuilder scale(float[] fArr, Float... fArr2) {
        holder(fArr, Sprite.SCALE, fArr2);
        return this;
    }

    public SpriteAnimatorBuilder alpha(float[] fArr, Integer... numArr) {
        holder(fArr, Sprite.ALPHA, numArr);
        return this;
    }

    public SpriteAnimatorBuilder scaleY(float[] fArr, Float... fArr2) {
        holder(fArr, Sprite.SCALE_Y, fArr2);
        return this;
    }

    public SpriteAnimatorBuilder rotateX(float[] fArr, Integer... numArr) {
        holder(fArr, Sprite.ROTATE_X, numArr);
        return this;
    }

    public SpriteAnimatorBuilder rotateY(float[] fArr, Integer... numArr) {
        holder(fArr, Sprite.ROTATE_Y, numArr);
        return this;
    }

    public SpriteAnimatorBuilder rotate(float[] fArr, Integer... numArr) {
        holder(fArr, Sprite.ROTATE, numArr);
        return this;
    }

    public SpriteAnimatorBuilder translateXPercentage(float[] fArr, Float... fArr2) {
        holder(fArr, Sprite.TRANSLATE_X_PERCENTAGE, fArr2);
        return this;
    }

    public SpriteAnimatorBuilder translateYPercentage(float[] fArr, Float... fArr2) {
        holder(fArr, Sprite.TRANSLATE_Y_PERCENTAGE, fArr2);
        return this;
    }

    private void holder(float[] fArr, Property property, Float[] fArr2) {
        ensurePair(fArr.length, fArr2.length);
        this.fds.put(property.getName(), new FloatFrameData(this, fArr, property, fArr2));
    }

    private void holder(float[] fArr, Property property, Integer[] numArr) {
        ensurePair(fArr.length, numArr.length);
        this.fds.put(property.getName(), new IntFrameData(this, fArr, property, numArr));
    }

    private void ensurePair(int i, int i2) {
        if (i == i2) {
            return;
        }
        throw new IllegalStateException(String.format(Locale.getDefault(), "The fractions.length must equal values.length, fraction.length[%d], values.length[%d]", Integer.valueOf(i), Integer.valueOf(i2)));
    }

    public SpriteAnimatorBuilder interpolator(Interpolator interpolator) {
        this.interpolator = interpolator;
        return this;
    }

    public SpriteAnimatorBuilder easeInOut(float... fArr) {
        interpolator(KeyFrameInterpolator.easeInOut(fArr));
        return this;
    }

    public SpriteAnimatorBuilder duration(long j) {
        this.duration = j;
        return this;
    }

    public SpriteAnimatorBuilder startFrame(int i) {
        if (i < 0) {
            Log.w("SpriteAnimatorBuilder", "startFrame should always be non-negative");
            i = 0;
        }
        this.startFrame = i;
        return this;
    }

    public ObjectAnimator build() {
        PropertyValuesHolder[] propertyValuesHolderArr = new PropertyValuesHolder[this.fds.size()];
        int i = 0;
        for (Map.Entry<String, FrameData> entry : this.fds.entrySet()) {
            FrameData value = entry.getValue();
            float[] fArr = value.fractions;
            Keyframe[] keyframeArr = new Keyframe[fArr.length];
            int i2 = this.startFrame;
            float f = fArr[i2];
            while (true) {
                int i3 = this.startFrame;
                T[] tArr = value.values;
                if (i2 < tArr.length + i3) {
                    int i4 = i2 - i3;
                    int length = i2 % tArr.length;
                    float f2 = fArr[length] - f;
                    if (f2 < 0.0f) {
                        f2 += fArr[fArr.length - 1];
                    }
                    if (value instanceof IntFrameData) {
                        keyframeArr[i4] = Keyframe.ofInt(f2, ((Integer) value.values[length]).intValue());
                    } else if (value instanceof FloatFrameData) {
                        keyframeArr[i4] = Keyframe.ofFloat(f2, ((Float) value.values[length]).floatValue());
                    } else {
                        keyframeArr[i4] = Keyframe.ofObject(f2, value.values[length]);
                    }
                    i2++;
                }
            }
            propertyValuesHolderArr[i] = PropertyValuesHolder.ofKeyframe(value.property, keyframeArr);
            i++;
        }
        ObjectAnimator ofPropertyValuesHolder = ObjectAnimator.ofPropertyValuesHolder(this.sprite, propertyValuesHolderArr);
        ofPropertyValuesHolder.setDuration(this.duration);
        ofPropertyValuesHolder.setRepeatCount(this.repeatCount);
        ofPropertyValuesHolder.setInterpolator(this.interpolator);
        return ofPropertyValuesHolder;
    }
}
