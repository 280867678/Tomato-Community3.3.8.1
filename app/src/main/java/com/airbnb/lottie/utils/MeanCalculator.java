package com.airbnb.lottie.utils;

/* loaded from: classes2.dex */
public class MeanCalculator {

    /* renamed from: n */
    private int f743n;
    private float sum;

    public void add(float f) {
        this.sum += f;
        this.f743n++;
        int i = this.f743n;
        if (i == Integer.MAX_VALUE) {
            this.sum /= 2.0f;
            this.f743n = i / 2;
        }
    }
}
