package com.luck.picture.lib.permissions;

/* loaded from: classes3.dex */
public class Permission {
    public final boolean granted;
    public final String name;
    public final boolean shouldShowRequestPermissionRationale;

    /* JADX INFO: Access modifiers changed from: package-private */
    public Permission(String str, boolean z, boolean z2) {
        this.name = str;
        this.granted = z;
        this.shouldShowRequestPermissionRationale = z2;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || Permission.class != obj.getClass()) {
            return false;
        }
        Permission permission = (Permission) obj;
        if (this.granted != permission.granted || this.shouldShowRequestPermissionRationale != permission.shouldShowRequestPermissionRationale) {
            return false;
        }
        return this.name.equals(permission.name);
    }

    public int hashCode() {
        return (((this.name.hashCode() * 31) + (this.granted ? 1 : 0)) * 31) + (this.shouldShowRequestPermissionRationale ? 1 : 0);
    }

    public String toString() {
        return "Permission{name='" + this.name + "', granted=" + this.granted + ", shouldShowRequestPermissionRationale=" + this.shouldShowRequestPermissionRationale + '}';
    }
}
