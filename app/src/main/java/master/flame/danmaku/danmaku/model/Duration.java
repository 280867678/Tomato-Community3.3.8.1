package master.flame.danmaku.danmaku.model;

/* loaded from: classes4.dex */
public class Duration implements Cloneable {
    private float factor = 1.0f;
    private long mInitialDuration;
    public long value;

    public Duration(long j) {
        this.mInitialDuration = j;
        this.value = j;
    }

    public void setValue(long j) {
        this.mInitialDuration = j;
        this.value = ((float) this.mInitialDuration) * this.factor;
    }

    public void setFactor(float f) {
        if (this.factor != f) {
            this.factor = f;
            this.value = ((float) this.mInitialDuration) * f;
        }
    }
}
