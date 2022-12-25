package razerdp.util.log;

import android.view.MotionEvent;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Map;

/* loaded from: classes4.dex */
class LogPrinterParser {
    /* JADX INFO: Access modifiers changed from: package-private */
    public static String parseContent(Object... objArr) {
        StringBuilder sb = new StringBuilder();
        if (objArr != null) {
            if (objArr.length > 1) {
                sb.append(" {  ");
            }
            int i = 0;
            for (Object obj : objArr) {
                sb.append("params【");
                sb.append(i);
                sb.append("】");
                sb.append(" = ");
                sb.append(parseContentInternal(obj));
                if (i < objArr.length - 1) {
                    sb.append(" , ");
                }
                i++;
            }
            if (objArr.length > 1) {
                sb.append("  }");
            }
        }
        return sb.toString();
    }

    private static String parseContentInternal(Object obj) {
        if (obj instanceof String) {
            return (String) obj;
        }
        if (obj instanceof Throwable) {
            return fromThrowable((Throwable) obj);
        }
        if (obj instanceof List) {
            return fromList((List) obj);
        }
        if (obj instanceof Map) {
            return fromMap((Map) obj);
        }
        if (obj instanceof MotionEvent) {
            return fromMotionEvent((MotionEvent) obj);
        }
        return String.valueOf(obj);
    }

    private static String fromMotionEvent(MotionEvent motionEvent) {
        return actionToString(motionEvent.getAction());
    }

    public static String actionToString(int i) {
        switch (i) {
            case 0:
                return "ACTION_DOWN";
            case 1:
                return "ACTION_UP";
            case 2:
                return "ACTION_MOVE";
            case 3:
                return "ACTION_CANCEL";
            case 4:
                return "ACTION_OUTSIDE";
            case 5:
            case 6:
            default:
                int i2 = (65280 & i) >> 8;
                int i3 = i & 255;
                if (i3 == 5) {
                    return "ACTION_POINTER_DOWN(" + i2 + ")";
                } else if (i3 == 6) {
                    return "ACTION_POINTER_UP(" + i2 + ")";
                } else {
                    return Integer.toString(i);
                }
            case 7:
                return "ACTION_HOVER_MOVE";
            case 8:
                return "ACTION_SCROLL";
            case 9:
                return "ACTION_HOVER_ENTER";
            case 10:
                return "ACTION_HOVER_EXIT";
            case 11:
                return "ACTION_BUTTON_PRESS";
            case 12:
                return "ACTION_BUTTON_RELEASE";
        }
    }

    static String fromThrowable(Throwable th) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        th.printStackTrace(printWriter);
        for (Throwable cause = th.getCause(); cause != null; cause = cause.getCause()) {
            cause.printStackTrace(printWriter);
        }
        String obj = stringWriter.toString();
        printWriter.close();
        return obj;
    }

    static String fromMap(Map map) {
        if (map == null) {
            return "map is null";
        }
        if (map.isEmpty()) {
            return "map is empty";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("{");
        sb.append("\n");
        sb.append("\t");
        for (Map.Entry entry : map.entrySet()) {
            sb.append(String.format("\t%1$s : %2$s", String.valueOf(entry.getKey()), String.valueOf(entry.getValue())));
            sb.append("\n");
        }
        sb.append("}");
        return sb.toString();
    }

    static String fromList(List list) {
        if (list == null) {
            return "list is null";
        }
        if (list.isEmpty()) {
            return "list is empty";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("\n");
        sb.append("{\n ");
        for (Object obj : list) {
            if (obj instanceof List) {
                sb.append(fromList((List) obj));
            } else {
                sb.append(String.valueOf(obj));
                sb.append(" ,\n ");
            }
        }
        sb.append("}");
        return sb.toString();
    }
}
