package com.tomatolive.library.utils.litepal.parser;

import android.text.TextUtils;
import com.tomatolive.library.utils.litepal.exceptions.InvalidAttributesException;
import com.tomatolive.library.utils.litepal.util.BaseUtility;
import com.tomatolive.library.utils.litepal.util.Const;
import com.tomatolive.library.utils.litepal.util.SharedUtil;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes4.dex */
public final class LitePalAttr {
    private static volatile LitePalAttr litePalAttr;
    private String cases;
    private List<String> classNames;
    private String dbName;
    private String extraKeyName;
    private String storage;
    private int version;

    private LitePalAttr() {
    }

    public static LitePalAttr getInstance() {
        if (litePalAttr == null) {
            synchronized (LitePalAttr.class) {
                if (litePalAttr == null) {
                    litePalAttr = new LitePalAttr();
                    loadLitePalXMLConfiguration();
                }
            }
        }
        return litePalAttr;
    }

    private static void loadLitePalXMLConfiguration() {
        if (BaseUtility.isLitePalXMLExists()) {
            LitePalConfig parseLitePalConfiguration = LitePalParser.parseLitePalConfiguration();
            litePalAttr.setDbName(parseLitePalConfiguration.getDbName());
            litePalAttr.setVersion(parseLitePalConfiguration.getVersion());
            litePalAttr.setClassNames(parseLitePalConfiguration.getClassNames());
            litePalAttr.setCases(parseLitePalConfiguration.getCases());
            litePalAttr.setStorage(parseLitePalConfiguration.getStorage());
        }
    }

    public static void clearInstance() {
        litePalAttr = null;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int i) {
        this.version = i;
    }

    public String getDbName() {
        return this.dbName;
    }

    public void setDbName(String str) {
        this.dbName = str;
    }

    public String getStorage() {
        return this.storage;
    }

    public void setStorage(String str) {
        this.storage = str;
    }

    public String getExtraKeyName() {
        return this.extraKeyName;
    }

    public void setExtraKeyName(String str) {
        this.extraKeyName = str;
    }

    public List<String> getClassNames() {
        List<String> list = this.classNames;
        if (list == null) {
            this.classNames = new ArrayList();
            this.classNames.add(Const.Utils.TABLE_SCHEMA_CLASS_NAME);
        } else if (list.isEmpty()) {
            this.classNames.add(Const.Utils.TABLE_SCHEMA_CLASS_NAME);
        }
        return this.classNames;
    }

    public void addClassName(String str) {
        getClassNames().add(str);
    }

    public void setClassNames(List<String> list) {
        this.classNames = list;
    }

    public String getCases() {
        return this.cases;
    }

    public void setCases(String str) {
        this.cases = str;
    }

    public void checkSelfValid() {
        if (TextUtils.isEmpty(this.dbName)) {
            loadLitePalXMLConfiguration();
            if (TextUtils.isEmpty(this.dbName)) {
                throw new InvalidAttributesException(InvalidAttributesException.DBNAME_IS_EMPTY_OR_NOT_DEFINED);
            }
        }
        if (!this.dbName.endsWith(".db")) {
            this.dbName += ".db";
        }
        int i = this.version;
        if (i >= 1 && i >= SharedUtil.getLastVersion(this.extraKeyName)) {
            if (TextUtils.isEmpty(this.cases)) {
                this.cases = "lower";
            } else if (this.cases.equals("upper") || this.cases.equals("lower") || this.cases.equals("keep")) {
            } else {
                throw new InvalidAttributesException(this.cases + " is an invalid value for <cases></cases>");
            }
        }
    }
}
