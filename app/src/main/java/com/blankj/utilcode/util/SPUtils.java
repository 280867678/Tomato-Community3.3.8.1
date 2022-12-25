package com.blankj.utilcode.util;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import java.util.HashMap;
import java.util.Map;

@SuppressLint({"ApplySharedPref"})
/* loaded from: classes2.dex */
public final class SPUtils {
    private static final Map<String, SPUtils> SP_UTILS_MAP = new HashMap();

    /* renamed from: sp */
    private SharedPreferences f1195sp;

    public static SPUtils getInstance() {
        return getInstance("", 0);
    }

    public static SPUtils getInstance(String str) {
        return getInstance(str, 0);
    }

    public static SPUtils getInstance(String str, int i) {
        if (isSpace(str)) {
            str = "spUtils";
        }
        SPUtils sPUtils = SP_UTILS_MAP.get(str);
        if (sPUtils == null) {
            synchronized (SPUtils.class) {
                sPUtils = SP_UTILS_MAP.get(str);
                if (sPUtils == null) {
                    sPUtils = new SPUtils(str, i);
                    SP_UTILS_MAP.put(str, sPUtils);
                }
            }
        }
        return sPUtils;
    }

    private SPUtils(String str, int i) {
        this.f1195sp = Utils.getApp().getSharedPreferences(str, i);
    }

    public void put(@NonNull String str, String str2) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        put(str, str2, false);
    }

    public void put(@NonNull String str, String str2, boolean z) {
        if (str != null) {
            if (z) {
                this.f1195sp.edit().putString(str, str2).commit();
                return;
            } else {
                this.f1195sp.edit().putString(str, str2).apply();
                return;
            }
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
    }

    public String getString(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return getString(str, "");
    }

    public String getString(@NonNull String str, String str2) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return this.f1195sp.getString(str, str2);
    }

    public void put(@NonNull String str, int i) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        put(str, i, false);
    }

    public void put(@NonNull String str, int i, boolean z) {
        if (str != null) {
            if (z) {
                this.f1195sp.edit().putInt(str, i).commit();
                return;
            } else {
                this.f1195sp.edit().putInt(str, i).apply();
                return;
            }
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
    }

    public int getInt(@NonNull String str, int i) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return this.f1195sp.getInt(str, i);
    }

    public void put(@NonNull String str, long j) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        put(str, j, false);
    }

    public void put(@NonNull String str, long j, boolean z) {
        if (str != null) {
            if (z) {
                this.f1195sp.edit().putLong(str, j).commit();
                return;
            } else {
                this.f1195sp.edit().putLong(str, j).apply();
                return;
            }
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
    }

    public long getLong(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return getLong(str, -1L);
    }

    public long getLong(@NonNull String str, long j) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return this.f1195sp.getLong(str, j);
    }

    public void put(@NonNull String str, float f) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        put(str, f, false);
    }

    public void put(@NonNull String str, float f, boolean z) {
        if (str != null) {
            if (z) {
                this.f1195sp.edit().putFloat(str, f).commit();
                return;
            } else {
                this.f1195sp.edit().putFloat(str, f).apply();
                return;
            }
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
    }

    public float getFloat(@NonNull String str, float f) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return this.f1195sp.getFloat(str, f);
    }

    public void put(@NonNull String str, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        put(str, z, false);
    }

    public void put(@NonNull String str, boolean z, boolean z2) {
        if (str != null) {
            if (z2) {
                this.f1195sp.edit().putBoolean(str, z).commit();
                return;
            } else {
                this.f1195sp.edit().putBoolean(str, z).apply();
                return;
            }
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 3, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
    }

    public boolean getBoolean(@NonNull String str) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 1, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return getBoolean(str, false);
    }

    public boolean getBoolean(@NonNull String str, boolean z) {
        if (str == null) {
            throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
        }
        return this.f1195sp.getBoolean(str, z);
    }

    public void remove(@NonNull String str, boolean z) {
        if (str != null) {
            if (z) {
                this.f1195sp.edit().remove(str).commit();
                return;
            } else {
                this.f1195sp.edit().remove(str).apply();
                return;
            }
        }
        throw new NullPointerException("Argument 'key' of type String (#0 out of 2, zero-based) is marked by @android.support.annotation.NonNull but got null for it");
    }

    private static boolean isSpace(String str) {
        if (str == null) {
            return true;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }
}
