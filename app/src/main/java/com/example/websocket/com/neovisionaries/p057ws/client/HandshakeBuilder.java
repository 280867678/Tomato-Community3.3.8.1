package com.example.websocket.com.neovisionaries.p057ws.client;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.HandshakeBuilder */
/* loaded from: classes2.dex */
class HandshakeBuilder {

    /* renamed from: RN */
    private static final String f1250RN = "\r\n";
    private List<WebSocketExtension> mExtensions;
    private List<String[]> mHeaders;
    private final String mHost;
    private String mKey;
    private final String mPath;
    private Set<String> mProtocols;
    private boolean mSecure;
    private final URI mUri;
    private String mUserInfo;
    private static final String[] CONNECTION_HEADER = {"Connection", "Upgrade"};
    private static final String[] UPGRADE_HEADER = {"Upgrade", "websocket"};
    private static final String[] VERSION_HEADER = {"Sec-WebSocket-Version", "13"};

    public HandshakeBuilder(boolean z, String str, String str2, String str3) {
        this.mSecure = z;
        this.mUserInfo = str;
        this.mHost = str2;
        this.mPath = str3;
        Object[] objArr = new Object[3];
        objArr[0] = z ? "wss" : "ws";
        objArr[1] = str2;
        objArr[2] = str3;
        this.mUri = URI.create(String.format("%s://%s%s", objArr));
    }

    public HandshakeBuilder(HandshakeBuilder handshakeBuilder) {
        this.mSecure = handshakeBuilder.mSecure;
        this.mUserInfo = handshakeBuilder.mUserInfo;
        this.mHost = handshakeBuilder.mHost;
        this.mPath = handshakeBuilder.mPath;
        this.mUri = handshakeBuilder.mUri;
        this.mKey = handshakeBuilder.mKey;
        this.mProtocols = copyProtocols(handshakeBuilder.mProtocols);
        this.mExtensions = copyExtensions(handshakeBuilder.mExtensions);
        this.mHeaders = copyHeaders(handshakeBuilder.mHeaders);
    }

    public void addProtocol(String str) {
        if (!isValidProtocol(str)) {
            throw new IllegalArgumentException("'protocol' must be a non-empty string with characters in the range U+0021 to U+007E not including separator characters.");
        }
        synchronized (this) {
            if (this.mProtocols == null) {
                this.mProtocols = new LinkedHashSet();
            }
            this.mProtocols.add(str);
        }
    }

    public void removeProtocol(String str) {
        if (str == null) {
            return;
        }
        synchronized (this) {
            if (this.mProtocols == null) {
                return;
            }
            this.mProtocols.remove(str);
            if (this.mProtocols.size() == 0) {
                this.mProtocols = null;
            }
        }
    }

    public void clearProtocols() {
        synchronized (this) {
            this.mProtocols = null;
        }
    }

    private static boolean isValidProtocol(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        for (int i = 0; i < length; i++) {
            char charAt = str.charAt(i);
            if (charAt < '!' || '~' < charAt || Token.isSeparator(charAt)) {
                return false;
            }
        }
        return true;
    }

    public boolean containsProtocol(String str) {
        synchronized (this) {
            if (this.mProtocols == null) {
                return false;
            }
            return this.mProtocols.contains(str);
        }
    }

    public void addExtension(WebSocketExtension webSocketExtension) {
        if (webSocketExtension == null) {
            return;
        }
        synchronized (this) {
            if (this.mExtensions == null) {
                this.mExtensions = new ArrayList();
            }
            this.mExtensions.add(webSocketExtension);
        }
    }

    public void addExtension(String str) {
        addExtension(WebSocketExtension.parse(str));
    }

    public void removeExtension(WebSocketExtension webSocketExtension) {
        if (webSocketExtension == null) {
            return;
        }
        synchronized (this) {
            if (this.mExtensions == null) {
                return;
            }
            this.mExtensions.remove(webSocketExtension);
            if (this.mExtensions.size() == 0) {
                this.mExtensions = null;
            }
        }
    }

    public void removeExtensions(String str) {
        if (str == null) {
            return;
        }
        synchronized (this) {
            if (this.mExtensions == null) {
                return;
            }
            ArrayList<WebSocketExtension> arrayList = new ArrayList();
            for (WebSocketExtension webSocketExtension : this.mExtensions) {
                if (webSocketExtension.getName().equals(str)) {
                    arrayList.add(webSocketExtension);
                }
            }
            for (WebSocketExtension webSocketExtension2 : arrayList) {
                this.mExtensions.remove(webSocketExtension2);
            }
            if (this.mExtensions.size() == 0) {
                this.mExtensions = null;
            }
        }
    }

    public void clearExtensions() {
        synchronized (this) {
            this.mExtensions = null;
        }
    }

    public boolean containsExtension(WebSocketExtension webSocketExtension) {
        if (webSocketExtension == null) {
            return false;
        }
        synchronized (this) {
            if (this.mExtensions == null) {
                return false;
            }
            return this.mExtensions.contains(webSocketExtension);
        }
    }

    public boolean containsExtension(String str) {
        if (str == null) {
            return false;
        }
        synchronized (this) {
            if (this.mExtensions == null) {
                return false;
            }
            for (WebSocketExtension webSocketExtension : this.mExtensions) {
                if (webSocketExtension.getName().equals(str)) {
                    return true;
                }
            }
            return false;
        }
    }

    public void addHeader(String str, String str2) {
        if (str == null || str.length() == 0) {
            return;
        }
        if (str2 == null) {
            str2 = "";
        }
        synchronized (this) {
            if (this.mHeaders == null) {
                this.mHeaders = new ArrayList();
            }
            this.mHeaders.add(new String[]{str, str2});
        }
    }

    public void removeHeaders(String str) {
        if (str == null || str.length() == 0) {
            return;
        }
        synchronized (this) {
            if (this.mHeaders == null) {
                return;
            }
            ArrayList<String[]> arrayList = new ArrayList();
            for (String[] strArr : this.mHeaders) {
                if (strArr[0].equals(str)) {
                    arrayList.add(strArr);
                }
            }
            for (String[] strArr2 : arrayList) {
                this.mHeaders.remove(strArr2);
            }
            if (this.mHeaders.size() == 0) {
                this.mHeaders = null;
            }
        }
    }

    public void clearHeaders() {
        synchronized (this) {
            this.mHeaders = null;
        }
    }

    public void setUserInfo(String str) {
        synchronized (this) {
            this.mUserInfo = str;
        }
    }

    public void setUserInfo(String str, String str2) {
        if (str == null) {
            str = "";
        }
        if (str2 == null) {
            str2 = "";
        }
        setUserInfo(String.format("%s:%s", str, str2));
    }

    public void clearUserInfo() {
        synchronized (this) {
            this.mUserInfo = null;
        }
    }

    public URI getURI() {
        return this.mUri;
    }

    public void setKey(String str) {
        this.mKey = str;
    }

    public String buildRequestLine() {
        return String.format("GET %s HTTP/1.1", this.mPath);
    }

    public List<String[]> buildHeaders() {
        ArrayList arrayList = new ArrayList();
        arrayList.add(new String[]{"Host", this.mHost});
        arrayList.add(CONNECTION_HEADER);
        arrayList.add(UPGRADE_HEADER);
        arrayList.add(VERSION_HEADER);
        arrayList.add(new String[]{"Sec-WebSocket-Key", this.mKey});
        Set<String> set = this.mProtocols;
        if (set != null && set.size() != 0) {
            arrayList.add(new String[]{"Sec-WebSocket-Protocol", Misc.join(this.mProtocols, ", ")});
        }
        List<WebSocketExtension> list = this.mExtensions;
        if (list != null && list.size() != 0) {
            arrayList.add(new String[]{"Sec-WebSocket-Extensions", Misc.join(this.mExtensions, ", ")});
        }
        String str = this.mUserInfo;
        if (str != null && str.length() != 0) {
            arrayList.add(new String[]{"Authorization", "Basic " + Base64.encode(this.mUserInfo)});
        }
        List<String[]> list2 = this.mHeaders;
        if (list2 != null && list2.size() != 0) {
            arrayList.addAll(this.mHeaders);
        }
        return arrayList;
    }

    public static String build(String str, List<String[]> list) {
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append(f1250RN);
        for (String[] strArr : list) {
            sb.append(strArr[0]);
            sb.append(": ");
            sb.append(strArr[1]);
            sb.append(f1250RN);
        }
        sb.append(f1250RN);
        return sb.toString();
    }

    private static Set<String> copyProtocols(Set<String> set) {
        if (set == null) {
            return null;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet(set.size());
        linkedHashSet.addAll(set);
        return linkedHashSet;
    }

    private static List<WebSocketExtension> copyExtensions(List<WebSocketExtension> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (WebSocketExtension webSocketExtension : list) {
            arrayList.add(new WebSocketExtension(webSocketExtension));
        }
        return arrayList;
    }

    private static List<String[]> copyHeaders(List<String[]> list) {
        if (list == null) {
            return null;
        }
        ArrayList arrayList = new ArrayList(list.size());
        for (String[] strArr : list) {
            arrayList.add(copyHeader(strArr));
        }
        return arrayList;
    }

    private static String[] copyHeader(String[] strArr) {
        return new String[]{strArr[0], strArr[1]};
    }
}
