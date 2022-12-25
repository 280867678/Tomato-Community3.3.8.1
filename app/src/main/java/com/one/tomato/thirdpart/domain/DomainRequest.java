package com.one.tomato.thirdpart.domain;

import android.text.TextUtils;
import com.one.tomato.entity.DomainJsonBean;
import com.one.tomato.entity.DomainUrl;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.HttpsUtils;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.base.okhttp.RetrofitClient;
import com.one.tomato.mvp.base.okhttp.interceptor.ResponseHeaderInterceptor;
import com.one.tomato.mvp.base.okhttp.interceptor.logging.Level;
import com.one.tomato.mvp.base.okhttp.interceptor.logging.LoggingInterceptor;
import com.one.tomato.utils.FileUtil;
import com.one.tomato.utils.LogUtil;
import com.one.tomato.utils.encrypt.AESUtil;
import com.one.tomato.utils.encrypt.RSAUtil;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import java.io.File;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/* loaded from: classes3.dex */
public class DomainRequest {
    private static DomainRequest domainRequest;
    private IDomainRequest requestListener;
    private Map<String, ArrayList<DomainUrl>> urlMap = new HashMap();
    private Map<String, String> fileNameMap = new HashMap();
    private ArrayList<String> requestDomainList = new ArrayList<>();
    private int requestDomainIndex = 0;
    private int requestStatus = 0;

    public String getSDCardDomainDir() {
        return "line";
    }

    public static DomainRequest getInstance() {
        if (domainRequest == null) {
            synchronized (DomainRequest.class) {
                if (domainRequest == null) {
                    domainRequest = new DomainRequest();
                }
            }
        }
        return domainRequest;
    }

    private DomainRequest() {
        int i = 0;
        while (true) {
            String[] strArr = DomainConfig.domainArray;
            if (i < strArr.length) {
                this.fileNameMap.put(strArr[i], DomainConfig.fileArray[i]);
                getDomainListByType(DomainConfig.domainArray[i]);
                i++;
            } else {
                LogUtil.m3784i("初始化时的域名列表：urlMap = \n" + this.urlMap);
                this.requestDomainList.add("server");
                this.requestDomainList.add("backUpDomain");
                this.requestDomainList.add("backUpIP");
                this.requestDomainList.add("backUpBlogDomain");
                return;
            }
        }
    }

    private synchronized ArrayList<DomainUrl> getDomainListByType(String str) {
        String sDCardDomainDir = getSDCardDomainDir();
        String str2 = this.fileNameMap.get(str);
        File file = new File(FileUtil.getDomainDir(sDCardDomainDir), str2);
        String str3 = "";
        if (file.exists()) {
            str3 = FileUtil.readSDCardData(file.getPath());
        }
        if (TextUtils.isEmpty(str3)) {
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(sDCardDomainDir);
            stringBuffer.append(File.separator);
            stringBuffer.append(str2);
            str3 = FileUtil.readAssetFile(stringBuffer.toString());
            FileUtil.writeSDCardData(file.getPath(), str3);
        }
        if (TextUtils.isEmpty(str3)) {
            LogUtil.m3786e("getDomainListByType ==>> 没有获取到加密数据");
            return null;
        }
        ArrayList<DomainUrl> arrayList = new ArrayList<>();
        if (!decrypt(str, str3, arrayList)) {
            return null;
        }
        this.urlMap.put(str, arrayList);
        return arrayList;
    }

    public synchronized String getDomainBaseServerUrl() {
        if (this.requestDomainIndex >= this.requestDomainList.size()) {
            this.requestDomainIndex = 0;
        }
        return getDomainUrlByType(this.requestDomainList.get(this.requestDomainIndex));
    }

    public synchronized String getDomainUrlByType(String str) {
        ArrayList<DomainUrl> arrayList = this.urlMap.get(str);
        if (arrayList == null) {
            LogUtil.m3786e("urlMap缓存中没有域名列表，调用方法getDomainListByType重新获取");
            arrayList = getDomainListByType(str);
            if (arrayList == null || arrayList.isEmpty()) {
                return "http://www.google.com";
            }
        }
        return arrayList.get(getListIndexByWeight(arrayList)).url;
    }

    public synchronized void switchDomainUrlByType(String str) {
    }

    public synchronized void requestDomainList(String str, final boolean z) {
        LogUtil.m3784i("请求域名的type = " + str);
        this.requestStatus = 1;
        if (this.requestListener != null) {
            this.requestListener.domainRequest(this.requestStatus);
        }
        if ("backUpBlogDomain".equals(str)) {
            HttpsUtils.SSLParams sslSocketFactory = HttpsUtils.getSslSocketFactory();
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory.sSLSocketFactory, sslSocketFactory.trustManager);
            builder.addInterceptor(new ResponseHeaderInterceptor());
            LoggingInterceptor.Builder builder2 = new LoggingInterceptor.Builder();
            builder2.loggable(false);
            builder2.setLevel(Level.BASIC);
            builder2.log(4);
            builder2.request("Request");
            builder2.response("Response");
            builder2.addHeader("log-header", "I am the log request header.");
            builder2.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
            builder.addInterceptor(builder2.build());
            builder.connectTimeout(15L, TimeUnit.SECONDS);
            builder.writeTimeout(15L, TimeUnit.SECONDS);
            builder.connectionPool(new ConnectionPool(8, 15L, TimeUnit.SECONDS));
            OkHttpClient build = builder.build();
            Retrofit.Builder builder3 = new Retrofit.Builder();
            builder3.client(build);
            builder3.addConverterFactory(new Converter.Factory(this) { // from class: com.one.tomato.thirdpart.domain.DomainRequest.1
                @Override // retrofit2.Converter.Factory
                public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
                    return new Converter<ResponseBody, String>(this) { // from class: com.one.tomato.thirdpart.domain.DomainRequest.1.1
                        @Override // retrofit2.Converter
                        public String convert(ResponseBody responseBody) throws IOException {
                            return responseBody.string();
                        }
                    };
                }
            });
            builder3.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
            builder3.baseUrl("http://www.google.com/");
            Retrofit build2 = builder3.build();
            ((IDomainRequestService) build2.create(IDomainRequestService.class)).requestBlog(getDomainUrlByType("backUpBlogDomain")).subscribeOn(Schedulers.m90io()).unsubscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new Observer<String>() { // from class: com.one.tomato.thirdpart.domain.DomainRequest.2
                @Override // io.reactivex.Observer
                public void onSubscribe(Disposable disposable) {
                    LogUtil.m3784i("博客：onSubscribe");
                }

                @Override // io.reactivex.Observer
                public void onNext(String str2) {
                    LogUtil.m3784i("博客请求成功：" + str2);
                    DomainRequest.this.requestBlogSuccess(str2);
                }

                @Override // io.reactivex.Observer
                public void onError(Throwable th) {
                    LogUtil.m3784i("博客请求失败：" + th.toString());
                    DomainRequest.this.requestFail();
                }

                @Override // io.reactivex.Observer
                public void onComplete() {
                    LogUtil.m3784i("博客：onComplete");
                }
            });
        } else {
            ((IDomainRequestService) RetrofitClient.getInstance().create(IDomainRequestService.class)).requestDomainList().subscribeOn(Schedulers.m90io()).unsubscribeOn(Schedulers.m90io()).observeOn(Schedulers.m90io()).subscribe(new ApiDisposableObserver<DomainJsonBean>() { // from class: com.one.tomato.thirdpart.domain.DomainRequest.3
                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResult(DomainJsonBean domainJsonBean) {
                    DomainRequest.this.requestSuccess(domainJsonBean);
                }

                @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
                public void onResultError(ResponseThrowable responseThrowable) {
                    if (z) {
                        return;
                    }
                    DomainRequest.this.requestFail();
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void requestBlogSuccess(String str) {
        if (TextUtils.isEmpty(str)) {
            requestFail();
            return;
        }
        int indexOf = str.indexOf("Winter//:");
        int indexOf2 = str.indexOf("://Winter");
        if (indexOf != -1 && indexOf2 != -1) {
            String substring = str.substring(indexOf + 9, indexOf2);
            ArrayList<DomainUrl> arrayList = new ArrayList<>();
            if (!decrypt("backUpBlogDomain", substring, arrayList)) {
                requestFail();
                return;
            }
            this.urlMap.put("server", arrayList);
            this.requestDomainIndex = 0;
            requestDomainList(this.requestDomainList.get(this.requestDomainIndex), true);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void requestSuccess(DomainJsonBean domainJsonBean) {
        for (int i = 0; i < DomainConfig.domainArray.length; i++) {
            String str = DomainConfig.domainArray[i];
            File file = new File(FileUtil.getDomainDir(getSDCardDomainDir()), this.fileNameMap.get(str));
            String str2 = "";
            try {
                str2 = (String) domainJsonBean.getClass().getField(str).get(domainJsonBean);
            } catch (Exception e) {
                e.printStackTrace();
            }
            FileUtil.writeSDCardData(file.getPath(), str2);
            getDomainListByType(str);
        }
        LogUtil.m3784i("接口请求成功的域名列表：urlMap = \n" + this.urlMap);
        this.requestDomainIndex = 0;
        this.requestStatus = 2;
        if (this.requestListener != null) {
            this.requestListener.domainRequest(this.requestStatus);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public synchronized void requestFail() {
        this.requestDomainIndex++;
        if (this.requestDomainIndex < this.requestDomainList.size()) {
            requestDomainList(this.requestDomainList.get(this.requestDomainIndex), false);
        } else {
            this.requestDomainIndex = 0;
            this.requestStatus = 3;
            if (this.requestListener != null) {
                this.requestListener.domainRequest(this.requestStatus);
            }
        }
    }

    public void setRequestListener(IDomainRequest iDomainRequest) {
        this.requestListener = iDomainRequest;
    }

    public boolean decrypt(String str, String str2, ArrayList<DomainUrl> arrayList) {
        try {
            String decryptAES = AESUtil.decryptAES(str2, "WTSecret81234512");
            if (TextUtils.isEmpty(decryptAES)) {
                LogUtil.m3786e("解密：domainType = " + str + ", 没有获取到加密数据");
                return false;
            }
            try {
                String[] split = decryptAES.split("\\|");
                String str3 = split[0];
                String str4 = split[1];
                String[] split2 = str3.split(",");
                String[] split3 = str4.split(",");
                for (int i = 0; i < split2.length; i++) {
                    arrayList.add(new DomainUrl(RSAUtil.RSAdecrypt("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgbJOBAheQBA2CYoJVpdYGEtGt6vBb+msn6kub/21HS/CipN+uRUZkTVdex/amFmYLYqQQTK3AziBVBnWsz05jlyimzKrNvZprVsFyNOm6ULH1x/lzR/3Tj/5UcrN1gTE8eONWtYQ9nriy1+9RrMkem4aps4rWOhuy3mXq8PpfOwIDAQAB", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOBsk4ECF5AEDYJiglWl1gYS0a3q8Fv6ayfqS5v/bUdL8KKk365FRmRNV17H9qYWZgtipBBMrcDOIFUGdazPTmOXKKbMqs29mmtWwXI06bpQsfXH+XNH/dOP/lRys3WBMTx441a1hD2euLLX71GsyR6bhqmzitY6G7LeZerw+l87AgMBAAECgYA4HY4IbQB9RzYliwIx7kSEwkHhreQp64TNtzzupcCqWieyU22GwtWmENyu22sl/mXHpQOG+9VaZ3AYMoRMEI31yvUEFgQhqKVmQiYzhLP0eZFPZIrVf5SPmPcbU3+vNCQTEB6eO1XvORLWUGoEgdtVPmBSTX6/KiHuKWGvCS4FmQJBAPzaYqGUfmWOmRgAfBE7w1qRIDyq2evlDLzdqguGTpo0NkR6nxEYihGNb5zYypd6JpERvtf+Qycb++ZygzAY2McCQQDjN50OysQvdX2shfo73u/0XcbYlhHYyrHGAnanLhwMMirl6awxJHCoRcBwNrXjne/v/+WghySwp/Hn8MY0/9ntAkEA89QsVKB7mrd+DlU5Tu0Qn19fdOFUsFP6io4/Ekn7tlwvEK4mgjflvLNlNB0ikBws4Kv6GxOH8kjcCwfWViU/tQJBAJyFgQHhmEgBLbOdD4YSy0WRHBuzNVQcPV5j8Ay2bMfR/08mK2Im8hxZAHnMlnvYHqM7qplsv0+aQcA/UqrL3PkCQFqjDjFS9v9EBEkrD3MZxhQABeF2bxChmdGVsxrfRMWzQuIIxv95F9GforezYJ0jC/8EJNicNl3xKmAJAkfCmDU=", split2[i]), Integer.parseInt(RSAUtil.RSAdecrypt("MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgbJOBAheQBA2CYoJVpdYGEtGt6vBb+msn6kub/21HS/CipN+uRUZkTVdex/amFmYLYqQQTK3AziBVBnWsz05jlyimzKrNvZprVsFyNOm6ULH1x/lzR/3Tj/5UcrN1gTE8eONWtYQ9nriy1+9RrMkem4aps4rWOhuy3mXq8PpfOwIDAQAB", "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAOBsk4ECF5AEDYJiglWl1gYS0a3q8Fv6ayfqS5v/bUdL8KKk365FRmRNV17H9qYWZgtipBBMrcDOIFUGdazPTmOXKKbMqs29mmtWwXI06bpQsfXH+XNH/dOP/lRys3WBMTx441a1hD2euLLX71GsyR6bhqmzitY6G7LeZerw+l87AgMBAAECgYA4HY4IbQB9RzYliwIx7kSEwkHhreQp64TNtzzupcCqWieyU22GwtWmENyu22sl/mXHpQOG+9VaZ3AYMoRMEI31yvUEFgQhqKVmQiYzhLP0eZFPZIrVf5SPmPcbU3+vNCQTEB6eO1XvORLWUGoEgdtVPmBSTX6/KiHuKWGvCS4FmQJBAPzaYqGUfmWOmRgAfBE7w1qRIDyq2evlDLzdqguGTpo0NkR6nxEYihGNb5zYypd6JpERvtf+Qycb++ZygzAY2McCQQDjN50OysQvdX2shfo73u/0XcbYlhHYyrHGAnanLhwMMirl6awxJHCoRcBwNrXjne/v/+WghySwp/Hn8MY0/9ntAkEA89QsVKB7mrd+DlU5Tu0Qn19fdOFUsFP6io4/Ekn7tlwvEK4mgjflvLNlNB0ikBws4Kv6GxOH8kjcCwfWViU/tQJBAJyFgQHhmEgBLbOdD4YSy0WRHBuzNVQcPV5j8Ay2bMfR/08mK2Im8hxZAHnMlnvYHqM7qplsv0+aQcA/UqrL3PkCQFqjDjFS9v9EBEkrD3MZxhQABeF2bxChmdGVsxrfRMWzQuIIxv95F9GforezYJ0jC/8EJNicNl3xKmAJAkfCmDU=", split3[i]))));
                }
                LogUtil.m3784i("解密：domainType = " + str + "，domainUrlList = " + arrayList.toString());
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        } catch (Exception e2) {
            e2.printStackTrace();
            LogUtil.m3786e("解密：domainType = " + str + ", 解密报异常");
            return false;
        }
    }

    private int getListIndexByWeight(List<DomainUrl> list) {
        double d;
        double d2;
        double d3;
        if (list == null || list.size() <= 1) {
            return 0;
        }
        Double valueOf = Double.valueOf(0.0d);
        Double d4 = valueOf;
        for (DomainUrl domainUrl : list) {
            try {
                d3 = domainUrl.weight;
            } catch (Exception e) {
                e.printStackTrace();
                d3 = 0.0d;
            }
            d4 = Double.valueOf(d4.doubleValue() + d3);
        }
        double random = Math.random();
        double d5 = 0.0d;
        double d6 = 0.0d;
        int i = 0;
        while (true) {
            if (i >= list.size()) {
                i = 0;
                break;
            }
            try {
                d = list.get(i).weight;
            } catch (Exception e2) {
                e2.printStackTrace();
                d = 0.0d;
            }
            d5 += d / d4.doubleValue();
            if (i == 0) {
                d6 = 0.0d;
            } else {
                try {
                    d2 = list.get(i - 1).weight;
                } catch (Exception e3) {
                    e3.printStackTrace();
                    d2 = 0.0d;
                }
                d6 += d2 / d4.doubleValue();
            }
            if (d6 <= random && random < d5) {
                break;
            }
            i++;
        }
        if (i < list.size()) {
            return i;
        }
        return 0;
    }
}
