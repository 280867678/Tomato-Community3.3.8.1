package com.one.tomato.mvp.base.okhttp;

import android.net.ParseException;
import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLException;
import org.apache.http.conn.ConnectTimeoutException;
import org.json.JSONException;
import retrofit2.HttpException;

/* loaded from: classes3.dex */
public class ExceptionHandle {
    public static ResponseThrowable handleException(Throwable th) {
        if (th instanceof HttpException) {
            ResponseThrowable responseThrowable = new ResponseThrowable(th, 1003);
            int code = ((HttpException) th).code();
            if (code == 401) {
                responseThrowable.message = "操作未授权";
            } else if (code == 408) {
                responseThrowable.message = "服务器执行超时";
            } else if (code == 500) {
                responseThrowable.message = "服务器内部错误";
            } else if (code == 503) {
                responseThrowable.message = "服务器不可用";
            } else if (code == 403) {
                responseThrowable.message = "请求被拒绝";
            } else if (code == 404) {
                responseThrowable.message = "资源不存在";
            } else {
                responseThrowable.message = "网络错误";
            }
            return responseThrowable;
        } else if ((th instanceof JsonParseException) || (th instanceof JSONException) || (th instanceof ParseException) || (th instanceof MalformedJsonException)) {
            ResponseThrowable responseThrowable2 = new ResponseThrowable(th, 1001);
            responseThrowable2.message = "解析错误";
            return responseThrowable2;
        } else if (th instanceof ConnectException) {
            ResponseThrowable responseThrowable3 = new ResponseThrowable(th, 1002);
            responseThrowable3.message = "连接失败";
            return responseThrowable3;
        } else if (th instanceof SSLException) {
            ResponseThrowable responseThrowable4 = new ResponseThrowable(th, 1005);
            responseThrowable4.message = "证书验证失败";
            return responseThrowable4;
        } else if (th instanceof ConnectTimeoutException) {
            ResponseThrowable responseThrowable5 = new ResponseThrowable(th, 1006);
            responseThrowable5.message = "连接超时";
            return responseThrowable5;
        } else if (th instanceof SocketTimeoutException) {
            ResponseThrowable responseThrowable6 = new ResponseThrowable(th, 1006);
            responseThrowable6.message = "连接超时";
            return responseThrowable6;
        } else if (th instanceof UnknownHostException) {
            ResponseThrowable responseThrowable7 = new ResponseThrowable(th, 1006);
            responseThrowable7.message = "主机地址未知";
            return responseThrowable7;
        } else {
            ResponseThrowable responseThrowable8 = new ResponseThrowable(th, 1000);
            responseThrowable8.message = "";
            return responseThrowable8;
        }
    }
}
