package com.gen.p059mh.webapp_extensions.modules;

import com.gen.p059mh.webapps.database.DBModule;
import com.gen.p059mh.webapps.database.DatabaseProperty;
import com.gen.p059mh.webapps.database.DatabaseTable;

@DatabaseTable(version = "1.0")
/* renamed from: com.gen.mh.webapp_extensions.modules.DownloadInfo */
/* loaded from: classes2.dex */
public class DownloadInfo extends DBModule {
    @DatabaseProperty(index = true)
    public String downloadId;
    @DatabaseProperty
    public DownloadState downloadState;
    @DatabaseProperty
    public String path;
    @DatabaseProperty
    public String url;

    public String getDownloadId() {
        return this.downloadId;
    }

    public void setDownloadId(String str) {
        this.downloadId = str;
    }

    public DownloadState getDownloadState() {
        return this.downloadState;
    }

    public void setDownloadState(DownloadState downloadState) {
        this.downloadState = downloadState;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String str) {
        this.url = str;
    }

    public String getPath() {
        return this.path;
    }

    public void setPath(String str) {
        this.path = str;
    }
}
