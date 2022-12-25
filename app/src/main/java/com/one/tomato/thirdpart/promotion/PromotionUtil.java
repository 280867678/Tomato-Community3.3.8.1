package com.one.tomato.thirdpart.promotion;

import android.content.Context;
import android.text.TextUtils;
import com.google.gson.JsonSyntaxException;
import com.j256.ormlite.field.DatabaseFieldConfigLoader;
import com.one.tomato.entity.PromotionParameter;
import com.one.tomato.entity.PromotionTokenBean;
import com.one.tomato.entity.p079db.SystemParam;
import com.one.tomato.mvp.base.BaseApplication;
import com.one.tomato.mvp.base.okhttp.ApiDisposableObserver;
import com.one.tomato.mvp.base.okhttp.ResponseThrowable;
import com.one.tomato.mvp.net.ApiImplService;
import com.one.tomato.thirdpart.domain.DomainServer;
import com.one.tomato.thirdpart.webapp.WebAppUtil;
import com.one.tomato.utils.DBUtil;
import com.one.tomato.utils.RxUtils;
import java.util.HashMap;

/* loaded from: classes3.dex */
public class PromotionUtil {
    public static final String DOMAIN = DomainServer.getInstance().getSpreadUrl();
    public static String merchantId;
    public static PromotionParameter parameter;
    public static String promotionToken;
    public static String signKey;

    /* loaded from: classes3.dex */
    public interface RequestTokenListener {
        void requestTokenFail();

        void requestTokenSuccess();
    }

    static {
        merchantId = "";
        signKey = "";
        SystemParam systemParam = DBUtil.getSystemParam();
        if (!TextUtils.isEmpty(systemParam.getSpreadParameter())) {
            try {
                parameter = (PromotionParameter) BaseApplication.getGson().fromJson(systemParam.getSpreadParameter(), (Class<Object>) PromotionParameter.class);
                "1".equals(parameter.spreadOpen);
                merchantId = parameter.merchantId;
                signKey = parameter.singKey;
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public static void requestToken(final RequestTokenListener requestTokenListener) {
        ApiImplService.Companion.getApiImplService().requestPromotionToken(DBUtil.getMemberId()).compose(RxUtils.schedulersTransformer()).subscribe(new ApiDisposableObserver<PromotionTokenBean>() { // from class: com.one.tomato.thirdpart.promotion.PromotionUtil.1
            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResult(PromotionTokenBean promotionTokenBean) {
                PromotionUtil.promotionToken = promotionTokenBean.token;
                RequestTokenListener.this.requestTokenSuccess();
            }

            @Override // com.one.tomato.mvp.base.okhttp.ApiDisposableObserver
            public void onResultError(ResponseThrowable responseThrowable) {
                RequestTokenListener.this.requestTokenFail();
            }
        });
    }

    public static void openXiaobai(Context context) {
        if (parameter == null) {
            return;
        }
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("alias", parameter.appAlias);
        hashMap.put(DatabaseFieldConfigLoader.FIELD_NAME_ID, parameter.appId);
        hashMap.put("host", parameter.appUrl);
        hashMap.put("key", parameter.appKey);
        new WebAppUtil().startWebAppActivity(context, hashMap, null);
    }
}
