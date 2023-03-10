package android.support.p005v7.view.menu;

import android.content.Context;
import android.support.p002v4.internal.view.SupportMenuItem;
import android.support.p002v4.internal.view.SupportSubMenu;
import android.support.p002v4.util.ArrayMap;
import android.view.MenuItem;
import android.view.SubMenu;
import java.util.Iterator;
import java.util.Map;

/* renamed from: android.support.v7.view.menu.BaseMenuWrapper */
/* loaded from: classes2.dex */
abstract class BaseMenuWrapper<T> extends BaseWrapper<T> {
    final Context mContext;
    private Map<SupportMenuItem, MenuItem> mMenuItems;
    private Map<SupportSubMenu, SubMenu> mSubMenus;

    /* JADX INFO: Access modifiers changed from: package-private */
    public BaseMenuWrapper(Context context, T t) {
        super(t);
        this.mContext = context;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final MenuItem getMenuItemWrapper(MenuItem menuItem) {
        if (menuItem instanceof SupportMenuItem) {
            SupportMenuItem supportMenuItem = (SupportMenuItem) menuItem;
            if (this.mMenuItems == null) {
                this.mMenuItems = new ArrayMap();
            }
            MenuItem menuItem2 = this.mMenuItems.get(menuItem);
            if (menuItem2 != null) {
                return menuItem2;
            }
            MenuItem wrapSupportMenuItem = MenuWrapperFactory.wrapSupportMenuItem(this.mContext, supportMenuItem);
            this.mMenuItems.put(supportMenuItem, wrapSupportMenuItem);
            return wrapSupportMenuItem;
        }
        return menuItem;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final SubMenu getSubMenuWrapper(SubMenu subMenu) {
        if (subMenu instanceof SupportSubMenu) {
            SupportSubMenu supportSubMenu = (SupportSubMenu) subMenu;
            if (this.mSubMenus == null) {
                this.mSubMenus = new ArrayMap();
            }
            SubMenu subMenu2 = this.mSubMenus.get(supportSubMenu);
            if (subMenu2 != null) {
                return subMenu2;
            }
            SubMenu wrapSupportSubMenu = MenuWrapperFactory.wrapSupportSubMenu(this.mContext, supportSubMenu);
            this.mSubMenus.put(supportSubMenu, wrapSupportSubMenu);
            return wrapSupportSubMenu;
        }
        return subMenu;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void internalClear() {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map != null) {
            map.clear();
        }
        Map<SupportSubMenu, SubMenu> map2 = this.mSubMenus;
        if (map2 != null) {
            map2.clear();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void internalRemoveGroup(int i) {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map == null) {
            return;
        }
        Iterator<SupportMenuItem> it2 = map.keySet().iterator();
        while (it2.hasNext()) {
            if (i == it2.next().getGroupId()) {
                it2.remove();
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public final void internalRemoveItem(int i) {
        Map<SupportMenuItem, MenuItem> map = this.mMenuItems;
        if (map == null) {
            return;
        }
        Iterator<SupportMenuItem> it2 = map.keySet().iterator();
        while (it2.hasNext()) {
            if (i == it2.next().getItemId()) {
                it2.remove();
                return;
            }
        }
    }
}
