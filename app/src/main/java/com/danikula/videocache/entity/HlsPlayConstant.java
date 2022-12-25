package com.danikula.videocache.entity;

import android.text.TextUtils;
import com.danikula.videocache.file.FileNameUtil;
import com.zzbwuhan.beard.BCrypto;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class HlsPlayConstant {
    private static Map<String, Object> hlsPlay = new HashMap();

    public static void loadM3U8(Map<String, Object> map) {
        if (map == null) {
            return;
        }
        Object obj = map.get("hls_play_m3u8_load");
        if (obj == null) {
            map.put("hls_play_m3u8_load", new Integer(1));
        } else {
            map.put("hls_play_m3u8_load", Integer.valueOf(((Integer) obj).intValue() + 1));
        }
    }

    public static boolean isReloadM3U8(Map<String, Object> map) {
        Object obj;
        return (map == null || (obj = map.get("hls_play_m3u8_load")) == null || ((Integer) obj).intValue() <= 1) ? false : true;
    }

    public static void putHlsPlayTsCount(Map<String, Object> map, int i) {
        if (map == null) {
            return;
        }
        map.put("hls_play_ts_count", new Integer(i));
    }

    public static int getHlsPlayTsCount(Map<String, Object> map) {
        Object obj = map.get("hls_play_ts_count");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    public static String getKey(String str) {
        return TextUtils.isEmpty(str) ? "" : FileNameUtil.getFileNameKey(str);
    }

    private static HlsPlay getHlsPlayForKey(Map<String, Object> map, String str) {
        Object obj;
        String key = getKey(str);
        if (!TextUtils.isEmpty(key) && (obj = map.get(key)) != null) {
            return (HlsPlay) obj;
        }
        return null;
    }

    private static int getPlayCurrentNum(Map<String, Object> map, String str) {
        HlsPlay hlsPlayForKey = getHlsPlayForKey(map, str);
        if (hlsPlayForKey == null) {
            return 0;
        }
        return hlsPlayForKey.currentNum;
    }

    public static int getPlayCurrentId(Map<String, Object> map) {
        Object obj = map.get("hls_play_current_num");
        if (obj == null) {
            return 0;
        }
        return ((Integer) obj).intValue();
    }

    private static void putPlayCurrentId(Map<String, Object> map, int i) {
        if (map == null) {
            return;
        }
        map.put("hls_play_current_num", Integer.valueOf(i));
    }

    public static void putPlayCurrentId(Map<String, Object> map, String str) {
        putPlayCurrentId(map, getPlayCurrentNum(map, str));
    }

    public static boolean isNextTsPlay(Map<String, Object> map, String str) {
        HlsPlay hlsPlayForKey = getHlsPlayForKey(map, str);
        if (hlsPlayForKey == null) {
            return false;
        }
        int i = hlsPlayForKey.currentNum;
        int playCurrentId = getPlayCurrentId(map);
        return playCurrentId != 0 && playCurrentId + 1 == i;
    }

    public static HlsPlay getHlsPlay(Map<String, Object> map, String str) {
        Object obj;
        if (map == null || TextUtils.isEmpty(str) || (obj = map.get(str)) == null) {
            return null;
        }
        return (HlsPlay) obj;
    }

    public static void putHlsKey(String str) {
        hlsPlay.put("hls_play_m3u8_key", str);
    }

    public static void putHlsKeyIdx(int i) {
        hlsPlay.put("hls_play_m3u8_key_index", Integer.valueOf(i));
    }

    public static String getHlsKey() {
        Object obj = hlsPlay.get("hls_play_m3u8_key");
        return obj == null ? "" : obj.toString();
    }

    public static int getHlsKeyIdx() {
        Object obj = hlsPlay.get("hls_play_m3u8_key_index");
        if (obj == null) {
            return -1;
        }
        return ((Integer) obj).intValue();
    }

    public static void setKeyPtr(Long l) {
        hlsPlay.put("hls_play_key_ptr", l);
    }

    public static Long getKeyPtr() {
        Object obj = hlsPlay.get("hls_play_key_ptr");
        if (obj != null) {
            return Long.valueOf(((Long) obj).longValue());
        }
        String hlsKey = getHlsKey();
        if (TextUtils.isEmpty(hlsKey)) {
            return 0L;
        }
        long decodeKey = BCrypto.decodeKey(hlsKey, getHlsKeyIdx());
        setKeyPtr(Long.valueOf(decodeKey));
        return Long.valueOf(decodeKey);
    }

    public static void putHLSPlayError(boolean z) {
        hlsPlay.put("hls_play_is_error", Boolean.valueOf(z));
    }

    public static boolean getHLSPlayEroor() {
        Object obj = hlsPlay.get("hls_play_is_error");
        if (obj != null) {
            return ((Boolean) obj).booleanValue();
        }
        return false;
    }

    public static void clear() {
        if (getKeyPtr().longValue() != 0) {
            BCrypto.releaseKey(getKeyPtr().longValue());
        }
        hlsPlay.clear();
    }
}
