package android.support.constraint.solver.widgets;

/* loaded from: classes5.dex */
public class Rectangle {
    public int height;
    public int width;

    /* renamed from: x */
    public int f11x;

    /* renamed from: y */
    public int f12y;

    public void setBounds(int i, int i2, int i3, int i4) {
        this.f11x = i;
        this.f12y = i2;
        this.width = i3;
        this.height = i4;
    }

    void grow(int i, int i2) {
        this.f11x -= i;
        this.f12y -= i2;
        this.width += i * 2;
        this.height += i2 * 2;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public boolean intersects(Rectangle rectangle) {
        int i;
        int i2;
        int i3 = this.f11x;
        int i4 = rectangle.f11x;
        return i3 >= i4 && i3 < i4 + rectangle.width && (i = this.f12y) >= (i2 = rectangle.f12y) && i < i2 + rectangle.height;
    }

    public boolean contains(int i, int i2) {
        int i3;
        int i4 = this.f11x;
        return i >= i4 && i < i4 + this.width && i2 >= (i3 = this.f12y) && i2 < i3 + this.height;
    }

    public int getCenterX() {
        return (this.f11x + this.width) / 2;
    }

    public int getCenterY() {
        return (this.f12y + this.height) / 2;
    }
}
