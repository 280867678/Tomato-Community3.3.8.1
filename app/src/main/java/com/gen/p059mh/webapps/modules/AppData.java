package com.gen.p059mh.webapps.modules;

import com.gen.p059mh.webapps.database.DBModule;
import com.gen.p059mh.webapps.database.DatabaseProperty;
import com.gen.p059mh.webapps.database.DatabaseTable;
import com.gen.p059mh.webapps.database.Table;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.SerializableUtils;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@DatabaseTable(version = "1.6")
/* renamed from: com.gen.mh.webapps.modules.AppData */
/* loaded from: classes2.dex */
public class AppData extends DBModule {
    @DatabaseProperty(index = true)
    private String appID;
    @DatabaseProperty
    private String scope;
    @DatabaseProperty
    private Table.Blob storageBlob;
    private Map<String, Object> scopeMap = null;
    public Map<String, Object> storageBlobMap = null;
    private boolean storageBlobChange = false;

    public String getAppID() {
        return this.appID;
    }

    public void setAppID(String str) {
        this.appID = str;
    }

    public Map<String, Object> getScopeMap() {
        if (this.scopeMap == null) {
            if (this.scope == null) {
                this.scopeMap = new HashMap();
            } else {
                this.scopeMap = (Map) new Gson().fromJson(this.scope, (Class<Object>) Map.class);
            }
        }
        return this.scopeMap;
    }

    public Map<String, Object> getStorageBlobMap() {
        StorageLinkedHashMap storageLinkedHashMap;
        if (this.storageBlobMap == null) {
            Table.Blob blob = this.storageBlob;
            try {
                if (blob == null) {
                    this.storageBlobMap = new StorageLinkedHashMap(true);
                } else {
                    try {
                        this.storageBlobMap = SerializableUtils.deSerializableMap(blob.getBuffer());
                    } catch (Exception e) {
                        e.printStackTrace();
                        if (this.storageBlobMap == null) {
                            storageLinkedHashMap = new StorageLinkedHashMap(true);
                        }
                    }
                    if (this.storageBlobMap == null) {
                        storageLinkedHashMap = new StorageLinkedHashMap(true);
                        this.storageBlobMap = storageLinkedHashMap;
                    }
                }
            } catch (Throwable th) {
                if (this.storageBlobMap == null) {
                    this.storageBlobMap = new StorageLinkedHashMap(true);
                }
                throw th;
            }
        }
        return this.storageBlobMap;
    }

    public Object getStorageBlob(String str) {
        if (getStorageBlobMap().containsKey(str)) {
            return getStorageBlobMap().get(str);
        }
        return null;
    }

    public void setStorageBlob(String str, Object obj) {
        getStorageBlobMap().put(str, obj);
        this.storageBlobChange = true;
        SerializableUtils.clearLast(getStorageBlobMap());
        Logger.m4112i("setData", "key success");
    }

    public void clearStorageBlob() {
        getStorageBlobMap().clear();
        Logger.m4112i("storageBlobMap", "storageBlobMap.size = " + getStorageBlobMap().size());
        this.storageBlobChange = true;
    }

    public void clearStorageBlob(String str) {
        getStorageBlobMap().remove(str);
        this.storageBlobChange = true;
    }

    public static AppData fromAppID(String str) {
        try {
            Table from = Table.from(AppData.class);
            AppData appData = (AppData) from.findOne("appID=\"" + str + "\"");
            if (appData != null) {
                return appData;
            }
        } catch (Table.SQLTaleNoSetupException e) {
            e.printStackTrace();
        }
        AppData appData2 = new AppData();
        appData2.setAppID(str);
        return appData2;
    }

    public static AppData[] fromAppID() {
        try {
            AppData[] appDataArr = (AppData[]) Table.from(AppData.class).findAll();
            if (appDataArr != null) {
                return appDataArr;
            }
        } catch (Table.SQLTaleNoSetupException e) {
            e.printStackTrace();
        }
        return new AppData[0];
    }

    @Override // com.gen.p059mh.webapps.database.DBModule
    public void save() {
        if (this.storageBlobChange) {
            try {
                if (getStorageBlobMap() != null) {
                    this.storageBlob = new Table.Blob(SerializableUtils.serializableMap(getStorageBlobMap()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        super.save();
    }
}
