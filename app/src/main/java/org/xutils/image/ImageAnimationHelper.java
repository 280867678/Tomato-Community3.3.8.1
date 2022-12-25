package org.xutils.image;

import android.graphics.drawable.Drawable;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import java.lang.reflect.Method;
import org.xutils.common.util.LogUtil;

/* loaded from: classes4.dex */
public final class ImageAnimationHelper {
    private static final Method cloneMethod;

    static {
        Method method;
        try {
            method = Animation.class.getDeclaredMethod("clone", new Class[0]);
            method.setAccessible(true);
        } catch (Throwable th) {
            LogUtil.m37w(th.getMessage(), th);
            method = null;
        }
        cloneMethod = method;
    }

    private ImageAnimationHelper() {
    }

    public static void fadeInDisplay(ImageView imageView, Drawable drawable) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.0f, 1.0f);
        alphaAnimation.setDuration(300L);
        alphaAnimation.setInterpolator(new DecelerateInterpolator());
        imageView.setImageDrawable(drawable);
        imageView.startAnimation(alphaAnimation);
    }

    public static void animationDisplay(ImageView imageView, Drawable drawable, Animation animation) {
        imageView.setImageDrawable(drawable);
        Method method = cloneMethod;
        if (method != null && animation != null) {
            try {
                imageView.startAnimation((Animation) method.invoke(animation, new Object[0]));
                return;
            } catch (Throwable unused) {
                imageView.startAnimation(animation);
                return;
            }
        }
        imageView.startAnimation(animation);
    }
}
