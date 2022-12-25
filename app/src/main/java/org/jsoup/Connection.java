package org.jsoup;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.net.URL;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;

/* loaded from: classes4.dex */
public interface Connection {

    /* loaded from: classes4.dex */
    public interface Base<T extends Base> {
        T cookie(String str, String str2);

        Map<String, String> cookies();

        boolean hasHeader(String str);

        String header(String str);

        T header(String str, String str2);

        Map<String, String> headers();

        T method(Method method);

        Method method();

        Map<String, List<String>> multiHeaders();

        T removeHeader(String str);

        URL url();

        T url(URL url);
    }

    /* loaded from: classes4.dex */
    public interface KeyVal {
        String contentType();

        boolean hasInputStream();

        InputStream inputStream();

        String key();

        String value();
    }

    /* loaded from: classes4.dex */
    public interface Request extends Base<Request> {
        Collection<KeyVal> data();

        Request followRedirects(boolean z);

        boolean followRedirects();

        boolean ignoreContentType();

        boolean ignoreHttpErrors();

        int maxBodySize();

        /* renamed from: parser */
        Request mo6823parser(Parser parser);

        Parser parser();

        String postDataCharset();

        Proxy proxy();

        String requestBody();

        Request requestBody(String str);

        SSLSocketFactory sslSocketFactory();

        int timeout();
    }

    /* loaded from: classes4.dex */
    public interface Response extends Base<Response> {
        byte[] bodyAsBytes();

        BufferedInputStream bodyStream();

        String charset();

        String contentType();

        Document parse() throws IOException;

        int statusCode();

        String statusMessage();
    }

    Connection cookies(Map<String, String> map);

    Response execute() throws IOException;

    Connection followRedirects(boolean z);

    Connection headers(Map<String, String> map);

    Connection url(String str);

    /* loaded from: classes4.dex */
    public enum Method {
        GET(false),
        POST(true),
        PUT(true),
        DELETE(false),
        PATCH(true),
        HEAD(false),
        OPTIONS(false),
        TRACE(false);
        
        private final boolean hasBody;

        Method(boolean z) {
            this.hasBody = z;
        }

        public final boolean hasBody() {
            return this.hasBody;
        }
    }
}
