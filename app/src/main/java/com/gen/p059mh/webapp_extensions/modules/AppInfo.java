package com.gen.p059mh.webapp_extensions.modules;

import android.text.TextUtils;
import com.gen.p059mh.webapps.database.DBModule;
import com.gen.p059mh.webapps.database.DatabaseProperty;
import com.gen.p059mh.webapps.database.DatabaseTable;
import com.gen.p059mh.webapps.database.Table;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DatabaseTable(version = "1.3")
/* renamed from: com.gen.mh.webapp_extensions.modules.AppInfo */
/* loaded from: classes2.dex */
public class AppInfo extends DBModule {
    @DatabaseProperty(index = true)
    private String appID;
    @DatabaseProperty
    private int needUpdate;
    @DatabaseProperty
    private String title;
    @DatabaseProperty
    private String version;

    public String toString() {
        return "AppInfo{appID='" + this.appID + "', title='" + this.title + "', version='" + this.version + "', needUpdate=" + this.needUpdate + '}';
    }

    public void setAppID(String str) {
        this.appID = str;
    }

    public String getAppID() {
        return this.appID;
    }

    public void setTitle(String str) {
        this.title = str;
    }

    public String getTitle() {
        return this.title;
    }

    public void setVersion(String str) {
        this.version = str;
    }

    public String getVersion() {
        return this.version;
    }

    public int isNeedUpdate() {
        return this.needUpdate;
    }

    public void setNeedUpdate(int i) {
        this.needUpdate = i;
    }

    public static AppInfo fromAppID(String str) {
        try {
            Table from = Table.from(AppInfo.class);
            AppInfo appInfo = (AppInfo) from.findOne("appID=\"" + str + "\"");
            if (appInfo != null) {
                return appInfo;
            }
        } catch (Table.SQLTaleNoSetupException e) {
            e.printStackTrace();
        }
        AppInfo appInfo2 = new AppInfo();
        appInfo2.setAppID(str);
        return appInfo2;
    }

    public static List<AppInfo> findAll() {
        ArrayList arrayList = new ArrayList();
        try {
            AppInfo[] appInfoArr = (AppInfo[]) Table.from(AppInfo.class).findAll();
            if (appInfoArr != null) {
                return Arrays.asList(appInfoArr);
            }
        } catch (Table.SQLTaleNoSetupException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    public boolean isNewVersion(String str) {
        if (!TextUtils.isEmpty(str) && !TextUtils.isEmpty(this.version) && !TextUtils.equals(str, this.version)) {
            return getVersionCode(str) > getVersionCode(this.version);
        }
        return false;
    }

    private int getVersionCode(String str) {
        try {
            return Integer.valueOf(str.substring(0, str.indexOf("."))).intValue();
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
