package com.tencent.liteav.p104b;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.Log;
import com.tencent.liteav.basic.p109e.CropRect;
import com.tencent.liteav.basic.p112h.TXCCombineFrame;
import com.tencent.liteav.p119d.Frame;
import com.tencent.liteav.p124i.TXCVideoEditConstants;
import com.tencent.liteav.p127l.TXCCombineProcessor;
import java.util.List;

@TargetApi(17)
/* renamed from: com.tencent.liteav.b.e */
/* loaded from: classes3.dex */
public class TXCombineProcess {

    /* renamed from: a */
    private TXCCombineProcessor f2247a;

    /* renamed from: b */
    private List<TXCVideoEditConstants.C3511a> f2248b;

    /* renamed from: c */
    private int f2249c;

    /* renamed from: d */
    private int f2250d;

    public TXCombineProcess(Context context) {
        this.f2247a = new TXCCombineProcessor(context);
    }

    /* renamed from: a */
    public void m3249a(List<TXCVideoEditConstants.C3511a> list, int i, int i2) {
        this.f2248b = list;
        this.f2249c = i;
        this.f2250d = i2;
    }

    /* renamed from: a */
    public int m3250a(int i, int i2, Frame frame, Frame frame2) {
        if (this.f2248b.size() < 2) {
            Log.w("TXCombineProcess", "join picture must has two TXAbsoluteRect!!!");
            return -1;
        }
        TXCVideoEditConstants.C3511a c3511a = this.f2248b.get(0);
        TXCCombineFrame tXCCombineFrame = new TXCCombineFrame();
        tXCCombineFrame.f2725a = i;
        tXCCombineFrame.f2726b = 0;
        int i3 = c3511a.f4363c;
        tXCCombineFrame.f2727c = i3;
        int i4 = c3511a.f4364d;
        tXCCombineFrame.f2728d = i4;
        tXCCombineFrame.f2730f = new CropRect(0, 0, i3, i4);
        tXCCombineFrame.f2731g = new CropRect(c3511a.f4361a, c3511a.f4362b, c3511a.f4363c, c3511a.f4364d);
        TXCVideoEditConstants.C3511a c3511a2 = this.f2248b.get(1);
        TXCCombineFrame tXCCombineFrame2 = new TXCCombineFrame();
        tXCCombineFrame2.f2725a = i2;
        tXCCombineFrame2.f2726b = 0;
        int i5 = c3511a2.f4363c;
        tXCCombineFrame2.f2727c = i5;
        int i6 = c3511a2.f4364d;
        tXCCombineFrame2.f2728d = i6;
        tXCCombineFrame2.f2730f = new CropRect(0, 0, i5, i6);
        tXCCombineFrame2.f2731g = new CropRect(c3511a2.f4361a, c3511a2.f4362b, c3511a2.f4363c, c3511a2.f4364d);
        TXCCombineFrame[] tXCCombineFrameArr = {tXCCombineFrame, tXCCombineFrame2};
        this.f2247a.m1285a(this.f2249c, this.f2250d);
        this.f2247a.m1277b(this.f2249c, this.f2250d);
        return this.f2247a.m1279a(tXCCombineFrameArr, 0);
    }

    /* renamed from: a */
    public void m3251a() {
        TXCCombineProcessor tXCCombineProcessor = this.f2247a;
        if (tXCCombineProcessor != null) {
            tXCCombineProcessor.m1286a();
        }
    }
}
