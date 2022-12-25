package razerdp.util.log;

import android.text.TextUtils;
import android.util.Log;
import com.zzz.ipfssdk.callback.exception.CodeState;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes4.dex */
public class PopupLog {
    private static final boolean[] mIsDebugMode = {false};

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public enum LogMethod {
        i,
        d,
        w,
        e,
        v
    }

    private static void logInternal(LogMethod logMethod, String str, Object... objArr) {
        if (!isOpenLog()) {
            return;
        }
        try {
            String content = getContent(objArr);
            if (content.length() <= 4000) {
                logMethod(logMethod, str, content);
                return;
            }
            while (content.length() > 4000) {
                content = content.replace(content.substring(0, CodeState.CODES.CODE_PLAYER_PLAY_ERROR), "");
                logMethod(logMethod, str, content);
            }
            logMethod(logMethod, str, content);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: razerdp.util.log.PopupLog$1 */
    /* loaded from: classes4.dex */
    public static /* synthetic */ class C55601 {
        static final /* synthetic */ int[] $SwitchMap$razerdp$util$log$PopupLog$LogMethod = new int[LogMethod.values().length];

        static {
            try {
                $SwitchMap$razerdp$util$log$PopupLog$LogMethod[LogMethod.d.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$razerdp$util$log$PopupLog$LogMethod[LogMethod.e.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$razerdp$util$log$PopupLog$LogMethod[LogMethod.i.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$razerdp$util$log$PopupLog$LogMethod[LogMethod.v.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$razerdp$util$log$PopupLog$LogMethod[LogMethod.w.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
        }
    }

    private static void logMethod(LogMethod logMethod, String str, String str2) {
        if (!isOpenLog()) {
            return;
        }
        int i = C55601.$SwitchMap$razerdp$util$log$PopupLog$LogMethod[logMethod.ordinal()];
        if (i == 1) {
            Log.d(str, str2);
        } else if (i == 2) {
            Log.e(str, str2);
        } else if (i == 3) {
            Log.i(str, str2);
        } else if (i == 4) {
            Log.v(str, str2);
        } else if (i == 5) {
            Log.w(str, str2);
        } else {
            Log.i(str, str2);
        }
    }

    private static String getContent(Object... objArr) {
        return wrapLogWithMethodLocation(LogPrinterParser.parseContent(objArr));
    }

    private static String wrapLogWithMethodLocation(String str) {
        int i;
        String str2;
        StackTraceElement currentStackTrace = getCurrentStackTrace();
        String str3 = "unknow";
        if (currentStackTrace != null) {
            str3 = currentStackTrace.getFileName();
            str2 = currentStackTrace.getMethodName();
            i = currentStackTrace.getLineNumber();
        } else {
            i = -1;
            str2 = str3;
        }
        StringBuilder sb = new StringBuilder();
        String wrapJson = wrapJson(str);
        sb.append("  (");
        sb.append(str3);
        sb.append(":");
        sb.append(i);
        sb.append(") #");
        sb.append(str2);
        sb.append("：");
        sb.append('\n');
        sb.append(wrapJson);
        return sb.toString();
    }

    private static StackTraceElement getCurrentStackTrace() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        int stackOffset = getStackOffset(stackTrace, PopupLog.class);
        if (stackOffset == -1 && (stackOffset = getStackOffset(stackTrace, Logger.class)) == -1 && (stackOffset = getStackOffset(stackTrace, Log.class)) == -1) {
            return null;
        }
        return stackTrace[stackOffset];
    }

    private static int getStackOffset(StackTraceElement[] stackTraceElementArr, Class cls) {
        int i = -1;
        for (int i2 = 0; i2 < stackTraceElementArr.length; i2++) {
            if (TextUtils.equals(stackTraceElementArr[i2].getClassName(), cls.getName())) {
                i = i2;
            } else if (i > -1) {
                break;
            }
        }
        if (i != -1) {
            int i3 = i + 1;
            return i3 >= stackTraceElementArr.length ? stackTraceElementArr.length - 1 : i3;
        }
        return i;
    }

    public static String wrapJson(String str) {
        if (TextUtils.isEmpty(str)) {
            return "json为空";
        }
        try {
            if (str.startsWith("{")) {
                str = "\n================JSON================\n" + new JSONObject(str).toString(2) + "\n================JSON================\n";
            } else if (str.startsWith("[")) {
                str = "\n================JSONARRAY================\n" + new JSONArray(str).toString(4) + "\n================JSONARRAY================\n";
            }
        } catch (JSONException unused) {
        }
        return str;
    }

    public static void setOpenLog(boolean z) {
        mIsDebugMode[0] = z;
    }

    public static boolean isOpenLog() {
        return mIsDebugMode[0];
    }

    /* renamed from: i */
    public static void m26i(Object obj) {
        m25i("BasePopup", obj);
    }

    /* renamed from: i */
    public static void m25i(String str, Object... objArr) {
        logInternal(LogMethod.i, str, objArr);
    }

    /* renamed from: e */
    public static void m27e(String str, Object... objArr) {
        logInternal(LogMethod.e, str, objArr);
    }
}
