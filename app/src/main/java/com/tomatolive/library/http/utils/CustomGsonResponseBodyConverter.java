package com.tomatolive.library.http.utils;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.sensorsdata.analytics.android.sdk.AopConstants;
import com.tomatolive.library.http.exception.ServerException;
import com.tomatolive.library.utils.AppUtils;
import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/* loaded from: classes3.dex */
public class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final TypeAdapter<T> adapter;
    private final Gson gson;

    /* JADX INFO: Access modifiers changed from: package-private */
    public CustomGsonResponseBodyConverter(Gson gson, TypeAdapter<T> typeAdapter) {
        this.gson = gson;
        this.adapter = typeAdapter;
    }

    @Override // retrofit2.Converter
    public T convert(@NonNull ResponseBody responseBody) throws IOException {
        String string = responseBody.string();
        JsonParser jsonParser = new JsonParser();
        if (isEncrypt(jsonParser, string)) {
            EncryptHttpResultModel encryptHttpResultModel = (EncryptHttpResultModel) this.gson.fromJson(string, (Class<Object>) EncryptHttpResultModel.class);
            ResultMode resultMode = new ResultMode();
            resultMode.code = encryptHttpResultModel.getCode();
            resultMode.msg = encryptHttpResultModel.getMessage();
            String jsonData = encryptHttpResultModel.getJsonData();
            if (jsonParser.parse(jsonData).isJsonArray()) {
                resultMode.data = new JsonParser().parse(jsonData).getAsJsonArray();
            } else {
                resultMode.data = new JsonParser().parse(jsonData).getAsJsonObject();
            }
            string = this.gson.toJson(resultMode);
        }
        try {
            ResultMode resultMode2 = (ResultMode) this.gson.fromJson(string, (Class<Object>) ResultMode.class);
            if (resultMode2 != null && AppUtils.isTokenInvalidErrorCode(resultMode2.getCode())) {
                responseBody.close();
                throw new ServerException(resultMode2.getCode(), resultMode2.getMsg());
            }
            return this.adapter.fromJson(string);
        } finally {
            responseBody.close();
        }
    }

    private boolean isEncrypt(JsonParser jsonParser, String str) {
        try {
            String json = this.gson.toJson(((ResultMode) this.gson.fromJson(str, (Class<Object>) ResultMode.class)).data);
            if (jsonParser.parse(json).isJsonArray() || jsonParser.parse(json).getAsJsonObject().size() != 2 || !jsonParser.parse(json).getAsJsonObject().has("key")) {
                return false;
            }
            return jsonParser.parse(json).getAsJsonObject().has(AopConstants.APP_PROPERTIES_KEY);
        } catch (Exception unused) {
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class ResultMode {
        private int code;
        private Object data;
        private String msg;

        private ResultMode() {
            this.code = 0;
        }

        public int getCode() {
            return this.code;
        }

        public void setCode(int i) {
            this.code = i;
        }

        public String getMsg() {
            return this.msg;
        }

        public void setMsg(String str) {
            this.msg = str;
        }

        public Object getData() {
            return this.data;
        }

        public void setData(Object obj) {
            this.data = obj;
        }
    }
}
