package com.gen.p059mh.webapps.modules;

import com.eclipsesource.p056v8.V8Object;
import com.gen.p059mh.webapps.build.Panel;
import com.gen.p059mh.webapps.webEngine.WebEngine;
import java.util.HashMap;
import java.util.Map;

/* renamed from: com.gen.mh.webapps.modules.Page */
/* loaded from: classes2.dex */
public class Page {
    Map<String, V8Object> components = new HashMap();
    String currentPath;
    V8Object page;
    Panel panel;

    public String getCurrentPath() {
        return this.currentPath;
    }

    public void setCurrentPath(String str) {
        this.currentPath = str;
    }

    public V8Object getPage() {
        return this.page;
    }

    public void setPage(V8Object v8Object) {
        this.page = v8Object;
    }

    public WebEngine getPageView() {
        return this.panel.getWebView();
    }

    public void setPanel(Panel panel) {
        this.panel = panel;
    }

    public Panel getPanel() {
        return this.panel;
    }

    public Map<String, V8Object> getComponents() {
        return this.components;
    }

    public void release() {
        Map<String, V8Object> map = this.components;
        if (map != null) {
            map.clear();
            this.components = null;
        }
        V8Object v8Object = this.page;
        if (v8Object != null) {
            v8Object.close();
            this.page = null;
        }
        if (this.panel != null) {
            this.panel = null;
        }
    }
}
