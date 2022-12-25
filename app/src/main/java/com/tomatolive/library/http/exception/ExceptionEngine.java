package com.tomatolive.library.http.exception;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;
import com.tomatolive.library.R$string;
import com.tomatolive.library.utils.SystemUtils;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import org.json.JSONException;
import retrofit2.HttpException;

/* loaded from: classes3.dex */
public class ExceptionEngine {
    public static final int ANALYTIC_SERVER_DATA_ERROR = 1001;
    public static final int CONNECT_ERROR = 1003;
    public static final int SERVER_ERROR = 2000;
    public static final int TIME_OUT_ERROR = 1004;
    public static final int UN_KNOWN_ERROR = 1000;

    public static boolean isExceptionErrorCode(int i) {
        return i == 1000 || i == 1001 || i == 2000;
    }

    public static ApiException handleException(Throwable th) {
        if (th instanceof HttpException) {
            ApiException apiException = new ApiException(th, 2000);
            apiException.setMsg(SystemUtils.getResString(R$string.fq_net_poor_retry));
            return apiException;
        } else if (th instanceof ServerException) {
            ServerException serverException = (ServerException) th;
            ApiException apiException2 = new ApiException(serverException, serverException.getCode());
            apiException2.setMsg(serverException.getMsg());
            return apiException2;
        } else if ((th instanceof JsonParseException) || (th instanceof JSONException) || (th instanceof ParseException) || (th instanceof MalformedJsonException)) {
            ApiException apiException3 = new ApiException(th, 1001);
            apiException3.setMsg("");
            return apiException3;
        } else if (th instanceof ConnectException) {
            ApiException apiException4 = new ApiException(th, 1003);
            apiException4.setMsg(SystemUtils.getResString(R$string.fq_text_no_network));
            return apiException4;
        } else if (th instanceof SocketTimeoutException) {
            ApiException apiException5 = new ApiException(th, 1004);
            apiException5.setMsg(SystemUtils.getResString(R$string.fq_net_timeout_retry));
            return apiException5;
        } else {
            ApiException apiException6 = new ApiException(th, 1000);
            apiException6.setMsg(SystemUtils.getResString(R$string.fq_net_poor_retry));
            return apiException6;
        }
    }
}
