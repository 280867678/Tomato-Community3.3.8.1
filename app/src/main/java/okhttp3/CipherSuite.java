package okhttp3;

import android.support.p006v8.renderscript.ScriptIntrinsicBLAS;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* loaded from: classes4.dex */
public final class CipherSuite {
    final String javaName;
    static final Comparator<String> ORDER_BY_NAME = new Comparator<String>() { // from class: okhttp3.CipherSuite.1
        @Override // java.util.Comparator
        public int compare(String str, String str2) {
            int min = Math.min(str.length(), str2.length());
            for (int i = 4; i < min; i++) {
                char charAt = str.charAt(i);
                char charAt2 = str2.charAt(i);
                if (charAt != charAt2) {
                    return charAt < charAt2 ? -1 : 1;
                }
            }
            int length = str.length();
            int length2 = str2.length();
            if (length != length2) {
                return length < length2 ? -1 : 1;
            }
            return 0;
        }
    };
    private static final Map<String, CipherSuite> INSTANCES = new TreeMap(ORDER_BY_NAME);
    public static final CipherSuite TLS_RSA_WITH_3DES_EDE_CBC_SHA = m73of("SSL_RSA_WITH_3DES_EDE_CBC_SHA", 10);
    public static final CipherSuite TLS_RSA_WITH_AES_128_CBC_SHA = m73of("TLS_RSA_WITH_AES_128_CBC_SHA", 47);
    public static final CipherSuite TLS_RSA_WITH_AES_256_CBC_SHA = m73of("TLS_RSA_WITH_AES_256_CBC_SHA", 53);
    public static final CipherSuite TLS_RSA_WITH_AES_128_GCM_SHA256 = m73of("TLS_RSA_WITH_AES_128_GCM_SHA256", 156);
    public static final CipherSuite TLS_RSA_WITH_AES_256_GCM_SHA384 = m73of("TLS_RSA_WITH_AES_256_GCM_SHA384", 157);
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA = m73of("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", 49171);
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA = m73of("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", 49172);
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256 = m73of("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", 49195);
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384 = m73of("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", 49196);
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256 = m73of("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", 49199);
    public static final CipherSuite TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384 = m73of("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", 49200);
    public static final CipherSuite TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256 = m73of("TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", 52392);
    public static final CipherSuite TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256 = m73of("TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", 52393);

    static {
        m73of("SSL_RSA_WITH_NULL_MD5", 1);
        m73of("SSL_RSA_WITH_NULL_SHA", 2);
        m73of("SSL_RSA_EXPORT_WITH_RC4_40_MD5", 3);
        m73of("SSL_RSA_WITH_RC4_128_MD5", 4);
        m73of("SSL_RSA_WITH_RC4_128_SHA", 5);
        m73of("SSL_RSA_EXPORT_WITH_DES40_CBC_SHA", 8);
        m73of("SSL_RSA_WITH_DES_CBC_SHA", 9);
        m73of("SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA", 17);
        m73of("SSL_DHE_DSS_WITH_DES_CBC_SHA", 18);
        m73of("SSL_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19);
        m73of("SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA", 20);
        m73of("SSL_DHE_RSA_WITH_DES_CBC_SHA", 21);
        m73of("SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22);
        m73of("SSL_DH_anon_EXPORT_WITH_RC4_40_MD5", 23);
        m73of("SSL_DH_anon_WITH_RC4_128_MD5", 24);
        m73of("SSL_DH_anon_EXPORT_WITH_DES40_CBC_SHA", 25);
        m73of("SSL_DH_anon_WITH_DES_CBC_SHA", 26);
        m73of("SSL_DH_anon_WITH_3DES_EDE_CBC_SHA", 27);
        m73of("TLS_KRB5_WITH_DES_CBC_SHA", 30);
        m73of("TLS_KRB5_WITH_3DES_EDE_CBC_SHA", 31);
        m73of("TLS_KRB5_WITH_RC4_128_SHA", 32);
        m73of("TLS_KRB5_WITH_DES_CBC_MD5", 34);
        m73of("TLS_KRB5_WITH_3DES_EDE_CBC_MD5", 35);
        m73of("TLS_KRB5_WITH_RC4_128_MD5", 36);
        m73of("TLS_KRB5_EXPORT_WITH_DES_CBC_40_SHA", 38);
        m73of("TLS_KRB5_EXPORT_WITH_RC4_40_SHA", 40);
        m73of("TLS_KRB5_EXPORT_WITH_DES_CBC_40_MD5", 41);
        m73of("TLS_KRB5_EXPORT_WITH_RC4_40_MD5", 43);
        m73of("TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50);
        m73of("TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51);
        m73of("TLS_DH_anon_WITH_AES_128_CBC_SHA", 52);
        m73of("TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56);
        m73of("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57);
        m73of("TLS_DH_anon_WITH_AES_256_CBC_SHA", 58);
        m73of("TLS_RSA_WITH_NULL_SHA256", 59);
        m73of("TLS_RSA_WITH_AES_128_CBC_SHA256", 60);
        m73of("TLS_RSA_WITH_AES_256_CBC_SHA256", 61);
        m73of("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64);
        m73of("TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", 65);
        m73of("TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", 68);
        m73of("TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", 69);
        m73of("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", 103);
        m73of("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", 106);
        m73of("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", 107);
        m73of("TLS_DH_anon_WITH_AES_128_CBC_SHA256", 108);
        m73of("TLS_DH_anon_WITH_AES_256_CBC_SHA256", 109);
        m73of("TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", ScriptIntrinsicBLAS.UNIT);
        m73of("TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", 135);
        m73of("TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", 136);
        m73of("TLS_PSK_WITH_RC4_128_SHA", 138);
        m73of("TLS_PSK_WITH_3DES_EDE_CBC_SHA", 139);
        m73of("TLS_PSK_WITH_AES_128_CBC_SHA", 140);
        m73of("TLS_PSK_WITH_AES_256_CBC_SHA", ScriptIntrinsicBLAS.LEFT);
        m73of("TLS_RSA_WITH_SEED_CBC_SHA", 150);
        m73of("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", 158);
        m73of("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", 159);
        m73of("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", 162);
        m73of("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", 163);
        m73of("TLS_DH_anon_WITH_AES_128_GCM_SHA256", 166);
        m73of("TLS_DH_anon_WITH_AES_256_GCM_SHA384", 167);
        m73of("TLS_EMPTY_RENEGOTIATION_INFO_SCSV", 255);
        m73of("TLS_FALLBACK_SCSV", 22016);
        m73of("TLS_ECDH_ECDSA_WITH_NULL_SHA", 49153);
        m73of("TLS_ECDH_ECDSA_WITH_RC4_128_SHA", 49154);
        m73of("TLS_ECDH_ECDSA_WITH_3DES_EDE_CBC_SHA", 49155);
        m73of("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA", 49156);
        m73of("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA", 49157);
        m73of("TLS_ECDHE_ECDSA_WITH_NULL_SHA", 49158);
        m73of("TLS_ECDHE_ECDSA_WITH_RC4_128_SHA", 49159);
        m73of("TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", 49160);
        m73of("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", 49161);
        m73of("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", 49162);
        m73of("TLS_ECDH_RSA_WITH_NULL_SHA", 49163);
        m73of("TLS_ECDH_RSA_WITH_RC4_128_SHA", 49164);
        m73of("TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA", 49165);
        m73of("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA", 49166);
        m73of("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA", 49167);
        m73of("TLS_ECDHE_RSA_WITH_NULL_SHA", 49168);
        m73of("TLS_ECDHE_RSA_WITH_RC4_128_SHA", 49169);
        m73of("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", 49170);
        m73of("TLS_ECDH_anon_WITH_NULL_SHA", 49173);
        m73of("TLS_ECDH_anon_WITH_RC4_128_SHA", 49174);
        m73of("TLS_ECDH_anon_WITH_3DES_EDE_CBC_SHA", 49175);
        m73of("TLS_ECDH_anon_WITH_AES_128_CBC_SHA", 49176);
        m73of("TLS_ECDH_anon_WITH_AES_256_CBC_SHA", 49177);
        m73of("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", 49187);
        m73of("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", 49188);
        m73of("TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256", 49189);
        m73of("TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384", 49190);
        m73of("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", 49191);
        m73of("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", 49192);
        m73of("TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256", 49193);
        m73of("TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384", 49194);
        m73of("TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256", 49197);
        m73of("TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384", 49198);
        m73of("TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256", 49201);
        m73of("TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384", 49202);
        m73of("TLS_ECDHE_PSK_WITH_AES_128_CBC_SHA", 49205);
        m73of("TLS_ECDHE_PSK_WITH_AES_256_CBC_SHA", 49206);
        m73of("TLS_ECDHE_PSK_WITH_CHACHA20_POLY1305_SHA256", 52396);
    }

    public static synchronized CipherSuite forJavaName(String str) {
        CipherSuite cipherSuite;
        synchronized (CipherSuite.class) {
            cipherSuite = INSTANCES.get(str);
            if (cipherSuite == null) {
                cipherSuite = new CipherSuite(str);
                INSTANCES.put(str, cipherSuite);
            }
        }
        return cipherSuite;
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static List<CipherSuite> forJavaNames(String... strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            arrayList.add(forJavaName(str));
        }
        return Collections.unmodifiableList(arrayList);
    }

    private CipherSuite(String str) {
        if (str == null) {
            throw new NullPointerException();
        }
        this.javaName = str;
    }

    /* renamed from: of */
    private static CipherSuite m73of(String str, int i) {
        return forJavaName(str);
    }

    public String toString() {
        return this.javaName;
    }
}
