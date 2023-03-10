package com.p140wj.rebound;

/* renamed from: com.wj.rebound.SpringLooper */
/* loaded from: classes4.dex */
public abstract class SpringLooper {
    protected BaseSpringSystem mSpringSystem;

    public abstract void start();

    public abstract void stop();

    public void setSpringSystem(BaseSpringSystem baseSpringSystem) {
        this.mSpringSystem = baseSpringSystem;
    }
}
