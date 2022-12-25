package org.xutils.view;

import android.app.Activity;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import org.xutils.C5540x;
import org.xutils.ViewInjector;
import org.xutils.common.util.LogUtil;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/* loaded from: classes4.dex */
public final class ViewInjectorImpl implements ViewInjector {
    private static final HashSet<Class<?>> IGNORED = new HashSet<>();
    private static volatile ViewInjectorImpl instance;
    private static final Object lock;

    static {
        IGNORED.add(Object.class);
        IGNORED.add(Activity.class);
        IGNORED.add(Fragment.class);
        try {
            IGNORED.add(Class.forName("android.support.v4.app.Fragment"));
            IGNORED.add(Class.forName("android.support.v4.app.FragmentActivity"));
        } catch (Throwable unused) {
        }
        lock = new Object();
    }

    private ViewInjectorImpl() {
    }

    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ViewInjectorImpl();
                }
            }
        }
        C5540x.Ext.setViewInjector(instance);
    }

    @Override // org.xutils.ViewInjector
    public void inject(View view) {
        injectObject(view, view.getClass(), new ViewFinder(view));
    }

    @Override // org.xutils.ViewInjector
    public void inject(Activity activity) {
        int value;
        Class<?> cls = activity.getClass();
        try {
            ContentView findContentView = findContentView(cls);
            if (findContentView != null && (value = findContentView.value()) > 0) {
                cls.getMethod("setContentView", Integer.TYPE).invoke(activity, Integer.valueOf(value));
            }
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        injectObject(activity, cls, new ViewFinder(activity));
    }

    @Override // org.xutils.ViewInjector
    public void inject(Object obj, View view) {
        injectObject(obj, obj.getClass(), new ViewFinder(view));
    }

    @Override // org.xutils.ViewInjector
    public View inject(Object obj, LayoutInflater layoutInflater, ViewGroup viewGroup) {
        int value;
        Class<?> cls = obj.getClass();
        View view = null;
        try {
            ContentView findContentView = findContentView(cls);
            if (findContentView != null && (value = findContentView.value()) > 0) {
                view = layoutInflater.inflate(value, viewGroup, false);
            }
        } catch (Throwable th) {
            LogUtil.m43e(th.getMessage(), th);
        }
        injectObject(obj, cls, new ViewFinder(view));
        return view;
    }

    private static ContentView findContentView(Class<?> cls) {
        if (cls == null || IGNORED.contains(cls)) {
            return null;
        }
        ContentView contentView = (ContentView) cls.getAnnotation(ContentView.class);
        return contentView == null ? findContentView(cls.getSuperclass()) : contentView;
    }

    private static void injectObject(Object obj, Class<?> cls, ViewFinder viewFinder) {
        Event event;
        ViewInject viewInject;
        if (cls == null || IGNORED.contains(cls)) {
            return;
        }
        injectObject(obj, cls.getSuperclass(), viewFinder);
        Field[] declaredFields = cls.getDeclaredFields();
        if (declaredFields != null && declaredFields.length > 0) {
            for (Field field : declaredFields) {
                Class<?> type = field.getType();
                if (!Modifier.isStatic(field.getModifiers()) && !Modifier.isFinal(field.getModifiers()) && !type.isPrimitive() && !type.isArray() && (viewInject = (ViewInject) field.getAnnotation(ViewInject.class)) != null) {
                    try {
                        View findViewById = viewFinder.findViewById(viewInject.value(), viewInject.parentId());
                        if (findViewById != null) {
                            field.setAccessible(true);
                            field.set(obj, findViewById);
                        } else {
                            throw new RuntimeException("Invalid @ViewInject for " + cls.getSimpleName() + "." + field.getName());
                            break;
                        }
                    } catch (Throwable th) {
                        LogUtil.m43e(th.getMessage(), th);
                    }
                }
            }
        }
        Method[] declaredMethods = cls.getDeclaredMethods();
        if (declaredMethods == null || declaredMethods.length <= 0) {
            return;
        }
        for (Method method : declaredMethods) {
            if (!Modifier.isStatic(method.getModifiers()) && Modifier.isPrivate(method.getModifiers()) && (event = (Event) method.getAnnotation(Event.class)) != null) {
                try {
                    int[] value = event.value();
                    int[] parentId = event.parentId();
                    int length = parentId == null ? 0 : parentId.length;
                    int i = 0;
                    while (i < value.length) {
                        int i2 = value[i];
                        if (i2 > 0) {
                            ViewInfo viewInfo = new ViewInfo();
                            viewInfo.value = i2;
                            viewInfo.parentId = length > i ? parentId[i] : 0;
                            method.setAccessible(true);
                            EventListenerManager.addEventMethod(viewFinder, viewInfo, event, obj, method);
                        }
                        i++;
                    }
                } catch (Throwable th2) {
                    LogUtil.m43e(th2.getMessage(), th2);
                }
            }
        }
    }
}
