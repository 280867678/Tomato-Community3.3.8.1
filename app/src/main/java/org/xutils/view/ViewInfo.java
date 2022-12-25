package org.xutils.view;

/* loaded from: classes4.dex */
final class ViewInfo {
    public int parentId;
    public int value;

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || ViewInfo.class != obj.getClass()) {
            return false;
        }
        ViewInfo viewInfo = (ViewInfo) obj;
        return this.value == viewInfo.value && this.parentId == viewInfo.parentId;
    }

    public int hashCode() {
        return (this.value * 31) + this.parentId;
    }
}
