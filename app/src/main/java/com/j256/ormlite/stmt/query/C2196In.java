package com.j256.ormlite.stmt.query;

import com.j256.ormlite.field.FieldType;
import com.j256.ormlite.p075db.DatabaseType;
import com.j256.ormlite.stmt.ArgumentHolder;
import java.util.Arrays;
import java.util.List;

/* renamed from: com.j256.ormlite.stmt.query.In */
/* loaded from: classes3.dex */
public class C2196In extends BaseComparison {

    /* renamed from: in */
    public final boolean f1494in;
    public Iterable<?> objects;

    public C2196In(String str, FieldType fieldType, Iterable<?> iterable, boolean z) {
        super(str, fieldType, null, true);
        this.objects = iterable;
        this.f1494in = z;
    }

    public C2196In(String str, FieldType fieldType, Object[] objArr, boolean z) {
        super(str, fieldType, null, true);
        this.objects = Arrays.asList(objArr);
        this.f1494in = z;
    }

    @Override // com.j256.ormlite.stmt.query.BaseComparison, com.j256.ormlite.stmt.query.Comparison
    public void appendOperation(StringBuilder sb) {
        sb.append(this.f1494in ? "IN " : "NOT IN ");
    }

    @Override // com.j256.ormlite.stmt.query.BaseComparison, com.j256.ormlite.stmt.query.Clause
    public /* bridge */ /* synthetic */ void appendSql(DatabaseType databaseType, String str, StringBuilder sb, List list) {
        super.appendSql(databaseType, str, sb, list);
    }

    @Override // com.j256.ormlite.stmt.query.BaseComparison, com.j256.ormlite.stmt.query.Comparison
    public void appendValue(DatabaseType databaseType, StringBuilder sb, List<ArgumentHolder> list) {
        sb.append('(');
        boolean z = true;
        for (Object obj : this.objects) {
            if (obj == null) {
                throw new IllegalArgumentException("one of the IN values for '" + this.columnName + "' is null");
            }
            if (z) {
                z = false;
            } else {
                sb.append(',');
            }
            super.appendArgOrValue(databaseType, this.fieldType, sb, list, obj);
        }
        sb.append(") ");
    }

    @Override // com.j256.ormlite.stmt.query.BaseComparison, com.j256.ormlite.stmt.query.Comparison
    public /* bridge */ /* synthetic */ String getColumnName() {
        return super.getColumnName();
    }

    @Override // com.j256.ormlite.stmt.query.BaseComparison
    public /* bridge */ /* synthetic */ String toString() {
        return super.toString();
    }
}
