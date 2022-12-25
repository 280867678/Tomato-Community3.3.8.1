package com.gen.p059mh.webapps.utils;

import android.os.Build;
import android.webkit.MimeTypeMap;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import com.gen.p059mh.webapps.listener.RequestResultListener;
import com.gen.p059mh.webapps.utils.Request;
import com.gen.p059mh.webapps.utils.ResourcesLoader;
import com.gen.p059mh.webapps.utils.Utils;
import com.tomatolive.library.http.utils.EncryptUtil;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.slf4j.Marker;

/* renamed from: com.gen.mh.webapps.utils.InjectJsContextUtils */
/* loaded from: classes2.dex */
public class InjectJsContextUtils {
    String defaultPath;
    ResourcesLoader.ResourceType resourceType;
    WACrypto waCrypto;
    boolean aem = false;
    final ArrayList<File> jsFiles = new ArrayList<>();
    Map<String, String> cookieMap = new HashMap();

    public void setAem(boolean z) {
        this.aem = z;
    }

    public InjectJsContextUtils(String str, ResourcesLoader.ResourceType resourceType) {
        this.defaultPath = str;
        this.resourceType = resourceType;
    }

    public WebResourceResponse loadingPage(WebView webView, String str) {
        return connectPage(str, webView, null);
    }

    public WebResourceResponse loadingPage(WebView webView, WebResourceRequest webResourceRequest) {
        if (Build.VERSION.SDK_INT >= 21) {
            return connectPage(webResourceRequest.getUrl().toString(), webView, webResourceRequest);
        }
        return null;
    }

    public WebResourceResponse connectPage(String str, final WebView webView, WebResourceRequest webResourceRequest) {
        final Map<String, String> hashMap;
        String str2;
        WebResourceResponse webResourceResponse;
        try {
            String str3 = this.defaultPath;
            if (webResourceRequest != null && Build.VERSION.SDK_INT >= 21) {
                hashMap = webResourceRequest.getRequestHeaders();
            } else {
                hashMap = new HashMap<>();
            }
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            webView.post(new Runnable() { // from class: com.gen.mh.webapps.utils.-$$Lambda$InjectJsContextUtils$zpbk-Bq5im83F_nQfKU4QG47C1c
                @Override // java.lang.Runnable
                public final void run() {
                    InjectJsContextUtils.lambda$connectPage$0(hashMap, webView, countDownLatch);
                }
            });
            countDownLatch.await();
            Connection connect = Jsoup.connect(str);
            connect.followRedirects(false);
            connect.headers(hashMap);
            connect.cookies(this.cookieMap);
            Connection.Response execute = connect.execute();
            if (execute.statusCode() == 200) {
                byte[] bodyAsBytes = execute.bodyAsBytes();
                if (execute.header("Content-Type").contains("text/html")) {
                    Document parse = execute.parse();
                    if (parse.head() != null) {
                        Element head = parse.head();
                        if (this.jsFiles.size() == 0) {
                            jsFilesInit();
                        }
                        ArrayList<String> arrayList = new ArrayList();
                        ArrayList<String> arrayList2 = new ArrayList();
                        Iterator<File> it2 = this.jsFiles.iterator();
                        while (it2.hasNext()) {
                            File next = it2.next();
                            StringBuilder sb = new StringBuilder();
                            sb.append("<script type=\"application/javascript\" src=\"http://");
                            sb.append(ResourcesLoader.DEFAULTS_HOST);
                            Iterator<File> it3 = it2;
                            sb.append(next.getAbsolutePath().replace(str3, ""));
                            sb.append("\"></script>\n");
                            String sb2 = sb.toString();
                            if (Utils.hasSuffix(next.getAbsolutePath(), ".pre.js")) {
                                arrayList.add(sb2);
                            } else {
                                arrayList2.add(sb2);
                            }
                            it2 = it3;
                        }
                        for (String str4 : arrayList) {
                            head.appendChild(new DataNode(str4));
                        }
                        for (String str5 : arrayList2) {
                            head.appendChild(new DataNode(str5));
                        }
                    }
                    str2 = parse.charset().toString();
                    bodyAsBytes = parse.toString().getBytes();
                } else {
                    str2 = "UTF-8";
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    webResourceResponse = new WebResourceResponse("text/html", str2, execute.statusCode(), execute.statusMessage(), execute.headers(), new ByteArrayInputStream(bodyAsBytes));
                } else {
                    webResourceResponse = new WebResourceResponse("text/html", str2, new ByteArrayInputStream(bodyAsBytes));
                }
                if (Build.VERSION.SDK_INT >= 21) {
                    Map<String, String> responseHeaders = webResourceResponse.getResponseHeaders();
                    if (!responseHeaders.containsKey("Access-Control-Allow-Origin")) {
                        responseHeaders.put("Access-Control-Allow-Origin", Marker.ANY_MARKER);
                    }
                    if (!responseHeaders.containsKey("Access-Control-Allow-Credentials")) {
                        responseHeaders.put("Access-Control-Allow-Credentials", "true");
                    }
                    if (!responseHeaders.containsKey("Access-Control-Max-Age")) {
                        responseHeaders.put("Access-Control-Max-Age", "1800");
                    }
                    if (!responseHeaders.containsKey("Access-Control-Allow-Methods")) {
                        responseHeaders.put("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
                    }
                    if (!responseHeaders.containsKey("Access-Control-Allow-Headers")) {
                        responseHeaders.put("Access-Control-Allow-Headers", Marker.ANY_MARKER);
                    }
                    webResourceResponse.setResponseHeaders(responseHeaders);
                }
                return webResourceResponse;
            }
            if (execute.statusCode() != 302 && execute.statusCode() != 301) {
                if (Build.VERSION.SDK_INT >= 21) {
                    return new WebResourceResponse(execute.contentType(), execute.charset(), execute.statusCode(), execute.statusMessage(), execute.headers(), execute.bodyStream());
                }
                return new WebResourceResponse(execute.contentType(), execute.charset(), execute.bodyStream());
            }
            if (execute.cookies() != null) {
                this.cookieMap = execute.cookies();
            }
            if (Build.VERSION.SDK_INT >= 21) {
                return new WebResourceResponse("text/html", EncryptUtil.CHARSET, 200, "OK", execute.headers(), null);
            }
            return new WebResourceResponse(execute.contentType(), execute.charset(), null);
        } catch (Exception e) {
            e.printStackTrace();
            return new WebResourceResponse("text/html", EncryptUtil.CHARSET, null);
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$connectPage$0(Map map, WebView webView, CountDownLatch countDownLatch) {
        try {
            map.put("User-agent", webView.getSettings().getUserAgentString());
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void putCooke(List<String> list) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < list.size(); i++) {
            stringBuffer.append(list.get(i));
            stringBuffer.append(";");
        }
    }

    public Map<String, String> getCookie() {
        return this.cookieMap;
    }

    public void resetCookie() {
        this.cookieMap = new HashMap();
    }

    public WebResourceResponse doLoadPage(final WebView webView, String str, String str2, Map<String, String> map) {
        final WebResourceResponse[] webResourceResponseArr = new WebResourceResponse[1];
        final CountDownLatch countDownLatch = new CountDownLatch(1);
        try {
            final CountDownLatch countDownLatch2 = new CountDownLatch(1);
            URL url = new URL(str);
            final Request request = new Request();
            request.setUrl(url);
            final String[] strArr = new String[1];
            webView.post(new Runnable() { // from class: com.gen.mh.webapps.utils.-$$Lambda$InjectJsContextUtils$_qj909sT289PZ7-5kHWbhI9Q6RE
                @Override // java.lang.Runnable
                public final void run() {
                    InjectJsContextUtils.lambda$doLoadPage$1(strArr, webView, request, countDownLatch2);
                }
            });
            countDownLatch2.await();
            request.setRequestHeaders(map);
            request.setMethod(str2);
            request.setRequestListener(new RequestResultListener() { // from class: com.gen.mh.webapps.utils.InjectJsContextUtils.1
                @Override // com.gen.p059mh.webapps.utils.Request.RequestListener
                public void onFail(int i, String str3) {
                    webResourceResponseArr[0] = null;
                    countDownLatch.countDown();
                }

                @Override // com.gen.p059mh.webapps.listener.RequestResultListener
                protected void onComplete(Request.Response response, byte[] bArr) {
                    String str3;
                    HashMap hashMap = new HashMap();
                    for (String str4 : response.headers.keySet()) {
                        hashMap.put(str4, response.headers.get(str4).get(0).toString());
                    }
                    String str5 = (String) hashMap.get("Content-Type");
                    ArrayList arrayList = new ArrayList();
                    if (str5.contains(";")) {
                        Logger.m4115e("mimeType,uncorrect:" + str5);
                        arrayList.addAll(Arrays.asList(str5.split(";")));
                    } else {
                        arrayList.add(str5);
                    }
                    Logger.m4114e("mimeType", arrayList);
                    Iterator it2 = arrayList.iterator();
                    while (true) {
                        if (!it2.hasNext()) {
                            str3 = str5;
                            break;
                        }
                        String str6 = (String) it2.next();
                        if (MimeTypeMap.getSingleton().hasMimeType(str6)) {
                            Logger.m4115e("mimeType,correct:" + str6);
                            str3 = str6;
                            break;
                        }
                    }
                    if (str3 != null && "text/html".equalsIgnoreCase(str3)) {
                        bArr = InjectJsContextUtils.this.html(bArr);
                    }
                    Logger.m4114e("mimeType", str3);
                    if (Build.VERSION.SDK_INT >= 21) {
                        WebResourceResponse[] webResourceResponseArr2 = webResourceResponseArr;
                        int i = response.statusCode;
                        if (bArr == null) {
                            bArr = new byte[0];
                        }
                        webResourceResponseArr2[0] = new WebResourceResponse(str3, "UTF-8", i, "OK", hashMap, new ByteArrayInputStream(bArr));
                    } else {
                        WebResourceResponse[] webResourceResponseArr3 = webResourceResponseArr;
                        if (bArr == null) {
                            bArr = new byte[0];
                        }
                        webResourceResponseArr3[0] = new WebResourceResponse(str3, null, new ByteArrayInputStream(bArr));
                    }
                    countDownLatch.countDown();
                }
            });
            request.start();
            countDownLatch.await();
            if (webResourceResponseArr[0] != null && Build.VERSION.SDK_INT >= 21) {
                Map<String, String> responseHeaders = webResourceResponseArr[0].getResponseHeaders();
                if (!responseHeaders.containsKey("Access-Control-Allow-Origin")) {
                    responseHeaders.put("Access-Control-Allow-Origin", "http://" + ResourcesLoader.WORK_HOST);
                }
                if (!responseHeaders.containsKey("Access-Control-Allow-Credentials")) {
                    responseHeaders.put("Access-Control-Allow-Credentials", "true");
                }
                if (!responseHeaders.containsKey("Access-Control-Max-Age")) {
                    responseHeaders.put("Access-Control-Max-Age", "1800");
                }
                if (!responseHeaders.containsKey("Access-Control-Allow-Methods")) {
                    responseHeaders.put("Access-Control-Allow-Methods", "GET,HEAD,OPTIONS,POST,PUT");
                }
                if (!responseHeaders.containsKey("Access-Control-Allow-Headers")) {
                    responseHeaders.put("Access-Control-Allow-Headers", Marker.ANY_MARKER);
                }
                webResourceResponseArr[0].setResponseHeaders(responseHeaders);
            }
            return webResourceResponseArr[0];
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ void lambda$doLoadPage$1(String[] strArr, WebView webView, Request request, CountDownLatch countDownLatch) {
        try {
            strArr[0] = webView.getOriginalUrl();
            request.setRequestHeaders("User-agent", webView.getSettings().getUserAgentString());
            countDownLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public byte[] html(byte[] bArr) {
        String str = this.defaultPath;
        Document parse = Jsoup.parse(new String(bArr), EncryptUtil.CHARSET);
        if (parse != null && parse.head() != null) {
            Element head = parse.head();
            if (this.jsFiles.size() == 0) {
                jsFilesInit();
            }
            Iterator<File> it2 = this.jsFiles.iterator();
            while (it2.hasNext()) {
                head.appendChild(new DataNode("<script type=\"application/javascript\" src=\"http://" + ResourcesLoader.DEFAULTS_HOST + it2.next().getAbsolutePath().replace(str, "") + "\"></script>\n"));
            }
        }
        return parse != null ? parse.toString().getBytes() : new byte[0];
    }

    private void jsFilesInit() {
        String str = this.defaultPath;
        ResourcesLoader.ListHandler listHandler = new ResourcesLoader.ListHandler() { // from class: com.gen.mh.webapps.utils.-$$Lambda$InjectJsContextUtils$64kS9RAWRQs0POOKkKe61XRuKIs
            @Override // com.gen.p059mh.webapps.utils.ResourcesLoader.ListHandler
            public final void onList(File file, boolean z) {
                InjectJsContextUtils.this.lambda$jsFilesInit$2$InjectJsContextUtils(file, z);
            }
        };
        ResourcesLoader.listAllFile(new File(str + "/android"), listHandler);
        ResourcesLoader.listAllFile(new File(str + "/web"), listHandler);
        ResourcesLoader.listAllFile(new File(str), listHandler);
        if (!this.aem) {
            ResourcesLoader.listAllFile(new File(str + "/api"), listHandler);
        }
        ResourcesLoader.ResourceType resourceType = this.resourceType;
        if (resourceType != null) {
            int size = resourceType.getDirs().size();
            for (int i = 0; i < size; i++) {
                ResourcesLoader.listAllFile(new File(str + "/" + this.resourceType.getDirs().get(i)), listHandler);
            }
        }
        Collections.sort(this.jsFiles, $$Lambda$InjectJsContextUtils$_9OXuFXEnUv3gOfkQa8W6ZYsSkk.INSTANCE);
    }

    public /* synthetic */ void lambda$jsFilesInit$2$InjectJsContextUtils(File file, boolean z) {
        if (z) {
            this.jsFiles.add(file);
        }
    }

    public String importResource(String str) {
        String str2 = this.defaultPath + str.replace("http://" + ResourcesLoader.DEFAULTS_HOST, "");
        Logger.m4114e("importResource", str2);
        return new String(Utils.loadData(str2, Utils.ENCODE_TYPE.DEFAULT, this.waCrypto));
    }

    public String inject(String str, String str2, String str3) {
        int indexOf;
        String str4;
        int indexOf2;
        if (str.indexOf("<head>") + 6 > 0) {
            str4 = str.substring(0, indexOf) + str3 + str.substring(indexOf, str.length());
        } else {
            str4 = "<head>" + str3 + "</head>" + str;
        }
        if (str4.indexOf("<head>") + 6 > 0) {
            return str4.substring(0, indexOf2) + "\n" + str2 + str4.substring(indexOf2, str4.length());
        }
        return str4;
    }
}
