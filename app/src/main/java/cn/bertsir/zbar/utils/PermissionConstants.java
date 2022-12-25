package cn.bertsir.zbar.utils;

import android.annotation.SuppressLint;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SuppressLint({"InlinedApi"})
/* loaded from: classes2.dex */
public final class PermissionConstants {
    public static final String CALENDAR = "android.permission-group.CALENDAR";
    public static final String CAMERA = "android.permission-group.CAMERA";
    public static final String CONTACTS = "android.permission-group.CONTACTS";
    private static final String[] GROUP_CALENDAR = {"android.permission.READ_CALENDAR", "android.permission.WRITE_CALENDAR"};
    private static final String[] GROUP_CAMERA = {"android.permission.CAMERA"};
    private static final String[] GROUP_CONTACTS = {"android.permission.READ_CONTACTS", "android.permission.WRITE_CONTACTS", "android.permission.GET_ACCOUNTS"};
    private static final String[] GROUP_LOCATION = {"android.permission.ACCESS_FINE_LOCATION", "android.permission.ACCESS_COARSE_LOCATION"};
    private static final String[] GROUP_MICROPHONE = {"android.permission.RECORD_AUDIO"};
    private static final String[] GROUP_PHONE = {"android.permission.READ_PHONE_STATE", "android.permission.CALL_PHONE", "android.permission.READ_CALL_LOG", "android.permission.WRITE_CALL_LOG", "com.android.voicemail.permission.ADD_VOICEMAIL", "android.permission.USE_SIP", "android.permission.PROCESS_OUTGOING_CALLS"};
    private static final String[] GROUP_SENSORS = {"android.permission.BODY_SENSORS"};
    private static final String[] GROUP_SMS = {"android.permission.SEND_SMS", "android.permission.RECEIVE_SMS", "android.permission.READ_SMS", "android.permission.RECEIVE_WAP_PUSH", "android.permission.RECEIVE_MMS"};
    private static final String[] GROUP_STORAGE = {"android.permission.READ_EXTERNAL_STORAGE", "android.permission.WRITE_EXTERNAL_STORAGE"};
    public static final String LOCATION = "android.permission-group.LOCATION";
    public static final String MICROPHONE = "android.permission-group.MICROPHONE";
    public static final String PHONE = "android.permission-group.PHONE";
    public static final String SENSORS = "android.permission-group.SENSORS";
    public static final String SMS = "android.permission-group.SMS";
    public static final String STORAGE = "android.permission-group.STORAGE";

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes2.dex */
    public @interface Permission {
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static String[] getPermissions(String str) {
        char c;
        switch (str.hashCode()) {
            case -1639857183:
                if (str.equals(CONTACTS)) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            case -1410061184:
                if (str.equals(PHONE)) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1250730292:
                if (str.equals(CALENDAR)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1140935117:
                if (str.equals(CAMERA)) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case 421761675:
                if (str.equals(SENSORS)) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case 828638019:
                if (str.equals(LOCATION)) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 852078861:
                if (str.equals(STORAGE)) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1581272376:
                if (str.equals(MICROPHONE)) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 1795181803:
                if (str.equals(SMS)) {
                    c = 7;
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
                return GROUP_CALENDAR;
            case 1:
                return GROUP_CAMERA;
            case 2:
                return GROUP_CONTACTS;
            case 3:
                return GROUP_LOCATION;
            case 4:
                return GROUP_MICROPHONE;
            case 5:
                return GROUP_PHONE;
            case 6:
                return GROUP_SENSORS;
            case 7:
                return GROUP_SMS;
            case '\b':
                return GROUP_STORAGE;
            default:
                return new String[]{str};
        }
    }
}
