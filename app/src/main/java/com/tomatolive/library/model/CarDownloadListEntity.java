package com.tomatolive.library.model;

import java.io.Serializable;
import java.util.List;

/* loaded from: classes3.dex */
public class CarDownloadListEntity implements Serializable {
    public List<CarDownloadEntity> carList;

    public String toString() {
        return "CarDownloadListEntity{carList=" + this.carList + '}';
    }
}
