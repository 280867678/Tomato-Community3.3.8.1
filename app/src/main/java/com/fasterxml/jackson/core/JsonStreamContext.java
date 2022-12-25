package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.p058io.CharTypes;

/* loaded from: classes2.dex */
public abstract class JsonStreamContext {
    protected int _index;
    protected int _type;

    public abstract String getCurrentName();

    public abstract Object getCurrentValue();

    /* renamed from: getParent */
    public abstract JsonStreamContext mo5976getParent();

    public abstract void setCurrentValue(Object obj);

    /* JADX INFO: Access modifiers changed from: protected */
    public JsonStreamContext() {
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public JsonStreamContext(JsonStreamContext jsonStreamContext) {
        this._type = jsonStreamContext._type;
        this._index = jsonStreamContext._index;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public JsonStreamContext(int i, int i2) {
        this._type = i;
        this._index = i2;
    }

    public final boolean inArray() {
        return this._type == 1;
    }

    public final boolean inRoot() {
        return this._type == 0;
    }

    public final boolean inObject() {
        return this._type == 2;
    }

    public String typeDesc() {
        int i = this._type;
        return i != 0 ? i != 1 ? i != 2 ? "?" : "Object" : "Array" : "root";
    }

    public final int getEntryCount() {
        return this._index + 1;
    }

    public final int getCurrentIndex() {
        int i = this._index;
        if (i < 0) {
            return 0;
        }
        return i;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(64);
        int i = this._type;
        if (i == 0) {
            sb.append("/");
        } else if (i == 1) {
            sb.append('[');
            sb.append(getCurrentIndex());
            sb.append(']');
        } else {
            sb.append('{');
            String currentName = getCurrentName();
            if (currentName != null) {
                sb.append('\"');
                CharTypes.appendQuoted(sb, currentName);
                sb.append('\"');
            } else {
                sb.append('?');
            }
            sb.append('}');
        }
        return sb.toString();
    }
}
