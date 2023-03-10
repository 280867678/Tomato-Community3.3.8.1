package com.github.ybq.android.spinkit;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.ChasingDots;
import com.github.ybq.android.spinkit.style.Circle;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.github.ybq.android.spinkit.style.FadingCircle;
import com.github.ybq.android.spinkit.style.FoldingCube;
import com.github.ybq.android.spinkit.style.MultiplePulse;
import com.github.ybq.android.spinkit.style.MultiplePulseRing;
import com.github.ybq.android.spinkit.style.Pulse;
import com.github.ybq.android.spinkit.style.PulseRing;
import com.github.ybq.android.spinkit.style.RotatingCircle;
import com.github.ybq.android.spinkit.style.RotatingPlane;
import com.github.ybq.android.spinkit.style.ThreeBounce;
import com.github.ybq.android.spinkit.style.WanderingCubes;
import com.github.ybq.android.spinkit.style.Wave;

/* loaded from: classes2.dex */
public class SpriteFactory {

    /* renamed from: com.github.ybq.android.spinkit.SpriteFactory$1 */
    /* loaded from: classes2.dex */
    static /* synthetic */ class C18551 {
        static final /* synthetic */ int[] $SwitchMap$com$github$ybq$android$spinkit$Style = new int[Style.values().length];

        static {
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.ROTATING_PLANE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.DOUBLE_BOUNCE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.WAVE.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.WANDERING_CUBES.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.PULSE.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.CHASING_DOTS.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.THREE_BOUNCE.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.CIRCLE.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.CUBE_GRID.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.FADING_CIRCLE.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.FOLDING_CUBE.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.ROTATING_CIRCLE.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.MULTIPLE_PULSE.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.PULSE_RING.ordinal()] = 14;
            } catch (NoSuchFieldError unused14) {
            }
            try {
                $SwitchMap$com$github$ybq$android$spinkit$Style[Style.MULTIPLE_PULSE_RING.ordinal()] = 15;
            } catch (NoSuchFieldError unused15) {
            }
        }
    }

    public static Sprite create(Style style) {
        switch (C18551.$SwitchMap$com$github$ybq$android$spinkit$Style[style.ordinal()]) {
            case 1:
                return new RotatingPlane();
            case 2:
                return new DoubleBounce();
            case 3:
                return new Wave();
            case 4:
                return new WanderingCubes();
            case 5:
                return new Pulse();
            case 6:
                return new ChasingDots();
            case 7:
                return new ThreeBounce();
            case 8:
                return new Circle();
            case 9:
                return new CubeGrid();
            case 10:
                return new FadingCircle();
            case 11:
                return new FoldingCube();
            case 12:
                return new RotatingCircle();
            case 13:
                return new MultiplePulse();
            case 14:
                return new PulseRing();
            case 15:
                return new MultiplePulseRing();
            default:
                return null;
        }
    }
}
