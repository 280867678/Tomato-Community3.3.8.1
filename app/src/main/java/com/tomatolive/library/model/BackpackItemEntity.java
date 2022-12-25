package com.tomatolive.library.model;

/* loaded from: classes3.dex */
public class BackpackItemEntity extends BaseGiftBackpackEntity {
    public String animalType;
    public String animalUrl;
    public String count;
    public String coverUrl;
    public String endTime;
    public String exp;
    public String imgUrl;
    public int isFragment;
    public int isNobilityTrumpet;
    public String laveTime;
    public String name;
    public String remainTime;
    public String type;
    public boolean isStayTuned = false;
    public int status = 1;

    public String toString() {
        return "BackpackItemEntity{count='" + this.count + "', isNobilityTrumpet=" + this.isNobilityTrumpet + '}';
    }

    public boolean isNobilityTrumpetBoolean() {
        return this.isNobilityTrumpet == 1;
    }

    public boolean isFreeze() {
        return this.status == 0;
    }

    public boolean isPropFragmentBoolean() {
        return this.isFragment == 1;
    }
}
