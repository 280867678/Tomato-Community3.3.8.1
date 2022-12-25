package com.tomatolive.library.http.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/* loaded from: classes3.dex */
public class CustomGsonConverterFactory extends Converter.Factory {
    private final Gson gson;

    private CustomGsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    public static CustomGsonConverterFactory create() {
        return create(new Gson());
    }

    public static CustomGsonConverterFactory create(Gson gson) {
        return new CustomGsonConverterFactory(gson);
    }

    @Override // retrofit2.Converter.Factory
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotationArr, Retrofit retrofit) {
        try {
            return new CustomGsonResponseBodyConverter(this.gson, this.gson.getAdapter(TypeToken.get(type)));
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // retrofit2.Converter.Factory
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] annotationArr, Annotation[] annotationArr2, Retrofit retrofit) {
        try {
            return new CustomGsonRequestBodyConverter(this.gson, this.gson.getAdapter(TypeToken.get(type)));
        } catch (Exception unused) {
            return null;
        }
    }
}
