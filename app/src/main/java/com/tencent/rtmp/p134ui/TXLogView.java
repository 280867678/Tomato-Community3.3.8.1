package com.tencent.rtmp.p134ui;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.p002v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import com.tencent.rtmp.TXLiveBase;
import com.tencent.rtmp.TXLiveConstants;
import java.text.SimpleDateFormat;
import org.slf4j.Marker;

/* renamed from: com.tencent.rtmp.ui.TXLogView */
/* loaded from: classes3.dex */
public class TXLogView extends LinearLayout {

    /* renamed from: a */
    StringBuffer f5751a;

    /* renamed from: b */
    private TextView f5752b;

    /* renamed from: c */
    private TextView f5753c;

    /* renamed from: d */
    private ScrollView f5754d;

    /* renamed from: e */
    private ScrollView f5755e;

    /* renamed from: f */
    private final int f5756f;

    /* renamed from: g */
    private boolean f5757g;

    public TXLogView(Context context) {
        this(context, null);
    }

    public TXLogView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f5751a = new StringBuffer("");
        this.f5756f = 3000;
        this.f5757g = false;
        setOrientation(1);
        this.f5752b = new TextView(context);
        this.f5753c = new TextView(context);
        this.f5754d = new ScrollView(context);
        this.f5755e = new ScrollView(context);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0);
        layoutParams.weight = 0.2f;
        this.f5754d.setLayoutParams(layoutParams);
        this.f5754d.setBackgroundColor(1627389951);
        this.f5754d.setVerticalScrollBarEnabled(true);
        this.f5754d.setScrollbarFadingEnabled(true);
        this.f5752b.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        this.f5752b.setTextSize(2, 11.0f);
        this.f5752b.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.f5752b.setTypeface(Typeface.MONOSPACE, 1);
        this.f5752b.setLineSpacing(4.0f, 1.0f);
        this.f5752b.setPadding(m281a(context, 2.0f), m281a(context, 2.0f), m281a(context, 2.0f), m281a(context, 2.0f));
        this.f5754d.addView(this.f5752b);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(-1, 0);
        layoutParams2.weight = 0.8f;
        layoutParams2.topMargin = m281a(context, 2.0f);
        this.f5755e.setLayoutParams(layoutParams2);
        this.f5755e.setBackgroundColor(1627389951);
        this.f5755e.setVerticalScrollBarEnabled(true);
        this.f5755e.setScrollbarFadingEnabled(true);
        this.f5753c.setLayoutParams(new FrameLayout.LayoutParams(-1, -1));
        this.f5753c.setTextSize(2, 13.0f);
        this.f5753c.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.f5753c.setPadding(m281a(context, 2.0f), m281a(context, 2.0f), m281a(context, 2.0f), m281a(context, 2.0f));
        this.f5755e.addView(this.f5753c);
        addView(this.f5754d);
        addView(this.f5755e);
        setVisibility(8);
    }

    /* renamed from: a */
    public static int m281a(Context context, float f) {
        return (int) ((f * context.getResources().getDisplayMetrics().density) + 0.5f);
    }

    /* renamed from: a */
    public void m279a(Bundle bundle, Bundle bundle2, int i) {
        String string;
        if (this.f5757g || i == 2011 || i == 2012) {
            return;
        }
        if (bundle != null && getVisibility() == 0) {
            this.f5752b.setText(m280a(bundle));
        }
        if (this.f5751a.length() <= 0) {
            StringBuffer stringBuffer = this.f5751a;
            stringBuffer.append("liteav sdk version:" + TXLiveBase.getSDKVersionStr());
        }
        if (bundle2 == null || (string = bundle2.getString("EVT_MSG")) == null || string.isEmpty()) {
            return;
        }
        m282a(i, string);
        if (getVisibility() != 0) {
            return;
        }
        this.f5753c.setText(this.f5751a.toString());
        m278a(this.f5755e, this.f5753c);
    }

    /* renamed from: a */
    protected void m282a(int i, String str) {
        if (i == 1020) {
            return;
        }
        String format = new SimpleDateFormat("HH:mm:ss.SSS").format(Long.valueOf(System.currentTimeMillis()));
        while (this.f5751a.length() > 3000) {
            int indexOf = this.f5751a.indexOf("\n");
            if (indexOf == 0) {
                indexOf = 1;
            }
            this.f5751a = this.f5751a.delete(0, indexOf);
        }
        StringBuffer stringBuffer = this.f5751a;
        stringBuffer.append("\n[" + format + "]" + str);
        this.f5751a = stringBuffer;
    }

    /* renamed from: a */
    protected String m280a(Bundle bundle) {
        return String.format("%-16s %-16s %-16s\n%-12s %-12s %-12s %-12s\n%-14s %-14s %-14s\n%-16s %-16s", "CPU:" + bundle.getString("CPU_USAGE"), "RES:" + bundle.getInt("VIDEO_WIDTH") + Marker.ANY_MARKER + bundle.getInt("VIDEO_HEIGHT"), "SPD:" + bundle.getInt("NET_SPEED") + "Kbps", "JIT:" + bundle.getInt("NET_JITTER"), "FPS:" + bundle.getInt("VIDEO_FPS"), "GOP:" + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_GOP) + "s", "ARA:" + bundle.getInt("AUDIO_BITRATE") + "Kbps", "QUE:" + bundle.getInt("CODEC_CACHE") + " | " + bundle.getInt("CACHE_SIZE") + "," + bundle.getInt(TXLiveConstants.NET_STATUS_VIDEO_CACHE_SIZE) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_V_DEC_CACHE_SIZE) + " | " + bundle.getInt(TXLiveConstants.NET_STATUS_AV_RECV_INTERVAL) + "," + bundle.getInt(TXLiveConstants.NET_STATUS_AV_PLAY_INTERVAL) + "," + String.format("%.1f", Float.valueOf(bundle.getFloat(TXLiveConstants.NET_STATUS_AUDIO_PLAY_SPEED))).toString(), "VRA:" + bundle.getInt("VIDEO_BITRATE") + "Kbps", "DRP:" + bundle.getInt("CODEC_DROP_CNT") + "|" + bundle.getInt("DROP_SIZE"), "SVR:" + bundle.getString("SERVER_IP"), "AUDIO:" + bundle.getString(TXLiveConstants.NET_STATUS_AUDIO_INFO));
    }

    /* renamed from: a */
    public void m283a() {
        this.f5751a.setLength(0);
        this.f5752b.setText("");
        this.f5753c.setText("");
    }

    /* renamed from: a */
    public void m277a(boolean z) {
        setVisibility(z ? 0 : 8);
        if (z) {
            this.f5753c.setText(this.f5751a.toString());
            m278a(this.f5755e, this.f5753c);
            return;
        }
        this.f5753c.setText("");
    }

    /* renamed from: b */
    public void m276b(boolean z) {
        this.f5757g = z;
    }

    /* renamed from: a */
    private void m278a(ScrollView scrollView, View view) {
        if (scrollView == null || view == null) {
            return;
        }
        int measuredHeight = view.getMeasuredHeight() - scrollView.getMeasuredHeight();
        if (measuredHeight < 0) {
            measuredHeight = 0;
        }
        scrollView.scrollTo(0, measuredHeight);
    }
}
