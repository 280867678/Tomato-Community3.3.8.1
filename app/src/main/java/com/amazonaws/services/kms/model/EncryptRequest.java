package com.amazonaws.services.kms.model;

import com.amazonaws.AmazonWebServiceRequest;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class EncryptRequest extends AmazonWebServiceRequest implements Serializable {
    private Map<String, String> encryptionContext = new HashMap();
    private List<String> grantTokens = new ArrayList();
    private String keyId;
    private ByteBuffer plaintext;

    public String getKeyId() {
        return this.keyId;
    }

    public void setKeyId(String str) {
        this.keyId = str;
    }

    public EncryptRequest withKeyId(String str) {
        this.keyId = str;
        return this;
    }

    public ByteBuffer getPlaintext() {
        return this.plaintext;
    }

    public void setPlaintext(ByteBuffer byteBuffer) {
        this.plaintext = byteBuffer;
    }

    public EncryptRequest withPlaintext(ByteBuffer byteBuffer) {
        this.plaintext = byteBuffer;
        return this;
    }

    public Map<String, String> getEncryptionContext() {
        return this.encryptionContext;
    }

    public void setEncryptionContext(Map<String, String> map) {
        this.encryptionContext = map;
    }

    public EncryptRequest withEncryptionContext(Map<String, String> map) {
        this.encryptionContext = map;
        return this;
    }

    public EncryptRequest addEncryptionContextEntry(String str, String str2) {
        if (this.encryptionContext == null) {
            this.encryptionContext = new HashMap();
        }
        if (this.encryptionContext.containsKey(str)) {
            throw new IllegalArgumentException("Duplicated keys (" + str.toString() + ") are provided.");
        }
        this.encryptionContext.put(str, str2);
        return this;
    }

    public EncryptRequest clearEncryptionContextEntries() {
        this.encryptionContext = null;
        return this;
    }

    public List<String> getGrantTokens() {
        return this.grantTokens;
    }

    public void setGrantTokens(Collection<String> collection) {
        if (collection == null) {
            this.grantTokens = null;
        } else {
            this.grantTokens = new ArrayList(collection);
        }
    }

    public EncryptRequest withGrantTokens(String... strArr) {
        if (getGrantTokens() == null) {
            this.grantTokens = new ArrayList(strArr.length);
        }
        for (String str : strArr) {
            this.grantTokens.add(str);
        }
        return this;
    }

    public EncryptRequest withGrantTokens(Collection<String> collection) {
        setGrantTokens(collection);
        return this;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        if (getKeyId() != null) {
            sb.append("KeyId: " + getKeyId() + ",");
        }
        if (getPlaintext() != null) {
            sb.append("Plaintext: " + getPlaintext() + ",");
        }
        if (getEncryptionContext() != null) {
            sb.append("EncryptionContext: " + getEncryptionContext() + ",");
        }
        if (getGrantTokens() != null) {
            sb.append("GrantTokens: " + getGrantTokens());
        }
        sb.append("}");
        return sb.toString();
    }

    public int hashCode() {
        int i = 0;
        int hashCode = ((((((getKeyId() == null ? 0 : getKeyId().hashCode()) + 31) * 31) + (getPlaintext() == null ? 0 : getPlaintext().hashCode())) * 31) + (getEncryptionContext() == null ? 0 : getEncryptionContext().hashCode())) * 31;
        if (getGrantTokens() != null) {
            i = getGrantTokens().hashCode();
        }
        return hashCode + i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof EncryptRequest)) {
            return false;
        }
        EncryptRequest encryptRequest = (EncryptRequest) obj;
        if ((encryptRequest.getKeyId() == null) ^ (getKeyId() == null)) {
            return false;
        }
        if (encryptRequest.getKeyId() != null && !encryptRequest.getKeyId().equals(getKeyId())) {
            return false;
        }
        if ((encryptRequest.getPlaintext() == null) ^ (getPlaintext() == null)) {
            return false;
        }
        if (encryptRequest.getPlaintext() != null && !encryptRequest.getPlaintext().equals(getPlaintext())) {
            return false;
        }
        if ((encryptRequest.getEncryptionContext() == null) ^ (getEncryptionContext() == null)) {
            return false;
        }
        if (encryptRequest.getEncryptionContext() != null && !encryptRequest.getEncryptionContext().equals(getEncryptionContext())) {
            return false;
        }
        if ((encryptRequest.getGrantTokens() == null) ^ (getGrantTokens() == null)) {
            return false;
        }
        return encryptRequest.getGrantTokens() == null || encryptRequest.getGrantTokens().equals(getGrantTokens());
    }
}
