package com.king.zxing;

import com.google.zxing.BarcodeFormat;
import java.util.Collection;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/* loaded from: classes3.dex */
public final class DecodeFormatManager {
    public static final Set<BarcodeFormat> QR_CODE_FORMATS = EnumSet.of(BarcodeFormat.QR_CODE);
    public static final Set<BarcodeFormat> DATA_MATRIX_FORMATS = EnumSet.of(BarcodeFormat.DATA_MATRIX);
    public static final Set<BarcodeFormat> AZTEC_FORMATS = EnumSet.of(BarcodeFormat.AZTEC);
    public static final Set<BarcodeFormat> PDF417_FORMATS = EnumSet.of(BarcodeFormat.PDF_417);
    public static final Set<BarcodeFormat> PRODUCT_FORMATS = EnumSet.of(BarcodeFormat.UPC_A, BarcodeFormat.UPC_E, BarcodeFormat.EAN_13, BarcodeFormat.EAN_8, BarcodeFormat.RSS_14, BarcodeFormat.RSS_EXPANDED);
    public static final Set<BarcodeFormat> INDUSTRIAL_FORMATS = EnumSet.of(BarcodeFormat.CODE_39, BarcodeFormat.CODE_93, BarcodeFormat.CODE_128, BarcodeFormat.ITF, BarcodeFormat.CODABAR);
    public static final Set<BarcodeFormat> ONE_D_FORMATS = EnumSet.copyOf((Collection) PRODUCT_FORMATS);
    private static final Map<String, Set<BarcodeFormat>> FORMATS_FOR_MODE = new HashMap();

    static {
        Pattern.compile(",");
        ONE_D_FORMATS.addAll(INDUSTRIAL_FORMATS);
        FORMATS_FOR_MODE.put("ONE_D_MODE", ONE_D_FORMATS);
        FORMATS_FOR_MODE.put("PRODUCT_MODE", PRODUCT_FORMATS);
        FORMATS_FOR_MODE.put("QR_CODE_MODE", QR_CODE_FORMATS);
        FORMATS_FOR_MODE.put("DATA_MATRIX_MODE", DATA_MATRIX_FORMATS);
        FORMATS_FOR_MODE.put("AZTEC_MODE", AZTEC_FORMATS);
        FORMATS_FOR_MODE.put("PDF417_MODE", PDF417_FORMATS);
    }
}
