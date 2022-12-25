package com.gen.p059mh.webapps.build;

import android.content.Context;
import android.support.p002v4.internal.view.SupportMenu;
import android.support.p002v4.widget.SwipeRefreshLayout;
import com.gen.p059mh.webapps.build.toolbar.ToolBarImpl;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.Utils;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.build.PagePanelBuilder */
/* loaded from: classes2.dex */
public class PagePanelBuilder implements Builder {
    private Context context;
    private Panel panel;
    int width;

    public PagePanelBuilder(Context context) {
        this.context = context;
        this.panel = new PagePanel(context);
    }

    public Builder buildSwipeRefreshView(boolean z) {
        SwipeRefreshLayout swipeRefreshLayout = new SwipeRefreshLayout(this.context);
        swipeRefreshLayout.setEnabled(z);
        swipeRefreshLayout.setBackgroundColor(SupportMenu.CATEGORY_MASK);
        this.panel.setRefreshView(swipeRefreshLayout);
        return this;
    }

    @Override // com.gen.p059mh.webapps.build.Builder
    public Builder buildNavigationBar(boolean z, Map map) {
        if (z) {
            ToolBarImpl toolBarImpl = new ToolBarImpl(this.context);
            toolBarImpl.setNavigationBarTitle(map.get("navigationBarTitleText").toString());
            toolBarImpl.setNavigationBarColor(Utils.colorFromCSS(map.get("navigationBarBackgroundColor").toString()));
            toolBarImpl.setTextColor(Utils.colorFromCSS(map.get("navigationBarTextStyle").toString()));
            toolBarImpl.hideHomeButton(((Boolean) map.get("isHome")).booleanValue());
            this.panel.setNavigationBar(toolBarImpl);
        }
        return this;
    }

    @Override // com.gen.p059mh.webapps.build.Builder
    public Builder buildTabBar(Map map) {
        if (map != null) {
            Logger.m4115e(map.toString());
        }
        return this;
    }

    @Override // com.gen.p059mh.webapps.build.Builder
    public Panel build() {
        this.panel.build(this.width);
        return this.panel;
    }

    public void release() {
        if (this.panel != null) {
            this.panel = null;
        }
        if (this.context != null) {
            this.context = null;
        }
    }

    @Override // com.gen.p059mh.webapps.build.Builder
    public Builder addWidth(int i) {
        this.width = i;
        return this;
    }
}
