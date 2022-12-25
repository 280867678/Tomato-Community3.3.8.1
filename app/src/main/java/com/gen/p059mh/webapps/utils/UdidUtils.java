package com.gen.p059mh.webapps.utils;

import android.content.Context;
import android.os.Environment;
import android.telephony.TelephonyManager;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.util.UUID;

/* renamed from: com.gen.mh.webapps.utils.UdidUtils */
/* loaded from: classes2.dex */
public class UdidUtils {
    private static final String DEVICES_FILE_NAME = ".DEVICES";

    public static String CACHE_IMAGE_DIR(Context context) {
        return context.getFilesDir().toString() + "/devices";
    }

    public static String getDeviceId(Context context) {
        String readDeviceID = readDeviceID(context);
        StringBuffer stringBuffer = new StringBuffer();
        if (readDeviceID == null || "".equals(readDeviceID)) {
            try {
                stringBuffer.append(getIMIEStatus(context));
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                stringBuffer.append(getLocalMac(context).replace(":", ""));
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            if (stringBuffer.length() <= 0) {
                stringBuffer.append(UUID.randomUUID().toString().replace("-", ""));
            }
            String md5 = getMD5(stringBuffer.toString(), false);
            if (stringBuffer.length() > 0) {
                saveDeviceID(md5, context);
            }
            return md5;
        }
        return readDeviceID;
    }

    public static String readDeviceID(Context context) {
        File devicesDir = getDevicesDir(context);
        if (!devicesDir.exists()) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(devicesDir), "UTF-8"));
            while (true) {
                int read = bufferedReader.read();
                if (read > -1) {
                    stringBuffer.append((char) read);
                } else {
                    bufferedReader.close();
                    return stringBuffer.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getIMIEStatus(Context context) {
        return ((TelephonyManager) context.getSystemService("phone")).getDeviceId();
    }

    private static String getLocalMac(Context context) {
        StringBuffer stringBuffer = new StringBuffer();
        try {
            NetworkInterface byName = NetworkInterface.getByName("eth1");
            if (byName == null) {
                byName = NetworkInterface.getByName("wlan0");
            }
            if (byName == null) {
                return "";
            }
            byte[] hardwareAddress = byName.getHardwareAddress();
            int length = hardwareAddress.length;
            for (int i = 0; i < length; i++) {
                stringBuffer.append(String.format("%02X:", Byte.valueOf(hardwareAddress[i])));
            }
            if (stringBuffer.length() > 0) {
                stringBuffer.deleteCharAt(stringBuffer.length() - 1);
            }
            return stringBuffer.toString();
        } catch (SocketException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void saveDeviceID(String str, Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(new FileOutputStream(getDevicesDir(context)), "UTF-8");
            outputStreamWriter.write(str);
            outputStreamWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMD5(String str, boolean z) {
        try {
            return bytesToHex(MessageDigest.getInstance("MD5").digest(str.getBytes()), z);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String bytesToHex(byte[] bArr, boolean z) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < bArr.length; i++) {
            int i2 = bArr[i];
            if (i2 < 0) {
                i2 += 256;
            }
            if (i2 < 16) {
                stringBuffer.append("0");
            }
            stringBuffer.append(Integer.toHexString(i2));
        }
        if (z) {
            return stringBuffer.toString().toUpperCase();
        }
        return stringBuffer.toString().toLowerCase();
    }

    private static File getDevicesDir(Context context) {
        if (Environment.getExternalStorageState().equals("mounted")) {
            File file = new File(CACHE_IMAGE_DIR(context));
            if (!file.exists()) {
                file.mkdirs();
            }
            return new File(file, DEVICES_FILE_NAME);
        }
        File file2 = new File(context.getFilesDir(), CACHE_IMAGE_DIR(context));
        if (!file2.exists()) {
            file2.mkdir();
        }
        return new File(file2, DEVICES_FILE_NAME);
    }
}
