package com.tomatolive.library.utils.router;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.blankj.utilcode.util.ToastUtils;
import com.tomatolive.library.utils.router.callbacks.InterceptorCallback;
import java.net.URI;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* loaded from: classes4.dex */
public class Router {
    private static final Router _router = new Router();
    private Context _context;
    private IInterceptor iInterceptor;
    private final Map<String, RouterOptions> _routes = new HashMap();
    private String _rootUrl = null;
    private final Map<String, RouterParams> _cachedRoutes = new HashMap();

    /* loaded from: classes4.dex */
    public static abstract class RouterCallback {
        public abstract void run(RouteContext routeContext);
    }

    public static Router sharedRouter() {
        return _router;
    }

    /* loaded from: classes4.dex */
    public class RouteContext {
        Context _context;
        Bundle _extras;
        Map<String, String> _params;

        public RouteContext(Map<String, String> map, Bundle bundle, Context context) {
            this._params = map;
            this._extras = bundle;
            this._context = context;
        }

        public Map<String, String> getParams() {
            return this._params;
        }

        public Bundle getExtras() {
            return this._extras;
        }

        public Context getContext() {
            return this._context;
        }
    }

    /* loaded from: classes4.dex */
    public static class RouterOptions {
        RouterCallback _callback;
        Map<String, String> _defaultParams;
        Class<? extends Activity> _klass;

        public String toString() {
            return "RouterOptions{_klass=" + this._klass + ", _callback=" + this._callback + ", _defaultParams=" + this._defaultParams + '}';
        }

        public RouterOptions() {
        }

        public RouterOptions(Class<? extends Activity> cls) {
            setOpenClass(cls);
        }

        public RouterOptions(Map<String, String> map) {
            setDefaultParams(map);
        }

        public RouterOptions(Map<String, String> map, Class<? extends Activity> cls) {
            setDefaultParams(map);
            setOpenClass(cls);
        }

        public void setOpenClass(Class<? extends Activity> cls) {
            this._klass = cls;
        }

        public Class<? extends Activity> getOpenClass() {
            return this._klass;
        }

        public RouterCallback getCallback() {
            return this._callback;
        }

        public void setCallback(RouterCallback routerCallback) {
            this._callback = routerCallback;
        }

        public void setDefaultParams(Map<String, String> map) {
            this._defaultParams = map;
        }

        public Map<String, String> getDefaultParams() {
            return this._defaultParams;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes4.dex */
    public static class RouterParams {
        public Map<String, String> openParams;
        public RouterOptions routerOptions;

        private RouterParams() {
        }

        public String toString() {
            return "RouterParams{routerOptions=" + this.routerOptions + ", openParams=" + this.openParams + '}';
        }
    }

    private Router() {
    }

    private Router(Context context) {
        setContext(context);
    }

    public void setContext(Context context) {
        this._context = context;
    }

    public IInterceptor getInterceptor() {
        return this.iInterceptor;
    }

    public void setInterceptor(IInterceptor iInterceptor) {
        this.iInterceptor = iInterceptor;
    }

    public Context getContext() {
        return this._context;
    }

    public void map(String str, RouterCallback routerCallback) {
        RouterOptions routerOptions = new RouterOptions();
        routerOptions.setCallback(routerCallback);
        map(str, null, routerOptions);
    }

    public void map(String str, Class<? extends Activity> cls) {
        map(str, cls, null);
    }

    public void map(String str, Class<? extends Activity> cls, RouterOptions routerOptions) {
        if (routerOptions == null) {
            routerOptions = new RouterOptions();
        }
        routerOptions.setOpenClass(cls);
        this._routes.put(str, routerOptions);
        Log.i("meme", "_routes = " + this._routes);
    }

    public void setRootUrl(String str) {
        this._rootUrl = str;
    }

    public String getRootUrl() {
        return this._rootUrl;
    }

    public void openExternal(String str) {
        openExternal(str, this._context);
    }

    public void openExternal(String str, Context context) {
        openExternal(str, null, context);
    }

    public void openExternal(String str, Bundle bundle) {
        openExternal(str, bundle, this._context);
    }

    public void openExternal(String str, Bundle bundle, Context context) {
        if (context == null) {
            throw new ContextNotProvided("You need to supply a context for Router " + toString());
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        addFlagsToIntent(intent, context);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        context.startActivity(intent);
    }

    public void open(String str) {
        open(str, this._context);
    }

    public void open(String str, Bundle bundle) {
        open(str, bundle, this._context);
    }

    public void open(String str, Context context) {
        open(str, null, context);
    }

    public void open(String str, Bundle bundle, final Context context) {
        if (context == null) {
            throw new ContextNotProvided("You need to supply a context for Router " + toString());
        }
        RouterParams paramsForUrl = paramsForUrl(str);
        if (paramsForUrl == null) {
            return;
        }
        RouterOptions routerOptions = paramsForUrl.routerOptions;
        if (routerOptions.getCallback() != null) {
            routerOptions.getCallback().run(new RouteContext(paramsForUrl.openParams, bundle, context));
            return;
        }
        Intent intentFor = intentFor(context, paramsForUrl);
        if (intentFor == null) {
            return;
        }
        if (bundle != null) {
            intentFor.putExtras(bundle);
        }
        IInterceptor iInterceptor = this.iInterceptor;
        if (iInterceptor != null) {
            iInterceptor.process(str, intentFor, context, new InterceptorCallback() { // from class: com.tomatolive.library.utils.router.Router.1
                @Override // com.tomatolive.library.utils.router.callbacks.InterceptorCallback
                public void onInterrupt(Throwable th) {
                }

                @Override // com.tomatolive.library.utils.router.callbacks.InterceptorCallback
                public void onContinue(Intent intent) {
                    context.startActivity(intent);
                }
            });
        } else {
            context.startActivity(intentFor);
        }
    }

    private void addFlagsToIntent(Intent intent, Context context) {
        if (context == this._context) {
            intent.addFlags(268435456);
        }
    }

    public Intent intentFor(String str) {
        return intentFor(paramsForUrl(str));
    }

    private Intent intentFor(RouterParams routerParams) {
        if (routerParams == null) {
            return null;
        }
        RouterOptions routerOptions = routerParams.routerOptions;
        Intent intent = new Intent();
        if (routerOptions.getDefaultParams() != null) {
            for (Map.Entry<String, String> entry : routerOptions.getDefaultParams().entrySet()) {
                intent.putExtra(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry2 : routerParams.openParams.entrySet()) {
            intent.putExtra(entry2.getKey(), entry2.getValue());
        }
        return intent;
    }

    public boolean isCallbackUrl(String str) {
        RouterParams paramsForUrl = paramsForUrl(str);
        return (paramsForUrl == null || paramsForUrl.routerOptions.getCallback() == null) ? false : true;
    }

    public Intent intentFor(Context context, String str) {
        return intentFor(context, paramsForUrl(str));
    }

    private Intent intentFor(Context context, RouterParams routerParams) {
        if (routerParams == null) {
            return null;
        }
        RouterOptions routerOptions = routerParams.routerOptions;
        if (routerOptions.getCallback() != null) {
            return null;
        }
        Intent intentFor = intentFor(routerParams);
        intentFor.setClass(context, routerOptions.getOpenClass());
        addFlagsToIntent(intentFor, context);
        return intentFor;
    }

    private RouterParams paramsForUrl(String str) {
        RouterParams routerParams;
        Map<String, String> urlToParamsMap;
        String cleanUrl = cleanUrl(str);
        String substring = URI.create("http://tempuri.org/" + cleanUrl).getPath().substring(1);
        if (this._cachedRoutes.get(cleanUrl) != null) {
            return this._cachedRoutes.get(cleanUrl);
        }
        String[] split = substring.split("/");
        for (String str2 : split) {
        }
        Iterator<Map.Entry<String, RouterOptions>> it2 = this._routes.entrySet().iterator();
        while (true) {
            if (!it2.hasNext()) {
                routerParams = null;
                break;
            }
            Map.Entry<String, RouterOptions> next = it2.next();
            String cleanUrl2 = cleanUrl(next.getKey());
            RouterOptions value = next.getValue();
            String[] split2 = cleanUrl2.split("/");
            for (String str3 : split2) {
            }
            if (split2.length == split.length && (urlToParamsMap = urlToParamsMap(split, split2)) != null) {
                routerParams = new RouterParams();
                routerParams.openParams = urlToParamsMap;
                routerParams.routerOptions = value;
                break;
            }
        }
        if (routerParams == null) {
            ToastUtils.showShort("No route found for url " + str);
            return null;
        }
        this._cachedRoutes.put(cleanUrl, routerParams);
        return routerParams;
    }

    private Map<String, String> urlToParamsMap(String[] strArr, String[] strArr2) {
        HashMap hashMap = new HashMap();
        for (int i = 0; i < strArr2.length; i++) {
            String str = strArr2[i];
            String str2 = strArr[i];
            if (str.charAt(0) == ':') {
                hashMap.put(str.substring(1, str.length()), str2);
            } else if (!str.equals(str2)) {
                return null;
            }
        }
        return hashMap;
    }

    private String cleanUrl(String str) {
        return str.startsWith("/") ? str.substring(1, str.length()) : str;
    }

    /* loaded from: classes4.dex */
    public static class RouteNotFoundException extends RuntimeException {
        private static final long serialVersionUID = -2278644339983544651L;

        public RouteNotFoundException(String str) {
            super(str);
        }
    }

    /* loaded from: classes4.dex */
    public static class ContextNotProvided extends RuntimeException {
        private static final long serialVersionUID = -1381427067387547157L;

        public ContextNotProvided(String str) {
            super(str);
        }
    }
}
