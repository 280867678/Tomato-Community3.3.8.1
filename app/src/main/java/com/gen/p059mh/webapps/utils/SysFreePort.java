package com.gen.p059mh.webapps.utils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Random;

/* renamed from: com.gen.mh.webapps.utils.SysFreePort */
/* loaded from: classes2.dex */
public class SysFreePort {
    private static Random random = new Random();
    private Socket socket = new Socket();

    public static SysFreePort custom() throws IOException {
        return new SysFreePort();
    }

    private SysFreePort() throws IOException {
        this.socket.bind(new InetSocketAddress(0));
    }

    public void freePort() throws IOException {
        Socket socket = this.socket;
        if (socket == null || socket.isClosed()) {
            return;
        }
        this.socket.close();
    }

    public int getPort() {
        Socket socket = this.socket;
        if (socket == null || socket.isClosed()) {
            return -1;
        }
        return this.socket.getLocalPort();
    }

    public int getPortAndFree() throws IOException {
        Socket socket = this.socket;
        if (socket == null || socket.isClosed()) {
            return -1;
        }
        int localPort = this.socket.getLocalPort();
        this.socket.close();
        return localPort;
    }

    public static int random(int i, int i2) {
        return random.nextInt(Math.abs(i2 - i)) + i;
    }
}
