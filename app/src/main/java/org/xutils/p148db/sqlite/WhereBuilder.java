package org.xutils.p148db.sqlite;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.ManyClause;
import com.j256.ormlite.stmt.query.SimpleComparison;
import com.tomatolive.library.utils.ConstantUtils;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.xutils.p148db.converter.ColumnConverterFactory;
import org.xutils.p148db.table.ColumnUtils;

/* renamed from: org.xutils.db.sqlite.WhereBuilder */
/* loaded from: classes4.dex */
public class WhereBuilder {
    private final List<String> whereItems = new ArrayList();

    private WhereBuilder() {
    }

    /* renamed from: b */
    public static WhereBuilder m31b() {
        return new WhereBuilder();
    }

    /* renamed from: b */
    public static WhereBuilder m30b(String str, String str2, Object obj) {
        WhereBuilder whereBuilder = new WhereBuilder();
        whereBuilder.appendCondition(null, str, str2, obj);
        return whereBuilder;
    }

    public WhereBuilder and(String str, String str2, Object obj) {
        appendCondition(this.whereItems.size() == 0 ? null : ManyClause.AND_OPERATION, str, str2, obj);
        return this;
    }

    public WhereBuilder and(WhereBuilder whereBuilder) {
        String str = this.whereItems.size() == 0 ? ConstantUtils.PLACEHOLDER_STR_ONE : "AND ";
        return expr(str + "(" + whereBuilder.toString() + ")");
    }

    /* renamed from: or */
    public WhereBuilder m29or(String str, String str2, Object obj) {
        appendCondition(this.whereItems.size() == 0 ? null : ManyClause.OR_OPERATION, str, str2, obj);
        return this;
    }

    /* renamed from: or */
    public WhereBuilder m28or(WhereBuilder whereBuilder) {
        String str = this.whereItems.size() == 0 ? ConstantUtils.PLACEHOLDER_STR_ONE : "OR ";
        return expr(str + "(" + whereBuilder.toString() + ")");
    }

    public WhereBuilder expr(String str) {
        List<String> list = this.whereItems;
        list.add(ConstantUtils.PLACEHOLDER_STR_ONE + str);
        return this;
    }

    public int getWhereItemSize() {
        return this.whereItems.size();
    }

    public String toString() {
        if (this.whereItems.size() == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : this.whereItems) {
            sb.append(str);
        }
        return sb.toString();
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r2v1 */
    /* JADX WARN: Type inference failed for: r2v10 */
    /* JADX WARN: Type inference failed for: r2v2, types: [java.util.List, java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r2v3, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r2v5, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r2v6, types: [java.util.List, java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r2v7, types: [java.lang.Iterable] */
    /* JADX WARN: Type inference failed for: r2v9, types: [java.lang.Iterable] */
    private void appendCondition(String str, String str2, String str3, Object obj) {
        StringBuilder sb = new StringBuilder();
        if (this.whereItems.size() > 0) {
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        }
        if (!TextUtils.isEmpty(str)) {
            sb.append(str);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
        }
        sb.append("\"");
        sb.append(str2);
        sb.append("\"");
        if ("!=".equals(str3)) {
            str3 = SimpleComparison.NOT_EQUAL_TO_OPERATION;
        } else if ("==".equals(str3)) {
            str3 = SimpleComparison.EQUAL_TO_OPERATION;
        }
        if (obj == null) {
            if (SimpleComparison.EQUAL_TO_OPERATION.equals(str3)) {
                sb.append(" IS NULL");
            } else if (SimpleComparison.NOT_EQUAL_TO_OPERATION.equals(str3)) {
                sb.append(" IS NOT NULL");
            } else {
                sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
                sb.append(str3);
                sb.append(" NULL");
            }
        } else {
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(str3);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            int i = 0;
            ?? r2 = 0;
            ?? arrayList = 0;
            if ("IN".equalsIgnoreCase(str3)) {
                if (obj instanceof Iterable) {
                    arrayList = (Iterable) obj;
                } else if (obj.getClass().isArray()) {
                    int length = Array.getLength(obj);
                    arrayList = new ArrayList(length);
                    while (i < length) {
                        arrayList.add(Array.get(obj, i));
                        i++;
                    }
                }
                if (arrayList != 0) {
                    StringBuilder sb2 = new StringBuilder("(");
                    for (Object obj2 : arrayList) {
                        Object convert2DbValueIfNeeded = ColumnUtils.convert2DbValueIfNeeded(obj2);
                        if (ColumnDbType.TEXT.equals(ColumnConverterFactory.getDbColumnType(convert2DbValueIfNeeded.getClass()))) {
                            String obj3 = convert2DbValueIfNeeded.toString();
                            if (obj3.indexOf(39) != -1) {
                                obj3 = obj3.replace("'", "''");
                            }
                            sb2.append("'");
                            sb2.append(obj3);
                            sb2.append("'");
                        } else {
                            sb2.append(convert2DbValueIfNeeded);
                        }
                        sb2.append(",");
                    }
                    sb2.deleteCharAt(sb2.length() - 1);
                    sb2.append(")");
                    sb.append(sb2.toString());
                } else {
                    throw new IllegalArgumentException("value must be an Array or an Iterable.");
                }
            } else if ("BETWEEN".equalsIgnoreCase(str3)) {
                if (obj instanceof Iterable) {
                    r2 = (Iterable) obj;
                } else if (obj.getClass().isArray()) {
                    int length2 = Array.getLength(obj);
                    r2 = new ArrayList(length2);
                    while (i < length2) {
                        r2.add(Array.get(obj, i));
                        i++;
                    }
                }
                if (r2 != 0) {
                    Iterator it2 = r2.iterator();
                    if (!it2.hasNext()) {
                        throw new IllegalArgumentException("value must have tow items.");
                    }
                    Object next = it2.next();
                    if (!it2.hasNext()) {
                        throw new IllegalArgumentException("value must have tow items.");
                    }
                    Object next2 = it2.next();
                    Object convert2DbValueIfNeeded2 = ColumnUtils.convert2DbValueIfNeeded(next);
                    Object convert2DbValueIfNeeded3 = ColumnUtils.convert2DbValueIfNeeded(next2);
                    if (ColumnDbType.TEXT.equals(ColumnConverterFactory.getDbColumnType(convert2DbValueIfNeeded2.getClass()))) {
                        String obj4 = convert2DbValueIfNeeded2.toString();
                        if (obj4.indexOf(39) != -1) {
                            obj4 = obj4.replace("'", "''");
                        }
                        String obj5 = convert2DbValueIfNeeded3.toString();
                        if (obj5.indexOf(39) != -1) {
                            obj5 = obj5.replace("'", "''");
                        }
                        sb.append("'");
                        sb.append(obj4);
                        sb.append("'");
                        sb.append(" AND ");
                        sb.append("'");
                        sb.append(obj5);
                        sb.append("'");
                    } else {
                        sb.append(convert2DbValueIfNeeded2);
                        sb.append(" AND ");
                        sb.append(convert2DbValueIfNeeded3);
                    }
                } else {
                    throw new IllegalArgumentException("value must be an Array or an Iterable.");
                }
            } else {
                Object convert2DbValueIfNeeded4 = ColumnUtils.convert2DbValueIfNeeded(obj);
                if (ColumnDbType.TEXT.equals(ColumnConverterFactory.getDbColumnType(convert2DbValueIfNeeded4.getClass()))) {
                    String obj6 = convert2DbValueIfNeeded4.toString();
                    if (obj6.indexOf(39) != -1) {
                        obj6 = obj6.replace("'", "''");
                    }
                    sb.append("'");
                    sb.append(obj6);
                    sb.append("'");
                } else {
                    sb.append(convert2DbValueIfNeeded4);
                }
            }
        }
        this.whereItems.add(sb.toString());
    }
}
