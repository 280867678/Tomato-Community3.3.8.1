package com.faceunity.beautycontrolview;

import com.faceunity.beautycontrolview.entity.Effect;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public enum EffectEnum {
    EffectNone("none", R$drawable.ic_delete_all, "none", 4, 0, "");
    
    private String bundleName;
    private String description;
    private int effectType;
    private int maxFace;
    private String path;
    private int resId;

    EffectEnum(String str, int i, String str2, int i2, int i3, String str3) {
        this.bundleName = str;
        this.resId = i;
        this.path = str2;
        this.maxFace = i2;
        this.effectType = i3;
        this.description = str3;
    }

    public String bundleName() {
        return this.bundleName;
    }

    public int resId() {
        return this.resId;
    }

    public String path() {
        return this.path;
    }

    public int maxFace() {
        return this.maxFace;
    }

    public int effectType() {
        return this.effectType;
    }

    public String description() {
        return this.description;
    }

    public Effect effect() {
        return new Effect(this.bundleName, this.resId, this.path, this.maxFace, this.effectType, this.description);
    }

    public static ArrayList<Effect> getEffectsByEffectType(int i) {
        EffectEnum[] values;
        ArrayList<Effect> arrayList = new ArrayList<>();
        arrayList.add(EffectNone.effect());
        for (EffectEnum effectEnum : values()) {
            if (effectEnum.effectType == i) {
                arrayList.add(effectEnum.effect());
            }
        }
        return arrayList;
    }

    public static ArrayList<Effect> getEffects() {
        ArrayList<Effect> arrayList = new ArrayList<>();
        for (EffectEnum effectEnum : values()) {
            arrayList.add(effectEnum.effect());
        }
        return arrayList;
    }
}
