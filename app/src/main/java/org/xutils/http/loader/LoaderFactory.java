package org.xutils.http.loader;

import java.io.File;
import java.lang.reflect.Type;
import java.util.HashMap;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

/* loaded from: classes4.dex */
public final class LoaderFactory {
    private static final HashMap<Type, Loader> converterHashMap = new HashMap<>();

    private LoaderFactory() {
    }

    static {
        converterHashMap.put(JSONObject.class, new JSONObjectLoader());
        converterHashMap.put(JSONArray.class, new JSONArrayLoader());
        converterHashMap.put(String.class, new StringLoader());
        converterHashMap.put(File.class, new FileLoader());
        converterHashMap.put(byte[].class, new ByteArrayLoader());
        BooleanLoader booleanLoader = new BooleanLoader();
        converterHashMap.put(Boolean.TYPE, booleanLoader);
        converterHashMap.put(Boolean.class, booleanLoader);
        IntegerLoader integerLoader = new IntegerLoader();
        converterHashMap.put(Integer.TYPE, integerLoader);
        converterHashMap.put(Integer.class, integerLoader);
    }

    public static Loader<?> getLoader(Type type, RequestParams requestParams) {
        Loader<?> newInstance;
        Loader loader = converterHashMap.get(type);
        if (loader == null) {
            newInstance = new ObjectLoader(type);
        } else {
            newInstance = loader.newInstance();
        }
        newInstance.setParams(requestParams);
        return newInstance;
    }

    public static <T> void registerLoader(Type type, Loader<T> loader) {
        converterHashMap.put(type, loader);
    }
}
