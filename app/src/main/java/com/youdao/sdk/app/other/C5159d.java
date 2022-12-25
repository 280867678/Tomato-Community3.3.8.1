package com.youdao.sdk.app.other;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;
import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import java.util.concurrent.atomic.AtomicInteger;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.youdao.sdk.app.other.d */
/* loaded from: classes4.dex */
public class C5159d {

    /* renamed from: a */
    private final AtomicInteger f5899a = new AtomicInteger();

    /* renamed from: b */
    private final String[] f5900b = {OrmLiteConfigUtil.RAW_DIR_NAME};

    /* renamed from: c */
    private SQLiteDatabase f5901c;

    /* renamed from: d */
    private volatile long f5902d;

    /* renamed from: e */
    private DatabaseUtils.InsertHelper f5903e;

    /* renamed from: f */
    private int f5904f;

    /* renamed from: g */
    private int f5905g;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C5159d(SQLiteDatabase sQLiteDatabase) {
        this.f5902d = 0L;
        this.f5901c = sQLiteDatabase;
        try {
            sQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS statistics (time INT8, raw TEXT);");
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT COUNT(*) FROM statistics", null);
            if (rawQuery.moveToFirst()) {
                this.f5899a.set(rawQuery.getInt(0));
            }
            rawQuery.close();
            Cursor rawQuery2 = sQLiteDatabase.rawQuery("SELECT MAX(_ROWID_) FROM statistics", null);
            if (rawQuery2.moveToFirst()) {
                this.f5902d = rawQuery2.getLong(0);
            }
            rawQuery2.close();
            this.f5903e = new DatabaseUtils.InsertHelper(sQLiteDatabase, "statistics");
            this.f5904f = this.f5903e.getColumnIndex(AopConstants.TIME_KEY);
            this.f5905g = this.f5903e.getColumnIndex(OrmLiteConfigUtil.RAW_DIR_NAME);
        } catch (Exception e) {
            Log.e("StatisticsDB", e.getLocalizedMessage(), e);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public int m224a() {
        return this.f5899a.get();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public long m220b() {
        return this.f5902d;
    }

    /* renamed from: a */
    private long m221a(String str, long j) {
        if (this.f5901c == null) {
            return -1L;
        }
        synchronized (this.f5903e) {
            this.f5903e.prepareForInsert();
            this.f5903e.bind(this.f5904f, j);
            this.f5903e.bind(this.f5905g, str);
            long execute = this.f5903e.execute();
            if (execute < 0) {
                return -1L;
            }
            this.f5899a.incrementAndGet();
            if (execute > this.f5902d) {
                this.f5902d = execute;
            } else {
                Log.e("StatisticsDB", "_ROWID_ NOT INCREASE: " + execute + ", " + this.f5902d);
                Cursor rawQuery = this.f5901c.rawQuery("SELECT MAX(_ROWID_) FROM statistics", null);
                if (rawQuery.moveToFirst()) {
                    this.f5902d = rawQuery.getLong(0);
                }
                rawQuery.close();
            }
            return execute;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public long m222a(String str) {
        if (TextUtils.isEmpty(str)) {
            return -1L;
        }
        return m221a(str, System.currentTimeMillis());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public Cursor m223a(long j) {
        SQLiteDatabase sQLiteDatabase = this.f5901c;
        if (sQLiteDatabase == null) {
            return null;
        }
        String[] strArr = this.f5900b;
        return sQLiteDatabase.query("statistics", strArr, "_ROWID_<=" + j, null, null, null, null);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: b */
    public boolean m219b(long j) {
        int i;
        SQLiteDatabase sQLiteDatabase = this.f5901c;
        if (sQLiteDatabase == null) {
            return false;
        }
        try {
            i = sQLiteDatabase.delete("statistics", "_ROWID_<=" + j, null);
        } catch (Exception e) {
            Log.e("StatisticsDB", e.getLocalizedMessage(), e);
            i = 0;
        }
        this.f5899a.addAndGet(-i);
        return i > 0;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: c */
    public boolean m218c() {
        int i;
        SQLiteDatabase sQLiteDatabase = this.f5901c;
        if (sQLiteDatabase == null) {
            return false;
        }
        try {
            i = sQLiteDatabase.delete("statistics", null, null);
        } catch (Exception e) {
            Log.e("StatisticsDB", e.getLocalizedMessage(), e);
            i = 0;
        }
        this.f5899a.set(0);
        return i > 0;
    }
}
