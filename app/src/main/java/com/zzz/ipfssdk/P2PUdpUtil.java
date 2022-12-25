package com.zzz.ipfssdk;

import android.util.Log;
import com.gen.p059mh.webapp_extensions.fragments.MainFragment;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.utils.ConstantUtils;
import com.zzz.ipfssdk.callback.exception.CodeState;
import com.zzz.ipfssdk.callback.exception.ServerInnerException;
import com.zzz.ipfssdk.callback.exception.UrlNotExsitsException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import p007b.p025d.p026a.AbstractC0807s;
import p007b.p025d.p026a.AbstractC0808t;
import p007b.p025d.p026a.C0738I;
import p007b.p025d.p026a.C0740K;
import p007b.p025d.p026a.C0742M;
import p007b.p025d.p026a.C0746Q;
import p007b.p025d.p026a.C0747S;
import p007b.p025d.p026a.C0749U;
import p007b.p025d.p026a.C0760ba;
import p007b.p025d.p026a.C0804p;
import p007b.p025d.p026a.RunnableC0748T;
import p007b.p025d.p026a.p027a.C0756a;

/* loaded from: classes4.dex */
public class P2PUdpUtil {

    /* renamed from: a */
    public static P2PUdpUtil f5961a;

    /* renamed from: b */
    public String f5962b;

    /* renamed from: e */
    public String f5963e;

    /* renamed from: f */
    public int f5964f;

    /* renamed from: g */
    public String f5965g;

    /* renamed from: h */
    public int f5966h;

    /* renamed from: q */
    public Thread f5974q;

    /* renamed from: j */
    public List<C0760ba> f5967j = new ArrayList();

    /* renamed from: r */
    public AtomicBoolean f5975r = new AtomicBoolean(false);

    /* renamed from: k */
    public ConcurrentHashMap<Long, C0746Q> f5968k = new ConcurrentHashMap<>();

    /* renamed from: l */
    public ConcurrentHashMap<String, AbstractC0807s> f5969l = new ConcurrentHashMap<>();

    /* renamed from: m */
    public ConcurrentHashMap<String, AbstractC0807s> f5970m = new ConcurrentHashMap<>();

    /* renamed from: n */
    public BlockingQueue<C0747S> f5971n = new LinkedBlockingQueue();

    /* renamed from: o */
    public Set f5972o = new HashSet();

    /* renamed from: p */
    public Object f5973p = new Object();

    /* renamed from: s */
    public AtomicBoolean f5976s = new AtomicBoolean(false);

    public P2PUdpUtil() {
        RunnableC0748T runnableC0748T = new RunnableC0748T(this);
        this.f5974q = new Thread(runnableC0748T);
    }

    /* renamed from: a */
    public static P2PUdpUtil m116a() {
        if (f5961a == null) {
            f5961a = new P2PUdpUtil();
        }
        return f5961a;
    }

    private void onCloseTaskCallback(long j) {
    }

    private void onCreateTaskCallback(long j) {
        C0746Q c0746q = this.f5968k.get(Long.valueOf(j));
        if (c0746q == null || c0746q.m5225a() == null) {
            return;
        }
        c0746q.m5225a().mo4925a(j);
    }

    private void onRecvDataCallback(long j, byte[] bArr) {
        if (this.f5968k.get(Long.valueOf(j)) == null) {
            return;
        }
        this.f5971n.add(new C0747S(j, bArr, bArr.length));
    }

    private void onRecvMsgCallback(String str, byte[] bArr, String str2, int i) {
        AbstractC0807s abstractC0807s = this.f5969l.get(String.format("%s:%d", str2, Integer.valueOf(i)));
        if (abstractC0807s != null) {
            abstractC0807s.mo4927a(str, bArr, str2, i);
        }
    }

    private int onRecvRegisterCallback(String str, String str2, int i, String str3, int i2, String str4, int i3) {
        AbstractC0807s abstractC0807s = this.f5970m.get(str);
        if (abstractC0807s != null) {
            abstractC0807s.mo4928a(str, str2, i, str3, i2, str4);
            return 0;
        }
        return -1;
    }

    private void onRecvTaskMsgCallback(long j, String str, String str2, byte[] bArr, int i) {
        C0746Q c0746q;
        AbstractC0808t m5225a;
        if (str.compareTo(this.f5962b) != 0 || (c0746q = this.f5968k.get(Long.valueOf(j))) == null || (m5225a = c0746q.m5225a()) == null) {
            return;
        }
        m5225a.mo4924a(j, str, str2, bArr, i);
    }

    private void onRegisterResCallback(String str, String str2, int i, String str3, int i2, String str4) {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("onRegisterResCallback: ");
            sb.append(str4);
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
            AbstractC0807s abstractC0807s = this.f5970m.get(new JSONObject(str4).getString("targetId"));
            if (abstractC0807s == null) {
                return;
            }
            abstractC0807s.mo4926b(str, str2, i, str3, i2, str4);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private int onTransferCheckThrow(long j) {
        return this.f5968k.get(Long.valueOf(j)) != null ? 1 : 0;
    }

    /* renamed from: a */
    public int m114a(long j) {
        if (!this.f5975r.get()) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "IpfsSDK hasn't started!");
            return -1;
        }
        if (this.f5968k.get(Long.valueOf(j)) != null) {
            synchronized (this.f5973p) {
                this.f5972o.add(Long.valueOf(j));
            }
        }
        return 0;
    }

    /* renamed from: a */
    public void m115a(int i) {
        C0760ba c0760ba;
        if (i < this.f5967j.size() && (c0760ba = this.f5967j.get(i)) != null) {
            String m5172a = c0760ba.m5172a();
            int m5171b = c0760ba.m5171b();
            String m5170c = c0760ba.m5170c();
            m116a().m105a(String.format("%s:%d", m5172a, Integer.valueOf(m5171b)), new C0749U(this));
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            linkedHashMap.put("type", 0);
            linkedHashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, m116a().m98c());
            linkedHashMap.put("verify", "");
            linkedHashMap.put(AopConstants.APP_PROPERTIES_KEY, m5170c);
            JSONObject jSONObject = new JSONObject(linkedHashMap);
            Log.d(LogUtil.TAG_IPFS_SDK, "JsonStr: " + jSONObject.toString());
            C0740K.m5251c("QueryIPAddrInfo register: " + jSONObject.toString());
            m116a().m102a(jSONObject.toString().getBytes(), m5172a, m5171b);
            m116a().m102a(jSONObject.toString().getBytes(), m5172a, m5171b);
            m116a().m102a(jSONObject.toString().getBytes(), m5172a, m5171b);
        }
    }

    /* renamed from: a */
    public synchronized void m113a(long j, C0742M c0742m, int i, long j2, byte[] bArr, AbstractC0808t abstractC0808t) {
        String str = c0742m.f422a;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "new taskId: " + j + ConstantUtils.PLACEHOLDER_STR_ONE + i + ConstantUtils.PLACEHOLDER_STR_ONE + j2);
        this.f5968k.put(Long.valueOf(j), new C0746Q(j, str, i, j2, abstractC0808t));
        if (m111a(c0742m)) {
            m106a(str, j, i, j2, bArr);
        }
    }

    /* renamed from: a */
    public void m112a(long j, byte[] bArr) {
        if (!this.f5975r.get()) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "IpfsSDK hasn't started!");
        } else {
            nativeSendTaskMsg(j, bArr);
        }
    }

    /* renamed from: a */
    public void m108a(String str, int i) {
        if (str == null || i == 0) {
            return;
        }
        nativeSetTarget(str, i);
    }

    /* renamed from: a */
    public void m107a(String str, int i, String str2, int i2) {
        synchronized (this) {
            LogUtil.m119i(LogUtil.TAG_IPFS_SDK, String.format("IPAddrInfo: %s:%d %s:%d", str, Integer.valueOf(i), str2, Integer.valueOf(i2)));
            this.f5963e = str;
            this.f5964f = i;
            this.f5965g = str2;
            this.f5966h = i2;
            new Date().getTime();
            notify();
        }
    }

    /* renamed from: a */
    public void m106a(String str, long j, int i, long j2, byte[] bArr) {
        if (!this.f5975r.get()) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "IpfsSDK hasn't started!");
            return;
        }
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "Send Request!");
        nativeSendRequest(j, str, this.f5962b, i, j2, bArr);
    }

    /* renamed from: a */
    public void m105a(String str, AbstractC0807s abstractC0807s) {
        this.f5969l.put(str, abstractC0807s);
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x0096, code lost:
        p007b.p025d.p026a.p027a.C0756a.m5182a().onException(new com.zzz.ipfssdk.callback.exception.CodeState(2000, com.zzz.ipfssdk.callback.exception.CodeState.MSGS.MSG_NATIVE_RESPONSE_TIMEOUT, null));
     */
    /* renamed from: a */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m104a(String str, String str2, int i) {
        this.f5962b = str;
        C0740K.m5251c("P2PUdp begin init!");
        C0756a.m5182a().onIniting();
        if (this.f5975r.compareAndSet(false, true)) {
            this.f5976s.set(true);
            this.f5974q.start();
            nativeInit(this.f5962b, str2, i);
            synchronized (this) {
                this.f5967j.clear();
                int i2 = 0;
                while (this.f5967j.size() == 0) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("QueryRelaySrv retryCount: ");
                    sb.append(i2);
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
                    StringBuilder sb2 = new StringBuilder();
                    sb2.append("Init queryRelaySrv retryCount: ");
                    sb2.append(i2);
                    C0740K.m5251c(sb2.toString());
                    i2++;
                    m94e();
                    try {
                        Thread.sleep(i2 * 50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                int i3 = 0;
                while (true) {
                    if (this.f5965g != null && this.f5966h != 0) {
                    }
                    StringBuilder sb3 = new StringBuilder();
                    sb3.append("GetIPAddrInfo retryCount: ");
                    sb3.append(i3);
                    LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb3.toString());
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append("Init getIPAddrInfo retryCount: ");
                    sb4.append(i3);
                    C0740K.m5251c(sb4.toString());
                    for (int i4 = 0; i4 < this.f5967j.size(); i4++) {
                        m115a(i4);
                    }
                    i3++;
                    try {
                        wait(5000L);
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    }
                }
            }
            C0740K.m5251c("P2PUdp init done!");
            C0756a.m5182a().onInitted();
        }
    }

    /* renamed from: a */
    public void m103a(byte[] bArr, C0742M c0742m) {
        String str;
        int i;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "sendRegisterToPeer---->" + c0742m.toString());
        synchronized (this) {
            if (this.f5965g == null) {
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "No natIP, return directly!");
                return;
            }
            if (c0742m.f425d.compareTo(this.f5965g) == 0) {
                str = c0742m.f423b;
                i = c0742m.f424c;
            } else {
                str = c0742m.f425d;
                i = c0742m.f426e;
            }
            m102a(bArr, str, i);
        }
    }

    /* renamed from: a */
    public void m102a(byte[] bArr, String str, int i) {
        if (!this.f5975r.get()) {
            LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "IpfsSDK hasn't started!");
        } else {
            nativeSendRegisterToPeer(this.f5962b, bArr, str, i);
        }
    }

    /* renamed from: a */
    public boolean m111a(C0742M c0742m) {
        String str;
        int i;
        synchronized (this) {
            if (this.f5965g != null && this.f5966h != 0) {
                if (this.f5965g.compareTo(c0742m.f425d) == 0) {
                    str = c0742m.f423b;
                    i = c0742m.f424c;
                } else {
                    str = c0742m.f425d;
                    i = c0742m.f426e;
                }
                m108a(str, i);
                return true;
            }
            return false;
        }
    }

    /* renamed from: b */
    public C0742M m101b() {
        synchronized (this) {
            if (this.f5965g == null || this.f5966h == 0) {
                return null;
            }
            return new C0742M(this.f5962b, this.f5963e, this.f5964f, this.f5965g, this.f5966h, null, null);
        }
    }

    /* renamed from: b */
    public void m99b(String str, AbstractC0807s abstractC0807s) {
        this.f5970m.put(str, abstractC0807s);
    }

    /* renamed from: c */
    public String m98c() {
        return this.f5962b;
    }

    /* renamed from: d */
    public void m96d() {
        synchronized (this) {
            this.f5967j.clear();
            int i = 0;
            while (this.f5967j.size() == 0) {
                StringBuilder sb = new StringBuilder();
                sb.append("QueryRelaySrv retryCount: ");
                sb.append(i);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("NetworkChange queryRelaySrv retryCount: ");
                sb2.append(i);
                C0740K.m5251c(sb2.toString());
                i++;
                m94e();
                try {
                    Thread.sleep(i * 50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            this.f5966h = 0;
            this.f5965g = null;
            int i2 = 0;
            while (this.f5965g == null && this.f5966h == 0) {
                StringBuilder sb3 = new StringBuilder();
                sb3.append("GetIPAddrInfo retryCount: ");
                sb3.append(i2);
                LogUtil.m121d(LogUtil.TAG_IPFS_SDK, sb3.toString());
                StringBuilder sb4 = new StringBuilder();
                sb4.append("NetworkChange queryIPAddrInfo retryCount: ");
                sb4.append(i2);
                C0740K.m5251c(sb4.toString());
                i2++;
                for (int i3 = 0; i3 < this.f5967j.size(); i3++) {
                    m115a(i3);
                }
                try {
                    wait(5000L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }
    }

    public void deRegisterMsgHandler(String str) {
        this.f5969l.remove(str);
    }

    /* JADX WARN: Removed duplicated region for block: B:34:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0063 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void m94e() {
        C0756a m5182a;
        CodeState codeState;
        String str;
        LogUtil.m121d(LogUtil.TAG_IPFS_SDK, "---------------------------------->queryRelaySrv!");
        HashMap hashMap = new HashMap();
        hashMap.put("Content-Type", "application/json; charset=UTF-8");
        hashMap.put("Connection", MainFragment.CLOSE_EVENT);
        try {
            str = C0804p.m4938a().m4934a(C0738I.f404a, "GET", hashMap, null);
        } catch (ServerInnerException e) {
            e.printStackTrace();
            m5182a = C0756a.m5182a();
            codeState = new CodeState(1000, CodeState.MSGS.MSG_HTTP_RESPONSE_500, e);
            m5182a.onException(codeState);
            str = null;
            if (str == null) {
            }
        } catch (UrlNotExsitsException e2) {
            e2.printStackTrace();
            m5182a = C0756a.m5182a();
            codeState = new CodeState(CodeState.CODES.CODE_URL_NOT_EXSITS, CodeState.MSGS.MSG_URL_NOT_EXSITS, e2);
            m5182a.onException(codeState);
            str = null;
            if (str == null) {
            }
        } catch (IOException e3) {
            e3.printStackTrace();
            m5182a = C0756a.m5182a();
            codeState = new CodeState(1000, CodeState.MSGS.MSG_HTTP_REQUEST_ERROR, e3);
            m5182a.onException(codeState);
            str = null;
            if (str == null) {
            }
        }
        if (str == null) {
            try {
                Log.d(LogUtil.TAG_IPFS_SDK, "QueryRelaySrv Response: " + str);
                C0740K.m5251c("QueryRelaySrv Response: " + str);
                JSONObject jSONObject = new JSONObject(str).getJSONObject(AopConstants.APP_PROPERTIES_KEY);
                String string = jSONObject.getString("token");
                JSONArray jSONArray = jSONObject.getJSONArray("relaySrv");
                StringBuilder sb = new StringBuilder();
                sb.append("Token is ");
                sb.append(string);
                Log.d(LogUtil.TAG_IPFS_SDK, sb.toString());
                StringBuilder sb2 = new StringBuilder();
                sb2.append("Srv Count:");
                sb2.append(jSONArray.length());
                Log.d(LogUtil.TAG_IPFS_SDK, sb2.toString());
                if (jSONArray.length() <= 0) {
                    C0756a.m5182a().onException(new CodeState(1000, CodeState.MSGS.MSG_HTTP_RESPONSE_EMPTY, null));
                    return;
                }
                for (int i = 0; i < jSONArray.length(); i++) {
                    JSONArray jSONArray2 = jSONArray.getJSONArray(i);
                    this.f5967j.add(new C0760ba(jSONArray2.getString(0), jSONArray2.getInt(1), string));
                }
            } catch (JSONException e4) {
                e4.printStackTrace();
                C0756a.m5182a().onException(new CodeState(1000, CodeState.MSGS.MSG_JSON_PARSE_ERROR, e4));
            }
        }
    }

    public final native int nativeCloseTaskSync(long j);

    public final native void nativeInit(String str, String str2, int i);

    public final native void nativeSendRegisterToPeer(String str, byte[] bArr, String str2, int i);

    public final native void nativeSendRequest(long j, String str, String str2, int i, long j2, byte[] bArr);

    public final native void nativeSendTaskMsg(long j, byte[] bArr);

    public final native void nativeSetTarget(String str, int i);
}
