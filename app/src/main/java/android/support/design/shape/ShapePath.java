package android.support.design.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.design.internal.Experimental;
import java.util.ArrayList;
import java.util.List;

@Experimental("The shapes API is currently experimental and subject to change")
/* loaded from: classes.dex */
public class ShapePath {
    public float endX;
    public float endY;
    private final List<PathOperation> operations = new ArrayList();
    public float startX;
    public float startY;

    /* loaded from: classes.dex */
    public static abstract class PathOperation {
        protected final Matrix matrix = new Matrix();

        public abstract void applyToPath(Matrix matrix, Path path);
    }

    public ShapePath() {
        reset(0.0f, 0.0f);
    }

    public ShapePath(float f, float f2) {
        reset(f, f2);
    }

    public void reset(float f, float f2) {
        this.startX = f;
        this.startY = f2;
        this.endX = f;
        this.endY = f2;
        this.operations.clear();
    }

    public void lineTo(float f, float f2) {
        PathLineOperation pathLineOperation = new PathLineOperation();
        pathLineOperation.f20x = f;
        pathLineOperation.f21y = f2;
        this.operations.add(pathLineOperation);
        this.endX = f;
        this.endY = f2;
    }

    public void quadToPoint(float f, float f2, float f3, float f4) {
        PathQuadOperation pathQuadOperation = new PathQuadOperation();
        pathQuadOperation.controlX = f;
        pathQuadOperation.controlY = f2;
        pathQuadOperation.endX = f3;
        pathQuadOperation.endY = f4;
        this.operations.add(pathQuadOperation);
        this.endX = f3;
        this.endY = f4;
    }

    public void addArc(float f, float f2, float f3, float f4, float f5, float f6) {
        PathArcOperation pathArcOperation = new PathArcOperation(f, f2, f3, f4);
        pathArcOperation.startAngle = f5;
        pathArcOperation.sweepAngle = f6;
        this.operations.add(pathArcOperation);
        double d = f5 + f6;
        this.endX = ((f + f3) * 0.5f) + (((f3 - f) / 2.0f) * ((float) Math.cos(Math.toRadians(d))));
        this.endY = ((f2 + f4) * 0.5f) + (((f4 - f2) / 2.0f) * ((float) Math.sin(Math.toRadians(d))));
    }

    public void applyToPath(Matrix matrix, Path path) {
        int size = this.operations.size();
        for (int i = 0; i < size; i++) {
            this.operations.get(i).applyToPath(matrix, path);
        }
    }

    /* loaded from: classes2.dex */
    public static class PathLineOperation extends PathOperation {

        /* renamed from: x */
        private float f20x;

        /* renamed from: y */
        private float f21y;

        @Override // android.support.design.shape.ShapePath.PathOperation
        public void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.lineTo(this.f20x, this.f21y);
            path.transform(matrix);
        }
    }

    /* loaded from: classes2.dex */
    public static class PathQuadOperation extends PathOperation {
        public float controlX;
        public float controlY;
        public float endX;
        public float endY;

        @Override // android.support.design.shape.ShapePath.PathOperation
        public void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            path.quadTo(this.controlX, this.controlY, this.endX, this.endY);
            path.transform(matrix);
        }
    }

    /* loaded from: classes2.dex */
    public static class PathArcOperation extends PathOperation {
        private static final RectF rectF = new RectF();
        public float bottom;
        public float left;
        public float right;
        public float startAngle;
        public float sweepAngle;

        /* renamed from: top  reason: collision with root package name */
        public float f6082top;

        public PathArcOperation(float f, float f2, float f3, float f4) {
            this.left = f;
            this.f6082top = f2;
            this.right = f3;
            this.bottom = f4;
        }

        @Override // android.support.design.shape.ShapePath.PathOperation
        public void applyToPath(Matrix matrix, Path path) {
            Matrix matrix2 = this.matrix;
            matrix.invert(matrix2);
            path.transform(matrix2);
            rectF.set(this.left, this.f6082top, this.right, this.bottom);
            path.arcTo(rectF, this.startAngle, this.sweepAngle, false);
            path.transform(matrix);
        }
    }
}
