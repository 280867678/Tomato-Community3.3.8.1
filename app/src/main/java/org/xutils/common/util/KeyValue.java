package org.xutils.common.util;

/* loaded from: classes4.dex */
public class KeyValue {
    public final String key;
    public final Object value;

    public KeyValue(String str, Object obj) {
        this.key = str;
        this.value = obj;
    }

    public String getValueStr() {
        Object obj = this.value;
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        String str = this.key;
        String str2 = ((KeyValue) obj).key;
        return str == null ? str2 == null : str.equals(str2);
    }

    public int hashCode() {
        String str = this.key;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        return "KeyValue{key='" + this.key + "', value=" + this.value + '}';
    }
}
