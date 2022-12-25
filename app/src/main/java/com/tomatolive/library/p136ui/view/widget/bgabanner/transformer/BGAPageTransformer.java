package com.tomatolive.library.p136ui.view.widget.bgabanner.transformer;

import android.support.p002v4.view.ViewPager;
import android.view.View;

/* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.transformer.BGAPageTransformer */
/* loaded from: classes4.dex */
public abstract class BGAPageTransformer implements ViewPager.PageTransformer {
    public abstract void handleInvisiblePage(View view, float f);

    public abstract void handleLeftPage(View view, float f);

    public abstract void handleRightPage(View view, float f);

    @Override // android.support.p002v4.view.ViewPager.PageTransformer
    public void transformPage(View view, float f) {
        if (f < -1.0f) {
            handleInvisiblePage(view, f);
        } else if (f <= 0.0f) {
            handleLeftPage(view, f);
        } else if (f <= 1.0f) {
            handleRightPage(view, f);
        } else {
            handleInvisiblePage(view, f);
        }
    }

    /* renamed from: com.tomatolive.library.ui.view.widget.bgabanner.transformer.BGAPageTransformer$1 */
    /* loaded from: classes4.dex */
    static /* synthetic */ class C48971 {

        /* renamed from: $SwitchMap$com$tomatolive$library$ui$view$widget$bgabanner$transformer$TransitionEffect */
        static final /* synthetic */ int[] f5869x37c169ca = new int[TransitionEffect.values().length];

        static {
            try {
                f5869x37c169ca[TransitionEffect.Default.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Alpha.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Rotate.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Cube.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Flip.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Accordion.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                f5869x37c169ca[TransitionEffect.ZoomFade.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Fade.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                f5869x37c169ca[TransitionEffect.ZoomCenter.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                f5869x37c169ca[TransitionEffect.ZoomStack.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Stack.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Depth.ordinal()] = 12;
            } catch (NoSuchFieldError unused12) {
            }
            try {
                f5869x37c169ca[TransitionEffect.Zoom.ordinal()] = 13;
            } catch (NoSuchFieldError unused13) {
            }
        }
    }

    public static BGAPageTransformer getPageTransformer(TransitionEffect transitionEffect) {
        switch (C48971.f5869x37c169ca[transitionEffect.ordinal()]) {
            case 1:
                return new DefaultPageTransformer();
            case 2:
                return new AlphaPageTransformer();
            case 3:
                return new RotatePageTransformer();
            case 4:
                return new CubePageTransformer();
            case 5:
                return new FlipPageTransformer();
            case 6:
                return new AccordionPageTransformer();
            case 7:
                return new ZoomFadePageTransformer();
            case 8:
                return new FadePageTransformer();
            case 9:
                return new ZoomCenterPageTransformer();
            case 10:
                return new ZoomStackPageTransformer();
            case 11:
                return new StackPageTransformer();
            case 12:
                return new DepthPageTransformer();
            case 13:
                return new ZoomPageTransformer();
            default:
                return new DefaultPageTransformer();
        }
    }
}
