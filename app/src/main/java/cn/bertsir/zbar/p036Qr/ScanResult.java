package cn.bertsir.zbar.p036Qr;

/* renamed from: cn.bertsir.zbar.Qr.ScanResult */
/* loaded from: classes2.dex */
public class ScanResult {
    public String result;
    public String scanType;

    public String getResult() {
        return this.result;
    }

    public void setResult(String str) {
        this.result = str;
    }

    public String getScanType() {
        return this.scanType;
    }

    public void setScanType(String str) {
        this.scanType = str;
    }

    public String toString() {
        return "ScanResult{result='" + this.result + "', scanType='" + this.scanType + "'}";
    }
}
