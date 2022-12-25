package com.koushikdutta.async.http.filter;

import p007b.p014c.p015a.AbstractC0717p;
import p007b.p014c.p015a.C0608K;
import p007b.p014c.p015a.C0714n;
import p007b.p014c.p015a.C0723u;
import p007b.p014c.p015a.p018c.p020b.C0650a;

/* loaded from: classes3.dex */
public class ChunkedInputFilter extends C0723u {

    /* renamed from: h */
    public static final /* synthetic */ boolean f1532h = !ChunkedInputFilter.class.desiredAssertionStatus();

    /* renamed from: i */
    public int f1533i = 0;

    /* renamed from: j */
    public int f1534j = 0;

    /* renamed from: k */
    public State f1535k = State.CHUNK_LEN;

    /* renamed from: l */
    public C0714n f1536l = new C0714n();

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public enum State {
        CHUNK_LEN,
        CHUNK_LEN_CR,
        CHUNK_LEN_CRLF,
        CHUNK,
        CHUNK_CR,
        CHUNK_CRLF,
        COMPLETE,
        ERROR
    }

    @Override // p007b.p014c.p015a.C0723u, p007b.p014c.p015a.p016a.AbstractC0613c
    /* renamed from: a */
    public void mo3861a(AbstractC0717p abstractC0717p, C0714n c0714n) {
        int i;
        int i2;
        int i3;
        State state;
        if (this.f1535k == State.ERROR) {
            c0714n.m5304l();
            return;
        }
        while (c0714n.m5303m() > 0) {
            try {
                switch (C0650a.f250a[this.f1535k.ordinal()]) {
                    case 1:
                        char m5316c = c0714n.m5316c();
                        if (m5316c == '\r') {
                            this.f1535k = State.CHUNK_LEN_CR;
                        } else {
                            this.f1533i *= 16;
                            if (m5316c >= 'a' && m5316c <= 'f') {
                                i = this.f1533i;
                                i2 = m5316c - 'a';
                            } else if (m5316c >= '0' && m5316c <= '9') {
                                i = this.f1533i;
                                i3 = m5316c - '0';
                                this.f1533i = i + i3;
                            } else if (m5316c < 'A' || m5316c > 'F') {
                                StringBuilder sb = new StringBuilder();
                                sb.append("invalid chunk length: ");
                                sb.append(m5316c);
                                mo3859b(new ChunkedDataException(sb.toString()));
                                return;
                            } else {
                                i = this.f1533i;
                                i2 = m5316c - 'A';
                            }
                            i3 = i2 + 10;
                            this.f1533i = i + i3;
                        }
                        this.f1534j = this.f1533i;
                        continue;
                        break;
                    case 2:
                        if (m3860b(c0714n.m5316c())) {
                            state = State.CHUNK;
                            break;
                        } else {
                            return;
                        }
                    case 3:
                        int min = Math.min(this.f1534j, c0714n.m5303m());
                        this.f1534j -= min;
                        if (this.f1534j == 0) {
                            this.f1535k = State.CHUNK_CR;
                        }
                        if (min != 0) {
                            c0714n.m5327a(this.f1536l, min);
                            C0608K.m5480a(this, this.f1536l);
                            continue;
                        }
                    case 4:
                        if (m3863a(c0714n.m5316c())) {
                            state = State.CHUNK_CRLF;
                            break;
                        } else {
                            return;
                        }
                    case 5:
                        if (!m3860b(c0714n.m5316c())) {
                            return;
                        }
                        if (this.f1533i > 0) {
                            this.f1535k = State.CHUNK_LEN;
                        } else {
                            this.f1535k = State.COMPLETE;
                            mo3859b((Exception) null);
                        }
                        this.f1533i = 0;
                        continue;
                    case 6:
                        if (!f1532h) {
                            throw new AssertionError();
                        }
                        return;
                    default:
                        continue;
                }
                this.f1535k = state;
            } catch (Exception e) {
                mo3859b(e);
                return;
            }
        }
    }

    /* renamed from: a */
    public final boolean m3863a(char c) {
        return m3862a(c, '\r');
    }

    /* renamed from: a */
    public final boolean m3862a(char c, char c2) {
        if (c != c2) {
            this.f1535k = State.ERROR;
            mo3859b(new ChunkedDataException(c2 + " was expected, got " + c));
            return false;
        }
        return true;
    }

    @Override // p007b.p014c.p015a.AbstractC0718q
    /* renamed from: b */
    public void mo3859b(Exception exc) {
        if (exc == null && this.f1535k != State.COMPLETE) {
            exc = new ChunkedDataException("chunked input ended before final chunk");
        }
        super.mo3859b(exc);
    }

    /* renamed from: b */
    public final boolean m3860b(char c) {
        return m3862a(c, '\n');
    }
}
