package com.squareup.okhttp.internal.spdy;

import java.util.concurrent.CountDownLatch;

/* loaded from: classes3.dex */
public final class Ping {
    private final CountDownLatch latch = new CountDownLatch(1);
    private long sent = -1;
    private long received = -1;

    Ping() {
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void send() {
        if (this.sent != -1) {
            throw new IllegalStateException();
        }
        this.sent = System.nanoTime();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void receive() {
        if (this.received != -1 || this.sent == -1) {
            throw new IllegalStateException();
        }
        this.received = System.nanoTime();
        this.latch.countDown();
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public void cancel() {
        if (this.received == -1) {
            long j = this.sent;
            if (j != -1) {
                this.received = j - 1;
                this.latch.countDown();
                return;
            }
        }
        throw new IllegalStateException();
    }
}
