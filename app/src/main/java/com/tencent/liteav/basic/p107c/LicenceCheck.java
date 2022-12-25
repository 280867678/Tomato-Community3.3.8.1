package com.tencent.liteav.basic.p107c;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.Process;
import android.text.TextUtils;
import android.util.Base64;
import com.tencent.liteav.basic.datareport.TXCDRApi;
import com.tencent.liteav.basic.datareport.TXCDRDef;
import com.tencent.liteav.basic.log.TXCLog;
import com.tencent.liteav.basic.util.FileUtil;
import com.tencent.liteav.basic.util.TXCCommonUtil;
import com.tomatolive.library.utils.LogConstants;
import java.io.File;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.tencent.liteav.basic.c.e */
/* loaded from: classes3.dex */
public class LicenceCheck {

    /* renamed from: l */
    private static LicenceCheck f2380l;

    /* renamed from: a */
    private Context f2381a;

    /* renamed from: b */
    private String f2382b;

    /* renamed from: c */
    private String f2383c;

    /* renamed from: h */
    private String f2388h;

    /* renamed from: i */
    private String f2389i;

    /* renamed from: j */
    private String f2390j;

    /* renamed from: d */
    private String f2384d = "TXUgcSDK.licence";

    /* renamed from: e */
    private String f2385e = "tmp.licence";

    /* renamed from: f */
    private String f2386f = "YTFaceSDK.licence";

    /* renamed from: g */
    private String f2387g = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAq4teqkW/TUruU89ElNVd\nKrpSL+HCITruyb6BS9mW6M4mqmxDhazDmQgMKNfsA0d2kxFucCsXTyesFNajaisk\nrAzVJpNGO75bQFap4jYzJYskIuas6fgIS7zSmGXgRcp6i0ZBH3pkVCXcgfLfsVCO\n+sN01jFhFgOC0LY2f1pJ+3jqktAlMIxy8Q9t7XwwL5/n8/Sledp7TwuRdnl2OPl3\nycCTRkXtOIoRNB9vgd9XooTKiEdCXC7W9ryvtwCiAB82vEfHWXXgzhsPC13URuFy\n1JqbWJtTCCcfsCVxuBplhVJAQ7JsF5SMntdJDkp7rJLhprgsaim2CRjcVseNmw97\nbwIDAQAB";

    /* renamed from: k */
    private boolean f2391k = false;

    /* renamed from: m */
    private boolean f2392m = false;

    /* renamed from: n */
    private int f2393n = -1;

    /* renamed from: a */
    public static LicenceCheck m3120a() {
        if (f2380l == null) {
            f2380l = new LicenceCheck();
        }
        return f2380l;
    }

    private LicenceCheck() {
    }

    /* renamed from: a */
    public void m3117a(Context context, String str, String str2) {
        this.f2381a = context.getApplicationContext();
        this.f2382b = str;
        this.f2383c = str2;
        if (this.f2381a == null || !m3097d()) {
            return;
        }
        this.f2388h = this.f2381a.getExternalFilesDir(null).getAbsolutePath();
        if (m3103b(this.f2388h + File.separator + this.f2384d)) {
            return;
        }
        TXCLog.m2915d("LicenceCheck", "setLicense, sdcard file not exist, to download");
        m3107b();
    }

    /* renamed from: b */
    public void m3107b() {
        if (TextUtils.isEmpty(this.f2382b)) {
            TXCLog.m2914e("LicenceCheck", "downloadLicense, mUrl is empty, ignore!");
        } else if (this.f2391k) {
            TXCLog.m2913i("LicenceCheck", "downloadLicense, in downloading, ignore");
        } else {
            HttpFileListener httpFileListener = new HttpFileListener() { // from class: com.tencent.liteav.basic.c.e.1
                @Override // com.tencent.liteav.basic.p107c.HttpFileListener
                /* renamed from: a */
                public void mo3088a(File file) {
                    TXCLog.m2913i("LicenceCheck", "downloadLicense, onSaveSuccess");
                    String m3091h = LicenceCheck.this.m3091h();
                    if (!TextUtils.isEmpty(m3091h)) {
                        if (LicenceCheck.this.m3102b(m3091h, (LicenceInfo) null) != 0) {
                            return;
                        }
                        LicenceCheck.this.m3093f();
                        return;
                    }
                    TXCLog.m2914e("LicenceCheck", "downloadLicense, readDownloadTempLicence is empty!");
                    LicenceCheck.this.f2391k = false;
                }

                @Override // com.tencent.liteav.basic.p107c.HttpFileListener
                /* renamed from: a */
                public void mo3087a(File file, Exception exc) {
                    TXCLog.m2913i("LicenceCheck", "downloadLicense, onSaveFailed");
                }

                @Override // com.tencent.liteav.basic.p107c.HttpFileListener
                /* renamed from: a */
                public void mo3089a(int i) {
                    TXCLog.m2913i("LicenceCheck", "downloadLicense, onProgressUpdate");
                }

                @Override // com.tencent.liteav.basic.p107c.HttpFileListener
                /* renamed from: a */
                public void mo3090a() {
                    TXCLog.m2913i("LicenceCheck", "downloadLicense, onProcessEnd");
                    LicenceCheck.this.f2391k = false;
                }
            };
            Context context = this.f2381a;
            if (context == null) {
                TXCLog.m2914e("LicenceCheck", "context is NULL !!! Please set context in method:setLicense(Context context, String url, String key)");
                return;
            }
            File externalFilesDir = context.getExternalFilesDir(null);
            if (externalFilesDir == null) {
                TXCLog.m2914e("LicenceCheck", "Please check permission WRITE_EXTERNAL_STORAGE permission has been set !!!");
                return;
            }
            this.f2388h = externalFilesDir.getAbsolutePath();
            new Thread(new HttpFileUtil(this.f2381a, this.f2382b, this.f2388h, this.f2385e, httpFileListener, false)).start();
            this.f2391k = true;
        }
    }

    /* renamed from: a */
    public int m3112a(LicenceInfo licenceInfo, Context context) {
        int m3104b = m3104b(licenceInfo, context);
        if (m3104b != 0) {
            m3107b();
        }
        return m3104b;
    }

    /* renamed from: b */
    private int m3104b(LicenceInfo licenceInfo, Context context) {
        if (this.f2392m) {
            return 0;
        }
        if (this.f2381a == null) {
            this.f2381a = context;
        }
        if (m3105b(licenceInfo) == 0) {
            this.f2392m = true;
            return 0;
        }
        int m3113a = m3113a(licenceInfo);
        if (m3113a != 0) {
            return m3113a;
        }
        this.f2392m = true;
        return 0;
    }

    /* renamed from: a */
    private int m3113a(LicenceInfo licenceInfo) {
        File externalFilesDir;
        if (!Environment.getExternalStorageState().equals("mounted")) {
            TXCLog.m2914e("LicenceCheck", "checkSdcardLicence, sdcard not mounted yet!");
            return -10;
        }
        if (this.f2381a.getExternalFilesDir(null) == null) {
            TXCLog.m2914e("LicenceCheck", "checkSdcardLicence, mContext.getExternalFilesDir is null!");
            return -10;
        }
        String str = externalFilesDir.getAbsolutePath() + File.separator + this.f2384d;
        if (!m3103b(str)) {
            return -7;
        }
        String m2895b = FileUtil.m2895b(str);
        if (TextUtils.isEmpty(m2895b)) {
            TXCLog.m2914e("LicenceCheck", "checkSdcardLicence, licenceSdcardStr is empty");
            return -8;
        }
        return m3110a(m2895b, licenceInfo);
    }

    /* renamed from: b */
    private int m3105b(LicenceInfo licenceInfo) {
        if (!m3095e()) {
            return -6;
        }
        String m2896b = FileUtil.m2896b(this.f2381a, this.f2384d);
        if (TextUtils.isEmpty(m2896b)) {
            TXCLog.m2914e("LicenceCheck", "checkAssetLicence, licenceSdcardStr is empty");
            return -8;
        }
        return m3110a(m2896b, licenceInfo);
    }

    /* renamed from: a */
    public int m3110a(String str, LicenceInfo licenceInfo) {
        try {
            new JSONObject(str);
            return m3102b(str, licenceInfo);
        } catch (JSONException unused) {
            return m3098c(str, licenceInfo);
        }
    }

    /* renamed from: d */
    private boolean m3097d() {
        if (!Environment.getExternalStorageState().equals("mounted")) {
            TXCLog.m2914e("LicenceCheck", "checkSdcardLicence, sdcard not mounted yet!");
            return false;
        } else if (this.f2381a.getExternalFilesDir(null) != null) {
            return true;
        } else {
            TXCLog.m2914e("LicenceCheck", "checkSdcardLicence, mContext.getExternalFilesDir is null!");
            return false;
        }
    }

    /* renamed from: e */
    private boolean m3095e() {
        return FileUtil.m2900a(this.f2381a, this.f2384d);
    }

    /* renamed from: b */
    private boolean m3103b(String str) {
        return FileUtil.m2898a(str);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: f */
    public void m3093f() {
        File file = new File(this.f2381a.getExternalFilesDir(null).getAbsolutePath() + File.separator + this.f2384d);
        if (file.exists()) {
            boolean delete = file.delete();
            TXCLog.m2913i("LicenceCheck", "delete dst file:" + delete);
        }
        File file2 = new File(this.f2388h + File.separator + this.f2385e);
        if (file2.exists()) {
            boolean renameTo = file2.renameTo(file);
            TXCLog.m2913i("LicenceCheck", "rename file:" + renameTo);
        }
        this.f2392m = true;
    }

    /* renamed from: c */
    private static long m3099c(String str) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(str).getTime();
        } catch (Exception e) {
            e.printStackTrace();
            return -1L;
        }
    }

    /* renamed from: a */
    public PublicKey m3111a(String str) throws Exception {
        return KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(Base64.decode(str, 0)));
    }

    /* renamed from: g */
    private String m3092g() {
        if (TextUtils.isEmpty(this.f2383c)) {
            TXCLog.m2914e("LicenceCheck", "decodeLicence, mKey is empty!!!");
            return "";
        }
        byte[] bytes = this.f2383c.getBytes();
        SecretKeySpec secretKeySpec = new SecretKeySpec(bytes, "AES");
        byte[] bArr = new byte[16];
        for (int i = 0; i < bytes.length && i < bArr.length; i++) {
            bArr[i] = bytes[i];
        }
        IvParameterSpec ivParameterSpec = new IvParameterSpec(bArr);
        byte[] decode = Base64.decode(this.f2389i, 0);
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(2, secretKeySpec, ivParameterSpec);
            String str = new String(cipher.doFinal(decode), "UTF-8");
            TXCLog.m2913i("LicenceCheck", "decodeLicence : " + str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: b */
    public int m3102b(String str, LicenceInfo licenceInfo) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            int optInt = jSONObject.optInt(LogConstants.APP_ID);
            this.f2389i = jSONObject.getString("encryptedLicense");
            this.f2390j = jSONObject.getString("signature");
            TXCLog.m2913i("LicenceCheck", "appid:" + optInt);
            TXCLog.m2913i("LicenceCheck", "encryptedLicense:" + this.f2389i);
            TXCLog.m2913i("LicenceCheck", "signature:" + this.f2390j);
            return m3100c(licenceInfo);
        } catch (JSONException e) {
            this.f2389i = null;
            this.f2390j = null;
            e.printStackTrace();
            m3119a(-1);
            return -1;
        }
    }

    /* renamed from: c */
    private int m3100c(LicenceInfo licenceInfo) {
        boolean z;
        boolean z2;
        try {
            z = m3108a(Base64.decode(this.f2389i, 0), Base64.decode(this.f2390j, 0), m3111a(this.f2387g));
        } catch (Exception e) {
            e.printStackTrace();
            TXCLog.m2914e("LicenceCheck", "verifyLicence, exception is : " + e);
            z = false;
        }
        if (!z) {
            m3119a(-2);
            TXCLog.m2914e("LicenceCheck", "verifyLicence, signature not pass!");
            return -2;
        }
        String m3092g = m3092g();
        if (TextUtils.isEmpty(m3092g)) {
            m3119a(-3);
            TXCLog.m2914e("LicenceCheck", "verifyLicence, decodeValue is empty!");
            return -3;
        }
        if (licenceInfo != null) {
            licenceInfo.f2395a = m3092g;
        }
        try {
            JSONObject jSONObject = new JSONObject(m3092g);
            String string = jSONObject.getString("pituLicense");
            JSONArray optJSONArray = jSONObject.optJSONArray("appData");
            if (optJSONArray == null) {
                TXCLog.m2914e("LicenceCheck", "verifyLicence, appDataArray is null!");
                m3119a(-1);
                return -1;
            }
            int i = 0;
            boolean z3 = false;
            while (true) {
                z2 = true;
                if (i >= optJSONArray.length()) {
                    z2 = false;
                    break;
                }
                JSONObject jSONObject2 = optJSONArray.getJSONObject(i);
                String optString = jSONObject2.optString("packageName");
                TXCLog.m2913i("LicenceCheck", "verifyLicence, packageName:" + optString);
                if (!optString.equals(this.f2381a.getPackageName())) {
                    TXCLog.m2914e("LicenceCheck", "verifyLicence, packageName not match!");
                    z3 = false;
                } else if (!m3096d(jSONObject2.optString("endDate"))) {
                    m3109a(jSONObject2, string);
                    z3 = true;
                    break;
                } else {
                    z3 = true;
                }
                i++;
            }
            if (!z3) {
                m3119a(-4);
                return -4;
            } else if (!z2) {
                m3119a(-5);
                return -5;
            } else {
                if (!TextUtils.isEmpty(string)) {
                    byte[] decode = Base64.decode(string, 0);
                    String absolutePath = this.f2381a.getExternalFilesDir(null).getAbsolutePath();
                    File file = new File(absolutePath + File.separator + this.f2386f);
                    FileUtil.m2897a(file.getAbsolutePath(), decode);
                    TXCCommonUtil.setPituLicencePath(file.getAbsolutePath());
                }
                TXCDRApi.txReportDAU(this.f2381a, TXCDRDef.f2432aI);
                return 0;
            }
        } catch (JSONException e2) {
            e2.printStackTrace();
            TXCLog.m2914e("LicenceCheck", "verifyLicence, json format error ! exception = " + e2);
            m3119a(-1);
            return -1;
        }
    }

    /* renamed from: a */
    private void m3109a(JSONObject jSONObject, String str) {
        int optInt = jSONObject.optInt("feature");
        if (optInt <= 1) {
            if (!TextUtils.isEmpty(str)) {
                this.f2393n = 5;
            } else {
                this.f2393n = 3;
            }
            TXCLog.m2913i("LicenceCheck", "parseVersionType, licence is old, mLicenceVersionType = " + this.f2393n);
            return;
        }
        this.f2393n = optInt & 15;
        TXCLog.m2913i("LicenceCheck", "parseVersionType, mLicenceVersionType = " + this.f2393n);
    }

    /* renamed from: c */
    public int m3101c() {
        return this.f2393n;
    }

    /* renamed from: a */
    private void m3119a(int i) {
        TXCDRApi.txReportDAU(this.f2381a, TXCDRDef.f2433aJ, i, "");
    }

    /* renamed from: d */
    private boolean m3096d(String str) {
        long m3099c = m3099c(str);
        if (m3099c < 0) {
            TXCLog.m2914e("LicenceCheck", "checkEndDate, end date millis < 0!");
            return true;
        } else if (m3099c >= System.currentTimeMillis()) {
            return false;
        } else {
            TXCLog.m2914e("LicenceCheck", "checkEndDate, end date expire!");
            return true;
        }
    }

    /* renamed from: c */
    private int m3098c(String str, LicenceInfo licenceInfo) {
        String m3094e = m3094e(str);
        if (TextUtils.isEmpty(m3094e)) {
            TXCLog.m2914e("LicenceCheck", "verifyOldLicence, decryptStr is empty");
            return -3;
        }
        if (licenceInfo != null) {
            licenceInfo.f2395a = m3094e;
        }
        try {
            JSONObject jSONObject = new JSONObject(m3094e);
            if (!jSONObject.getString("packagename").equals(m3118a(this.f2381a))) {
                TXCLog.m2914e("LicenceCheck", "packagename not match!");
                m3119a(-4);
                return -4;
            } else if (m3096d(jSONObject.getString("enddate"))) {
                return -5;
            } else {
                this.f2393n = 5;
                TXCDRApi.txReportDAU(this.f2381a, TXCDRDef.f2432aI);
                return 0;
            }
        } catch (JSONException e) {
            e.printStackTrace();
            TXCLog.m2914e("LicenceCheck", "verifyOldLicence, json format error !");
            m3119a(-1);
            return -1;
        }
    }

    /* renamed from: a */
    private static String m3118a(Context context) {
        int myPid = Process.myPid();
        for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : ((ActivityManager) context.getSystemService("activity")).getRunningAppProcesses()) {
            if (runningAppProcessInfo.pid == myPid) {
                return runningAppProcessInfo.processName;
            }
        }
        return "";
    }

    /* renamed from: e */
    private String m3094e(String str) {
        try {
            return new String(RSAUtils.m3085b(Base64.decode(str, 0), Base64.decode("MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAKfMXaF6wx9lev2U\nIzkk6ydI2sdaSQAD2ZvDBLq+5Fm6nGwSSWawl03D4vHcWIUa3wnz6f19/y8wzrj4\nnTfcEnT94SPdB6GhGsqPwbwRp9MHAqd/2gWZxSb005il2yiOZafk6X4NGKCn2tGd\nyNaCF+m9rLykuLdZHB0Z53ivgseNAgMBAAECgYAvXI2pAH+Goxwd6uwuOu9svTGT\nRzaHnI6VWmxBUZQeh3+TOW4iYAG03291GN6bY0RFCOWouSGH7lzK9NFbbPCAQ/hx\ncO48PqioHoq7K8sqzd3XaYBv39HrRnM8JvZsqv0PLJwX/LGm2y/MRaKAC6bcHtse\npgh+NNmUxXNRcTMRAQJBANezmenBcR8HTcY5YaEk3SQRzOo+QhIXuuD4T/FESpVJ\nmVQGxJjLsEBua1j38WG2QuepE5JiVbkQ0jQSvhUiZK0CQQDHJa+vWu6l72lQAvIx\nwmRISorvLb/tnu5bH0Ele42oX+w4p/tm03awdVjhVANnpDjYS2H6EzrF/pfis7k9\nV2phAkB4E4gz47bYYhV+qsTZkw70HGCpab0YG1OyFylRkwW983nCl/3rXUChrZZe\nsbATCAZYtfuqOsmju2R5DpH4a+wFAkBmHlcWbmSNxlSUaM5U4b+WqlLQDv+qE6Na\nKo63b8HWI0n4S3tI4QqttZ7b/L66OKXFk/Ir0AyFVuX/o/VLFTZBAkAdSTEkGwE5\nGQmhxu95sKxmdlUY6Q0Gwwpi06C1BPBrj2VkGXpBP0twhPVAq/3xVjjb+2KXVTUW\nIpRLc06M4vhv", 0)));
        } catch (Exception e) {
            e.printStackTrace();
            TXCLog.m2914e("LicenceCheck", "decryptLicenceStr, exception is : " + e);
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: h */
    public String m3091h() {
        return FileUtil.m2895b(new File(this.f2388h + File.separator + this.f2385e).getAbsolutePath());
    }

    /* renamed from: a */
    public static boolean m3108a(byte[] bArr, byte[] bArr2, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signature = Signature.getInstance("SHA256WithRSA");
        signature.initVerify(publicKey);
        signature.update(bArr);
        return signature.verify(bArr2);
    }
}
