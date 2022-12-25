package cn.bertsir.zbar.format_control;

import com.gen.p059mh.webapps.utils.Logger;
import java.util.Map;

/* loaded from: classes2.dex */
public class BarCodeUtil {
    private static final String TAG = "BarCodeUtil";
    private static Map<String, String> declaredFieldNameList;

    static {
        try {
            declaredFieldNameList = SymbolTypeList.getDeclaredFieldNameList();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static String getScanTypeNameFromZXingBarCodeFormat(com.google.zxing.BarcodeFormat barcodeFormat) {
        return String.valueOf(barcodeFormat);
    }

    public static String getScanTypeNameFromSymbolType(int i) {
        Logger.m4112i(TAG, "getScanTypeNameFromSymbolType: " + i);
        return declaredFieldNameList.get(String.valueOf(i));
    }
}
