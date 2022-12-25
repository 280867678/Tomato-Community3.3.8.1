package com.tomatolive.library.utils.litepal.tablemanager;

import android.database.sqlite.SQLiteDatabase;
import com.tomatolive.library.utils.litepal.tablemanager.model.TableModel;
import com.tomatolive.library.utils.litepal.util.DBUtility;
import java.util.ArrayList;
import java.util.List;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public class Creator extends AssociationCreator {
    public static final String TAG = "Creator";

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.tomatolive.library.utils.litepal.tablemanager.AssociationCreator, com.tomatolive.library.utils.litepal.tablemanager.Generator
    public void createOrUpgradeTable(SQLiteDatabase sQLiteDatabase, boolean z) {
        for (TableModel tableModel : getAllTableModels()) {
            createOrUpgradeTable(tableModel, sQLiteDatabase, z);
        }
    }

    /* JADX INFO: Access modifiers changed from: protected */
    public void createOrUpgradeTable(TableModel tableModel, SQLiteDatabase sQLiteDatabase, boolean z) {
        execute(getCreateTableSQLs(tableModel, sQLiteDatabase, z), sQLiteDatabase);
        giveTableSchemaACopy(tableModel.getTableName(), 0, sQLiteDatabase);
    }

    protected List<String> getCreateTableSQLs(TableModel tableModel, SQLiteDatabase sQLiteDatabase, boolean z) {
        ArrayList arrayList = new ArrayList();
        if (z) {
            arrayList.add(generateDropTableSQL(tableModel));
            arrayList.add(generateCreateTableSQL(tableModel));
        } else if (DBUtility.isTableExists(tableModel.getTableName(), sQLiteDatabase)) {
            return null;
        } else {
            arrayList.add(generateCreateTableSQL(tableModel));
        }
        return arrayList;
    }

    private String generateDropTableSQL(TableModel tableModel) {
        return generateDropTableSQL(tableModel.getTableName());
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String generateCreateTableSQL(TableModel tableModel) {
        return generateCreateTableSQL(tableModel.getTableName(), tableModel.getColumnModels(), true);
    }
}
