package com.gen.p059mh.webapps.utils;

import android.content.Context;
import android.net.Proxy;
import android.os.Build;
import java.io.IOException;
import java.net.BindException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.regex.Pattern;

/* renamed from: com.gen.mh.webapps.utils.NetUtils */
/* loaded from: classes2.dex */
public class NetUtils {
    private static final Pattern IPV4_PATTERN = Pattern.compile("^(([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])\\.){3}([0-9]|[1-9][0-9]|1[0-9]{2}|2[0-4][0-9]|25[0-5])$");
    public static int server_port = 10001;

    public static boolean isIPv4Address(String str) {
        return IPV4_PATTERN.matcher(str).matches();
    }

    public static InetAddress getLocalIPAddress() {
        Enumeration<NetworkInterface> enumeration;
        try {
            enumeration = NetworkInterface.getNetworkInterfaces();
        } catch (SocketException e) {
            e.printStackTrace();
            enumeration = null;
        }
        if (enumeration != null) {
            while (enumeration.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = enumeration.nextElement().getInetAddresses();
                if (inetAddresses != null) {
                    while (inetAddresses.hasMoreElements()) {
                        InetAddress nextElement = inetAddresses.nextElement();
                        if (!nextElement.isLoopbackAddress() && isIPv4Address(nextElement.getHostAddress())) {
                            return nextElement;
                        }
                    }
                    continue;
                }
            }
        }
        return null;
    }

    public static int createPort() {
        try {
            server_port = SysFreePort.custom().getPortAndFree();
            if (server_port != -1) {
                return server_port;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        server_port++;
        if (!isPortAvailable("http://127.0.0.1", server_port, 200)) {
            return server_port;
        }
        return createPort();
    }

    public static boolean isPortAvailable(String str, int i, int i2) {
        try {
            new Socket(InetAddress.getByName(str), i);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean isPortAvailable(int i) {
        new Socket();
        ArrayList arrayList = new ArrayList();
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                Enumeration<InetAddress> inetAddresses = networkInterfaces.nextElement().getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    arrayList.add(inetAddresses.nextElement().getHostAddress());
                }
            }
            boolean z = false;
            for (int i2 = 0; i2 < arrayList.size() && (z = bindPort((String) arrayList.get(i2), i).booleanValue()); i2++) {
            }
            return z;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static Boolean bindPort(String str, int i) throws Exception {
        try {
            try {
                Socket socket = new Socket();
                socket.bind(new InetSocketAddress(str, i));
                socket.close();
                return true;
            } catch (Exception e) {
                if (e instanceof BindException) {
                    Logger.m4113i(i + " 已被占用 开始查找新端口...");
                }
                return false;
            }
        } catch (Throwable unused) {
            return false;
        }
    }

    public static ProxyModel checkProxy(Context context) {
        int port;
        String str;
        if (Build.VERSION.SDK_INT >= 14) {
            str = System.getProperty("http.proxyHost");
            String property = System.getProperty("http.proxyPort");
            if (property == null) {
                property = "-1";
            }
            port = Integer.parseInt(property);
        } else {
            String host = Proxy.getHost(context);
            port = Proxy.getPort(context);
            str = host;
        }
        if (str == null || port == -1) {
            return null;
        }
        return new ProxyModel(port, str);
    }

    /* renamed from: com.gen.mh.webapps.utils.NetUtils$ProxyModel */
    /* loaded from: classes2.dex */
    public static class ProxyModel {
        String address;
        int port;

        public ProxyModel(int i, String str) {
            this.port = i;
            this.address = str;
        }

        public int getPort() {
            return this.port;
        }

        public String getAddress() {
            return this.address;
        }
    }
}
