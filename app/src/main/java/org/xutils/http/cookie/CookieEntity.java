package org.xutils.http.cookie;

import android.text.TextUtils;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.net.HttpCookie;
import java.net.URI;
import org.xutils.p148db.annotation.Column;
import org.xutils.p148db.annotation.Table;

@Table(name = "cookie", onCreated = "CREATE UNIQUE INDEX index_cookie_unique ON cookie(\"name\",\"domain\",\"path\")")
/* loaded from: classes4.dex */
final class CookieEntity {
    private static final long MAX_EXPIRY = System.currentTimeMillis() + 3110400000000L;
    @Column(name = "comment")
    private String comment;
    @Column(name = "commentURL")
    private String commentURL;
    @Column(name = "discard")
    private boolean discard;
    @Column(name = "domain")
    private String domain;
    @Column(name = "expiry")
    private long expiry;
    @Column(isId = true, name = DatabaseFieldConfigLoader.FIELD_NAME_ID)

    /* renamed from: id */
    private long f6069id;
    @Column(name = "name")
    private String name;
    @Column(name = "path")
    private String path;
    @Column(name = "portList")
    private String portList;
    @Column(name = "secure")
    private boolean secure;
    @Column(name = "uri")
    private String uri;
    @Column(name = "value")
    private String value;
    @Column(name = DatabaseFieldConfigLoader.FIELD_NAME_VERSION)
    private int version;

    public CookieEntity() {
        this.expiry = MAX_EXPIRY;
        this.version = 1;
    }

    public CookieEntity(URI uri, HttpCookie httpCookie) {
        this.expiry = MAX_EXPIRY;
        this.version = 1;
        this.uri = uri == null ? null : uri.toString();
        this.name = httpCookie.getName();
        this.value = httpCookie.getValue();
        this.comment = httpCookie.getComment();
        this.commentURL = httpCookie.getCommentURL();
        this.discard = httpCookie.getDiscard();
        this.domain = httpCookie.getDomain();
        long maxAge = httpCookie.getMaxAge();
        if (maxAge != -1 && maxAge > 0) {
            this.expiry = (maxAge * 1000) + System.currentTimeMillis();
            if (this.expiry < 0) {
                this.expiry = MAX_EXPIRY;
            }
        } else {
            this.expiry = -1L;
        }
        this.path = httpCookie.getPath();
        if (!TextUtils.isEmpty(this.path) && this.path.length() > 1 && this.path.endsWith("/")) {
            String str = this.path;
            this.path = str.substring(0, str.length() - 1);
        }
        this.portList = httpCookie.getPortlist();
        this.secure = httpCookie.getSecure();
        this.version = httpCookie.getVersion();
    }

    public HttpCookie toHttpCookie() {
        HttpCookie httpCookie = new HttpCookie(this.name, this.value);
        httpCookie.setComment(this.comment);
        httpCookie.setCommentURL(this.commentURL);
        httpCookie.setDiscard(this.discard);
        httpCookie.setDomain(this.domain);
        long j = this.expiry;
        if (j == -1) {
            httpCookie.setMaxAge(-1L);
        } else {
            httpCookie.setMaxAge((j - System.currentTimeMillis()) / 1000);
        }
        httpCookie.setPath(this.path);
        httpCookie.setPortlist(this.portList);
        httpCookie.setSecure(this.secure);
        httpCookie.setVersion(this.version);
        return httpCookie;
    }

    public long getId() {
        return this.f6069id;
    }

    public void setId(long j) {
        this.f6069id = j;
    }

    public String getUri() {
        return this.uri;
    }

    public void setUri(String str) {
        this.uri = str;
    }

    public boolean isExpired() {
        long j = this.expiry;
        return j != -1 && j < System.currentTimeMillis();
    }
}
