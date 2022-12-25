package com.tomatolive.library.model;

import android.support.annotation.DrawableRes;
import com.tomatolive.library.base.BaseActivity;
import java.io.Serializable;

/* loaded from: classes3.dex */
public class MenuEntity implements Serializable {
    private Class<? extends BaseActivity> cls;
    private boolean isChecked;
    public boolean isSelected;
    public String menuDesc;
    public String menuID;
    public int menuIcon;
    public String menuTitle;
    public int menuType;
    public int position;

    public boolean isSelected() {
        return this.isSelected;
    }

    public void setSelected(boolean z) {
        this.isSelected = z;
    }

    public MenuEntity() {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
    }

    public MenuEntity(String str) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
    }

    public MenuEntity(String str, int i) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
        this.menuIcon = i;
    }

    public MenuEntity(String str, int i, boolean z) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
        this.menuIcon = i;
        this.isSelected = z;
    }

    public MenuEntity(String str, int i, boolean z, int i2) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
        this.menuIcon = i;
        this.isSelected = z;
        this.menuType = i2;
    }

    public MenuEntity(String str, String str2) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
        this.menuDesc = str2;
    }

    public MenuEntity(String str, String str2, int i) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
        this.menuDesc = str2;
        this.menuIcon = i;
    }

    public MenuEntity(String str, int i, Class<? extends BaseActivity> cls) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
        this.menuIcon = i;
        this.cls = cls;
    }

    public MenuEntity(String str, int i, int i2, Class<? extends BaseActivity> cls) {
        this.isSelected = false;
        this.position = 0;
        this.isChecked = false;
        this.menuTitle = str;
        this.menuIcon = i;
        this.menuType = i2;
        this.cls = cls;
    }

    public String getMenuTitle() {
        return this.menuTitle;
    }

    public void setMenuTitle(String str) {
        this.menuTitle = str;
    }

    @DrawableRes
    public int getMenuIcon() {
        return this.menuIcon;
    }

    public void setMenuIcon(int i) {
        this.menuIcon = i;
    }

    public Class<? extends BaseActivity> getCls() {
        return this.cls;
    }

    public void setCls(Class<? extends BaseActivity> cls) {
        this.cls = cls;
    }

    public int getMenuType() {
        return this.menuType;
    }

    public void setMenuType(int i) {
        this.menuType = i;
    }

    public boolean isChecked() {
        return this.isChecked;
    }

    public void setChecked(boolean z) {
        this.isChecked = z;
    }

    public String getMenuDesc() {
        return this.menuDesc;
    }

    public void setMenuDesc(String str) {
        this.menuDesc = str;
    }

    public String getMenuID() {
        return this.menuID;
    }

    public void setMenuID(String str) {
        this.menuID = str;
    }
}
