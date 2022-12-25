package io.requery.android.database;

import android.database.sqlite.SQLiteException;
import android.util.Log;
import android.util.Pair;
import io.requery.android.database.sqlite.SQLiteDatabase;
import java.io.File;
import java.util.List;

/* loaded from: classes4.dex */
public final class DefaultDatabaseErrorHandler implements DatabaseErrorHandler {
    @Override // io.requery.android.database.DatabaseErrorHandler
    public void onCorruption(SQLiteDatabase sQLiteDatabase) {
        Log.e("DefaultDatabaseError", "Corruption reported by sqlite on database: " + sQLiteDatabase.getPath());
        if (!sQLiteDatabase.isOpen()) {
            deleteDatabaseFile(sQLiteDatabase.getPath());
            return;
        }
        List<Pair<String, String>> list = null;
        try {
            try {
                list = sQLiteDatabase.getAttachedDbs();
            } catch (SQLiteException unused) {
            }
            try {
                sQLiteDatabase.close();
            } catch (SQLiteException unused2) {
            }
        } finally {
            if (list != null) {
                for (Pair<String, String> next : list) {
                    deleteDatabaseFile((String) next.second);
                }
            } else {
                deleteDatabaseFile(sQLiteDatabase.getPath());
            }
        }
    }

    private void deleteDatabaseFile(String str) {
        if (str.equalsIgnoreCase(":memory:") || str.trim().length() == 0) {
            return;
        }
        Log.e("DefaultDatabaseError", "deleting the database file: " + str);
        try {
            SQLiteDatabase.deleteDatabase(new File(str));
        } catch (Exception e) {
            Log.w("DefaultDatabaseError", "delete failed: " + e.getMessage());
        }
    }
}
