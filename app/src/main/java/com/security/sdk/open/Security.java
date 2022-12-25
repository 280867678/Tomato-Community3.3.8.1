package com.security.sdk.open;

import android.content.Context;
import android.util.Log;
import com.security.sdk.p094b.AbstractC3081b;
import com.security.sdk.p094b.C3080a;
import java.io.File;

/* loaded from: classes3.dex */
public class Security {
    private static Security mInstance = null;
    private static String secInfo = "";
    public static boolean soLoadSucc;
    private Context mContext;
    private String soPath;

    private Security(Context context) {
        this.mContext = context;
        secInfo = new SafeInfo().getSafeInfo(context);
        loadSo();
        new C3080a(this.mContext, this.soPath, new AbstractC3081b() { // from class: com.security.sdk.open.Security.1
            @Override // com.security.sdk.p094b.AbstractC3081b
            public void onSuccess() {
                try {
                    Security.this.loadSo();
                } catch (Exception unused) {
                    throw new InitException("初始化失败，请检查网络");
                }
            }
        }).m3700b();
    }

    public static Security getInstance() {
        Security security = mInstance;
        if (security != null) {
            return security;
        }
        throw new InitException("请在Application中调用初始化接口，如果已经调用，请检查网络");
    }

    private static String getSecurityInfo() {
        return secInfo;
    }

    public static void init(Context context) {
        if (mInstance == null) {
            synchronized (Security.class) {
                if (mInstance == null) {
                    mInstance = new Security(context);
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void loadSo() {
        File file = new File(this.mContext.getDir("libs", 0), "libsec-ch.so");
        this.soPath = file.getAbsolutePath();
        try {
            if (file.exists()) {
                Log.i("mmm", "soFile exists:" + this.soPath);
                System.load(this.soPath);
                soLoadSucc = true;
            } else {
                Log.i("mmm", "soFile: not exists2" + this.soPath);
                System.loadLibrary("sec-ch");
            }
        } catch (Exception e) {
            Log.i("mmm", "soFile: not exists2" + e.getMessage());
            soLoadSucc = false;
            System.loadLibrary("sec-ch");
        }
    }

    public native String getSec();
}
