package org.xutils.http.body;

import android.net.Uri;
import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import org.xutils.common.util.KeyValue;

/* loaded from: classes4.dex */
public class UrlEncodedParamsBody implements RequestBody {
    private String charset;
    private byte[] content;

    @Override // org.xutils.http.body.RequestBody
    public void setContentType(String str) {
    }

    public UrlEncodedParamsBody(List<KeyValue> list, String str) throws IOException {
        this.charset = "UTF-8";
        if (!TextUtils.isEmpty(str)) {
            this.charset = str;
        }
        StringBuilder sb = new StringBuilder();
        if (list != null) {
            for (KeyValue keyValue : list) {
                String str2 = keyValue.key;
                String valueStr = keyValue.getValueStr();
                if (!TextUtils.isEmpty(str2) && valueStr != null) {
                    if (sb.length() > 0) {
                        sb.append("&");
                    }
                    sb.append(Uri.encode(str2, this.charset));
                    sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                    sb.append(Uri.encode(valueStr, this.charset));
                }
            }
        }
        this.content = sb.toString().getBytes(this.charset);
    }

    @Override // org.xutils.http.body.RequestBody
    public long getContentLength() {
        return this.content.length;
    }

    @Override // org.xutils.http.body.RequestBody
    public String getContentType() {
        return "application/x-www-form-urlencoded;charset=" + this.charset;
    }

    @Override // org.xutils.http.body.RequestBody
    public void writeTo(OutputStream outputStream) throws IOException {
        outputStream.write(this.content);
        outputStream.flush();
    }
}
