package org.xutils.http;

import android.text.TextUtils;
import com.j256.ormlite.stmt.query.SimpleComparison;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.util.KeyValue;
import org.xutils.common.util.LogUtil;
import org.xutils.http.body.BodyItemWrapper;
import org.xutils.http.body.FileBody;
import org.xutils.http.body.InputStreamBody;
import org.xutils.http.body.MultipartBody;
import org.xutils.http.body.RequestBody;
import org.xutils.http.body.StringBody;
import org.xutils.http.body.UrlEncodedParamsBody;

/* JADX INFO: Access modifiers changed from: package-private */
/* loaded from: classes4.dex */
public abstract class BaseParams {
    private String bodyContent;
    private HttpMethod method;
    private RequestBody requestBody;
    private String charset = "UTF-8";
    private boolean multipart = false;
    private boolean asJsonContent = false;
    private final List<Header> headers = new ArrayList();
    private final List<KeyValue> queryStringParams = new ArrayList();
    private final List<KeyValue> bodyParams = new ArrayList();
    private final List<KeyValue> fileParams = new ArrayList();

    public void setCharset(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.charset = str;
        }
    }

    public String getCharset() {
        return this.charset;
    }

    public void setMethod(HttpMethod httpMethod) {
        this.method = httpMethod;
    }

    public HttpMethod getMethod() {
        return this.method;
    }

    public boolean isMultipart() {
        return this.multipart;
    }

    public void setMultipart(boolean z) {
        this.multipart = z;
    }

    public boolean isAsJsonContent() {
        return this.asJsonContent;
    }

    public void setAsJsonContent(boolean z) {
        this.asJsonContent = z;
    }

    public void setHeader(String str, String str2) {
        Header header = new Header(str, str2, true);
        Iterator<Header> it2 = this.headers.iterator();
        while (it2.hasNext()) {
            if (str.equals(it2.next().key)) {
                it2.remove();
            }
        }
        this.headers.add(header);
    }

    public void addHeader(String str, String str2) {
        this.headers.add(new Header(str, str2, false));
    }

    public void addParameter(String str, Object obj) {
        if (obj == null) {
            return;
        }
        HttpMethod httpMethod = this.method;
        int i = 0;
        if (httpMethod == null || HttpMethod.permitsRequestBody(httpMethod)) {
            if (!TextUtils.isEmpty(str)) {
                if ((obj instanceof File) || (obj instanceof InputStream) || (obj instanceof byte[])) {
                    this.fileParams.add(new KeyValue(str, obj));
                    return;
                } else if (obj instanceof Iterable) {
                    for (Object obj2 : (Iterable) obj) {
                        this.bodyParams.add(new ArrayItem(str, obj2));
                    }
                    return;
                } else if (obj instanceof JSONArray) {
                    JSONArray jSONArray = (JSONArray) obj;
                    int length = jSONArray.length();
                    while (i < length) {
                        this.bodyParams.add(new ArrayItem(str, jSONArray.opt(i)));
                        i++;
                    }
                    return;
                } else if (obj.getClass().isArray()) {
                    int length2 = Array.getLength(obj);
                    while (i < length2) {
                        this.bodyParams.add(new ArrayItem(str, Array.get(obj, i)));
                        i++;
                    }
                    return;
                } else {
                    this.bodyParams.add(new KeyValue(str, obj));
                    return;
                }
            }
            this.bodyContent = obj.toString();
        } else if (!TextUtils.isEmpty(str)) {
            if (obj instanceof Iterable) {
                for (Object obj3 : (Iterable) obj) {
                    this.queryStringParams.add(new ArrayItem(str, obj3));
                }
            } else if (obj.getClass().isArray()) {
                int length3 = Array.getLength(obj);
                while (i < length3) {
                    this.queryStringParams.add(new ArrayItem(str, Array.get(obj, i)));
                    i++;
                }
            } else {
                this.queryStringParams.add(new KeyValue(str, obj));
            }
        }
    }

    public void addQueryStringParameter(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            this.queryStringParams.add(new KeyValue(str, str2));
        }
    }

    public void addBodyParameter(String str, String str2) {
        if (!TextUtils.isEmpty(str)) {
            this.bodyParams.add(new KeyValue(str, str2));
        } else {
            this.bodyContent = str2;
        }
    }

    public void addBodyParameter(String str, File file) {
        addBodyParameter(str, file, null, null);
    }

    public void addBodyParameter(String str, Object obj, String str2) {
        addBodyParameter(str, obj, str2, null);
    }

    public void addBodyParameter(String str, Object obj, String str2, String str3) {
        if (TextUtils.isEmpty(str2) && TextUtils.isEmpty(str3)) {
            this.fileParams.add(new KeyValue(str, obj));
        } else {
            this.fileParams.add(new KeyValue(str, new BodyItemWrapper(obj, str2, str3)));
        }
    }

    public void setBodyContent(String str) {
        this.bodyContent = str;
    }

    public String getBodyContent() {
        checkBodyParams();
        return this.bodyContent;
    }

    public List<Header> getHeaders() {
        return new ArrayList(this.headers);
    }

    public List<KeyValue> getQueryStringParams() {
        checkBodyParams();
        return new ArrayList(this.queryStringParams);
    }

    public List<KeyValue> getBodyParams() {
        checkBodyParams();
        return new ArrayList(this.bodyParams);
    }

    public List<KeyValue> getFileParams() {
        checkBodyParams();
        return new ArrayList(this.fileParams);
    }

    public List<KeyValue> getStringParams() {
        ArrayList arrayList = new ArrayList(this.queryStringParams.size() + this.bodyParams.size());
        arrayList.addAll(this.queryStringParams);
        arrayList.addAll(this.bodyParams);
        return arrayList;
    }

    public String getStringParameter(String str) {
        for (KeyValue keyValue : this.queryStringParams) {
            if (str == null && keyValue.key == null) {
                return keyValue.getValueStr();
            }
            if (str != null && str.equals(keyValue.key)) {
                return keyValue.getValueStr();
            }
        }
        for (KeyValue keyValue2 : this.bodyParams) {
            if (str == null && keyValue2.key == null) {
                return keyValue2.getValueStr();
            }
            if (str != null && str.equals(keyValue2.key)) {
                return keyValue2.getValueStr();
            }
        }
        return null;
    }

    public List<KeyValue> getParams(String str) {
        ArrayList arrayList = new ArrayList();
        for (KeyValue keyValue : this.queryStringParams) {
            if (str == null && keyValue.key == null) {
                arrayList.add(keyValue);
            } else if (str != null && str.equals(keyValue.key)) {
                arrayList.add(keyValue);
            }
        }
        for (KeyValue keyValue2 : this.bodyParams) {
            if (str == null && keyValue2.key == null) {
                arrayList.add(keyValue2);
            } else if (str != null && str.equals(keyValue2.key)) {
                arrayList.add(keyValue2);
            }
        }
        for (KeyValue keyValue3 : this.fileParams) {
            if (str == null && keyValue3.key == null) {
                arrayList.add(keyValue3);
            } else if (str != null && str.equals(keyValue3.key)) {
                arrayList.add(keyValue3);
            }
        }
        return arrayList;
    }

    public void clearParams() {
        this.queryStringParams.clear();
        this.bodyParams.clear();
        this.fileParams.clear();
        this.bodyContent = null;
        this.requestBody = null;
    }

    public void removeParameter(String str) {
        if (!TextUtils.isEmpty(str)) {
            Iterator<KeyValue> it2 = this.queryStringParams.iterator();
            while (it2.hasNext()) {
                if (str.equals(it2.next().key)) {
                    it2.remove();
                }
            }
            Iterator<KeyValue> it3 = this.bodyParams.iterator();
            while (it3.hasNext()) {
                if (str.equals(it3.next().key)) {
                    it3.remove();
                }
            }
            Iterator<KeyValue> it4 = this.fileParams.iterator();
            while (it4.hasNext()) {
                if (str.equals(it4.next().key)) {
                    it4.remove();
                }
            }
            return;
        }
        this.bodyContent = null;
    }

    public void setRequestBody(RequestBody requestBody) {
        this.requestBody = requestBody;
    }

    public RequestBody getRequestBody() throws IOException {
        String str;
        checkBodyParams();
        RequestBody requestBody = this.requestBody;
        if (requestBody != null) {
            return requestBody;
        }
        if (!TextUtils.isEmpty(this.bodyContent)) {
            return new StringBody(this.bodyContent, this.charset);
        }
        if (this.multipart || this.fileParams.size() > 0) {
            if (!this.multipart && this.fileParams.size() == 1) {
                Iterator<KeyValue> it2 = this.fileParams.iterator();
                if (!it2.hasNext()) {
                    return null;
                }
                Object obj = it2.next().value;
                if (obj instanceof BodyItemWrapper) {
                    BodyItemWrapper bodyItemWrapper = (BodyItemWrapper) obj;
                    Object value = bodyItemWrapper.getValue();
                    str = bodyItemWrapper.getContentType();
                    obj = value;
                } else {
                    str = null;
                }
                if (obj instanceof File) {
                    return new FileBody((File) obj, str);
                }
                if (obj instanceof InputStream) {
                    return new InputStreamBody((InputStream) obj, str);
                }
                if (obj instanceof byte[]) {
                    return new InputStreamBody(new ByteArrayInputStream((byte[]) obj), str);
                }
                if (obj instanceof String) {
                    StringBody stringBody = new StringBody((String) obj, this.charset);
                    stringBody.setContentType(str);
                    return stringBody;
                }
                LogUtil.m38w("Some params will be ignored for: " + toString());
                return null;
            }
            this.multipart = true;
            return new MultipartBody(this.fileParams, this.charset);
        } else if (this.bodyParams.size() <= 0) {
            return null;
        } else {
            return new UrlEncodedParamsBody(this.bodyParams, this.charset);
        }
    }

    public String toJSONString() {
        JSONObject jSONObject;
        ArrayList arrayList = new ArrayList(this.queryStringParams.size() + this.bodyParams.size());
        arrayList.addAll(this.queryStringParams);
        arrayList.addAll(this.bodyParams);
        try {
            if (!TextUtils.isEmpty(this.bodyContent)) {
                jSONObject = new JSONObject(this.bodyContent);
            } else {
                jSONObject = new JSONObject();
            }
            params2Json(jSONObject, arrayList);
            return jSONObject.toString();
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString() {
        checkBodyParams();
        StringBuilder sb = new StringBuilder();
        if (!this.queryStringParams.isEmpty()) {
            for (KeyValue keyValue : this.queryStringParams) {
                sb.append(keyValue.key);
                sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                sb.append(keyValue.value);
                sb.append("&");
            }
            sb.deleteCharAt(sb.length() - 1);
        }
        if (HttpMethod.permitsRequestBody(this.method)) {
            if (!TextUtils.isEmpty(this.bodyContent)) {
                sb.append(this.bodyContent);
            } else if (!this.bodyParams.isEmpty()) {
                for (KeyValue keyValue2 : this.bodyParams) {
                    sb.append(keyValue2.key);
                    sb.append(SimpleComparison.EQUAL_TO_OPERATION);
                    sb.append(keyValue2.value);
                    sb.append("&");
                }
                sb.deleteCharAt(sb.length() - 1);
            }
        }
        return sb.toString();
    }

    private synchronized void checkBodyParams() {
        JSONObject jSONObject;
        if (this.bodyParams.isEmpty()) {
            return;
        }
        if (!HttpMethod.permitsRequestBody(this.method) || !TextUtils.isEmpty(this.bodyContent) || this.requestBody != null) {
            this.queryStringParams.addAll(this.bodyParams);
            this.bodyParams.clear();
        }
        if (!this.bodyParams.isEmpty() && (this.multipart || this.fileParams.size() > 0)) {
            this.fileParams.addAll(this.bodyParams);
            this.bodyParams.clear();
        }
        if (this.asJsonContent && !this.bodyParams.isEmpty()) {
            try {
                if (!TextUtils.isEmpty(this.bodyContent)) {
                    jSONObject = new JSONObject(this.bodyContent);
                } else {
                    jSONObject = new JSONObject();
                }
                params2Json(jSONObject, this.bodyParams);
                this.bodyContent = jSONObject.toString();
                this.bodyParams.clear();
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void params2Json(JSONObject jSONObject, List<KeyValue> list) throws JSONException {
        JSONArray jSONArray;
        HashSet hashSet = new HashSet(list.size());
        LinkedHashMap linkedHashMap = new LinkedHashMap(list.size());
        for (int i = 0; i < list.size(); i++) {
            KeyValue keyValue = list.get(i);
            String str = keyValue.key;
            if (!TextUtils.isEmpty(str)) {
                if (linkedHashMap.containsKey(str)) {
                    jSONArray = (JSONArray) linkedHashMap.get(str);
                } else {
                    jSONArray = new JSONArray();
                    linkedHashMap.put(str, jSONArray);
                }
                jSONArray.put(RequestParamsHelper.parseJSONObject(keyValue.value));
                if (keyValue instanceof ArrayItem) {
                    hashSet.add(str);
                }
            }
        }
        for (Map.Entry entry : linkedHashMap.entrySet()) {
            String str2 = (String) entry.getKey();
            JSONArray jSONArray2 = (JSONArray) entry.getValue();
            if (jSONArray2.length() > 1 || hashSet.contains(str2)) {
                jSONObject.put(str2, jSONArray2);
            } else {
                jSONObject.put(str2, jSONArray2.get(0));
            }
        }
    }

    /* loaded from: classes4.dex */
    public static final class ArrayItem extends KeyValue {
        public ArrayItem(String str, Object obj) {
            super(str, obj);
        }
    }

    /* loaded from: classes4.dex */
    public static final class Header extends KeyValue {
        public final boolean setHeader;

        public Header(String str, String str2, boolean z) {
            super(str, str2);
            this.setHeader = z;
        }
    }
}
