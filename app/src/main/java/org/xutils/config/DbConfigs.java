package org.xutils.config;

import org.xutils.DbManager;
import org.xutils.common.util.LogUtil;
import org.xutils.p149ex.DbException;

/* loaded from: classes4.dex */
public enum DbConfigs {
    HTTP(new DbManager.DaoConfig().setDbName("xUtils_http_cache.db").setDbVersion(1).setDbOpenListener(new DbManager.DbOpenListener() { // from class: org.xutils.config.DbConfigs.2
        @Override // org.xutils.DbManager.DbOpenListener
        public void onDbOpened(DbManager dbManager) {
            dbManager.getDatabase().enableWriteAheadLogging();
        }
    }).setDbUpgradeListener(new DbManager.DbUpgradeListener() { // from class: org.xutils.config.DbConfigs.1
        @Override // org.xutils.DbManager.DbUpgradeListener
        public void onUpgrade(DbManager dbManager, int i, int i2) {
            try {
                dbManager.dropDb();
            } catch (DbException e) {
                LogUtil.m43e(e.getMessage(), e);
            }
        }
    })),
    COOKIE(new DbManager.DaoConfig().setDbName("xUtils_http_cookie.db").setDbVersion(1).setDbOpenListener(new DbManager.DbOpenListener() { // from class: org.xutils.config.DbConfigs.4
        @Override // org.xutils.DbManager.DbOpenListener
        public void onDbOpened(DbManager dbManager) {
            dbManager.getDatabase().enableWriteAheadLogging();
        }
    }).setDbUpgradeListener(new DbManager.DbUpgradeListener() { // from class: org.xutils.config.DbConfigs.3
        @Override // org.xutils.DbManager.DbUpgradeListener
        public void onUpgrade(DbManager dbManager, int i, int i2) {
            try {
                dbManager.dropDb();
            } catch (DbException e) {
                LogUtil.m43e(e.getMessage(), e);
            }
        }
    }));
    
    private DbManager.DaoConfig config;

    DbConfigs(DbManager.DaoConfig daoConfig) {
        this.config = daoConfig;
    }

    public DbManager.DaoConfig getConfig() {
        return this.config;
    }
}
