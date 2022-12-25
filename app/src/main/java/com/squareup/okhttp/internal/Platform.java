package com.squareup.okhttp.internal;

import com.squareup.okhttp.Protocol;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;
import okio.Buffer;

/* loaded from: classes3.dex */
public class Platform {
    private static final Platform PLATFORM = findPlatform();

    public String getPrefix() {
        return "OkHttp";
    }

    public String getSelectedProtocol(SSLSocket sSLSocket) {
        return null;
    }

    public void setProtocols(SSLSocket sSLSocket, List<Protocol> list) {
    }

    public void tagSocket(Socket socket) throws SocketException {
    }

    public void untagSocket(Socket socket) throws SocketException {
    }

    public static Platform get() {
        return PLATFORM;
    }

    public void logW(String str) {
        System.out.println(str);
    }

    public URI toUriLenient(URL url) throws URISyntaxException {
        return url.toURI();
    }

    public void configureTls(SSLSocket sSLSocket, String str, String str2) {
        if (str2.equals("SSLv3")) {
            sSLSocket.setEnabledProtocols(new String[]{"SSLv3"});
        }
    }

    public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) throws IOException {
        socket.connect(inetSocketAddress, i);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:(2:2|3)|4|5|6|7|8|(3:10|11|12)|13|14|15|16|17|18|19) */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x006a, code lost:
        r3 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Platform findPlatform() {
        Class<?> cls;
        Class<?> cls2;
        Method method;
        Method method2;
        Class<?> cls3;
        try {
            try {
                cls = Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
            } catch (ClassNotFoundException unused) {
                cls = Class.forName("org.apache.harmony.xnet.provider.jsse.OpenSSLSocketImpl");
            }
            Class<?> cls4 = cls;
            Method method3 = cls4.getMethod("setUseSessionTickets", Boolean.TYPE);
            Method method4 = cls4.getMethod("setHostname", String.class);
            Method method5 = null;
            try {
                cls3 = Class.forName("android.net.TrafficStats");
                method = cls3.getMethod("tagSocket", Socket.class);
            } catch (ClassNotFoundException | NoSuchMethodException unused2) {
                method = null;
            }
            try {
                method2 = cls3.getMethod("untagSocket", Socket.class);
            } catch (ClassNotFoundException | NoSuchMethodException unused3) {
                method2 = null;
                Method method6 = cls4.getMethod("setNpnProtocols", byte[].class);
                method5 = cls4.getMethod("getNpnSelectedProtocol", new Class[0]);
                return new Android(cls4, method3, method4, method, method2, method6, method5);
            }
            Method method62 = cls4.getMethod("setNpnProtocols", byte[].class);
            method5 = cls4.getMethod("getNpnSelectedProtocol", new Class[0]);
            return new Android(cls4, method3, method4, method, method2, method62, method5);
        } catch (ClassNotFoundException | NoSuchMethodException unused4) {
            String str = "org.eclipse.jetty.alpn.ALPN";
            try {
                try {
                    cls2 = Class.forName(str);
                } catch (ClassNotFoundException | NoSuchMethodException unused5) {
                    return new Platform();
                }
            } catch (ClassNotFoundException unused6) {
                str = "org.eclipse.jetty.npn.NextProtoNego";
                cls2 = Class.forName(str);
            }
            Class<?> cls5 = Class.forName(str + "$Provider");
            return new JdkWithJettyBootPlatform(cls2.getMethod("put", SSLSocket.class, cls5), cls2.getMethod("get", SSLSocket.class), Class.forName(str + "$ClientProvider"), Class.forName(str + "$ServerProvider"));
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class Android extends Platform {
        private final Method getNpnSelectedProtocol;
        protected final Class<?> openSslSocketClass;
        private final Method setHostname;
        private final Method setNpnProtocols;
        private final Method setUseSessionTickets;
        private final Method trafficStatsTagSocket;
        private final Method trafficStatsUntagSocket;

        private Android(Class<?> cls, Method method, Method method2, Method method3, Method method4, Method method5, Method method6) {
            this.openSslSocketClass = cls;
            this.setUseSessionTickets = method;
            this.setHostname = method2;
            this.trafficStatsTagSocket = method3;
            this.trafficStatsUntagSocket = method4;
            this.setNpnProtocols = method5;
            this.getNpnSelectedProtocol = method6;
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void connectSocket(Socket socket, InetSocketAddress inetSocketAddress, int i) throws IOException {
            try {
                socket.connect(inetSocketAddress, i);
            } catch (SecurityException e) {
                IOException iOException = new IOException("Exception in connect");
                iOException.initCause(e);
                throw iOException;
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void configureTls(SSLSocket sSLSocket, String str, String str2) {
            super.configureTls(sSLSocket, str, str2);
            if (!str2.equals("TLSv1") || !this.openSslSocketClass.isInstance(sSLSocket)) {
                return;
            }
            try {
                this.setUseSessionTickets.invoke(sSLSocket, true);
                this.setHostname.invoke(sSLSocket, str);
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void setProtocols(SSLSocket sSLSocket, List<Protocol> list) {
            if (this.setNpnProtocols != null && this.openSslSocketClass.isInstance(sSLSocket)) {
                try {
                    this.setNpnProtocols.invoke(sSLSocket, Platform.concatLengthPrefixed(list));
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2);
                }
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public String getSelectedProtocol(SSLSocket sSLSocket) {
            if (this.getNpnSelectedProtocol != null && this.openSslSocketClass.isInstance(sSLSocket)) {
                try {
                    byte[] bArr = (byte[]) this.getNpnSelectedProtocol.invoke(sSLSocket, new Object[0]);
                    if (bArr != null) {
                        return new String(bArr, Util.UTF_8);
                    }
                    return null;
                } catch (IllegalAccessException e) {
                    throw new AssertionError(e);
                } catch (InvocationTargetException e2) {
                    throw new RuntimeException(e2);
                }
            }
            return null;
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void tagSocket(Socket socket) throws SocketException {
            Method method = this.trafficStatsTagSocket;
            if (method == null) {
                return;
            }
            try {
                method.invoke(null, socket);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2);
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void untagSocket(Socket socket) throws SocketException {
            Method method = this.trafficStatsUntagSocket;
            if (method == null) {
                return;
            }
            try {
                method.invoke(null, socket);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            } catch (InvocationTargetException e2) {
                throw new RuntimeException(e2);
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public static class JdkWithJettyBootPlatform extends Platform {
        private final Class<?> clientProviderClass;
        private final Method getMethod;
        private final Method putMethod;
        private final Class<?> serverProviderClass;

        public JdkWithJettyBootPlatform(Method method, Method method2, Class<?> cls, Class<?> cls2) {
            this.putMethod = method;
            this.getMethod = method2;
            this.clientProviderClass = cls;
            this.serverProviderClass = cls2;
        }

        @Override // com.squareup.okhttp.internal.Platform
        public void setProtocols(SSLSocket sSLSocket, List<Protocol> list) {
            try {
                ArrayList arrayList = new ArrayList(list.size());
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    Protocol protocol = list.get(i);
                    if (protocol != Protocol.HTTP_1_0) {
                        arrayList.add(protocol.toString());
                    }
                }
                this.putMethod.invoke(null, sSLSocket, Proxy.newProxyInstance(Platform.class.getClassLoader(), new Class[]{this.clientProviderClass, this.serverProviderClass}, new JettyNegoProvider(arrayList)));
            } catch (IllegalAccessException e) {
                throw new AssertionError(e);
            } catch (InvocationTargetException e2) {
                throw new AssertionError(e2);
            }
        }

        @Override // com.squareup.okhttp.internal.Platform
        public String getSelectedProtocol(SSLSocket sSLSocket) {
            try {
                JettyNegoProvider jettyNegoProvider = (JettyNegoProvider) Proxy.getInvocationHandler(this.getMethod.invoke(null, sSLSocket));
                if (!jettyNegoProvider.unsupported && jettyNegoProvider.selected == null) {
                    Logger.getLogger("com.squareup.okhttp.OkHttpClient").log(Level.INFO, "NPN/ALPN callback dropped: SPDY and HTTP/2 are disabled. Is npn-boot or alpn-boot on the boot class path?");
                    return null;
                } else if (!jettyNegoProvider.unsupported) {
                    return jettyNegoProvider.selected;
                } else {
                    return null;
                }
            } catch (IllegalAccessException unused) {
                throw new AssertionError();
            } catch (InvocationTargetException unused2) {
                throw new AssertionError();
            }
        }
    }

    /* loaded from: classes3.dex */
    private static class JettyNegoProvider implements InvocationHandler {
        private final List<String> protocols;
        private String selected;
        private boolean unsupported;

        public JettyNegoProvider(List<String> list) {
            this.protocols = list;
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            String name = method.getName();
            Class<?> returnType = method.getReturnType();
            if (objArr == null) {
                objArr = Util.EMPTY_STRING_ARRAY;
            }
            if (name.equals("supports") && Boolean.TYPE == returnType) {
                return true;
            }
            if (name.equals("unsupported") && Void.TYPE == returnType) {
                this.unsupported = true;
                return null;
            } else if (name.equals("protocols") && objArr.length == 0) {
                return this.protocols;
            } else {
                if ((name.equals("selectProtocol") || name.equals("select")) && String.class == returnType && objArr.length == 1 && (objArr[0] instanceof List)) {
                    List list = (List) objArr[0];
                    int size = list.size();
                    for (int i = 0; i < size; i++) {
                        if (this.protocols.contains(list.get(i))) {
                            String str = (String) list.get(i);
                            this.selected = str;
                            return str;
                        }
                    }
                    String str2 = this.protocols.get(0);
                    this.selected = str2;
                    return str2;
                } else if ((name.equals("protocolSelected") || name.equals("selected")) && objArr.length == 1) {
                    this.selected = (String) objArr[0];
                    return null;
                } else {
                    return method.invoke(this, objArr);
                }
            }
        }
    }

    static byte[] concatLengthPrefixed(List<Protocol> list) {
        Buffer buffer = new Buffer();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            Protocol protocol = list.get(i);
            if (protocol != Protocol.HTTP_1_0) {
                buffer.mo6807writeByte(protocol.toString().length());
                buffer.mo6814writeUtf8(protocol.toString());
            }
        }
        return buffer.readByteArray();
    }
}
