package com.j256.ormlite.stmt;

import com.j256.ormlite.support.DatabaseResults;

/* loaded from: classes3.dex */
public interface GenericRowMapper<T> {
    T mapRow(DatabaseResults databaseResults);
}
