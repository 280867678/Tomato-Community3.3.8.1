package com.j256.ormlite.misc;

import com.j256.ormlite.dao.AbstractC2183Dao;
import java.sql.SQLException;

/* loaded from: classes3.dex */
public abstract class BaseDaoEnabled<T, ID> {
    public transient AbstractC2183Dao<T, ID> dao;

    private void checkForDao() {
        if (this.dao != null) {
            return;
        }
        throw new SQLException("Dao has not been set on " + BaseDaoEnabled.class + " object: " + this);
    }

    public int create() {
        checkForDao();
        return this.dao.create(this);
    }

    public int delete() {
        checkForDao();
        return this.dao.delete((AbstractC2183Dao<T, ID>) this);
    }

    public ID extractId() {
        checkForDao();
        return this.dao.extractId(this);
    }

    public AbstractC2183Dao<T, ID> getDao() {
        return this.dao;
    }

    public String objectToString() {
        try {
            checkForDao();
            return this.dao.objectToString(this);
        } catch (SQLException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public boolean objectsEqual(T t) {
        checkForDao();
        return this.dao.objectsEqual(this, t);
    }

    public int refresh() {
        checkForDao();
        return this.dao.refresh(this);
    }

    public void setDao(AbstractC2183Dao<T, ID> abstractC2183Dao) {
        this.dao = abstractC2183Dao;
    }

    public int update() {
        checkForDao();
        return this.dao.update((AbstractC2183Dao<T, ID>) this);
    }

    public int updateId(ID id) {
        checkForDao();
        return this.dao.updateId(this, id);
    }
}
