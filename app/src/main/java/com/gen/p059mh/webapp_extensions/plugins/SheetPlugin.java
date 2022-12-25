package com.gen.p059mh.webapp_extensions.plugins;

import com.gen.p059mh.webapp_extensions.views.dialog.SelectDialog;
import com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack;
import com.gen.p059mh.webapps.Plugin;
import com.google.gson.Gson;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.plugins.SheetPlugin */
/* loaded from: classes2.dex */
public class SheetPlugin extends Plugin {
    SelectDialog sheetDialog;

    public SheetPlugin() {
        super("actionsheet");
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, final Plugin.PluginCallback pluginCallback) {
        List<String> list = (List) ((Map) new Gson().fromJson(str, (Class<Object>) Map.class)).get("itemList");
        if (list != null) {
            if (this.sheetDialog == null) {
                this.sheetDialog = new SelectDialog(getWebViewFragment().getContext());
                this.sheetDialog.setSheetCallBack(new SheetCallBack(this) { // from class: com.gen.mh.webapp_extensions.plugins.SheetPlugin.1
                    @Override // com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack
                    public void onSelect(int i, String str2) {
                        HashMap hashMap = new HashMap();
                        hashMap.put("success", true);
                        hashMap.put("tapIndex", Integer.valueOf(i));
                        pluginCallback.response(hashMap);
                    }

                    @Override // com.gen.p059mh.webapp_extensions.views.dialog.SheetCallBack
                    public void onCancel() {
                        HashMap hashMap = new HashMap();
                        hashMap.put("success", true);
                        hashMap.put("cancel", true);
                        pluginCallback.response(hashMap);
                    }
                });
            }
            this.sheetDialog.show();
            this.sheetDialog.refreshData(list);
            return;
        }
        pluginCallback.response(null);
    }
}
