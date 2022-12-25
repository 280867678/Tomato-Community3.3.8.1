package com.alipay.android.phone.mrpc.core;

import com.coremedia.iso.boxes.UserBox;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

/* renamed from: com.alipay.android.phone.mrpc.core.j */
/* loaded from: classes2.dex */
public final class C0899j extends AbstractC0878a {

    /* renamed from: g */
    private AbstractC0896g f780g;

    public C0899j(AbstractC0896g abstractC0896g, Method method, int i, String str, byte[] bArr, boolean z) {
        super(method, i, str, bArr, "application/x-www-form-urlencoded", z);
        this.f780g = abstractC0896g;
    }

    @Override // com.alipay.android.phone.mrpc.core.AbstractC0912v
    /* renamed from: a */
    public final Object mo4798a() {
        C0905o c0905o = new C0905o(this.f780g.mo4851a());
        c0905o.m4830a(this.f749b);
        c0905o.m4834a(this.f752e);
        c0905o.m4831a(this.f753f);
        c0905o.m4833a(DatabaseFieldConfigLoader.FIELD_NAME_ID, String.valueOf(this.f751d));
        c0905o.m4833a("operationType", this.f750c);
        c0905o.m4833a("gzip", String.valueOf(this.f780g.mo4848d()));
        c0905o.m4832a((Header) new BasicHeader(UserBox.TYPE, UUID.randomUUID().toString()));
        List<Header> m4867b = this.f780g.mo4849c().m4867b();
        if (m4867b != null && !m4867b.isEmpty()) {
            for (Header header : m4867b) {
                c0905o.m4832a(header);
            }
        }
        StringBuilder sb = new StringBuilder("threadid = ");
        sb.append(Thread.currentThread().getId());
        sb.append("; ");
        sb.append(c0905o.toString());
        try {
            C0911u c0911u = this.f780g.mo4850b().mo4839a(c0905o).get();
            if (c0911u == null) {
                throw new RpcException((Integer) 9, "response is null");
            }
            return c0911u.m4800b();
        } catch (InterruptedException e) {
            throw new RpcException(13, "", e);
        } catch (CancellationException e2) {
            throw new RpcException(13, "", e2);
        } catch (ExecutionException e3) {
            Throwable cause = e3.getCause();
            if (cause == null || !(cause instanceof HttpException)) {
                throw new RpcException(9, "", e3);
            }
            HttpException httpException = (HttpException) cause;
            int code = httpException.getCode();
            switch (code) {
                case 1:
                    code = 2;
                    break;
                case 2:
                    code = 3;
                    break;
                case 3:
                    code = 4;
                    break;
                case 4:
                    code = 5;
                    break;
                case 5:
                    code = 6;
                    break;
                case 6:
                    code = 7;
                    break;
                case 7:
                    code = 8;
                    break;
                case 8:
                    code = 15;
                    break;
                case 9:
                    code = 16;
                    break;
            }
            throw new RpcException(Integer.valueOf(code), httpException.getMsg());
        }
    }
}
