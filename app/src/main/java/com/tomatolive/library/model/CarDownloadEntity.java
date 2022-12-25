package com.tomatolive.library.model;

import com.blankj.utilcode.util.FileUtils;
import com.tomatolive.library.utils.GlideUtils;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class CarDownloadEntity implements Serializable {
    public String animLocalPath;
    public String animalUrl;

    /* renamed from: id */
    public String f5835id;
    public String imgUrl;
    public String name;
    public String versionCode = "0";

    public String getCarFileName() {
        return FileUtils.getFileNameNoExtension(this.animalUrl);
    }

    public String getAnimalUrl() {
        return GlideUtils.formatDownUrl(this.animalUrl);
    }

    public String toString() {
        return "CarDownloadEntity{id='" + this.f5835id + "', name='" + this.name + "', animLocalPath='" + this.animLocalPath + "'}";
    }
}
