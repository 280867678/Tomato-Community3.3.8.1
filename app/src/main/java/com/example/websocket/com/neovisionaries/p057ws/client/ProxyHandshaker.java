package com.example.websocket.com.neovisionaries.p057ws.client;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.List;
import java.util.Map;

/* JADX INFO: Access modifiers changed from: package-private */
/* renamed from: com.example.websocket.com.neovisionaries.ws.client.ProxyHandshaker */
/* loaded from: classes2.dex */
public class ProxyHandshaker {

    /* renamed from: RN */
    private static final String f1251RN = "\r\n";
    private final String mHost;
    private final int mPort;
    private final ProxySettings mSettings;
    private final Socket mSocket;

    public ProxyHandshaker(Socket socket, String str, int i, ProxySettings proxySettings) {
        this.mSocket = socket;
        this.mHost = str;
        this.mPort = i;
        this.mSettings = proxySettings;
    }

    public void perform() throws IOException {
        sendRequest();
        receiveResponse();
    }

    private void sendRequest() throws IOException {
        byte[] bytesUTF8 = Misc.getBytesUTF8(buildRequest());
        OutputStream outputStream = this.mSocket.getOutputStream();
        outputStream.write(bytesUTF8);
        outputStream.flush();
    }

    private String buildRequest() {
        String format = String.format("%s:%d", this.mHost, Integer.valueOf(this.mPort));
        StringBuilder sb = new StringBuilder();
        sb.append("CONNECT ");
        sb.append(format);
        sb.append(" HTTP/1.1");
        sb.append(f1251RN);
        sb.append("Host: ");
        sb.append(format);
        sb.append(f1251RN);
        addHeaders(sb);
        addProxyAuthorization(sb);
        sb.append(f1251RN);
        return sb.toString();
    }

    private void addHeaders(StringBuilder sb) {
        for (Map.Entry<String, List<String>> entry : this.mSettings.getHeaders().entrySet()) {
            String key = entry.getKey();
            for (String str : entry.getValue()) {
                if (str == null) {
                    str = "";
                }
                sb.append(key);
                sb.append(": ");
                sb.append(str);
                sb.append(f1251RN);
            }
        }
    }

    private void addProxyAuthorization(StringBuilder sb) {
        String id = this.mSettings.getId();
        if (id == null || id.length() == 0) {
            return;
        }
        String password = this.mSettings.getPassword();
        if (password == null) {
            password = "";
        }
        String format = String.format("%s:%s", id, password);
        sb.append("Proxy-Authorization: Basic ");
        sb.append(Base64.encode(format));
        sb.append(f1251RN);
    }

    private void receiveResponse() throws IOException {
        InputStream inputStream = this.mSocket.getInputStream();
        readStatusLine(inputStream);
        skipHeaders(inputStream);
    }

    private void readStatusLine(InputStream inputStream) throws IOException {
        String readLine = Misc.readLine(inputStream, "UTF-8");
        if (readLine == null || readLine.length() == 0) {
            throw new IOException("The response from the proxy server does not contain a status line.");
        }
        String[] split = readLine.split(" +", 3);
        if (split.length < 2) {
            throw new IOException("The status line in the response from the proxy server is badly formatted. The status line is: " + readLine);
        } else if ("200".equals(split[1])) {
        } else {
            throw new IOException("The status code in the response from the proxy server is not '200 Connection established'. The status line is: " + readLine);
        }
    }

    private void skipHeaders(InputStream inputStream) throws IOException {
        while (true) {
            int i = 0;
            while (true) {
                int read = inputStream.read();
                if (read != -1) {
                    if (read == 10) {
                        if (i == 0) {
                            return;
                        }
                    } else if (read != 13) {
                        i++;
                    } else {
                        int read2 = inputStream.read();
                        if (read2 == -1) {
                            throw new EOFException("The end of the stream from the proxy server was reached unexpectedly after a carriage return.");
                        }
                        if (read2 != 10) {
                            i += 2;
                        } else if (i == 0) {
                            return;
                        }
                    }
                } else {
                    throw new EOFException("The end of the stream from the proxy server was reached unexpectedly.");
                }
            }
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public String getProxiedHostname() {
        return this.mHost;
    }
}
