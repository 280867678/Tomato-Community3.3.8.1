package com.github.amr.mimetypes;

import java.util.Arrays;
import java.util.Objects;

/* loaded from: classes2.dex */
public class MimeType {
    private String[] extensions;
    private String mimeType;

    public MimeType(String str, String... strArr) {
        this.mimeType = str;
        this.extensions = strArr;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public String[] getExtensions() {
        return this.extensions;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof MimeType)) {
            return false;
        }
        MimeType mimeType = (MimeType) obj;
        return Objects.equals(this.mimeType, mimeType.mimeType) && Arrays.equals(this.extensions, mimeType.extensions);
    }

    public int hashCode() {
        return (this.mimeType.hashCode() * 47) + Arrays.hashCode(this.extensions);
    }
}
