package com.facebook.drawee.backends.pipeline.info;

/* loaded from: classes2.dex */
public class ImageOriginUtils {
    public static String toString(int i) {
        return i != 2 ? i != 3 ? i != 4 ? i != 5 ? i != 6 ? "unknown" : "local" : "memory_bitmap" : "memory_encoded" : "disk" : "network";
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static int mapProducerNameToImageOrigin(String str) {
        char c;
        switch (str.hashCode()) {
            case -1914072202:
                if (str.equals("BitmapMemoryCacheGetProducer")) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1683996557:
                if (str.equals("LocalResourceFetchProducer")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case -1579985851:
                if (str.equals("LocalFileFetchProducer")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -1307634203:
                if (str.equals("EncodedMemoryCacheProducer")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1224383234:
                if (str.equals("NetworkFetchProducer")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 656304759:
                if (str.equals("DiskCacheProducer")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 957714404:
                if (str.equals("BitmapMemoryCacheProducer")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 1019542023:
                if (str.equals("LocalAssetFetchProducer")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1721672898:
                if (str.equals("DataFetchProducer")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case 1793127518:
                if (str.equals("LocalContentUriThumbnailFetchProducer")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case 2113652014:
                if (str.equals("LocalContentUriFetchProducer")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
                return 5;
            case 2:
                return 4;
            case 3:
                return 3;
            case 4:
                return 2;
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
                return 6;
            default:
                return 1;
        }
    }
}
