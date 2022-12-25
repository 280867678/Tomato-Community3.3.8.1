package com.tomatolive.library.model;

import android.graphics.drawable.Drawable;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes3.dex */
public class IconEntity {
    public Drawable guardDrawable;
    public Drawable nobilityDrawable;
    public Drawable roleDrawable;
    public List<String> urls;
    public Drawable userGradeDrawable;
    public String value = "";
    public Drawable weekStarDrawable;

    public String toString() {
        return "IconEntity{value='" + this.value + "', roleDrawable=" + this.roleDrawable + ", guardDrawable=" + this.guardDrawable + ", userGradeDrawable=" + this.userGradeDrawable + ", nobilityDrawable=" + this.nobilityDrawable + ", urls=" + this.urls + '}';
    }

    public List<String> getMarkUrls() {
        List<String> list = this.urls;
        return list == null ? new ArrayList() : list;
    }
}
