package com.gen.p059mh.webapps.build.tabbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.gen.p059mh.webapps.listener.ITabBarOperation;
import com.gen.p059mh.webapps.listener.ImageCallBack;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import com.one.tomato.entity.C2516Ad;
import java.io.File;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.build.tabbar.TabBarImpl */
/* loaded from: classes2.dex */
public class TabBarImpl implements TabBar {
    int color;
    LinearLayout contentLayout;
    List<Map> dataList;
    int lastSelectIndex = 0;
    int selectedColor;
    ITabBarOperation tabBarOperation;
    LinearLayout tabLayout;

    @Override // com.gen.p059mh.webapps.build.tabbar.TabBar
    public void release() {
    }

    @Override // com.gen.p059mh.webapps.build.tabbar.TabBar
    public void init(ViewGroup viewGroup, Map map) {
        Context context = viewGroup.getContext();
        viewGroup.removeAllViews();
        this.color = Utils.colorFromCSS(map.get("color").toString());
        this.selectedColor = Utils.colorFromCSS(map.get("selectedColor").toString());
        int colorFromCSS = Utils.colorFromCSS(map.get("borderStyle").toString());
        int colorFromCSS2 = map.containsKey("backgroundColor") ? Utils.colorFromCSS(map.get("backgroundColor").toString()) : -1;
        View view = new View(context);
        view.setLayoutParams(new ViewGroup.LayoutParams(-1, (int) Utils.d2p(context, 1)));
        view.setBackgroundColor(colorFromCSS);
        this.tabLayout = new LinearLayout(context);
        this.tabLayout.setOrientation(1);
        this.tabLayout.setBackgroundColor(colorFromCSS2);
        this.tabLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) Utils.d2p(context, 60)));
        this.tabLayout.addView(view);
        this.dataList = (List) map.get(C2516Ad.TYPE_LIST);
        this.contentLayout = new LinearLayout(context);
        this.contentLayout.setOrientation(0);
        this.contentLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, (int) Utils.d2p(context, 59)));
        this.tabLayout.addView(this.contentLayout);
        for (final int i = 0; i < this.dataList.size(); i++) {
            final Map map2 = this.dataList.get(i);
            LinearLayout linearLayout = new LinearLayout(context);
            linearLayout.setGravity(17);
            linearLayout.setLayoutParams(new LinearLayout.LayoutParams(-1, -1, 1.0f));
            linearLayout.setOrientation(1);
            final DotView dotView = new DotView(context);
            dotView.setText(map2.get("text").toString());
            linearLayout.addView(dotView);
            StringBuilder sb = new StringBuilder();
            sb.append(ResourcesLoader.WORK_HOST);
            sb.append(File.separator);
            String str = "iconPath";
            sb.append(map2.get(str).toString());
            Logger.m4113i(sb.toString());
            ITabBarOperation iTabBarOperation = this.tabBarOperation;
            if (iTabBarOperation != null) {
                StringBuilder sb2 = new StringBuilder();
                sb2.append("http://");
                sb2.append(ResourcesLoader.WORK_HOST);
                sb2.append(File.separator);
                if (i == 0) {
                    str = "selectedIconPath";
                }
                sb2.append(map2.get(str).toString());
                iTabBarOperation.loadIcon(sb2.toString(), new ImageCallBack<Drawable>() { // from class: com.gen.mh.webapps.build.tabbar.TabBarImpl.1
                    @Override // com.gen.p059mh.webapps.listener.ImageCallBack
                    public void onResult(Drawable drawable) {
                        if (i == 0) {
                            dotView.setTextColor(TabBarImpl.this.selectedColor);
                            dotView.setImageDrawable(drawable);
                            return;
                        }
                        dotView.setTextColor(TabBarImpl.this.color);
                        dotView.setImageDrawable(drawable);
                    }

                    @Override // com.gen.p059mh.webapps.listener.ImageCallBack
                    public void onFailure(Exception exc) {
                        exc.printStackTrace();
                    }
                });
            }
            linearLayout.setOnClickListener(new View.OnClickListener() { // from class: com.gen.mh.webapps.build.tabbar.TabBarImpl.2
                @Override // android.view.View.OnClickListener
                public void onClick(View view2) {
                    if (TabBarImpl.this.checkCanClick(i)) {
                        TabBarImpl tabBarImpl = TabBarImpl.this;
                        tabBarImpl.setTabBarSelectPosition(tabBarImpl.lastSelectIndex, i);
                        TabBarImpl tabBarImpl2 = TabBarImpl.this;
                        int i2 = i;
                        tabBarImpl2.lastSelectIndex = i2;
                        ITabBarOperation iTabBarOperation2 = tabBarImpl2.tabBarOperation;
                        if (iTabBarOperation2 == null) {
                            return;
                        }
                        iTabBarOperation2.onClick(i2, File.separator + map2.get("pagePath").toString());
                    }
                }
            });
            this.contentLayout.addView(linearLayout);
        }
        viewGroup.addView(this.tabLayout);
    }

    boolean checkCanClick(int i) {
        LinearLayout linearLayout;
        LinearLayout linearLayout2;
        int i2 = this.lastSelectIndex;
        return (i2 == i || (linearLayout = (LinearLayout) this.contentLayout.getChildAt(i2)) == null || ((DotView) linearLayout.getChildAt(0)) == null || (linearLayout2 = (LinearLayout) this.contentLayout.getChildAt(i)) == null || ((DotView) linearLayout2.getChildAt(0)) == null) ? false : true;
    }

    public boolean setTabBarSelectPosition(int i, int i2) {
        if (i == i2) {
            return false;
        }
        final DotView dotView = (DotView) ((LinearLayout) this.contentLayout.getChildAt(i)).getChildAt(0);
        if (this.color != 0 && this.selectedColor != 0) {
            ITabBarOperation iTabBarOperation = this.tabBarOperation;
            iTabBarOperation.loadIcon("http://" + ResourcesLoader.WORK_HOST + File.separator + this.dataList.get(i).get("iconPath"), new ImageCallBack<Drawable>(this) { // from class: com.gen.mh.webapps.build.tabbar.TabBarImpl.3
                @Override // com.gen.p059mh.webapps.listener.ImageCallBack
                public void onFailure(Exception exc) {
                }

                @Override // com.gen.p059mh.webapps.listener.ImageCallBack
                public void onResult(Drawable drawable) {
                    dotView.setImageDrawable(drawable);
                }
            });
            dotView.setTextColor(this.color);
        }
        final DotView dotView2 = (DotView) ((LinearLayout) this.contentLayout.getChildAt(i2)).getChildAt(0);
        if (this.color == 0 || this.selectedColor == 0) {
            return true;
        }
        ITabBarOperation iTabBarOperation2 = this.tabBarOperation;
        iTabBarOperation2.loadIcon("http://" + ResourcesLoader.WORK_HOST + File.separator + this.dataList.get(i2).get("selectedIconPath"), new ImageCallBack<Drawable>(this) { // from class: com.gen.mh.webapps.build.tabbar.TabBarImpl.4
            @Override // com.gen.p059mh.webapps.listener.ImageCallBack
            public void onFailure(Exception exc) {
            }

            @Override // com.gen.p059mh.webapps.listener.ImageCallBack
            public void onResult(Drawable drawable) {
                dotView2.setImageDrawable(drawable);
            }
        });
        dotView2.setTextColor(this.selectedColor);
        return true;
    }

    @Override // com.gen.p059mh.webapps.build.tabbar.TabBar
    public void setOperation(ITabBarOperation iTabBarOperation) {
        if (iTabBarOperation != null) {
            this.tabBarOperation = iTabBarOperation;
        }
    }

    @Override // com.gen.p059mh.webapps.build.tabbar.TabBar
    public void showTabBarRedDot(boolean z, int i) {
        if (this.contentLayout.getChildCount() > i) {
            Logger.m4114e("showTabBarRedDot", Integer.valueOf(i));
            ((DotView) ((LinearLayout) this.contentLayout.getChildAt(i)).getChildAt(0)).setBadgeText(z ? "" : null);
        }
    }

    @Override // com.gen.p059mh.webapps.build.tabbar.TabBar
    public void setTabBarBadge(boolean z, int i, String str) {
        if (this.contentLayout.getChildCount() > i) {
            DotView dotView = (DotView) ((LinearLayout) this.contentLayout.getChildAt(i)).getChildAt(0);
            if (!z) {
                str = "";
            }
            dotView.setBadgeText(str);
        }
    }
}
