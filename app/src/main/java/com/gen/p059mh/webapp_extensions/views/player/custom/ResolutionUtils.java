package com.gen.p059mh.webapp_extensions.views.player.custom;

import java.util.List;

/* renamed from: com.gen.mh.webapp_extensions.views.player.custom.ResolutionUtils */
/* loaded from: classes2.dex */
public class ResolutionUtils {
    public static String getKeyByResource(int i) {
        return i != 1 ? i != 2 ? i != 3 ? i != 4 ? "" : "1080P" : "720P" : "480P" : "360P";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int getResolutionByKey(String str) {
        char c;
        switch (str.hashCode()) {
            case 1572803:
                if (str.equals("360P")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case 1604516:
                if (str.equals("480P")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1688123:
                if (str.equals("720P")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case 46737881:
                if (str.equals("1080P")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        if (c != 0) {
            if (c == 1) {
                return 2;
            }
            if (c == 2) {
                return 3;
            }
            if (c == 3) {
                return 4;
            }
        }
        return 1;
    }

    public static ResourceEntity getResourceByResolution(List<ResourceEntity> list, int i) {
        if (list == null || list.size() == 0) {
            return null;
        }
        ResourceEntity resourceEntity = list.get(0);
        for (ResourceEntity resourceEntity2 : list) {
            if (resourceEntity2.getResolution() == i) {
                resourceEntity = resourceEntity2;
            }
        }
        return resourceEntity;
    }
}
