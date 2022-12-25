package p007b.p025d.p026a;

import android.content.Context;
import android.util.Pair;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.IpfsSDK;
import com.zzz.ipfssdk.LogUtil;
import com.zzz.ipfssdk.P2PUdpUtil;
import com.zzz.ipfssdk.callback.OnStateChangeListenner;
import com.zzz.ipfssdk.callback.exception.CodeState;
import com.zzz.ipfssdk.util.net.NetworkType;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p007b.p025d.p026a.p027a.C0756a;
import p007b.p025d.p026a.p028b.C0759a;
import p007b.p025d.p026a.p030d.C0769c;
import p007b.p025d.p026a.p030d.C0770d;
import p007b.p025d.p026a.p030d.C0772f;
import p007b.p025d.p026a.p030d.C0774h;
import p007b.p025d.p026a.p030d.C0775i;
import p007b.p025d.p026a.p030d.RunnableC0778l;
import p007b.p025d.p026a.p032e.p034b.C0784a;
import p007b.p025d.p026a.p032e.p034b.C0785b;

/* renamed from: b.d.a.I */
/* loaded from: classes2.dex */
public class C0738I {

    /* renamed from: a */
    public static String f404a = null;

    /* renamed from: b */
    public static String f405b = null;

    /* renamed from: c */
    public static String f406c = null;

    /* renamed from: d */
    public static C0738I f407d = null;

    /* renamed from: e */
    public String f408e;

    /* renamed from: g */
    public Context f409g;

    /* renamed from: m */
    public OnStateChangeListenner f415m;

    /* renamed from: n */
    public ExecutorService f416n = Executors.newFixedThreadPool(1);

    /* renamed from: o */
    public C0785b.AbstractC0787b f417o = new C0735F(this);

    /* renamed from: h */
    public AtomicBoolean f410h = new AtomicBoolean(false);

    /* renamed from: k */
    public boolean f413k = true;

    /* renamed from: j */
    public String f412j = null;

    /* renamed from: i */
    public int f411i = -1;

    /* renamed from: l */
    public Object f414l = new Object();

    static {
        System.loadLibrary("native-lib");
    }

    public C0738I() {
        C0740K.m5253a("FX001");
    }

    /* renamed from: a */
    public static C0738I m5275a() {
        if (f407d == null) {
            f407d = new C0738I();
        }
        return f407d;
    }

    /* renamed from: c */
    public static String m5261c() {
        return "v3.0.2.4";
    }

    /* renamed from: a */
    public Pair<String, CodeState> m5269a(String str, String str2) {
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "getVodResourceUrl urlName = " + str + ",cdnUrl = " + str2);
        synchronized (this) {
            C0775i c0775i = new C0775i();
            C0774h m5066g = c0775i.m5066g();
            if (m5066g != null) {
                m5066g.m5091b(0, System.currentTimeMillis());
            }
            C0742M m101b = P2PUdpUtil.m116a().m101b();
            if (m101b == null) {
                return new Pair<>(str2, new CodeState(2001, CodeState.MSGS.MSG_SDK_IP_NOT_INIT, null));
            }
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("sdkType", 0);
            linkedHashMap.put("url", str);
            linkedHashMap.put("token", this.f408e);
            linkedHashMap.put("p2pID", m101b.f422a);
            linkedHashMap.put("localIP", m101b.f423b);
            linkedHashMap.put("localPort", Integer.valueOf(m101b.f424c));
            linkedHashMap.put("natIP", m101b.f425d);
            linkedHashMap.put("natPort", Integer.valueOf(m101b.f426e));
            String jSONObject = new JSONObject(linkedHashMap).toString();
            StringBuilder sb = new StringBuilder();
            sb.append("QueryResource: ");
            sb.append(f405b);
            sb.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb.append(jSONObject);
            LogUtil.m119i("IpfsSDKOld", sb.toString());
            StringBuilder sb2 = new StringBuilder();
            sb2.append("QueryResource: ");
            sb2.append(f405b);
            sb2.append(ConstantUtils.PLACEHOLDER_STR_ONE);
            sb2.append(jSONObject);
            C0740K.m5251c(sb2.toString());
            HashMap hashMap = new HashMap();
            hashMap.put("Content-Type", "application/json; charset=UTF-8");
            hashMap.put("Connection", MainFragment.CLOSE_EVENT);
            C0804p.m4938a().m4936a(0, f405b, "POST", hashMap, jSONObject, new C0737H(this));
            CodeState[] codeStateArr = {null};
            synchronized (this.f414l) {
                if (this.f412j == null) {
                    try {
                        this.f414l.wait(7000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            m5066g.m5153e(str);
            if (this.f412j != null) {
                Pair<String, CodeState> m5268a = m5268a(this.f412j, str, str2, c0775i);
                this.f411i = -1;
                this.f412j = null;
                return m5268a;
            }
            if (this.f411i == -1) {
                codeStateArr[0] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_TIME_OUT, CodeState.MSGS.MSG_QUERY_RESOURCE_TIME_OUT_ERR, null);
            } else if (this.f411i == 0) {
                codeStateArr[0] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_HTTP_REQUEST_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_HTTP_REQUEST_ERR, null);
            } else {
                StringBuilder sb3 = new StringBuilder();
                sb3.append(CodeState.MSGS.MSG_QUERY_RESOURCE_UNEXPECTED_CODE_ERR);
                sb3.append(this.f411i);
                codeStateArr[0] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_UNEXPECTED_CODE_ERR, sb3.toString(), null);
            }
            C0756a.m5182a().onException(codeStateArr[0]);
            return new Pair<>(str2, codeStateArr[0]);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:127:0x031f  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x0339 A[Catch: MalformedURLException -> 0x02fd, JSONException -> 0x0300, TRY_LEAVE, TryCatch #16 {MalformedURLException -> 0x02fd, JSONException -> 0x0300, blocks: (B:152:0x02e0, B:154:0x02e6, B:156:0x02f0, B:124:0x0309, B:128:0x0320, B:129:0x0339), top: B:151:0x02e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02e0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0401  */
    /* JADX WARN: Type inference failed for: r3v13 */
    /* JADX WARN: Type inference failed for: r3v14 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v3 */
    /* JADX WARN: Type inference failed for: r3v39 */
    /* JADX WARN: Type inference failed for: r3v40 */
    /* JADX WARN: Type inference failed for: r3v49 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v66 */
    /* JADX WARN: Type inference failed for: r3v7 */
    /* JADX WARN: Type inference failed for: r3v8 */
    /* JADX WARN: Type inference failed for: r7v10 */
    /* JADX WARN: Type inference failed for: r7v17 */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v5 */
    /* JADX WARN: Type inference failed for: r7v6 */
    /* JADX WARN: Type inference failed for: r7v7 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public Pair<String, CodeState> m5268a(String str, String str2, String str3, C0775i c0775i) {
        String str4;
        Object obj;
        Object obj2;
        CodeState[] codeStateArr;
        int i;
        int i2;
        String str5;
        String str6;
        int i3;
        JSONObject jSONObject;
        String string;
        String string2;
        String str7;
        String str8;
        URL url;
        String str9;
        C0770d c0770d;
        int i4;
        Object obj3;
        StringBuilder sb;
        String str10;
        String substring;
        String format;
        StringBuilder sb2;
        String str11 = str3;
        CodeState[] codeStateArr2 = {null};
        C0770d m5067f = c0775i.m5067f();
        C0774h m5066g = c0775i.m5066g();
        if (NetworkType.NETWORK_WIFI == C0784a.m5027a(m5258d())) {
            m5067f.m5125h(0);
        } else {
            m5067f.m5125h(1);
        }
        try {
            JSONObject jSONObject2 = new JSONObject(str);
            i3 = jSONObject2.getInt("err_code");
            jSONObject = jSONObject2.getJSONObject(AopConstants.APP_PROPERTIES_KEY);
            string = jSONObject.has("playId") ? jSONObject.getString("playId") : "";
            if (jSONObject.has("sdkReportAddr")) {
                RunnableC0778l.m5061a().m5053a(jSONObject.getString("sdkReportAddr"));
            }
            C0769c.m5146a().m5142b(string);
            string2 = jSONObject.getString("urlName");
            c0775i.m5073b(string);
            m5067f.m5157c(string);
            m5066g.m5157c(string);
            m5066g.m5153e(string2);
            m5067f.m5153e(string2);
        } catch (MalformedURLException e) {
            e = e;
            str4 = str11;
            obj2 = null;
        } catch (JSONException e2) {
            e = e2;
            str4 = str11;
            obj = null;
        }
        if (i3 == 770) {
            codeStateArr2[0] = new CodeState(3001, CodeState.MSGS.MSG_TOKEN_ERROR, null);
            c0775i.m5081a(0);
        } else if (i3 == 771) {
            codeStateArr2[0] = new CodeState(3002, CodeState.MSGS.MSG_NO_THIS_RESOURCE, null);
        } else if (i3 == 767) {
            codeStateArr2[0] = new CodeState(3003, CodeState.MSGS.MSG_ROOTNODE_NOT_EXIST, null);
        } else if (i3 != 762) {
            String string3 = jSONObject.getString("chanId");
            m5066g.m5161a(string3);
            m5067f.m5161a(string3);
            String string4 = jSONObject.getString("url");
            try {
                if (string4 != null) {
                    URL url2 = new URL(string4);
                    str4 = url2.getPath();
                    str7 = string4;
                    if (url2.getPort() != -1) {
                        sb2 = new StringBuilder();
                        sb2.append(url2.getProtocol());
                        sb2.append("://");
                        sb2.append(url2.getHost());
                        sb2.append(":");
                        sb2.append(url2.getPort());
                    } else {
                        sb2 = new StringBuilder();
                        sb2.append(url2.getProtocol());
                        sb2.append("://");
                        sb2.append(url2.getHost());
                    }
                    str8 = sb2.toString();
                    m5066g.m5159b(str8);
                    m5066g.m5155d(str4);
                    m5067f.m5159b(str8);
                    m5067f.m5155d(str4);
                    url = url2;
                } else {
                    str7 = string4;
                    str8 = null;
                    str4 = null;
                    url = i3;
                }
                int i5 = jSONObject.getInt("status_code");
                if (i5 == 0) {
                    str9 = "";
                    try {
                        codeStateArr2[0] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_NORMAL, CodeState.MSGS.MSG_QUERY_RESOURCE_NORMAL, null);
                        c0770d = m5067f;
                        m5066g.m5092a(0, System.currentTimeMillis());
                        m5066g.m5093a(0, 0);
                    } catch (MalformedURLException e3) {
                        e = e3;
                        str4 = str11;
                        obj2 = null;
                        codeStateArr = codeStateArr2;
                        i2 = obj2;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    } catch (JSONException e4) {
                        e = e4;
                        str4 = str11;
                        obj = 0;
                        codeStateArr = codeStateArr2;
                        i2 = obj;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    }
                } else {
                    str9 = "";
                    c0770d = m5067f;
                    try {
                        if (i5 == 1) {
                            url = 0;
                            codeStateArr2[0] = new CodeState(3005, CodeState.MSGS.MSG_BACK2SRC_NOT_COMPLETED, null);
                            i4 = 1;
                        } else if (i5 == 2) {
                            url = 0;
                            codeStateArr2[0] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_P2P_DISABLE, CodeState.MSGS.MSG_P2P_DISABLE, null);
                            i4 = 2;
                        } else if (i5 == 3) {
                            url = 0;
                            codeStateArr2[0] = new CodeState(3004, CodeState.MSGS.MSG_NODE_NOT_EXIST, null);
                            i4 = 3;
                        }
                        c0775i.m5081a(i4);
                    } catch (MalformedURLException e5) {
                        e = e5;
                        str4 = str11;
                        obj2 = url;
                        codeStateArr = codeStateArr2;
                        i2 = obj2;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    } catch (JSONException e6) {
                        e = e6;
                        str4 = str11;
                        obj = url;
                        codeStateArr = codeStateArr2;
                        i2 = obj;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    }
                }
                if (str2.compareTo(string2) != 0) {
                    obj3 = null;
                    try {
                        codeStateArr2[0] = new CodeState(CodeState.CODES.CODE_QUERY_DATA_BROKEN, CodeState.MSGS.MSG_QUERY_DATA_BROKEN, null);
                        c0775i.m5081a(6);
                    } catch (MalformedURLException e7) {
                        e = e7;
                        str4 = str11;
                        obj2 = obj3;
                        codeStateArr = codeStateArr2;
                        i2 = obj2;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    } catch (JSONException e8) {
                        e = e8;
                        str4 = str11;
                        obj = obj3;
                        codeStateArr = codeStateArr2;
                        i2 = obj;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    }
                } else {
                    obj3 = null;
                }
                i2 = jSONObject.getInt("type");
                try {
                } catch (MalformedURLException e9) {
                    e = e9;
                } catch (JSONException e10) {
                    e = e10;
                }
            } catch (MalformedURLException e11) {
                e = e11;
                str4 = str11;
                codeStateArr = codeStateArr2;
            } catch (JSONException e12) {
                e = e12;
                str4 = str11;
                codeStateArr = codeStateArr2;
            }
            if (i2 == 0 || str2.compareTo(string2) != 0) {
                str4 = str11;
                codeStateArr = codeStateArr2;
                i = 0;
                str5 = (str4 == null || str3.length() == 0) ? str7 : str4;
            } else {
                String string5 = jSONObject.getString("fileId");
                i = jSONObject.getInt("size");
                int i6 = jSONObject.getInt("url_type");
                String string6 = jSONObject.getString("hlslist_dlurl");
                JSONArray jSONArray = jSONObject.getJSONArray("cacheNodes");
                ArrayList<C0742M> arrayList = new ArrayList<>();
                ArrayList arrayList2 = new ArrayList();
                int i7 = 0;
                while (i7 < jSONArray.length()) {
                    try {
                        JSONArray jSONArray2 = jSONArray.getJSONArray(i7);
                        JSONArray jSONArray3 = jSONArray;
                        codeStateArr = codeStateArr2;
                        try {
                            String string7 = jSONArray2.getString(0);
                            String str12 = string5;
                            String string8 = jSONArray2.getString(5);
                            String string9 = jSONArray2.getString(6);
                            arrayList.add(new C0742M(string7, jSONArray2.getString(1), (short) jSONArray2.getInt(2), jSONArray2.getString(3), (short) jSONArray2.getInt(4), string8, string9));
                            arrayList2.add(string8);
                            C0772f c0772f = new C0772f();
                            c0772f.m5157c(string);
                            c0772f.m5153e(string2);
                            c0772f.m5159b(str8);
                            c0772f.m5155d(str4);
                            c0772f.m5161a(string3);
                            c0772f.m5101f(string8);
                            c0772f.m5100g(string9);
                            c0775i.m5076a(string7, c0772f);
                            i7++;
                            jSONArray = jSONArray3;
                            string5 = str12;
                            str11 = str3;
                            codeStateArr2 = codeStateArr;
                            i6 = i6;
                        } catch (MalformedURLException e13) {
                            e = e13;
                            str4 = str3;
                            i2 = 0;
                            i = 0;
                            e.printStackTrace();
                            codeStateArr[i] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, e);
                            str6 = i2;
                            str5 = str6;
                            if (str5 != null) {
                            }
                            return new Pair<>(str4, codeStateArr[i]);
                        } catch (JSONException e14) {
                            e = e14;
                            str4 = str3;
                            i2 = 0;
                            i = 0;
                            e.printStackTrace();
                            codeStateArr[i] = new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
                            str6 = i2;
                            str5 = str6;
                            if (str5 != null) {
                            }
                            return new Pair<>(str4, codeStateArr[i]);
                        }
                    } catch (MalformedURLException e15) {
                        e = e15;
                        codeStateArr = codeStateArr2;
                        str4 = str3;
                        i2 = 0;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    } catch (JSONException e16) {
                        e = e16;
                        codeStateArr = codeStateArr2;
                        str4 = str3;
                        i2 = 0;
                        i = 0;
                        e.printStackTrace();
                        codeStateArr[i] = new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
                        str6 = i2;
                        str5 = str6;
                        if (str5 != null) {
                        }
                        return new Pair<>(str4, codeStateArr[i]);
                    }
                }
                String str13 = string5;
                int i8 = i6;
                codeStateArr = codeStateArr2;
                c0770d.m5138a(arrayList2);
                String str14 = DatabaseFieldConfigLoader.FIELD_NAME_INDEX;
                if (i8 == 1) {
                    sb = new StringBuilder();
                    sb.append(str14);
                    str10 = ".m3u8";
                } else if (i8 == 0) {
                    sb = new StringBuilder();
                    sb.append(str14);
                    str10 = ".mp4";
                } else {
                    if (i8 == 2) {
                        sb = new StringBuilder();
                        sb.append(str14);
                        str10 = ".flv";
                    }
                    str4 = str3;
                    if (str4 != null) {
                        try {
                        } catch (MalformedURLException e17) {
                            e = e17;
                            i2 = 0;
                            i = 0;
                            e.printStackTrace();
                            codeStateArr[i] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, e);
                            str6 = i2;
                            str5 = str6;
                            if (str5 != null) {
                            }
                            return new Pair<>(str4, codeStateArr[i]);
                        } catch (JSONException e18) {
                            e = e18;
                            i2 = 0;
                            i = 0;
                            e.printStackTrace();
                            codeStateArr[i] = new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
                            str6 = i2;
                            str5 = str6;
                            if (str5 != null) {
                            }
                            return new Pair<>(str4, codeStateArr[i]);
                        }
                        if (str3.length() != 0 && IpfsSDK.getInstance().isCdnEnable()) {
                            substring = str4.substring(0, str4.lastIndexOf(47));
                            format = String.format("/%s/.*", str13);
                            if (C0754Z.m5186d().m5189b(format) != null) {
                                str5 = String.format("http://127.0.0.1:%s/%s/%s", Integer.valueOf(C0759a.f499a), str13, str14);
                                i = 0;
                            } else {
                                C0752X m5187c = C0754Z.m5186d().m5187c(format);
                                try {
                                } catch (MalformedURLException e19) {
                                    e = e19;
                                } catch (JSONException e20) {
                                    e = e20;
                                }
                                if (m5187c != null) {
                                    try {
                                        i = 0;
                                        m5187c.m5213a(i8, string2, format, substring, str13, i, arrayList, this.f408e, string, string6, c0775i);
                                        str5 = String.format("http://127.0.0.1:%s/%s/%s", Integer.valueOf(C0759a.f499a), str13, str14);
                                    } catch (MalformedURLException e21) {
                                        e = e21;
                                        i = 0;
                                        i2 = 0;
                                        e.printStackTrace();
                                        codeStateArr[i] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, CodeState.MSGS.MSG_QUERY_RESOURCE_ILLEGAL_MALFORMED_URL_ERR, e);
                                        str6 = i2;
                                        str5 = str6;
                                        if (str5 != null) {
                                        }
                                        return new Pair<>(str4, codeStateArr[i]);
                                    } catch (JSONException e22) {
                                        e = e22;
                                        i = 0;
                                        i2 = 0;
                                        e.printStackTrace();
                                        codeStateArr[i] = new CodeState(3000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e);
                                        str6 = i2;
                                        str5 = str6;
                                        if (str5 != null) {
                                        }
                                        return new Pair<>(str4, codeStateArr[i]);
                                    }
                                } else {
                                    i = 0;
                                    str6 = null;
                                    codeStateArr[0] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_NO_HANDLER, CodeState.MSGS.MSG_QUERY_RESOURCE_NO_HANDLER_ERR, null);
                                    str5 = str6;
                                }
                            }
                        }
                    }
                    substring = str9;
                    format = String.format("/%s/.*", str13);
                    if (C0754Z.m5186d().m5189b(format) != null) {
                    }
                }
                sb.append(str10);
                str14 = sb.toString();
                str4 = str3;
                if (str4 != null) {
                }
                substring = str9;
                format = String.format("/%s/.*", str13);
                if (C0754Z.m5186d().m5189b(format) != null) {
                }
            }
            if (str5 != null) {
                str4 = str5;
            }
            return new Pair<>(str4, codeStateArr[i]);
        } else {
            codeStateArr2[0] = new CodeState(CodeState.CODES.CODE_QUERY_RESOURCE_PARAM_ERROR, CodeState.MSGS.MSG_QUERY_RESOURCE_PARAM_ERR, null);
        }
        str4 = str11;
        str6 = null;
        codeStateArr = codeStateArr2;
        i = 0;
        str5 = str6;
        if (str5 != null) {
        }
        return new Pair<>(str4, codeStateArr[i]);
    }

    /* renamed from: a */
    public final C0752X m5270a(String str) {
        try {
            String path = new URL(str).getPath();
            return C0754Z.m5186d().m5189b(String.format("/%s/.*", path.substring(1, path.lastIndexOf("/"))));
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /* renamed from: a */
    public void m5274a(int i, String str) {
        synchronized (this.f414l) {
            this.f411i = i;
            this.f412j = str;
            this.f414l.notify();
        }
    }

    /* renamed from: a */
    public void m5273a(Context context, String str, String str2) {
        LogUtil.m119i("IpfsSDKOld", "IpfsSDKOld version:v3.0.2.4");
        LogUtil.m119i("IpfsSDKOld", "init: token = " + str + ",domain = " + str2);
        C0740K.m5251c("IpfsSDKOld version:v3.0.2.4");
        this.f409g = context.getApplicationContext();
        this.f408e = str;
        if (!str2.endsWith("/")) {
            str2 = str2 + "/";
        }
        f404a = str2 + "node_mgmt/query_relaysrv";
        f405b = str2 + "node_mgmt/query_resource";
        f406c = str2 + "node_mgmt/query_resource_nodes";
        new Thread(new RunnableC0736G(this, context)).start();
    }

    /* renamed from: a */
    public void m5271a(OnStateChangeListenner onStateChangeListenner) {
        this.f415m = onStateChangeListenner;
    }

    /* renamed from: a */
    public void m5267a(String str, boolean z) {
        C0752X m5270a;
        LogUtil.m119i("IpfsSDKOld", "EnableIPfs: " + str + ConstantUtils.PLACEHOLDER_STR_ONE + z);
        if (str == null || str.length() == 0 || (m5270a = m5270a(str)) == null) {
            return;
        }
        m5270a.m5204a(z);
    }

    /* renamed from: a */
    public void m5266a(boolean z) {
        LogUtil.m119i("IpfsSDKOld", "EnableIpfs: " + z);
        ArrayList<C0752X> m5188c = C0754Z.m5186d().m5188c();
        for (int i = 0; i < m5188c.size(); i++) {
            m5188c.get(i).m5204a(z);
        }
    }

    /* renamed from: b */
    public int m5263b(String str) {
        C0752X m5270a;
        LogUtil.m119i("IpfsSDKOld", "PlayUrl:" + str);
        if (str == null || str.length() == 0 || (m5270a = m5270a(str)) == null) {
            return -1;
        }
        return m5270a.m5214a();
    }

    /* renamed from: b */
    public OnStateChangeListenner m5265b() {
        return this.f415m;
    }

    /* renamed from: b */
    public void m5262b(boolean z) {
        LogUtil.m119i("IpfsSDKOld", "CDNEanble:" + z);
        this.f413k = z;
    }

    /* renamed from: c */
    public void m5259c(String str) {
        try {
            String path = new URL(str).getPath();
            C0754Z.m5186d().m5192a(String.format("/%s/.*", path.substring(1, path.lastIndexOf("/"))));
            try {
                Thread.sleep(300L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (MalformedURLException e2) {
            e2.printStackTrace();
        }
    }

    /* renamed from: d */
    public Context m5258d() {
        return this.f409g;
    }

    /* renamed from: e */
    public boolean m5257e() {
        return this.f413k;
    }

    /* renamed from: f */
    public void m5256f() {
        C0785b.m5025a().m5022b();
        RunnableC0778l.m5061a().m5045e();
    }

    /* renamed from: g */
    public void m5255g() {
        C0754Z.m5186d().m5190b();
    }
}
