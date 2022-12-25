package com.tomatolive.library.p136ui.view.widget.heard.animation;

import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.view.View;
import android.view.ViewGroup;
import com.tomatolive.library.R$dimen;
import com.tomatolive.library.R$styleable;
import com.tomatolive.library.utils.SystemUtils;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

/* renamed from: com.tomatolive.library.ui.view.widget.heard.animation.RxAbstractPathAnimator */
/* loaded from: classes4.dex */
public abstract class RxAbstractPathAnimator {
    protected final Config mConfig;
    private final Random mRandom = new Random();

    public abstract void start(boolean z, View view, ViewGroup viewGroup);

    public RxAbstractPathAnimator(Config config) {
        this.mConfig = config;
    }

    public float randomRotation() {
        return (this.mRandom.nextFloat() * 28.6f) - 14.3f;
    }

    public Path createPath(AtomicInteger atomicInteger, View view, int i) {
        int i2;
        Random random = this.mRandom;
        int nextInt = random.nextInt(this.mConfig.xRand);
        int nextInt2 = random.nextInt(this.mConfig.xRand);
        int i3 = this.mConfig.initY;
        Config config = this.mConfig;
        int intValue = (atomicInteger.intValue() * 15) + (config.animLength * i) + random.nextInt(config.animLengthRand);
        Config config2 = this.mConfig;
        int i4 = intValue / config2.bezierFactor;
        int i5 = config2.xPointFactor;
        int i6 = nextInt + i5;
        int i7 = i5 + nextInt2;
        int i8 = i3 - intValue;
        Path path = new Path();
        Config config3 = this.mConfig;
        path.moveTo(config3.initX, config3.initY);
        float f = i6;
        float f2 = i3 - (intValue / 2);
        path.cubicTo(this.mConfig.initX, i3 - i4, f, i2 + i4, f, f2);
        path.moveTo(f, f2);
        float f3 = i7;
        path.cubicTo(f, i2 - i4, f3, i4 + i8, f3, i8);
        return path;
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.heard.animation.RxAbstractPathAnimator$Config */
    /* loaded from: classes4.dex */
    public static class Config {
        public int animDuration;
        public int animLength;
        public int animLengthRand;
        public int bezierFactor;
        public int heartHeight;
        public int heartWidth;
        public int initX;
        public int initY;
        public int xPointFactor;
        public int xRand;

        public static Config defaultConfig(int i, int i2) {
            Config config = new Config();
            config.initX = i;
            config.initY = i2;
            config.xRand = (int) SystemUtils.dp2px(40.0f);
            config.animLength = (int) SystemUtils.dp2px(100.0f);
            config.animLengthRand = (int) SystemUtils.dp2px(150.0f);
            config.bezierFactor = 6;
            config.xPointFactor = (int) SystemUtils.dp2px(30.0f);
            config.heartWidth = (int) SystemUtils.dp2px(30.0f);
            config.heartHeight = (int) SystemUtils.dp2px(30.0f);
            config.animDuration = 3000;
            return config;
        }

        public static Config fromTypeArray(TypedArray typedArray) {
            Config config = new Config();
            Resources resources = typedArray.getResources();
            config.initX = (int) typedArray.getDimension(R$styleable.RxHeartLayout_initX, resources.getDimensionPixelOffset(R$dimen.heart_anim_init_x));
            config.initY = (int) typedArray.getDimension(R$styleable.RxHeartLayout_initY, resources.getDimensionPixelOffset(R$dimen.heart_anim_init_y));
            config.xRand = (int) typedArray.getDimension(R$styleable.RxHeartLayout_xRand, resources.getDimensionPixelOffset(R$dimen.heart_anim_bezier_x_rand));
            config.animLength = (int) typedArray.getDimension(R$styleable.RxHeartLayout_animLength, resources.getDimensionPixelOffset(R$dimen.heart_anim_length));
            config.animLengthRand = (int) typedArray.getDimension(R$styleable.RxHeartLayout_animLengthRand, resources.getDimensionPixelOffset(R$dimen.heart_anim_length_rand));
            config.bezierFactor = typedArray.getInteger(R$styleable.RxHeartLayout_bezierFactor, 6);
            config.xPointFactor = (int) typedArray.getDimension(R$styleable.RxHeartLayout_xPointFactor, resources.getDimensionPixelOffset(R$dimen.heart_anim_x_point_factor));
            config.heartWidth = (int) typedArray.getDimension(R$styleable.RxHeartLayout_heart_width, resources.getDimensionPixelOffset(R$dimen.heart_size_width));
            config.heartHeight = (int) typedArray.getDimension(R$styleable.RxHeartLayout_heart_height, resources.getDimensionPixelOffset(R$dimen.heart_size_height));
            config.animDuration = typedArray.getInteger(R$styleable.RxHeartLayout_anim_duration, 3000);
            return config;
        }
    }
}
