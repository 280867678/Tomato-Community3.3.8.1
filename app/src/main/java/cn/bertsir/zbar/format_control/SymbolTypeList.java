package cn.bertsir.zbar.format_control;

import com.gen.p059mh.webapps.utils.Logger;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class SymbolTypeList {
    public final int NONE = 0;
    public final int PARTIAL = 1;
    public final int EAN8 = 8;
    public final int UPCE = 9;
    public final int ISBN10 = 10;
    public final int UPCA = 12;
    public final int EAN13 = 13;
    public final int ISBN13 = 14;
    public final int I25 = 25;
    public final int DATABAR = 34;
    public final int DATABAR_EXP = 35;
    public final int CODABAR = 38;
    public final int CODE39 = 39;
    public final int PDF417 = 57;
    public final int QRCODE = 64;
    public final int CODE93 = 93;
    public final int CODE128 = 128;

    public static Map<String, String> getDeclaredFieldNameList() throws IllegalAccessException {
        Field[] declaredFields;
        HashMap hashMap = new HashMap();
        SymbolTypeList symbolTypeList = new SymbolTypeList();
        hashMap.clear();
        for (Field field : SymbolTypeList.class.getDeclaredFields()) {
            field.setAccessible(true);
            String valueOf = String.valueOf(field.getInt(symbolTypeList));
            hashMap.put(valueOf, field.getName());
            Logger.m4112i("CZ", "变量名称为（首字母大写）：" + field.getName() + "," + valueOf);
        }
        return hashMap;
    }
}
