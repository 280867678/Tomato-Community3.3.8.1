package com.googlecode.mp4parser.util;

import com.coremedia.iso.IsoTypeReader;
import com.coremedia.iso.IsoTypeWriter;
import java.nio.ByteBuffer;

/* loaded from: classes3.dex */
public class Matrix {

    /* renamed from: a */
    double f1355a;

    /* renamed from: b */
    double f1356b;

    /* renamed from: c */
    double f1357c;

    /* renamed from: d */
    double f1358d;

    /* renamed from: tx */
    double f1359tx;

    /* renamed from: ty */
    double f1360ty;

    /* renamed from: u */
    double f1361u;

    /* renamed from: v */
    double f1362v;

    /* renamed from: w */
    double f1363w;
    public static final Matrix ROTATE_0 = new Matrix(1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    public static final Matrix ROTATE_90 = new Matrix(0.0d, 1.0d, -1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    public static final Matrix ROTATE_180 = new Matrix(-1.0d, 0.0d, 0.0d, -1.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);
    public static final Matrix ROTATE_270 = new Matrix(0.0d, -1.0d, 1.0d, 0.0d, 0.0d, 0.0d, 1.0d, 0.0d, 0.0d);

    public Matrix(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        this.f1361u = d5;
        this.f1362v = d6;
        this.f1363w = d7;
        this.f1355a = d;
        this.f1356b = d2;
        this.f1357c = d3;
        this.f1358d = d4;
        this.f1359tx = d8;
        this.f1360ty = d9;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Matrix.class != obj.getClass()) {
            return false;
        }
        Matrix matrix = (Matrix) obj;
        return Double.compare(matrix.f1355a, this.f1355a) == 0 && Double.compare(matrix.f1356b, this.f1356b) == 0 && Double.compare(matrix.f1357c, this.f1357c) == 0 && Double.compare(matrix.f1358d, this.f1358d) == 0 && Double.compare(matrix.f1359tx, this.f1359tx) == 0 && Double.compare(matrix.f1360ty, this.f1360ty) == 0 && Double.compare(matrix.f1361u, this.f1361u) == 0 && Double.compare(matrix.f1362v, this.f1362v) == 0 && Double.compare(matrix.f1363w, this.f1363w) == 0;
    }

    public int hashCode() {
        long doubleToLongBits = Double.doubleToLongBits(this.f1361u);
        long doubleToLongBits2 = Double.doubleToLongBits(this.f1362v);
        long doubleToLongBits3 = Double.doubleToLongBits(this.f1363w);
        long doubleToLongBits4 = Double.doubleToLongBits(this.f1355a);
        long doubleToLongBits5 = Double.doubleToLongBits(this.f1356b);
        long doubleToLongBits6 = Double.doubleToLongBits(this.f1357c);
        long doubleToLongBits7 = Double.doubleToLongBits(this.f1358d);
        long doubleToLongBits8 = Double.doubleToLongBits(this.f1359tx);
        long doubleToLongBits9 = Double.doubleToLongBits(this.f1360ty);
        return (((((((((((((((((int) (doubleToLongBits ^ (doubleToLongBits >>> 32))) * 31) + ((int) (doubleToLongBits2 ^ (doubleToLongBits2 >>> 32)))) * 31) + ((int) (doubleToLongBits3 ^ (doubleToLongBits3 >>> 32)))) * 31) + ((int) (doubleToLongBits4 ^ (doubleToLongBits4 >>> 32)))) * 31) + ((int) (doubleToLongBits5 ^ (doubleToLongBits5 >>> 32)))) * 31) + ((int) (doubleToLongBits6 ^ (doubleToLongBits6 >>> 32)))) * 31) + ((int) (doubleToLongBits7 ^ (doubleToLongBits7 >>> 32)))) * 31) + ((int) (doubleToLongBits8 ^ (doubleToLongBits8 >>> 32)))) * 31) + ((int) (doubleToLongBits9 ^ (doubleToLongBits9 >>> 32)));
    }

    public String toString() {
        if (equals(ROTATE_0)) {
            return "Rotate 0°";
        }
        if (equals(ROTATE_90)) {
            return "Rotate 90°";
        }
        if (equals(ROTATE_180)) {
            return "Rotate 180°";
        }
        if (equals(ROTATE_270)) {
            return "Rotate 270°";
        }
        return "Matrix{u=" + this.f1361u + ", v=" + this.f1362v + ", w=" + this.f1363w + ", a=" + this.f1355a + ", b=" + this.f1356b + ", c=" + this.f1357c + ", d=" + this.f1358d + ", tx=" + this.f1359tx + ", ty=" + this.f1360ty + '}';
    }

    public static Matrix fromFileOrder(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9) {
        return new Matrix(d, d2, d4, d5, d3, d6, d9, d7, d8);
    }

    public static Matrix fromByteBuffer(ByteBuffer byteBuffer) {
        return fromFileOrder(IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint1616(byteBuffer), IsoTypeReader.readFixedPoint0230(byteBuffer));
    }

    public void getContent(ByteBuffer byteBuffer) {
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1355a);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1356b);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f1361u);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1357c);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1358d);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f1362v);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1359tx);
        IsoTypeWriter.writeFixedPoint1616(byteBuffer, this.f1360ty);
        IsoTypeWriter.writeFixedPoint0230(byteBuffer, this.f1363w);
    }
}
