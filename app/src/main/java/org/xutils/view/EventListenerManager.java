package org.xutils.view;

import android.text.TextUtils;
import android.view.View;
import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import org.xutils.common.util.DoubleKeyValueMap;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.Event;

/* loaded from: classes4.dex */
final class EventListenerManager {
    private static final HashSet<String> AVOID_QUICK_EVENT_SET = new HashSet<>(2);
    private static final DoubleKeyValueMap<ViewInfo, Class<?>, Object> listenerCache = new DoubleKeyValueMap<>();

    static {
        AVOID_QUICK_EVENT_SET.add("onClick");
        AVOID_QUICK_EVENT_SET.add("onItemClick");
    }

    public static void addEventMethod(ViewFinder viewFinder, ViewInfo viewInfo, Event event, Object obj, Method method) {
        boolean z;
        try {
            View findViewByInfo = viewFinder.findViewByInfo(viewInfo);
            if (findViewByInfo == null) {
                return;
            }
            Class<?> type = event.type();
            String str = event.setter();
            if (TextUtils.isEmpty(str)) {
                str = "set" + type.getSimpleName();
            }
            String method2 = event.method();
            Object obj2 = listenerCache.get(viewInfo, type);
            if (obj2 != null) {
                DynamicHandler dynamicHandler = (DynamicHandler) Proxy.getInvocationHandler(obj2);
                z = obj.equals(dynamicHandler.getHandler());
                if (z) {
                    dynamicHandler.addMethod(method2, method);
                }
            } else {
                z = false;
            }
            if (!z) {
                DynamicHandler dynamicHandler2 = new DynamicHandler(obj);
                dynamicHandler2.addMethod(method2, method);
                obj2 = Proxy.newProxyInstance(type.getClassLoader(), new Class[]{type}, dynamicHandler2);
                listenerCache.put(viewInfo, type, obj2);
            }
            findViewByInfo.getClass().getMethod(str, type).invoke(findViewByInfo, obj2);
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
    }

    /* loaded from: classes4.dex */
    public static class DynamicHandler implements InvocationHandler {
        private static long lastClickTime;
        private WeakReference<Object> handlerRef;
        private final HashMap<String, Method> methodMap = new HashMap<>(1);

        public DynamicHandler(Object obj) {
            this.handlerRef = new WeakReference<>(obj);
        }

        public void addMethod(String str, Method method) {
            this.methodMap.put(str, method);
        }

        public Object getHandler() {
            return this.handlerRef.get();
        }

        @Override // java.lang.reflect.InvocationHandler
        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
            Object obj2 = this.handlerRef.get();
            if (obj2 != null) {
                String name = method.getName();
                if ("toString".equals(name)) {
                    return DynamicHandler.class.getSimpleName();
                }
                Method method2 = this.methodMap.get(name);
                if (method2 == null && this.methodMap.size() == 1) {
                    Iterator<Map.Entry<String, Method>> it2 = this.methodMap.entrySet().iterator();
                    if (it2.hasNext()) {
                        Map.Entry<String, Method> next = it2.next();
                        if (TextUtils.isEmpty(next.getKey())) {
                            method2 = next.getValue();
                        }
                    }
                }
                if (method2 != null) {
                    if (EventListenerManager.AVOID_QUICK_EVENT_SET.contains(name)) {
                        long currentTimeMillis = System.currentTimeMillis() - lastClickTime;
                        if (currentTimeMillis < 300) {
                            LogUtil.m46d("onClick cancelled: " + currentTimeMillis);
                            return null;
                        }
                        lastClickTime = System.currentTimeMillis();
                    }
                    try {
                        return method2.invoke(obj2, objArr);
                    } catch (Throwable th) {
                        throw new RuntimeException("invoke method error:" + obj2.getClass().getName() + "#" + method2.getName(), th);
                    }
                }
                LogUtil.m38w("method not impl: " + name + "(" + obj2.getClass().getSimpleName() + ")");
            }
            return null;
        }
    }
}
