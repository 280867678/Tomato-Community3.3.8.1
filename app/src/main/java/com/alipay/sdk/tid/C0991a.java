package com.alipay.sdk.tid;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import com.alipay.sdk.encrypt.C0968b;
import com.alipay.sdk.util.C0994a;
import com.alipay.sdk.util.C0996c;
import java.lang.ref.WeakReference;

/* renamed from: com.alipay.sdk.tid.a */
/* loaded from: classes2.dex */
final class C0991a extends SQLiteOpenHelper {

    /* renamed from: c */
    private WeakReference<Context> f1024c;

    /* JADX INFO: Access modifiers changed from: package-private */
    public C0991a(Context context) {
        super(context, "msp.db", (SQLiteDatabase.CursorFactory) null, 1);
        this.f1024c = new WeakReference<>(context);
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("create table if not exists tb_tid (name text primary key, tid text, key_tid text, dt datetime);");
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        sQLiteDatabase.execSQL("drop table if exists tb_tid");
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: a */
    public void m4472a() {
        SQLiteDatabase sQLiteDatabase = null;
        try {
            try {
                sQLiteDatabase = getWritableDatabase();
                sQLiteDatabase.execSQL("drop table if exists tb_tid");
                if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
                    return;
                }
            } catch (Exception e) {
                C0996c.m4436a(e);
                if (sQLiteDatabase == null || !sQLiteDatabase.isOpen()) {
                    return;
                }
            }
            sQLiteDatabase.close();
        } catch (Throwable th) {
            if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                sQLiteDatabase.close();
            }
            throw th;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002b, code lost:
        if (r2.isOpen() != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x005a, code lost:
        if (r2.isOpen() != false) goto L17;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0063  */
    /* JADX WARN: Removed duplicated region for block: B:23:? A[RETURN, SYNTHETIC] */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String m4471a(String str, String str2) {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor;
        Cursor cursor2 = null;
        r1 = null;
        r1 = null;
        r1 = null;
        String str3 = null;
        cursor2 = null;
        try {
            sQLiteDatabase = getReadableDatabase();
            try {
                cursor = sQLiteDatabase.rawQuery("select tid from tb_tid where name=?", new String[]{m4469c(str, str2)});
                try {
                    if (cursor.moveToFirst()) {
                        str3 = cursor.getString(0);
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase != null) {
                    }
                } catch (Exception unused) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase != null) {
                    }
                    if (TextUtils.isEmpty(str3)) {
                    }
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                        sQLiteDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                cursor = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
            cursor = null;
            sQLiteDatabase = null;
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabase = null;
        }
        return TextUtils.isEmpty(str3) ? C0968b.m4550b(str3, C0994a.m4443c(this.f1024c.get())) : str3;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:16:0x002b, code lost:
        if (r2.isOpen() != false) goto L17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x002d, code lost:
        r2.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x005a, code lost:
        if (r2.isOpen() != false) goto L17;
     */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String m4470b(String str, String str2) {
        SQLiteDatabase sQLiteDatabase;
        Cursor cursor;
        Cursor cursor2 = null;
        r1 = null;
        r1 = null;
        r1 = null;
        String str3 = null;
        cursor2 = null;
        try {
            sQLiteDatabase = getReadableDatabase();
            try {
                cursor = sQLiteDatabase.rawQuery("select key_tid from tb_tid where name=?", new String[]{m4469c(str, str2)});
                try {
                    if (cursor.moveToFirst()) {
                        str3 = cursor.getString(0);
                    }
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase != null) {
                    }
                } catch (Exception unused) {
                    if (cursor != null) {
                        cursor.close();
                    }
                    if (sQLiteDatabase != null) {
                    }
                    return str3;
                } catch (Throwable th) {
                    th = th;
                    cursor2 = cursor;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    if (sQLiteDatabase != null && sQLiteDatabase.isOpen()) {
                        sQLiteDatabase.close();
                    }
                    throw th;
                }
            } catch (Exception unused2) {
                cursor = null;
            } catch (Throwable th2) {
                th = th2;
            }
        } catch (Exception unused3) {
            cursor = null;
            sQLiteDatabase = null;
        } catch (Throwable th3) {
            th = th3;
            sQLiteDatabase = null;
        }
        return str3;
    }

    /* renamed from: c */
    private String m4469c(String str, String str2) {
        return str + str2;
    }
}
