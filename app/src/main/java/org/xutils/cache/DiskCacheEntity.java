package org.xutils.cache;

import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.util.Date;
import org.xutils.p148db.annotation.Column;
import org.xutils.p148db.annotation.Table;

@Table(name = "disk_cache")
/* loaded from: classes4.dex */
public final class DiskCacheEntity {
    @Column(name = "etag")
    private String etag;
    @Column(name = "expires")
    private long expires = Long.MAX_VALUE;
    @Column(name = "hits")
    private long hits;
    @Column(isId = true, name = DatabaseFieldConfigLoader.FIELD_NAME_ID)

    /* renamed from: id */
    private long f6065id;
    @Column(name = "key", property = "UNIQUE")
    private String key;
    @Column(name = "lastAccess")
    private long lastAccess;
    @Column(name = "lastModify")
    private Date lastModify;
    @Column(name = "path")
    private String path;
    @Column(name = "textContent")
    private String textContent;

    public long getId() {
        return this.f6065id;
    }

    public void setId(long j) {
        this.f6065id = j;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String str) {
        this.key = str;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getPath() {
        return this.path;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void setPath(String str) {
        this.path = str;
    }

    public String getTextContent() {
        return this.textContent;
    }

    public void setTextContent(String str) {
        this.textContent = str;
    }

    public long getExpires() {
        return this.expires;
    }

    public void setExpires(long j) {
        this.expires = j;
    }

    public String getEtag() {
        return this.etag;
    }

    public void setEtag(String str) {
        this.etag = str;
    }

    public long getHits() {
        return this.hits;
    }

    public void setHits(long j) {
        this.hits = j;
    }

    public Date getLastModify() {
        return this.lastModify;
    }

    public void setLastModify(Date date) {
        this.lastModify = date;
    }

    public long getLastAccess() {
        long j = this.lastAccess;
        return j == 0 ? System.currentTimeMillis() : j;
    }

    public void setLastAccess(long j) {
        this.lastAccess = j;
    }
}
