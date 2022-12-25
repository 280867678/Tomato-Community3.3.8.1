package com.gen.p059mh.webapp_extensions.utils;

import android.view.animation.TranslateAnimation;

/* renamed from: com.gen.mh.webapp_extensions.utils.AnimationUtil */
/* loaded from: classes2.dex */
public class AnimationUtil {
    public static TranslateAnimation moveLeft2Self(int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 1.0f, 1, 0.0f, 1, 0.0f, 1, 0.0f);
        translateAnimation.setDuration(i);
        return translateAnimation;
    }

    public static TranslateAnimation moveSelf2Left(int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 1.0f, 1, 0.0f, 1, 0.0f);
        translateAnimation.setDuration(i);
        return translateAnimation;
    }

    public static TranslateAnimation moveTop2Self(int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, -1.0f, 1, 0.0f);
        translateAnimation.setDuration(i);
        return translateAnimation;
    }

    public static TranslateAnimation moveSelf2Top(int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, -1.0f);
        translateAnimation.setDuration(i);
        return translateAnimation;
    }

    public static TranslateAnimation moveSelf2Bottom(int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.0f, 1, 1.0f);
        translateAnimation.setDuration(i);
        return translateAnimation;
    }

    public static TranslateAnimation moveBottom2Self(int i) {
        TranslateAnimation translateAnimation = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 1.0f, 1, 0.0f);
        translateAnimation.setDuration(i);
        return translateAnimation;
    }
}
