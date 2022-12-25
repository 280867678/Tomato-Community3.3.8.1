package com.gen.p059mh.webapps.container;

import com.gen.p059mh.webapps.container.impl.AppContainerImpl;
import com.gen.p059mh.webapps.container.impl.WebContainerImpl;

/* renamed from: com.gen.mh.webapps.container.ContainerFactory */
/* loaded from: classes2.dex */
public class ContainerFactory {
    public Container createContainer(String str) {
        char c;
        int hashCode = str.hashCode();
        if (hashCode == 96801) {
            if (str.equals("app")) {
                c = 2;
            }
            c = 65535;
        } else if (hashCode != 117588) {
            if (hashCode == 3165170 && str.equals("game")) {
                c = 1;
            }
            c = 65535;
        } else {
            if (str.equals("web")) {
                c = 0;
            }
            c = 65535;
        }
        if (c != 0) {
            if (c == 1) {
                return new WebContainerImpl();
            }
            if (c == 2) {
                return new AppContainerImpl();
            }
            return null;
        }
        return new WebContainerImpl();
    }
}
