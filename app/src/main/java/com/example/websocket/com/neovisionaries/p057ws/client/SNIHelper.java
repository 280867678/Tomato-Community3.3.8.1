package com.example.websocket.com.neovisionaries.p057ws.client;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.SNIHelper */
/* loaded from: classes2.dex */
class SNIHelper {
    private static Constructor<?> sSNIHostNameConstructor;
    private static Method sSetServerNamesMethod;

    SNIHelper() {
    }

    static {
        try {
            initialize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void initialize() throws Exception {
        sSNIHostNameConstructor = Misc.getConstructor("javax.net.ssl.SNIHostName", new Class[]{String.class});
        sSetServerNamesMethod = Misc.getMethod("javax.net.ssl.SSLParameters", "setServerNames", new Class[]{List.class});
    }

    private static Object createSNIHostName(String str) {
        return Misc.newInstance(sSNIHostNameConstructor, str);
    }

    private static List<Object> createSNIHostNames(String[] strArr) {
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            arrayList.add(createSNIHostName(str));
        }
        return arrayList;
    }

    private static void setServerNames(SSLParameters sSLParameters, String[] strArr) {
        Misc.invoke(sSetServerNamesMethod, sSLParameters, createSNIHostNames(strArr));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static void setServerNames(Socket socket, String[] strArr) {
        SSLParameters sSLParameters;
        if (!(socket instanceof SSLSocket) || strArr == null || (sSLParameters = ((SSLSocket) socket).getSSLParameters()) == null) {
            return;
        }
        setServerNames(sSLParameters, strArr);
    }
}
