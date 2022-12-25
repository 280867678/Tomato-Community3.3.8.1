package com.danikula.videocache.file;

import android.content.Context;
import android.text.TextUtils;
import com.danikula.videocache.ProxyCacheUtils;
import com.tencent.ijk.media.player.IjkMediaMeta;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes2.dex */
public class FileNameUtil {
    public static String getFileNameKey(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        int lastIndexOf2 = str.lastIndexOf(47);
        return (lastIndexOf == -1 || lastIndexOf <= lastIndexOf2 || (lastIndexOf + 2) + 4 <= str.length()) ? "" : str.substring(lastIndexOf2 + 1, lastIndexOf);
    }

    public static String generateFolder(String str) {
        List asList = Arrays.asList(str.split("/"));
        int indexOf = asList.indexOf("_s3");
        return ProxyCacheUtils.computeMD5("_s3/" + ((String) asList.get(indexOf + 1)) + "/" + ((String) asList.get(indexOf + 2)) + "/" + ((String) asList.get(indexOf + 3)));
    }

    public static String generateFileName(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        int lastIndexOf2 = str.lastIndexOf(47);
        int lastIndexOf3 = str.lastIndexOf(63);
        return lastIndexOf3 != -1 ? (lastIndexOf == -1 || lastIndexOf <= lastIndexOf2) ? "" : str.substring(lastIndexOf2 + 1, lastIndexOf3) : (lastIndexOf == -1 || lastIndexOf <= lastIndexOf2 || (lastIndexOf + 2) + 4 <= str.length()) ? "" : str.substring(lastIndexOf2 + 1, str.length());
    }

    public static String generate(String str, String str2) {
        String generateFolder = generateFolder(str);
        String generateFileName = generateFileName(str2);
        return generateFolder + "/" + generateFileName;
    }

    public static String generate(String str, String str2, String str3) {
        String generateFolder = generateFolder(str);
        String generateFileName = generateFileName(str2);
        if (TextUtils.isEmpty(str3)) {
            return generateFolder + "/" + generateFileName;
        }
        return generateFolder + "/" + str3 + "/" + generateFileName;
    }

    public static String getRootUrl(String str) {
        return str.substring(0, str.lastIndexOf(47));
    }

    public static String getUrl(String str) {
        int lastIndexOf = str.lastIndexOf(63);
        return lastIndexOf == -1 ? str : str.substring(0, lastIndexOf);
    }

    public static boolean isM3U8File(String str) {
        return getUrl(str).trim().toLowerCase().endsWith(IjkMediaMeta.IJKM_KEY_M3U8);
    }

    public static String encodeUrlParam(String str) {
        String str2;
        int lastIndexOf = str.lastIndexOf("token=");
        String url = getUrl(str);
        if (lastIndexOf != -1) {
            str2 = str.substring(lastIndexOf + 6, str.length());
            url = url + "?token=";
        } else {
            str2 = "";
        }
        try {
            return URLEncoder.encode(url, EncryptUtil.CHARSET) + str2;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String decodeUrlParam(String str) {
        String str2;
        try {
            str2 = URLDecoder.decode(str, EncryptUtil.CHARSET);
        } catch (Exception e) {
            e.printStackTrace();
            str2 = "";
        }
        if (str2.lastIndexOf("token=") != -1) {
            String str3 = getUrl(str2) + "?token=" + str.substring(str.lastIndexOf("token%3D") + 8, str.length());
            return str3.split("token=").length > 1 ? str3.substring(0, str.lastIndexOf("token%3D") - 1) : str3;
        }
        return str2;
    }

    public static boolean isPlayCache(Context context, String str) {
        context.getPackageName();
        if (TextUtils.isEmpty(str)) {
        }
        return false;
    }
}
