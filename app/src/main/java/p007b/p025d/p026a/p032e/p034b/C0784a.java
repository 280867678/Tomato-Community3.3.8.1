package p007b.p025d.p026a.p032e.p034b;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import com.zzz.ipfssdk.util.net.NetworkType;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;

/* renamed from: b.d.a.e.b.a */
/* loaded from: classes2.dex */
public class C0784a {
    static {
        new DecimalFormat("#.##");
    }

    /* renamed from: a */
    public static int m5028a(int i) {
        int i2 = -101;
        if (i != -101) {
            i2 = -1;
            if (i != -1) {
                switch (i) {
                    case 1:
                    case 2:
                    case 4:
                    case 7:
                    case 11:
                        return 1;
                    case 3:
                    case 5:
                    case 6:
                    case 8:
                    case 9:
                    case 10:
                    case 12:
                    case 14:
                    case 15:
                        return 2;
                    case 13:
                        return 3;
                    default:
                        return 0;
                }
            }
        }
        return i2;
    }

    /* renamed from: a */
    public static NetworkType m5027a(Context context) {
        int m5026b = m5026b(context);
        NetworkType networkType = NetworkType.NETWORK_UNKNOWN;
        return m5026b != -101 ? m5026b != -1 ? m5026b != 0 ? m5026b != 1 ? m5026b != 2 ? m5026b != 3 ? networkType : NetworkType.NETWORK_4G : NetworkType.NETWORK_3G : NetworkType.NETWORK_2G : networkType : NetworkType.NETWORK_NO : NetworkType.NETWORK_WIFI;
    }

    /* renamed from: b */
    public static int m5026b(Context context) {
        int i;
        NetworkInfo activeNetworkInfo;
        Context context2 = (Context) new WeakReference(context).get();
        try {
            activeNetworkInfo = ((ConnectivityManager) context2.getSystemService("connectivity")).getActiveNetworkInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (activeNetworkInfo == null || !activeNetworkInfo.isAvailable() || !activeNetworkInfo.isConnected()) {
            i = -1;
        } else {
            int type = activeNetworkInfo.getType();
            if (type == 1) {
                i = -101;
            } else {
                if (type == 0) {
                    i = ((TelephonyManager) context2.getSystemService("phone")).getNetworkType();
                }
                i = 0;
            }
        }
        return m5028a(i);
    }
}
