package android.support.design.animation;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimatorRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleableRes;
import android.support.p002v4.util.SimpleArrayMap;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class MotionSpec {
    private static final String TAG = "MotionSpec";
    private final SimpleArrayMap<String, MotionTiming> timings = new SimpleArrayMap<>();

    public boolean hasTiming(String str) {
        return this.timings.get(str) != null;
    }

    public MotionTiming getTiming(String str) {
        if (!hasTiming(str)) {
            throw new IllegalArgumentException();
        }
        return this.timings.get(str);
    }

    public void setTiming(String str, @Nullable MotionTiming motionTiming) {
        this.timings.put(str, motionTiming);
    }

    public long getTotalDuration() {
        int size = this.timings.size();
        long j = 0;
        for (int i = 0; i < size; i++) {
            MotionTiming valueAt = this.timings.valueAt(i);
            j = Math.max(j, valueAt.getDelay() + valueAt.getDuration());
        }
        return j;
    }

    @Nullable
    public static MotionSpec createFromAttribute(Context context, TypedArray typedArray, @StyleableRes int i) {
        int resourceId;
        if (!typedArray.hasValue(i) || (resourceId = typedArray.getResourceId(i, 0)) == 0) {
            return null;
        }
        return createFromResource(context, resourceId);
    }

    @Nullable
    public static MotionSpec createFromResource(Context context, @AnimatorRes int i) {
        try {
            Animator loadAnimator = AnimatorInflater.loadAnimator(context, i);
            if (loadAnimator instanceof AnimatorSet) {
                return createSpecFromAnimators(((AnimatorSet) loadAnimator).getChildAnimations());
            }
            if (loadAnimator == null) {
                return null;
            }
            ArrayList arrayList = new ArrayList();
            arrayList.add(loadAnimator);
            return createSpecFromAnimators(arrayList);
        } catch (Exception e) {
            Log.w(TAG, "Can't load animation resource ID #0x" + Integer.toHexString(i), e);
            return null;
        }
    }

    private static MotionSpec createSpecFromAnimators(List<Animator> list) {
        MotionSpec motionSpec = new MotionSpec();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            addTimingFromAnimator(motionSpec, list.get(i));
        }
        return motionSpec;
    }

    private static void addTimingFromAnimator(MotionSpec motionSpec, Animator animator) {
        if (animator instanceof ObjectAnimator) {
            ObjectAnimator objectAnimator = (ObjectAnimator) animator;
            motionSpec.setTiming(objectAnimator.getPropertyName(), MotionTiming.createFromAnimator(objectAnimator));
            return;
        }
        throw new IllegalArgumentException("Animator must be an ObjectAnimator: " + animator);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && MotionSpec.class == obj.getClass()) {
            return this.timings.equals(((MotionSpec) obj).timings);
        }
        return false;
    }

    public int hashCode() {
        return this.timings.hashCode();
    }

    public String toString() {
        return '\n' + MotionSpec.class.getName() + '{' + Integer.toHexString(System.identityHashCode(this)) + " timings: " + this.timings + "}\n";
    }
}
