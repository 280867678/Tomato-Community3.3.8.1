package org.xutils.http.cookie;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import org.xutils.C5540x;
import org.xutils.DbManager;
import org.xutils.common.task.PriorityExecutor;
import org.xutils.common.util.LogUtil;
import org.xutils.config.DbConfigs;
import org.xutils.p148db.Selector;
import org.xutils.p148db.sqlite.WhereBuilder;
import org.xutils.p148db.table.DbModel;

/* loaded from: classes4.dex */
public enum DbCookieStore implements CookieStore {
    INSTANCE;
    
    private static final int LIMIT_COUNT = 5000;
    private static final long TRIM_TIME_SPAN = 1000;
    private final Executor trimExecutor = new PriorityExecutor(1, true);
    private long lastTrimTime = 0;

    /* renamed from: db */
    private final DbManager f6070db = C5540x.getDb(DbConfigs.COOKIE.getConfig());

    DbCookieStore() {
        try {
            this.f6070db.delete(CookieEntity.class, WhereBuilder.m30b("expiry", SimpleComparison.EQUAL_TO_OPERATION, -1L));
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    @Override // java.net.CookieStore
    public void add(URI uri, HttpCookie httpCookie) {
        if (httpCookie == null) {
            return;
        }
        try {
            this.f6070db.replace(new CookieEntity(getEffectiveURI(uri), httpCookie));
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        trimSize();
    }

    @Override // java.net.CookieStore
    public List<HttpCookie> get(URI uri) {
        if (uri == null) {
            throw new NullPointerException("uri is null");
        }
        URI effectiveURI = getEffectiveURI(uri);
        ArrayList arrayList = new ArrayList();
        try {
            Selector selector = this.f6070db.selector(CookieEntity.class);
            WhereBuilder m31b = WhereBuilder.m31b();
            String host = effectiveURI.getHost();
            if (!TextUtils.isEmpty(host)) {
                WhereBuilder m30b = WhereBuilder.m30b("domain", SimpleComparison.EQUAL_TO_OPERATION, host);
                WhereBuilder m29or = m30b.m29or("domain", SimpleComparison.EQUAL_TO_OPERATION, "." + host);
                int indexOf = host.indexOf(".");
                int lastIndexOf = host.lastIndexOf(".");
                if (indexOf > 0 && lastIndexOf > indexOf) {
                    String substring = host.substring(indexOf, host.length());
                    if (!TextUtils.isEmpty(substring)) {
                        m29or.m29or("domain", SimpleComparison.EQUAL_TO_OPERATION, substring);
                    }
                }
                m31b.and(m29or);
            }
            String path = effectiveURI.getPath();
            if (!TextUtils.isEmpty(path)) {
                WhereBuilder m29or2 = WhereBuilder.m30b("path", SimpleComparison.EQUAL_TO_OPERATION, path).m29or("path", SimpleComparison.EQUAL_TO_OPERATION, "/").m29or("path", SimpleComparison.EQUAL_TO_OPERATION, null);
                int lastIndexOf2 = path.lastIndexOf("/");
                while (lastIndexOf2 > 0) {
                    path = path.substring(0, lastIndexOf2);
                    m29or2.m29or("path", SimpleComparison.EQUAL_TO_OPERATION, path);
                    lastIndexOf2 = path.lastIndexOf("/");
                }
                m31b.and(m29or2);
            }
            m31b.m29or("uri", SimpleComparison.EQUAL_TO_OPERATION, effectiveURI.toString());
            List<CookieEntity> findAll = selector.where(m31b).findAll();
            if (findAll != null) {
                for (CookieEntity cookieEntity : findAll) {
                    if (!cookieEntity.isExpired()) {
                        arrayList.add(cookieEntity.toHttpCookie());
                    }
                }
            }
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        return arrayList;
    }

    @Override // java.net.CookieStore
    public List<HttpCookie> getCookies() {
        ArrayList arrayList = new ArrayList();
        try {
            List<CookieEntity> findAll = this.f6070db.findAll(CookieEntity.class);
            if (findAll != null) {
                for (CookieEntity cookieEntity : findAll) {
                    if (!cookieEntity.isExpired()) {
                        arrayList.add(cookieEntity.toHttpCookie());
                    }
                }
            }
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        return arrayList;
    }

    @Override // java.net.CookieStore
    public List<URI> getURIs() {
        ArrayList arrayList = new ArrayList();
        try {
            List<DbModel> findAll = this.f6070db.selector(CookieEntity.class).select("uri").findAll();
            if (findAll != null) {
                for (DbModel dbModel : findAll) {
                    String string = dbModel.getString("uri");
                    if (!TextUtils.isEmpty(string)) {
                        arrayList.add(new URI(string));
                    }
                }
            }
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        return arrayList;
    }

    @Override // java.net.CookieStore
    public boolean remove(URI uri, HttpCookie httpCookie) {
        if (httpCookie == null) {
            return true;
        }
        try {
            WhereBuilder m30b = WhereBuilder.m30b("name", SimpleComparison.EQUAL_TO_OPERATION, httpCookie.getName());
            String domain = httpCookie.getDomain();
            if (!TextUtils.isEmpty(domain)) {
                m30b.and("domain", SimpleComparison.EQUAL_TO_OPERATION, domain);
            }
            String path = httpCookie.getPath();
            if (!TextUtils.isEmpty(path)) {
                if (path.length() > 1 && path.endsWith("/")) {
                    path = path.substring(0, path.length() - 1);
                }
                m30b.and("path", SimpleComparison.EQUAL_TO_OPERATION, path);
            }
            this.f6070db.delete(CookieEntity.class, m30b);
            return true;
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
            return false;
        }
    }

    @Override // java.net.CookieStore
    public boolean removeAll() {
        try {
            this.f6070db.delete(CookieEntity.class);
            return true;
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
            return true;
        }
    }

    private void trimSize() {
        this.trimExecutor.execute(new Runnable() { // from class: org.xutils.http.cookie.DbCookieStore.1
            @Override // java.lang.Runnable
            public void run() {
                List findAll;
                long currentTimeMillis = System.currentTimeMillis();
                if (currentTimeMillis - DbCookieStore.this.lastTrimTime < DbCookieStore.TRIM_TIME_SPAN) {
                    return;
                }
                DbCookieStore.this.lastTrimTime = currentTimeMillis;
                try {
                    DbCookieStore.this.f6070db.delete(CookieEntity.class, WhereBuilder.m30b("expiry", SimpleComparison.LESS_THAN_OPERATION, Long.valueOf(System.currentTimeMillis())).and("expiry", "!=", -1L));
                } catch (Throwable th) {
                    LogUtil.m43e(th.getMessage(), th);
                }
                try {
                    int count = (int) DbCookieStore.this.f6070db.selector(CookieEntity.class).count();
                    if (count <= 5010 || (findAll = DbCookieStore.this.f6070db.selector(CookieEntity.class).where("expiry", "!=", -1L).orderBy("expiry", false).limit(count - 5000).findAll()) == null) {
                        return;
                    }
                    DbCookieStore.this.f6070db.delete(findAll);
                } catch (Throwable th2) {
                    LogUtil.m43e(th2.getMessage(), th2);
                }
            }
        });
    }

    private URI getEffectiveURI(URI uri) {
        try {
            return new URI("http", uri.getHost(), uri.getPath(), null, null);
        } catch (Throwable unused) {
            return uri;
        }
    }
}
