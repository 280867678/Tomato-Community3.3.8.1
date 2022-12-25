package com.faceunity.beautycontrolview.entity;

/* loaded from: classes2.dex */
public class Effect {
    private int effectType;
    private String path;

    public Effect(String str, int i, String str2, int i2, int i3, String str3) {
        this.path = str2;
        this.effectType = i3;
    }

    public String path() {
        return this.path;
    }

    public int effectType() {
        return this.effectType;
    }
}
