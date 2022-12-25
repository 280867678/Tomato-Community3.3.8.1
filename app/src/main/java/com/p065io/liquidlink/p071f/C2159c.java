package com.p065io.liquidlink.p071f;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import com.p065io.liquidlink.p074i.C2173a;
import com.p089pm.liquidlink.p092c.C3056d;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.nio.charset.Charset;

/* renamed from: com.io.liquidlink.f.c */
/* loaded from: classes3.dex */
public class C2159c extends AbstractC2157a {
    public C2159c(Context context) {
        super(context);
        C3056d.m3731a(C2159c.class);
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2157a
    /* renamed from: b */
    public boolean mo3981b(String str, String str2) {
        String mo3974a = mo3974a(str);
        String encodeToString = Base64.encodeToString(str2.getBytes(Charset.forName(EncryptUtil.CHARSET)), 0);
        if (TextUtils.isEmpty(mo3974a)) {
            ContentResolver contentResolver = this.f1444a.getContentResolver();
            Uri contentUri = MediaStore.Files.getContentUri("external");
            ContentValues contentValues = new ContentValues();
            if (Build.VERSION.SDK_INT <= 28) {
                contentValues.put("_data", str);
            }
            contentValues.put("title", encodeToString);
            contentValues.put("_display_name", str);
            if (contentResolver.insert(contentUri, contentValues) == null) {
                return false;
            }
        } else if (!mo3974a.equalsIgnoreCase(str2)) {
            ContentResolver contentResolver2 = this.f1444a.getContentResolver();
            Uri contentUri2 = MediaStore.Files.getContentUri("external");
            ContentValues contentValues2 = new ContentValues();
            contentValues2.put("title", encodeToString);
            if (contentResolver2.update(contentUri2, contentValues2, "_display_name=?", new String[]{str}) < 1) {
                return false;
            }
        }
        return true;
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x006e, code lost:
        if (r10.isClosed() == false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:7:0x0056, code lost:
        if (r10.isClosed() == false) goto L8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:8:0x0070, code lost:
        r10.close();
     */
    @Override // com.p065io.liquidlink.p071f.AbstractC2157a
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public String mo3980c(String str) {
        Cursor cursor;
        try {
            cursor = this.f1444a.getContentResolver().query(MediaStore.Files.getContentUri("external"), new String[]{"_display_name", "title"}, "_display_name=?", new String[]{str}, null);
            if (cursor != null) {
                try {
                    if (cursor.getCount() > 0) {
                        cursor.moveToFirst();
                        String m3920a = C2173a.m3920a(cursor.getString(cursor.getColumnIndex("title")), 0);
                        cursor.close();
                        if (cursor != null && !cursor.isClosed()) {
                            cursor.close();
                        }
                        return m3920a;
                    }
                } catch (Exception unused) {
                    if (cursor != null) {
                    }
                    return null;
                } catch (Throwable th) {
                    th = th;
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                    throw th;
                }
            }
            if (cursor != null) {
            }
        } catch (Exception unused2) {
            cursor = null;
        } catch (Throwable th2) {
            th = th2;
            cursor = null;
        }
        return null;
    }

    @Override // com.p065io.liquidlink.p071f.AbstractC2157a
    /* renamed from: d */
    public boolean mo3979d(String str) {
        try {
            return this.f1444a.getContentResolver().delete(MediaStore.Files.getContentUri("external"), "_display_name=?", new String[]{str}) > 0;
        } catch (Exception unused) {
            return false;
        }
    }
}
