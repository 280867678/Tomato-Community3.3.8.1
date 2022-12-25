package com.sensorsdata.analytics.android.sdk.data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.sensorsdata.analytics.android.sdk.SALog;
import org.json.JSONArray;
import org.json.JSONObject;

/* loaded from: classes3.dex */
public class OldBDatabaseHelper extends SQLiteOpenHelper {
    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onCreate(SQLiteDatabase sQLiteDatabase) {
    }

    @Override // android.database.sqlite.SQLiteOpenHelper
    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public OldBDatabaseHelper(Context context, String str) {
        super(context, str, (SQLiteDatabase.CursorFactory) null, 4);
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0060, code lost:
        return r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:12:0x005d, code lost:
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x005b, code lost:
        if (r3 == null) goto L10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:9:0x004f, code lost:
        if (r3 != null) goto L12;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public JSONArray getAllEvents() {
        JSONArray jSONArray = new JSONArray();
        Cursor cursor = null;
        try {
            try {
                cursor = getReadableDatabase().rawQuery(String.format("SELECT * FROM %s ORDER BY %s", "events", "created_at"), null);
                while (cursor.moveToNext()) {
                    JSONObject jSONObject = new JSONObject();
                    jSONObject.put("created_at", cursor.getString(cursor.getColumnIndex("created_at")));
                    jSONObject.put(AopConstants.APP_PROPERTIES_KEY, cursor.getString(cursor.getColumnIndex(AopConstants.APP_PROPERTIES_KEY)));
                    jSONArray.put(jSONObject);
                }
                close();
            } catch (Exception e) {
                SALog.printStackTrace(e);
                close();
            }
        } catch (Throwable th) {
            close();
            if (cursor != null) {
                cursor.close();
            }
            throw th;
        }
    }
}
