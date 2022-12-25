package com.faceunity.beautycontrolview;

import com.faceunity.beautycontrolview.entity.Filter;
import java.util.ArrayList;

/* loaded from: classes2.dex */
public enum FilterEnum {
    nature("origin", R$drawable.nature, "origin", 0),
    delta("delta", R$drawable.delta, "delta", 0),
    electric("electric", R$drawable.electric, "electric", 0),
    slowlived("slowlived", R$drawable.slowlived, "slowlived", 0),
    tokyo("tokyo", R$drawable.tokyo, "tokyo", 0),
    warm("warm", R$drawable.warm, "warm", 0),
    ziran("ziran", R$drawable.origin, "自然", 1),
    danya("danya", R$drawable.qingxin, "淡雅", 1),
    fennen("fennen", R$drawable.shaonv, "粉嫩", 1),
    qingxin("qingxin", R$drawable.ziran, "清新", 1),
    hongrun("hongrun", R$drawable.hongrun, "红润", 1);
    
    private String description;
    private String filterName;
    private int filterType;
    private int resId;

    FilterEnum(String str, int i, String str2, int i2) {
        this.filterName = str;
        this.resId = i;
        this.description = str2;
        this.filterType = i2;
    }

    public String filterName() {
        return this.filterName;
    }

    public int resId() {
        return this.resId;
    }

    public String description() {
        return this.description;
    }

    public Filter filter() {
        return new Filter(this.filterName, this.resId, this.description, this.filterType);
    }

    public static ArrayList<Filter> getFiltersByFilterType(int i) {
        FilterEnum[] values;
        ArrayList<Filter> arrayList = new ArrayList<>();
        for (FilterEnum filterEnum : values()) {
            if (filterEnum.filterType == i) {
                arrayList.add(filterEnum.filter());
            }
        }
        return arrayList;
    }
}
