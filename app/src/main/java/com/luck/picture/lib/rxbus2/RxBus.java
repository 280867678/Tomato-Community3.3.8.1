package com.luck.picture.lib.rxbus2;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes3.dex */
public class RxBus {
    private static volatile RxBus defaultInstance;
    private Map<Class, List<Disposable>> subscriptionsByEventType = new HashMap();
    private Map<Object, List<Class>> eventTypesBySubscriber = new HashMap();
    private Map<Class, List<SubscriberMethod>> subscriberMethodByEventType = new HashMap();
    private final Subject<Object> bus = PublishSubject.create().toSerialized();

    private RxBus() {
    }

    public static RxBus getDefault() {
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                if (defaultInstance == null) {
                    defaultInstance = new RxBus();
                }
            }
        }
        return defaultInstance;
    }

    public <T> Flowable<T> toObservable(Class<T> cls) {
        return (Flowable<T>) this.bus.toFlowable(BackpressureStrategy.BUFFER).ofType(cls);
    }

    private <T> Flowable<T> toObservable(final int i, final Class<T> cls) {
        return this.bus.toFlowable(BackpressureStrategy.BUFFER).ofType(Message.class).filter(new Predicate<Message>(this) { // from class: com.luck.picture.lib.rxbus2.RxBus.2
            @Override // io.reactivex.functions.Predicate
            public boolean test(Message message) throws Exception {
                return message.getCode() == i && cls.isInstance(message.getObject());
            }
        }).map(new Function<Message, Object>(this) { // from class: com.luck.picture.lib.rxbus2.RxBus.1
            @Override // io.reactivex.functions.Function
            /* renamed from: apply  reason: avoid collision after fix types in other method */
            public Object mo6755apply(Message message) throws Exception {
                return message.getObject();
            }
        }).cast(cls);
    }

    public void register(Object obj) {
        Method[] declaredMethods;
        for (Method method : obj.getClass().getDeclaredMethods()) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                Class<?>[] parameterTypes = method.getParameterTypes();
                if (parameterTypes != null && parameterTypes.length == 1) {
                    Class<?> cls = parameterTypes[0];
                    addEventTypeToMap(obj, cls);
                    Subscribe subscribe = (Subscribe) method.getAnnotation(Subscribe.class);
                    SubscriberMethod subscriberMethod = new SubscriberMethod(obj, method, cls, subscribe.code(), subscribe.threadMode());
                    addSubscriberToMap(cls, subscriberMethod);
                    addSubscriber(subscriberMethod);
                } else if (parameterTypes == null || parameterTypes.length == 0) {
                    addEventTypeToMap(obj, BusData.class);
                    Subscribe subscribe2 = (Subscribe) method.getAnnotation(Subscribe.class);
                    SubscriberMethod subscriberMethod2 = new SubscriberMethod(obj, method, BusData.class, subscribe2.code(), subscribe2.threadMode());
                    addSubscriberToMap(BusData.class, subscriberMethod2);
                    addSubscriber(subscriberMethod2);
                }
            }
        }
    }

    private void addEventTypeToMap(Object obj, Class cls) {
        List<Class> list = this.eventTypesBySubscriber.get(obj);
        if (list == null) {
            list = new ArrayList<>();
            this.eventTypesBySubscriber.put(obj, list);
        }
        if (!list.contains(cls)) {
            list.add(cls);
        }
    }

    private void addSubscriberToMap(Class cls, SubscriberMethod subscriberMethod) {
        List<SubscriberMethod> list = this.subscriberMethodByEventType.get(cls);
        if (list == null) {
            list = new ArrayList<>();
            this.subscriberMethodByEventType.put(cls, list);
        }
        if (!list.contains(subscriberMethod)) {
            list.add(subscriberMethod);
        }
    }

    private void addSubscriptionToMap(Class cls, Disposable disposable) {
        List<Disposable> list = this.subscriptionsByEventType.get(cls);
        if (list == null) {
            list = new ArrayList<>();
            this.subscriptionsByEventType.put(cls, list);
        }
        if (!list.contains(disposable)) {
            list.add(disposable);
        }
    }

    private void addSubscriber(final SubscriberMethod subscriberMethod) {
        Flowable observable;
        int i = subscriberMethod.code;
        if (i == -1) {
            observable = toObservable(subscriberMethod.eventType);
        } else {
            observable = toObservable(i, subscriberMethod.eventType);
        }
        addSubscriptionToMap(subscriberMethod.subscriber.getClass(), postToObservable(observable, subscriberMethod).subscribe(new Consumer<Object>() { // from class: com.luck.picture.lib.rxbus2.RxBus.3
            @Override // io.reactivex.functions.Consumer
            public void accept(Object obj) throws Exception {
                RxBus.this.callEvent(subscriberMethod, obj);
            }
        }));
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.luck.picture.lib.rxbus2.RxBus$4 */
    /* loaded from: classes3.dex */
    public static /* synthetic */ class C22704 {
        static final /* synthetic */ int[] $SwitchMap$com$luck$picture$lib$rxbus2$ThreadMode = new int[ThreadMode.values().length];

        static {
            try {
                $SwitchMap$com$luck$picture$lib$rxbus2$ThreadMode[ThreadMode.MAIN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$luck$picture$lib$rxbus2$ThreadMode[ThreadMode.NEW_THREAD.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$luck$picture$lib$rxbus2$ThreadMode[ThreadMode.CURRENT_THREAD.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    private Flowable postToObservable(Flowable flowable, SubscriberMethod subscriberMethod) {
        Scheduler mainThread;
        int i = C22704.$SwitchMap$com$luck$picture$lib$rxbus2$ThreadMode[subscriberMethod.threadMode.ordinal()];
        if (i == 1) {
            mainThread = AndroidSchedulers.mainThread();
        } else if (i == 2) {
            mainThread = Schedulers.newThread();
        } else if (i == 3) {
            mainThread = Schedulers.trampoline();
        } else {
            throw new IllegalStateException("Unknown thread mode: " + subscriberMethod.threadMode);
        }
        return flowable.observeOn(mainThread);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void callEvent(SubscriberMethod subscriberMethod, Object obj) {
        List<SubscriberMethod> list = this.subscriberMethodByEventType.get(obj.getClass());
        if (list == null || list.size() <= 0) {
            return;
        }
        for (SubscriberMethod subscriberMethod2 : list) {
            if (((Subscribe) subscriberMethod2.method.getAnnotation(Subscribe.class)).code() == subscriberMethod.code && subscriberMethod.subscriber.equals(subscriberMethod2.subscriber) && subscriberMethod.method.equals(subscriberMethod2.method)) {
                subscriberMethod2.invoke(obj);
            }
        }
    }

    public synchronized boolean isRegistered(Object obj) {
        return this.eventTypesBySubscriber.containsKey(obj);
    }

    public void unregister(Object obj) {
        List<Class> list = this.eventTypesBySubscriber.get(obj);
        if (list != null) {
            for (Class cls : list) {
                unSubscribeByEventType(obj.getClass());
                unSubscribeMethodByEventType(obj, cls);
            }
            this.eventTypesBySubscriber.remove(obj);
        }
    }

    private void unSubscribeByEventType(Class cls) {
        List<Disposable> list = this.subscriptionsByEventType.get(cls);
        if (list != null) {
            Iterator<Disposable> it2 = list.iterator();
            while (it2.hasNext()) {
                Disposable next = it2.next();
                if (next != null && !next.isDisposed()) {
                    next.dispose();
                    it2.remove();
                }
            }
        }
    }

    private void unSubscribeMethodByEventType(Object obj, Class cls) {
        List<SubscriberMethod> list = this.subscriberMethodByEventType.get(cls);
        if (list != null) {
            Iterator<SubscriberMethod> it2 = list.iterator();
            while (it2.hasNext()) {
                if (it2.next().subscriber.equals(obj)) {
                    it2.remove();
                }
            }
        }
    }

    public void post(Object obj) {
        this.bus.onNext(obj);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes3.dex */
    public class Message {
        private int code;
        private Object object;

        /* JADX INFO: Access modifiers changed from: private */
        public int getCode() {
            return this.code;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Object getObject() {
            return this.object;
        }
    }
}
