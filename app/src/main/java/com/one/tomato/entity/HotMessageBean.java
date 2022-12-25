package com.one.tomato.entity;

import kotlin.jvm.internal.Intrinsics;

/* compiled from: HotMessageBean.kt */
/* loaded from: classes3.dex */
public final class HotMessageBean {
    private final int backgroundId;

    /* renamed from: id */
    private final int f1709id;
    private final int imageId;
    private final String textTitle;

    public static /* synthetic */ HotMessageBean copy$default(HotMessageBean hotMessageBean, int i, int i2, String str, int i3, int i4, Object obj) {
        if ((i4 & 1) != 0) {
            i = hotMessageBean.imageId;
        }
        if ((i4 & 2) != 0) {
            i2 = hotMessageBean.backgroundId;
        }
        if ((i4 & 4) != 0) {
            str = hotMessageBean.textTitle;
        }
        if ((i4 & 8) != 0) {
            i3 = hotMessageBean.f1709id;
        }
        return hotMessageBean.copy(i, i2, str, i3);
    }

    public final int component1() {
        return this.imageId;
    }

    public final int component2() {
        return this.backgroundId;
    }

    public final String component3() {
        return this.textTitle;
    }

    public final int component4() {
        return this.f1709id;
    }

    public final HotMessageBean copy(int i, int i2, String textTitle, int i3) {
        Intrinsics.checkParameterIsNotNull(textTitle, "textTitle");
        return new HotMessageBean(i, i2, textTitle, i3);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof HotMessageBean) {
                HotMessageBean hotMessageBean = (HotMessageBean) obj;
                if (this.imageId == hotMessageBean.imageId) {
                    if ((this.backgroundId == hotMessageBean.backgroundId) && Intrinsics.areEqual(this.textTitle, hotMessageBean.textTitle)) {
                        if (this.f1709id == hotMessageBean.f1709id) {
                        }
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = ((this.imageId * 31) + this.backgroundId) * 31;
        String str = this.textTitle;
        return ((i + (str != null ? str.hashCode() : 0)) * 31) + this.f1709id;
    }

    public String toString() {
        return "HotMessageBean(imageId=" + this.imageId + ", backgroundId=" + this.backgroundId + ", textTitle=" + this.textTitle + ", id=" + this.f1709id + ")";
    }

    public HotMessageBean(int i, int i2, String textTitle, int i3) {
        Intrinsics.checkParameterIsNotNull(textTitle, "textTitle");
        this.imageId = i;
        this.backgroundId = i2;
        this.textTitle = textTitle;
        this.f1709id = i3;
    }

    public final int getBackgroundId() {
        return this.backgroundId;
    }

    public final int getId() {
        return this.f1709id;
    }

    public final int getImageId() {
        return this.imageId;
    }

    public final String getTextTitle() {
        return this.textTitle;
    }
}
