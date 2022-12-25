package com.gen.p059mh.webapp_extensions.unity;

import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/* renamed from: com.gen.mh.webapp_extensions.unity.CssProcesser */
/* loaded from: classes2.dex */
public class CssProcesser extends Unity {
    Unity.Method process = new Unity.Method() { // from class: com.gen.mh.webapp_extensions.unity.CssProcesser.1
        @Override // com.gen.p059mh.webapps.unity.Unity.Method
        public void call(Unity.MethodCallback methodCallback, Object... objArr) {
            Map map;
            Object obj = ((List) objArr[0]).get(0);
            String str = null;
            if (obj instanceof Map) {
                map = (Map) obj;
            } else {
                map = obj instanceof ArrayList ? (Map) ((ArrayList) obj).get(0) : null;
            }
            if (map.containsKey("n")) {
                str = (String) map.get("n");
            }
            String str2 = str;
            if (!map.containsKey("p")) {
                methodCallback.run("fail");
                return;
            }
            try {
                URL url = new URL(Utils.getRealPath("http://" + ResourcesLoader.WORK_HOST + "/index.html", (String) map.get("p")));
                String str3 = CssProcesser.this.getWebViewFragment().getWorkPath() + url.getPath().replace(CssProcesser.this.getWebViewFragment().getWorkHost(), "");
                String processWithFilePath = Utils.processWithFilePath(str2, new String(Utils.loadData(str3, Utils.ENCODE_TYPE.WORK, CssProcesser.this.getWebViewFragment().getWACrypto())), CssProcesser.this.getWebViewFragment().getWACrypto().getWorkCrypto(), CssProcesser.this.getWebViewFragment().getWorkPath(), str3);
                Logger.m4113i(processWithFilePath);
                methodCallback.run(processWithFilePath);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
    };

    public CssProcesser() {
        registerMethod("process", this.process);
    }
}
