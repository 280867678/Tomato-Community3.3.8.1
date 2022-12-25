package com.example.websocket.com.neovisionaries.p057ws.client;

import com.tomatolive.library.utils.ConstantUtils;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/* renamed from: com.example.websocket.com.neovisionaries.ws.client.HandshakeReader */
/* loaded from: classes2.dex */
class HandshakeReader {
    private static final String ACCEPT_MAGIC = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private final WebSocket mWebSocket;

    public HandshakeReader(WebSocket webSocket) {
        this.mWebSocket = webSocket;
    }

    public Map<String, List<String>> readHandshake(WebSocketInputStream webSocketInputStream, String str) throws WebSocketException {
        StatusLine readStatusLine = readStatusLine(webSocketInputStream);
        Map<String, List<String>> readHttpHeaders = readHttpHeaders(webSocketInputStream);
        validateStatusLine(readStatusLine, readHttpHeaders, webSocketInputStream);
        validateUpgrade(readStatusLine, readHttpHeaders);
        validateConnection(readStatusLine, readHttpHeaders);
        validateAccept(readStatusLine, readHttpHeaders, str);
        validateExtensions(readStatusLine, readHttpHeaders);
        validateProtocol(readStatusLine, readHttpHeaders);
        return readHttpHeaders;
    }

    private StatusLine readStatusLine(WebSocketInputStream webSocketInputStream) throws WebSocketException {
        try {
            String readLine = webSocketInputStream.readLine();
            if (readLine == null || readLine.length() == 0) {
                throw new WebSocketException(WebSocketError.STATUS_LINE_EMPTY, "The status line of the opening handshake response is empty.");
            }
            try {
                return new StatusLine(readLine);
            } catch (Exception unused) {
                WebSocketError webSocketError = WebSocketError.STATUS_LINE_BAD_FORMAT;
                throw new WebSocketException(webSocketError, "The status line of the opening handshake response is badly formatted. The status line is: " + readLine);
            }
        } catch (IOException e) {
            WebSocketError webSocketError2 = WebSocketError.OPENING_HANDSHAKE_RESPONSE_FAILURE;
            throw new WebSocketException(webSocketError2, "Failed to read an opening handshake response from the server: " + e.getMessage(), e);
        }
    }

    private Map<String, List<String>> readHttpHeaders(WebSocketInputStream webSocketInputStream) throws WebSocketException {
        TreeMap treeMap = new TreeMap(String.CASE_INSENSITIVE_ORDER);
        StringBuilder sb = null;
        while (true) {
            try {
                String readLine = webSocketInputStream.readLine();
                if (readLine == null || readLine.length() == 0) {
                    break;
                }
                char charAt = readLine.charAt(0);
                if (charAt != ' ' && charAt != '\t') {
                    if (sb != null) {
                        parseHttpHeader(treeMap, sb.toString());
                    }
                    sb = new StringBuilder(readLine);
                } else if (sb != null) {
                    sb.append(readLine.replaceAll("^[ \t]+", ConstantUtils.PLACEHOLDER_STR_ONE));
                }
            } catch (IOException e) {
                WebSocketError webSocketError = WebSocketError.HTTP_HEADER_FAILURE;
                throw new WebSocketException(webSocketError, "An error occurred while HTTP header section was being read: " + e.getMessage(), e);
            }
        }
        if (sb != null) {
            parseHttpHeader(treeMap, sb.toString());
        }
        return treeMap;
    }

    private void parseHttpHeader(Map<String, List<String>> map, String str) {
        String[] split = str.split(":", 2);
        if (split.length < 2) {
            return;
        }
        String trim = split[0].trim();
        String trim2 = split[1].trim();
        List<String> list = map.get(trim);
        if (list == null) {
            list = new ArrayList<>();
            map.put(trim, list);
        }
        list.add(trim2);
    }

    private void validateStatusLine(StatusLine statusLine, Map<String, List<String>> map, WebSocketInputStream webSocketInputStream) throws WebSocketException {
        if (statusLine.getStatusCode() == 101) {
            return;
        }
        byte[] readBody = readBody(map, webSocketInputStream);
        WebSocketError webSocketError = WebSocketError.NOT_SWITCHING_PROTOCOLS;
        throw new OpeningHandshakeException(webSocketError, "The status code of the opening handshake response is not '101 Switching Protocols'. The status line is: " + statusLine, statusLine, map, readBody);
    }

    private byte[] readBody(Map<String, List<String>> map, WebSocketInputStream webSocketInputStream) {
        int contentLength = getContentLength(map);
        if (contentLength <= 0) {
            return null;
        }
        try {
            byte[] bArr = new byte[contentLength];
            webSocketInputStream.readBytes(bArr, contentLength);
            return bArr;
        } catch (Throwable unused) {
            return null;
        }
    }

    private int getContentLength(Map<String, List<String>> map) {
        try {
            return Integer.parseInt(map.get("Content-Length").get(0));
        } catch (Exception unused) {
            return -1;
        }
    }

    private void validateUpgrade(StatusLine statusLine, Map<String, List<String>> map) throws WebSocketException {
        List<String> list = map.get("Upgrade");
        if (list == null || list.size() == 0) {
            throw new OpeningHandshakeException(WebSocketError.NO_UPGRADE_HEADER, "The opening handshake response does not contain 'Upgrade' header.", statusLine, map);
        }
        for (String str : list) {
            for (String str2 : str.split("\\s*,\\s*")) {
                if ("websocket".equalsIgnoreCase(str2)) {
                    return;
                }
            }
        }
        throw new OpeningHandshakeException(WebSocketError.NO_WEBSOCKET_IN_UPGRADE_HEADER, "'websocket' was not found in 'Upgrade' header.", statusLine, map);
    }

    private void validateConnection(StatusLine statusLine, Map<String, List<String>> map) throws WebSocketException {
        List<String> list = map.get("Connection");
        if (list == null || list.size() == 0) {
            throw new OpeningHandshakeException(WebSocketError.NO_CONNECTION_HEADER, "The opening handshake response does not contain 'Connection' header.", statusLine, map);
        }
        for (String str : list) {
            for (String str2 : str.split("\\s*,\\s*")) {
                if ("Upgrade".equalsIgnoreCase(str2)) {
                    return;
                }
            }
        }
        throw new OpeningHandshakeException(WebSocketError.NO_UPGRADE_IN_CONNECTION_HEADER, "'Upgrade' was not found in 'Connection' header.", statusLine, map);
    }

    private void validateAccept(StatusLine statusLine, Map<String, List<String>> map, String str) throws WebSocketException {
        List<String> list = map.get("Sec-WebSocket-Accept");
        if (list == null) {
            throw new OpeningHandshakeException(WebSocketError.NO_SEC_WEBSOCKET_ACCEPT_HEADER, "The opening handshake response does not contain 'Sec-WebSocket-Accept' header.", statusLine, map);
        }
        String str2 = list.get(0);
        try {
            if (!Base64.encode(MessageDigest.getInstance("SHA-1").digest(Misc.getBytesUTF8(str + ACCEPT_MAGIC))).equals(str2)) {
                throw new OpeningHandshakeException(WebSocketError.UNEXPECTED_SEC_WEBSOCKET_ACCEPT_HEADER, "The value of 'Sec-WebSocket-Accept' header is different from the expected one.", statusLine, map);
            }
        } catch (Exception unused) {
        }
    }

    private void validateExtensions(StatusLine statusLine, Map<String, List<String>> map) throws WebSocketException {
        String[] split;
        List<String> list = map.get("Sec-WebSocket-Extensions");
        if (list == null || list.size() == 0) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        for (String str : list) {
            for (String str2 : str.split("\\s*,\\s*")) {
                WebSocketExtension parse = WebSocketExtension.parse(str2);
                if (parse == null) {
                    throw new OpeningHandshakeException(WebSocketError.EXTENSION_PARSE_ERROR, "The value in 'Sec-WebSocket-Extensions' failed to be parsed: " + str2, statusLine, map);
                }
                String name = parse.getName();
                if (!this.mWebSocket.getHandshakeBuilder().containsExtension(name)) {
                    throw new OpeningHandshakeException(WebSocketError.UNSUPPORTED_EXTENSION, "The extension contained in the Sec-WebSocket-Extensions header is not supported: " + name, statusLine, map);
                }
                parse.validate();
                arrayList.add(parse);
            }
        }
        validateExtensionCombination(statusLine, map, arrayList);
        this.mWebSocket.setAgreedExtensions(arrayList);
    }

    private void validateExtensionCombination(StatusLine statusLine, Map<String, List<String>> map, List<WebSocketExtension> list) throws WebSocketException {
        WebSocketExtension webSocketExtension = null;
        for (WebSocketExtension webSocketExtension2 : list) {
            if (webSocketExtension2 instanceof PerMessageCompressionExtension) {
                if (webSocketExtension != null) {
                    throw new OpeningHandshakeException(WebSocketError.EXTENSIONS_CONFLICT, String.format("'%s' extension and '%s' extension conflict with each other.", webSocketExtension.getName(), webSocketExtension2.getName()), statusLine, map);
                }
                webSocketExtension = webSocketExtension2;
            }
        }
    }

    private void validateProtocol(StatusLine statusLine, Map<String, List<String>> map) throws WebSocketException {
        String str;
        List<String> list = map.get("Sec-WebSocket-Protocol");
        if (list == null || (str = list.get(0)) == null || str.length() == 0) {
            return;
        }
        if (!this.mWebSocket.getHandshakeBuilder().containsProtocol(str)) {
            WebSocketError webSocketError = WebSocketError.UNSUPPORTED_PROTOCOL;
            throw new OpeningHandshakeException(webSocketError, "The protocol contained in the Sec-WebSocket-Protocol header is not supported: " + str, statusLine, map);
        }
        this.mWebSocket.setAgreedProtocol(str);
    }
}
