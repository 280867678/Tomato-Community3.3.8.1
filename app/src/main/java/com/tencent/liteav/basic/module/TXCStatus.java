package com.tencent.liteav.basic.module;

import com.tencent.rtmp.TXLiveConstants;

/* loaded from: classes3.dex */
public class TXCStatus {
    private static native double nativeStatusGetDoubleValue(String str, int i);

    private static native long nativeStatusGetIntValue(String str, int i);

    private static native String nativeStatusGetStrValue(String str, int i);

    private static native boolean nativeStatusSetDoubleValue(String str, int i, double d);

    private static native boolean nativeStatusSetIntValue(String str, int i, long j);

    private static native boolean nativeStatusSetStrValue(String str, int i, String str2);

    private static native void nativeStatusStartRecord(String str);

    private static native void nativeStatusStopRecord(String str);

    /* renamed from: a */
    public static void m2910a(String str) {
        nativeStatusStartRecord(str);
    }

    /* renamed from: b */
    public static void m2907b(String str) {
        nativeStatusStopRecord(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x003e  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m2908a(String str, int i, Object obj) {
        if (str == null || str.length() == 0 || obj == null) {
            return false;
        }
        if (i != 1001 && i != 5001) {
            if (i != 5002) {
                switch (i) {
                    case 2001:
                    case 2002:
                    case 2003:
                    case 2004:
                    case TXLiveConstants.PLAY_EVT_PLAY_PROGRESS /* 2005 */:
                    case TXLiveConstants.PLAY_EVT_PLAY_END /* 2006 */:
                    case TXLiveConstants.PLAY_EVT_PLAY_LOADING /* 2007 */:
                    case TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER /* 2008 */:
                        break;
                    default:
                        switch (i) {
                            case TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC /* 2010 */:
                            case TXLiveConstants.PLAY_EVT_CHANGE_ROTATION /* 2011 */:
                            case TXLiveConstants.PLAY_EVT_GET_MESSAGE /* 2012 */:
                            case TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED /* 2013 */:
                            case TXLiveConstants.PLAY_EVT_VOD_LOADING_END /* 2014 */:
                            case TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC /* 2015 */:
                                break;
                            default:
                                switch (i) {
                                    case 3001:
                                        if (obj instanceof String) {
                                            return nativeStatusSetStrValue(str, i, (String) obj);
                                        }
                                        return false;
                                    case 3002:
                                        break;
                                    case 3003:
                                        break;
                                    default:
                                        switch (i) {
                                            case 4001:
                                                break;
                                            case 4002:
                                            case 4003:
                                            case 4004:
                                                break;
                                            default:
                                                switch (i) {
                                                    case 6001:
                                                    case 6003:
                                                    case 6004:
                                                    case 6005:
                                                    case 6006:
                                                    case 6007:
                                                    case 6008:
                                                    case 6009:
                                                        break;
                                                    case 6002:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case 7001:
                                                            case 7002:
                                                            case 7003:
                                                            case 7004:
                                                            case 7005:
                                                            case 7006:
                                                            case 7007:
                                                            case 7008:
                                                            case 7009:
                                                            case 7010:
                                                            case 7011:
                                                            case 7013:
                                                            case 7016:
                                                            case 7017:
                                                            case 7018:
                                                            case 7020:
                                                                if (obj instanceof Long) {
                                                                    return nativeStatusSetIntValue(str, i, ((Long) obj).longValue());
                                                                }
                                                                return false;
                                                            case 7012:
                                                            case 7014:
                                                            case 7015:
                                                            case 7019:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case 7101:
                                                                    case 7102:
                                                                    case 7103:
                                                                    case 7104:
                                                                    case 7105:
                                                                    case 7107:
                                                                    case 7108:
                                                                    case 7109:
                                                                    case 7111:
                                                                    case 7116:
                                                                    case 7117:
                                                                    case 7118:
                                                                        break;
                                                                    case 7106:
                                                                    case 7110:
                                                                    case 7113:
                                                                    case 7114:
                                                                    case 7115:
                                                                    case 7119:
                                                                        break;
                                                                    case 7112:
                                                                        break;
                                                                    default:
                                                                        return false;
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
            }
            if (obj instanceof Long) {
                return nativeStatusSetIntValue(str, i, ((Long) obj).longValue());
            }
            return false;
        }
        if (obj instanceof Double) {
            return nativeStatusSetDoubleValue(str, i, ((Double) obj).doubleValue());
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x002c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0035  */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Object m2909a(String str, int i) {
        if (str == null || str.length() == 0) {
            return null;
        }
        if (i != 1001 && i != 5001) {
            if (i != 5002) {
                switch (i) {
                    case 2001:
                    case 2002:
                    case 2003:
                    case 2004:
                    case TXLiveConstants.PLAY_EVT_PLAY_PROGRESS /* 2005 */:
                    case TXLiveConstants.PLAY_EVT_PLAY_END /* 2006 */:
                    case TXLiveConstants.PLAY_EVT_PLAY_LOADING /* 2007 */:
                    case TXLiveConstants.PLAY_EVT_START_VIDEO_DECODER /* 2008 */:
                        break;
                    default:
                        switch (i) {
                            case TXLiveConstants.PLAY_EVT_GET_PLAYINFO_SUCC /* 2010 */:
                            case TXLiveConstants.PLAY_EVT_CHANGE_ROTATION /* 2011 */:
                            case TXLiveConstants.PLAY_EVT_GET_MESSAGE /* 2012 */:
                            case TXLiveConstants.PLAY_EVT_VOD_PLAY_PREPARED /* 2013 */:
                            case TXLiveConstants.PLAY_EVT_VOD_LOADING_END /* 2014 */:
                            case TXLiveConstants.PLAY_EVT_STREAM_SWITCH_SUCC /* 2015 */:
                                break;
                            default:
                                switch (i) {
                                    case 3001:
                                        return nativeStatusGetStrValue(str, i);
                                    case 3002:
                                        break;
                                    case 3003:
                                        break;
                                    default:
                                        switch (i) {
                                            case 4001:
                                                break;
                                            case 4002:
                                            case 4003:
                                            case 4004:
                                                break;
                                            default:
                                                switch (i) {
                                                    case 6001:
                                                    case 6003:
                                                    case 6004:
                                                    case 6005:
                                                    case 6006:
                                                    case 6007:
                                                    case 6008:
                                                    case 6009:
                                                        break;
                                                    case 6002:
                                                        break;
                                                    default:
                                                        switch (i) {
                                                            case 7001:
                                                            case 7002:
                                                            case 7003:
                                                            case 7004:
                                                            case 7005:
                                                            case 7006:
                                                            case 7007:
                                                            case 7008:
                                                            case 7009:
                                                            case 7010:
                                                            case 7011:
                                                            case 7013:
                                                            case 7016:
                                                            case 7017:
                                                            case 7018:
                                                            case 7020:
                                                                return Long.valueOf(nativeStatusGetIntValue(str, i));
                                                            case 7012:
                                                            case 7014:
                                                            case 7015:
                                                            case 7019:
                                                                break;
                                                            default:
                                                                switch (i) {
                                                                    case 7101:
                                                                    case 7102:
                                                                    case 7103:
                                                                    case 7104:
                                                                    case 7105:
                                                                    case 7107:
                                                                    case 7108:
                                                                    case 7109:
                                                                    case 7111:
                                                                    case 7116:
                                                                    case 7117:
                                                                    case 7118:
                                                                        break;
                                                                    case 7106:
                                                                    case 7110:
                                                                    case 7113:
                                                                    case 7114:
                                                                    case 7115:
                                                                    case 7119:
                                                                        break;
                                                                    case 7112:
                                                                        break;
                                                                    default:
                                                                        return null;
                                                                }
                                                        }
                                                }
                                        }
                                }
                        }
                }
            }
            return Long.valueOf(nativeStatusGetIntValue(str, i));
        }
        return Double.valueOf(nativeStatusGetDoubleValue(str, i));
    }

    /* renamed from: b */
    public static long m2906b(String str, int i) {
        Object m2909a = m2909a(str, i);
        if (m2909a == null || !(m2909a instanceof Long)) {
            return 0L;
        }
        return ((Long) m2909a).longValue();
    }

    /* renamed from: c */
    public static String m2905c(String str, int i) {
        Object m2909a = m2909a(str, i);
        return (m2909a == null || !(m2909a instanceof String)) ? "" : (String) m2909a;
    }

    /* renamed from: d */
    public static int m2904d(String str, int i) {
        Object m2909a = m2909a(str, i);
        if (m2909a == null || !(m2909a instanceof Long)) {
            return 0;
        }
        return ((Long) m2909a).intValue();
    }

    /* renamed from: e */
    public static double m2903e(String str, int i) {
        Object m2909a = m2909a(str, i);
        if (m2909a == null || !(m2909a instanceof Double)) {
            return 0.0d;
        }
        return ((Double) m2909a).doubleValue();
    }
}
