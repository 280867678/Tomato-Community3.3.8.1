package com.dianping.logan;

import p007b.p012b.p013a.C0591j;
import p007b.p012b.p013a.C0593l;

/* loaded from: classes2.dex */
public class LoganModel {

    /* renamed from: a */
    public Action f1235a;

    /* renamed from: b */
    public C0593l f1236b;

    /* renamed from: c */
    public C0591j f1237c;

    /* JADX INFO: Access modifiers changed from: package-private */
    /* loaded from: classes2.dex */
    public enum Action {
        WRITE,
        SEND,
        FLUSH
    }

    /* renamed from: a */
    public boolean m4173a() {
        C0593l c0593l;
        C0591j c0591j;
        Action action = this.f1235a;
        if (action != null) {
            if (action == Action.SEND && (c0591j = this.f1237c) != null) {
                c0591j.m5496a();
                throw null;
            } else if ((this.f1235a == Action.WRITE && (c0593l = this.f1236b) != null && c0593l.m5494a()) || this.f1235a == Action.FLUSH) {
                return true;
            }
        }
        return false;
    }
}
