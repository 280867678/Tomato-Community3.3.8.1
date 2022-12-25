package com.amazonaws.services.p054s3.model;

import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.io.Serializable;

/* renamed from: com.amazonaws.services.s3.model.CanonicalGrantee */
/* loaded from: classes2.dex */
public class CanonicalGrantee implements Grantee, Serializable {

    /* renamed from: id */
    private String f1179id = null;
    private String displayName = null;

    @Override // com.amazonaws.services.p054s3.model.Grantee
    public String getTypeIdentifier() {
        return DatabaseFieldConfigLoader.FIELD_NAME_ID;
    }

    public CanonicalGrantee(String str) {
        setIdentifier(str);
    }

    @Override // com.amazonaws.services.p054s3.model.Grantee
    public void setIdentifier(String str) {
        this.f1179id = str;
    }

    @Override // com.amazonaws.services.p054s3.model.Grantee
    public String getIdentifier() {
        return this.f1179id;
    }

    public void setDisplayName(String str) {
        this.displayName = str;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public boolean equals(Object obj) {
        if (obj instanceof CanonicalGrantee) {
            return this.f1179id.equals(((CanonicalGrantee) obj).f1179id);
        }
        return false;
    }

    public int hashCode() {
        return this.f1179id.hashCode();
    }
}
