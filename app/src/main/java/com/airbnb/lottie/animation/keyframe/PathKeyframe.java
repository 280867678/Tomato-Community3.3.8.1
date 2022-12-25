package com.airbnb.lottie.animation.keyframe;

import android.graphics.Path;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.utils.Utils;
import com.airbnb.lottie.value.Keyframe;

/* loaded from: classes2.dex */
public class PathKeyframe extends Keyframe<PointF> {
    @Nullable
    private Path path;

    public PathKeyframe(LottieComposition lottieComposition, Keyframe<PointF> keyframe) {
        super(lottieComposition, keyframe.startValue, keyframe.endValue, keyframe.interpolator, keyframe.startFrame, keyframe.endFrame);
        T t;
        T t2 = this.endValue;
        boolean z = (t2 == 0 || (t = this.startValue) == 0 || !((PointF) t).equals(((PointF) t2).x, ((PointF) t2).y)) ? false : true;
        T t3 = this.endValue;
        if (t3 == 0 || z) {
            return;
        }
        this.path = Utils.createPath((PointF) this.startValue, (PointF) t3, keyframe.pathCp1, keyframe.pathCp2);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    @Nullable
    public Path getPath() {
        return this.path;
    }
}
