package cn.bertsir.zbar.format_control;

import java.io.Serializable;

/* loaded from: classes2.dex */
public enum BarcodeType implements Serializable {
    ALL,
    BARCODE,
    QRCODE,
    ONLY_QR_CODE,
    ONLY_CODE_128,
    ONLY_EAN_13,
    HIGH_FREQUENCY,
    CUSTOM
}
