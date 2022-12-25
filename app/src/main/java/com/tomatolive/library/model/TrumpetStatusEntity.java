package com.tomatolive.library.model;

import java.io.Serializable;

/* loaded from: classes3.dex */
public class TrumpetStatusEntity implements Serializable {
    public int count;
    public int status;

    public boolean isEnable() {
        return this.status == 1;
    }
}
