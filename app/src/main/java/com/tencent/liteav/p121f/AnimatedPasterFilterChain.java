package com.tencent.liteav.p121f;

import android.text.TextUtils;
import com.tencent.liteav.p118c.AnimatedPasterJsonConfig;
import com.tencent.liteav.p119d.AnimatedPaster;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p119d.Resolution;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* renamed from: com.tencent.liteav.f.a */
/* loaded from: classes3.dex */
public class AnimatedPasterFilterChain extends BasicFilterChain {

    /* renamed from: d */
    private static AnimatedPasterFilterChain f3812d;

    /* renamed from: e */
    private List<TXCVideoEditConstants.C3512b> f3813e;

    /* renamed from: f */
    private CopyOnWriteArrayList<AnimatedPaster> f3814f = new CopyOnWriteArrayList<>();

    /* renamed from: a */
    public static AnimatedPasterFilterChain m1935a() {
        if (f3812d == null) {
            f3812d = new AnimatedPasterFilterChain();
        }
        return f3812d;
    }

    private AnimatedPasterFilterChain() {
    }

    /* renamed from: a */
    public void m1931a(List<TXCVideoEditConstants.C3512b> list) {
        this.f3813e = list;
        this.f3814f.clear();
        Frame frame = this.f3844c;
        if (frame != null) {
            m1934a(frame);
        }
    }

    /* renamed from: b */
    public List<AnimatedPaster> m1930b() {
        return this.f3814f;
    }

    /* renamed from: a */
    public void m1934a(Frame frame) {
        List<TXCVideoEditConstants.C3512b> list;
        int i;
        long j;
        if (frame == null || this.f3842a == 0 || this.f3843b == 0 || (list = this.f3813e) == null || list.size() == 0) {
            return;
        }
        Resolution m1882b = m1882b(frame);
        for (TXCVideoEditConstants.C3512b c3512b : this.f3813e) {
            if (c3512b != null) {
                TXCVideoEditConstants.C3512b m1933a = m1933a(c3512b, m1883a(c3512b.f4366b, m1882b));
                AnimatedPasterJsonConfig m1932a = m1932a(m1933a.f4365a);
                if (m1932a != null && (i = m1932a.f3320c) > 0) {
                    long j2 = m1933a.f4367c;
                    long j3 = m1933a.f4368d - j2;
                    int i2 = m1932a.f3319b;
                    int i3 = i2 / i;
                    int i4 = (int) (j3 / i2);
                    if (j3 % i2 > 0) {
                        i4++;
                    }
                    long j4 = j2;
                    int i5 = 0;
                    while (i5 < i4) {
                        int i6 = 0;
                        while (true) {
                            if (i6 >= m1932a.f3320c) {
                                j = j4;
                                break;
                            }
                            long j5 = i3 + j4;
                            long j6 = j4;
                            if (j5 > m1933a.f4368d) {
                                j = j6;
                                break;
                            }
                            AnimatedPaster animatedPaster = new AnimatedPaster();
                            animatedPaster.f3449a = m1933a.f4365a + m1932a.f3324g.get(i6).f3325a + ".png";
                            animatedPaster.f3450b = m1933a.f4366b;
                            animatedPaster.f3451c = j6;
                            animatedPaster.f3452d = j5;
                            animatedPaster.f3453e = m1933a.f4369e;
                            this.f3814f.add(animatedPaster);
                            j4 = animatedPaster.f3452d;
                            i6++;
                        }
                        i5++;
                        j4 = j;
                    }
                }
            }
        }
    }

    /* renamed from: a */
    private AnimatedPasterJsonConfig m1932a(String str) {
        String m1929b = m1929b(str + "config.json");
        if (TextUtils.isEmpty(m1929b)) {
            return null;
        }
        try {
            JSONObject jSONObject = new JSONObject(m1929b);
            AnimatedPasterJsonConfig animatedPasterJsonConfig = new AnimatedPasterJsonConfig();
            try {
                animatedPasterJsonConfig.f3318a = jSONObject.getString("name");
                animatedPasterJsonConfig.f3320c = jSONObject.getInt("count");
                animatedPasterJsonConfig.f3319b = jSONObject.getInt("period");
                animatedPasterJsonConfig.f3321d = jSONObject.getInt("width");
                animatedPasterJsonConfig.f3322e = jSONObject.getInt("height");
                animatedPasterJsonConfig.f3323f = jSONObject.getInt("keyframe");
                JSONArray jSONArray = jSONObject.getJSONArray("frameArray");
                for (int i = 0; i < animatedPasterJsonConfig.f3320c; i++) {
                    JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                    AnimatedPasterJsonConfig.C3411a c3411a = new AnimatedPasterJsonConfig.C3411a();
                    c3411a.f3325a = jSONObject2.getString("picture");
                    animatedPasterJsonConfig.f3324g.add(c3411a);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return animatedPasterJsonConfig;
        } catch (JSONException e2) {
            e2.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x004a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* renamed from: b */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String m1929b(String str) {
        BufferedReader bufferedReader;
        IOException e;
        String str2 = "";
        try {
            try {
                bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(str)));
                while (true) {
                    try {
                        try {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            str2 = str2 + readLine;
                        } catch (IOException e2) {
                            e = e2;
                            e.printStackTrace();
                            if (bufferedReader != null) {
                                bufferedReader.close();
                            }
                            return str2;
                        }
                    } catch (Throwable th) {
                        th = th;
                        if (bufferedReader != null) {
                            try {
                                bufferedReader.close();
                            } catch (IOException e3) {
                                e3.printStackTrace();
                            }
                        }
                        throw th;
                    }
                }
                bufferedReader.close();
                bufferedReader.close();
            } catch (IOException e4) {
                e4.printStackTrace();
            }
        } catch (IOException e5) {
            e = e5;
            bufferedReader = null;
        } catch (Throwable th2) {
            th = th2;
            bufferedReader = null;
            if (bufferedReader != null) {
            }
            throw th;
        }
        return str2;
    }

    /* renamed from: a */
    private TXCVideoEditConstants.C3512b m1933a(TXCVideoEditConstants.C3512b c3512b, TXCVideoEditConstants.C3517g c3517g) {
        TXCVideoEditConstants.C3512b c3512b2 = new TXCVideoEditConstants.C3512b();
        c3512b2.f4366b = c3517g;
        c3512b2.f4365a = c3512b.f4365a;
        c3512b2.f4367c = c3512b.f4367c;
        c3512b2.f4368d = c3512b.f4368d;
        c3512b2.f4369e = c3512b.f4369e;
        return c3512b2;
    }

    /* renamed from: c */
    public void m1928c() {
        this.f3814f.clear();
        List<TXCVideoEditConstants.C3512b> list = this.f3813e;
        if (list != null) {
            list.clear();
        }
        this.f3813e = null;
    }
}
