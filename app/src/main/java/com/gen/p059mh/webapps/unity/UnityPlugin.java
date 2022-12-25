package com.gen.p059mh.webapps.unity;

import android.support.p002v4.app.NotificationCompat;
import com.gen.p059mh.webapps.Plugin;
import com.gen.p059mh.webapps.unity.Unity;
import com.gen.p059mh.webapps.utils.Logger;
import com.google.gson.Gson;
import com.tomatolive.library.utils.LogConstants;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

/* renamed from: com.gen.mh.webapps.unity.UnityPlugin */
/* loaded from: classes2.dex */
public class UnityPlugin extends Plugin {
    public static Stack stack = new Stack();

    /* renamed from: id */
    public static int f1303id = 1638;
    public Map<String, Class<Unity>> unityClasses = new HashMap();
    Map<String, Map<String, Method>> unityMethods = new HashMap();
    Map<String, Map<String, Field>> unityFields = new HashMap();
    public Map<String, Object> unityObjectWithJava = new HashMap();
    public Map<String, Object> unityObjectMap = new HashMap();

    public UnityPlugin() {
        super("unity");
        stack.push(this.unityObjectMap);
    }

    public <T extends Unity> void registerUnity(Class<T> cls, String str) {
        if (!str.startsWith("@")) {
            str = "@" + str;
        }
        this.unityClasses.put(str, cls);
    }

    public void registerObject(Unity unity, String str) {
        registerObject(unity, str, null);
    }

    public void registerObject(Unity unity, String str, Object obj) {
        try {
            if (!str.startsWith("@")) {
                str = "@" + str;
            }
            unity.setWebViewFragment(getWebViewFragment());
            unity.setExecutor(getExecutor());
            unity.onInitialize(obj);
            unity.setId(str);
            this.unityObjectWithJava.put(str, unity);
            unity.onReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void process(String str, Plugin.PluginCallback pluginCallback) {
        Logger.m4112i("UnityPlugin", str);
        Map map = (Map) new Gson().fromJson(str, (Class<Object>) Map.class);
        if (map != null) {
            String str2 = (String) map.get(LogConstants.FOLLOW_OPERATION_TYPE);
            char c = 65535;
            switch (str2.hashCode()) {
                case -1183693704:
                    if (str2.equals("invoke")) {
                        c = 1;
                        break;
                    }
                    break;
                case 108960:
                    if (str2.equals("new")) {
                        c = 2;
                        break;
                    }
                    break;
                case 3143097:
                    if (str2.equals("find")) {
                        c = 0;
                        break;
                    }
                    break;
                case 539043005:
                    if (str2.equals("push.state")) {
                        c = 4;
                        break;
                    }
                    break;
                case 1557372922:
                    if (str2.equals("destroy")) {
                        c = 3;
                        break;
                    }
                    break;
                case 1649844916:
                    if (str2.equals("pop.state")) {
                        c = 5;
                        break;
                    }
                    break;
            }
            if (c == 0) {
                findAction((String) map.get("key"), pluginCallback);
            } else if (c == 1) {
                invokeAction(map, pluginCallback);
            } else if (c == 2) {
                newAction(map, pluginCallback);
            } else if (c == 3) {
                destroyAction(map, pluginCallback);
            } else if (c == 4) {
                pushStateAction();
            } else if (c != 5) {
            } else {
                popStateAction();
            }
        }
    }

    private void findAction(String str, Plugin.PluginCallback pluginCallback) {
        Method[] methods;
        if (this.unityClasses.containsKey(str)) {
            HashMap hashMap = new HashMap();
            hashMap.put("type", "class");
            hashMap.put("key", str);
            Class<Unity> cls = this.unityClasses.get(str);
            ArrayList arrayList = new ArrayList();
            if (cls != null) {
                for (Method method : cls.getMethods()) {
                    if (Modifier.isStatic(method.getModifiers())) {
                        arrayList.add(method.getName());
                    }
                }
            }
            hashMap.put("success", true);
            hashMap.put("methods", arrayList);
            pluginCallback.response(hashMap);
        }
        if (this.unityObjectWithJava.containsKey(str)) {
            HashMap hashMap2 = new HashMap();
            hashMap2.put("type", "object");
            hashMap2.put("key", str);
            Object obj = this.unityObjectWithJava.get(str);
            if (obj == null) {
                hashMap2.put("success", false);
                pluginCallback.response(hashMap2);
                return;
            } else if (obj instanceof Function) {
                hashMap2.put("type", "method");
                hashMap2.put("success", true);
                pluginCallback.response(hashMap2);
                return;
            } else if (obj instanceof Unity) {
                hashMap2.put("methods", ((Unity) obj).getMethods());
                hashMap2.put("success", true);
                pluginCallback.response(hashMap2);
                return;
            }
        }
        if (this.unityObjectMap.containsKey(str)) {
            HashMap hashMap3 = new HashMap();
            hashMap3.put("type", "object");
            hashMap3.put("key", str);
            Object obj2 = this.unityObjectMap.get(str);
            if (obj2 == null) {
                hashMap3.put("success", false);
                pluginCallback.response(hashMap3);
            } else if (obj2 instanceof Function) {
                hashMap3.put("type", "method");
                hashMap3.put("success", true);
                pluginCallback.response(hashMap3);
            } else if (obj2 instanceof Unity) {
                hashMap3.put("methods", ((Unity) obj2).getMethods());
                hashMap3.put("success", true);
                pluginCallback.response(hashMap3);
            }
        }
    }

    private void invokeAction(Map map, final Plugin.PluginCallback pluginCallback) {
        Method[] methods;
        if (map.get("key") == null || map.get("key").toString().length() <= 0) {
            return;
        }
        String obj = map.get("key").toString();
        if (this.unityObjectMap.containsKey(obj)) {
            Unity unity = (Unity) this.unityObjectMap.get(obj);
            if (unity == null) {
                return;
            }
            String str = (String) map.get("method");
            if (map.get("method") == null && (unity instanceof Function)) {
                str = "invoke";
            }
            unity.invokeAction(str, new Unity.MethodCallback() { // from class: com.gen.mh.webapps.unity.UnityPlugin.1
                @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
                public void run(Object obj2) {
                    HashMap hashMap = new HashMap();
                    hashMap.put("success", true);
                    hashMap.put("result", UnityPlugin.this.toScript(obj2));
                    pluginCallback.response(hashMap);
                }
            }, fromScript(map.get("params")));
        } else if (this.unityClasses.containsKey(obj)) {
            String str2 = (String) map.get("method");
            List list = (List) map.get("params");
            Class<Unity> cls = this.unityClasses.get(obj);
            if (cls != null) {
                for (Method method : cls.getMethods()) {
                    if (Modifier.isStatic(method.getModifiers()) && method.getName().equals(str2)) {
                        try {
                            method.invoke(null, fromScript(list), new Unity.MethodCallback() { // from class: com.gen.mh.webapps.unity.UnityPlugin.2
                                @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
                                public void run(Object obj2) {
                                    HashMap hashMap = new HashMap();
                                    hashMap.put("success", true);
                                    hashMap.put("result", UnityPlugin.this.toScript(obj2));
                                    pluginCallback.response(hashMap);
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            HashMap hashMap = new HashMap();
                            hashMap.put("success", false);
                            hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "View not found!");
                            pluginCallback.response(hashMap);
                        }
                    }
                }
                return;
            }
            targetIsNull(pluginCallback);
        } else if (this.unityObjectWithJava.containsKey(obj)) {
            Unity unity2 = (Unity) this.unityObjectWithJava.get(obj);
            if (unity2 == null) {
                return;
            }
            String str3 = (String) map.get("method");
            if (map.get("method") == null && (unity2 instanceof Function)) {
                str3 = "invoke";
            }
            unity2.invokeAction(str3, new Unity.MethodCallback() { // from class: com.gen.mh.webapps.unity.UnityPlugin.3
                @Override // com.gen.p059mh.webapps.unity.Unity.MethodCallback
                public void run(Object obj2) {
                    HashMap hashMap2 = new HashMap();
                    hashMap2.put("success", true);
                    hashMap2.put("result", UnityPlugin.this.toScript(obj2));
                    pluginCallback.response(hashMap2);
                }
            }, fromScript(map.get("params")));
        } else {
            noFind(pluginCallback);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v1, types: [java.util.Map] */
    public Object fromScript(Object obj) {
        Logger.m4112i("fromScript", obj != 0 ? obj.toString() : "null");
        if (obj == 0) {
            return null;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            for (int i = 0; i < list.size(); i++) {
                list.set(i, fromScript(list.get(i)));
            }
        } else if (obj instanceof Map) {
            obj = (Map) obj;
            if (obj.get("__object") != null && ((Boolean) obj.get("__object")).booleanValue()) {
                Object obj2 = this.unityObjectMap.containsKey(obj.get("_key").toString()) ? this.unityObjectMap.get(obj.get("_key").toString()) : null;
                if (obj2 == null) {
                    return null;
                }
                return obj2 instanceof Function ? (Function) obj2 : obj2;
            }
            for (Object obj3 : obj.keySet()) {
                obj.put(obj3, fromScript(obj.get(obj3)));
            }
        }
        return obj;
    }

    public Object toScript(Object obj) {
        Logger.m4112i("toScript", obj != null ? obj.toString() : "null");
        if (obj == null) {
            return null;
        }
        if (obj instanceof List) {
            List list = (List) obj;
            for (int i = 0; i < list.size(); i++) {
                list.set(i, toScript(list.get(i)));
            }
            return list;
        } else if (obj instanceof Unity) {
            Unity unity = (Unity) obj;
            if (!this.unityObjectMap.containsKey(unity.getId())) {
                this.unityObjectMap.put(unity.getId(), unity);
            }
            HashMap hashMap = new HashMap();
            hashMap.put("__object", true);
            hashMap.put("type", "object");
            hashMap.put("key", unity.getId());
            hashMap.put("methods", unity.getMethods());
            return hashMap;
        } else if (obj instanceof Map) {
            Map map = (Map) obj;
            for (Object obj2 : map.keySet()) {
                map.put(obj2, toScript(map.get(obj2)));
            }
            return map;
        } else if (Unity.Method.class.isAssignableFrom(obj.getClass())) {
            MethodFunction methodFunction = new MethodFunction((Unity.Method) obj);
            HashMap hashMap2 = new HashMap();
            hashMap2.put("__object", true);
            hashMap2.put("type", "method");
            hashMap2.put("key", methodFunction.getId());
            hashMap2.put("methods", methodFunction.getMethods());
            this.unityObjectMap.put(methodFunction.getId() + "", methodFunction);
            return hashMap2;
        } else if (!(obj instanceof Class)) {
            return obj;
        } else {
            Class<Unity> cls = (Class) obj;
            if (!Unity.class.isAssignableFrom(cls)) {
                return obj;
            }
            String str = "@" + cls.getName();
            if (this.unityClasses.containsKey(str)) {
                HashMap hashMap3 = new HashMap();
                hashMap3.put("__object", true);
                hashMap3.put("type", "class");
                hashMap3.put("key", str);
                hashMap3.put("methods", this.unityClasses.get(str).getMethods());
                return hashMap3;
            }
            this.unityClasses.put("@" + cls.getName(), cls);
            HashMap hashMap4 = new HashMap();
            hashMap4.put("__object", true);
            hashMap4.put("type", "class");
            hashMap4.put("key", "@" + cls.getName());
            hashMap4.put("methods", cls.getMethods());
            return hashMap4;
        }
    }

    private void newAction(Map map, Plugin.PluginCallback pluginCallback) {
        List list = (List) map.get("params");
        if (map.get("key") != null && map.get("key").toString().length() > 0) {
            String obj = map.get("key").toString();
            if (this.unityClasses.containsKey(obj)) {
                Class<Unity> cls = this.unityClasses.get(obj);
                if (cls != null) {
                    try {
                        Unity newInstance = cls.getConstructor(new Class[0]).newInstance(new Object[0]);
                        newInstance.setWebViewFragment(getWebViewFragment());
                        newInstance.setExecutor(getExecutor());
                        newInstance.onInitialize(fromScript(list));
                        Map<String, Object> map2 = this.unityObjectMap;
                        map2.put(newInstance.getId() + "", newInstance);
                        newInstance.onReady();
                        HashMap hashMap = new HashMap();
                        hashMap.put("success", true);
                        hashMap.put("key", newInstance.getId() + "");
                        hashMap.put("methods", newInstance.getMethods());
                        pluginCallback.response(hashMap);
                        return;
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }
                targetIsNull(pluginCallback);
                return;
            }
            noFind(pluginCallback);
            return;
        }
        noFind(pluginCallback);
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void onShow() {
        super.onShow();
        for (String str : this.unityObjectWithJava.keySet()) {
            if (this.unityObjectWithJava.get(str) instanceof Unity) {
                ((Unity) this.unityObjectWithJava.get(str)).onShow();
            }
        }
        for (String str2 : this.unityObjectMap.keySet()) {
            if (this.unityObjectMap.get(str2) instanceof Unity) {
                ((Unity) this.unityObjectMap.get(str2)).onShow();
            }
        }
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void onHide() {
        super.onHide();
        for (String str : this.unityObjectWithJava.keySet()) {
            if (this.unityObjectWithJava.get(str) instanceof Unity) {
                ((Unity) this.unityObjectWithJava.get(str)).onHide();
            }
        }
        for (String str2 : this.unityObjectMap.keySet()) {
            if (this.unityObjectMap.get(str2) instanceof Unity) {
                ((Unity) this.unityObjectMap.get(str2)).onHide();
            }
        }
    }

    @Override // com.gen.p059mh.webapps.Plugin
    public void unload() {
        for (String str : this.unityObjectWithJava.keySet()) {
            if (this.unityObjectWithJava.get(str) instanceof Unity) {
                ((Unity) this.unityObjectWithJava.get(str)).unload();
                ((Unity) this.unityObjectWithJava.get(str)).onDestroy();
            }
        }
        this.unityObjectWithJava.clear();
        for (String str2 : this.unityObjectMap.keySet()) {
            if (this.unityObjectMap.get(str2) instanceof Unity) {
                ((Unity) this.unityObjectMap.get(str2)).unload();
                ((Unity) this.unityObjectMap.get(str2)).onDestroy();
            }
        }
        this.unityObjectMap.clear();
        super.unload();
    }

    private void destroyAction(Map map, Plugin.PluginCallback pluginCallback) {
        if (this.unityObjectMap.containsKey(map.get("key"))) {
            Unity unity = (Unity) this.unityObjectMap.get(map.get("key"));
            if (unity != null) {
                unity.unload();
                unity.onDestroy();
                this.unityObjectMap.remove(unity);
                HashMap hashMap = new HashMap();
                hashMap.put("success", true);
                hashMap.put("key", map.get("key"));
                pluginCallback.response(hashMap);
                return;
            }
            targetIsNull(pluginCallback);
            return;
        }
        noFind(pluginCallback);
    }

    private void pushStateAction() {
        Logger.m4113i("sss size:" + stack.size());
        this.unityObjectMap = new HashMap();
        stack.push(this.unityObjectMap);
    }

    private void popStateAction() {
        if (stack.size() > 0) {
            stack.pop();
        }
    }

    private void targetIsNull(Plugin.PluginCallback pluginCallback) {
        Logger.m4108w("Unity", "The find target is null");
        HashMap hashMap = new HashMap();
        hashMap.put("success", false);
        hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "The find target is null");
        pluginCallback.response(hashMap);
    }

    private void noFind(Plugin.PluginCallback pluginCallback) {
        Logger.m4108w("Unity", "No key find");
        HashMap hashMap = new HashMap();
        hashMap.put("success", false);
        hashMap.put(NotificationCompat.CATEGORY_MESSAGE, "No key find");
        pluginCallback.response(hashMap);
    }
}
